package road;

import movie.Camera;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import traffic.CarWithSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author kjrz
 */
public class RoadTest {

    static Route route;
    Road road;
    CarWithSystem bmw;
    CarWithSystem opel;

    @BeforeClass
    public static void setUpRoute() {
        List<Position> p = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i == 7) p.add(new Position(i, 0, true));
            else p.add(new Position(i, 0));
        }
        route = new Route(p);
    }

    @Before
    public void setUpRoad() {
        road = new Road(route);
        this.bmw = road.addCarWithSystem(0, "bmw");
        this.opel = road.addCarWithSystem(3, "opel");
    }

    @Test
    public void shouldLetThemLook() {
        assertThat(road.look(0, 2), is(0)); // not in range
        assertThat(road.look(0, 3), is(3)); // in range
        assertThat(road.look(9, 9), is(1)); // cyclic
    }

    @Test
    public void shouldKnowPositionsInNewTimeFrame() {
        // when
        bmw.move(4);
        opel.move(5);

        // then
        assertThat(bmw.look(3), is(0));
        assertThat(bmw.look(4), is(4));
    }

    @Test
    public void shouldWorkWithCamera() {
        // given
        Camera cam = mock(Camera.class);
        road.mountCamera(cam);

        // when
        bmw.move(1);
        opel.move(1);

        // then
        verify(cam, times(1)).nextTimeFrame(anySetOf(Integer.class));
    }
}
