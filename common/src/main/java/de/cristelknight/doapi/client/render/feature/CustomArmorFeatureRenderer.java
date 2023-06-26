package de.cristelknight.doapi.client.render.feature;


import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.common.item.CustomArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class CustomArmorFeatureRenderer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

	public Map<FullCustomArmor, Pair<HumanoidModel<T>, HumanoidModel<T>>> MODELS = Maps.newHashMap();
	private final EntityModelSet modelLoader;

	public CustomArmorFeatureRenderer(RenderLayerParent<T, M> context, EntityModelSet modelSet) {
		super(context);
		this.modelLoader = modelSet;
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
		this.renderArmorPiece(poseStack, multiBufferSource, livingEntity, EquipmentSlot.CHEST, i, this.getArmorModel(livingEntity, EquipmentSlot.CHEST));
		this.renderArmorPiece(poseStack, multiBufferSource, livingEntity, EquipmentSlot.LEGS, i, this.getArmorModel(livingEntity, EquipmentSlot.LEGS));
		this.renderArmorPiece(poseStack, multiBufferSource, livingEntity, EquipmentSlot.FEET, i, this.getArmorModel(livingEntity, EquipmentSlot.FEET));
		//this.renderArmorPiece(poseStack, multiBufferSource, livingEntity, EquipmentSlot.HEAD, i, this.getArmorModel(livingEntity, EquipmentSlot.HEAD));
	}

	private void renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity, EquipmentSlot equipmentSlot, int i, FullCustomArmor armor) {
		ItemStack itemStack = livingEntity.getItemBySlot(equipmentSlot);
		if (!(itemStack.getItem() instanceof ArmorItem armorItem)) {
			return;
		}
		if (armorItem.getEquipmentSlot() != equipmentSlot) {
			return;
		}
		if(armor == null) return;

		Pair<HumanoidModel<T>, HumanoidModel<T>> humanoidModels = MODELS.get(armor);

		HumanoidModel<T> humanoidModel = usesInnerModel(equipmentSlot) ? humanoidModels.getSecond() : humanoidModels.getFirst();


		this.getParentModel().copyPropertiesTo(humanoidModel);
		this.setPartVisibility(humanoidModel, equipmentSlot);
		boolean bl = this.usesInnerModel(equipmentSlot);
		boolean bl2 = itemStack.hasFoil();

		if (armorItem instanceof DyeableArmorItem) {
			int j = ((DyeableArmorItem)armorItem).getColor(itemStack);
			float f = (float)(j >> 16 & 0xFF) / 255.0f;
			float g = (float)(j >> 8 & 0xFF) / 255.0f;
			float h = (float)(j & 0xFF) / 255.0f;
			this.renderModel(poseStack, multiBufferSource, i, armor, bl2, humanoidModel,  f, g, h);
			//this.renderModel(poseStack, multiBufferSource, i, armor, bl2, humanoidModel, 1.0f, 1.0f, 1.0f, "overlay");
		} else {
			this.renderModel(poseStack, multiBufferSource, i, armor, bl2, humanoidModel,1.0f, 1.0f, 1.0f);
		}
	}



	private void setPartVisibility(HumanoidModel<T> humanoidModel, EquipmentSlot equipmentSlot) {
		humanoidModel.setAllVisible(false);
		switch (equipmentSlot) {
			case HEAD -> {
				humanoidModel.head.visible = true;
				humanoidModel.hat.visible = true;
			}
			case CHEST -> {
				humanoidModel.body.visible = true;
				humanoidModel.rightArm.visible = true;
				humanoidModel.leftArm.visible = true;
			}
			case LEGS -> {
				humanoidModel.body.visible = true;
				humanoidModel.rightLeg.visible = true;
				humanoidModel.leftLeg.visible = true;
			}
			case FEET -> {
				humanoidModel.rightLeg.visible = true;
				humanoidModel.leftLeg.visible = true;
			}
		}
	}

	private void renderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, FullCustomArmor armor, boolean bl, HumanoidModel<T> humanoidModel, float f, float g, float h) {
		VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(armor.texture), false, bl);
		humanoidModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, f, g, h, 1.0f);
	}

	public void addModels(FullCustomArmor armor, HumanoidModel<T> outerModel, HumanoidModel<T> innerModel){
		MODELS.put(armor, new Pair<>(outerModel, innerModel));

	}

	private FullCustomArmor getArmorModel(T entity, EquipmentSlot slot) {
		Item hatItem = getArmorItem(entity, slot);
		if(hatItem != null) {
			if(MODELS.isEmpty()) {
				List<DoApiAPI> apis = Util.getApis(DoApiAPI.class, "doapi", DoApiPlugin.class);
				for(DoApiAPI api : apis){
					api.registerArmor(MODELS, modelLoader);
				}
			}
			for(FullCustomArmor armor : MODELS.keySet()){
				if(armor.set.contains(hatItem)) return armor;
			}
		}
		return null;
	}

	private CustomArmorItem getArmorItem(T entity, EquipmentSlot slot) {
		ItemStack armorSlot = entity.getItemBySlot(slot);
		if(armorSlot.getItem() instanceof CustomArmorItem hat && !armorSlot.isEmpty()) {
			return hat;
		}
		return null;
	}

	private boolean usesInnerModel(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.LEGS;
	}

}

