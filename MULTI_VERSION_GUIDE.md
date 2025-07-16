# Multi-Version Compatibility Guide

This guide explains how to build and maintain the Runics Mod across multiple Minecraft versions (1.16.5, 1.18.2, 1.19.4, 1.20.1).

## Overview

The mod uses a multi-version architecture that shares common code while allowing version-specific adaptations for API changes between Minecraft versions.

## Supported Versions

| Minecraft Version | Forge Version | Java Version | Status |
|-------------------|---------------|--------------|--------|
| 1.16.5           | 36.2.39       | 8           | ✅ Supported |
| 1.18.2           | 40.2.0        | 17          | ✅ Supported (Default) |
| 1.19.4           | 45.2.0        | 17          | ✅ Supported |
| 1.20.1           | 47.2.0        | 17          | ✅ Supported |

## Directory Structure

```
src/
├── main/
│   ├── java/                          # Common code (all versions)
│   │   └── com/runicsmod/runics/
│   │       ├── compat/
│   │       │   └── VersionCompat.java  # 1.18.2 compatibility
│   │       └── ... (shared code)
│   ├── java-1.16.5/                   # 1.16.5-specific code
│   │   └── com/runicsmod/runics/compat/
│   │       └── VersionCompat.java      # 1.16.5 compatibility
│   ├── java-1.19.4/                   # 1.19.4-specific code
│   │   └── com/runicsmod/runics/compat/
│   │       └── VersionCompat.java      # 1.19.4 compatibility
│   ├── java-1.20.1/                   # 1.20.1-specific code
│   │   └── com/runicsmod/runics/compat/
│   │       └── VersionCompat.java      # 1.20.1 compatibility
│   ├── resources/                      # Common resources
│   ├── resources-1.16.5/               # 1.16.5-specific resources
│   │   └── META-INF/mods.toml
│   ├── resources-1.19.4/               # 1.19.4-specific resources
│   │   └── META-INF/mods.toml
│   └── resources-1.20.1/               # 1.20.1-specific resources
│       └── META-INF/mods.toml
```

## Building for Specific Versions

### Single Version Build

To build for a specific Minecraft version:

```bash
# Build for Minecraft 1.16.5
./gradlew build -Pmc_version=1.16.5

# Build for Minecraft 1.19.4
./gradlew build -Pmc_version=1.19.4

# Build for Minecraft 1.20.1
./gradlew build -Pmc_version=1.20.1

# Build for default version (1.18.2)
./gradlew build
```

### All Versions Build

To build for all supported versions at once:

```bash
# Using the build script
./build-all-versions.sh

# Or using Gradle task
./gradlew buildAll
```

### Individual Version Tasks

You can also build individual versions using specific Gradle tasks:

```bash
./gradlew build1165    # Builds for 1.16.5
./gradlew build1182    # Builds for 1.18.2  
./gradlew build1194    # Builds for 1.19.4
./gradlew build1201    # Builds for 1.20.1
```

## Version-Specific Adaptations

### API Compatibility Layer

The `VersionCompat` class handles API differences between versions:

- **1.16.5**: Uses `StringTextComponent` and `ITextComponent`
- **1.18.2**: Uses `TextComponent` and `Component`
- **1.19.4+**: Uses `Component.literal()` and `Component.translatable()`

### Usage Example

Instead of using version-specific APIs directly:

```java
// ❌ Don't do this (version-specific)
Component message = new TextComponent("Hello World");  // Only works in 1.18.2

// ✅ Do this (cross-version compatible)
Component message = VersionCompat.createTextComponent("Hello World");
```

### Adding Version-Specific Code

1. **For minor differences**: Use the `VersionCompat` class
2. **For major differences**: Create version-specific files in `java-[version]/`

Example of version-specific implementation:

```java
// src/main/java-1.16.5/com/runicsmod/runics/SomeFeature.java
public class SomeFeature {
    public void doSomething() {
        // 1.16.5-specific implementation
    }
}

// src/main/java-1.19.4/com/runicsmod/runics/SomeFeature.java  
public class SomeFeature {
    public void doSomething() {
        // 1.19.4-specific implementation
    }
}
```

## JAR Output

Built JARs are named with version suffixes:

- `runics-1.16.5-1.1.3-mc1.16.5.jar`
- `runics-1.18.2-1.1.3-mc1.18.2.jar`
- `runics-1.19.4-1.1.3-mc1.19.4.jar`
- `runics-1.20.1-1.1.3-mc1.20.1.jar`

## Distribution

### CurseForge

Upload each JAR separately with appropriate Minecraft version tags:

1. Upload `runics-1.16.5-*.jar` → Set Minecraft version to 1.16.5
2. Upload `runics-1.18.2-*.jar` → Set Minecraft version to 1.18.2
3. Upload `runics-1.19.4-*.jar` → Set Minecraft version to 1.19.4
4. Upload `runics-1.20.1-*.jar` → Set Minecraft version to 1.20.1

### Modrinth

Similar process - upload each version with correct Minecraft version metadata.

### GitHub Releases

Create a single release with all JAR files:

```
Release: Runics Mod v1.1.3
Files:
- runics-1.16.5-1.1.3-mc1.16.5.jar
- runics-1.18.2-1.1.3-mc1.18.2.jar  
- runics-1.19.4-1.1.3-mc1.19.4.jar
- runics-1.20.1-1.1.3-mc1.20.1.jar
```

## Troubleshooting

### Build Errors

1. **Java version mismatch**: Ensure you have the correct Java version installed
   - 1.16.5 requires Java 8
   - 1.18.2+ requires Java 17

2. **Missing dependencies**: Check Forge version compatibility

3. **API changes**: Check if new Minecraft versions require additional compatibility code

### Testing

Test each version in a clean environment:

```bash
# Test specific version
./gradlew runClient -Pmc_version=1.19.4
```

## Adding New Minecraft Versions

1. **Update `build.gradle`**: Add new version mapping
2. **Create directories**: `src/main/java-[version]/` and `src/main/resources-[version]/`
3. **Add compatibility**: Create `VersionCompat.java` for the new version
4. **Create `mods.toml`**: Version-specific metadata
5. **Test thoroughly**: Ensure all features work

## Best Practices

1. **Keep common code shared**: Only create version-specific code when necessary
2. **Use compatibility layer**: Abstract version differences through `VersionCompat`
3. **Test all versions**: Use the build script to verify all versions work
4. **Document changes**: Update this guide when adding new versions
5. **Version tags**: Use clear version tags in git for releases

---

**Happy multi-version modding!** 🎮 