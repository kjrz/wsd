package road;

/**
 * @author kjrz
 */
public class Position {
    public final int x;
    public final int y;
    public final int a;
    public final boolean lights;

    public Position(){
        this.x = 0;
        this.y = 0;
        this.a = 0;
        this.lights = false;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.a = 0;
        this.lights = false;
    }

    public Position(int x, int y, int a) {
        this.x = x;
        this.y = y;
        this.a = a;
        this.lights = false;
    }

    public Position(int x, int y, boolean lights) {
        this.x = x;
        this.y = y;
        this.a = 0;
        this.lights = lights;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
