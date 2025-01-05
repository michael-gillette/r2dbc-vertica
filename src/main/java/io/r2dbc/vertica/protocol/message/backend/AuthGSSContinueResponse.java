package io.r2dbc.vertica.protocol.message.backend;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;

public final class AuthGSSContinueResponse implements BackendMessage<AuthGSSContinueResponse.Data> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Data decode(ByteBuf src) {
        return new Data(Wire.readBytesLE(src));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(Data content, ByteBuf dst) {
        Wire.writeBytesLE(dst, content.gssData());
    }

    public static final class Data implements BackendContent {

        private final byte[] gssData;

        public Data(byte[] gssData) {
            this.gssData = gssData;
        }

        public byte[] gssData() {
            return gssData;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Data)) {
                return false;
            }
            Data data = (Data) o;
            return Arrays.equals(gssData, data.gssData);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(gssData);
        }

        @Override
        public String toString() {
            return "AuthGSSContinueResponse.Data{" +
                    "gssData=" + Arrays.toString(gssData) +
                    '}';
        }
    }

}
