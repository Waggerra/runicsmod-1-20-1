package com.runicsmod.runics.compat;

import net.minecraft.network.chat.Component;

/**
 * Compatibility layer for Minecraft 1.19.4
 * Handles API differences between versions
 */
public class VersionCompat {
    
    /**
     * Creates a text component from a string
     */
    public static Component createTextComponent(String text) {
        return Component.literal(text);
    }
    
    /**
     * Creates a translatable text component
     */
    public static Component createTranslatableComponent(String key, Object... args) {
        return Component.translatable(key, args);
    }
    
    /**
     * Gets the Minecraft version string
     */
    public static String getMinecraftVersion() {
        return "1.19.4";
    }
    
    /**
     * Checks if this version supports specific features
     */
    public static boolean supportsFeature(String feature) {
        switch (feature) {
            case "new_chat_system":
                return true;
            case "registry_access":
                return true;
            default:
                return false;
        }
    }
} 