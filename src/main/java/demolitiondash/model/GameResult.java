package demolitiondash.model;

import java.util.Optional;

public class GameResult {
    private final boolean isTie;
    private final String winner;

    private GameResult(boolean isTie, String winner) {
        this.isTie = isTie;
        this.winner = winner;
    }


    public static GameResult tie() {
        return new GameResult(true, null);
    }

    public static GameResult win(String winner){
        return new GameResult(false, winner);
    }

    public boolean isTie() {
        return isTie;
    }

    public boolean isWin(){
        return !isTie;
    }

    public Optional<String> getWinner(){
        if(isTie) return Optional.empty();
        return Optional.of(winner);
    }

    @Override
    public String toString() {
        return isTie ? "TIE" : winner + " WON";
    }
}