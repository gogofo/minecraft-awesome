package gogofo.minecraft.awesome.entity;

import gogofo.minecraft.awesome.PerimeterManager;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.inventory.ContainerConstructor;
import gogofo.minecraft.awesome.utils.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static gogofo.minecraft.awesome.gui.GuiEnum.E_CONSTRUCTOR;
import static net.minecraft.block.Block.NULL_AABB;

public class EntityConstructor extends EntityMachineBlock {

    public EntityConstructor(World world) {
        super(world);
    }

    public EntityConstructor(World worldIn, BlockPos pos) {
        super(worldIn, pos);
    }

    @Override
    protected boolean canMove(EnumFacing direction) {
        BlockPos below_pos = getPosition().offset(direction).offset(EnumFacing.DOWN);

        return world.getBlockState(below_pos).getCollisionBoundingBox(world, below_pos) != NULL_AABB &&
                super.canMove(direction);
    }

    @Override
    protected int[] onElectricUpdate() {
        int oilUsed = 0;
        int chargeUsed = 0;

        if (ticksExisted % 20 == 0) {
            EnumFacing facing = getFacing();

            if (!getBlockToPlace().isEmpty()) {
                if (tryPlaceBlock() && tryMove(facing)) {
                    chargeUsed += 10;
                    oilUsed += 3;
                } else {
                    setFacing(facing.rotateY());
                    chargeUsed += 1;
                    oilUsed += 1;
                }
            }
        }

        if (ticksExisted % 5 == 0) {
            oilUsed = Math.max(1, oilUsed);
        }

        return new int[] {chargeUsed, oilUsed};
    }

    @Override
    protected GuiEnum getGui() {
        return E_CONSTRUCTOR;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerConstructor(playerInventory, this);
    }

    @Override
    public int getSlotCount() {
        return super.getSlotCount() + 27;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index < super.getSlotCount()) {
            return super.isItemValidForSlot(index, stack);
        } else {
            return true;
        }
    }

    @Override
    protected Item getDroppedItem() {
        return Items.constructor;
    }

    @Override
    public int getOilCapacity() {
        return 10000;
    }

    private boolean tryPlaceBlock() {
        BlockPos below_pos = getPosition().offset(getFacing()).offset(EnumFacing.DOWN);

        if (world.getBlockState(below_pos).getCollisionBoundingBox(world, below_pos) != NULL_AABB ||
                !world.getBlockState(below_pos).getBlock().isReplaceable(world, below_pos) ||
                PerimeterManager.instance.hasPerimeter(below_pos)) {
            return false;
        }

        ItemStack stack = getBlockToPlace();
        if (stack.isEmpty()) {
            return false;
        }

        Block block = Block.getBlockFromItem(stack.getItem());
        IBlockState state = block.getStateFromMeta(stack.getItemDamage());

        world.setBlockState(below_pos, state);
        stack.shrink(1);
        SoundType soundType = block.getSoundType(state, world, below_pos, this);
        world.playSound(null, below_pos, soundType.getPlaceSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);

        return true;
    }

    private ItemStack getBlockToPlace() {
        ItemStack stack = InventoryUtils.findStack(this, null, super.getSlotCount());
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        Block block = Block.getBlockFromItem(stack.getItem());
        if (block == Blocks.AIR) {
            return ItemStack.EMPTY;
        }

        return stack;
    }
}
