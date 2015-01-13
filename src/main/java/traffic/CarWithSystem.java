package traffic;

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

    public int clearAhead(int range) {
        return road.seeAhead(position, range);
    }

    public void stepAhead() {
        position = road.stepAhead(position);
    }

    @Override
    public String toString() {
        return id;
    }

    // TODO: close time frame and notify rode

    // TODO: add agent and listen to agent
}
