package uwu.lopyluna.create_dd.creative;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import com.simibubi.create.foundation.utility.Components;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.mutable.MutableObject;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.item.DDItems;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings({"unused", "removal", "all"})
public class DDItemTab {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DDCreate.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB = REGISTER.register("base",
            () -> CreativeModeTab.builder()
                    .title(Components.translatable("itemGroup.create_dd.base"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> DDItems.spectral_ruby.asStack())
                    .displayItems(new RegistrateDisplayItemsGenerator(true, DDItemTab.BASE_CREATIVE_TAB))
                    .build());
    
    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }


    private record RegistrateDisplayItemsGenerator(boolean addItems,
                                                   RegistryObject<CreativeModeTab> tabFilter) implements CreativeModeTab.DisplayItemsGenerator {
            private static final Predicate<Item> IS_ITEM_3D_PREDICATE;

            static {
                MutableObject<Predicate<Item>> isItem3d = new MutableObject<>(item -> false);
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    isItem3d.setValue(item -> {
                        ItemRenderer itemRenderer = Minecraft.getInstance()
                                .getItemRenderer();
                        BakedModel model = itemRenderer.getModel(new ItemStack(item), null, null, 0);
                        return model.isGui3d();
                    });
                });
                IS_ITEM_3D_PREDICATE = isItem3d.getValue();
            }

            @OnlyIn(Dist.CLIENT)
            private static Predicate<Item> makeClient3dItemPredicate() {
                return item -> {
                    ItemRenderer itemRenderer = Minecraft.getInstance()
                            .getItemRenderer();
                    BakedModel model = itemRenderer.getModel(new ItemStack(item), null, null, 0);
                    return model.isGui3d();
                };
            }

        private static Predicate<Item> makeExclusionPredicate() {
                Set<Item> exclusions = new ReferenceOpenHashSet<>();

                List<ItemProviderEntry<?>> simpleExclusions = List.of(
                        DDItems.incomplete_crafted_inductive_mechanism1,
                        DDItems.incomplete_crafted_inductive_mechanism2,
                        DDItems.incomplete_crafted_kinetic_mechanism1,
                        DDItems.incomplete_crafted_kinetic_mechanism2,
                        DDItems.incomplete_stargaze_singularity,
                        DDItems.incomplete_integrated_circuit,
                        DDItems.incomplete_integrated_mechanism,
                        DDItems.incomplete_abstruse_mechanism,
                        DDItems.incomplete_calculation_mechanism,
                        DDItems.incomplete_inductive_mechanism,
                        DDItems.incomplete_infernal_mechanism,
                        DDItems.incomplete_sealed_mechanism,
                        DDItems.abstruse_mechanism,
                        DDBlocks.ponder_in_a_box,
                        DDBlocks.POTATO_TURRET,
                        DDBlocks.RADIANT_DRILL,
                        DDBlocks.SHADOW_DRILL,
                        DDBlocks.secondary_adjustable_chain_gearshift,
                        DDBlocks.secondary_encased_chain_drive

                        //ITEMS LIST

                );

                List<ItemEntry<TagDependentIngredientItem>> tagDependentExclusions = List.of(

                        //CUSTOM ITEMS LIST

                );

                List<ItemProviderEntry<?>> craftsAdditionsExclusions = List.of(
                        DDItems.OVERCHARGE_ALLOY,
                        DDItems.OVERCHARGE_ALLOY_SHEET,
                        DDBlocks.OVERCHARGED_SCAFFOLD,
                        DDBlocks.overcharged_alloy_block,
                        DDBlocks.overcharged_casing

                );

                for (ItemProviderEntry<?> entry : simpleExclusions) {
                    exclusions.add(entry.asItem());
                }

                for (ItemEntry<TagDependentIngredientItem> entry : tagDependentExclusions) {
                    TagDependentIngredientItem item = entry.get();
                    if (item.shouldHide()) {
                        exclusions.add(entry.asItem());
                    }
                }

                for (ItemProviderEntry<?> entry : craftsAdditionsExclusions) {
                    if (!ModList.get().isLoaded("createaddition")) {
                    exclusions.add(entry.asItem());
                    }
                }

                return exclusions::contains;
            }

            private static List<RegistrateDisplayItemsGenerator.ItemOrdering> makeOrderings() {
                List<RegistrateDisplayItemsGenerator.ItemOrdering> orderings = new ReferenceArrayList<>();

                Map<ItemProviderEntry<?>, ItemProviderEntry<?>> simpleBeforeOrderings = Map.of(
                );

                Map<ItemProviderEntry<?>, ItemProviderEntry<?>> simpleAfterOrderings = Map.of(
                );

                simpleBeforeOrderings.forEach((entry, otherEntry) -> {
                    orderings.add(RegistrateDisplayItemsGenerator.ItemOrdering.before(entry.asItem(), otherEntry.asItem()));
                });

                simpleAfterOrderings.forEach((entry, otherEntry) -> {
                    orderings.add(RegistrateDisplayItemsGenerator.ItemOrdering.after(entry.asItem(), otherEntry.asItem()));
                });

                return orderings;
            }

            private static Function<Item, ItemStack> makeStackFunc() {
                Map<Item, Function<Item, ItemStack>> factories = new Reference2ReferenceOpenHashMap<>();

                Map<ItemProviderEntry<?>, Function<Item, ItemStack>> simpleFactories = Map.of(
                );

                simpleFactories.forEach((entry, factory) -> {
                    factories.put(entry.asItem(), factory);
                });

                return item -> {
                    Function<Item, ItemStack> factory = factories.get(item);
                    if (factory != null) {
                        return factory.apply(item);
                    }
                    return new ItemStack(item);
                };
            }

            private static Function<Item, CreativeModeTab.TabVisibility> makeVisibilityFunc() {
                Map<Item, CreativeModeTab.TabVisibility> visibilities = new Reference2ObjectOpenHashMap<>();

                Map<ItemProviderEntry<?>, CreativeModeTab.TabVisibility> simpleVisibilities = Map.of(
                );

                simpleVisibilities.forEach((entry, factory) -> {
                    visibilities.put(entry.asItem(), factory);
                });


                return item -> {
                    CreativeModeTab.TabVisibility visibility = visibilities.get(item);
                    if (visibility != null) {
                        return visibility;
                    }
                    return CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
                };
            }

            @Override
            public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
                Predicate<Item> exclusionPredicate = makeExclusionPredicate();
                List<RegistrateDisplayItemsGenerator.ItemOrdering> orderings = makeOrderings();
                Function<Item, ItemStack> stackFunc = makeStackFunc();
                Function<Item, CreativeModeTab.TabVisibility> visibilityFunc = makeVisibilityFunc();

                List<Item> items = new LinkedList<>();
                if (addItems) {
                    items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE.negate())));
                }
                items.addAll(collectBlocks(exclusionPredicate));
                if (addItems) {
                    items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE)));
                }

                applyOrderings(items, orderings);
                outputAll(output, items, stackFunc, visibilityFunc);
            }

            private List<Item> collectBlocks(Predicate<Item> exclusionPredicate) {
                List<Item> items = new ReferenceArrayList<>();
                for (RegistryEntry<Block> entry : DDCreate.REGISTRATE.getAll(Registries.BLOCK)) {
                    if (!CreateRegistrate.isInCreativeTab(entry, tabFilter))
                        continue;
                    Item item = entry.get()
                            .asItem();
                    if (item == Items.AIR)
                        continue;
                    if (!exclusionPredicate.test(item))
                        items.add(item);
                }
                items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
                return items;
            }

            private List<Item> collectItems(Predicate<Item> exclusionPredicate) {
                List<Item> items = new ReferenceArrayList<>();
                for (RegistryEntry<Item> entry : DDCreate.REGISTRATE.getAll(Registries.ITEM)) {
                    if (!CreateRegistrate.isInCreativeTab(entry, tabFilter))
                        continue;
                    Item item = entry.get();
                    if (item instanceof BlockItem)
                        continue;
                    if (!exclusionPredicate.test(item))
                        items.add(item);
                }
                return items;
            }

            private static void applyOrderings(List<Item> items, List<RegistrateDisplayItemsGenerator.ItemOrdering> orderings) {
                for (RegistrateDisplayItemsGenerator.ItemOrdering ordering : orderings) {
                    int anchorIndex = items.indexOf(ordering.anchor());
                    if (anchorIndex != -1) {
                        Item item = ordering.item();
                        int itemIndex = items.indexOf(item);
                        if (itemIndex != -1) {
                            items.remove(itemIndex);
                            if (itemIndex < anchorIndex) {
                                anchorIndex--;
                            }
                        }
                        if (ordering.type() == RegistrateDisplayItemsGenerator.ItemOrdering.Type.AFTER) {
                            items.add(anchorIndex + 1, item);
                        } else {
                            items.add(anchorIndex, item);
                        }
                    }
                }
            }

            private static void outputAll(CreativeModeTab.Output output, List<Item> items, Function<Item, ItemStack> stackFunc, Function<Item, CreativeModeTab.TabVisibility> visibilityFunc) {
                for (Item item : items) {
                    output.accept(stackFunc.apply(item), visibilityFunc.apply(item));
                }
            }

            private record ItemOrdering(Item item, Item anchor, RegistrateDisplayItemsGenerator.ItemOrdering.Type type) {
                public static RegistrateDisplayItemsGenerator.ItemOrdering before(Item item, Item anchor) {
                    return new RegistrateDisplayItemsGenerator.ItemOrdering(item, anchor, RegistrateDisplayItemsGenerator.ItemOrdering.Type.BEFORE);
                }

                public static RegistrateDisplayItemsGenerator.ItemOrdering after(Item item, Item anchor) {
                    return new RegistrateDisplayItemsGenerator.ItemOrdering(item, anchor, RegistrateDisplayItemsGenerator.ItemOrdering.Type.AFTER);
                }

                public enum Type {
                    BEFORE,
                    AFTER;
                }
            }
        }
}
