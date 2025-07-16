# Minecraft Version Support Guide

## Current Status
- ✅ **Minecraft 1.18.2** - Fully supported (current version)
- 🔶 **Minecraft 1.19.4** - Setup ready, needs API adaptations
- 🔶 **Minecraft 1.20.1** - Setup ready, needs API adaptations  
- 🔶 **Minecraft 1.16.5** - Setup ready, needs significant changes

## Quick Start

### Build for Current Version (1.18.2)
```bash
./gradlew build
```

### Build for Other Versions
```bash
./gradlew build -Pmc_version=1.19.4
./gradlew build -Pmc_version=1.20.1
./gradlew build -Pmc_version=1.16.5
```

## Major API Changes by Version

### 1.16.5 → 1.18.2 Changes
- `StringTextComponent` → `TextComponent`
- `ITextComponent` → `Component`
- Java 8 → Java 17
- Registry system changes

### 1.18.2 → 1.19.4 Changes
- `TextComponent` → `Component.literal()`
- `TranslatableComponent` → `Component.translatable()`
- Creative tab registration changes
- Event system changes
- Data generation changes

### 1.19.4 → 1.20.1 Changes
- Further creative tab refinements
- Component system stable
- Some event parameter changes

## Implementation Approach

### Phase 1: Core Compatibility (Current)
✅ Multi-version build system
✅ Version-specific mods.toml files
✅ Basic compatibility layer structure

### Phase 2: API Adaptation (Next Steps)
The following files need version-specific implementations:

1. **Text Components** (All classes using TextComponent/Component)
   - `PactSelectionScreen.java`
   - `RuneItem.java` 
   - All packet handlers
   - Event handlers

2. **Creative Tabs** (1.19.4+)
   - `RunicsMod.java` - Creative tab creation
   - `ModItems.java` - Item tab assignment

3. **Events** (Version-specific changes)
   - `ServerEventHandler.java` - Event parameter changes
   - `ClientEventHandler.java` - Client event changes

4. **Data Generation** (1.19.4+)
   - `ModItemModelProvider.java` - Constructor changes

### Phase 3: Testing & Distribution
- Build all versions
- Test functionality 
- Upload to mod platforms

## Practical Next Steps

### For 1.19.4 Support

1. **Update Component Usage**
```java
// Instead of:
new TextComponent("text")
new TranslatableComponent("key", args)

// Use VersionCompat:
VersionCompat.createTextComponent("text")
VersionCompat.createTranslatableComponent("key", args)
```

2. **Update Creative Tab (1.19.4+)**
```java
// 1.18.2 style:
public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.RUNIC_GEM.get());
    }
};

// 1.19.4+ style:
public static final CreativeModeTab TAB = CreativeModeTab.builder()
    .icon(() -> new ItemStack(ModItems.RUNIC_GEM.get()))
    .title(Component.translatable("itemGroup." + MODID))
    .build();
```

3. **Update Item Properties (1.19.4+)**
```java
// 1.18.2 style:
new Item.Properties().tab(RunicsMod.TAB)

// 1.19.4+ style:  
new Item.Properties() // Tab handled differently in 1.19.4+
```

### Recommended Implementation Strategy

1. **Start with 1.19.4**: Most similar to 1.18.2
2. **Create version-specific classes** for major differences
3. **Use compatibility layer** for minor differences
4. **Test thoroughly** before moving to next version

## Files That Need Version-Specific Updates

### High Priority (Core functionality)
- [ ] `src/main/java/com/runicsmod/runics/RunicsMod.java`
- [ ] `src/main/java/com/runicsmod/runics/init/ModItems.java`
- [ ] `src/main/java/com/runicsmod/runics/client/gui/PactSelectionScreen.java`
- [ ] `src/main/java/com/runicsmod/runics/items/RuneItem.java`

### Medium Priority (Network/Events)
- [ ] `src/main/java/com/runicsmod/runics/network/*.java`
- [ ] `src/main/java/com/runicsmod/runics/events/ServerEventHandler.java`
- [ ] `src/main/java/com/runicsmod/runics/client/ClientEventHandler.java`

### Low Priority (Data generation)
- [ ] `src/main/java/com/runicsmod/runics/data/ModItemModelProvider.java`
- [ ] `src/main/java/com/runicsmod/runics/client/PactKeybinds.java`

## Testing Checklist

For each version:
- [ ] Mod loads without errors
- [ ] Items render correctly
- [ ] Runes can be crafted and enabled
- [ ] Pacts work correctly
- [ ] GUI displays properly
- [ ] Network packets function
- [ ] Data generation works

## Distribution

Once all versions are working:

1. **Build all versions**: `./build-all-versions.sh`
2. **Upload to CurseForge**: Separate files per MC version
3. **Upload to Modrinth**: Version-specific uploads
4. **GitHub Release**: All JARs in one release

---

**Current Recommendation**: Focus on getting 1.19.4 working first, as it's the most commonly requested version and has manageable changes from 1.18.2. 