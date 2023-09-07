package uwu.lopyluna.create_dd.ponder;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.BeltItemElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.utility.IntAttached;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.foundation.utility.Pointing;
import com.simibubi.create.infrastructure.ponder.scenes.ProcessingScenes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class DDProcessingScenes extends ProcessingScenes {
    
    public static void bulk_pressing(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("hydraulic_press", "Processing Items with the Hydraulic Press");
        scene.configureBasePlate(0, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.idle(5);

        ElementLink<WorldSectionElement> depot =
                scene.world.showIndependentSection(util.select.position(2, 1, 1), Direction.DOWN);
        scene.world.moveSection(depot, util.vector.of(0, 0, 1), 0);
        scene.idle(10);

        Selection pressS = util.select.position(2, 3, 2);
        BlockPos pressPos = util.grid.at(2, 3, 2);
        BlockPos depotPos = util.grid.at(2, 1, 1);
        scene.world.setKineticSpeed(pressS, 0);
        scene.world.showSection(pressS, Direction.DOWN);
        scene.idle(10);

        scene.world.showSection(util.select.fromTo(2, 1, 3, 2, 1, 5), Direction.NORTH);
        scene.idle(3);
        scene.world.showSection(util.select.position(2, 2, 3), Direction.SOUTH);
        scene.idle(3);
        scene.world.showSection(util.select.position(2, 3, 3), Direction.NORTH);
        scene.world.setKineticSpeed(pressS, -32);
        scene.effects.indicateSuccess(pressPos);
        scene.idle(10);

        Vec3 pressSide = util.vector.blockSurface(pressPos, Direction.WEST);
        scene.overlay.showText(60)
                .pointAt(pressSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The Hydraulic Press can process items provided beneath it");
        scene.idle(70);
        scene.overlay.showText(60)
                .pointAt(pressSide.subtract(0, 2, 0))
                .placeNearTarget()
                .text("The Input items can be dropped or placed on a Depot under the Press");
        scene.idle(50);
        ItemStack copper = new ItemStack(Items.COPPER_INGOT);
        scene.world.createItemOnBeltLike(depotPos, Direction.NORTH, copper);
        Vec3 depotCenter = util.vector.centerOf(depotPos.south());
        scene.overlay.showControls(new InputWindowElement(depotCenter, Pointing.UP).withItem(copper), 30);
        scene.idle(10);

        Class<MechanicalPressBlockEntity> type = MechanicalPressBlockEntity.class;
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .start(PressingBehaviour.Mode.BELT));
        scene.idle(30);
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .makePressingParticleEffect(depotCenter.add(0, 8 / 16f, 0), copper));
        scene.world.removeItemsFromBelt(depotPos);
        ItemStack sheet = AllItems.COPPER_SHEET.asStack();
        scene.world.createItemOnBeltLike(depotPos, Direction.UP, sheet);
        scene.idle(10);
        scene.overlay.showControls(new InputWindowElement(depotCenter, Pointing.UP).withItem(sheet), 50);
        scene.idle(60);

        scene.world.hideIndependentSection(depot, Direction.NORTH);
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(0, 1, 3, 0, 2, 3), Direction.DOWN);
        scene.idle(10);
        scene.world.showSection(util.select.fromTo(4, 1, 2, 0, 2, 2), Direction.SOUTH);
        scene.idle(20);
        BlockPos beltPos = util.grid.at(0, 1, 2);
        scene.overlay.showText(40)
                .pointAt(util.vector.blockSurface(beltPos, Direction.WEST))
                .placeNearTarget()
                .attachKeyFrame()
                .text("When items are provided on a belt...");
        scene.idle(30);

        ElementLink<BeltItemElement> ingot = scene.world.createItemOnBelt(beltPos, Direction.SOUTH, copper);
        scene.idle(15);
        ElementLink<BeltItemElement> ingot2 = scene.world.createItemOnBelt(beltPos, Direction.SOUTH, copper);
        scene.idle(15);
        scene.world.stallBeltItem(ingot, true);
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .start(PressingBehaviour.Mode.BELT));

        scene.overlay.showText(50)
                .pointAt(pressSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The Press will hold and process them automatically");

        scene.idle(30);
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .makePressingParticleEffect(depotCenter.add(0, 8 / 16f, 0), copper));
        scene.world.removeItemsFromBelt(pressPos.below(2));
        ingot = scene.world.createItemOnBelt(pressPos.below(2), Direction.UP, sheet);
        scene.world.stallBeltItem(ingot, true);
        scene.idle(15);
        scene.world.stallBeltItem(ingot, false);
        scene.idle(15);
        scene.world.stallBeltItem(ingot2, true);
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .start(PressingBehaviour.Mode.BELT));
        scene.idle(30);
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .makePressingParticleEffect(depotCenter.add(0, 8 / 16f, 0), copper));
        scene.world.removeItemsFromBelt(pressPos.below(2));
        ingot2 = scene.world.createItemOnBelt(pressPos.below(2), Direction.UP, sheet);
        scene.world.stallBeltItem(ingot2, true);
        scene.idle(15);
        scene.world.stallBeltItem(ingot2, false);

    }

    public static void bronze_compacting(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("hydraulic_press_compacting", "Compacting items with the Hydraulic Press");
        scene.configureBasePlate(0, 0, 5);
        scene.world.setBlock(util.grid.at(1, 1, 2), AllBlocks.ANDESITE_CASING.getDefaultState(), false);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(1, 4, 3, 1, 1, 5), Direction.DOWN);
        scene.idle(5);
        scene.world.showSection(util.select.position(1, 1, 2), Direction.DOWN);
        scene.idle(5);
        scene.world.showSection(util.select.position(1, 2, 2), Direction.DOWN);
        scene.idle(5);
        scene.world.showSection(util.select.position(1, 4, 2), Direction.SOUTH);
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(3, 1, 1, 1, 1, 1), Direction.SOUTH);
        scene.world.showSection(util.select.fromTo(3, 1, 5, 3, 1, 2), Direction.SOUTH);
        scene.idle(20);

        BlockPos basin = util.grid.at(1, 2, 2);
        BlockPos pressPos = util.grid.at(1, 4, 2);
        Vec3 basinSide = util.vector.blockSurface(basin, Direction.WEST);

        ItemStack copper = new ItemStack(Items.COPPER_INGOT);
        ItemStack copperBlock = new ItemStack(Items.COPPER_BLOCK);

        scene.overlay.showText(60)
                .pointAt(basinSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Pressing items held in a Basin will cause them to be Compacted");
        scene.idle(40);

        scene.overlay.showControls(new InputWindowElement(util.vector.topOf(basin), Pointing.DOWN).withItem(copper),
                30);
        scene.idle(30);
        Class<MechanicalPressBlockEntity> type = MechanicalPressBlockEntity.class;
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .start(PressingBehaviour.Mode.BASIN));
        scene.idle(30);
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .makeCompactingParticleEffect(util.vector.centerOf(basin), copper));
        scene.world.modifyBlockEntityNBT(util.select.position(basin), BasinBlockEntity.class, nbt -> {
            nbt.put("VisualizedItems",
                    NBTHelper.writeCompoundList(ImmutableList.of(IntAttached.with(1, copperBlock)), ia -> ia.getValue()
                            .serializeNBT()));
        });
        scene.idle(4);
        scene.world.createItemOnBelt(util.grid.at(1, 1, 1), Direction.UP, copperBlock);
        scene.idle(30);

        scene.overlay.showText(80)
                .pointAt(basinSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Compacting includes any filled 2x2 or 3x3 Crafting Recipe, plus a couple extra ones");

        scene.idle(30);
        ItemStack log = new ItemStack(Items.OAK_LOG);
        ItemStack bark = new ItemStack(Items.OAK_WOOD);

        scene.overlay.showControls(new InputWindowElement(util.vector.topOf(basin), Pointing.DOWN).withItem(log), 30);
        scene.idle(30);
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .start(PressingBehaviour.Mode.BASIN));
        scene.idle(30);
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .makeCompactingParticleEffect(util.vector.centerOf(basin), log));
        scene.world.modifyBlockEntityNBT(util.select.position(basin), BasinBlockEntity.class, nbt -> {
            nbt.put("VisualizedItems",
                    NBTHelper.writeCompoundList(ImmutableList.of(IntAttached.with(1, bark)), ia -> ia.getValue()
                            .serializeNBT()));
        });
        scene.idle(4);
        scene.world.createItemOnBelt(util.grid.at(1, 1, 1), Direction.UP, bark);
        scene.idle(30);

        scene.rotateCameraY(-30);
        scene.idle(10);
        scene.world.setBlock(util.grid.at(1, 1, 2), AllBlocks.BLAZE_BURNER.getDefaultState()
                .setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.KINDLED), true);
        scene.idle(10);

        scene.overlay.showText(80)
                .pointAt(basinSide.subtract(0, 1, 0))
                .placeNearTarget()
                .text("Some of those recipe may require the heat of a Blaze Burner");
        scene.idle(40);

        scene.rotateCameraY(30);

        scene.idle(60);
        Vec3 filterPos = util.vector.of(1, 2.75f, 2.5f);
        scene.overlay.showFilterSlotInput(filterPos, Direction.WEST, 100);
        scene.overlay.showText(100)
                .pointAt(filterPos)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The filter slot can be used in case two recipe are conflicting.");
        scene.idle(80);
    }
    
}
