package de.cristelknight.doapi.client.render.feature;


import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.common.item.CustomHatItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

@Deprecated
public class CustomHatFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

	public final Map<Item, EntityModel<T>> MODELS = Maps.newHashMap();

	private final EntityModelSet modelLoader;
	private final float yOffset;

	public CustomHatFeatureRenderer(RenderLayerParent<T, M> context, EntityModelSet modelSet) {
		this(context, modelSet, 0);
	}

	public CustomHatFeatureRenderer(RenderLayerParent<T, M> context, EntityModelSet modelLoader, float yOffset) {
		super(context);
		this.yOffset = yOffset;
		this.modelLoader = modelLoader;
	}

	public EntityModel<T> getHatModel(T entity, EquipmentSlot slot) {
		Item hatItem = this.getHatItem(entity, slot);
		if(hatItem != null) {
			if(this.MODELS.isEmpty()) {
				List<DoApiAPI> apis = Util.getApis(DoApiAPI.class, "doapi", DoApiPlugin.class);
				for(DoApiAPI api : apis){
					api.registerHat(this.MODELS, this.modelLoader);
				}
			}
			return this.MODELS.get(hatItem);
		}
		return null;
	}

	public CustomHatItem getHatItem(T entity, EquipmentSlot slot) {
		ItemStack headSlot = entity.getItemBySlot(slot);
		if(headSlot.getItem() instanceof CustomHatItem hat && !headSlot.isEmpty())
			return hat;
		return null;
	}



	@Override
	public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
		for(EquipmentSlot slot : slots){
			EntityModel<T> headModel = this.getHatModel(entity, slot);


			ItemStack headSlot = entity.getItemBySlot(slot);
			if(headModel != null && headSlot.getItem() instanceof CustomHatItem armorItem){

				matrices.pushPose();
                this.setupHat(matrices, slot, armorItem.getOffset());

				VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(this.getTexture(entity, slot)), false, headSlot.hasFoil());
				headModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
				matrices.popPose();
			}
		}
	}

	public void setupHat(PoseStack matrices, EquipmentSlot slot, float extraYOffset) {
		if(slot == EquipmentSlot.HEAD){
			((HeadedModel) this.getParentModel()).getHead().translateAndRotate(matrices);
		}
		matrices.scale(1F,1F,1F);
		matrices.translate(0, this.yOffset + extraYOffset, 0);
	}


	protected ResourceLocation getTexture(T entity, EquipmentSlot slot) {
		CustomHatItem customItem = this.getHatItem(entity, slot);
		if(customItem != null) return customItem.getTexture();
		return super.getTextureLocation(entity);
	}
}