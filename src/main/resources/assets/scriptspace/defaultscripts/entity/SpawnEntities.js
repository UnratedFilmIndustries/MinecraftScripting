/*
 * Spawns an entity at each occurrence of a specified block, shifted from that block by a specified offset.
 * For example, using the offsets x=0, y=1, z=0 would spawn an entity on top of each occurrence of the specified block.
 * Note that entities are only allowed to spawn in the selection. However, if the offset requires it, the found blocks can also be located outside the selection.
 */

var settings = [
                new SettingStringList("entityType", "Spawn entities of this type", Entity.allLivingEntityNames),
                new SettingBlock("block", "At this block", Block.forName("grass")),
                new SettingInt("offsetX", "With this X offset from the found block", 0),
                new SettingInt("offsetY", "With this Y offset from the found block", 1),
                new SettingInt("offsetZ", "With this Z offset from the found block", 0),
                new SettingFloat("percentage", "Only spawn this % of possible spawns", 100, 0, 100)
                ];

function run(player, selection, settings) {
    var world = selection.world;

    for each (var entityLoc in selection.blockLocations) {
        var blockLoc = new Vec3(entityLoc.x - settings.offsetX, entityLoc.y - settings.offsetY, entityLoc.z - settings.offsetZ);

        // Check whether the block is of the specified type
        if (!world.getBlock(blockLoc).equals(settings.block))
            continue;

        // If the percentage is relevant (< 100), do a quick dice roll for the probability
        if (settings.percentage < 100 && ! (Rand.randomFloat() * 100 < settings.percentage))
            continue;

        // Create the entity
        var entity = Entity.createEntityByName(settings.entityType, world);
        entity.setLocation(entityLoc.x + 0.5, entityLoc.y + 0.5, entityLoc.z + 0.5);

        // Finally spawn the entity
        world.addEntity(entity);
    }
}
