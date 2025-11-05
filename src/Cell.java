public class Cell {
    boolean alive;
    int age;

    public Cell(boolean alive, int age) {
        this.alive = alive;
        this.age = age;
    }

    public Cell() {
        this(false, 0);
    }
}
