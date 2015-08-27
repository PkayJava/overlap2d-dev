<?php

function recurse_copy($source, $dest)
{
    // Check for symlinks
    if (is_link($source)) {
        return symlink(readlink($source), $dest);
    }

    // Simple copy for a file
    if (is_file($source)) {
        return copy($source, $dest);
    }

    // Make destination directory
    if (!is_dir($dest)) {
        mkdir($dest);
    }

    // Loop through the folder
    $dir = dir($source);
    while (false !== $entry = $dir->read()) {
        // Skip pointers
        if ($entry == '.' || $entry == '..') {
            continue;
        }

        // Deep copy directories
        recurse_copy("$source/$entry", "$dest/$entry");
    }

    // Clean up
    $dir->close();
    return true;
}

function patch($sha, $source, $destination){

    $pwd = getcwd() . '/';

    $newSha = sha1_file($pwd . $source);

    if ($sha == $newSha){
        $temp = VQMod::modCheck($pwd . $source);
        copy($temp,$pwd . $destination);
    } else {
        echo $source . " was dirty => $newSha \n\r";
    }

}
