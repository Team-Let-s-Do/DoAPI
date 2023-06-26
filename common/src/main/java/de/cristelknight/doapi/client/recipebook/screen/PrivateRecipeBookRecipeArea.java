package de.cristelknight.doapi.client.recipebook.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.cristelknight.doapi.client.recipebook.handler.AbstractPrivateRecipeScreenHandler;
import de.cristelknight.doapi.client.recipebook.screen.widgets.PrivateRecipeBookWidget;
import de.cristelknight.doapi.client.recipebook.screen.widgets.PrivateAnimatedResultButton;
import de.cristelknight.doapi.client.recipebook.screen.widgets.PrivateRecipeAlternativesWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PrivateRecipeBookRecipeArea {
    private final List<PrivateAnimatedResultButton> resultButtons = Lists.newArrayListWithCapacity(20);
    @Nullable
    private PrivateAnimatedResultButton hoveredResultButton;
    private final PrivateRecipeAlternativesWidget alternatesWidget = new PrivateRecipeAlternativesWidget();
    private Minecraft client;
    private List<? extends Recipe<Container>> resultCollections = ImmutableList.of();
    private StateSwitchingButton nextPageButton;
    private StateSwitchingButton prevPageButton;
    private int pageCount;
    private int currentPage;
    @Nullable
    private Recipe<?> lastClickedRecipe;
    private AbstractPrivateRecipeScreenHandler recipeScreenHandler;

    public PrivateRecipeBookRecipeArea() {
        for(int i = 0; i < 20; ++i) {
            this.resultButtons.add(new PrivateAnimatedResultButton());
        }
    }

    public void initialize(Minecraft client, int parentLeft, int parentTop, AbstractPrivateRecipeScreenHandler recipeScreenHandler) {
        this.client = client;
        this.recipeScreenHandler = recipeScreenHandler;

        for(int i = 0; i < this.resultButtons.size(); ++i) {
            this.resultButtons.get(i).setPos(parentLeft + 11 + 25 * (i % 5), parentTop + 31 + 25 * (i / 5));
        }

        this.nextPageButton = new StateSwitchingButton(parentLeft + 93, parentTop + 137, 12, 17, false);
        this.nextPageButton.initTextureValues(1, 208, 13, 18, PrivateRecipeBookWidget.TEXTURE);
        this.prevPageButton = new StateSwitchingButton(parentLeft + 38, parentTop + 137, 12, 17, true);
        this.prevPageButton.initTextureValues(1, 208, 13, 18, PrivateRecipeBookWidget.TEXTURE);
    }

    public void setResults(List<? extends Recipe<Container>> resultCollections, boolean resetCurrentPage) {
        this.resultCollections = resultCollections;
        this.pageCount = (int)Math.ceil((double)resultCollections.size() / 20.0);
        if (this.pageCount <= this.currentPage || resetCurrentPage) {
            this.currentPage = 0;
        }

        this.refreshResultButtons();
    }

    private void refreshResultButtons() {
        int i = 20 * this.currentPage;

        for(int j = 0; j < this.resultButtons.size(); ++j) {
            PrivateAnimatedResultButton animatedResultButton = this.resultButtons.get(j);
            if (i + j < this.resultCollections.size()) {
                Recipe<?> recipe = this.resultCollections.get(i + j);
                animatedResultButton.showResultCollection(recipe, recipeScreenHandler);
                animatedResultButton.visible = true;
            } else {
                animatedResultButton.visible = false;
            }
        }

        this.hideShowPageButtons();
    }

    private void hideShowPageButtons() {
        this.nextPageButton.visible = this.pageCount > 1 && this.currentPage < this.pageCount - 1;
        this.prevPageButton.visible = this.pageCount > 1 && this.currentPage > 0;
    }

    public void draw(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, float delta) {
        if (this.pageCount > 1) {
            int var10000 = this.currentPage + 1;
            String string = "" + var10000 + "/" + this.pageCount;
            int i = this.client.font.width(string);
            guiGraphics.drawString(this.client.font, string, (x - i / 2 + 73), (y + 141), -1, false);
        }

        this.hoveredResultButton = null;

        for (PrivateAnimatedResultButton animatedResultButton : this.resultButtons) {
            animatedResultButton.render(guiGraphics, mouseX, mouseY, delta);
            if (animatedResultButton.visible && animatedResultButton.isHoveredOrFocused()) {
                this.hoveredResultButton = animatedResultButton;
            }
        }

        this.prevPageButton.render(guiGraphics, mouseX, mouseY, delta);
        this.nextPageButton.render(guiGraphics, mouseX, mouseY, delta);
        this.alternatesWidget.render(guiGraphics, mouseX, mouseY, delta);
    }

    public void drawTooltip(GuiGraphics guiGraphics, int x, int y) {
        if (this.client.screen != null && this.hoveredResultButton != null && !this.alternatesWidget.isVisible()) {
            guiGraphics.renderComponentTooltip(this.client.font, this.hoveredResultButton.getTooltip(this.client.screen), x, y);
        }

    }

    @Nullable
    public Recipe<?> getLastClickedRecipe() {
        return this.lastClickedRecipe;
    }

    public void hideAlternates() {
        this.alternatesWidget.setVisible(false);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button, int areaLeft, int areaTop, int areaWidth, int areaHeight) {
        this.lastClickedRecipe = null;
        if (this.alternatesWidget.isVisible()) {
            if (this.alternatesWidget.mouseClicked(mouseX, mouseY, button)) {
                this.lastClickedRecipe = this.alternatesWidget.getLastClickedRecipe();
            } else {
                this.alternatesWidget.setVisible(false);
            }

            return true;
        } else if (this.nextPageButton.mouseClicked(mouseX, mouseY, button)) {
            ++this.currentPage;
            this.refreshResultButtons();
            return true;
        } else if (this.prevPageButton.mouseClicked(mouseX, mouseY, button)) {
            --this.currentPage;
            this.refreshResultButtons();
            return true;
        } else {
            Iterator<PrivateAnimatedResultButton> var10 = this.resultButtons.iterator();

            PrivateAnimatedResultButton animatedResultButton;
            do {
                if (!var10.hasNext()) {
                    return false;
                }
                animatedResultButton = var10.next();
            } while(!animatedResultButton.mouseClicked(mouseX, mouseY, button));
            if (button == 0) {
                this.lastClickedRecipe = animatedResultButton.currentRecipe();
            }
            if (button == 1 && !this.alternatesWidget.isVisible() && animatedResultButton.hasResult()) {
                // Implement maybe later
                // this.alternatesWidget.showAlternativesForResult(this.client, animatedResultButton.getRecipe(), animatedResultButton.x, animatedResultButton.y, areaLeft + areaWidth / 2, areaTop + 13 + areaHeight / 2, (float)animatedResultButton.getWidth());
            }
            return true;
        }
    }


    public Minecraft getClient() {
        return this.client;
    }
}