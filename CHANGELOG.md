2.2.0
-----

### Additions
* Vastly improve the CustomNPCs API extension. Using the new API, scripts are now able to edit Custom NPCs to the finest level of detail.
* Rename the `ScriptIO` class to `ScriptLogger` because that essentially is what the class does.
* Also greatly improve other minor API components here and there.
* Add proper support for "air" item stacks both to the GUI and the internals.
* It is now possible to choose selections by clicking with the selection item. Moreover, as soon as you edit a selection, that selection is automatically chosen.
* The currently chosen selection can be temporarily hidden and shown again with the key that was previously used to choose a selection.

### Fixes
* Fixed the mcmod.info file not containing any version information in the dev JAR.
* Fixed the entire game crashing when the optional CutomNPCs mod wasn't installed.
* Fixed several issues with script execution that could potentially come up at really rare occasions.

2.1.3
-----

### Fixes
* Fixed the fork URL in this very changelog.

2.1.2
-----

### Additions
* Added this very changelog.

2.1.1
-----

### Fixes
* Fixed severe JavaDoc errors which caused the entire build to crash.

2.1.0
-----

### Notes
* This is the first iteration of the mod, so there aren't any changes to mention here. Note, however, that this mod was forked from: https://github.com/DavidGoldman/MinecraftScripting
