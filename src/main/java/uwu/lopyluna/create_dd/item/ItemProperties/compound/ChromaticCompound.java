package uwu.lopyluna.create_dd.item.ItemProperties.compound;

import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.item.DDItems;


public class ChromaticCompound extends Item {

    public ChromaticCompound(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.level;
        CRecipes config = AllConfigs.server().recipes;

        double y = entity.getY();
        double yMotion = entity.getDeltaMovement().y;
        int minHeight = world.getMinBuildHeight();
        CompoundTag data = entity.getPersistentData();

        // Convert to Shadow steel if in void
        if (y < minHeight && y - yMotion < -10 + minHeight && config.enableShadowSteelRecipe.get()) {
            ItemStack newStack = DDItems.SHADOW_STEEL.asStack();
            newStack.setCount(stack.getCount());
            data.putBoolean("JustCreated", true);
            entity.setItem(newStack);
        }

        if (!config.enableRefinedRadianceRecipe.get())
            return false;

        // Is inside beacon beam?
        boolean isOverBeacon = false;
        int entityX = Mth.floor(entity.getX());
        int entityZ = Mth.floor(entity.getZ());
        int localWorldHeight = world.getHeight(Heightmap.Types.WORLD_SURFACE, entityX, entityZ);

        BlockPos.MutableBlockPos testPos =
                new BlockPos.MutableBlockPos(entityX, Math.min(Mth.floor(entity.getY()), localWorldHeight), entityZ);

        while (testPos.getY() > -32) {
            testPos.move(Direction.DOWN);
            BlockState state = world.getBlockState(testPos);
            if (state.getLightBlock(world, testPos) >= 15 && state.getBlock() != Blocks.BEDROCK)
                break;
            if (state.getBlock() == Blocks.BEACON) {
                BlockEntity be = world.getBlockEntity(testPos);

                if (!(be instanceof BeaconBlockEntity))
                    break;

                BeaconBlockEntity bte = (BeaconBlockEntity) be;

                if (!bte.getBeamSections().isEmpty())
                    isOverBeacon = true;

                break;
            }
        }

        if (isOverBeacon) {
            ItemStack newStack = DDItems.REFINED_RADIANCE.asStack();
            newStack.setCount(stack.getCount());
            data.putBoolean("JustCreated", true);
            entity.setItem(newStack);
            return false;
        }

        return false;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack heldItem, Player player, LivingEntity entity,
                                                  InteractionHand hand) {
        if (!(entity instanceof Blaze))
            return InteractionResult.PASS;

        Level world = player.level;
        spawnCaptureEffects(world, entity.position());
        if (world.isClientSide)
            return InteractionResult.FAIL;

        giveBlazeGoldItemTo(player, heldItem, hand);
        entity.discard();
        return InteractionResult.FAIL;
    }

    protected void giveBlazeGoldItemTo(Player player, ItemStack heldItem, InteractionHand hand) {
        ItemStack filled = DDItems.BLAZE_GOLD.asStack();
        if (!player.isCreative())
            heldItem.shrink(1);
        if (heldItem.isEmpty()) {
            player.setItemInHand(hand, filled);
            return;
        }
        player.getInventory()
                .placeItemBackInInventory(filled);
    }

    private void spawnCaptureEffects(Level world, Vec3 vec) {
        if (world.isClientSide) {
            for (int i = 0; i < 40; i++) {
                Vec3 motion = VecHelper.offsetRandomly(Vec3.ZERO, world.random, .125f);
                world.addParticle(ParticleTypes.FLAME, vec.x, vec.y, vec.z, motion.x, motion.y, motion.z);
                world.addParticle(ParticleTypes.SMOKE, vec.x, vec.y, vec.z, motion.x, motion.y, motion.z);
                world.addParticle(ParticleTypes.LAVA, vec.x, vec.y, vec.z, motion.x, motion.y, motion.z);
                Vec3 circle = motion.multiply(1, 0, 1)
                        .normalize()
                        .scale(.5f);
                world.addParticle(ParticleTypes.SMOKE, circle.x, vec.y, circle.z, 0, -0.125, 0);
            }
            return;
        }

        BlockPos soundPos = new BlockPos(vec);
        world.playSound(null, soundPos, SoundEvents.BLAZE_HURT, SoundSource.PLAYERS, .25f, .5f);
        world.playSound(null, soundPos, SoundEvents.BLAZE_DEATH, SoundSource.PLAYERS, .5f, .75f);
        world.playSound(null, soundPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, .5f, .75f);
    }
}
