
package de.unratedfilms.scriptspace.common.selection;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.unratedfilms.scriptspace.common.util.ReflectionHelper;
import de.unratedfilms.scriptspace.common.util.Vec3i;

public abstract class Selection {

    /**
     * The ID of the dimension this selection is part of.
     * Since this method only returns an integer, it can be called on the client without any problems.
     */
    public final int dimensionId;

    protected Selection(int dimensionId) {

        this.dimensionId = dimensionId;
    }

    /**
     * Returns the {@link WorldServer server-side world object} this selection is part of.
     * Note that calling this method on the client may lead to errors when the player is in another dimension!
     * Therefore, you should use it carefully on the client side.
     *
     * @return The server-side world that contains this selection.
     * @throws IllegalStateException If this method is called from the client and the player isn't located in the dimension this selection was made in.
     */
    public World getWorld() {

        if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            return DimensionManager.getWorld(dimensionId);
        } else {
            // What follows is some hacky reflection trickery to avoid NoClassDefFoundErrors on the server because it doesn't know what the hell a GuiScreen is ...
            return (World) ReflectionHelper.invokeDeclaredMethod(Selection.class, this, "doGetWorldOnClient", new Class[0]);
        }
    }

    @SideOnly (Side.CLIENT)
    private World doGetWorldOnClient() {

        if (Minecraft.getMinecraft().isSingleplayer()) {
            return DimensionManager.getWorld(dimensionId);
        } else {
            World currentWorld = Minecraft.getMinecraft().theWorld;
            Validate.validState(currentWorld.provider.dimensionId == dimensionId,
                    "You called Selection.getWorld() on the client, and the player is in dimension %d instead of %d", currentWorld.provider.dimensionId, dimensionId);
            return currentWorld;
        }

    }

    /**
     * Returns the center location of the selection.
     * You can use this to get the center of gravity.
     *
     * @return The center of the shape.
     */
    public abstract Vec3 getCenter();

    /**
     * Returns the rounded block center location of the shape.
     * You can use this to get the center of gravity as a block location.
     *
     * @return The center location of the shape as a rounded block location.
     */
    public Vec3i getBlockCenter() {

        // floor rounding
        return new Vec3i(getCenter());
    }

    /**
     * Returns a new AABB ("cuboid shape") the current selection exactly fits into.
     * Note that no spare space is allowed to be left on any side.
     *
     * @return An AABB that surrounds the current shape.
     */
    public abstract AxisAlignedBB getAABB();

    /**
     * Checks whether the given location is inside the shape.
     * The location is represented by three doubles representing the three coordinates.
     * There will only be a positive result if all three coordinates are intersecting the shape.
     *
     * @param x The x-coordinate of the location that should be checked for intersection.
     * @param y The y-coordinate of the location that should be checked for intersection.
     * @param z The z-coordinate of the location that should be checked for intersection.
     * @return Whether the provided location intersects the shape.
     */
    public abstract boolean intersects(double x, double y, double z);

    /**
     * Returns a collection of {@link Vec3 vector}s that are located inside the shape and completely fill in the shape.
     * The distance parameter controls how far away the vectors are from each other.
     * By making the distance smaller, the resolution becomes higher and more vectors are returned.
     * Note that inserting the distance 1 will return one vector for each block inside the shape (at least if you round the coordinates).<br>
     * <br>
     * For example, you could retrieve the block locations inside the shape as follows:
     *
     * <pre>
     * for (Vec3 blockLocation : <i>shape</i>.getContent(1)) {
     *     ...
     * }
     * </pre>
     *
     * @param distance The distance between the returned vectors.
     * @return The vectors inside the shape separated by the given distance.
     */
    public abstract List<Vec3> getLocations(double distance);

    public List<Vec3i> getBlockLocations() {

        List<Vec3i> blockLocations = new ArrayList<>();

        for (Vec3 location : getLocations(1)) {
            blockLocations.add(new Vec3i(location));
        }

        return blockLocations;
    }

    /**
     * Returns all {@link Entity entities} which are inside the selection or can, more generally, be classified as "selected".
     * Note that this method should not be called on the client since the client can't oversee all available worlds.
     *
     * @return All selected entities.
     */
    public abstract List<Entity> getEntities();

    /**
     * Returns all {@link TileEntity tile entities} which are inside the selection or can, more generally, be classified as "selected".
     * Note that this method should not be called on the client since the client can't oversee all available worlds.
     *
     * @return All selected tile entities.
     */
    public abstract List<TileEntity> getTileEntities();

}
