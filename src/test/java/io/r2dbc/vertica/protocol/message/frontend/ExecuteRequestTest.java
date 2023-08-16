package io.r2dbc.vertica.protocol.message.frontend;

import io.netty.buffer.Unpooled;
import io.r2dbc.vertica.protocol.message.ProtocolMessageTestBase;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

class ExecuteRequestTest extends ProtocolMessageTestBase<ExecuteRequest.Data> {

    public ExecuteRequestTest() {
        super(new ExecuteRequest());
    }

    @Override
    protected Stream<Translation<ExecuteRequest.Data>> translations() {
        return Stream.of(
            new Translation<>(
                "uses portal",
                new ExecuteRequest.Data(
                    "portal",
                    2
                ),
                Unpooled.buffer()
                    .writeByte(69)
                    .writeInt(15)
                    .writeBytes("portal".getBytes(StandardCharsets.UTF_8))
                    .writeByte(0)
                    .writeInt(2)
            ),
            new Translation<>(
                "uses client",
                new ExecuteRequest.Data(
                    "client",
                    4
                ),
                Unpooled.buffer()
                    .writeByte(69)
                    .writeInt(15)
                    .writeBytes("client".getBytes(StandardCharsets.UTF_8))
                    .writeByte(0)
                    .writeInt(4)
            )
        );
    }

}
