
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.Arrays;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.core.Viewport;
import de.unratedfilms.guilib.widgets.model.Button;
import de.unratedfilms.guilib.widgets.model.Button.ButtonHandler;
import de.unratedfilms.guilib.widgets.model.ButtonItem;
import de.unratedfilms.guilib.widgets.view.impl.ButtonItemImpl;
import de.unratedfilms.guilib.widgets.view.impl.TextTooltipImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.Setting;
import de.unratedfilms.scriptspace.net.NetworkService;
import de.unratedfilms.scriptspace.net.messages.GiveItemStackServerMessage;

public abstract class SettingWidgetAbstractItemButton<S extends Setting> extends SettingWidget<S> {

    protected final ButtonItem button;

    public SettingWidgetAbstractItemButton(S setting, ItemStack item, ButtonHandler handler) {

        super(setting);

        button = new CustomizedItemButton(item, handler);
        settingContainer.addWidget(button);
    }

    private static class CustomizedItemButton extends ButtonItemImpl {

        public CustomizedItemButton(ItemStack itemStack, ButtonHandler handler) {

            super(itemStack, handler);
        }

        @Override
        public CustomizedItemButton setItemStack(ItemStack itemStack) {

            super.setItemStack(itemStack);
            zLevel = itemStack.getItem() != null && itemStack.hasEffect(0) ? 50 : 0;

            return this;
        }

        @Override
        protected TextTooltipImpl generateTooltip() {

            TextTooltipImpl tooltip = super.generateTooltip();
            tooltip.addLines(Arrays.asList(I18n.format("gui.scriptspace.itemButton.give")));
            return tooltip;
        }

        @Override
        public void drawInLocalContext(Viewport viewport, int lmx, int lmy) {

            super.drawInLocalContext(viewport, lmx, lmy);

            int x = getX();
            int y = getY();
            int width = getWidth();
            int height = getHeight();

            GL11.glDisable(GL11.GL_DEPTH_TEST);
            drawRect(x, y, x + width, y + 1, 0xff000000);
            drawRect(x, y + height - 1, x + width, y + height, 0xff000000);
            drawRect(x, y, x + 1, y + height, 0xff000000);
            drawRect(x + width - 1, y, x + width, y + height, 0xff000000);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }

    }

    protected static class Handler implements ButtonHandler {

        @Override
        public void buttonClicked(Button button, MouseButton mouseButton) {

            if (mouseButton == MouseButton.MIDDLE || mouseButton == MouseButton.RIGHT) {
                NetworkService.DISPATCHER.sendToServer(new GiveItemStackServerMessage( ((ButtonItem) button).getItemStack()));
            }
        }

    }

}
