package road;

import java.util.List;

/**
 * @author kjrz
 */
public class Route {
    private final List<Position> steps;

    public Route(List<Position> steps) {
        this.steps = steps;
    }

    public Position get(int current) {
        return steps.get(current);
    }

    public int next(int current) {
        return current + 1 < steps.size() ? current + 1 : 0;
    }

    public int size() {
        return steps.size();
    }
}
