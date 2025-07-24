package dev.lopyluna.dndesires.content.blocks.kinetics.multimeter;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.gauge.GaugeBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.createmod.catnip.platform.CatnipServices;
import net.createmod.catnip.theme.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MultiMeterBE extends GaugeBlockEntity {

    static BlockPos lastSent;
    float stressTarget;

    public MultiMeterBE(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        registerAwardables(behaviours, AllAdvancements.STRESSOMETER, AllAdvancements.STRESSOMETER_MAXED);
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);

        if (!IRotate.StressImpact.isEnabled()) dialTarget = mergeTarget(0);
        else if (isOverStressed()) dialTarget = mergeTarget(1.125f);
        else if (maxStress == 0) dialTarget = mergeTarget(0);
        else dialTarget = mergeTarget(currentStress / maxStress);

        if (dialTarget > 0) {
            if (dialTarget < .5f) color = mergeColor(Color.mixColors(0x00FF00, 0xFFFF00, dialTarget * 2));
            else if (dialTarget < 1) color = mergeColor(Color.mixColors(0xFFFF00, 0xFF0000, (dialTarget) * 2 - 1));
            else color = mergeColor(0xFF0000);
        }

        sendData();
        setChanged();
    }

    public static float getDialTarget(float speed) {
        speed = Math.abs(speed);
        var medium = AllConfigs.server().kinetics.mediumSpeed.get().floatValue();
        var fast = AllConfigs.server().kinetics.fastSpeed.get().floatValue();
        var max = AllConfigs.server().kinetics.maxRotationSpeed.get().floatValue();
        float target;
        if (speed == 0) target = 0;
        else if (speed < medium) target = Mth.lerp(speed / medium, 0, .45f);
        else if (speed < fast) target = Mth.lerp((speed - medium) / (fast - medium), .45f, .75f);
        else target = Mth.lerp((speed - fast) / (max - fast), .75f, 1.125f);
        return target;
    }

    public float mergeTarget(float stress) {
        stressTarget = stress;
        var currSpeed = getSpeed();
        var target = getDialTarget(Math.abs(currSpeed));
        if (currSpeed == 0) target = 0;
        return stress > 0 ? Mth.clamp(mergeFloat(stress, target), 0, isOverStressed() ? 1.125f : 1f) : target;
    }

    public int mergeColor(int color) {
        return Color.mixColors(Color.mixColors(IRotate.SpeedLevel.of(Math.abs(getSpeed())).getColor(), 0xffffff, .25f), color, .5f);
    }

    public float mergeFloat(float a, float b) {
        return a + (b - a) * .5f;
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
        if (getSpeed() != 0) updateFromNetwork(capacity, stress, getOrCreateNetwork().getSize());
        else setChanged();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (!IRotate.StressImpact.isEnabled()) return false;

        CreateLang.translate("gui.gauge.info_header").forGoggles(tooltip);

        CreateLang.translate("gui.speedometer.title")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);
        IRotate.SpeedLevel.getFormattedSpeedText(speed, isOverStressed())
                .forGoggles(tooltip);


        var capacity = getNetworkCapacity();
        var stressFraction = getNetworkStress() / (capacity == 0 ? 1 : capacity);

        CreateLang.translate("gui.stressometer.title")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        if (getTheoreticalSpeed() == 0) CreateLang.text(TooltipHelper.makeProgressBar(3, 0))
                .translate("gui.stressometer.no_rotation")
                .style(ChatFormatting.DARK_GRAY)
                .forGoggles(tooltip);
        else {
            IRotate.StressImpact.getFormattedStressText(stressFraction).forGoggles(tooltip);
            CreateLang.translate("gui.stressometer.capacity").style(ChatFormatting.GRAY).forGoggles(tooltip);

            var remainingCapacity = capacity - getNetworkStress();
            var su = CreateLang.translate("generic.unit.stress");
            var stressTip = CreateLang.number(remainingCapacity).add(su).style(IRotate.StressImpact.of(stressFraction).getRelativeColor());

            if (remainingCapacity != capacity) stressTip.text(ChatFormatting.GRAY, " / ").add(CreateLang.number(capacity)
                    .add(su)
                    .style(ChatFormatting.DARK_GRAY)
            );

            stressTip.forGoggles(tooltip, 1);
        }
        if (!worldPosition.equals(lastSent)) CatnipServices.NETWORK.sendToServer(new GaugeObservedPacket(lastSent = worldPosition));
        return true;
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        if (clientPacket && worldPosition.equals(lastSent)) lastSent = null;
    }


    public float getNetworkStress() {
        return stress;
    }

    public float getNetworkCapacity() {
        return capacity;
    }

    public void onObserved() {
        award(AllAdvancements.STRESSOMETER);
        if (Mth.equal(stressTarget, 1)) award(AllAdvancements.STRESSOMETER_MAXED);
    }
}