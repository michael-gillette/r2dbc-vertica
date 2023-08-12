package io.r2dbc.vertica.protocol.message.frontend;

public final class CloseRequest {

    private final String name;
    private final boolean isPortal;

    public CloseRequest(String name, boolean isPortal) {
        this.name = name;
        this.isPortal = isPortal;
    }

}
