package io.r2dbc.vertica.protocol.message;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

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

    /**
     * A set of functions for encoding and decoding content over the wire.
     */
    @SuppressWarnings("UnusedReturnValue")
    abstract class Wire {

        /**
         * Reads the remaining bytes in the buffer into an array, in reverse order.
         *
         * @param src A pool of bytes to read.
         * @return {@code byte[]}
         */
        public static byte[] readBytesLE(ByteBuf src) {
            var size = src.readableBytes();
            var result = new byte[size];
            for (var offset = size - 1; offset >= 0; offset--) {
                result[offset] = src.readByte();
            }
            return result;
        }

        /**
         * Reads a NULL terminated UTF-8 string.
         *
         * @param src A pool of bytes to read.
         * @return {@link String}
         */
        public static String readCStringUTF8(ByteBuf src) {
            var result = src.readCharSequence(src.bytesBefore((byte) 0), StandardCharsets.UTF_8);
            src.skipBytes(1);
            return result.toString();
        }

        /**
         * Writes a byte array into a buffer, in reverse order.
         *
         * @param dst A byte buffer.
         * @param bytes Data to write
         * @return {@link ByteBuf} ({@code dst})
         */
        public static ByteBuf writeBytesLE(ByteBuf dst, byte[] bytes) {
            for (var x = bytes.length - 1; x >= 0; x--) {
                dst.writeByte(bytes[x]);
            }
            return dst;
        }

        /**
         * Writes a NULL terminated UTF-8 string into a buffer.
         *
         * @param dst A byte buffer.
         * @param string String to write.
         * @return {@link ByteBuf} ({@code self})
         */
        public static ByteBuf writeCStringUTF8(ByteBuf dst, String string) {
            dst.writeCharSequence(string, StandardCharsets.UTF_8);
            dst.writeByte(0);
            return dst;
        }

        private Wire() {
            // this is a utility class
        }

    }

}
