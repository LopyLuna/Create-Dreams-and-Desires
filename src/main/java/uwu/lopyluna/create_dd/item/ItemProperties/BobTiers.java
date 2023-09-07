package uwu.lopyluna.create_dd.item.ItemProperties;

import com.simibubi.create.AllItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.item.DDItems;

public class BobTiers {
    public static final Tier Ravager = new Tier()
    {
        @Override
        public int getUses() {
            return 4096;
        }

        @Override
        public float getSpeed() {
            return 32.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 5.5F;
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
            return Ingredient.of(DDItems.mithril_ingot.get());
        }
    };

    public static final Tier Deforester = new Tier()
    {
        @Override
        public int getUses() {
            return 1024;
        }

        @Override
        public float getSpeed() {
            return 2F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 4F;
        }

        @Override
        public int getLevel() {
            return 3;
        }

        @Override
        public int getEnchantmentValue() {
            return 20;
        }

        @NotNull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(AllItems.ANDESITE_ALLOY.get());
        }
    };
}