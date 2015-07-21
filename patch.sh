#!/bin/sh

PROJECT_HOME="$(cd "$(dirname "$0")"/ && pwd)"

Git_Home="$PROJECT_HOME/git"
Source_Home="$PROJECT_HOME/source"

VisUI_Git="https://github.com/kotcrab/VisEditor.git"
VisUI_Home="$Git_Home/VisEditor"

# Overlap2D_Git="https://github.com/UnderwaterApps/overlap2d.git"
Overlap2D_Git="https://github.com/azakhary/overlap2d.git"
Overlap2D_Home="$Git_Home/overlap2d"

# Overlap2D_Runtime_Git="https://github.com/UnderwaterApps/overlap2d-runtime-libgdx.git"
Overlap2D_Runtime_Git="https://github.com/azakhary/overlap2d-runtime-libgdx.git"
Overlap2D_Runtime_Home="$Git_Home/overlap2d-runtime-libgdx"

Ashley_Git="https://github.com/libgdx/ashley.git"
Ashley_Home="$Git_Home/ashley"

Spine_Git="https://github.com/EsotericSoftware/spine-runtimes.git"
Spine_Home="$Git_Home/spine-runtimes"

mkdir -p $Git_Home
mkdir -p $Source_Home

if [ ! -d "$Ashley_Home" ]; then
    cd $Git_Home
    git clone $Ashley_Git
else
    cd $Ashley_Home
    git pull
fi

if [ ! -d "$Overlap2D_Home" ]; then
    cd $Git_Home
    git clone $Overlap2D_Git
    cd $Overlap2D_Home
    git checkout mvc-ashley
else
    cd $Overlap2D_Home
    git pull
    git checkout mvc-ashley
fi

if [ ! -d "$Overlap2D_Runtime_Home" ]; then
    cd $Git_Home
    git clone $Overlap2D_Runtime_Git
    cd $Overlap2D_Runtime_Home
    git checkout dev-ashley
else
    cd $Overlap2D_Runtime_Home
    git pull
    git checkout dev-ashley
fi

if [ ! -d "$VisUI_Home" ]; then
    cd $Git_Home
    git clone $VisUI_Git
else
    cd $VisUI_Home
    git pull
fi

if [ ! -d "$Spine_Home" ]; then
    cd $Git_Home
    git clone $Spine_Git
else
    cd $Spine_Home
    git pull
fi

rm -rf "$PROJECT_HOME/vqmod/checked.cache"
rm -rf "$PROJECT_HOME/vqmod/mods.cache"

cd $PROJECT_HOME

php patch.php