#!/bin/bash
 set -x
echo "UpdateVersion v 1.03"
version=$(sh current_version.sh)
command_tag="tag"
command_postfix="add"
command_full="full"

if [ -n "$1" ]; then
    shcommand=$1
    echo "Command: '"$shcommand"'"
    if [ "x$command_tag" == "x$shcommand" ]; then
        tag=$(sh current_version.sh tag)
        echo "Using Tag of Repository '"$tag"'"
        if [ -n "$tag" ]; then
            version=$tag
        else
            echo "Error: Tag not valid, using default"]
        fi

    elif [ "x$command_postfix" = "x$shcommand" ]; then
        if [ -n "$2" ]; then
            postfix=$2
            echo "Parameter Postfix found..."
            version=$version$postfix
        else
            echo "Parameter Postfix not found..."
        fi
    elif [ "x$command_full" = "x$shcommand" ]; then
        postfix=$2
        echo "Parameter Full found..."
        version=$postfix
    else
        echo "Command '"$shcommand"' not known"
    fi
else
    echo "No command set, using default..."

fi

echo "Setting version to "$version

# mvn versions:set -DnewVersion=$version
#
#mvn versions:commit

 mvn versions:set -DnewVersion=$version

mvn versions:commit
