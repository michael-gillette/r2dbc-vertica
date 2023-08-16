package io.r2dbc.vertica.protocol.message.frontend;

import io.netty.buffer.ByteBuf;

import java.util.Objects;

public final class ExecuteRequest implements FrontendMessage<ExecuteRequest.Data> {

    @Override
    public Data decode(ByteBuf src) {
        if (src.readByte() != 69) {
            throw new IllegalArgumentException("expected content type: 69");
        }

        src.readInt();
        var name = Wire.readCStringUTF8(src);
        var maxRows = src.readInt();

        return new Data(name, maxRows);
    }

    @Override
    public void encode(Data content, ByteBuf dst) {
        dst.writeByte(69);
        dst.writeInt(9 + content.name().length());
        Wire.writeCStringUTF8(dst, content.name());
        dst.writeInt(content.maxRows());
    }

    public static final class Data implements FrontendContent {

        private final String name;
        private final int maxRows;

        public Data(String name, int maxRows) {
            this.name = name;
            this.maxRows = maxRows;
        }

        public String name() {
            return name;
        }

        public int maxRows() {
            return maxRows;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return maxRows == data.maxRows && Objects.equals(name, data.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, maxRows);
        }

        @Override
        public String toString() {
            return "ExecuteRequest.Data{" +
                    "name='" + name + '\'' +
                    ", maxRows=" + maxRows +
                    '}';
        }
    }

}
