package io.r2dbc.vertica.protocol.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

public abstract class ProtocolMessageTestBase<T extends Message.Content> {

    protected final Message<T> subject;

    protected ProtocolMessageTestBase(Message<T> subject) {
        this.subject = subject;
    }

    protected abstract Stream<Translation<T>> translations();

    @TestFactory
    Stream<DynamicTest> translationTests() {
        return translations().map(it -> DynamicTest.dynamicTest(
            it.name,
            () -> {
                T srcObj = it.content;
                ByteBuf srcBuf = it.buffer;
                T dstObj = subject.decode(srcBuf);
                ByteBuf dstBuf = Unpooled.buffer();
                subject.encode(srcObj, dstBuf);

                Assertions.assertThat(dstBuf.array())
                    .as("can encode what it decoded")
                    .isEqualTo(srcBuf.array());

                Assertions.assertThat(dstObj)
                    .as("can decode what it encoded")
                    .isEqualTo(srcObj);
            }
        ));
    }

    protected static final class Translation<T extends Message.Content> {
        public final String name;
        public final T content;
        public final ByteBuf buffer;

        public Translation(String name, T content, ByteBuf buffer) {
            this.name = name;
            this.content = content;
            this.buffer = buffer;
        }
    }

}
