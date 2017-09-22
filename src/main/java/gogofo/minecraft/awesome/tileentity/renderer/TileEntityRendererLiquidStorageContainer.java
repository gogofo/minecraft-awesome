package gogofo.minecraft.awesome.tileentity.renderer;

import gogofo.minecraft.awesome.tileentity.TileEntityLiquidStorageContainer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fluids.BlockFluidClassic;

public class TileEntityRendererLiquidStorageContainer extends FastTESR<TileEntityLiquidStorageContainer> {

    @Override
    public void renderTileEntityFast(TileEntityLiquidStorageContainer te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        final float PX = 1f / 16f;

        // Y
        final float DOWN = 1 * PX;
        final float UP = (15 - 12) * PX;

        // Z
        final float NORTH = 1 * PX;
        final float SOUTH = 15 * PX;

        // X
        final float WEST = 1 * PX;
        final float EAST = 15 * PX;

        Block liquidBlock = gogofo.minecraft.awesome.init.Blocks.oil;

        BlockModelShapes bm = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
        TextureAtlasSprite still =  bm.getTexture(liquidBlock.getDefaultState());
        TextureAtlasSprite flow =  bm.getTexture(liquidBlock.getDefaultState());

        // Lightmap calculations
        int upCombined = getWorld().getCombinedLight(te.getPos().up(), 0);
        int upLMa = upCombined >> 16 & 65535;
        int upLMb = upCombined & 65535;

        int northCombined = getWorld().getCombinedLight(te.getPos().add(new BlockPos(0,0,1)), 0);
        int northLMa = northCombined >> 16 & 65535;
        int northLMb = northCombined & 65535;

        int southCombined = getWorld().getCombinedLight(te.getPos().add(new BlockPos(0,0,-1)), 0);
        int southLMa = southCombined >> 16 & 65535;
        int southLMb = southCombined & 65535;

        int westCombined = getWorld().getCombinedLight(te.getPos().add(new BlockPos(-1,0,0)), 0);
        int westLMa = westCombined >> 16 & 65535;
        int westLMb = westCombined & 65535;

        int eastCombined = getWorld().getCombinedLight(te.getPos().add(new BlockPos(1,0,0)), 0);
        int eastLMa = eastCombined >> 16 & 65535;
        int eastLMb = eastCombined & 65535;

        buffer.setTranslation(x, y, z);

        // UP face
        finish(liquidBlock, buffer.pos(WEST, UP, NORTH), still.getMinU(), still.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, UP, NORTH), still.getMaxU(), still.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, UP, SOUTH), still.getMaxU(), still.getMaxV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(WEST, UP, SOUTH), still.getMinU(), still.getMaxV(), upLMa, upLMb);

        // DOWN face
        finish(liquidBlock, buffer.pos(WEST, DOWN, NORTH), still.getMinU(), still.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, DOWN, NORTH), still.getMaxU(), still.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, DOWN, SOUTH), still.getMaxU(), still.getMaxV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(WEST, DOWN, SOUTH), still.getMinU(), still.getMaxV(), upLMa, upLMb);

        // NORTH face

        finish(liquidBlock, buffer.pos(WEST, UP, SOUTH), flow.getMinU(), flow.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, UP, SOUTH), flow.getMaxU(), flow.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, DOWN, SOUTH), flow.getMaxU(), flow.getMaxV(), northLMa, northLMb);
        finish(liquidBlock, buffer.pos(WEST, DOWN, SOUTH), flow.getMinU(), flow.getMaxV(), northLMa, northLMb);

        // SOUTH face
        finish(liquidBlock, buffer.pos(WEST, UP, NORTH), flow.getMinU(), flow.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, UP, NORTH), flow.getMaxU(), flow.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, DOWN, NORTH), flow.getMaxU(), flow.getMaxV(), southLMa, southLMb);
        finish(liquidBlock, buffer.pos(WEST, DOWN, NORTH), flow.getMinU(), flow.getMaxV(), southLMa, southLMb);

        // WEST face
        finish(liquidBlock, buffer.pos(WEST, UP, NORTH), flow.getMinU(), flow.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(WEST, UP, SOUTH), flow.getMaxU(), flow.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(WEST, DOWN, SOUTH), flow.getMaxU(), flow.getMaxV(), westLMa, westLMb);
        finish(liquidBlock, buffer.pos(WEST, DOWN, NORTH), flow.getMinU(), flow.getMaxV(), westLMa, westLMb);

        // EAST face
        finish(liquidBlock, buffer.pos(EAST, UP, NORTH), flow.getMinU(), flow.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, UP, SOUTH), flow.getMaxU(), flow.getMinV(), upLMa, upLMb);
        finish(liquidBlock, buffer.pos(EAST, DOWN, SOUTH), flow.getMaxU(), flow.getMaxV(), eastLMa, eastLMb);
        finish(liquidBlock, buffer.pos(EAST, DOWN, NORTH), flow.getMinU(), flow.getMaxV(), eastLMa, eastLMb);
    }

    private void finish(Block liquidBlock, BufferBuilder buffer, float u, float v, int LMa, int LMb) {

        int color = 0xF0FFFFFF;

        if (liquidBlock instanceof BlockFluidClassic) {
            color = ((BlockFluidClassic)liquidBlock).getFluid().getColor();
        }

        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = ((color) & 0xFF) / 255f;
        buffer.color(r,g,b,a).tex(u, v).lightmap(LMa,LMb).endVertex();
    }
}
