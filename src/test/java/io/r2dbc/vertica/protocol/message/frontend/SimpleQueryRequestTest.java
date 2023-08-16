package io.r2dbc.vertica.protocol.message.frontend;

import io.netty.buffer.Unpooled;
import io.r2dbc.vertica.protocol.message.ProtocolMessageTestBase;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

class SimpleQueryRequestTest extends ProtocolMessageTestBase<SimpleQueryRequest.Data> {

    public SimpleQueryRequestTest() {
        super(new SimpleQueryRequest());
    }

    @Override
    protected Stream<Translation<SimpleQueryRequest.Data>> translations() {
        return Stream.of(
            new Translation<>(
                "simple statement",
                new SimpleQueryRequest.Data(
                    "SELECT 1"
                ),
                Unpooled.buffer()
                    .writeByte(81)
                    .writeInt(13)
                    .writeBytes("SELECT 1".getBytes(StandardCharsets.UTF_8))
                    .writeByte(0)
            )
        );
    }

}
