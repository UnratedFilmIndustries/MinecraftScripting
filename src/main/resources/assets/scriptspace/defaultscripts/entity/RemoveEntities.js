/*
 * Removes all selected entities that match the given criteria.
 */

var settings = [
                new SettingStringList("entityType", "Entity type", [ "All" ].concat(Entity.allLivingEntityNames.slice(0))),
                new SettingString("entityName", "Entity name (optional)")
                ];

function run(player, selection, settings) {
    for each (var entity in selection.entities) {
        // Check for correct entity type
        if (settings.entityType != "All" && entity.internalName != settings.entityType)
            continue;

        // Check for correct entity name
        if (settings.entityName != "" && entity.entityName != settings.entityName)
            continue;

        entity.setDead();
    }
}
