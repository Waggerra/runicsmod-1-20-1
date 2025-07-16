#!/bin/bash

# Build script for all supported Minecraft versions
# This script builds the Runics Mod for all supported Minecraft versions

echo "Building Runics Mod for all supported Minecraft versions..."

# Array of supported versions
versions=("1.16.5" "1.18.2" "1.19.4" "1.20.1")

# Create output directory
mkdir -p build/distributions

# Build each version
for version in "${versions[@]}"; do
    echo ""
    echo "========================================="
    echo "Building for Minecraft $version"
    echo "========================================="
    
    # Clean previous build
    ./gradlew clean
    
    # Build for specific version
    ./gradlew build -Pmc_version=$version
    
    if [ $? -eq 0 ]; then
        echo "✅ Successfully built for Minecraft $version"
        
        # Copy the built JAR to distributions folder with clear naming
        find build/libs -name "*$version*.jar" -exec cp {} build/distributions/ \;
    else
        echo "❌ Failed to build for Minecraft $version"
        exit 1
    fi
done

echo ""
echo "========================================="
echo "All versions built successfully!"
echo "========================================="
echo ""
echo "Built JARs are available in build/distributions/"
ls -la build/distributions/

echo ""
echo "You can now upload these JARs to:"
echo "- CurseForge"
echo "- Modrinth" 
echo "- GitHub Releases"
echo ""
echo "Each JAR is specifically built for its Minecraft version." 