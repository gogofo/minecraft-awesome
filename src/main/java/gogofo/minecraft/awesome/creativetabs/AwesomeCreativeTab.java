package gogofo.minecraft.awesome.creativetabs;

import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class AwesomeCreativeTab extends CreativeTabs {

	public AwesomeCreativeTab(String label) {
		super(label);
	}

	@Override
	public Item getTabIconItem() {
		return Items.drill;
	}
}
