
package de.unratedfilms.scriptspace.client.render;

import static de.unratedfilms.scriptspace.client.render.RenderSettings.SEL_CORNER_EXPANSION;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import de.unratedfilms.scriptspace.client.selection.SelectionStorage;
import de.unratedfilms.scriptspace.common.items.CustomItems;
import de.unratedfilms.scriptspace.common.items.ItemSelection;
import de.unratedfilms.scriptspace.common.selection.Selection;
import de.unratedfilms.scriptspace.common.selection.SelectionBlock;
import de.unratedfilms.scriptspace.common.selection.SelectionCuboid;

public class SelectionRenderer {

    private static final int            UPDATE_PERIOD      = 5;                // ticks between two updates of the rendered selections

    private int                         updateCountdown;

    private final List<CachedSelection> renderedSelections = new ArrayList<>();

    @SubscribeEvent
    public void tick(ClientTickEvent event) {

        if (event.phase == Phase.START && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            updateCountdown--;

            if (updateCountdown <= 0) {
                updateRenderedSelections();
                updateCountdown = UPDATE_PERIOD;
            }
        }
    }

    private void updateRenderedSelections() {

        renderedSelections.clear();

        // If there's a currently chosen selection and the render setting is switched on, render it
        if (SelectionStorage.chosenSelection != null && SelectionStorage.renderChosenSelection) {
            proposeSelectionForRendering(SelectionStorage.chosenSelection);
        }

        // If there's a currently held selection, render it
        ItemStack heldStack = Minecraft.getMinecraft().player.inventory.getCurrentItem();
        if (heldStack != null && heldStack.getItem() == CustomItems.SELECTION) {
            Selection heldSelection = ItemSelection.getSelection(heldStack);
            proposeSelectionForRendering(heldSelection);
        }
    }

    private void proposeSelectionForRendering(Selection selection) {

        if (selection != null && Minecraft.getMinecraft().player.dimension == selection.dimensionId) {
            renderedSelections.add(new CachedSelection(selection));
        }
    }

    @SubscribeEvent
    public void renderWorldLast(RenderWorldLastEvent event) {

        for (CachedSelection selection : renderedSelections) {
            // Mark all selected entities and tile entities
            for (Entity selectedEntity : selection.entities) {
                drawBoundingBox(event.getContext(), event.getPartialTicks(), selection, selectedEntity, RenderSettings.SEL_BOX);
            }
            for (TileEntity selectedTileEntity : selection.tileEntities) {
                drawBoundingBox(event.getContext(), event.getPartialTicks(), selection, selectedTileEntity, RenderSettings.SEL_BOX);
            }

            // If the selection is a single block or a cuboid, draw an additional bounding box to represent that selection
            if (selection.original instanceof SelectionBlock) {
                // Draw the block location
                drawBoundingBox(event.getContext(), event.getPartialTicks(), selection.aabb, RenderSettings.SEL_CORNER_1);
            } else if (selection.original instanceof SelectionCuboid) {
                SelectionCuboid cuboid = (SelectionCuboid) selection.original;

                // Draw the whole bounding box
                drawBoundingBox(event.getContext(), event.getPartialTicks(), selection.aabb, RenderSettings.SEL_BOX);

                // Draw the two corners
                drawBoundingBox(event.getContext(), event.getPartialTicks(), new AxisAlignedBB(cuboid.corner1).expand(SEL_CORNER_EXPANSION, SEL_CORNER_EXPANSION, SEL_CORNER_EXPANSION), RenderSettings.SEL_CORNER_1);
                drawBoundingBox(event.getContext(), event.getPartialTicks(), new AxisAlignedBB(cuboid.corner2).expand(SEL_CORNER_EXPANSION, SEL_CORNER_EXPANSION, SEL_CORNER_EXPANSION), RenderSettings.SEL_CORNER_2);
            }
        }
    }

    private void drawBoundingBox(RenderGlobal r, float f, AxisAlignedBB aabb, RenderSetting[] settings) {

        EntityPlayer player = Minecraft.getMinecraft().player;
        double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * f;
        double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * f;
        double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * f;
        aabb = aabb.offset(-playerX, -playerY, -playerZ);
        drawOutline(r, f, aabb, settings);
    }

    private void drawBoundingBox(RenderGlobal r, float f, CachedSelection selection, Entity entity, RenderSetting[] settings) {

        if (entity.isEntityAlive() && entity.dimension == selection.original.dimensionId && entity.getEntityBoundingBox() != null) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * f;
            double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * f;
            double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * f;
            drawOutline(r, f, entity.getEntityBoundingBox().offset(-playerX, -playerY, -playerZ), settings);
        }
    }

    private void drawBoundingBox(RenderGlobal r, float f, CachedSelection selection, TileEntity tile, RenderSetting[] settings) {

        if (tile.getWorld().provider.getDimension() == selection.original.dimensionId) {
            Block block = tile.getWorld().getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
            if (block != null) {
                block.setBlockBoundsBasedOnState(tile.getWorld(), tile.xCoord, tile.yCoord, tile.zCoord);
                AxisAlignedBB aabb = block.getSelectedBoundingBoxFromPool(tile.getWorld(), tile.xCoord, tile.yCoord, tile.zCoord);

                EntityPlayer player = Minecraft.getMinecraft().player;
                double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * f;
                double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * f;
                double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * f;
                drawOutline(r, f, aabb.offset(-playerX, -playerY, -playerZ), settings);
            }
        }
    }

    private void drawOutline(RenderGlobal r, float partial, AxisAlignedBB aabb, RenderSetting[] settings) {

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int originalDepth = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
        Tessellator tes = Tessellator.getInstance();

        for (RenderSetting s : settings) {
            GL11.glColor4f(s.r, s.g, s.b, s.a);
            GL11.glDepthFunc(s.depthFunc);

            tes.startDrawing(3);
            tes.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            tes.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            tes.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            tes.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            tes.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            tes.draw();
            tes.startDrawing(3);
            tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            tes.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            tes.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            tes.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            tes.draw();
            tes.startDrawing(1);
            tes.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            tes.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            tes.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            tes.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            tes.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            tes.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            tes.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            tes.draw();
        }

        GL11.glDepthFunc(originalDepth);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    private static class CachedSelection {

        private final Selection        original;
        private AxisAlignedBB          aabb;
        private final List<Entity>     entities;
        private final List<TileEntity> tileEntities;

        private CachedSelection(Selection original) {

            this.original = original;

            if (original instanceof SelectionBlock || original instanceof SelectionCuboid) {
                aabb = original.getAABB();
            }

            entities = original.getEntities();
            tileEntities = original.getTileEntities();
        }

    }

}
