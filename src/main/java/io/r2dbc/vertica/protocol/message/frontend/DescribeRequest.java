package io.r2dbc.vertica.protocol.message.frontend;

import io.netty.buffer.ByteBuf;

import java.util.Objects;

public final class DescribeRequest implements FrontendMessage<DescribeRequest.Data> {

    @Override
    public Data decode(ByteBuf src) {
        if (src.readByte() != 68) {
            throw new IllegalArgumentException("expected content type: 68");
        }

        src.readInt();
        boolean usePortal = src.readByte() == 80;
        String name = Wire.readCStringUTF8(src);

        return new Data(usePortal, name);
    }

    @Override
    public void encode(Data content, ByteBuf dst) {
        dst.writeByte(68);
        dst.writeInt(6 + content.name().length());
        dst.writeByte(content.usePortal() ? 80 : 83);
        Wire.writeCStringUTF8(dst, content.name());
    }

    public static class Data implements FrontendContent {

        private final boolean usePortal;
        private final String name;

        public Data(boolean usePortal, String name) {
            this.usePortal = usePortal;
            this.name = name;
        }

        public boolean usePortal() {
            return usePortal;
        }

        public String name() {
            return name;
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
            return usePortal == data.usePortal && Objects.equals(name, data.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(usePortal, name);
        }

        @Override
        public String toString() {
            return "DescribeRequest.Data{" +
                    "usePortal=" + usePortal +
                    ", name='" + name + '\'' +
                    '}';
        }

    }


}
