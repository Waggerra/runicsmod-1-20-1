package com.runicsmod.runics.compat;

import net.minecraft.network.chat.ITextComponent;
import net.minecraft.network.chat.StringTextComponent;
import net.minecraft.network.chat.TranslationTextComponent;

/**
 * Compatibility layer for Minecraft 1.16.5
 * Handles API differences between versions
 */
public class VersionCompat {
    
    /**
     * Creates a text component from a string
     */
    public static ITextComponent createTextComponent(String text) {
        return new StringTextComponent(text);
    }
    
    /**
     * Creates a translatable text component
     */
    public static ITextComponent createTranslatableComponent(String key, Object... args) {
        return new TranslationTextComponent(key, args);
    }
    
    /**
     * Gets the Minecraft version string
     */
    public static String getMinecraftVersion() {
        return "1.16.5";
    }
    
    /**
     * Checks if this version supports specific features
     */
    public static boolean supportsFeature(String feature) {
        switch (feature) {
            case "new_chat_system":
                return false;
            case "registry_access":
                return false;
            default:
                return false;
        }
    }
} 