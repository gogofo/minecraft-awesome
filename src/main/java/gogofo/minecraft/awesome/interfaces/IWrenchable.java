package gogofo.minecraft.awesome.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWrenchable {
    void onWrenchRightClicked(EntityPlayer player, ItemStack wrench);
}
