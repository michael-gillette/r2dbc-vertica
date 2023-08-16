package io.r2dbc.vertica.protocol.message.frontend;

import io.netty.buffer.Unpooled;
import io.r2dbc.vertica.protocol.message.ProtocolMessageTestBase;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

class DescribeRequestTest extends ProtocolMessageTestBase<DescribeRequest.Data> {

    public DescribeRequestTest() {
        super(new DescribeRequest());
    }

    @Override
    protected Stream<Translation<DescribeRequest.Data>> translations() {
        return Stream.of(
            new Translation<>(
                "uses portal",
                new DescribeRequest.Data(
                    true,
                    "portal"
                ),
                Unpooled.buffer()
                    .writeByte(68)
                    .writeInt(12)
                    .writeByte(80)
                    .writeBytes("portal".getBytes(StandardCharsets.UTF_8))
                    .writeByte(0)
            ),
            new Translation<>(
                "uses client",
                new DescribeRequest.Data(
                    false,
                    "client"
                ),
                Unpooled.buffer()
                    .writeByte(68)
                    .writeInt(12)
                    .writeByte(83)
                    .writeBytes("client".getBytes(StandardCharsets.UTF_8))
                    .writeByte(0)
            )
        );
    }

}
