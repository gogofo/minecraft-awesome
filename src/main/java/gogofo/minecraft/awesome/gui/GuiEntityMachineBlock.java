package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.entity.EntityMachineBlock;
import gogofo.minecraft.awesome.init.Fluids;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiEntityMachineBlock extends AwesomeGui {
    public static final int OIL_X = 30;
    public static final int OIL_Y = 18;
    public static final int OIL_HEIGHT = 45;

    public static final int GLASS_X = OIL_X + 2;
    public static final int GLASS_Y = OIL_Y + 2;
    public static final int GLASS_HEIGHT = OIL_HEIGHT - 4;
    public static final int GLASS_WIDTH = 16;

    public static final int CLEAR_HEIGHT = 140;

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

        float fill = entity.getOilAmount() * 1.0f / entity.getOilCapacity();

        drawRect(guiX() + GLASS_X,
                guiY() + GLASS_Y,
                guiX() + GLASS_X + GLASS_WIDTH,
                guiY() + GLASS_Y + GLASS_HEIGHT,
                0xFF8B8B8B);

        drawRect(guiX() + GLASS_X,
                guiY() + GLASS_Y + Math.round(GLASS_HEIGHT * (1-fill)),
                guiX() + GLASS_X + GLASS_WIDTH,
                guiY() + GLASS_Y + GLASS_HEIGHT,
                Fluids.oil.getColor());

        drawGlassContainer(OIL_X, OIL_Y, OIL_HEIGHT);
    }

    @Override
    protected int getClearSectionHeight() {
        return CLEAR_HEIGHT;
    }

    @Override
    protected int getInboxStartY() {
        return CLEAR_HEIGHT;
    }
}
