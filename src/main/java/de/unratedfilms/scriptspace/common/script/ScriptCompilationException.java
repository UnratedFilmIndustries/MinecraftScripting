
package de.unratedfilms.scriptspace.common.script;

@SuppressWarnings ("serial")
public class ScriptCompilationException extends Exception {

    private final SourceScript sourceScript;

    public ScriptCompilationException(SourceScript sourceScript) {

        this.sourceScript = sourceScript;
    }

    public ScriptCompilationException(SourceScript sourceScript, String message) {

        super(message);

        this.sourceScript = sourceScript;
    }

    public ScriptCompilationException(SourceScript sourceScript, Throwable cause) {

        super(cause);

        this.sourceScript = sourceScript;
    }

    public ScriptCompilationException(SourceScript sourceScript, String message, Throwable cause) {

        super(message, cause);

        this.sourceScript = sourceScript;
    }

    public SourceScript getSourceScript() {

        return sourceScript;
    }

}
