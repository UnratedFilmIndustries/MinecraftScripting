
package de.unratedfilms.scriptspace.common.script;

import java.util.List;
import org.apache.commons.lang3.Validate;
import com.google.common.collect.ImmutableList;
import de.unratedfilms.scriptspace.common.script.api.settings.Setting;
import de.unratedfilms.scriptspace.common.script.services.ScriptCompilationService;

public class Program {

    private final String                 title;
    private final SourceScript           sourceScript;
    private final ImmutableList<Setting> settings;

    /**
     * Creates a program with the script's default settings and no title.
     */
    public Program(SourceScript sourceScript) throws ScriptCompilationException {

        this("", sourceScript);
    }

    /**
     * Creates a program with the script's default settings and the given title.
     *
     * @param title The title, which can be empty but not {@code null}.
     */
    public Program(String title, SourceScript sourceScript) throws ScriptCompilationException {

        Validate.notNull(title);
        Validate.notNull(sourceScript);

        this.title = title;
        this.sourceScript = sourceScript;
        settings = getCompiledScript().getDefaultSettings();
    }

    /**
     * Creates a program with the given script settings and the given title.
     *
     * @param title The title, which can be empty but not {@code null}.
     */
    public Program(String title, SourceScript sourceScript, List<Setting> settings) {

        Validate.notNull(title);
        Validate.notNull(sourceScript);
        Validate.notNull(settings);

        this.title = title;
        this.sourceScript = sourceScript;
        this.settings = ImmutableList.copyOf(settings);
    }

    public String getTitle() {

        return title;
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
