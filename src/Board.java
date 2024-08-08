import java.util.ArrayList;
import java.util.List;

public class Board {
    private char[][] grid;
    private List<Ship> ships;

    public Board(int size) {
        grid = new char[size][size];
        ships = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '~';
            }
        }
    }

    public boolean placeShip(Ship ship) {
        for (int[] coord : ship.getCoordinates()) {
            int x = coord[0];
            int y = coord[1];
            if (x >= grid.length || y >= grid[0].length || grid[x][y] != '~') {
                return false;
            }
        }
        for (int[] coord : ship.getCoordinates()) {
            int x = coord[0];
            int y = coord[1];
            grid[x][y] = 'S';
        }
        ships.add(ship);
        return true;
    }

    public boolean checkHit(int x, int y) {
        if (grid[x][y] == 'S') {
            grid[x][y] = 'X';
            return true;
        } else {
            grid[x][y] = 'O';
            return false;
        }
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            for (int[] coord : ship.getCoordinates()) {
                if (grid[coord[0]][coord[1]] != 'X') {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getSize() {
        return grid.length;
    }
}