
package de.unratedfilms.scriptspace.common.script;

import org.apache.commons.lang3.Validate;

/**
 * A script which has not yet been compiled.
 * This class stores the script's name and the raw sourcecode as a string, but no further information about the script.
 * Note that this class can be easily serialized and sent over the network, an ability that's not provided by {@link CompiledScript}s.
 *
 * @see CompiledScript
 */
public class SourceScript implements Comparable<SourceScript> {

    private final String name;
    private final String code;

    public SourceScript(String name, String code) {

        Validate.notBlank(name);
        Validate.notNull(code);

        this.name = name;
        this.code = code;
    }

    public String getName() {

        return name;
    }

    public String getCode() {

        return code;
    }

    @Override
    public int compareTo(SourceScript o) {

        return name.compareTo(o.name);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (code == null ? 0 : code.hashCode());
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SourceScript other = (SourceScript) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "source script '" + name + "' (" + code.length() + " chars)";
    }

}
