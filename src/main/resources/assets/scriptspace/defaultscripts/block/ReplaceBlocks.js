/*
 * Finds and replaces blocks within the selection.
 */

var settings = [
                new SettingBoolean("ignoreBlockData", "Find all subtypes", false),
                new SettingBlock("oldBlock", "Find"),
                new SettingBlock("newBlock", "Replace with")
                ];

function run(player, selection, settings) {
    var world = selection.world;

    if (settings.oldBlock == settings.newBlock && !settings.ignoreBlockData) 
        return;

    for each (var blockLoc in selection.blockLocations) {
        if (settings.oldBlock.equalsIgnoreData(world.getBlock(blockLoc)) && (settings.ignoreBlockData || world.getBlockData(blockLoc) == settings.oldBlock.data)) {
            world.removeTileEntity(blockLoc);
            world.setBlock(blockLoc, settings.newBlock);
        }
    }
}
