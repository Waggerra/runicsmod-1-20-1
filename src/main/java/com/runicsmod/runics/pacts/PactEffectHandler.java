package com.runicsmod.runics.pacts;

import com.runicsmod.runics.capability.RuneCapabilityProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class PactEffectHandler {
    
    // UUIDs for permanent attribute modifiers
    public static final UUID GLASS_CANNON_HEALTH_UUID = UUID.fromString("1-2-3-4-5");
    public static final UUID BLOOD_PRICE_HEALTH_UUID = UUID.fromString("1-2-3-4-6");
    public static final UUID SKYCALLER_HEALTH_UUID = UUID.fromString("1-2-3-4-7");
    public static final UUID STONE_PACT_RESISTANCE_UUID = UUID.fromString("1-2-3-4-8");
    public static final UUID STONE_PACT_KNOCKBACK_UUID = UUID.fromString("1-2-3-4-9");
    public static final UUID PHANTOM_STEP_ARMOR_UUID = UUID.fromString("1-2-3-4-10");

    // UUIDs for cult pact modifiers (different from personal pacts)
    public static final UUID CULT_GLASS_CANNON_HEALTH_UUID = UUID.fromString("2-2-3-4-5");
    public static final UUID CULT_BLOOD_PRICE_HEALTH_UUID = UUID.fromString("2-2-3-4-6");
    public static final UUID CULT_SKYCALLER_HEALTH_UUID = UUID.fromString("2-2-3-4-7");
    public static final UUID CULT_STONE_PACT_RESISTANCE_UUID = UUID.fromString("2-2-3-4-8");
    public static final UUID CULT_STONE_PACT_KNOCKBACK_UUID = UUID.fromString("2-2-3-4-9");
    public static final UUID CULT_PHANTOM_STEP_ARMOR_UUID = UUID.fromString("2-2-3-4-10");

    // Method to completely remove ALL mod-related attribute modifiers
    public static void removeAllModAttributeModifiers(ServerPlayer player) {
        try {
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth != null) {
                // Remove all pact health modifiers
                maxHealth.removeModifier(BLOOD_PRICE_HEALTH_UUID);
                maxHealth.removeModifier(GLASS_CANNON_HEALTH_UUID);
                maxHealth.removeModifier(SKYCALLER_HEALTH_UUID);
                // Remove cult pact health modifiers
                maxHealth.removeModifier(CULT_BLOOD_PRICE_HEALTH_UUID);
                maxHealth.removeModifier(CULT_GLASS_CANNON_HEALTH_UUID);
                maxHealth.removeModifier(CULT_SKYCALLER_HEALTH_UUID);
            }
            
            AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
            if (armor != null) {
                // Remove armor modifiers
                armor.removeModifier(STONE_PACT_RESISTANCE_UUID);
                armor.removeModifier(PHANTOM_STEP_ARMOR_UUID);
                // Remove cult armor modifiers
                armor.removeModifier(CULT_STONE_PACT_RESISTANCE_UUID);
                armor.removeModifier(CULT_PHANTOM_STEP_ARMOR_UUID);
            }
            
            AttributeInstance knockbackResistance = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (knockbackResistance != null) {
                // Remove knockback resistance modifiers
                knockbackResistance.removeModifier(STONE_PACT_KNOCKBACK_UUID);
                knockbackResistance.removeModifier(CULT_STONE_PACT_KNOCKBACK_UUID);
            }
            
            // Remove any speed modifiers from Raidho rune
            AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null) {
                speed.removeModifier(java.util.UUID.fromString("0-0-0-0-1")); // Raidho speed modifier
            }
        } catch (Exception e) {
            // Silently handle any errors to prevent crashes
            System.err.println("Runics Mod: Error removing attribute modifiers - " + e.getMessage());
        }
    }

    public static void removeAllPactHealthModifiers(ServerPlayer player) {
        try {
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth != null) {
                maxHealth.removeModifier(BLOOD_PRICE_HEALTH_UUID);
                maxHealth.removeModifier(GLASS_CANNON_HEALTH_UUID);
                maxHealth.removeModifier(SKYCALLER_HEALTH_UUID);
            }
        } catch (Exception e) {
            // Silently handle any errors to prevent crashes
            System.err.println("Runics Mod: Error removing health modifiers - " + e.getMessage());
        }
    }

    public static void applyPactEffects(ServerPlayer player, PactType pactType) {
        try {
            // Always clean up old modifiers first!
            removeAllPactHealthModifiers(player);
            
            player.getCapability(RuneCapabilityProvider.RUNE_CAPABILITY).ifPresent(cap -> {
                switch (pactType) {
                    case BLOOD_PRICE:
                        // Lose 3 hearts permanently
                        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                        if (maxHealth != null && maxHealth.getModifier(BLOOD_PRICE_HEALTH_UUID) == null) {
                            // Check for compatibility with other mods (like Lifesteal)
                            double currentMaxHealth = maxHealth.getValue();
                            if (currentMaxHealth >= 8.0) { // Only apply if player has at least 4 hearts
                                maxHealth.addPermanentModifier(new AttributeModifier(BLOOD_PRICE_HEALTH_UUID, "BloodPriceHealth", -6.0, AttributeModifier.Operation.ADDITION));
                            }
                        }
                        // Gain +1 extra rune slot
                        cap.setExtraRuneSlots(1);
                        cap.setMaxHealthReduction(6.0f);
                        break;

                    case GLASS_CANNON:
                        // Lose 8 hearts permanently
                        AttributeInstance glassMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                        if (glassMaxHealth != null && glassMaxHealth.getModifier(GLASS_CANNON_HEALTH_UUID) == null) {
                            // Check for compatibility with other mods (like Lifesteal)
                            double currentMaxHealth = glassMaxHealth.getValue();
                            if (currentMaxHealth >= 18.0) { // Only apply if player has at least 9 hearts
                                glassMaxHealth.addPermanentModifier(new AttributeModifier(GLASS_CANNON_HEALTH_UUID, "GlassCannonHealth", -16.0, AttributeModifier.Operation.ADDITION));
                            }
                        }
                        cap.setMaxHealthReduction(16.0f);
                        break;

                    case SKYCALLERS_PACT:
                        // Lose 5 hearts permanently
                        AttributeInstance skyMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                        if (skyMaxHealth != null && skyMaxHealth.getModifier(SKYCALLER_HEALTH_UUID) == null) {
                            // Check for compatibility with other mods (like Lifesteal)
                            double currentMaxHealth = skyMaxHealth.getValue();
                            if (currentMaxHealth >= 12.0) { // Only apply if player has at least 6 hearts
                                skyMaxHealth.addPermanentModifier(new AttributeModifier(SKYCALLER_HEALTH_UUID, "SkyCallerHealth", -10.0, AttributeModifier.Operation.ADDITION));
                            }
                        }
                        cap.setMaxHealthReduction(10.0f);
                        break;

                    case STONE_PACT:
                        // Gain permanent resistance and knockback resistance
                        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
                        if (armor != null && armor.getModifier(STONE_PACT_RESISTANCE_UUID) == null) {
                            armor.addPermanentModifier(new AttributeModifier(STONE_PACT_RESISTANCE_UUID, "StonePactArmor", 8.0, AttributeModifier.Operation.ADDITION));
                        }
                        
                        AttributeInstance knockbackResistance = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
                        if (knockbackResistance != null && knockbackResistance.getModifier(STONE_PACT_KNOCKBACK_UUID) == null) {
                            knockbackResistance.addPermanentModifier(new AttributeModifier(STONE_PACT_KNOCKBACK_UUID, "StonePactKnockback", 1.0, AttributeModifier.Operation.ADDITION));
                        }
                        break;

                    case OATH_OF_IRON_WALL:
                        // No permanent effects, just prevents shield usage
                        break;

                    case PHANTOM_STEP_PACT:
                        // Permanently -4 armor points
                        AttributeInstance phantomArmor = player.getAttribute(Attributes.ARMOR);
                        if (phantomArmor != null && phantomArmor.getModifier(PHANTOM_STEP_ARMOR_UUID) == null) {
                            phantomArmor.addPermanentModifier(new AttributeModifier(PHANTOM_STEP_ARMOR_UUID, "PhantomStepArmor", -4.0, AttributeModifier.Operation.ADDITION));
                        }
                        break;

                    default:
                        // Other pacts don't have immediate permanent effects
                        break;
                }
            });
            
            // Set current health to max health after reduction (with safety checks)
            float currentHealth = player.getHealth();
            float maxHealth = player.getMaxHealth();
            if (currentHealth > maxHealth && maxHealth > 0) {
                player.setHealth(Math.max(1.0f, maxHealth));
            }
        } catch (Exception e) {
            // Prevent crashes if anything goes wrong
            System.err.println("Runics Mod: Error applying pact effects - " + e.getMessage());
        }
    }
    
    // Cult Pact Methods
    public static void applyCultPactEffects(ServerPlayer player, PactType pactType) {
        try {
            switch (pactType) {
                case BLOOD_PRICE:
                    // Lose 3 hearts permanently (cult version)
                    AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                    if (maxHealth != null && maxHealth.getModifier(CULT_BLOOD_PRICE_HEALTH_UUID) == null) {
                        double currentMaxHealth = maxHealth.getValue();
                        if (currentMaxHealth >= 8.0) { // Only apply if player has at least 4 hearts
                            maxHealth.addPermanentModifier(new AttributeModifier(CULT_BLOOD_PRICE_HEALTH_UUID, "CultBloodPriceHealth", -6.0, AttributeModifier.Operation.ADDITION));
                        }
                    }
                    break;

                case GLASS_CANNON:
                    // Lose 8 hearts permanently (cult version)
                    AttributeInstance glassMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                    if (glassMaxHealth != null && glassMaxHealth.getModifier(CULT_GLASS_CANNON_HEALTH_UUID) == null) {
                        double currentMaxHealth = glassMaxHealth.getValue();
                        if (currentMaxHealth >= 18.0) { // Only apply if player has at least 9 hearts
                            glassMaxHealth.addPermanentModifier(new AttributeModifier(CULT_GLASS_CANNON_HEALTH_UUID, "CultGlassCannonHealth", -16.0, AttributeModifier.Operation.ADDITION));
                        }
                    }
                    break;

                case SKYCALLERS_PACT:
                    // Lose 5 hearts permanently (cult version)
                    AttributeInstance skyMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                    if (skyMaxHealth != null && skyMaxHealth.getModifier(CULT_SKYCALLER_HEALTH_UUID) == null) {
                        double currentMaxHealth = skyMaxHealth.getValue();
                        if (currentMaxHealth >= 12.0) { // Only apply if player has at least 6 hearts
                            skyMaxHealth.addPermanentModifier(new AttributeModifier(CULT_SKYCALLER_HEALTH_UUID, "CultSkyCallerHealth", -10.0, AttributeModifier.Operation.ADDITION));
                        }
                    }
                    break;

                case STONE_PACT:
                    // Gain permanent resistance and knockback resistance (cult version)
                    AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
                    if (armor != null && armor.getModifier(CULT_STONE_PACT_RESISTANCE_UUID) == null) {
                        armor.addPermanentModifier(new AttributeModifier(CULT_STONE_PACT_RESISTANCE_UUID, "CultStonePactArmor", 8.0, AttributeModifier.Operation.ADDITION));
                    }
                    
                    AttributeInstance knockbackResistance = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
                    if (knockbackResistance != null && knockbackResistance.getModifier(CULT_STONE_PACT_KNOCKBACK_UUID) == null) {
                        knockbackResistance.addPermanentModifier(new AttributeModifier(CULT_STONE_PACT_KNOCKBACK_UUID, "CultStonePactKnockback", 1.0, AttributeModifier.Operation.ADDITION));
                    }
                    break;

                case PHANTOM_STEP_PACT:
                    // Permanently -4 armor points (cult version)
                    AttributeInstance phantomArmor = player.getAttribute(Attributes.ARMOR);
                    if (phantomArmor != null && phantomArmor.getModifier(CULT_PHANTOM_STEP_ARMOR_UUID) == null) {
                        phantomArmor.addPermanentModifier(new AttributeModifier(CULT_PHANTOM_STEP_ARMOR_UUID, "CultPhantomStepArmor", -4.0, AttributeModifier.Operation.ADDITION));
                    }
                    break;

                default:
                    // Other pacts don't have permanent effects
                    break;
            }
            
            // Adjust health if needed
            float currentHealth = player.getHealth();
            float maxHealth = player.getMaxHealth();
            if (currentHealth > maxHealth && maxHealth > 0) {
                player.setHealth(Math.max(1.0f, maxHealth));
            }
        } catch (Exception e) {
            System.err.println("Runics Mod: Error applying cult pact effects - " + e.getMessage());
        }
    }
    
    public static void removeCultPactEffects(ServerPlayer player, PactType pactType) {
        try {
            switch (pactType) {
                case BLOOD_PRICE:
                    AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                    if (maxHealth != null) {
                        maxHealth.removeModifier(CULT_BLOOD_PRICE_HEALTH_UUID);
                    }
                    break;

                case GLASS_CANNON:
                    AttributeInstance glassMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                    if (glassMaxHealth != null) {
                        glassMaxHealth.removeModifier(CULT_GLASS_CANNON_HEALTH_UUID);
                    }
                    break;

                case SKYCALLERS_PACT:
                    AttributeInstance skyMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                    if (skyMaxHealth != null) {
                        skyMaxHealth.removeModifier(CULT_SKYCALLER_HEALTH_UUID);
                    }
                    break;

                case STONE_PACT:
                    AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
                    if (armor != null) {
                        armor.removeModifier(CULT_STONE_PACT_RESISTANCE_UUID);
                    }
                    
                    AttributeInstance knockbackResistance = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
                    if (knockbackResistance != null) {
                        knockbackResistance.removeModifier(CULT_STONE_PACT_KNOCKBACK_UUID);
                    }
                    break;

                case PHANTOM_STEP_PACT:
                    AttributeInstance phantomArmor = player.getAttribute(Attributes.ARMOR);
                    if (phantomArmor != null) {
                        phantomArmor.removeModifier(CULT_PHANTOM_STEP_ARMOR_UUID);
                    }
                    break;

                default:
                    // Other pacts don't have permanent effects to remove
                    break;
            }
        } catch (Exception e) {
            System.err.println("Runics Mod: Error removing cult pact effects - " + e.getMessage());
        }
    }
} 