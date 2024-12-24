package io.r2dbc.vertica.protocol.message.frontend;

import io.netty.buffer.Unpooled;
import io.r2dbc.vertica.protocol.ProtocolVersion;
import io.r2dbc.vertica.protocol.message.ProtocolMessageTestBase;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

class StartupRequestTest extends ProtocolMessageTestBase<StartupRequest.Data> {

    public StartupRequestTest() {
        super(new StartupRequest());
    }

    @Override
    protected Stream<Translation<StartupRequest.Data>> translations() {
        HashMap<String, String> manyMap = new LinkedHashMap<>();
        manyMap.put("hello", "world");
        manyMap.put("protocol_version", ProtocolVersion.STR);

        return Stream.of(
            new Translation<>(
                "whenZeroParams",
                new StartupRequest.Data(
                    Map.of()
                ),
                Unpooled.buffer()
                    .writeInt(9)
                    .writeShort(ProtocolVersion.MAJOR)
                    .writeShort(ProtocolVersion.MINOR)
                    .writeChar(0)
            ),
            new Translation<>(
                "whenManyParams",
                new StartupRequest.Data(
                    manyMap
                ),
                Unpooled.buffer()
                    .writeInt(42)
                    .writeShort(ProtocolVersion.MAJOR)
                    .writeShort(ProtocolVersion.MINOR)
                    .writeBytes("hello".getBytes(StandardCharsets.UTF_8))
                    .writeByte(0)
                    .writeBytes("world".getBytes(StandardCharsets.UTF_8))
                    .writeByte(0)
                    .writeBytes("protocol_version".getBytes(StandardCharsets.UTF_8))
                    .writeByte(0)
                    .writeInt(ProtocolVersion.INT)
                    .writeChar(0)
            )
        );
    }

}
