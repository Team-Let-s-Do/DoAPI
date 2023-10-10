package de.cristelknight.doapi.client.recipebook.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import de.cristelknight.doapi.client.recipebook.IRecipeBookGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PrivateRecipeGroupButtonWidget extends StateSwitchingButton {
    private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("recipe_book/tab"), new ResourceLocation("recipe_book/tab_selected"));
    private final IRecipeBookGroup group;
    private float bounce;

    public PrivateRecipeGroupButtonWidget(IRecipeBookGroup group) {
        super(0, 0, 35, 27, false);
        this.group = group;
        this.initTextureValues(SPRITES);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.sprites == null) return;
        if (this.bounce > 0.0F) {
            float g = 1.0F + 0.1F * (float)Math.sin(this.bounce / 15.0F * 3.1415927F);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((float)(this.getX() + 8), (float)(this.getY() + 12), 0.0F);
            guiGraphics.pose().scale(1.0F, g, 1.0F);
            guiGraphics.pose().translate((float)(-(this.getX() + 8)), (float)(-(this.getY() + 12)), 0.0F);
        }

        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.disableDepthTest();
        ResourceLocation resourceLocation = this.sprites.get(true, this.isStateTriggered);
        int k = this.getX();
        if (this.isStateTriggered) {
            k -= 2;
        }

        guiGraphics.blitSprite(resourceLocation, k, this.getY(), this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcons(guiGraphics);
        if (this.bounce > 0.0F) {
            guiGraphics.pose().popPose();
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