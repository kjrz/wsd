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
        int next = current + 1;
        if (next == steps.size()) next = 0;
        if (steps.get(next).lights) return next(next);
        else return next;
    }

    public int size() {
        return steps.size();
    }
}
