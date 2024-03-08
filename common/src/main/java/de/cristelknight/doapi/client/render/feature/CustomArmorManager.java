package de.cristelknight.doapi.client.render.feature;

import com.google.common.collect.Sets;
import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class CustomArmorManager<T extends LivingEntity> {
    private final Set<CustomArmorSet<T>> ARMORS = Sets.newHashSet();

    private final EntityModelSet modelLoader;

    public CustomArmorManager(EntityModelSet modelLoader) {
        this.modelLoader = modelLoader;
    }

    public boolean isEmpty() {
        return this.ARMORS.isEmpty();
    }

    public CustomArmorSet<T> addArmor(CustomArmorSet<T> model) {
        this.ARMORS.add(model);
        return model;
    }

    public @Nullable CustomArmorSet<T> getSet(Item item) {
        for (CustomArmorSet<T> armor : this.ARMORS)
            if (armor.getArmor().contains(item))
                return armor;
        return null;
    }

    public @Nullable EntityModel<T> getModel(Item item, EquipmentSlot slot) {
        if (this.ARMORS.isEmpty())
            this.updateArmors();
        for (CustomArmorSet<T> armor : this.ARMORS)
            if (armor.getArmor().contains(item))
                return armor.getModel(slot);
        return null;
    }

    public @Nullable ResourceLocation getTexture(Item item, String string) {
        if (this.ARMORS.isEmpty())
            this.updateArmors();
        for (CustomArmorSet<T> armor : this.ARMORS)
            if (armor.getArmor().contains(item))
                return armor.getTexture(string);
        return null;
    }

    private void updateArmors() {
        List<DoApiAPI> apis = Util.getApis(DoApiAPI.class, "doapi", DoApiPlugin.class);
        for (DoApiAPI api : apis)
            api.registerArmor(this, this.modelLoader);
    }
}