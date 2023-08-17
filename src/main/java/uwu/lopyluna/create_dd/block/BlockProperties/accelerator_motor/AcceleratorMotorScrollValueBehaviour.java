package uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.content.kinetics.motor.KineticScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsFormatter;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public class AcceleratorMotorScrollValueBehaviour extends KineticScrollValueBehaviour {
    public AcceleratorMotorScrollValueBehaviour(Component label, SmartBlockEntity be, ValueBoxTransform slot) {
        super(label, be, slot);
    }

    @Override
    public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
        ImmutableList<Component> rows = ImmutableList.of(Components.literal("\u27f3")
                        .withStyle(ChatFormatting.BOLD),
                Components.literal("\u27f2")
                        .withStyle(ChatFormatting.BOLD));
        ValueSettingsFormatter formatter = new ValueSettingsFormatter(this::formatSettings);
        return new ValueSettingsBoard(label, 256, 16, rows, formatter);
    }
}
