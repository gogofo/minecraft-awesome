package gogofo.minecraft.awesome.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlock extends ModelBase {

    private ModelRenderer cube;

    public ModelBlock() {
        cube = new ModelRenderer(this);
        cube.addBox(0, 0, 0, 16, 16, 16);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        cube.render(scale);
    }
}
