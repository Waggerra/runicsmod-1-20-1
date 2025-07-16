package com.runicsmod.runics;

import com.runicsmod.runics.client.ClientEventHandler;
import com.runicsmod.runics.commands.RuneStatusCommand;
import com.runicsmod.runics.commands.CultCommand;
import com.runicsmod.runics.config.RunicsConfig;
import com.runicsmod.runics.data.ModItemModelProvider;
import com.runicsmod.runics.events.ServerEventHandler;
import com.runicsmod.runics.init.ModItems;
import com.runicsmod.runics.network.NetworkHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(RunicsMod.MODID)
public class RunicsMod {
    public static final String MODID = "runics";
    private static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.RUNIC_GEM.get()))
            .title(Component.translatable("itemGroup.runics"))
            .displayItems((parameters, output) -> {
                // Add all mod items to the creative tab
                output.accept(ModItems.RUNIC_GEM.get());
                output.accept(ModItems.ANSUZ_RUNE.get());
                output.accept(ModItems.ALGIZ_RUNE.get());
                output.accept(ModItems.RAIDHO_RUNE.get());
                output.accept(ModItems.SOWULO_RUNE.get());
                output.accept(ModItems.JERA_RUNE.get());
                output.accept(ModItems.ISA_RUNE.get());
                output.accept(ModItems.EIHWAZ_RUNE.get());
                output.accept(ModItems.ALGIZ_INVERTED_RUNE.get());
                output.accept(ModItems.KENAZ_RUNE.get());
                output.accept(ModItems.PACT_PAPYRUS.get());
                output.accept(ModItems.MOB_CAPSULE.get());
            })
            .build());

    public RunicsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::gatherData);

        // Register config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RunicsConfig.SPEC);

        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NetworkHandler.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // Client-side setup is handled by ClientEventHandler
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));
        }
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        RuneStatusCommand.register(event.getDispatcher());
        CultCommand.register(event.getDispatcher());
    }
} 