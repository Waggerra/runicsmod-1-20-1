package com.runicsmod.runics.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CultCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("cult")
            .then(Commands.literal("status")
                .executes(CultCommand::executeStatus))
        );
    }
    
    private static int executeStatus(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof ServerPlayer player) {
            // For now, just show a placeholder message
            player.sendSystemMessage(Component.literal("Cult system is not yet fully implemented"));
        }
        return 1;
    }
}