package uwu.lopyluna.create_flavored.item.ItemProperties;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_flavored.item.Pipebomb;

public class BobTiers {
    public static final Tier MITHRIL = new Tier()
    {
        @Override
        public int getUses() {
            return 16384;
        }

        @Override
        public float getSpeed() {
            return 32.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 4.5F;
        }

        @Override
        public int getLevel() {
            return 5;
        }

        @Override
        public int getEnchantmentValue() {
            return 25;
        }

        @NotNull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Pipebomb.mithril_ingot.get());
        }
    };
}