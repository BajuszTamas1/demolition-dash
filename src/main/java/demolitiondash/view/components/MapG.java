    package demolitiondash.view.components;

    import demolitiondash.model.*;
    import demolitiondash.model.Box;
    import demolitiondash.model.bomb.Bomb;
    import demolitiondash.model.map.Map;
    import demolitiondash.model.map.MapObserver;
    import demolitiondash.model.powerup.PowerupItem;
    import demolitiondash.res.ResourceLoader;
    import demolitiondash.util.Coordinate;
    import demolitiondash.util.ImageUtils;
    import demolitiondash.util.player.PlayerStore;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.image.BufferedImage;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Queue;
    import java.util.concurrent.ConcurrentLinkedQueue;


    public class MapG extends JPanel implements MapObserver {
        private final Map map;
        private final ArrayList<BombG> bombs = new ArrayList<>();
        private final ArrayList<AnimateG> animates = new ArrayList<>();
        private List<FlameG> flames =  new ArrayList<>();
        private final ArrayList<PowerupItemG> powerupsItems = new ArrayList<>();
        private final ArrayList<BoxG> boxes = new ArrayList<>();
        private final Queue<Drawable> staticElemsToAdd = new ConcurrentLinkedQueue<>();
        private final BufferedImage prerendered;
        private final Image barricadeImage = ResourceLoader.loadImage("imgs/assets/Items/Barricade/Idle.png");
        private final BufferedImage barricadePreviewOK;
        private final BufferedImage barricadePreviewBAD;

        public MapG(Map map){
            this.map = map;
            this.prerendered = prerenderMap();
            this.barricadePreviewOK = ImageUtils.adjustHue(
                ImageUtils.toBufferedImage(barricadeImage), 0.3);
            this.barricadePreviewBAD = ImageUtils.applyOpacity(
                    ImageUtils.adjustHue(
                            ImageUtils.toBufferedImage(barricadeImage), 0.9),
                    0.4f);
            this.setSize(new Dimension(prerendered.getWidth(), prerendered.getHeight()));
            map.addObserver(this);
            addBoxWrappers();
            addAnimateWrappers();
            addNewStaticElems();
        }

        private void addBoxWrappers() {
            List<Box> boxes = map.getStaticElems().stream().filter(e -> e instanceof Box).map(e -> (Box) e).toList();
            for(var box: boxes){
                boxAdded(box);
            }
        }

        private void addAnimateWrappers(){
            List<Double> hues = PlayerStore.getInstance().getPlayerHues();
            List<Player> players = map.getPlayers();
            for(int i = 0; i < players.size(); i++){
                animates.add(new PlayerG(players.get(i), hues.get(i)));
            }
            for(var monster : map.getMonsters()){
                animates.add(new MonsterG(monster));
            }
        }

        @Override
        public void bombAdded(Bomb bomb) {
            staticElemsToAdd.add(new BombG(bomb));
        }

        @Override
        public void flamesUpdated(List<Flame> flames) {
            this.flames = flames.stream().map(FlameG::new).toList();
        }

        @Override
        public void powerupItemAdded(PowerupItem powerupItem) {
            powerupsItems.add(new PowerupItemG(powerupItem));
        }

        @Override
        public void barricadeOutlineAdded(Player player) {
            var playerG = animates.stream().filter(a -> a.getAnimate() == player).findAny();
            playerG.ifPresent(g -> ((PlayerG) g).showBarricadeOutline = true);
        }

        @Override
        public void barricadeOutlineRemoved(Player player) {
            var playerG = animates.stream().filter(a -> a.getAnimate() == player).findAny();
            playerG.ifPresent(g -> ((PlayerG) g).showBarricadeOutline = false);
        }

        @Override
        public void boxAdded(Box box) {
            if(box instanceof Barricade bar) staticElemsToAdd.add(new BarricadeG(bar));
            else staticElemsToAdd.add(new BoxG(box));
        }


        private BufferedImage prerenderMap() {
            BufferedImage prerendered = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = prerendered.createGraphics();

            Image green = ResourceLoader.loadImage("imgs/assets/Background/Green.png");
            Image gold = ResourceLoader.loadImage("imgs/assets/Terrain/Gold.png");

            for (int row = 0; row < map.ROW_COUNT; row++) {
                for (int col = 0; col < map.COL_COUNT; col++) {
                    g2d.drawImage(green, col * Map.TILE_SIZE, row * Map.TILE_SIZE, Map.TILE_SIZE, Map.TILE_SIZE, null);
                }
            }
            for(var entity: map.getStaticElems().stream().filter(e -> e instanceof Wall).toList()){
                g2d.drawImage(gold, entity.getLocation().x, entity.getLocation().y, Map.TILE_SIZE, Map.TILE_SIZE, null);
            }

            g2d.dispose();
            return prerendered;
        }

        synchronized private void removeDisposableEntities(){
            animates.removeIf(AnimateG::isReadyToGetDisposed);
            boxes.removeIf(BoxG::isReadyToGetDisposed);
            bombs.removeIf(BombG::isReadyToGetDisposed);
            powerupsItems.removeIf(PowerupItemG::isReadyToGetDisposed);
        }

        private void addNewStaticElems(){
            synchronized (staticElemsToAdd){
                for(var elem: staticElemsToAdd){
                    if(elem instanceof BombG bomb) bombs.add(bomb);
                    else if(elem instanceof BoxG box) boxes.add(box);
                }
                staticElemsToAdd.clear();
            }
        }

        public void update(){
            removeDisposableEntities();
            addNewStaticElems();
        }

        @Override
        synchronized protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D gr = (Graphics2D)g;

            gr.drawImage(prerendered, 0, 0, null);

            for(var item: powerupsItems){
                gr.drawImage(item.getImage(),
                        item.getLocation().x, item.getLocation().y,
                        Map.TILE_SIZE, Map.TILE_SIZE,
                        null);
            }
            for(var box: boxes){
                gr.drawImage(box.getImage(),
                        box.getLocation().x, box.getLocation().y,
                        box.getBox().getSize().width, box.getBox().getSize().height,
                        null);
            }
            for(var ent: bombs){
                gr.drawImage(ent.getImage(),
                        ent.getLocation().x, ent.getLocation().y,
                        ent.getBomb().getSize().width, ent.getBomb().getSize().height,
                        null);
            }
            for(var ent: animates){
                gr.drawImage(ent.getImage(),
                        ent.getLocation().x, ent.getLocation().y,
                        ent.getAnimate().getSize().width, ent.getAnimate().getSize().height,
                        null);


                if(ent instanceof PlayerG pg && pg.showBarricadeOutline && pg.getPlayer().canPlaceBarricade()){
                    Coordinate barricadeLocation = map.getBarricadeLocation(pg.getPlayer());

                    BufferedImage img = barricadePreviewBAD;
                    if(map.canPlaceBarricade(pg.getPlayer(), barricadeLocation))
                        img = barricadePreviewOK;

                    gr.drawImage(img, barricadeLocation.x,barricadeLocation.y, Map.TILE_SIZE, Map.TILE_SIZE, null);
                }
            }
            for(var ent: flames){
                gr.drawImage(ent.getImage(),
                        ent.getLocation().x, ent.getLocation().y,
                        Map.TILE_SIZE, Map.TILE_SIZE,
                        null);
            }
        }
    }
