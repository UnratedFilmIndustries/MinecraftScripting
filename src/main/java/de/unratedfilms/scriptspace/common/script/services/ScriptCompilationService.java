
package de.unratedfilms.scriptspace.common.script.services;

import static de.unratedfilms.scriptspace.common.Consts.LOGGER;
import static de.unratedfilms.scriptspace.common.Consts.MOD_ID;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import de.unratedfilms.scriptspace.common.script.CompiledScript;
import de.unratedfilms.scriptspace.common.script.ScriptCompilationException;
import de.unratedfilms.scriptspace.common.script.SourceScript;
import de.unratedfilms.scriptspace.common.script.env.ScriptEnvironmentProvider;
import de.unratedfilms.scriptspace.common.script.env.ScriptEnvironmentProvider.ScriptEnvironmentTask;

public class ScriptCompilationService {

    public static CompiledScript compileScript(final SourceScript sourceScript) throws ScriptCompilationException {

        Object result = ScriptEnvironmentProvider.invokeInScriptEnvironment(new ScriptEnvironmentTask<Object>() {

            @Override
            public Object invoke(Context context, Scriptable globalScope) {

                try {
                    Script compilation = context.compileString(sourceScript.getCode(), sourceScript.getName(), 1, null);
                    Scriptable scope = context.newObject(globalScope);

                    // We execute the script once so that parameters etc. can be later retrieved
                    // This doesn't actually do anything since all of the script's functionality lies in functions
                    compilation.exec(context, scope);

                    try {
                        return new CompiledScript(sourceScript, compilation, scope);
                    } catch (ScriptCompilationException e) {
                        return e;
                    }
                } catch (RhinoException e) {
                    return new ScriptCompilationException(sourceScript, e);
                }
            }

        });

        // If nothing went wrong during compilation, return the CompiledScript object
        if (result instanceof CompiledScript) {
            return (CompiledScript) result;
        }
        // Otherwise, throw the compilation exception
        else {
            throw (ScriptCompilationException) result;
        }
    }

    public static void sendErrorMessagesOnCompilationException(ScriptCompilationException e) {

        LOGGER.error("Error compiling script '{}': {}", e.getSourceScript(), e.getMessage());
    }

    public static void sendErrorMessagesOnCompilationException(ScriptCompilationException e, ICommandSender causer) {

        sendErrorMessagesOnCompilationException(e);

        causer.sendMessage(new TextComponentTranslation("message." + MOD_ID + ".scriptCompilationError", e.getSourceScript().getName()));
        causer.sendMessage(new TextComponentString(e.getMessage()));
    }

    private ScriptCompilationService() {

    }

}
