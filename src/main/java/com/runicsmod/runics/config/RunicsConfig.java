package com.runicsmod.runics.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class RunicsConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Rune effectiveness multipliers
    public static final ForgeConfigSpec.DoubleValue ALGIZ_DAMAGE_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue SOWULO_DAMAGE_MULTIPLIER;
    public static final ForgeConfigSpec.IntValue JERA_HUNGER_RATE;
    public static final ForgeConfigSpec.DoubleValue RAIDHO_SPEED_MULTIPLIER;
    
    // Cooldowns
    public static final ForgeConfigSpec.IntValue ISA_COOLDOWN_SECONDS;
    public static final ForgeConfigSpec.IntValue EIHWAZ_COOLDOWN_SECONDS;
    
    // Detection ranges
    public static final ForgeConfigSpec.IntValue EIHWAZ_DETECTION_RANGE;
    public static final ForgeConfigSpec.IntValue KENAZ_DETECTION_RANGE;

    static {
        BUILDER.comment("Runics Mod Configuration");

        BUILDER.push("rune_effects");
        
        ALGIZ_DAMAGE_REDUCTION = BUILDER
            .comment("Damage reduction percentage for Algiz rune (0.3 = 30%)")
            .defineInRange("algiz_damage_reduction", 0.3, 0.0, 1.0);
            
        SOWULO_DAMAGE_MULTIPLIER = BUILDER
            .comment("Damage multiplier for Sowulo rune (1.5 = 50% more damage)")
            .defineInRange("sowulo_damage_multiplier", 1.5, 1.0, 3.0);
            
        JERA_HUNGER_RATE = BUILDER
            .comment("Hunger regeneration rate in ticks (20 ticks = 1 second)")
            .defineInRange("jera_hunger_rate", 600, 100, 6000);
            
        RAIDHO_SPEED_MULTIPLIER = BUILDER
            .comment("Speed multiplier for Raidho rune (1.3 = 30% faster)")
            .defineInRange("raidho_speed_multiplier", 1.3, 1.0, 2.0);

        BUILDER.pop();
        
        BUILDER.push("cooldowns");
        
        ISA_COOLDOWN_SECONDS = BUILDER
            .comment("Cooldown for Isa rune freeze effect in seconds")
            .defineInRange("isa_cooldown_seconds", 60, 10, 300);
            
        EIHWAZ_COOLDOWN_SECONDS = BUILDER
            .comment("Cooldown for Eihwaz rune after taking damage or attacking")
            .defineInRange("eihwaz_cooldown_seconds", 10, 5, 60);

        BUILDER.pop();
        
        BUILDER.push("detection");
        
        EIHWAZ_DETECTION_RANGE = BUILDER
            .comment("Detection range for Eihwaz rune mob sensing")
            .defineInRange("eihwaz_detection_range", 16, 8, 64);
            
        KENAZ_DETECTION_RANGE = BUILDER
            .comment("Detection range for Kenaz rune block detection")
            .defineInRange("kenaz_detection_range", 8, 4, 32);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}