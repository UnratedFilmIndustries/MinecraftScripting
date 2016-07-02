
package de.unratedfilms.scriptspace.common.script.api.wrapper.world;

import net.minecraft.item.Item;

public class ScriptItem {

    public static ScriptItem forName(String name) {

        Item item = null;
        if (Item.itemRegistry.containsKey(name)) {
            item = (Item) Item.itemRegistry.getObject(name);
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

        return Item.itemRegistry.getNameForObject(item);
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
