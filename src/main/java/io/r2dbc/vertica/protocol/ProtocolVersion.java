package io.r2dbc.vertica.protocol;

/**
 * The Vertica Protocol Version.
 *
 * <p>
 * These values are hardcoded for the project.
 */
public abstract class ProtocolVersion {

    /**
     * The textual representation of the protocol version.
     */
    public static final String STR = "3.14";

    /**
     * The integer representation of the protocol version.
     */
    public static final int INT;

    /**
     * The version part to the left of the "{@literal .}".
     */
    public static final short MAJOR;

    /**
     * The version part to the right of the "{@literal .}".
     */
    public static final short MINOR;

    static {
        String[] versionParts = STR.split("\\.");
        int versionMajor = Integer.parseInt(versionParts[0]);
        int versionMinor = versionParts.length == 1 ? 0 : Integer.parseInt(versionParts[1]);

        INT = versionMajor << 16 | versionMinor;
        MAJOR = (short) (INT >> 16);
        MINOR = (short) (INT & '\uffff');
    }

    /**
     * Converts a protocol version received over the wire to text.
     *
     * @param version The integer representation of a version.
     * @return {@link String}
     */
    public static String stringFromInt(int version) {
        return ((short) (version >> 16)) + "." + ((short) (version & '\uffff'));
    }

    private ProtocolVersion() {
        // this is a utility class
    }

}
