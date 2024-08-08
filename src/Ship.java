public class Ship {
    private int size;
    private int[][] coordinates;

    public Ship(int size, int[][] coordinates) {
        this.size = size;
        this.coordinates = coordinates;
    }

    public int getSize() {
        return size;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }
}
