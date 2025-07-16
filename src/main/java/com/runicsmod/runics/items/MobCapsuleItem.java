package com.runicsmod.runics.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class MobCapsuleItem extends Item {
    
    public MobCapsuleItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        
        if (level.isClientSide || player == null) {
            return InteractionResult.SUCCESS;
        }
        
        CompoundTag nbt = stack.getOrCreateTag();
        
        // If capsule has a mob, try to release it
        if (nbt.contains("StoredMob")) {
            return releaseMob(level, pos.above(), stack, player);
        } else {
            // Try to capture a mob
            return captureMob(level, pos, stack, player);
        }
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }
        
        CompoundTag nbt = stack.getOrCreateTag();
        
        // If capsule has a mob, try to release it at player's position
        if (nbt.contains("StoredMob")) {
            BlockPos releasePos = player.blockPosition();
            InteractionResult result = releaseMob(level, releasePos, stack, player);
            return result == InteractionResult.SUCCESS ? 
                InteractionResultHolder.success(stack) : 
                InteractionResultHolder.fail(stack);
        }
        
        return InteractionResultHolder.pass(stack);
    }
    
    private InteractionResult captureMob(Level level, BlockPos pos, ItemStack stack, Player player) {
        // Find nearby living entities within 3 block radius
        AABB searchArea = new AABB(pos).inflate(3.0);
        List<LivingEntity> nearbyMobs = level.getEntitiesOfClass(LivingEntity.class, searchArea);
        
        for (LivingEntity entity : nearbyMobs) {
            // Skip players
            if (entity instanceof Player) continue;
            
            // Check if this is a tamed animal belonging to the player
            boolean isTamedByPlayer = false;
            if (entity instanceof TamableAnimal tamable) {
                isTamedByPlayer = tamable.isTame() && tamable.getOwner() == player;
            }
            // Support for Dragon Mounts: Legacy mod dragons (check by class name to avoid hard dependency)
            else if (entity.getClass().getName().contains("dragon") || entity.getClass().getName().contains("Dragon")) {
                // Try to check if it's tamed by this player using reflection for mod compatibility
                try {
                    var ownerMethod = entity.getClass().getMethod("getOwner");
                    var isTamedMethod = entity.getClass().getMethod("isTamed");
                    
                    Object owner = ownerMethod.invoke(entity);
                    Boolean tamed = (Boolean) isTamedMethod.invoke(entity);
                    
                    isTamedByPlayer = tamed && owner == player;
                } catch (Exception e) {
                    // Reflection failed, fall back to health check
                    isTamedByPlayer = false;
                }
            }
            
            // Capture if: tamed by player OR has less than 5 HP
            if (isTamedByPlayer || entity.getHealth() < 5.0f) {
                return captureMobEntity(entity, stack, player, isTamedByPlayer);
            }
        }
        
        player.sendSystemMessage(Component.literal("§cNo capturable mobs found nearby (must be tamed by you or have less than 5 HP)"));
        return InteractionResult.FAIL;
    }
    
    private InteractionResult captureMobEntity(LivingEntity entity, ItemStack stack, Player player, boolean isTamed) {
        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag mobData = new CompoundTag();
        
        // Store mob data
        entity.save(mobData);
        nbt.put("StoredMob", mobData);
        nbt.putString("MobName", entity.getDisplayName().getString());
        nbt.putFloat("MobHealth", entity.getHealth());
        nbt.putBoolean("WasTamed", isTamed);
        
        // Remove the entity from world
        entity.discard();
        
        String captureReason = isTamed ? " (tamed)" : " with " + String.format("%.1f", entity.getHealth()) + " HP";
        player.sendSystemMessage(Component.literal("§aCaptured " + entity.getDisplayName().getString() + captureReason));
        
        return InteractionResult.SUCCESS;
    }
    
    // Overload for backward compatibility
    private InteractionResult captureMobEntity(LivingEntity entity, ItemStack stack, Player player) {
        return captureMobEntity(entity, stack, player, false);
    }
    
    private InteractionResult releaseMob(Level level, BlockPos pos, ItemStack stack, Player player) {
        CompoundTag nbt = stack.getOrCreateTag();
        
        if (!nbt.contains("StoredMob")) {
            return InteractionResult.FAIL;
        }
        
        CompoundTag mobData = nbt.getCompound("StoredMob");
        
        try {
            // Create entity from stored data
            Entity entity = EntityType.loadEntityRecursive(mobData, level, (e) -> {
                e.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                return e;
            });
            
            if (entity != null && level instanceof ServerLevel serverLevel) {
                serverLevel.addFreshEntity(entity);
                
                // Clear the capsule
                nbt.remove("StoredMob");
                nbt.remove("MobName");
                nbt.remove("MobHealth");
                
                player.sendSystemMessage(Component.literal("§aReleased " + entity.getDisplayName().getString()));
                return InteractionResult.SUCCESS;
            }
        } catch (Exception e) {
            player.sendSystemMessage(Component.literal("§cFailed to release mob: " + e.getMessage()));
        }
        
        return InteractionResult.FAIL;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag nbt = stack.getOrCreateTag();
        
        if (nbt.contains("StoredMob")) {
            String mobName = nbt.getString("MobName");
            float health = nbt.getFloat("MobHealth");
            boolean wasTamed = nbt.getBoolean("WasTamed");
            
            tooltip.add(Component.literal("§7Contains: §f" + mobName));
            tooltip.add(Component.literal("§7Health: §c" + String.format("%.1f", health) + " HP"));
            if (wasTamed) {
                tooltip.add(Component.literal("§7§o(Was tamed)"));
            }
            tooltip.add(Component.literal("§7Right-click to release"));
        } else {
            tooltip.add(Component.literal("§7Empty capsule"));
            tooltip.add(Component.literal("§7Use near weakened mobs (< 5 HP)"));
            tooltip.add(Component.literal("§7Or tamed animals"));
        }
        
        super.appendHoverText(stack, level, tooltip, flag);
    }
    
    @Override
    public boolean isFoil(ItemStack stack) {
        // Make the capsule glow with enchanted effect
        return true;
    }
} 