package io.r2dbc.vertica.protocol.message.frontend;

import io.r2dbc.vertica.protocol.message.Message;

public interface FrontendMessage<T extends FrontendMessage.FrontendContent> extends Message<T> {

    interface FrontendContent extends Content {

    }

}
