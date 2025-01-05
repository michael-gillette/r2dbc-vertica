package io.r2dbc.vertica.protocol.message.backend;

import io.netty.buffer.ByteBuf;

import java.util.Objects;

public final class ReadyForQueryResponse implements BackendMessage<ReadyForQueryResponse.Data> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Data decode(ByteBuf src) {
        int readableBytes = src.readableBytes();
        if (readableBytes != 1) {
            throw new IllegalArgumentException("expected buffer with 1 byte remaining but found " + readableBytes);
        }

        return new Data(src.readByte());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(Data content, ByteBuf dst) {
        dst.writeByte(content.transactionState());
    }

    public static final class Data implements BackendContent {

        private final byte transactionState;

        public Data(byte transactionState) {
            this.transactionState = transactionState;
        }

        public byte transactionState() {
            return transactionState;
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
            return transactionState == data.transactionState;
        }

        @Override
        public int hashCode() {
            return Objects.hash(transactionState);
        }

        @Override
        public String toString() {
            return "ReadyForQueryResponse.Data{" +
                    "transactionState=" + transactionState +
                    '}';
        }

    }

}
