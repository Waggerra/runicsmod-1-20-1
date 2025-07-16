package com.runicsmod.runics.compat;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * Compatibility layer for Minecraft 1.18.2
 * Handles API differences between versions
 */
public class VersionCompat {
    
    /**
     * Creates a text component from a string
     */
    public static Component createTextComponent(String text) {
        return new TextComponent(text);
    }
    
    /**
     * Creates a translatable text component
     */
    public static Component createTranslatableComponent(String key, Object... args) {
        return new TranslatableComponent(key, args);
    }
    
    /**
     * Gets the Minecraft version string
     */
    public static String getMinecraftVersion() {
        return "1.18.2";
    }
    
    /**
     * Checks if this version supports specific features
     */
    public static boolean supportsFeature(String feature) {
        switch (feature) {
            case "new_chat_system":
                return false;
            case "registry_access":
                return true;
            default:
                return false;
        }
    }
} 