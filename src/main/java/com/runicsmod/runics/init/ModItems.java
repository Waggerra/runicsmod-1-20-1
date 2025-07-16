package com.runicsmod.runics.init;

import com.runicsmod.runics.RunicsMod;
import com.runicsmod.runics.items.PactPapyrusItem;
import com.runicsmod.runics.items.RuneItem;
import com.runicsmod.runics.items.MobCapsuleItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RunicsMod.MODID);

    public static final RegistryObject<Item> RUNIC_GEM = ITEMS.register("runic_gem",
            () -> new Item(new Item.Properties().tab(RunicsMod.TAB)));

    public static final RegistryObject<Item> ANSUZ_RUNE = ITEMS.register("ansuz_rune",
            () -> new RuneItem(new Item.Properties().tab(RunicsMod.TAB), "ansuz"));
    public static final RegistryObject<Item> ALGIZ_RUNE = ITEMS.register("algiz_rune",
            () -> new RuneItem(new Item.Properties().tab(RunicsMod.TAB), "algiz"));
    public static final RegistryObject<Item> RAIDHO_RUNE = ITEMS.register("raidho_rune",
            () -> new RuneItem(new Item.Properties().tab(RunicsMod.TAB), "raidho"));
    public static final RegistryObject<Item> SOWULO_RUNE = ITEMS.register("sowulo_rune",
            () -> new RuneItem(new Item.Properties().tab(RunicsMod.TAB), "sowulo"));
    public static final RegistryObject<Item> JERA_RUNE = ITEMS.register("jera_rune",
            () -> new RuneItem(new Item.Properties().tab(RunicsMod.TAB), "jera"));
    public static final RegistryObject<Item> ISA_RUNE = ITEMS.register("isa_rune",
            () -> new RuneItem(new Item.Properties().tab(RunicsMod.TAB), "isa"));
    public static final RegistryObject<Item> EIHWAZ_RUNE = ITEMS.register("eihwaz_rune",
            () -> new RuneItem(new Item.Properties().tab(RunicsMod.TAB), "eihwaz"));
    public static final RegistryObject<Item> ALGIZ_INVERTED_RUNE = ITEMS.register("algiz_inverted_rune",
            () -> new RuneItem(new Item.Properties().tab(RunicsMod.TAB), "algiz_inverted"));
    public static final RegistryObject<Item> KENAZ_RUNE = ITEMS.register("kenaz_rune",
            () -> new RuneItem(new Item.Properties().tab(RunicsMod.TAB), "kenaz"));
    
    public static final RegistryObject<Item> PACT_PAPYRUS = ITEMS.register("pact_papyrus",
            () -> new PactPapyrusItem(new Item.Properties().tab(RunicsMod.TAB).stacksTo(1)));

    // Cult System Items
    public static final RegistryObject<Item> MOB_CAPSULE = ITEMS.register("mob_capsule",
            () -> new MobCapsuleItem(new Item.Properties().tab(RunicsMod.TAB).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
} 