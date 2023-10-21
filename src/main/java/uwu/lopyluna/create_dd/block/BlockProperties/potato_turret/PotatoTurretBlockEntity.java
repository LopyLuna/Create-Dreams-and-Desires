package uwu.lopyluna.create_dd.block.BlockProperties.potato_turret;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.DDCreate;

import java.util.List;

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

    public ServerPlayer owner;

    public SmartInventory inventory;

    public float amogus = 50;


    public float visualAngleY=0;
    public float visualAngleX=0;
    public static final int RANGE = 4;


    public PotatoTurretBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        inventory = new SmartInventory(1, this)
                .withMaxStackSize(1).forbidExtraction().forbidInsertion();
    }



    public void tick(){
        super.tick();


        targetAngleY %=360;
        amogus %= 360;



        angleX.chase(visualAngleX, 0.3f, LerpedFloat.Chaser.EXP);
        angleX.tickChaser();

        angleY.chase(visualAngleY+180, 0.3f, LerpedFloat.Chaser.EXP);
        angleY.tickChaser();




        visualAngleY = (float) targetAngleY;
        visualAngleX = (float) targetAngleX;
        // if(level.isClientSide)
        //     return;

        for(LivingEntity livingEntity : level.getEntitiesOfClass(LivingEntity.class, rangeZone())) {
            if(livingEntity.isDeadOrDying())
                continue;
            if(livingEntity instanceof ServerPlayer)
                if(((ServerPlayer) livingEntity).isCreative()||((ServerPlayer) livingEntity).isSpectator())
                    continue;
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

            setTargetAngles();
            if(timer >= fireRate)
                shoot();
        }
    }
    public void setTargetAngles(){
        double distanceX = targetedEntity.getBlockX()-this.getBlockPos().getX();
        double distanceY = targetedEntity.getBlockY()-this.getBlockPos().getY();
        double distanceZ = targetedEntity.getBlockZ()-this.getBlockPos().getZ();
        double distanceHorizontal = Math.sqrt((distanceZ*distanceZ)+(distanceX*distanceX));


        DDCreate.LOGGER.debug("distance X  " + distanceX);
        DDCreate.LOGGER.debug("distance Z  " + distanceZ);


        targetAngleX= Math.toDegrees(Math.atan(distanceY/distanceHorizontal));
        targetAngleY = Math.toDegrees(Math.atan(distanceX / distanceZ));


        if(distanceZ<0)
            targetAngleY+=180;

    }

    public void shoot(){
        timer = 0;

        targetVec = Vec3.directionFromRotation(-(float) targetAngleX, (float) -targetAngleY);
        amogus = (float) targetAngleY;
        DDCreate.LOGGER.debug("ANGLE:  " + targetAngleY);

        targetVec = targetVec.scale(4);

        if(distance>=4)
            targetVec = targetVec.multiply(1,-1.6,1);

        if(level.isClientSide())
            return;

        PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(level);
        projectile.setItem(Items.POTATO.getDefaultInstance());
        projectile.setPos(getBlockPos().getX()+.5, getBlockPos().getY()+1.5, getBlockPos().getZ()+.5);
        projectile.setDeltaMovement(targetVec);
        level.addFreshEntity(projectile);

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
        Lang.translate("recipe.assembly.step",targetAngleY)
                .style(ChatFormatting.LIGHT_PURPLE)
                .forGoggles(tooltip,1);
        return true;
    }
    public AABB rangeZone(){
        return new AABB(getBlockPos().getX()-RANGE,getBlockPos().getY()-RANGE,getBlockPos().getZ()-RANGE,getBlockPos().getX()+RANGE,getBlockPos().getY()+RANGE,getBlockPos().getZ()+RANGE);
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