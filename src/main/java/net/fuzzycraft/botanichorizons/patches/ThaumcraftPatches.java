package net.fuzzycraft.botanichorizons.patches;

import net.fuzzycraft.botanichorizons.mod.ForgeMod;
import net.fuzzycraft.botanichorizons.util.Constants;
import net.fuzzycraft.botanichorizons.util.ResearchBuilder;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchPage;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibOreDict;

import java.util.LinkedList;
import java.util.List;

public class ThaumcraftPatches {
    public static void applyPatches() {

        // make the research page
        ResearchCategories.registerCategory(
                ResearchBuilder.category,
                new ResourceLocation("botania", "textures/items/grassSeeds0.png"),
                new ResourceLocation(ForgeMod.MOD_ID, "textures/tc_bg.png")
        );

        // MV method of upgrading shrooms into flowers
        new ResearchBuilder("FLOWERS")
                .setBookLocation(0,3)
                .setResearchIconItem("botania", "grassSeeds0.png")
                .setResearchAspects(Aspect.PLANT, Aspect.EXCHANGE, Aspect.MAGIC)
                .setDifficulty(1)
                .addSingleTextPage()
                .apply(builder -> {
                    for (int i = 0; i < 16; i++) {
                        builder.addCrucibleRecipe(
                            ThaumcraftAspects.FLOWER,
                            new ItemStack(ModBlocks.flower, 1, i),
                            new ItemStack(ModBlocks.mushroom, 2, i)
                        );
                    }
                }).commit();

        // MV method of duplicating flowers
        new ResearchBuilder("FLOWERDUPE")
            .setBookLocation(-2,3)
            .setResearchIconItem("botania", "grassSeeds1.png")
            .setResearchAspects(Aspect.PLANT, Aspect.SENSES, Aspect.MAGIC)
            .setDifficulty(1)
            .addSingleTextPage()
            .apply(builder -> {
                for (int i = 0; i < 16; i++) {
                    builder.addCrucibleRecipe(
                        ThaumcraftAspects.FLOWER,
                        new ItemStack(ModBlocks.flower, 2, i),
                        new ItemStack(ModBlocks.flower, 1, i)
                    );
                }
            }).commit();

        // HV method of mutating flowers
        new ResearchBuilder("FLOWERCOLOUR")
            .setBookLocation(2,3)
            .setResearchAspects(Aspect.PLANT, Aspect.EXCHANGE, Aspect.SENSES, Aspect.CRAFT)
            .setDifficulty(2)
            .addSingleTextPage()
            .apply(builder -> {
                for (int i = 0; i < 16; i++) {
                    ItemStack dye = new ItemStack(Items.dye, 1, 15 - i); // vanilla dyes sort the other way
                    builder.addInfusionRecipe(
                        new AspectList().add(Aspect.PLANT, 8).add(Aspect.EXCHANGE, 8).add(Aspect.SENSES, 16),
                        new ItemStack(ModBlocks.flower, 1, i),
                        5,
                        new ItemStack(ModBlocks.flower, 1, OreDictionary.WILDCARD_VALUE),
                        dye, dye, dye, dye
                    );
                }
            })
            .commit();

        // Runic altar
        new ResearchBuilder("ALTAR")
            .setBookLocation(0, -1)
            .setDifficulty(3)
            .setResearchAspects(Aspect.PLANT, Aspect.EXCHANGE, Aspect.MAGIC, Aspect.AURA, Aspect.ELDRITCH, Aspect.MECHANISM)
            .addSingleTextPage()
            .apply( builder -> {
                ItemStack block = new ItemStack(ModBlocks.livingrock);
                ItemStack pearl = OreDictionary.getOres(LibOreDict.MANA_PEARL).get(0);
                ItemStack prismarine = OreDictionary.getOres(LibOreDict.PRISMARINE_SHARD).get(0);
                ItemStack manasteel = OreDictionary.getOres(LibOreDict.MANA_STEEL).get(0);
                builder.addInfusionRecipe(
                    new AspectList().add(Aspect.PLANT, 64).add(Aspect.CRAFT, 32).add(Aspect.MAGIC, 32).add(Aspect.CRAFT, 32).add(Aspect.AURA, 16),
                    new ItemStack(ModBlocks.runeAltar, 1),
                    10,
                    Constants.thaumcraftMatrix(),
                    block, pearl, prismarine, manasteel, block, pearl, prismarine, manasteel);
            })
            .commit();

        // Agglomeration Plate
        new ResearchBuilder("TERRASTEEL")
                .setBookLocation(0, -3)
                .setDifficulty(3)
                .setResearchAspects(Aspect.EARTH, Aspect.GREED, Aspect.MAGIC, Aspect.AURA, Aspect.METAL, Aspect.TOOL)
                .addSingleTextPage()
                .apply( builder -> {
                    List<ItemStack> ingredients = new LinkedList<>();
                    for (String oredict: LibOreDict.RUNE) {
                        ingredients.add(OreDictionary.getOres(oredict).get(0));
                    }
                    ItemStack blockWhite = new ItemStack(ModBlocks.livingrock);
                    ItemStack blockBlue = new ItemStack(Blocks.lapis_block);
                    ItemStack blockMana = new ItemStack(ModBlocks.storage, 1, 0);
                    ItemStack gemTier = new ItemStack((Item) Item.itemRegistry.getObject("dreamcraft:item.EngravedDiamondCrystalChip"));
                    ingredients.add(blockWhite);
                    ingredients.add(8, blockWhite);
                    ingredients.add(blockBlue);
                    ingredients.add(9, blockBlue);
                    ingredients.add(blockMana);
                    ingredients.add(10, blockMana);
                    ingredients.add(gemTier);
                    ingredients.add(11, gemTier);
                    builder.addInfusionRecipe(
                            new AspectList().add(Aspect.EARTH, 64).add(Aspect.GREED, 64).add(Aspect.EXCHANGE, 32).add(Aspect.MAGIC, 32).add(Aspect.CRAFT, 32).add(Aspect.AURA, 16),
                            new ItemStack(ModBlocks.terraPlate, 1),
                            16,
                            new ItemStack(ModBlocks.runeAltar),
                            ingredients.toArray(new ItemStack[0]));
                })
                .commit();

        new ResearchBuilder("ALFHEIM")
            .setBookLocation(0, -6)
            .setDifficulty(3)
            .setResearchAspects(Aspect.EARTH, Aspect.PLANT, Aspect.ELDRITCH, Aspect.AURA, Aspect.TRAVEL, Aspect.SENSES)
            .addTextPages(0,1)
            .addCraftingRecipe(
                    new ItemStack(ModBlocks.livingwood,4, Constants.LIVINGWOOD_META_GLIMMERING),
                    new AspectList().add(Aspect.EARTH, 50).add(Aspect.WATER, 50).add(Aspect.AIR, 25),
                    "LWL", "WGW", "LWL",
                    'W', new ItemStack(ModBlocks.livingwood, 1, Constants.LIVINGWOOD_META_BLOCK),
                    'L', "ingotSteeleaf",
                    'G', "gemEmerald"
            )
            .addTextPages(1,1)
            .apply(builder -> {
                ItemStack glimmer = new ItemStack(ModBlocks.livingwood, 1, Constants.LIVINGWOOD_META_GLIMMERING);
                ItemStack glow = new ItemStack(Blocks.glowstone);
                ItemStack gemIV = new ItemStack((Item) Item.itemRegistry.getObject("dreamcraft:item.EngravedEnergyChip"));
                ItemStack construct = Constants.thaumcraftConstruct();
                ItemStack terra = new ItemStack(ModItems.manaResource, 1, Constants.MANARESOURCE_META_TERRASTEEL);

                builder.addInfusionRecipe(
                    new AspectList().add(Aspect.TRAVEL, 64).add(Aspect.MAGIC, 64).add(Aspect.PLANT, 64).add(Aspect.ELDRITCH, 64).add(Aspect.VOID, 32),
                    new ItemStack(ModBlocks.alfPortal),
                    20,
                    new ItemStack(ModBlocks.alchemyCatalyst),
                    construct, glimmer, glow, terra, construct, glimmer, gemIV, terra, construct, glimmer, glow, terra, construct, glimmer, gemIV, terra
                );
            })
            .commit();

        // Catalysts
        new ResearchBuilder("ALCHEMY_CATALYST")
                .setBookLocation(0, 1)
                .setDifficulty(1)
                .setResearchAspects(Aspect.WATER, Aspect.EXCHANGE, Aspect.MAGIC)
                .addSingleTextPage()
                .addCraftingRecipe(
                        new ItemStack(ModBlocks.alchemyCatalyst),
                        new AspectList().add(Aspect.EARTH, 20).add(Aspect.ORDER, 40).add(Aspect.WATER, 20),
                        "SPS", "BCB", "SIS",
                        'S', new ItemStack(ModBlocks.livingrock, 1, Constants.LIVINGWOOD_META_BLOCK),
                        'C', Constants.thaumcraftConstruct(),
                        'I', LibOreDict.MANA_STEEL,
                        'B', new ItemStack(Items.brewing_stand),
                        'P', LibOreDict.MANA_PEARL
                )
                .commit();

        new ResearchBuilder("CONJURATION_CATALYST")
            .setBookLocation(2, -6)
            .setDifficulty(1)
            .setResearchAspects(Aspect.WATER, Aspect.EXCHANGE, Aspect.MAGIC)
            .addSingleTextPage()
            .addCraftingRecipe(
                    new ItemStack(ModBlocks.conjurationCatalyst),
                    new AspectList().add(Aspect.FIRE, 50).add(Aspect.ORDER, 150).add(Aspect.WATER, 50),
                    "SPS", "ICI", "SPS",
                    'S', new ItemStack(ModBlocks.livingrock, 1, Constants.LIVINGWOOD_META_BLOCK),
                    'C', new ItemStack(ModBlocks.alchemyCatalyst),
                    'I', LibOreDict.TERRA_STEEL,
                    'P', LibOreDict.PIXIE_DUST
            )
            .commit();

        // Pylons
        new ResearchBuilder("MANA_PYLON")
            .setBookLocation(2, 0)
            .setDifficulty(2)
            .setResearchAspects(Aspect.CRYSTAL, Aspect.GREED, Aspect.MIND)
            .addSingleTextPage()
            .addCraftingRecipe(
                    new ItemStack(ModBlocks.pylon, 1, Constants.PYLON_META_MANA),
                    new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.AIR, 20),
                    "SIS", "GCG", "SIS",
                    'C', new ItemStack(ModBlocks.storage, 1, Constants.STORAGE_META_DIAMONDBLOCK),
                    'I', new ItemStack(ModBlocks.storage, 1, Constants.STORAGE_META_MANASTEELBLOCK),
                    'G', "plateGold",
                    'S', "gemInfusedWater"
            )
            .commit();

        new ResearchBuilder("NATURA_PYLON")
            .setBookLocation(4, -1)
            .setDifficulty(2)
            .setResearchAspects(Aspect.CRYSTAL, Aspect.GREED, Aspect.MIND, Aspect.TRAVEL, Aspect.PLANT)
            .addSingleTextPage()
            .addCraftingRecipe(
                    new ItemStack(ModBlocks.pylon, 1, Constants.PYLON_META_NATURA),
                    new AspectList().add(Aspect.EARTH, 50).add(Aspect.ORDER, 100).add(Aspect.AIR, 50),
                    "SIS", "GCG", "SIS",
                    'C', new ItemStack(ModBlocks.pylon, 1, Constants.PYLON_META_MANA),
                    'I', LibOreDict.TERRA_STEEL,
                    'G', "ingotSteeleaf",
                    'S', "gemInfusedEarth"
            )
            .commit();

        new ResearchBuilder("GAIA_PYLON")
            .setBookLocation(6, -2)
            .setDifficulty(3)
            .setResearchAspects(Aspect.ELDRITCH, Aspect.GREED, Aspect.MIND, Aspect.CRYSTAL, Aspect.DARKNESS, Aspect.TRAP)
            .addSingleTextPage()
            .apply(builder -> {
                ItemStack pinkGem = new ItemStack(ModItems.manaResource, 1, Constants.MANARESOURCE_META_DRAGONSTONE);
                ItemStack blackPlate = OreDictionary.getOres("plateNaquadah").get(0);
                ItemStack voidCap = new ItemStack((Item) Item.itemRegistry.getObject("dreamcraft:item.ChargedVoidWandCap"));
                ItemStack pinkChip = new ItemStack((Item) Item.itemRegistry.getObject("dreamcraft:item.EngravedManyullynCrystalChip"));
                builder.addInfusionRecipe(
                    new AspectList().add(Aspect.ELDRITCH, 64).add(Aspect.TRAP, 64).add(Aspect.MAGIC, 64).add(Aspect.DARKNESS, 32).add(Aspect.CRYSTAL, 32),
                    new ItemStack(ModBlocks.pylon, 1, Constants.PYLON_META_GAIA),
                    16,
                    new ItemStack(ModBlocks.pylon, 1, Constants.PYLON_META_MANA),
                    voidCap, blackPlate, pinkChip, pinkGem, pinkChip, blackPlate, voidCap, blackPlate, pinkChip, pinkGem, pinkChip, blackPlate
                );
            })
            .commit();
    }

    public static AspectList researchList(Aspect... aspects) {
        AspectList list = new AspectList();
        for (Aspect aspect: aspects) {
            list.add(aspect, 1);
        }
        return list;
    }

    public static ResearchPage textPage(String category, String key) {
        return new ResearchPage(key, category + "." + key + ".body");
    }
}