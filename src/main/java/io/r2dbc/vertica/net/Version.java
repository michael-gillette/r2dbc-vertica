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
package io.r2dbc.vertica.net;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Vertica uses SemVer.
 *
 * @see <a href="https://semver.org/">SemVer</a>.
 * @since 19.2
 */
public final class Version implements Comparable<Version> {

    private final int major;

    private final int minor;

    private final int patch;

    private final String version;

    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.version = String.format(Locale.ROOT, "%d.%d.%d", major, minor, patch);
    }

    public int major() {
        return major;
    }

    public int minor() {
        return minor;
    }

    public int patch() {
        return patch;
    }

    @Override
    public int compareTo(Version other) {
        return Comparator.comparing(Version::major)
            .thenComparing(Version::minor)
            .thenComparing(Version::patch)
            .compare(this, other);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Version)) {
            return false;
        }

        Version o = (Version) object;
        return major == o.major && minor == o.minor && patch == o.patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }

    @Override
    public String toString() {
        return version;
    }

}
