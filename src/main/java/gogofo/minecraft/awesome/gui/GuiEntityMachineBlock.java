package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.entity.EntityMachineBlock;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiEntityMachineBlock extends AwesomeGui{
    protected final EntityMachineBlock entity;
    protected InventoryPlayer playerInventory;

    public GuiEntityMachineBlock(InventoryPlayer playerInventory,
                                 EntityMachineBlock entity)
    {
        super(entity.createContainer(playerInventory, playerInventory.player),
                playerInventory,
                entity);

        this.playerInventory = playerInventory;
        this.entity = entity;
    }

    @Override
    protected void drawCustomGui() {
        drawSlotsByCustomContainer(entity.createContainer(playerInventory, playerInventory.player));

        drawGlassContainer(80, 30, 50);
    }
}
