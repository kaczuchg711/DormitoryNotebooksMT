#!/bin/bash

# Check if the input file exists
if [ ! -f "$1" ]; then
    echo "Usage: $0 <command_file>"
    exit 1
fi

# Read each line from the input file and execute the command
while read -r line; do
    eval "$line"
done < "$1"
