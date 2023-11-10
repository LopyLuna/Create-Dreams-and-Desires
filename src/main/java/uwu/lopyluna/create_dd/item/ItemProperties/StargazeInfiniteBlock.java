package uwu.lopyluna.create_dd.item.ItemProperties;

import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "inline"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class StargazeInfiniteBlock extends BlockItem {

    public StargazeInfiniteBlock(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        InteractionResult interactionresult = this.place(new BlockPlaceContext(pContext));

        if (!interactionresult.consumesAction() && this.isEdible()) {
            InteractionResult interactionresult1 = this.use(pContext.getLevel(), pContext.getPlayer(), pContext.getHand()).getResult();
            return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : interactionresult1;
        } else {
            return interactionresult;
        }
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        BlockState pBlock = this.getBlock().defaultBlockState();
        BlockPlaceContext blockplacecontext = this.updatePlacementContext(pContext);
        BlockState blockstate = this.getPlacementState(blockplacecontext);
        BlockPos blockpos = blockplacecontext.getClickedPos();
        Level level = blockplacecontext.getLevel();
        Player player = blockplacecontext.getPlayer();
        ItemStack itemstack = blockplacecontext.getItemInHand();
        BlockState blockstate1 = level.getBlockState(blockpos);
        if (!pContext.canPlace()) {
            level.playSound(player, blockpos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.75F + level.random.nextFloat() * 1.05F, 0.0F + level.random.nextFloat() * 1.05F);
            return InteractionResult.FAIL;
        } else {
            if (blockplacecontext == null) {
                level.playSound(player, blockpos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.75F + level.random.nextFloat() * 1.05F, 0.0F + level.random.nextFloat() * 1.05F);
                return InteractionResult.FAIL;
            } else {
                if (blockstate == null) {
                    level.playSound(player, blockpos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.75F + level.random.nextFloat() * 1.05F, 0.0F + level.random.nextFloat() * 1.05F);
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(blockplacecontext, blockstate)) {
                    level.playSound(player, blockpos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.75F + level.random.nextFloat() * 1.05F, 0.0F + level.random.nextFloat() * 1.05F);
                    return InteractionResult.FAIL;
                } else {
                    if (blockstate1.is(blockstate.getBlock())) {
                        blockstate1 = this.updateBlockStateFromTag(blockpos, level, itemstack, blockstate1);
                        this.updateCustomBlockEntityTag(blockpos, level, player, itemstack, blockstate1);
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                        }
                    }

                    level.gameEvent(GameEvent.BLOCK_PLACE, blockpos, GameEvent.Context.of(player, blockstate1));
                    SoundType soundtype = pBlock.getSoundType(level, blockpos, pContext.getPlayer());
                    level.playSound(player, blockpos, this.getPlaceSound(pBlock, level, blockpos, pContext.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    level.playSound(player, blockpos, SoundEvents.EVOKER_CAST_SPELL, SoundSource.BLOCKS, 0.15F + level.random.nextFloat() * 1.05F, 0.75F + level.random.nextFloat() * 1.05F);

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    private BlockState updateBlockStateFromTag(BlockPos pPos, Level pLevel, ItemStack pStack, BlockState pState) {
        BlockState blockstate = pState;
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null) {
            CompoundTag compoundtag1 = compoundtag.getCompound("BlockStateTag");
            StateDefinition<Block, BlockState> statedefinition = pState.getBlock().getStateDefinition();

            for(String s : compoundtag1.getAllKeys()) {
                Property<?> property = statedefinition.getProperty(s);
                if (property != null) {
                    String s1 = compoundtag1.get(s).getAsString();
                    blockstate = updateState(blockstate, property, s1);
                }
            }
        }

        if (blockstate != pState) {
            pLevel.setBlock(pPos, blockstate, 2);
        }

        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState pState, Property<T> pProperty, String pValueIdentifier) {
        return pProperty.getValue(pValueIdentifier).map((p_40592_) -> {
            return pState.setValue(pProperty, p_40592_);
        }).orElse(pState);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        return pContext.getLevel().setBlock(pContext.getClickedPos(), pState, 11);
    }
    
    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Deprecated
    protected SoundEvent getPlaceSound(BlockState pState) {
        return pState.getSoundType().getPlaceSound();
    }

    protected SoundEvent getPlaceSound(BlockState state, Level world, BlockPos pos, Player entity) {
        return state.getSoundType(world, pos, entity).getPlaceSound();
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.level;
        Vec3 pos = entity.position();
        CompoundTag persistentData = entity.getPersistentData();

        if (world.isClientSide) {
            if (world.random.nextFloat() < getIdleParticleChance(entity)) {
                Vec3 ppos = VecHelper.offsetRandomly(pos, world.random, .6f);
                world.addParticle(ParticleTypes.GLOW, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
            }

            if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
                Vec3 basemotion = new Vec3(0, -2, 0);
                world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0.025F, 0);
                for (int i = 0; i < 20; i++) {
                    Vec3 motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
                    world.addParticle(ParticleTypes.GLOW, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                }
                persistentData.putBoolean("PlayEffects", true);
            }

            return false;
        }

        entity.setNoGravity(true);

        return false;
    }

    protected float getIdleParticleChance(ItemEntity entity) {
        return (float) (Mth.clamp(entity.getItem()
                .getCount() - 5, Mth.clamp(entity.getDeltaMovement().y * 20, 2, 5), 100) / 64f);
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    @Override
    public void fillItemCategory(CreativeModeTab pGroup, NonNullList<ItemStack> pItems) {
        if (this.allowedIn(pGroup)) {
            pItems.add(new ItemStack(this));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    }

    @Override
    public void registerBlocks(Map<Block, Item> pBlockToItemMap, Item pItem) {
    }

    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
    }
}
