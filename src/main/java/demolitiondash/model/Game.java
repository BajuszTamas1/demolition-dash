package demolitiondash.model;

import demolitiondash.model.map.Map;
import demolitiondash.model.map.MapLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game {
    private final int REFRESH_RATE = 60;
    public final int UPDATE_INTERV = 1000 / REFRESH_RATE;
    private final List<String> playerNames;
    private final List<Integer> playerScores;
    private final MapLayout mapLayout;
    private Map map;
    private final int roundsToWin;

    public Game(MapLayout layout, List<String> playerNames, int roundsToWin){
        this.mapLayout = layout;
        this.playerNames = playerNames;
        this.roundsToWin = roundsToWin;

        this.playerScores = new ArrayList<>();
        for(int i = 0; i < playerNames.size(); i++){
            playerScores.add(0);
        }

        initMap();
    }
    public Map getMap() {
        return map;
    }

    public List<Integer> getPlayerScores() {
        return playerScores;
    }

    public boolean isRoundOver(){
        return map.alivePlayerCount() <= 1 && map.getBombCount() == 0 && map.getExplosionCount() == 0;
    }

    public Optional<GameResult> getRoundResult(){
        if(isRoundOver()){
            if(map.alivePlayerCount() == 0)
                return Optional.of(GameResult.tie());
            return Optional.of(GameResult.win(map.alivePlayers().getFirst().getName()));
        }
        return Optional.empty();
    }

    public boolean isGameOver(){
        return isRoundOver() && playerScores.contains(roundsToWin);
    }

    public void recordRoundResult(GameResult res){
        if(res.getWinner().isPresent()){
            int index = playerNames.indexOf(res.getWinner().get());
            playerScores.set(index, playerScores.get(index) + 1);
        }
    }

    public Optional<GameResult> getGameResult(){
        if(isGameOver()) {
            var winnerIndex = playerScores.indexOf(roundsToWin);
            return Optional.of(GameResult.win(playerNames.get(winnerIndex)));
        }
        return Optional.empty();
    }

    public String getWinner(){
        if(isGameOver()) {
            var winnerIndex = playerScores.indexOf(roundsToWin);
            return playerNames.get(winnerIndex);
        }
        return null;
    }

    public void initMap(){
        map = new Map(mapLayout.clone());

        int playerCount = 1;
        for (String name : playerNames){
            new Player(map, name, playerCount);
            playerCount++;
        }

        var monsterCount = mapLayout.getMonsterPositions().size();
        for(int i = 0; i < monsterCount; i++)
            new Monster(map);
    }

    public void update(){
        map.update();
    }
}
