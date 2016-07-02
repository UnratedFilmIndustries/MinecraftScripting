
package de.unratedfilms.scriptspace.common.script;

import net.minecraft.entity.player.EntityPlayerMP;
import de.unratedfilms.scriptspace.common.selection.Selection;

@SuppressWarnings ("serial")
public class ProgramExecutionException extends Exception {

    private final Program        program;
    private final EntityPlayerMP causer;
    private final Selection      selection;

    public ProgramExecutionException(Program program, EntityPlayerMP causer, Selection selection) {

        this.program = program;
        this.causer = causer;
        this.selection = selection;
    }

    public ProgramExecutionException(Program program, EntityPlayerMP causer, Selection selection, String message) {

        super(message);

        this.program = program;
        this.causer = causer;
        this.selection = selection;
    }

    public ProgramExecutionException(Program program, EntityPlayerMP causer, Selection selection, Throwable cause) {

        super(cause);

        this.program = program;
        this.causer = causer;
        this.selection = selection;
    }

    public ProgramExecutionException(Program program, EntityPlayerMP causer, Selection selection, String message, Throwable cause) {

        super(message, cause);

        this.program = program;
        this.causer = causer;
        this.selection = selection;
    }

    public Program getProgram() {

        return program;
    }

    public EntityPlayerMP getCauser() {

        return causer;
    }

    public Selection getSelection() {

        return selection;
    }

}
