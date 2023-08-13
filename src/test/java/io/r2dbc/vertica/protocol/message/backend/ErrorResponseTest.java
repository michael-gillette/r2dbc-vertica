package io.r2dbc.vertica.protocol.message.backend;

import io.netty.buffer.Unpooled;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

class ErrorResponseTest {

    private final ErrorResponse subject = new ErrorResponse();

    @Nested
    @DisplayName("decode(ByteBuf)")
    class DecodeByteBuf {

        @Test
        void whenTooFewBytes_thenThrow() {
            var src = Unpooled.buffer();

            Assertions.assertThatThrownBy(() -> subject.decode(src))
                .isOfAnyClassIn(IllegalArgumentException.class)
                .hasMessage("expected non-empty content");
        }

        @ParameterizedTest
        @ValueSource(
            strings = { "hello", "world" }
        )
        void whenOk_thenReturn(String error) {
            var src = Unpooled.buffer();
            src.writeCharSequence(error, StandardCharsets.UTF_8);
            src.writeByte((byte) 0);

            Assertions.assertThat(subject.decode(src))
                .isEqualTo(new ErrorResponse.Data(error));
        }

    }

    @Nested
    @DisplayName("encode(T, ByteBuf)")
    class EncodeTByteBuf {

        @ParameterizedTest
        @MethodSource("io.r2dbc.vertica.protocol.message.backend.ErrorResponseTest#contentArgs")
        void whenOk_thenReturn(ErrorResponse.Data content) {
            var dst = Unpooled.buffer();

            subject.encode(content, dst);

            Assertions.assertThat(dst)
                .isEqualTo(
                    Unpooled.buffer()
                        .writeBytes(content.error().getBytes(StandardCharsets.UTF_8))
                        .writeByte(0)
                );
        }

    }

    public static Stream<ErrorResponse.Data> contentArgs() {
        return Stream.of(
            new ErrorResponse.Data("hello"),
            new ErrorResponse.Data("world")
        );
    }

}
