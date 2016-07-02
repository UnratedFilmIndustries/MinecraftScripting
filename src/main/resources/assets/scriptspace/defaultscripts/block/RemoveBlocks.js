/*
 * Removes all selected blocks.
 */

var settings = [
                new SettingBoolean("removeTileEntities", "Remove tile entity blocks", true)
                ];

function run(player, selection, settings) {
    var world = selection.world;

    for each (var blockLoc in selection.blockLocations) {
        if (world.getTileEntity(blockLoc) == null) {
            world.setBlockToAir(blockLoc);
        } else if (settings.removeTileEntities) {
            world.removeTileEntity(blockLoc);
            world.setBlockToAir(blockLoc);
        }
    }
}
