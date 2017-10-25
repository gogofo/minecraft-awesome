package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.entity.EntityDigger;
import gogofo.minecraft.awesome.gui.GuiEntityMachineBlock;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerDigger extends ContainerEntityMachineBlock {
    private final EntityDigger entity;

    public ContainerDigger(InventoryPlayer inventoryPlayer, EntityDigger entity) {
    	super(inventoryPlayer, entity);

    	this.entity = entity;

    	int DIGGER_INVENTORY_START = GuiEntityMachineBlock.CLEAR_HEIGHT - 3*18 - 18;

        addRegularSlotGrid(super.getCustomSlotCount(), 8, DIGGER_INVENTORY_START, 3, 9);
        addSlotGrid(super.getCustomSlotCount() + 3*9, 115, 8, 3, 3, SlotDiggerAttachment::new);
    }

    @Override
    protected int getCustomSlotCount() {
        return entity.getSlotCount();
    }
}
