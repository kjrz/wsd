package traffic;

import road.Position;
import road.Road;

/**
 * @author kjrz
 */
public class CarWithSystem implements Car {
    private final String id;
    private final Road road;

    private int position;

    public CarWithSystem(int position, String id, Road road) {
        this.position = position;
        this.id = id;
        this.road = road;
    }

    @Override
    public int look(int range) {
        return road.look(position, range);
    }

    @Override
    public void move(int steps) {
        position = road.move(position, steps);
    }

    @Override
    public String toString() {
        Position p = road.getPosition(position);
        return String.format("%s@(%d, %d)", id, p.x, p.y);
    }

    // TODO: listen to agent
    // TODO: listen to driver
}
