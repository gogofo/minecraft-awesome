package gogofo.minecraft.awesome.block;

import java.util.Random;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.tileentity.TileEntityCharger;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCharger extends AwesomeBlockRunningMachine implements IElectricalBlock {

	public BlockCharger() {
		super(Material.IRON);
		
		this.setHardness(1.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCharger();
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.charger);
    }
	
	@Override
	protected int getGuiId() {
		return GuiEnum.CHARGER.ordinal();
	}
}
