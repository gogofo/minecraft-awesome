package gogofo.minecraft.awesome.item;

import com.google.common.collect.Sets;
import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import javax.annotation.Nullable;
import java.util.Set;

public class ItemAwesomePickaxeShovel extends ItemTool implements ISingleColoredObject {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK,
                                                                   Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE,
                                                                   Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE,
                                                                   Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE,
                                                                   Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.CLAY,
                                                                   Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER,
                                                                   Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER);

    private final int color;
    private final ItemAwesomePickaxe pickaxe;
    private final ItemAwesomeShovel shovel;

    public ItemAwesomePickaxeShovel(ToolMaterial material, int color) {
        super(1.0F, -2.8F, material, EFFECTIVE_ON);
        this.color = color;

        this.pickaxe = new ItemAwesomePickaxe(material, color);
        this.shovel = new ItemAwesomeShovel(material, color);
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return pickaxe.canHarvestBlock(blockIn) || shovel.canHarvestBlock(blockIn);
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        return Math.max(pickaxe.getStrVsBlock(stack, state), shovel.getStrVsBlock(stack, state));
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        return Math.max(pickaxe.getHarvestLevel(stack, toolClass, player, blockState), shovel.getHarvestLevel(stack, toolClass, player, blockState));
    }
}
