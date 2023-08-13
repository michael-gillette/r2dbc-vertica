package io.r2dbc.vertica.protocol.message.backend;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class ErrorResponse implements BackendMessage<ErrorResponse.Data> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Data decode(ByteBuf src) {
        if (src.readableBytes() == 0) {
            throw new IllegalArgumentException("expected non-empty content");
        }

        var error = src.readCharSequence(src.bytesBefore((byte) 0), StandardCharsets.UTF_8).toString();

        src.skipBytes(1);

        return new Data(error);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(Data content, ByteBuf dst) {
        dst.writeCharSequence(content.error(), StandardCharsets.UTF_8);
        dst.writeByte(0);
    }

    public static final class Data implements BackendContent {

        private final String error;

        public Data(String error) {
            this.error = error;
        }

        public String error() {
            return error;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(error, data.error);
        }

        @Override
        public int hashCode() {
            return Objects.hash(error);
        }

        @Override
        public String toString() {
            return "ErrorResponse.Data{" +
                    "error='" + error + '\'' +
                    '}';
        }

    }

}
