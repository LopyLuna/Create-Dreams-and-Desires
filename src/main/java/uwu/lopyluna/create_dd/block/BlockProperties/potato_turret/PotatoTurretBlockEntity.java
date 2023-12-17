package uwu.lopyluna.create_dd.block.BlockProperties.potato_turret;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonProjectileType;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileTypeManager;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import java.util.List;
import java.util.Optional;

public class PotatoTurretBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation {

    public Vec3 targetVec;
    public double targetAngleY;
    public double targetAngleX;
    public LerpedFloat angleY = LerpedFloat.angular();
    public LerpedFloat angleX = LerpedFloat.angular();

    public LivingEntity targetedEntity;

    public int timer = 0;

    public int fireRate = 5;

    public double distance=0;

    public String owner;

    public SmartInventory inventory;

    public float amogus = 50;


    public float visualAngleY=0;
    public float visualAngleX=0;
    public static final int RANGE = 20;

    protected LazyOptional<IItemHandlerModifiable> itemCapability;


    public PotatoTurretBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        inventory = new SmartInventory(1, this)
                .withMaxStackSize(64);

        itemCapability = LazyOptional.of(() -> new CombinedInvWrapper(inventory));
    }

    ;
    public void tick(){
        super.tick();

        fireRate = (int) (17-(getSpeed()/20));




        targetAngleY %=360;
        amogus %= 360;

        if(visualAngleX==0)
            visualAngleX=0.0001f;
        if(visualAngleY==0)
            visualAngleY=0.0001f;

        angleX.chase(visualAngleX, 0.3f, LerpedFloat.Chaser.EXP);
        angleX.tickChaser();

        angleY.chase(visualAngleY+180, 0.3f, LerpedFloat.Chaser.EXP);
        angleY.tickChaser();




        visualAngleY = (float) targetAngleY;
        visualAngleX = (float) targetAngleX;
        // if(level.isClientSide)
        //     return;

        for(LivingEntity livingEntity : level.getEntitiesOfClass(LivingEntity.class, rangeZone())) {

            if(livingEntity instanceof  Player) {
                if (((Player) livingEntity).isCreative() || ((Player) livingEntity).isSpectator())
                    continue;
                if(owner!=null)
                    if(livingEntity.getStringUUID().equals(owner))
                        continue;
            }
            double checkedDistance = getDistance(livingEntity);
            if(distance>=RANGE)
                continue;


            if(distance==0||distance>=checkedDistance){
                distance=checkedDistance;
                targetedEntity = livingEntity;
            }

        }



        timer++;
        if(targetedEntity!=null) {

            distance = getDistance(targetedEntity);

            setTargetAngles();
            if(timer >= fireRate&&targetedEntity!=null&&distance<=RANGE)
                shoot();
            if(targetedEntity.isDeadOrDying()||distance>=RANGE)
                targetedEntity = null;
            if(targetedEntity instanceof Player)
                if(((Player) targetedEntity).isCreative()||targetedEntity.isSpectator())
                    targetedEntity = null;
        }

        if(targetedEntity == null) {
            distance = 0;
            visualAngleX=0;
            visualAngleY=0;
        }
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    @Override
    public void invalidate() {
        super.invalidate();
        itemCapability.invalidate();
    }
    public void setTargetAngles(){
        double distanceX = targetedEntity.getBlockX()-this.getBlockPos().getX();
        double distanceY = targetedEntity.getBlockY()-this.getBlockPos().getY();
        double distanceZ = targetedEntity.getBlockZ()-this.getBlockPos().getZ();
        double distanceHorizontal = Math.sqrt((distanceZ*distanceZ)+(distanceX*distanceX));


        targetAngleX= Math.toDegrees(Math.atan(distanceY/distanceHorizontal));
        targetAngleY = Math.toDegrees(Math.atan(distanceX / distanceZ));


        if(distanceZ<0)
            targetAngleY+=180;

    }

    public void shoot(){




        if(inventory.isEmpty())
            return;

        Optional<PotatoCannonProjectileType> type = PotatoProjectileTypeManager.getTypeForStack(inventory.getItem(0));


        if(!type.isPresent())
            return;


        timer = 0;

        targetVec = Vec3.directionFromRotation((float) -targetAngleX, (float) -targetAngleY);
        amogus = (float) targetAngleY;


        targetVec = targetVec.scale(2);

        if(distance>=10)
            targetVec = targetVec.add(0,0.15,0);
//
        // if(level.isClientSide())
        //     return;

        PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(level);

        projectile.setItem(inventory.getItem(0));
        projectile.setPos(getBlockPos().getX()+.5, getBlockPos().getY()+1.5, getBlockPos().getZ()+.5);
        projectile.setDeltaMovement(targetVec);
        level.addFreshEntity(projectile);

        inventory.removeItem(0,1);



    }

    public double getDistance(LivingEntity livingEntity){

        double x = Math.abs(livingEntity.getBlockX()-this.getBlockPos().getX());
        double y = Math.abs(livingEntity.getBlockY()-this.getBlockPos().getY());
        double z = Math.abs(livingEntity.getBlockZ()-this.getBlockPos().getZ());

        return Math.sqrt((x*x)+(y*y)+(z*z));
    }
    @Override
    @SuppressWarnings("removal")
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Lang.translate("recipe.assembly.step",distance)
                .style(ChatFormatting.LIGHT_PURPLE)
                .forGoggles(tooltip,1);
        return true;
    }
    public AABB rangeZone(){
        return new AABB(getBlockPos()).inflate(RANGE);
    }


    @Override
    @SuppressWarnings("removal")
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemCapability.cast();
        return super.getCapability(cap, side);
    }
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        inventory.deserializeNBT(compound.getCompound("InputItems"));
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound,clientPacket);

        compound.put("InputItems", inventory.serializeNBT());



    }
}