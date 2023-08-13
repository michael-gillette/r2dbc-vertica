package io.r2dbc.vertica.protocol.message.backend;

import io.netty.buffer.Unpooled;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

class ReadyForQueryResponseTest {

    private final ReadyForQueryResponse subject = new ReadyForQueryResponse();

    @Nested
    @DisplayName("decode(ByteBuf)")
    class DecodeByteBuf {

        @Test
        void whenTooFewBytes_thenThrow() {
            var src = Unpooled.buffer();

            Assertions.assertThatThrownBy(() -> subject.decode(src))
                .isOfAnyClassIn(IllegalArgumentException.class)
                .hasMessage("expected buffer with 1 byte remaining but found 0");
        }

        @Test
        void whenTooManyBytes_thenThrow() {
            var src = Unpooled.buffer(2);

            Assertions.assertThatThrownBy(() -> subject.decode(src))
                    .isOfAnyClassIn(IllegalArgumentException.class)
                    .hasMessage("expected buffer with 1 byte remaining but found 0");
        }

        @ParameterizedTest
        @ValueSource(
            ints = { 0, 1 }
        )
        void whenOk_thenReturn(int transactionState) {
            var src = Unpooled.buffer().writeByte(transactionState);

            Assertions.assertThat(subject.decode(src))
                .isEqualTo(new ReadyForQueryResponse.Data((byte) transactionState));
        }

    }

    @Nested
    @DisplayName("encode(T, ByteBuf)")
    class EncodeTByteBuf {

        @ParameterizedTest
        @MethodSource("io.r2dbc.vertica.protocol.message.backend.ReadyForQueryResponseTest#contentArgs")
        void whenOk_thenReturn(ReadyForQueryResponse.Data content) {
            var dst = Unpooled.buffer();

            subject.encode(content, dst);

            Assertions.assertThat(dst)
                .isEqualTo(
                    Unpooled.buffer()
                        .writeByte(content.transactionState())
                );
        }

    }

    public static Stream<ReadyForQueryResponse.Data> contentArgs() {
        return Stream.of(
            new ReadyForQueryResponse.Data((byte) 0),
            new ReadyForQueryResponse.Data((byte) 1)
        );
    }

}
