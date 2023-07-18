package de.cristelknight.doapi.client.recipebook.screen.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.DoApiRL;
import de.cristelknight.doapi.client.recipebook.handler.AbstractPrivateRecipeScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PrivateAnimatedResultButton extends AbstractWidget {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/recipe_book.png");
    private AbstractPrivateRecipeScreenHandler craftingScreenHandler;
    private Recipe<?> recipe;
    private float bounce;

    public PrivateAnimatedResultButton() {
        super(0, 0, 25, 25, CommonComponents.EMPTY);
    }

    public void showResultCollection(Recipe<?> recipe, AbstractPrivateRecipeScreenHandler craftingScreenHandler) {
        this.recipe = recipe;
        this.craftingScreenHandler = craftingScreenHandler;
    }

    public Recipe<?> getRecipe() {
        return this.recipe;
    }

    public void setPos(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        Minecraft minecraftClient = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = 29;
        if (!craftingScreenHandler.hasIngredient(this.recipe)) {
            i += 25;
        }

        int j = 206;

        boolean bl = this.bounce > 0.0F;
        PoseStack poseStack = RenderSystem.getModelViewStack();
        if (bl) {
            float f = 1.0F + 0.1F * (float) Math.sin((this.bounce / 15.0F * 3.1415927F));
            poseStack.pushPose();
            poseStack.translate((this.getX() + 8), (this.getY() + 12), 0.0);
            poseStack.scale(f, f, 1.0F);
            poseStack.translate((-(this.getX() + 8)), (-(this.getY() + 12)), 0.0);
            RenderSystem.applyModelViewMatrix();
            this.bounce -= delta;
        }

        guiGraphics.blit(BACKGROUND_TEXTURE, this.getX(), this.getY(), i, j, this.width, this.height);
        Recipe<?> recipe = this.getResult();
        int k = 4;

        guiGraphics.renderItem(recipe.getResultItem(minecraftClient.level.registryAccess()), this.getX() + k, this.getY() + k);
        if (bl) {
            poseStack.popPose();
            RenderSystem.applyModelViewMatrix();
        }

    }

    private Recipe<?> getResult() {
        return this.recipe;
    }

    public boolean hasResult() {
        return this.getResult() != null;
    }

    public Recipe<?> currentRecipe() {
        return this.getResult();
    }

    @Override
    protected boolean isValidClickButton(int button) {
        return button == 0 || button == 1;
    }

    public List<Component> getTooltipText() {
        ItemStack itemStack = this.getResult().getResultItem(Minecraft.getInstance().level.registryAccess());
        return Lists.newArrayList(Screen.getTooltipFromItem(Minecraft.getInstance(), itemStack));
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
        ItemStack itemStack = this.getResult().getResultItem(Minecraft.getInstance().level.registryAccess());
        builder.add(NarratedElementType.TITLE, Component.translatable("narration.recipe", itemStack.getHoverName()));

        builder.add(NarratedElementType.USAGE, Component.translatable("narration.button.usage.hovered"));
    }


    @Override
    public int getWidth() {
        return 25;
    }
}