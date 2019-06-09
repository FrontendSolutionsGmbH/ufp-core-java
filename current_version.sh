#!/bin/sh
# script_version 1.03


version="0.0.3-SNAPSHOT"
command_tag="tag"

if [ -n "$1" ]; then
    shcommand=$1
     if [ "x$command_tag" == "x$shcommand" ]; then
        echo $(git describe --abbrev=0 --tags --always)
    else
        echo $version
    fi
else
    echo $version
fi
