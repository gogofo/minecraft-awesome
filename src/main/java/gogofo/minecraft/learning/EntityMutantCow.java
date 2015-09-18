package gogofo.minecraft.learning;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityMutantCow extends EntityAnimal {

	public EntityMutantCow(World worldIn) {
		super(worldIn);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityMutantCow(worldObj);
	}

}
