package com.runicsmod.runics;

import com.runicsmod.runics.client.ClientEventHandler;
import com.runicsmod.runics.commands.RuneStatusCommand;
import com.runicsmod.runics.commands.CultCommand;
import com.runicsmod.runics.config.RunicsConfig;
import com.runicsmod.runics.data.ModItemModelProvider;
import com.runicsmod.runics.events.ServerEventHandler;
import com.runicsmod.runics.init.ModItems;
import com.runicsmod.runics.network.NetworkHandler;
import net.minecraft.data.DataGenerator;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

@Mod(RunicsMod.MODID)
public class RunicsMod {
    public static final String MODID = "runics";
    private static final Logger LOGGER = LogManager.getLogger();

    public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.RUNIC_GEM.get());
        }
    };

    public RunicsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

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