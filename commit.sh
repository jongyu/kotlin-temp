#!/usr/bin/env bash

git add .
git commit -m "$1"
git push -u origin $(git symbolic-ref --short -q HEAD)