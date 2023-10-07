package de.cristelknight.doapi.config.cloth;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class LinkEntry extends AbstractConfigListEntry<Void> {
    private static final int HEIGHT = 40;
    private Button button = null;

    public LinkEntry(Component fieldName, Button.OnPress onPress, ResourceLocation texture, int offset) {
        super(fieldName, false);
        // TODO button = new ImageButton(0, 0, 200, HEIGHT, 0, 0, offset, texture,200, 40, onPress);
    }


    @Override
    public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
        super.render(graphics, index, y, x, entryWidth, entryHeight, mouseX, mouseY, isHovered, delta);
        button.setX(x + (entryWidth - button.getWidth()) / 2);
        button.setY(y + (entryHeight - HEIGHT) / 2);
        button.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public Void getValue() { return null; }

    @Override
    public Optional<Void> getDefaultValue() { return Optional.empty(); }

    @Override
    public void save() {}

    private List<Button> children0() {
        if(button == null) return Collections.emptyList();
        return Collections.singletonList(button);
    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() { return children0(); }

    @Override
    public List<? extends NarratableEntry> narratables() { return children0(); }

}