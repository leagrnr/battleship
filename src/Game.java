import java.util.HashSet;
import java.util.Set;

public class Game {
    private Board playerBoard;
    private Board computerBoard;
    private Set<String> computerMoves;
    private int[] lastComputerMove;
    private int playerTurns;
    private int computerTurns;

    public Game(int size) {
        playerBoard = new Board(size);
        computerBoard = new Board(size);
        computerMoves = new HashSet<>();
        playerTurns = 0;
        computerTurns = 0;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getComputerBoard() {
        return computerBoard;
    }

    public void placeShips(Board board) {
        int[][] shipSizes = {{5}, {4}, {3}, {3}, {2}};
        for (int[] size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                int x = (int) (Math.random() * board.getSize());
                int y = (int) (Math.random() * board.getSize());
                boolean horizontal = Math.random() < 0.5;
                int[][] coordinates = generateCoordinates(x, y, size[0], horizontal);
                if (coordinates != null) {
                    placed = board.placeShip(new Ship(size[0], coordinates));
                }
            }
        }
    }

    private int[][] generateCoordinates(int x, int y, int size, boolean horizontal) {
        int[][] coordinates = new int[size][2];
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                if (y + i >= playerBoard.getSize()) return null;
                coordinates[i][0] = x;
                coordinates[i][1] = y + i;
            } else {
                if (x + i >= playerBoard.getSize()) return null;
                coordinates[i][0] = x + i;
                coordinates[i][1] = y;
            }
        }
        return coordinates;
    }

    public boolean playerTurn(int x, int y) {
        playerTurns++;
        return computerBoard.checkHit(x, y);
    }

    public boolean computerTurn() {
        if (computerMoves.size() >= playerBoard.getSize() * playerBoard.getSize()) {
            return false;
        }

        int x, y;
        do {
            x = (int) (Math.random() * playerBoard.getSize());
            y = (int) (Math.random() * playerBoard.getSize());
        } while (computerMoves.contains(x + "," + y));

        computerMoves.add(x + "," + y);
        lastComputerMove = new int[]{x, y};
        computerTurns++;
        return playerBoard.checkHit(x, y);
    }

    public int[] getLastComputerMove() {
        return lastComputerMove;
    }

    public boolean isPlayerWin() {
        return computerBoard.allShipsSunk();
    }

    public boolean isComputerWin() {
        return playerBoard.allShipsSunk();
    }

    public int getPlayerTurns() {
        return playerTurns;
    }

    public int getComputerTurns() {
        return computerTurns;
    }
}
