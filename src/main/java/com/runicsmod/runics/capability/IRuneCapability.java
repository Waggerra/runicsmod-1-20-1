package com.runicsmod.runics.capability;

import com.runicsmod.runics.pacts.PactType;

public interface IRuneCapability {
    String getActiveRune(); // Legacy support - returns first rune or empty
    void setActiveRune(String rune); // Legacy support - sets as primary rune
    boolean hasRune();
    
    // Multi-rune support
    java.util.Set<String> getActiveRunes();
    void addActiveRune(String rune);
    void removeActiveRune(String rune);
    boolean hasActiveRune(String rune);
    void clearActiveRunes();
    int getMaxRuneSlots();

    // For Isa Rune
    int getIsaCooldown();
    void setIsaCooldown(int cooldown);

    // For Algiz Inverted Rune
    int getAlgizInvertedSneakTicks();
    void setAlgizInvertedSneakTicks(int ticks);

    // For Eihwaz Rune
    int getEihwazCooldown();
    void setEihwazCooldown(int cooldown);

    // For Lavaborn Pact
    int getLavabornCooldown();
    void setLavabornCooldown(int cooldown);

    // For Pacts
    PactType getActivePact();
    void setActivePact(PactType pact);
    boolean hasPact();
    
    int getPactCooldown();
    void setPactCooldown(int cooldown);
    
    // For specific pact effects
    int getExtraRuneSlots();
    void setExtraRuneSlots(int slots);
    
    float getMaxHealthReduction();
    void setMaxHealthReduction(float reduction);

    void tick();
} 