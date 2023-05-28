package library;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HamcrestTest {

    @Test
    void standartAssertions() {
        assertThat("qwe", is("qwe"));
    }
}
