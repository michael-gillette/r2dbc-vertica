package io.r2dbc.vertica.net;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class VersionTest {

    @Nested
    @DisplayName("given 1.2.3")
    class Given123 {

        private final Version subject = new Version(1, 2, 3);

        @Test
        void whenGreaterThan() {
            Assertions.assertThat(subject)
                .isLessThan(new Version(1, 2, 4))
                .isLessThan(new Version(1, 3, 3))
                .isLessThan(new Version(2, 2, 3));
        }

        @Test
        void whenEqualTo() {
            Assertions.assertThat(subject)
                .isEqualTo(new Version(1, 2, 3));
        }

        @Test
        void whenLesserThan() {
            Assertions.assertThat(subject)
                .isGreaterThan(new Version(1, 2, 2))
                .isGreaterThan(new Version(1, 1, 3))
                .isGreaterThan(new Version(0, 2, 3));
        }

    }

}
