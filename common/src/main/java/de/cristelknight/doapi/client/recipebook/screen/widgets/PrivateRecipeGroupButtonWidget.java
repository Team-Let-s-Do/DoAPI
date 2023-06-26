package de.cristelknight.doapi.client.recipebook.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.client.recipebook.IRecipeBookGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PrivateRecipeGroupButtonWidget extends StateSwitchingButton {
    private final IRecipeBookGroup group;
    private float bounce;

    public PrivateRecipeGroupButtonWidget(IRecipeBookGroup group) {
        super(0, 0, 35, 27, false);
        this.group = group;
        this.initTextureValues(153, 2, 35, 0, PrivateRecipeBookWidget.TEXTURE);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        PoseStack poseStack = guiGraphics.pose();
        if (this.bounce > 0.0F) {
            float f = 1.0F + 0.1F * (float) Math.sin((this.bounce / 15.0F * 3.1415927F));
            poseStack.pushPose();
            poseStack.translate((this.getX() + 8), (this.getY() + 12), 0.0);
            poseStack.scale(1.0F, f, 1.0F);
            poseStack.translate((-(this.getX() + 8)), (-(this.getY() + 12)), 0.0);
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        RenderSystem.disableDepthTest();
        int i = this.xTexStart;
        int j = this.yTexStart;
        if (this.isStateTriggered) {
            i += this.xDiffTex;
        }

        if (this.isHovered()) {
            j += this.yDiffTex;
        }

        int k = this.getX();
        if (this.isStateTriggered) {
            k -= 2;
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(this.resourceLocation, k, this.getY(), i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcons(guiGraphics);
        if (this.bounce > 0.0F) {
            poseStack.popPose();
            this.bounce -= delta;
        }
    }

    private void renderIcons(GuiGraphics guiGraphics) {
        List<ItemStack> list = this.group.getIcons();
        int i = this.isStateTriggered ? -2 : 0;
        if (list.size() == 1) {
            guiGraphics.renderItem(list.get(0), this.getX() + 9 + i, this.getY() + 5);
        } else if (list.size() == 2) {
            guiGraphics.renderItem(list.get(0), this.getX() + 3 + i, this.getY() + 5);
            guiGraphics.renderItem(list.get(1), this.getX() + 14 + i, this.getY() + 5);
        }

    }

    public IRecipeBookGroup getGroup() {
        return this.group;
    }
}