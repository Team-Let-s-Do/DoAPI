package de.cristelknight.doapi.client.recipebook;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PrivateRecipeBookGhostSlots {
    private final List<PrivateRecipeBookGhostSlots.PrivateGhostInputSlot> slots = Lists.newArrayList();
    float time;

    public PrivateRecipeBookGhostSlots() {
    }

    public void reset() {
        this.slots.clear();
        this.time = 0.0F;
    }

    public void addSlot(ItemStack itemStack, int x, int y) {
        this.slots.add(new PrivateRecipeBookGhostSlots.PrivateGhostInputSlot(itemStack, x, y));
    }

    public PrivateRecipeBookGhostSlots.PrivateGhostInputSlot getSlot(int index) {
        return this.slots.get(index);

    }

    public int getSlotCount() {
        return this.slots.size();
    }

    public void draw(GuiGraphics guiGraphics, Minecraft client, int i, int j, boolean bl, float f) {
        if (!Screen.hasControlDown()) {
            this.time += f;
        }

        for(int k = 0; k < this.slots.size(); ++k) {
            PrivateRecipeBookGhostSlots.PrivateGhostInputSlot ghostIngredient = this.slots.get(k);
            int l = ghostIngredient.getX() + i;
            int m = ghostIngredient.getY() + j;
            if (k == 0 && bl) {
                guiGraphics.fill(l - 4, m - 4, l + 20, m + 20, 822018048);
            } else {
                guiGraphics.fill(l, m, l + 16, m + 16, 822018048);
            }

            ItemStack itemStack = ghostIngredient.itemStack;
            guiGraphics.renderFakeItem(itemStack, l, m);
            guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), l, m, l + 16, m + 16, 822083583);
            if (k == 0) {
                guiGraphics.renderItemDecorations(client.font, itemStack, l, m);
            }
        }

    }

    @Environment(EnvType.CLIENT)
    public static class PrivateGhostInputSlot {
        private final ItemStack itemStack;
        private final int x;
        private final int y;

        public PrivateGhostInputSlot(ItemStack itemStack, int x, int y) {
            this.itemStack = itemStack;
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public ItemStack getCurrentItemStack() {
            return itemStack;
        }
    }
}
