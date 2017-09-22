package gogofo.minecraft.awesome.recipe;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.init.Ores;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = AwesomeMod.MODID)
public class RecipeGenericTools {

    @SubscribeEvent
    public static void registerTools(final RegistryEvent.Register<IRecipe> event) {
        final IForgeRegistry<IRecipe> registry = event.getRegistry();

        for (Ores.Ore ore : Ores.getOres()) {
            Ores.Ore.ToolsConfig config = ore.getToolsConfig();
            if (config == null) {
                continue;
            }

            if (config.isHasSword()) {
                addSwordRecipe(registry, ore);
            }

            if (config.isHasWorkingTools()) {
                addPickaxeRecipe(registry, ore);
                addAxeRecipe(registry, ore);
                addHoeRecipe(registry, ore);
                addShovelRecipe(registry, ore);
            }

            if (config.isHasArmors()) {
                addChestplateRecipe(registry, ore);
                addHelmetRecipe(registry, ore);
                addLeggingsRecipe(registry, ore);
                addBootsRecipe(registry, ore);
            }
        }
    }

    private static void addSwordRecipe(IForgeRegistry<IRecipe> registry, Ores.Ore ore) {
        ResourceLocation location = new ResourceLocation(AwesomeMod.MODID, ore.getName() + "_sword_recipe");
        ShapedOreRecipe recipe = new ShapedOreRecipe(location, new ItemStack(ore.getSword()),
                " A ",
                " A ",
                " B ",
                'A', ore.getIngot(),
                'B', net.minecraft.init.Items.STICK);
        recipe.setRegistryName(location);

        registry.register(recipe);
    }

    private static void addPickaxeRecipe(IForgeRegistry<IRecipe> registry, Ores.Ore ore) {
        ResourceLocation location = new ResourceLocation(AwesomeMod.MODID, ore.getName() + "_pickaxe_recipe");
        ShapedOreRecipe recipe = new ShapedOreRecipe(location, new ItemStack(ore.getPickaxe()),
                "AAA",
                " B ",
                " B ",
                'A', ore.getIngot(),
                'B', net.minecraft.init.Items.STICK);
        recipe.setRegistryName(location);

        registry.register(recipe);
    }

    private static void addAxeRecipe(IForgeRegistry<IRecipe> registry, Ores.Ore ore) {
        ResourceLocation location = new ResourceLocation(AwesomeMod.MODID, ore.getName() + "_axe_recipe");
        ShapedOreRecipe recipe = new ShapedOreRecipe(location, new ItemStack(ore.getAxe()),
                "AA ",
                "AB ",
                " B ",
                'A', ore.getIngot(),
                'B', net.minecraft.init.Items.STICK);
        recipe.setRegistryName(location);

        registry.register(recipe);
    }

    private static void addHoeRecipe(IForgeRegistry<IRecipe> registry, Ores.Ore ore) {
        ResourceLocation location = new ResourceLocation(AwesomeMod.MODID, ore.getName() + "_hoe_recipe");
        ShapedOreRecipe recipe = new ShapedOreRecipe(location, new ItemStack(ore.getHoe()),
                "AA ",
                " B ",
                " B ",
                'A', ore.getIngot(),
                'B', net.minecraft.init.Items.STICK);
        recipe.setRegistryName(location);

        registry.register(recipe);
    }

    private static void addShovelRecipe(IForgeRegistry<IRecipe> registry, Ores.Ore ore) {
        ResourceLocation location = new ResourceLocation(AwesomeMod.MODID, ore.getName() + "_shovel_recipe");
        ShapedOreRecipe recipe = new ShapedOreRecipe(location, new ItemStack(ore.getShovel()),
                " A ",
                " B ",
                " B ",
                'A', ore.getIngot(),
                'B', net.minecraft.init.Items.STICK);
        recipe.setRegistryName(location);

        registry.register(recipe);
    }

    private static void addChestplateRecipe(IForgeRegistry<IRecipe> registry, Ores.Ore ore) {
        ResourceLocation location = new ResourceLocation(AwesomeMod.MODID, ore.getName() + "_chestplate_recipe");
        ShapedOreRecipe recipe = new ShapedOreRecipe(location, new ItemStack(ore.getChestplate()),
                "A A",
                "AAA",
                "AAA",
                'A', ore.getIngot());
        recipe.setRegistryName(location);

        registry.register(recipe);
    }

    private static void addHelmetRecipe(IForgeRegistry<IRecipe> registry, Ores.Ore ore) {
        ResourceLocation location = new ResourceLocation(AwesomeMod.MODID, ore.getName() + "_helmet_recipe");
        ShapedOreRecipe recipe = new ShapedOreRecipe(location, new ItemStack(ore.getHelmet()),
                "AAA",
                "A A",
                'A', ore.getIngot());
        recipe.setRegistryName(location);

        registry.register(recipe);
    }

    private static void addLeggingsRecipe(IForgeRegistry<IRecipe> registry, Ores.Ore ore) {
        ResourceLocation location = new ResourceLocation(AwesomeMod.MODID, ore.getName() + "_leggings_recipe");
        ShapedOreRecipe recipe = new ShapedOreRecipe(location, new ItemStack(ore.getLeggings()),
                "AAA",
                "A A",
                "A A",
                'A', ore.getIngot());
        recipe.setRegistryName(location);

        registry.register(recipe);
    }

    private static void addBootsRecipe(IForgeRegistry<IRecipe> registry, Ores.Ore ore) {
        ResourceLocation location = new ResourceLocation(AwesomeMod.MODID, ore.getName() + "_boots_recipe");
        ShapedOreRecipe recipe = new ShapedOreRecipe(location, new ItemStack(ore.getBoots()),
                "A A",
                "A A",
                'A', ore.getIngot());
        recipe.setRegistryName(location);

        registry.register(recipe);
    }
}
