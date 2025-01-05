package io.r2dbc.vertica.protocol.message.frontend;

import io.r2dbc.vertica.protocol.type.StatementParameter;
import io.r2dbc.vertica.protocol.type.TypedValue;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class CopyDataRequest {

    private final boolean addHeader;
    private final Collection<StatementParameter> statementParameters;
    private final Map<Integer, Collection<TypedValue>> statementData;
    private final InputStream stream;
    private final int numParams;
    private final boolean isEmpty;

    public CopyDataRequest(
            boolean addHeader,
            Collection<StatementParameter> statementParameters,
            Map<Integer, Collection<TypedValue>> statementData,
            InputStream stream,
            int numParams,
            boolean isEmpty
    ) {
        this.addHeader = addHeader;
        this.statementParameters = Collections.unmodifiableCollection(new ArrayList<>(statementParameters));
        this.statementData = Collections.unmodifiableMap(new HashMap<>(statementData));
        this.stream = stream;
        this.numParams = numParams;
        this.isEmpty = isEmpty;
    }

}
