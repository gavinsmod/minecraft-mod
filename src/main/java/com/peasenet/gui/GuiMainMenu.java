package com.peasenet.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peasenet.main.GavinsMod;
import com.peasenet.mods.Type;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import static com.peasenet.main.GavinsMod.VERSION;

/**
 * @author gt3ch1
 * @version 5/19/2022
 */
public class GuiMainMenu extends Screen {

    private final Gui gui;

    public GuiMainMenu(GuiClick gui) {
        super(Text.literal("Gavin's Mod " + VERSION));
        this.gui = gui;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        assert this.client != null;
        var tr = this.client.textRenderer;
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.enableBlend();
        gui.render(matrixStack, tr);

        super.render(matrixStack, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        assert gui != null;
        gui.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        assert gui != null;
        gui.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        GavinsMod.setEnabled(Type.MOD_GUI, false);
        super.close();
    }
}
