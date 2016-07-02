
package de.unratedfilms.scriptspace.common.script.services;

import static de.unratedfilms.scriptspace.common.Consts.LOGGER;
import static de.unratedfilms.scriptspace.common.Consts.MOD_ID;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;
import de.unratedfilms.scriptspace.common.script.CompiledScript;
import de.unratedfilms.scriptspace.common.script.Program;
import de.unratedfilms.scriptspace.common.script.ProgramExecutionException;
import de.unratedfilms.scriptspace.common.script.ScriptCompilationException;
import de.unratedfilms.scriptspace.common.script.api.settings.Setting;
import de.unratedfilms.scriptspace.common.script.api.wrapper.ScriptSelection;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityPlayer;
import de.unratedfilms.scriptspace.common.script.env.ScriptEnvironmentProvider;
import de.unratedfilms.scriptspace.common.script.env.ScriptEnvironmentProvider.ScriptEnvironmentTask;
import de.unratedfilms.scriptspace.common.selection.Selection;

public class ProgramExecutionService {

    public static void executeProgram(final Program program, final EntityPlayerMP player, final Selection selection) throws ScriptCompilationException, ProgramExecutionException {

        // Compile the script
        final CompiledScript script = program.getCompiledScript();

        // Actually run the script
        ProgramExecutionException executionException = ScriptEnvironmentProvider.invokeInScriptEnvironment(new ScriptEnvironmentTask<ProgramExecutionException>() {

            @Override
            public ProgramExecutionException invoke(Context context, Scriptable globalScope) {

                try {
                    // Construct the settings object which holds all setting values
                    Scriptable settingsObject = context.newObject(globalScope);
                    for (Setting setting : program.getSettings()) {
                        settingsObject.put(setting.name, settingsObject, setting.getValue());
                    }

                    // Construct the argument array
                    Object[] arguments = new Object[] { new ScriptEntityPlayer(player), new ScriptSelection(selection), settingsObject };

                    // Call the script's method "run(player, selection, options)"
                    script.getRunFunction().call(context, script.getScope(), script.getScope(), arguments);

                    // Everything went fine, we don't need to throw an exception
                    return null;
                } catch (RhinoException e) {
                    return new ProgramExecutionException(program, player, selection, e);
                }
            }

        });

        // If something went wrong during execution, throw the execution exception
        if (executionException != null) {
            throw executionException;
        }
    }

    public static void sendErrorMessagesOnExecutionException(ProgramExecutionException e) {

        LOGGER.error("Error executing script '{}': {}", e.getProgram(), e.getMessage());

        e.getCauser().addChatMessage(new ChatComponentTranslation("message." + MOD_ID + ".programExecutionError", e.getProgram().getSourceScript().getName()));
        e.getCauser().addChatMessage(new ChatComponentText(e.getMessage()));
    }

    private ProgramExecutionService() {

    }

}
