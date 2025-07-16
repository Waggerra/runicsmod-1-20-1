package com.runicsmod.runics.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.runicsmod.runics.capability.RuneCapabilityProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class RuneStatusCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("runes")
            .then(Commands.literal("status")
                .executes(RuneStatusCommand::executeStatus))
            .then(Commands.literal("clear")
                .executes(RuneStatusCommand::executeClear))
        );
    }
    
    private static int executeStatus(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof ServerPlayer player) {
            player.getCapability(RuneCapabilityProvider.RUNE_CAPABILITY).ifPresent(cap -> {
                player.sendSystemMessage(Component.literal("=== Active Runes ==="));
                
                String[] runes = {"ansuz", "algiz", "raidho", "sowulo", "jera", "isa", "eihwaz", "algiz_inverted", "kenaz"};
                boolean hasActiveRunes = false;
                
                for (String rune : runes) {
                    if (cap.isRuneEnabled(rune)) {
                        player.sendSystemMessage(Component.literal("- " + rune + " (Enabled)"));
                        hasActiveRunes = true;
                    }
                }
                
                if (!hasActiveRunes) {
                    player.sendSystemMessage(Component.literal("No active runes"));
                }
            });
        }
        return 1;
    }
    
    private static int executeClear(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof ServerPlayer player) {
            player.getCapability(RuneCapabilityProvider.RUNE_CAPABILITY).ifPresent(cap -> {
                String[] runes = {"ansuz", "algiz", "raidho", "sowulo", "jera", "isa", "eihwaz", "algiz_inverted", "kenaz"};
                
                for (String rune : runes) {
                    cap.setRuneEnabled(rune, false);
                }
                
                player.sendSystemMessage(Component.literal("All runes cleared"));
            });
        }
        return 1;
    }
}