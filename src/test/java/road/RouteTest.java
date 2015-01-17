package road;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author kjrz
 */
public class RouteTest {

    static Route r;

    @BeforeClass
    public static void prepareTestRoute() {
        List<Position> p = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i == 7) p.add(new Position(i, 0, true));
            else p.add(new Position(i, 0));
        }
        r = new Route(p);
    }

    @Test
    public void shouldAllowStepsByOne() {
        // given
        int i = 0;

        // when
        int j = r.next(i);

        // then
        assertThat(j, is(i + 1));
    }

    @Test
    public void shouldBeCyclic() {
        // given
        int i = 9;

        // when
        int j = r.next(i);

        // then
        assertThat(j, is(0));
    }

    @Test
    public void shouldSkipLights() {
        // given
        int i = 6;

        // when
        int j = r.next(i);

        // then
        assertThat(j, is(i + 2));
    }
}
