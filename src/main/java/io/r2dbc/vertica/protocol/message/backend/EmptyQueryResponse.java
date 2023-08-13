package io.r2dbc.vertica.protocol.message.backend;

import io.netty.buffer.ByteBuf;

public final class EmptyQueryResponse implements BackendMessage<EmptyQueryResponse.Data> {

    /**
     * No content.
     *
     * <p>
     * {@inheritDoc}
     */
    @Override
    public Data decode(ByteBuf src) {
        if (src.readableBytes() > 0) {
            throw new IllegalArgumentException("expected no content");
        }
        return Data.INSTANCE;
    }

    /**
     * No content.
     *
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void encode(Data content, ByteBuf dst) {
        // do nothing
    }

    public enum Data implements BackendContent {

        INSTANCE

    }

}
