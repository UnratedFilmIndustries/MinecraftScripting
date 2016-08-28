
package de.unratedfilms.scriptspace.client.gui;

import net.minecraft.client.gui.GuiScreen;
import de.unratedfilms.guilib.core.Widget;
import de.unratedfilms.guilib.integration.Container;
import de.unratedfilms.guilib.widgets.model.Scrollbar;
import de.unratedfilms.guilib.widgets.view.impl.ScrollbarImpl;

/**
 * A screen which simply implements a scrollable container with something above and something below it.
 * All widgets inside the container are rendered on top of each other.
 * It is not possible to render two widgets beside each other.
 */
public abstract class SimpleScrollableContainerScreen extends CustomScreen {

    private static final int SCROLLABLE_CONTAINER_H_PADDING        = 10; // The horizontal padding between the edge of the container and the widgets
    private static final int SCROLLABLE_CONTAINER_V_PADDING        = 10; // The vertical padding between the edge of the container and the widgets

    private static final int SCROLLABLE_CONTAINER_WIDGET_V_PADDING = 5;  // The vertical padding between to widgets inside the container

    private static final int SCROLLBAR_WIDTH                       = 10;

    protected final int      scrollableContainerMarginLeft;
    protected final int      scrollableContainerMarginRight;
    protected final int      scrollableContainerMarginTop;
    protected final int      scrollableContainerMarginBottom;

    protected Container      mainContainer;

    protected Container      scrollableContainer;
    protected Scrollbar      scrollbar;

    protected SimpleScrollableContainerScreen(GuiScreen parent, int scrollableContainerMarginLeft, int scrollableContainerMarginRight, int scrollableContainerMarginTop, int scrollableContainerMarginBottom) {

        super(parent);

        this.scrollableContainerMarginLeft = scrollableContainerMarginLeft;
        this.scrollableContainerMarginRight = scrollableContainerMarginRight;
        this.scrollableContainerMarginTop = scrollableContainerMarginTop;
        this.scrollableContainerMarginBottom = scrollableContainerMarginBottom;
    }

    @Override
    protected void createGui() {

        mainContainer = new Container();

        scrollbar = new ScrollbarImpl(SCROLLBAR_WIDTH);
        scrollableContainer = new Container(scrollbar, 10, 2 * SCROLLABLE_CONTAINER_V_PADDING);

        containers.add(scrollableContainer);
        containers.add(mainContainer);
        selectedContainer = scrollableContainer;
    }

    @Override
    protected void revalidateGui() {

        mainContainer.revalidate(0, 0, width, height);

        int scLeft = scrollableContainerMarginLeft;
        int scRight = width - scrollableContainerMarginRight - SCROLLBAR_WIDTH;
        int scTop = scrollableContainerMarginTop;
        int scBottom = height - scrollableContainerMarginBottom;
        int scWidth = scRight - scLeft;
        int scHeight = scBottom - scTop;

        int y = scTop + SCROLLABLE_CONTAINER_V_PADDING;
        for (Widget widget : scrollableContainer.getWidgets()) {
            widget.setPosition(scLeft + SCROLLABLE_CONTAINER_H_PADDING, y);
            y += widget.getHeight() + SCROLLABLE_CONTAINER_WIDGET_V_PADDING;
        }

        scrollbar.setPosition(scRight, scTop);
        scrollableContainer.revalidate(scLeft, scTop, scWidth, scHeight);
    }

    @Override
    public void drawBackground() {

        drawRect(0, 0, width, height, 0x80101010);
        drawRect(scrollableContainer.left(), scrollableContainer.top(), scrollableContainer.right(), scrollableContainer.bottom(), 0x80101010);
    }

}
