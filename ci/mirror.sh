#!/bin/bash

REPO_PATH="${PROJECT_HOME}/heuristic-learning/"

cd "${REPO_PATH}" && git pull origin master || :
git push github master 
exit 0
