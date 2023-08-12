package io.r2dbc.vertica.protocol.message.frontend;

public final class PasswordRequest {

    private final byte[] password;
    private final boolean useGSS;

    public PasswordRequest(byte[] password, boolean useGSS) {
        this.password = password;
        this.useGSS = useGSS;
    }

}
