
package de.unratedfilms.scriptspace.client.render;

import org.lwjgl.opengl.GL11;

class RenderSettings {

    public static final RenderSetting[] SEL_BOX              = new RenderSetting[] {
                                                             new RenderSetting(1f, 0f, 0f, .7f, GL11.GL_LEQUAL),
                                                             new RenderSetting(1f, 0f, 0f, .2f, GL11.GL_GREATER)
                                                             };

    public static final RenderSetting[] SEL_CORNER_1         = new RenderSetting[] {
                                                             new RenderSetting(0f, 1f, 0f, .7f, GL11.GL_LEQUAL),
                                                             new RenderSetting(0f, 1f, 0f, .2f, GL11.GL_GREATER)
                                                             };

    public static final RenderSetting[] SEL_CORNER_2         = new RenderSetting[] {
                                                             new RenderSetting(0f, 0f, 1f, .7f, GL11.GL_LEQUAL),
                                                             new RenderSetting(0f, 0f, 1f, .2f, GL11.GL_GREATER)
                                                             };

    public static final float           SEL_CORNER_EXPANSION = .010f;

    private RenderSettings() {

    }

}
