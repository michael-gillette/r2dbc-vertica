package io.r2dbc.vertica.protocol.message.backend;

import io.r2dbc.vertica.protocol.message.Message;

/**
 * Represents a message received from the Vertica server.
 *
 * @param <T> A POJO that represents the contents of this message.
 * @since 0.0.1
 */
public interface BackendMessage<T extends BackendMessage.BackendContent> extends Message<T> {

    /**
     * Represents the contents of a message received from the Vertica server.
     */
    interface BackendContent extends Content {

    }

}
