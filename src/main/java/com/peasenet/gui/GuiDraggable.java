package com.peasenet.gui;

import com.peasenet.util.math.Point;
import net.minecraft.text.Text;

public class GuiDraggable extends GuiClick {

    /**
     * Creates a new GUI menu.
     *
     * @param position
     * @param width    - The width of the gui.
     * @param height   - The height of the gui.
     * @param title    - The title of the gui.
     */
    public GuiDraggable(Point position, int width, int height, Text title) {
        super(position, width, height, title);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        // calculate the offset between the mouse position and the top left corner of the gui
        setPosition(new Point((int) mouseX, (int) mouseY));
        return (mouseX != deltaX && mouseY != deltaY);
    }
}
