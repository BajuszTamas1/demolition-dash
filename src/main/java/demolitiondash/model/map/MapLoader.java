package demolitiondash.model.map;

import demolitiondash.model.Barricade;
import demolitiondash.model.Box;
import demolitiondash.model.Entity;
import demolitiondash.model.Wall;
import demolitiondash.res.ResourceLoader;
import demolitiondash.util.Coordinate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {
    private static MapLoader instace;
    private ArrayList<MapLayout> mapLayouts = new ArrayList<>();
    private MapLoader(String path){
        preloadMaps(path);
    }

    public static MapLoader getInstance(){
        if(instace == null) instace = new MapLoader("levels.txt");
        return instace;
    }

    private void preloadMaps(String path){
        InputStream is = ResourceLoader.loadResource(path);

        try (Scanner sc = new Scanner(is)){
            String line = readNextLine(sc);
            ArrayList<String> rows = new ArrayList<>();

            while (!line.isEmpty()){
                rows.clear();
                line = readNextLine(sc);
                while (!line.isEmpty() && line.trim().charAt(0) != ';'){
                    rows.add(line);
                    line = readNextLine(sc);
                }
                addLayout(rows);
            }
        } catch (Exception e){
            System.out.println("Error while loading the maps: " + e.getStackTrace());
        }
    }

    // # - wall
    // $ - box
    //  - empty
    // % - baricade
    // @ - player
    /// & - monster
    private void addLayout(ArrayList<String> rowsList){
        int cols = 0;
        for (String s : rowsList){
            if (s.length() > cols) cols = s.length();
        }

        int rows = rowsList.size();

        ArrayList<Coordinate> monsters = new ArrayList<>();
        ArrayList<Coordinate> players = new ArrayList<>();
        ArrayList<Entity> statics = new ArrayList<>();

        for (int i = 0; i < rows; i++){
            String s = rowsList.get(i);
            for (int j = 0; j < s.length(); j++){
                var coord = Coordinate.multiply(new Coordinate(j, i), Map.TILE_SIZE);
                switch (s.charAt(j)){
                    case '@': players.add(coord); break;
                    case '#': statics.add(new Wall(coord)); break;
                    case '%': statics.add(new Barricade(coord)); break;
                    case '$': statics.add(new Box(coord)); break;
                    case '&': monsters.add(coord); break;
                }
            }
        }
        mapLayouts.add(new MapLayout(statics, rows, cols, players, monsters));
    }

    /**
     * Retrieves a map layout by index.
     *
     * @param index The index of the map layout to retrieve.
     * @return The map layout at the specified index.
     */
    public MapLayout getLayout(int index){
        return mapLayouts.get(index).clone();
    }

    private String readNextLine(Scanner sc){
        String line = "";
        while (sc.hasNextLine() && line.trim().isEmpty()){
            line = sc.nextLine();
        }
        return line;
    }

}
