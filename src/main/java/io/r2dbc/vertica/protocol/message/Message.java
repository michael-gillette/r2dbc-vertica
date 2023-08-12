package io.r2dbc.vertica.protocol.message;

import io.netty.buffer.ByteBuf;

/**
 * Represents a message passed between a Vertica server and a Vertica client.
 *
 * @param <T> A POJO that represents the contents of the message.
 * @since 0.0.1
 */
public interface Message<T extends Message.Content> {

    /**
     * Decodes the contents of this message.
     *
     * @param src A buffer of bytes received by the Vertica client.
     * @return {@link T}
     */
    T decode(ByteBuf src);

    /**
     * Encodes the contents of this message.
     *
     * @param content The message content.
     * @param dst A buffer of bytes to send to the Vertica server.
     */
    void encode(T content, ByteBuf dst);

    /**
     * Represents the contents of a message.
     */
    interface Content {

    }

}
