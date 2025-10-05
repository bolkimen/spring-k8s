#!/usr/bin/env bash

export SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

python3 -m venv $SCRIPT_DIR/.venv
source $SCRIPT_DIR/.venv/bin/activate

pip3 install -r $SCRIPT_DIR/requirements.txt
