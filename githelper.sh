#!/bin/bash

#
# Branches
#
declare -a BRANCHES=(
    "master"
    "class/1-api"
    "class/2-core-test"
    "class/3-core-impl"
    "class/4-projection"
)

echo "--------------------------------------------------"
echo "Githelper script for propagating changes"
echo "across ${#BRANCHES[*]} branches starting with ${BRANCHES[0]}."
echo "--------------------------------------------------"

git fetch --all

COMMAND=""

case "$1" in
  "build")
    echo "Build command detected, will execute build of every branch"
    COMMAND="./mvnw clean install -T8 -B"
    ;;
  "push")
    echo "Push command detected, will push every branch"
    COMMAND="git push origin"
    ;;

  "push-public")
    echo "Push to publi command detected. will push every."
    COMMAND="git push public"
    ;;
  *)
    echo "No command detected, will just merge branches but let them locally. Try $0 build | push | push-public"
    ;;
esac

## now loop through the above array
for (( i = 1; i < ${#BRANCHES[*]}; ++ i ))
do
    PREVIOUS="${BRANCHES[i-1]}"
    CURRENT="${BRANCHES[i]}"

    echo "-----"
    echo "Checking out $CURRENT"
    git checkout $CURRENT
    echo "Merging changes from $PREVIOUS"
    git merge $PREVIOUS --no-edit

    echo "Executing command $COMMAND"
    `$COMMAND`
done

git checkout ${BRANCHES[0]}
