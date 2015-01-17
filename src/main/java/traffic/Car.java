package traffic;

/**
 * @author kjrz
 */
public interface Car {

    public class CrashException extends IllegalStateException {
        // TODO: remember position where it is
    }

    public int look(int range);

    public void move(int steps);
}
