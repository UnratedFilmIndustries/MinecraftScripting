ScriptSpace
===========

ScriptSpace is a Minecraft mod that allows to run filter scripts which can directly modify a previously selected portion of the world. Think of them as a map-making tool -- they can modify blocks, entities, and tile entities.

* Users can run scripts which can directly modify a previously selected portion of the Minecraft world. Some filters are included with the mod by default.
* By default, custom scripts are loaded from the `/mods/scriptspace/scripts` folder, which can be found in the user's Minecraft directory.
* To see what functions are available to the scripts, check out all of the classes inside the `common.script.api` package. Note that some of the class names may be abbreviated to the scripts; check out `ContextConfigurator.java` in the `common.script.env` package to see which classes have shortened names.

In order to use this mod, you first need to install GuiLib as a dependency!

Moreover, the mod provides easy scripting wrappers for the following mods (if they are installed):

* [Custom NPCs](http://www.kodevelopment.nl/minecraft/customnpcs)

License
-------

This project has been forked from: https://github.com/DavidGoldman/MinecraftScripting

It may be used under the terms of the GNU General Public License (GPL) v3.0. See the LICENSE.md file or https://www.gnu.org/licenses/gpl-3.0.txt for details.
