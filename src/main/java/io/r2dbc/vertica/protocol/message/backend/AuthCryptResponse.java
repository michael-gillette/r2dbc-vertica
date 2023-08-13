package io.r2dbc.vertica.protocol.message.backend;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;

public final class AuthCryptResponse implements BackendMessage<AuthCryptResponse.Data> {

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
        Wire.writeBytesLE(dst, content.salt());
    }

    public static final class Data implements BackendContent {

        private final byte[] salt;

        public Data(byte[] salt) {
            this.salt = salt;
        }

        public byte[] salt() {
            return salt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Arrays.equals(salt, data.salt);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(salt);
        }

        @Override
        public String toString() {
            return "Data{" +
                    "salt=" + Arrays.toString(salt) +
                    '}';
        }
    }

}
