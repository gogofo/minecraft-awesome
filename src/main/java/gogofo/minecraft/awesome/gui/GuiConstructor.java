package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.inventory.ContainerConstructor;
import gogofo.minecraft.awesome.inventory.ContainerFuser;
import gogofo.minecraft.awesome.tileentity.TileEntityConstructor;
import gogofo.minecraft.awesome.tileentity.TileEntityFuser;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiConstructor extends AwesomeGuiWithControls {
    private final TileEntityConstructor tileEntityConstructor;
    private InventoryPlayer playerInventory;

    public GuiConstructor(InventoryPlayer playerInventory,
                          TileEntityConstructor tileEntityConstructor)
    {
        super(new ContainerConstructor(playerInventory, tileEntityConstructor),
                playerInventory,
                tileEntityConstructor);
        
        this.playerInventory = playerInventory;
        this.tileEntityConstructor = tileEntityConstructor;
    }

    @Override
    protected void drawCustomGui() {
    }
}
