package uwu.lopyluna.create_dd.content.items.exp;

import com.simibubi.create.content.materials.ExperienceNuggetItem;
import com.simibubi.create.foundation.utility.VecHelper;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ExperienceNuggetItemOne extends ExperienceNuggetItem {
	public ExperienceNuggetItemOne(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);
		if (pLevel.isClientSide) {
			pLevel.playSound(pPlayer, pPlayer.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS,
					.6f, 1.25f);
			pLevel.playSound(pPlayer, pPlayer.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS,
					.75f, 0.5f);
			return InteractionResultHolder.consume(itemInHand);
		}

		int amountUsed = pPlayer.isSteppingCarefully() ? 1 : itemInHand.getCount();
		int total = Mth.ceil(27f * amountUsed);
		int maxOrbs = amountUsed == 1 ? 1 : 5;
		int valuePer = Math.max(1, 1 + total / maxOrbs);

		for (int i = 0; i < maxOrbs; i++) {
			int value = Math.min(valuePer, total - i * valuePer);
			if (value == 0)
				continue;

			Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, pLevel.random, 1)
					.normalize();
			Vec3 look = pPlayer.getLookAngle();
			Vec3 motion = look.scale(0.2)
					.add(0, 0.2, 0)
					.add(offset.scale(.1));
			Vec3 cross = look.cross(VecHelper.rotate(new Vec3(-.75f, 0, 0), -pPlayer.getYRot(), Direction.Axis.Y));

			Vec3 global = offset.add(pPlayer.getPosition(1));
			global = pPlayer.getEyePosition()
					.add(look.scale(.5f))
					.add(cross);
			ExperienceOrb xp = new ExperienceOrb(pLevel, global.x, global.y, global.z, value);
			xp.setDeltaMovement(motion);
			pLevel.addFreshEntity(xp);
		}

		itemInHand.shrink(amountUsed);
		if (!itemInHand.isEmpty())
			return InteractionResultHolder.success(itemInHand);

		pPlayer.setItemInHand(pUsedHand, ItemStack.EMPTY);
		return InteractionResultHolder.consume(itemInHand);
	}
}
