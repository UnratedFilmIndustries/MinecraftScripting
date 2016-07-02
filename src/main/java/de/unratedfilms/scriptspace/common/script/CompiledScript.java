
package de.unratedfilms.scriptspace.common.script;

import org.apache.commons.lang3.Validate;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import com.google.common.collect.ImmutableList;
import de.unratedfilms.scriptspace.common.script.api.settings.Setting;

/**
 * A script whose sourcecode has been compiled into a Rhino {@link Script} object.
 * Moreover, this class stores some other information about the script, including the {@link #getSettings() settings array} and the {@link #getRunFunction() run function}.
 * Note that this class also holds a reference to the original {@link SourceScript} object.
 *
 * @see SourceScript
 */
public class CompiledScript implements Comparable<CompiledScript> {

    private final SourceScript     source;

    private final Script           compilation;
    private final Scriptable       scope;

    /* function settings(player, selection) */
    private ImmutableList<Setting> defaultSettings;
    /* function run(player, selection, settings) */
    private Function               runFunction;

    /**
     * @param compilation The compiled {@link Script} object, which must have been executed once!
     */
    public CompiledScript(SourceScript source, Script compilation, Scriptable scope) throws ScriptCompilationException {

        Validate.notNull(source);
        Validate.notNull(compilation);
        Validate.notNull(scope);

        this.source = source;
        this.compilation = compilation;
        this.scope = scope;

        // Gets runFunction and settings and thereby also checks whether the script is valid
        retrieveInformationFromCompilation();
    }

    private void retrieveInformationFromCompilation() throws ScriptCompilationException {

        // Get the settings array
        Object defaultSettingsVariable = scope.get("settings", scope);
        if (defaultSettingsVariable == null || defaultSettingsVariable == Scriptable.NOT_FOUND) {
            defaultSettings = ImmutableList.of();
        } else {
            try {
                defaultSettings = ImmutableList.copyOf((Setting[]) Context.jsToJava(defaultSettingsVariable, Setting[].class));
            } catch (EvaluatorException e) {
                throw new ScriptCompilationException(source, "Unable to read 'settings' variable", e);
            }
        }

        // Get the run function
        Object runFunction = scope.get("run", scope);
        if (runFunction instanceof Function) {
            this.runFunction = (Function) runFunction;
        } else {
            throw new ScriptCompilationException(source, "No 'run' function detected");
        }
    }

    public SourceScript getSource() {

        return source;
    }

    public Script getCompilation() {

        return compilation;
    }

    public Scriptable getScope() {

        return scope;
    }

    public boolean hasSettings() {

        return getDefaultSettings().isEmpty();
    }

    public ImmutableList<Setting> getDefaultSettings() {

        return defaultSettings;
    }

    public Function getRunFunction() {

        return runFunction;
    }

    @Override
    public int compareTo(CompiledScript o) {

        return source.compareTo(o.source);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (source == null ? 0 : source.hashCode());
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
        CompiledScript other = (CompiledScript) obj;
        if (source == null) {
            if (other.source != null) {
                return false;
            }
        } else if (!source.equals(other.source)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "compiled script, based on " + source;
    }

}
