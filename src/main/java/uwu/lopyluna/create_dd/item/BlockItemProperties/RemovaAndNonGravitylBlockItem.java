package uwu.lopyluna.create_dd.item.BlockItemProperties;

import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class RemovaAndNonGravitylBlockItem extends BlockItem {

    private final boolean CCA;
    private final boolean Scaffolding;
    private String CompountType;

    public RemovaAndNonGravitylBlockItem(Block pBlock, Properties pProperties, boolean CCA, boolean Scaffolding, String CompountType) {
        super(pBlock, pProperties);
        this.CCA = CCA;
        this.Scaffolding = Scaffolding;
        this.CompountType = CompountType;
    }

    public static RemovaAndNonGravitylBlockItem CCA(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, true, false, "None");
    }

    public static RemovaAndNonGravitylBlockItem BlazeBrass(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, false, false, "BlazeBrass");
    }
    public static RemovaAndNonGravitylBlockItem BlazeBrassScaffolding(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, false, true, "BlazeBrass");
    }
    public static RemovaAndNonGravitylBlockItem RefinedRadiance(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, false, false, "RefinedRadiance");
    }
    public static RemovaAndNonGravitylBlockItem RefinedRadianceScaffolding(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, false, true, "RefinedRadiance");
    }
    public static RemovaAndNonGravitylBlockItem ShadowSteel(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, false, false, "ShadowSteel");
    }
    public static RemovaAndNonGravitylBlockItem ShadowSteelScaffolding(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, false, true, "ShadowSteel");
    }
    public static RemovaAndNonGravitylBlockItem StargazeSingularity(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, false, false, "StargazeSingularity");
    }
    public static RemovaAndNonGravitylBlockItem StargazeSingularityScaffolding(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, false, true, "StargazeSingularity");
    }

    public static RemovaAndNonGravitylBlockItem CCAScaffolding(Block block, Properties properties) {
        return new RemovaAndNonGravitylBlockItem(block, properties, true, true, "None");
    }

    @Override
    public void fillItemCategory(CreativeModeTab pGroup, NonNullList<ItemStack> pItems) {
        if (this.allowedIn(pGroup)) {
            if (!(ModList.get().isLoaded("createaddition") && CCA)) {
                this.getBlock().fillItemCategory(pGroup, pItems);
            } else if (!(Objects.equals(CompountType, "StargazeSingularity") || Objects.equals(CompountType, "ShadowSteel") || Objects.equals(CompountType, "RefinedRadiance") || Objects.equals(CompountType, "BlazeBrass"))) {
                this.getBlock().fillItemCategory(pGroup, pItems);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level world, List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (CCA) {
        tooltip.add(Component.translatable("tooltip.removal.summary"));
        }
    }

    @Nullable
    public BlockPlaceContext updatePlacementContext(@NotNull BlockPlaceContext pContext) {
        if (Scaffolding) {
            BlockPos blockpos = pContext.getClickedPos();
            Level level = pContext.getLevel();
            BlockState blockstate = level.getBlockState(blockpos);
            Block block = this.getBlock();
            if (!blockstate.is(block))
                return pContext;
    
            Direction direction;
            if (pContext.isSecondaryUseActive()) {
                direction = pContext.isInside() ? pContext.getClickedFace()
                        .getOpposite() : pContext.getClickedFace();
            } else {
                direction = pContext.getClickedFace() == Direction.UP ? pContext.getHorizontalDirection() : Direction.UP;
            }
    
            int i = 0;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.mutable()
                    .move(direction);
    
            while (i < 7) {
                if (!level.isClientSide && !level.isInWorldBounds(blockpos$mutableblockpos)) {
                    Player player = pContext.getPlayer();
                    int j = level.getMaxBuildHeight();
                    if (player instanceof ServerPlayer sp && blockpos$mutableblockpos.getY() >= j)
                        sp.sendSystemMessage(Component.translatable("build.tooHigh", j - 1)
                                .withStyle(ChatFormatting.RED), true);
                    break;
                }
    
                blockstate = level.getBlockState(blockpos$mutableblockpos);
                if (!blockstate.is(this.getBlock())) {
                    if (blockstate.canBeReplaced(pContext)) {
                        return BlockPlaceContext.at(pContext, blockpos$mutableblockpos, direction);
                    }
                    break;
                }
    
                blockpos$mutableblockpos.move(direction);
                if (direction.getAxis()
                        .isHorizontal()) {
                    ++i;
                }
            }
        } else {
            return pContext;
        }
        return null;
    }


    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.level;
        Vec3 pos = entity.position();
        CompoundTag persistentData = entity.getPersistentData();
        if (world.isClientSide) {
            if (Objects.equals(CompountType, "BlazeBrass")) {
                if (world.random.nextFloat() < getIdleParticleChance(entity)) {
                    Vec3 ppos = VecHelper.offsetRandomly(pos, world.random, .4f);
                    world.addParticle(ParticleTypes.FLAME, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                    world.addParticle(ParticleTypes.LAVA, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                    world.addParticle(ParticleTypes.SMOKE, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                    world.addParticle(ParticleTypes.SMOKE, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                    world.addParticle(ParticleTypes.FALLING_LAVA, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                }

                if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
                    Vec3 basemotion = new Vec3(0, 1, 0);
                    world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0, 0);
                    for (int i = 0; i < 20; i++) {
                        Vec3 motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
                        world.addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.LAVA, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.FALLING_LAVA, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    }
                    persistentData.putBoolean("PlayEffects", true);
                }

                return false;
        }
            if (CCA) {
                if (world.random.nextFloat() < getIdleParticleChance(entity)) {
                    Vec3 ppos = VecHelper.offsetRandomly(pos, world.random, .5f);
                    world.addParticle(ParticleTypes.WAX_OFF, ppos.x, pos.y + 0.5f, ppos.z, 0, -0.1f, 0);
                    world.addParticle(ParticleTypes.WAX_OFF, ppos.x, pos.y + -0.25f, ppos.z, 0, .1f, 0);
                    world.addParticle(ParticleTypes.WAX_OFF, ppos.x, pos.y + 0.25f, ppos.z, 0, -0.1f, 0);
                }

                if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
                    Vec3 basemotion = new Vec3(0, 1, 0);
                    world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0, 0);
                    for (int i = 0; i < 20; i++) {
                        Vec3 motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
                        world.addParticle(ParticleTypes.WAX_OFF, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    }
                    persistentData.putBoolean("PlayEffects", true);
                }

                return false;
        }
            if (Objects.equals(CompountType, "RefinedRadiance")) {
                if (world.random.nextFloat() < getIdleParticleChance(entity)) {
                    Vec3 ppos = VecHelper.offsetRandomly(pos, world.random, .5f);
                    world.addParticle(ParticleTypes.END_ROD, ppos.x, pos.y, ppos.z, 0, -.1f, 0);
                }

                if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
                    Vec3 basemotion = new Vec3(0, 1, 0);
                    world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0, 0);
                    for (int i = 0; i < 20; i++) {
                        Vec3 motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
                        world.addParticle(ParticleTypes.WITCH, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    }
                    persistentData.putBoolean("PlayEffects", true);
                }

                return false;
        }
            if (Objects.equals(CompountType, "ShadowSteel")) {
                if (world.random.nextFloat() < getIdleParticleChance(entity)) {
                    Vec3 ppos = VecHelper.offsetRandomly(pos, world.random, .4f);
                    world.addParticle(ParticleTypes.WITCH, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                    world.addParticle(ParticleTypes.REVERSE_PORTAL, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                    world.addParticle(ParticleTypes.WITCH, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                    world.addParticle(ParticleTypes.REVERSE_PORTAL, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                }

                if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
                    Vec3 basemotion = new Vec3(0, 1, 0);
                    world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0, 0);
                    for (int i = 0; i < 20; i++) {
                        Vec3 motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
                        world.addParticle(ParticleTypes.WITCH, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.WITCH, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    }
                    persistentData.putBoolean("PlayEffects", true);
                }

                return false;
        }
            if (Objects.equals(CompountType, "StargazeSingularity")) {
                if (world.random.nextFloat() < getIdleParticleChance(entity)) {
                    Vec3 ppos = VecHelper.offsetRandomly(pos, world.random, .6f);
                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                    world.addParticle(ParticleTypes.GLOW, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                    world.addParticle(ParticleTypes.SNEEZE, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                    world.addParticle(ParticleTypes.SMOKE, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                    world.addParticle(ParticleTypes.SMOKE, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                    world.addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                }

                if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
                    Vec3 basemotion = new Vec3(0, -2, 0);
                    world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0.025F, 0);
                    for (int i = 0; i < 20; i++) {
                        Vec3 motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
                        world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.GLOW, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.SNEEZE, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                        world.addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    }
                    persistentData.putBoolean("PlayEffects", true);
                }

                return false;
            }
        }


        entity.setNoGravity(true);

        if (!persistentData.contains("JustCreated"))
            return false;
        onCreated(entity, persistentData);
        return false;
    }

    protected float getIdleParticleChance(ItemEntity entity) {
        return Mth.clamp(entity.getItem()
                .getCount() - 10, 5, 100) / 64f;
    }

    protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
        entity.lifespan = 6000;
        persistentData.remove("JustCreated");

        // just a flag to tell the client to play an effect
        entity.setSilent(true);
    }
}
