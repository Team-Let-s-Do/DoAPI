package de.cristelknight.doapi.api;

import de.cristelknight.doapi.client.render.feature.CustomArmorManager;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Set;

public interface DoApiAPI {

    void registerBlocks(Set<Block> blocks);

    <T extends LivingEntity> void registerHat(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader);

    <T extends LivingEntity> void registerArmor(CustomArmorManager<T> armors, EntityModelSet modelLoader);

}
