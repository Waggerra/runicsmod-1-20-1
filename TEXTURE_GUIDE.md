# Texture Guide for Runics Mod

## Required Texture Files

Place your custom 16x16 PNG textures in: `src/main/resources/assets/runics/textures/item/`

### Current Texture Files Needed:

1. **algiz_rune.png** - Protection rune (shield symbol)
2. **ansuz_rune.png** - Animal friend rune (mouth/communication symbol)
3. **raidho_rune.png** - Journey rune (wheel/travel symbol)
4. **sowulo_rune.png** - Fire rune (sun/lightning symbol)
5. **jera_rune.png** - Harvest rune (harvest/season symbol)
6. **isa_rune.png** - Ice rune (ice/stillness symbol)
7. **eihwaz_rune.png** - Protection from mobs rune (yew tree symbol)
8. **algiz_inverted_rune.png** - Camouflage rune (inverted protection symbol)
9. **runic_gem.png** - Crafting component (gem/crystal)

### Currently Present:
✅ algiz_rune.png
✅ ansuz_rune.png  
✅ raidho_rune.png
✅ sowulo_rune.png
✅ jera_rune.png
✅ isa_rune.png
✅ eihwaz_rune.png
✅ algiz_inverted_rune.png (placeholder)
✅ runic_gem.png (placeholder)

### Notes:
- All textures should be 16x16 pixels
- Use PNG format
- Transparent backgrounds recommended
- The placeholders (algiz_inverted_rune.png and runic_gem.png) are copies of algiz_rune.png
- Replace the placeholders with your custom designs

### After Adding Textures:
1. Run `./gradlew build` to rebuild the mod
2. Test in-game to ensure textures appear correctly
3. All items should now display proper names and textures

## Removed Items:
- **kenaz_rune** - Removed due to crashes (entity highlighting feature)

## Texture Specifications

- **Size**: 16x16 pixels (standard Minecraft item texture size)
- **Format**: PNG with transparency support
- **Style**: Should look like ancient runic symbols

## Suggested Designs

### Jera Rune (Harvest)
- Color scheme: Yellow/golden tones
- Symbol: Angular "J" shape or harvest-related symbol
- Background: Parchment-like texture

### Sowulo Rune (Sun)
- Color scheme: Red/orange/yellow (fire colors)
- Symbol: Angular "S" shape or sun-related symbol
- Background: Glowing or fiery texture

### Algiz Rune (Protection)
- Color scheme: Blue/silver tones
- Symbol: Angular "Y" or "Z" shape
- Background: Shield-like or metallic texture

### Ansuz Rune (Wisdom)
- Color scheme: Purple/mystical tones
- Symbol: Angular "A" or "F" shape
- Background: Mystical or ancient texture

## Alternative

If you don't want to create custom textures, you can temporarily use existing Minecraft textures by changing the texture paths in the model files to point to vanilla items like:
- `"layer0": "minecraft:item/paper"` for a simple placeholder

## Creating the Textures

1. Use any image editor (GIMP, Photoshop, Paint.NET, etc.)
2. Create a 16x16 pixel canvas
3. Design your rune symbol
4. Save as PNG with transparency
5. Place in the correct directory structure 