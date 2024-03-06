package de.cristelknight.doapi.common.block.entity;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.common.item.StandardItem;
import de.cristelknight.doapi.common.registry.DoApiBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class StandardBlockEntity extends BlockEntity implements BlockEntityTicker<StandardBlockEntity> {

    private ItemStack stack;
    public StandardBlockEntity(BlockPos blockPos, BlockState state) {
        super(DoApiBlockEntityTypes.STANDARD.get(), blockPos, state);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        CompoundTag tag = new CompoundTag();
        if(stack == null) stack = ItemStack.EMPTY;
        stack.save(tag);
        nbt.put("stack", tag);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        stack = ItemStack.of(nbt.getCompound("stack"));
    }

    public void fromItem(ItemStack stack){
        Item item = stack.getItem();
        if(item instanceof StandardItem){
            this.stack = new ItemStack(stack.getItem());
        }
        else throw new RuntimeException("[DoApi] False item for standard! At: " + getBlockPos());
    }

    public Item getItem(){
        return stack == null ? Items.AIR : stack.getItem();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState blockState, StandardBlockEntity blockEntity) {
        if (!level.isClientSide() && level.getGameTime() % 80L == 0L) {
            MobEffectInstance instance = StandardItem.getEffectInstanceOrNull(getItem());
            if (instance == null) {
                DoApi.LOGGER.error("MobEffectInstance for StandardBlock is null! At: " + pos);
                return;
            }
            level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(8F), player -> true).forEach(player -> player.addEffect(instance));
        }
    }
}