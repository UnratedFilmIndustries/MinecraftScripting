
package de.unratedfilms.scriptspace.client.gui;

import net.minecraft.client.gui.GuiScreen;
import de.unratedfilms.guilib.core.Axis;
import de.unratedfilms.guilib.integration.BasicScreen;
import de.unratedfilms.guilib.layouts.AlignLayout;
import de.unratedfilms.guilib.layouts.FlowLayout;
import de.unratedfilms.guilib.widgets.model.ContainerFlexible;
import de.unratedfilms.guilib.widgets.model.Scrollbar;
import de.unratedfilms.guilib.widgets.view.impl.ContainerClippingImpl;
import de.unratedfilms.guilib.widgets.view.impl.ContainerScrollableImpl;
import de.unratedfilms.guilib.widgets.view.impl.ScrollbarImpl;

/**
 * A screen which simply implements a scrollable container with something above and something below it.
 * All widgets inside the container are rendered on top of each other.
 * It is not possible to render two widgets beside each other.
 */
public abstract class SimpleScrollableContainerScreen extends BasicScreen {

    private static final int    SCROLLABLE_CONTAINER_H_PADDING        = 10; // The horizontal padding between the edge of the container and the widgets
    private static final int    SCROLLABLE_CONTAINER_V_PADDING        = 10; // The vertical padding between the edge of the container and the widgets

    private static final int    SCROLLABLE_CONTAINER_WIDGET_V_PADDING = 5;  // The vertical padding between to widgets inside the container

    protected final int         scrollableContainerMarginLeft;
    protected final int         scrollableContainerMarginRight;
    protected final int         scrollableContainerMarginTop;
    protected final int         scrollableContainerMarginBottom;

    protected ContainerFlexible mainContainer;

    protected ContainerFlexible scrollableContainer;
    protected Scrollbar         scrollbar;

    protected SimpleScrollableContainerScreen(GuiScreen parent, int scrollableContainerMarginLeft, int scrollableContainerMarginRight, int scrollableContainerMarginTop, int scrollableContainerMarginBottom) {

        super(parent);

        this.scrollableContainerMarginLeft = scrollableContainerMarginLeft;
        this.scrollableContainerMarginRight = scrollableContainerMarginRight;
        this.scrollableContainerMarginTop = scrollableContainerMarginTop;
        this.scrollableContainerMarginBottom = scrollableContainerMarginBottom;
    }

    @Override
    protected void createGui() {

        mainContainer = new ContainerClippingImpl();
        setRootWidget(mainContainer);

        scrollbar = new ScrollbarImpl(2 * SCROLLABLE_CONTAINER_V_PADDING);
        scrollableContainer = new ContainerScrollableImpl(scrollbar, 10);
        mainContainer.addWidgets(scrollableContainer);

        // ----- Revalidation -----

        mainContainer
                .appendLayoutManager(c -> {
                    int scLeft = scrollableContainerMarginLeft;
                    int scRight = mainContainer.getWidth() - scrollableContainerMarginRight;
                    int scTop = scrollableContainerMarginTop;
                    int scBottom = mainContainer.getHeight() - scrollableContainerMarginBottom;
                    int scWidth = scRight - scLeft;
                    int scHeight = scBottom - scTop;
                    scrollableContainer.setBounds(scLeft, scTop, scWidth, scHeight);
                });

        scrollableContainer
                .appendLayoutManager(c -> {
                    scrollbar.setPosition(scrollableContainer.getWidth() - scrollbar.getWidth(), 0);
                })
                .appendLayoutManager(new AlignLayout(Axis.X, SCROLLABLE_CONTAINER_H_PADDING))
                .appendLayoutManager(new FlowLayout(Axis.Y, SCROLLABLE_CONTAINER_V_PADDING, SCROLLABLE_CONTAINER_WIDGET_V_PADDING));
    }

    @Override
    public void drawBackground() {

        drawRect(0, 0, mainContainer.getWidth(), mainContainer.getHeight(), 0x80101010);
        drawRect(scrollableContainer.getX(), scrollableContainer.getY(), scrollableContainer.getX() + scrollableContainer.getWidth(), scrollableContainer.getY() + scrollableContainer.getHeight(), 0x80101010);
    }

}
