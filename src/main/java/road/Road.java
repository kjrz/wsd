package road;

import traffic.Car;
import traffic.CarCrashException;
import traffic.CarWithSystem;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kjrz
 */
public class Road {
    private int carsCount = 0;
    private Set<Integer> occupied = new HashSet<>();
    private final Route route;

    public Road(Route route) {
        this.route = route;
    }

    public Car addCarWithSystem(int position, String id) {
        assertPosition(position);
        occupied.add(position);
        carsCount++;
        return new CarWithSystem(position, id, this);
    }

    public Car addCarWithoutSystem(int position, String id) {
        throw new UnsupportedOperationException();
    }

    private void assertPosition(int position) {
        if (occupied(position)) {
            throw new IllegalArgumentException(position + " occupied");
        }
        if (position >= route.size()) {
            throw new IllegalArgumentException(position + " beyond range");
        }
    }

    public boolean occupied(int position) {
        return occupied.contains(position);
    }

    public int seeAhead(int from, int range) {
        for (int i = 1; i <= range; i++) {
            if (occupied(from + i)) return i;
        }
        return 0;
    }

    public int stepAhead(int current) {
        occupied.remove(current);
        int next = route.next(current);
        if (occupied(next)) throw new CarCrashException();
        occupied.add(next);
        return next;
    }

    // TODO: close time frame and notify registered cameras
}
