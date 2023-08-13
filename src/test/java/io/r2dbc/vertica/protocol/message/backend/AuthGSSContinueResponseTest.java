package io.r2dbc.vertica.protocol.message.backend;

import io.netty.buffer.Unpooled;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AuthGSSContinueResponseTest {

    private final AuthGSSContinueResponse subject = new AuthGSSContinueResponse();

    @Nested
    @DisplayName("decode(ByteBuf)")
    class DecodeByteBuf {

        @Test
        void whenOk_thenReturn() {
            var src = Unpooled.buffer().writeBytes(new byte[] {
                (byte) 0x01, (byte) 0x02, (byte) 0x03,
            });

            Assertions.assertThat(subject.decode(src))
                .isEqualTo(new AuthGSSContinueResponse.Data(
                    new byte[] { (byte) 0x03, (byte) 0x02, (byte) 0x01 }
                ));
        }

    }

    @Nested
    @DisplayName("encode(T, ByteBuf)")
    class EncodeTByteBuf {

        @Test
        void whenOk_thenReturn() {
            var dst = Unpooled.buffer();

            subject.encode(
                new AuthGSSContinueResponse.Data(new byte[] {
                    (byte) 0x01, (byte) 0x02
                }),
                dst
            );

            Assertions.assertThat(dst)
                .isEqualTo(
                    Unpooled.buffer()
                        .writeByte(0x02)
                        .writeByte(0x01)
                );
        }

    }

}
