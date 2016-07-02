/*
 * Removes all selected entities.
 */

function run(player, selection, settings) {
    for each (var entity in selection.entities) {
        entity.setDead();
    }
}
