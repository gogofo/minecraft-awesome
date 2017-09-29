package gogofo.minecraft.awesome.entity;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.inventory.ContainerConstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
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
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected boolean canMove(EnumFacing direction) {
        BlockPos below_pos = getPosition().offset(direction).offset(EnumFacing.DOWN);

        return world.getBlockState(below_pos).getCollisionBoundingBox(world, below_pos) != NULL_AABB &&
                super.canMove(direction);
    }

    @Override
    protected void onElectricUpdate() {
        if (ticksExisted % 20 == 0) {
            BlockPos prevPos = getPosition();
            EnumFacing facing = getFacing();
            if (tryMove(facing)) {
                world.setBlockState(prevPos, Blocks.COBBLESTONE.getDefaultState());
            } else {
                setFacing(facing.rotateY());
            }
        }
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
    protected Item getDroppedItem() {
        return Items.burnt_residue;
    }

    @Override
    protected int getOilCapacity() {
        return 10;
    }
}
