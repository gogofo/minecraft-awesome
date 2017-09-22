package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.init.Fluids;
import gogofo.minecraft.awesome.init.Materials;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOil extends BlockFluidClassic {
    public BlockOil() {
        super(Fluids.oil, Materials.OIL);

        Blocks.FIRE.setFireInfo(this, 60, 300);
    }

    @SideOnly(Side.CLIENT)
    public void render() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
    }
}
