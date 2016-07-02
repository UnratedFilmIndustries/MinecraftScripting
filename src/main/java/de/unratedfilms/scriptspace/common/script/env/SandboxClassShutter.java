
package de.unratedfilms.scriptspace.common.script.env;

import org.mozilla.javascript.ClassShutter;
import com.google.common.collect.ImmutableList;
import de.unratedfilms.scriptspace.common.Consts;

class SandboxClassShutter implements ClassShutter {

    private static final ImmutableList<String> PACKAGE_WHITELIST;
    private static final ImmutableList<String> CLASS_WHITELIST;

    static {

        // Some classes in java.lang are quite scary, so no :O
        PACKAGE_WHITELIST = ImmutableList.<String> builder()
                .add(Consts.ROOT_PACKAGE + ".common.script.api")
                .add("java.util")
                .build();

        CLASS_WHITELIST = ImmutableList.<String> builder()
                .add("java.lang.Object")
                .add("java.lang.String")
                .add("java.lang.Boolean")
                .add("java.lang.Float")
                .add("java.lang.Double")
                .add("java.lang.Integer")
                .add("java.lang.Byte")
                .add("java.lang.Short")
                .add("java.lang.Long")
                .add("java.lang.Class")
                .build();

    }

    @Override
    public boolean visibleToScripts(String fullClassName) {

        if (CLASS_WHITELIST.contains(fullClassName)) {
            return true;
        }

        for (String s : PACKAGE_WHITELIST) {
            if (fullClassName.startsWith(s)) {
                return true;
            }
        }

        return false;
    }

}
