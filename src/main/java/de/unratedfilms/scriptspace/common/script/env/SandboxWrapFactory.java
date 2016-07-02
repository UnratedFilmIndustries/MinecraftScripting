
package de.unratedfilms.scriptspace.common.script.env;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;

/**
 * Simple sandbox helper to return our sandboxed java objects.
 *
 * @see http://codeutopia.net/blog/2009/01/02/sandboxing-rhino-in-java/
 */
class SandboxWrapFactory extends WrapFactory {

    @Override
    @SuppressWarnings ("rawtypes")
    public Scriptable wrapAsJavaObject(Context cx, Scriptable scope, Object javaObject, Class staticType) {

        return new SandboxNativeJavaObject(scope, javaObject, staticType);
    }

    /**
     * Simple sandbox for native java objects.
     * This will prevent JS scripts from using methods such as {@link Object#wait()}.
     *
     * @see ContextFactory
     * @see http://codeutopia.net/blog/2009/01/02/sandboxing-rhino-in-java/
     */
    @SuppressWarnings ("serial")
    private static class SandboxNativeJavaObject extends NativeJavaObject {

        public SandboxNativeJavaObject(Scriptable scope, Object javaObject, Class<?> staticType) {

            super(scope, javaObject, staticType);
        }

        @Override
        public Object get(String name, Scriptable start) {

            if (name.equals("wait") || name.equals("notify") || name.equals("notifyAll")) {
                return NOT_FOUND;
            }

            return super.get(name, start);
        }

    }

}
