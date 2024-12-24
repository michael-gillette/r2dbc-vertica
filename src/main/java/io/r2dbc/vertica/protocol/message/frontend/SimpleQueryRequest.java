package io.r2dbc.vertica.protocol.message.frontend;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class SimpleQueryRequest implements FrontendMessage<SimpleQueryRequest.Data> {

    @Override
    public Data decode(ByteBuf src) {
        if (src.readByte() != 81) {
            throw new IllegalArgumentException("unexpected request type");
        }

        CharSequence sql = src.readCharSequence(src.readInt() - 5, StandardCharsets.UTF_8);
        src.readByte();

        return new Data(sql.toString());
    }

    @Override
    public void encode(Data content, ByteBuf dst) {
        String sql = content.sql().replace("\u0000", "\\000");

        dst.writeByte(81);
        dst.writeInt(5 + sql.length());
        Wire.writeCStringUTF8(dst, sql);
    }

    public static final class Data implements FrontendContent {

        private final String sql;

        public Data(String sql) {
            this.sql = sql;
        }

        public String sql() {
            return sql;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(sql, data.sql);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sql);
        }

        @Override
        public String toString() {
            return "SimpleQueryRequest.Data{" +
                    "sql='" + sql + '\'' +
                    '}';
        }
    }

}
