package io.r2dbc.vertica.protocol.message.frontend;

public final class SimpleQueryRequest {

    private final String sql;

    public SimpleQueryRequest(String sql) {
        this.sql = sql;
    }

}
