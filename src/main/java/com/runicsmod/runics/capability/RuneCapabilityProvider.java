package com.runicsmod.runics.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RuneCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<IRuneCapability> RUNE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    
    private final RuneCapability instance = new RuneCapability();
    private final LazyOptional<IRuneCapability> holder = LazyOptional.of(() -> instance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return RUNE_CAPABILITY.orEmpty(cap, holder);
    }

    @Override
    public CompoundTag serializeNBT() {
        try {
            return instance.serializeNBT();
        } catch (Exception e) {
            System.err.println("Runics Mod: Error serializing capability NBT - " + e.getMessage());
            // Return empty tag to prevent crashes
            return new CompoundTag();
        }
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        try {
            instance.deserializeNBT(nbt);
        } catch (Exception e) {
            System.err.println("Runics Mod: Error deserializing capability NBT - " + e.getMessage());
            // Reset to default state to prevent crashes
            try {
                instance.deserializeNBT(new CompoundTag());
            } catch (Exception resetError) {
                System.err.println("Runics Mod: Error resetting capability - " + resetError.getMessage());
            }
        }
    }
} 