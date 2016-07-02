
package de.unratedfilms.scriptspace.common.script;

import java.util.List;
import org.apache.commons.lang3.Validate;
import com.google.common.collect.ImmutableList;
import de.unratedfilms.scriptspace.common.script.api.settings.Setting;
import de.unratedfilms.scriptspace.common.script.services.ScriptCompilationService;

public class Program {

    private final SourceScript           sourceScript;
    private final ImmutableList<Setting> settings;

    /**
     * Creates a program with the script's default settings.
     */
    public Program(SourceScript sourceScript) throws ScriptCompilationException {

        Validate.notNull(sourceScript);

        this.sourceScript = sourceScript;
        settings = getCompiledScript().getDefaultSettings();
    }

    public Program(SourceScript sourceScript, List<Setting> settings) {

        Validate.notNull(sourceScript);
        Validate.notNull(settings);

        this.sourceScript = sourceScript;
        this.settings = ImmutableList.copyOf(settings);
    }

    public SourceScript getSourceScript() {

        return sourceScript;
    }

    public CompiledScript getCompiledScript() throws ScriptCompilationException {

        return ScriptCompilationService.compileScript(sourceScript);
    }

    public ImmutableList<Setting> getSettings() {

        return settings;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (settings == null ? 0 : settings.hashCode());
        result = prime * result + (sourceScript == null ? 0 : sourceScript.hashCode());
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
        Program other = (Program) obj;
        if (settings == null) {
            if (other.settings != null) {
                return false;
            }
        } else if (!settings.equals(other.settings)) {
            return false;
        }
        if (sourceScript == null) {
            if (other.sourceScript != null) {
                return false;
            }
        } else if (!sourceScript.equals(other.sourceScript)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "program of script with settings " + settings + ", based on " + sourceScript;
    }

}
