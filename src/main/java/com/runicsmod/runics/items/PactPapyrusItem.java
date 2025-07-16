package com.runicsmod.runics.items;

import com.runicsmod.runics.capability.RuneCapabilityProvider;
import com.runicsmod.runics.network.NetworkHandler;
import com.runicsmod.runics.network.OpenPactGuiPacket;
import com.runicsmod.runics.pacts.PactType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class PactPapyrusItem extends Item {
    
    public PactPapyrusItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        // Give it enchantment glow like classic minecraft paper
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            player.getCapability(RuneCapabilityProvider.RUNE_CAPABILITY).ifPresent(cap -> {
                // Generate a random pact
                Random random = new Random();
                PactType[] allPacts = PactType.values();
                PactType randomPact = allPacts[random.nextInt(allPacts.length)];
                
                if (cap.hasPact()) {
                    // Show replacement GUI with the random pact
                    NetworkHandler.sendToPlayer(new OpenPactGuiPacket(randomPact, true), (net.minecraft.server.level.ServerPlayer) player);
                } else {
                    // Show selection GUI with the random pact
                    NetworkHandler.sendToPlayer(new OpenPactGuiPacket(randomPact, false), (net.minecraft.server.level.ServerPlayer) player);
                }
            });
            
            // Always consume the item, regardless of choice
            stack.shrink(1);
        }
        
        return InteractionResultHolder.success(stack);
    }
} 