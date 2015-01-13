package road;

/**
 * @author kjrz
 */
public class Position {
    public final int x;
    public final int y;
    public final boolean lights;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.lights = false;
    }

    public Position(int x, int y, boolean lights) {
        this.x = x;
        this.y = y;
        this.lights = lights;
    }
}
