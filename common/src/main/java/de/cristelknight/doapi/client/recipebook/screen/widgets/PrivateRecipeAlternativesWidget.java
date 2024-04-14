package de.cristelknight.doapi.client.recipebook.screen.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.config.builtin.RBConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PrivateRecipeAlternativesWidget implements Renderable, GuiEventListener {
    static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/recipe_book.png");
    private final List<CustomAlternativeButtonWidget> alternativeButtons = Lists.newArrayList();
    private boolean visible;
    private int buttonX;
    private int buttonY;
    private Recipe<?> recipe;
    @Nullable
    private Recipe<?> lastClickedRecipe;
    float time;

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public Recipe<?> getResults() {
        return this.recipe;
    }

    @Nullable
    public Recipe<?> getLastClickedRecipe() {
        return this.lastClickedRecipe;
    }

    public void showAlternativesForResult(Recipe<?> recipe, int buttonX, int buttonY, int areaCenterX, int areaCenterY, float delta) {
        this.recipe = recipe;

        boolean bl = RBConfig.DEFAULT.getConfig().craftableToggle();

        int k = 4;
        int l = (int) Math.ceil(((float) 1 / (float) k));

        this.buttonX = buttonX;
        this.buttonY = buttonY;

        float f = (float) (this.buttonX + 25);
        float g = (float) (areaCenterX + 50);
        if (f > g) {
            this.buttonX = (int) ((float) this.buttonX - delta * (float) ((int) ((f - g) / delta)));
        }

        float h = (float) (this.buttonY + l * 25);
        float n = (float) (areaCenterY + 50);
        if (h > n) {
            this.buttonY = (int) ((float) this.buttonY - delta * (float) Mth.ceil((h - n) / delta));
        }

        float o = (float) this.buttonY;
        float p = (float) (areaCenterY - 100);
        if (o < p) {
            this.buttonY = (int) ((float) this.buttonY - delta * (float) Mth.ceil((o - p) / delta));
        }

        this.visible = true;
        this.alternativeButtons.clear();

        this.alternativeButtons.add(new CustomAlternativeButtonWidget(this.buttonX + 6, this.buttonY + 6, this.recipe, bl));

        this.lastClickedRecipe = null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return false;
        } else {
            Iterator<CustomAlternativeButtonWidget> var6 = this.alternativeButtons.iterator();

            CustomAlternativeButtonWidget alternativeButtonWidget;
            do {
                if (!var6.hasNext()) {
                    return false;
                }

                alternativeButtonWidget = var6.next();
            } while (!alternativeButtonWidget.mouseClicked(mouseX, mouseY, button));

            this.lastClickedRecipe = alternativeButtonWidget.recipe;
            return true;
        }
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            this.time += delta;
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(0.0, 0.0, 170.0);
            int i = this.alternativeButtons.size() <= 16 ? 4 : 5;
            int j = Math.min(this.alternativeButtons.size(), i);
            int k = Mth.ceil((float) this.alternativeButtons.size() / (float) i);
            this.renderGrid(guiGraphics, j, k, 24, 4, 82, 208);
            RenderSystem.disableBlend();

            for (CustomAlternativeButtonWidget alternativeButtonWidget : this.alternativeButtons) {
                alternativeButtonWidget.render(guiGraphics, mouseX, mouseY, delta);
            }

            poseStack.popPose();
        }
    }

    private void renderGrid(GuiGraphics guiGraphics, int i, int j, int k, int l, int m, int n) {
        guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX, this.buttonY, m, n, l, l);
        guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l * 2 + i * k, this.buttonY, m + k + l, n, l, l);
        guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX, this.buttonY + l * 2 + j * k, m, n + k + l, l, l);
        guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l * 2 + i * k, this.buttonY + l * 2 + j * k, m + k + l, n + k + l, l, l);

        for (int o = 0; o < i; ++o) {
            guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l + o * k, this.buttonY, m + l, n, k, l);
            guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l + (o + 1) * k, this.buttonY, m + l, n, l, l);

            for (int p = 0; p < j; ++p) {
                if (o == 0) {
                    guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX, this.buttonY + l + p * k, m, n + l, l, k);
                    guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX, this.buttonY + l + (p + 1) * k, m, n + l, l, l);
                }

                guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l + o * k, this.buttonY + l + p * k, m + l, n + l, k, k);
                guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l + (o + 1) * k, this.buttonY + l + p * k, m + l, n + l, l, k);
                guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l + o * k, this.buttonY + l + (p + 1) * k, m + l, n + l, k, l);
                guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l + (o + 1) * k - 1, this.buttonY + l + (p + 1) * k - 1, m + l, n + l, l + 1, l + 1);
                if (o == i - 1) {
                    guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l * 2 + i * k, this.buttonY + l + p * k, m + k + l, n + l, l, k);
                    guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l * 2 + i * k, this.buttonY + l + (p + 1) * k, m + k + l, n + l, l, l);
                }
            }

            guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l + o * k, this.buttonY + l * 2 + j * k, m + l, n + k + l, k, l);
            guiGraphics.blit(BACKGROUND_TEXTURE, this.buttonX + l + (o + 1) * k, this.buttonY + l * 2 + j * k, m + l, n + k + l, l, l);
        }

    }

    @Environment(EnvType.CLIENT)
    private class CustomAlternativeButtonWidget extends AbstractWidget implements PlaceRecipe<Ingredient> {
        final Recipe<?> recipe;
        private final boolean craftable;
        protected final List<InputSlot> slots = Lists.newArrayList();

        public CustomAlternativeButtonWidget(int x, int y, Recipe<?> recipe, boolean craftable) {
            super(x, y, 200, 20, CommonComponents.EMPTY);
            this.width = 24;
            this.height = 24;
            this.recipe = recipe;
            this.craftable = craftable;
            this.alignRecipe(recipe);
        }

        protected void alignRecipe(Recipe<?> recipe) {
            this.placeRecipe(3, 3, -1, recipe, recipe.getIngredients().iterator(), 0);
        }

        protected void updateWidgetNarration(NarrationElementOutput builder) {
            this.defaultButtonNarrationText(builder);
        }

        public void addItemToSlot(Iterator<Ingredient> inputs, int slot, int amount, int gridX, int gridY) {
            ItemStack[] itemStacks = inputs.next().getItems();
            if (itemStacks.length != 0) {
                this.slots.add(new InputSlot(3 + gridY * 7, 3 + gridX * 7, itemStacks));
            }

        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
            int i = 152;
            if (!this.craftable) {
                i += 26;
            }

            int j = 78;
            if (this.isHovered()) {
                j += 26;
            }
            guiGraphics.blit(BACKGROUND_TEXTURE, this.getX(), this.getY(), i, j, this.width, this.height);
            PoseStack poseStack = RenderSystem.getModelViewStack();
            poseStack.pushPose();
            poseStack.translate((this.getX() + 2), (this.getY() + 2), 125.0);

            for (InputSlot inputSlot : this.slots) {
                poseStack.pushPose();
                poseStack.translate(inputSlot.y, inputSlot.x, 0.0);
                poseStack.scale(0.375F, 0.375F, 1.0F);
                poseStack.translate(-8.0, -8.0, 0.0);
                RenderSystem.applyModelViewMatrix();
                guiGraphics.renderItem(inputSlot.stacks[Mth.floor(PrivateRecipeAlternativesWidget.this.time / 30.0F) % inputSlot.stacks.length], 0, 0);
                poseStack.popPose();
            }

            poseStack.popPose();
            RenderSystem.applyModelViewMatrix();
        }

        @Environment(EnvType.CLIENT)
        protected class InputSlot {
            public final ItemStack[] stacks;
            public final int y;
            public final int x;

            public InputSlot(int y, int x, ItemStack[] stacks) {
                this.y = y;
                this.x = x;
                this.stacks = stacks;
            }
        }
    }
}
