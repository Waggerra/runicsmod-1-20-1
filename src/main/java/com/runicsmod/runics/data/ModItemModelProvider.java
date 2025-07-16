package com.runicsmod.runics.data;

import com.runicsmod.runics.RunicsMod;
import com.runicsmod.runics.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), RunicsMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Simple items that use the basic handheld model
        simpleItem(ModItems.RUNIC_GEM);
        
        // Rune items
        simpleItem(ModItems.ANSUZ_RUNE);
        simpleItem(ModItems.ALGIZ_RUNE);
        simpleItem(ModItems.RAIDHO_RUNE);
        simpleItem(ModItems.SOWULO_RUNE);
        simpleItem(ModItems.JERA_RUNE);
        simpleItem(ModItems.ISA_RUNE);
        simpleItem(ModItems.EIHWAZ_RUNE);
        simpleItem(ModItems.ALGIZ_INVERTED_RUNE);
        simpleItem(ModItems.KENAZ_RUNE);
        
        // Special items
        simpleItem(ModItems.PACT_PAPYRUS);
        simpleItem(ModItems.MOB_CAPSULE);
    }

    private void simpleItem(RegistryObject<Item> item) {
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RunicsMod.MODID, "item/" + item.getId().getPath()));
    }
}