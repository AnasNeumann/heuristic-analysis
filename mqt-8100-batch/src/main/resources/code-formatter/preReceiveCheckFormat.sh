#!/bin/bash

DIR_HOOKS="/data/gitlab/git-data/repositories/POSC/Hub.git/custom_hooks"

export LC_ALL=fr_FR.UTF-8
export LANG=fr_FR.UTF-8

checkFormat() {
    oldrev="$1"
    newrev="$2"
    files=$(git diff --name-only $oldrev $newrev | grep -e "\.java$")
    if [[ -n $files ]]; then
        rm -rf "${DIR_HOOKS}/tmp/tmp*"
        TEMPDIR="${DIR_HOOKS}$(mktemp -d)"
        TEMPDIR2="${DIR_HOOKS}$(mktemp -d)"

        while read file; do
            echo "Creating dir d1=${TEMPDIR}/$(dirname ${file}) and d2=${TEMPDIR2}/$(dirname ${file}) for $file"
            mkdir -p "${TEMPDIR}/$(dirname ${file})"
            mkdir -p "${TEMPDIR2}/$(dirname ${file})"
            echo "Creating files f1=${TEMPDIR}/${file} and f2=${TEMPDIR2}/${file}"
            git show $newrev:$file > "${TEMPDIR}/${file}"
            git show $newrev:$file > "${TEMPDIR2}/${file}"
        done <<< "$files"

        files_to_cmpar=($(find $TEMPDIR2 -name '*.java'))
        files_to_check=($(find $TEMPDIR  -name '*.java'))
        echo "${files_to_check[@]}"

        echo "${#files_to_check[@]} files to check..."
        if [[ ${#files_to_check[@]} -gt 0 ]]; then
            "$DIR_HOOKS"/codeFormat.sh "${files_to_check[@]}" -b

            for (( i=0; i<${#files_to_check[@]} ; i++ )); do
                file1=${files_to_check[$i]}
                file2=${files_to_cmpar[$i]}
                echo "Diff between $file1 (formatted) and $file2"
                if [[ $(diff $file1 $file2) ]]; then
                     echo "Bad formatting : $vdiff"
                     exit 1
                fi
            done

        fi
    fi
}
