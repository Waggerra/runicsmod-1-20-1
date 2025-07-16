package com.runicsmod.runics.pacts;

public enum PactType {
    // Passive Pacts
    BLOOD_PRICE("Blood Price", 
        "Lose 3 hearts permanently", 
        "Gain +1 extra rune slot", 
        "The ink runs red. The price is flesh, the reward is potential.", 
        false, 0, 3),
    
    GLASS_CANNON("Glass Cannon", 
        "Lose 8 hearts permanently", 
        "Deal double damage with all attacks", 
        "Strike first, or don't strike at all.", 
        false, 0, 8),
    
    WRATHBOUND("Wrathbound", 
        "You deal 30% less damage on high health", 
        "Deal +60% more damage when below 50% health", 
        "Only in pain does true rage awaken.", 
        false, 0, 0),
    
    STONE_PACT("Stone Pact", 
        "You cannot use shields", 
        "Gain Resistance II and Knockback Resistance", 
        "Move with purpose. Or not at all.", 
        false, 0, 0),
    
    WITHERING_HEART("Withering Heart", 
        "You permanently cannot regenerate naturally", 
        "You gain lifesteal, hitting entities makes you regenerate", 
        "Life feeds death, death feeds life.", 
        false, 0, 0),
    
    PACT_OF_SHADOWS("Pact of Shadows", 
        "Sunlight gives you Weakness", 
        "Gain Invisibility in darkness or caves", 
        "You are but a whisper in the dark.", 
        false, 0, 0),
    
    // Active Pacts
    SKYCALLERS_PACT("Skycaller's Pact", 
        "Lose 5 permanent hearts", 
        "Launch 100 blocks upward with effects", 
        "You gave your breath to the stars. Now they carry you.", 
        true, 6000, 5), // 5 minutes
    
    OATH_OF_IRON_WALL("Oath of the Iron Wall", 
        "Cannot use shields", 
        "Gain Resistance III and Knockback Resistance for 10s", 
        "No shield in hand. But your will is armor.", 
        true, 3000, 0), // 2.5 minutes
    
    PHANTOM_STEP_PACT("Phantom Step Pact", 
        "Permanently -4 armor points, +5% damage taken", 
        "Teleport behind nearest enemy, apply Blindness", 
        "Step through the veil, and strike unseen.", 
        true, 1800, 0), // 1.5 minutes
    
    LAVABORN_PACT("Lavaborn Pact", 
        "Getting any damage in water kills you instantly", 
        "Gain fire resistance when touched by fire/lava for 3 minutes", 
        "Born of flame, sworn to fire. Water is your bane, but fire is your ally.", 
        false, 12000, 0), // 10 minutes cooldown
    
    PACT_OF_WEBBED_FATE("Pact of Webbed Fate", 
        "Deal 20% less damage", 
        "Spawns cobwebs and slowness aura for 8s", 
        "All paths tangle for those who weave them.", 
        true, 2400, 0); // 2 minutes

    private final String name;
    private final String curse;
    private final String benefit;
    private final String quote;
    private final boolean isActive;
    private final int cooldownTicks;
    private final int heartCost;

    PactType(String name, String curse, String benefit, String quote, boolean isActive, int cooldownTicks, int heartCost) {
        this.name = name;
        this.curse = curse;
        this.benefit = benefit;
        this.quote = quote;
        this.isActive = isActive;
        this.cooldownTicks = cooldownTicks;
        this.heartCost = heartCost;
    }

    public String getName() { return name; }
    public String getCurse() { return curse; }
    public String getBenefit() { return benefit; }
    public String getQuote() { return quote; }
    public boolean isActive() { return isActive; }
    public int getCooldownTicks() { return cooldownTicks; }
    public int getHeartCost() { return heartCost; }
} 