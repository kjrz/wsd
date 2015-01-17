package road;

import movie.Camera;
import traffic.Car;
import traffic.CarWithSystem;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kjrz
 */
public class Road {
    private final Route route;
    private final Set<Camera> cams = new HashSet<>();
    private Set<Integer> positions = new HashSet<>();
    private Set<Integer> nextPositions = new HashSet<>();

    public Road(Route route) {
        this.route = route;
    }

    public CarWithSystem addCarWithSystem(int position, String id) {
        assertPosition(position);
        positions.add(position);
        return new CarWithSystem(position, id, this);
    }

    public Car addCarWithoutSystem(int position, String id) {
        throw new UnsupportedOperationException();
    }

    private void assertPosition(int position) {
        if (occupied(position)) {
            throw new IllegalArgumentException(position + " positions");
        }
        if (position >= route.size() || position < 0) {
            throw new IllegalArgumentException(position + " beyond range");
        }
    }

    private boolean occupied(int position) {
        return positions.contains(position);
    }

    public int look(int from, int range) {
        for (int i = 1; i <= range; i++) {
            from = route.next(from);
            if (occupied(from)) return i;
        }
        return 0;
    }

    public int move(int position, int steps) {
        for (int i = 0; i < steps; i++) position = route.next(position);
        if (nextPositions.contains(position)) throw new Car.CrashException();
        nextPositions.add(position);
        if (nextPositions.size() == positions.size()) timeFrameEnd();
        return position;
    }

    private void timeFrameEnd() {
        positions = nextPositions;
        nextPositions = new HashSet<>();
        sendImageToCameras(positions);
    }

    private void sendImageToCameras(Set<Integer> positions) {
        for (Camera cam : cams) {
            cam.nextTimeFrame(positions);
        }
    }

    public void mountCamera(Camera cam) {
        cams.add(cam);
    }

    public Position getPosition(int i) {
        return route.get(i);
    }
}
