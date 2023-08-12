package io.r2dbc.vertica.protocol.message.frontend;

import io.r2dbc.vertica.protocol.type.StatementParameter;
import io.r2dbc.vertica.protocol.type.TypedValue;

import java.util.Collection;
import java.util.Map;

public final class BindRequest {

    private final Collection<StatementParameter> statementInputs;
    private final Map<Integer, Collection<TypedValue>> statementData;
    private final String statementName;
    private final String portalName;
    private final boolean useBinary;

    public BindRequest(
        Collection<StatementParameter> statementInputs,
        Map<Integer,
        Collection<TypedValue>> statementData,
        String statementName,
        String portalName,
        boolean useBinary
    ) {
        this.statementInputs = statementInputs;
        this.statementData = statementData;
        this.statementName = statementName;
        this.portalName = portalName;
        this.useBinary = useBinary;
    }

}
