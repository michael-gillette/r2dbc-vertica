package io.r2dbc.vertica.protocol.message.frontend;

import io.netty.buffer.ByteBuf;
import io.r2dbc.vertica.protocol.ProtocolVersion;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class StartupRequest implements FrontendMessage<StartupRequest.Data> {

    @Override
    public Data decode(ByteBuf src) {
        if (src.readableBytes() < 9) {
            throw new IllegalArgumentException("expected 'size', 'major', 'minor', NUL elements");
        }

        int size = src.readInt();
        @SuppressWarnings("unused")
        short protocolVersionMajor = src.readShort();
        @SuppressWarnings("unused")
        short protocolVersionMinor = src.readShort();

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        ByteBuf parametersSlice = src.readSlice(size - 9);
        while (parametersSlice.readableBytes() > 0) {
            String k = Wire.readCStringUTF8(parametersSlice);
            if (k.equals("protocol_version")) {
                parameters.put(k, ProtocolVersion.stringFromInt(parametersSlice.readInt()));
            } else {
                parameters.put(k, Wire.readCStringUTF8(parametersSlice));
            }
        }

        return new Data(parameters);
    }

    @Override
    public void encode(Data content, ByteBuf dst) {
        int lengthOffset = dst.writerIndex();

        dst.writeInt(0);
        dst.writeShort(ProtocolVersion.MAJOR);
        dst.writeShort(ProtocolVersion.MINOR);

        content.parameters().forEach((key, value) -> {
            Wire.writeCStringUTF8(dst, key);

            if (key.equals("protocol_version")) {
                dst.writeInt(ProtocolVersion.INT);
            } else {
                Wire.writeCStringUTF8(dst, value);
            }
        });

        dst.writeByte(0);

        dst.setInt(lengthOffset, dst.writerIndex() - lengthOffset);
    }

    public static final class Data implements FrontendContent {

        private final Map<String, String> parameters;

        public Data(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        public Map<String, String> parameters() {
            return parameters;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(parameters, data.parameters);
        }

        @Override
        public int hashCode() {
            return Objects.hash(parameters);
        }

        @Override
        public String toString() {
            return "StartupRequest.Data{" +
                    "parameters=" + parameters +
                    '}';
        }
    }

}
