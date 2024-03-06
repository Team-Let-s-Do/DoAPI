package de.cristelknight.doapi.api;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.client.render.feature.FullCustomArmor;
import de.cristelknight.doapi.common.item.ICustomArmor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Set;

public interface DoApiAPI {
    void registerBlocks(Set<Block> blocks);

    <T extends LivingEntity> void registerHat(Map<ICustomArmor, EntityModel<T>> models, EntityModelSet modelLoader);

    <T extends LivingEntity> void registerArmor(Map<FullCustomArmor, Pair<HumanoidModel<T>, HumanoidModel<T>>> models, EntityModelSet modelLoader);

}
