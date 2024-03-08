package de.cristelknight.doapi.client.render.feature;

import de.cristelknight.doapi.DoApi;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class CustomArmorSet<T extends LivingEntity> {
    private final Set<Item> armor;
    private ResourceLocation texture;

    private EntityModel<T> outerModel, innerModel, hatModel;

    public CustomArmorSet(Item... armor) {
        this.armor = Set.of(armor);
    }

    public CustomArmorSet<T> setTexture(ResourceLocation texture) {
        String path = texture.getPath();
        if (!path.contains("/") || !path.contains("."))
            this.texture = new ResourceLocation(texture.getNamespace(), "textures/models/armor/" + path + ".png");
        else
            this.texture = texture;
        return this;
    }

    public CustomArmorSet<T> setOuterModel(EntityModel<T> model) {
        this.outerModel = model;
        return this;
    }

    public CustomArmorSet<T> setInnerModel(EntityModel<T> model) {
        this.innerModel = model;
        return this;
    }

    public CustomArmorSet<T> setHatModel(EntityModel<T> model) {
        this.hatModel = model;
        return this;
    }

    public Set<Item> getArmor() {
        return this.armor;
    }

    public ResourceLocation getTexture(String string) { // textures/models/armor/
        if (string != null) {
            String path = this.texture.toString();
            int dotIndex = path.lastIndexOf('.');
            if (dotIndex != -1) {
                String basePath = path.substring(0, dotIndex);
                String extension = path.substring(dotIndex);

                String newPath = basePath + "_" + string + extension;
                return new ResourceLocation(newPath);
            }
        }
        return this.texture;
    }

    @Nullable
    public EntityModel<T> getModel(EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            if (this.hatModel == null)
                DoApi.LOGGER.warn("Add a hat model to the armor set: " + this.texture.toString());
            return this.hatModel;
        }
        if (this.usesInnerModel(slot)) {
            if (this.innerModel == null)
                DoApi.LOGGER.warn("Add a inner model to the armor set: " + this.texture.toString());
            return this.innerModel;
        } else {
            if (this.outerModel == null)
                DoApi.LOGGER.warn("Add a outer model to the armor set: " + this.texture.toString());
            return this.outerModel;
        }
    }

    private boolean usesInnerModel(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.LEGS;
    }
}