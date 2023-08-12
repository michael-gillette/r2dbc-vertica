package io.r2dbc.vertica.protocol.message.frontend;

public final class CancelRequest {

    private final int backendProcessId;
    private final int cancelKey;

    public CancelRequest(int backendProcessId, int cancelKey) {
        this.backendProcessId = backendProcessId;
        this.cancelKey = cancelKey;
    }

}
