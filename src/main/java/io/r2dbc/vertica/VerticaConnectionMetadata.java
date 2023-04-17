/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.r2dbc.vertica;

import io.r2dbc.spi.ConnectionMetadata;
import io.r2dbc.vertica.net.Version;

/**
 * A Vertica specific {@link ConnectionMetadata}.
 *
 * @since 0.0.1
 */
public final class VerticaConnectionMetadata implements ConnectionMetadata {

    private final Version version;

    public VerticaConnectionMetadata(Version version) {
        this.version = version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDatabaseProductName() {
        return "vertica";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDatabaseVersion() {
        return version.toString();
    }

}
