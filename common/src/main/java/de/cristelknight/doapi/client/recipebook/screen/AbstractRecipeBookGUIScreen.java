package de.cristelknight.doapi.client.recipebook.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import de.cristelknight.doapi.DoApiRL;
import de.cristelknight.doapi.client.recipebook.handler.AbstractPrivateRecipeScreenHandler;
import de.cristelknight.doapi.client.recipebook.screen.widgets.PrivateRecipeBookWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

@Environment(EnvType.CLIENT)
public abstract class AbstractRecipeBookGUIScreen<T extends AbstractPrivateRecipeScreenHandler> extends AbstractContainerScreen<T> {
    private final ResourceLocation BACKGROUND;
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new DoApiRL("textures/gui/recipe_button.png"); // Use own texture to prevent mods to remove our recipe book
    public final PrivateRecipeBookWidget recipeBook;
    private boolean narrow;

    public AbstractRecipeBookGUIScreen(T handler, Inventory inventory, Component title, PrivateRecipeBookWidget recipeBook, ResourceLocation background) {
        super(handler, inventory, title);
        BACKGROUND = background;
        this.recipeBook = recipeBook;
    }

    @Override
    protected void init() {
        super.init();
        this.narrow = this.width < 379;
        this.recipeBook.initialize(this.width, this.height, this.minecraft, this.narrow, this.menu);
        this.leftPos = this.recipeBook.findLeftEdge(this.width, this.imageWidth);
        this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.topPos + 25, 20, 18, 0, 0, 19, getRecipeButtonTexture(), (button) -> {
            this.recipeBook.toggleOpen();
            this.leftPos = this.recipeBook.findLeftEdge(this.width, this.imageWidth);
            button.setPosition(this.leftPos + 5, this.topPos + 25);
        }));
        this.titleLabelX += 20;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        if (this.recipeBook.isOpen() && this.narrow) {
            System.out.println("render");
            this.renderBg(guiGraphics, delta, mouseX, mouseY);
            this.recipeBook.render(guiGraphics, mouseX, mouseY, delta);
        } else {
            this.recipeBook.render(guiGraphics, mouseX, mouseY, delta);
            super.render(guiGraphics, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(guiGraphics, this.leftPos, this.topPos, true, delta);
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.recipeBook.drawTooltip(guiGraphics, this.leftPos, this.topPos, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        final int posX = this.leftPos;
        final int posY = this.topPos;
        guiGraphics.blit(BACKGROUND, posX, posY, 0, 0, this.imageWidth - 1, this.imageHeight);

        renderProgressArrow(guiGraphics);
        renderBurnIcon(guiGraphics, posX, posY);
    }

    protected void renderProgressArrow(GuiGraphics guiGraphics) {
    }

    protected void renderBurnIcon(GuiGraphics guiGraphics, int posX, int posY) {
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.recipeBook.update();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return this.narrow && this.recipeBook.isOpen() || super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int button, ClickType actionType) {
        super.slotClicked(slot, slotId, button, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return !this.recipeBook.keyPressed(keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double) left || mouseY < (double) top || mouseX >= (double) (left + this.imageWidth) || mouseY >= (double) (top + this.imageHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight) && bl;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return this.recipeBook.charTyped(chr, modifiers) || super.charTyped(chr, modifiers);
    }

    @Override
    public void removed() {
        super.removed();
    }

    public ResourceLocation getRecipeButtonTexture() {
        return RECIPE_BUTTON_TEXTURE;
    }
}
