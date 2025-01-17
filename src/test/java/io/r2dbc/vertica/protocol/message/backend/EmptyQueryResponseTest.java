package io.r2dbc.vertica.protocol.message.backend;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EmptyQueryResponseTest {

    private final EmptyQueryResponse subject = new EmptyQueryResponse();

    @Nested
    @DisplayName("decode(ByteBuf)")
    class DecodeByteBuf {

        @Test
        void whenTooManyBytes_thenThrow() {
            ByteBuf src = Unpooled.buffer().writeByte(1);

            Assertions.assertThatThrownBy(() -> subject.decode(src))
                .isOfAnyClassIn(IllegalArgumentException.class)
                .hasMessage("expected no content");
        }

        @Test
        void whenOk_thenReturn() {
            ByteBuf src = Unpooled.buffer();

            Assertions.assertThat(subject.decode(src))
                .isEqualTo(EmptyQueryResponse.Data.INSTANCE);
        }

    }

    @Nested
    @DisplayName("encode(T, ByteBuf)")
    class EncodeTByteBuf {

        @Test
        void whenOk_thenReturn() {
            ByteBuf dst = Unpooled.buffer();

            subject.encode(EmptyQueryResponse.Data.INSTANCE, dst);

            Assertions.assertThat(dst)
                .isEqualTo(
                    Unpooled.buffer(0)
                );
        }

    }

}
