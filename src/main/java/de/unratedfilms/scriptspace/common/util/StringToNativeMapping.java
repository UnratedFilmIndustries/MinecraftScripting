
package de.unratedfilms.scriptspace.common.util;

import java.util.ArrayList;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class StringToNativeMapping<N> {

    public static <N> StringToNativeMapping<N> withDefault(N defaultNative) {

        return new StringToNativeMapping<>(defaultNative);
    }

    private final BiMap<String, N> mapping = HashBiMap.create();
    private final N                defaultNative;

    private StringToNativeMapping(N defaultNative) {

        this.defaultNative = defaultNative;
    }

    public StringToNativeMapping<N> put(String string, N nativee) {

        mapping.put(string, nativee);
        return this;
    }

    public String[] getAll() {

        return new ArrayList<>(mapping.keySet()).toArray(new String[0]);
    }

    public String fromNative(N nativee) {

        return mapping.containsValue(nativee) ? mapping.inverse().get(nativee) : fromNative(defaultNative);
    }

    public N toNative(String string) {

        return mapping.containsKey(string) ? mapping.get(string) : defaultNative;
    }

}
