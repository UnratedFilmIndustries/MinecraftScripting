
package de.unratedfilms.scriptspace.common.script.api.wrapper.world;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ScriptItem {

    public static ScriptItem forName(String name) {

        Item item = null;
        if (Item.REGISTRY.containsKey(new ResourceLocation(name))) {
            item = Item.REGISTRY.getObject(new ResourceLocation(name));
        } else {
            try {
                item = Item.getItemById(Integer.parseInt(name));
            } catch (NumberFormatException e) {}
        }
        return fromItem(item);
    }

    public static ScriptItem forID(int id) {

        return fromItem(Item.getItemById(id));
    }

    public static ScriptItem fromItem(Item item) {

        return item == null ? null : new ScriptItem(item);
    }

    public final Item item;

    private ScriptItem(Item item) {

        this.item = item;
    }

    public int getItemID() {

        return Item.getIdFromItem(item);
    }

    public String getItemName() {

        return Item.REGISTRY.getNameForObject(item).toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (! (obj instanceof ScriptItem)) {
            return false;
        }
        ScriptItem otherItem = (ScriptItem) obj;
        return otherItem != null && item == otherItem.item;
    }

}
