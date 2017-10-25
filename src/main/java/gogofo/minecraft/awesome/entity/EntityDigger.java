package gogofo.minecraft.awesome.entity;

import gogofo.minecraft.awesome.PerimeterManager;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.inventory.ContainerDigger;
import gogofo.minecraft.awesome.utils.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static gogofo.minecraft.awesome.gui.GuiEnum.E_DIGGER;
import static net.minecraft.block.Block.NULL_AABB;

public class EntityDigger extends EntityMachineBlock {

    public static final int INVENTORY_SLOTS = 27;
    public static final int ATTACHMENT_SLOTS = 9;

    public EntityDigger(World world) {
        super(world);
    }

    public EntityDigger(World worldIn, BlockPos pos) {
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

            if (tryDestroyBlocks() && tryMove(facing)) {
                chargeUsed += 10;
                oilUsed += 3;
            } else {
                setFacing(facing.rotateY());
                chargeUsed += 1;
                oilUsed += 1;
            }
        }

        if (ticksExisted % 5 == 0) {
            oilUsed = Math.max(1, oilUsed);
        }

        return new int[] {chargeUsed, oilUsed};
    }

    @Override
    protected GuiEnum getGui() {
        return E_DIGGER;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerDigger(playerInventory, this);
    }

    @Override
    public int getSlotCount() {
        return super.getSlotCount() + INVENTORY_SLOTS + ATTACHMENT_SLOTS;
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
        return Items.digger;
    }

    @Override
    public int getOilCapacity() {
        return 10000;
    }

    private boolean tryDestroyBlocks() {

        BlockPos front = getPosition().offset(getFacing());
        IBlockState blockState = world.getBlockState(front);
        Block block = blockState.getBlock();

        if (PerimeterManager.instance.hasPerimeter(front)) {
            return false;
        }

        NonNullList<ItemStack> drops = NonNullList.create();
        block.getDrops(drops, world, front, blockState, 0);

        ArrayList<ItemStack> leftovers = new ArrayList<>();

        for (ItemStack drop : drops) {
            ItemStack stack = InventoryUtils.mergeStack(this, drop, super.getSlotCount(), super.getSlotCount() + INVENTORY_SLOTS);
            if (!stack.isEmpty()) {
                leftovers.add(stack);
            }
        }

        world.setBlockToAir(front);

        for (ItemStack drop : leftovers) {
            BlockPos dropZone = getPosition().offset(getFacing().getOpposite());
            world.spawnEntity(new EntityItem(world, dropZone.getX(), dropZone.getY(), dropZone.getZ(), drop));
        }

        if (block != Blocks.AIR) {
            SoundType soundType = block.getSoundType(blockState, world, front, this);
            world.playSound(null, front, soundType.getBreakSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
        }

        return true;
    }
}
