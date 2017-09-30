package gogofo.minecraft.awesome.utils;

import gogofo.minecraft.awesome.interfaces.IConfigurableSidedInventory;
import gogofo.minecraft.awesome.interfaces.IPositionedSidedInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;

public class InventoryUtils {

    public static boolean isEmpty(ItemStack[] itemStackArray) {
        for (ItemStack anItemStackArray : itemStackArray) {
            if (!anItemStackArray.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public static ItemStack decrStackSize(ItemStack[] itemStackArray, int index, int count, IInventory owner) {
        if (!itemStackArray[index].isEmpty())
        {
            ItemStack itemstack;

            if (itemStackArray[index].getCount() <= count)
            {
                itemstack = itemStackArray[index];
                itemStackArray[index] = ItemStack.EMPTY;
                owner.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = itemStackArray[index].splitStack(count);

                if (itemStackArray[index].isEmpty())
                {
                    itemStackArray[index] = ItemStack.EMPTY;
                }

                owner.markDirty();
                return itemstack;
            }
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }

    public static ItemStack removeStackFromSlot(ItemStack[] itemStackArray, int index) {
        if (!itemStackArray[index].isEmpty())
        {
            ItemStack itemstack = itemStackArray[index];
            itemStackArray[index] = ItemStack.EMPTY;
            return itemstack;
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }

    public static void setInventorySlotForContents(ItemStack[] itemStackArray, int index, ItemStack stack, IInventory owner) {
        boolean itemIsSame = !stack.isEmpty() && stack.isItemEqual(itemStackArray[index]) && ItemStack.areItemStackTagsEqual(stack, itemStackArray[index]);
        itemStackArray[index] = stack;

        if (!stack.isEmpty() && stack.getCount() > owner.getInventoryStackLimit())
        {
            stack.setCount(owner.getInventoryStackLimit());
        }

        if (index == 0 && !itemIsSame)
        {
            owner.markDirty();
        }
    }

    public static void clear(ItemStack[] itemStackArray) {
        Arrays.fill(itemStackArray, ItemStack.EMPTY);
    }

    public static void initSlotsForFace(ArrayList<ArrayList<Integer>> slotsForFace, IPositionedSidedInventory owner, boolean defaults) {
        slotsForFace.clear();

        for (EnumFacing face : EnumFacing.values()) {
            slotsForFace.add(new ArrayList<>());

            if (defaults) {
                slotsForFace.get(face.getIndex()).addAll(Arrays.asList(owner.getDefaultSlotForFace(face)));
            }
        }
    }

    public static void readFromNBT(ItemStack[] itemStackArray, ArrayList<ArrayList<Integer>> slotsForFace, IConfigurableSidedInventory owner, NBTTagCompound compound) {
        Arrays.fill(itemStackArray, ItemStack.EMPTY);

        NBTTagList nbttaglist = compound.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbtTagCompound = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbtTagCompound.getByte("Slot");

            if (b0 >= 0 && b0 < itemStackArray.length)
            {

                itemStackArray[b0] = new ItemStack(
                        nbtTagCompound);
            }
        }

        if (compound.hasKey("SlotFacing")) {
            NBTTagCompound nbtTagCompound = compound.getCompoundTag("SlotFacing");
            initSlotsForFace(slotsForFace, owner, false);

            for (EnumFacing face : EnumFacing.values()) {
                int[] slots = nbtTagCompound.getIntArray(face.toString());

                for (int slot : slots) {
                    owner.addSlotToFace(slot, face);
                }
            }
        } else {
            initSlotsForFace(slotsForFace, owner, true);
        }
    }

    public static NBTTagCompound writeToNBT(ItemStack[] itemStackArray, IPositionedSidedInventory owner, NBTTagCompound compound) {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < itemStackArray.length; ++i)
        {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setByte("Slot", (byte)i);
            itemStackArray[i].writeToNBT(nbtTagCompound);
            nbttaglist.appendTag(nbtTagCompound);
        }

        compound.setTag("Items", nbttaglist);


        NBTTagCompound nbtTagCompound = new NBTTagCompound();

        for (EnumFacing face : EnumFacing.values()) {
            nbtTagCompound.setIntArray(face.toString(), owner.getSlotsForFace(face));
        }

        compound.setTag("SlotFacing", nbtTagCompound);

        return compound;
    }

    public static ItemStack findStack(IInventory inventory, Item target, int from) {
        for (int i = from; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);

            if (!stack.isEmpty() &&
                    (stack.getItem() == target || target == null)) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }
}
