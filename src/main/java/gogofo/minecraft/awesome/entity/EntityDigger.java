package gogofo.minecraft.awesome.entity;

import gogofo.minecraft.awesome.PerimeterManager;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.inventory.ContainerDigger;
import gogofo.minecraft.awesome.utils.InventoryUtils;
import javafx.util.Pair;
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
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;

import static gogofo.minecraft.awesome.gui.GuiEnum.E_DIGGER;
import static net.minecraft.block.Block.NULL_AABB;

public class EntityDigger extends EntityMachineBlock {

    public static final int INVENTORY_SLOTS = 27;
    public static final int ATTACHMENT_SLOTS = 9;

    private final int ATTACHMENT_START_INDEX = super.getSlotCount() + INVENTORY_SLOTS;
    private final int ATTACHMENT_MAX_INDEX = ATTACHMENT_START_INDEX + ATTACHMENT_SLOTS - 1;
    int nextAttachmentIndex = ATTACHMENT_START_INDEX;

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

            Pair<Integer, Boolean> nextToolResult = getNextAttachment();

            Integer toolIndex = nextToolResult.getKey();
            Boolean finalAttachment = nextToolResult.getValue();

            tryDestroyBlock(getStackInSlot(toolIndex), getDiggingPositionByAttachmentIndex(toolIndex));

            if (finalAttachment) {
                if (tryMove(facing)) {

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

    /**
     * @return attachment stack index (can be an empty stack) and true if its the final attachment, false otherwise
     */
    private Pair<Integer, Boolean> getNextAttachment() {
        while (getStackInSlot(nextAttachmentIndex).isEmpty() && nextAttachmentIndex < ATTACHMENT_MAX_INDEX) {
            nextAttachmentIndex += 1;
        }

        int retIndex = nextAttachmentIndex;
        boolean isFinal = false;

        if (nextAttachmentIndex == ATTACHMENT_MAX_INDEX) {
            nextAttachmentIndex = ATTACHMENT_START_INDEX;
            isFinal = true;
        } else {
            nextAttachmentIndex += 1;
        }

        return new Pair<>(retIndex, isFinal);
    }

    private BlockPos getDiggingPositionByAttachmentIndex(int index) {
        index -= ATTACHMENT_START_INDEX;
        int y = 2 - (index / 3);
        int xz = index % 3 - 1;

        return getPosition().offset(getFacing())
                    .offset(EnumFacing.EAST, getFacing().getFrontOffsetZ() * xz)
                    .offset(EnumFacing.SOUTH, getFacing().getFrontOffsetX() * xz)
                    .offset(EnumFacing.UP, y);
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

    private boolean tryDestroyBlock(ItemStack attachmentStack, BlockPos targetPos) {

        BlockPos front = getPosition().offset(getFacing());

        if (PerimeterManager.instance.hasPerimeter(front)) {
            return false;
        }

        if (!attachmentStack.isEmpty()) {
            IBlockState targetBlockState = world.getBlockState(targetPos);

            if (ForgeHooks.canToolHarvestBlock(world, targetPos, attachmentStack)) {
                harvestBlock(targetPos, targetBlockState, targetBlockState.getBlock());
                attachmentStack.setItemDamage(attachmentStack.getItemDamage() + 1);
                return true;
            }
        }

        return false;
    }

    private void harvestBlock(BlockPos front, IBlockState blockState, Block block) {
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
    }
}
