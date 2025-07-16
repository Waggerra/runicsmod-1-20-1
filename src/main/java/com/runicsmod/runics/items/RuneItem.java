package com.runicsmod.runics.items;

import com.runicsmod.runics.capability.RuneCapabilityProvider;
import com.runicsmod.runics.config.RunicsConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RuneItem extends Item {
    private final String runeType;

    public RuneItem(Properties properties, String runeType) {
        super(properties);
        this.runeType = runeType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            player.getCapability(RuneCapabilityProvider.RUNE_CAPABILITY).ifPresent(cap -> {
                boolean wasEnabled = cap.isRuneEnabled(runeType);
                cap.setRuneEnabled(runeType, !wasEnabled);
                
                String status = wasEnabled ? "disabled" : "enabled";
                player.sendSystemMessage(Component.literal("Rune " + runeType + " " + status)
                    .withStyle(wasEnabled ? ChatFormatting.RED : ChatFormatting.GREEN));
            });
        }
        
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        
        // Add rune-specific tooltip based on type
        switch (runeType) {
            case "ansuz":
                tooltip.add(Component.literal("Reduces hunger consumption").withStyle(ChatFormatting.BLUE));
                break;
            case "algiz":
                tooltip.add(Component.literal("Provides damage resistance").withStyle(ChatFormatting.BLUE));
                break;
            case "raidho":
                tooltip.add(Component.literal("Increases movement speed").withStyle(ChatFormatting.BLUE));
                break;
            case "sowulo":
                tooltip.add(Component.literal("Increases damage").withStyle(ChatFormatting.BLUE));
                break;
            case "jera":
                tooltip.add(Component.literal("Improves harvest yields").withStyle(ChatFormatting.BLUE));
                break;
            case "isa":
                tooltip.add(Component.literal("Freezes nearby water").withStyle(ChatFormatting.BLUE));
                break;
            case "eihwaz":
                tooltip.add(Component.literal("Reveals nearby mobs").withStyle(ChatFormatting.BLUE));
                break;
            case "algiz_inverted":
                tooltip.add(Component.literal("Inverts damage resistance").withStyle(ChatFormatting.BLUE));
                break;
            case "kenaz":
                tooltip.add(Component.literal("Provides light and warmth").withStyle(ChatFormatting.BLUE));
                break;
        }
        
        tooltip.add(Component.literal("Right-click to toggle").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        // Give runes a mystical glow
        return true;
    }

    public String getRuneType() {
        return runeType;
    }
}