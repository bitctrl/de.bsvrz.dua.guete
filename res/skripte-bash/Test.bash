#!/bin/bash
. ../../testEinstellungen.sh

killall java
sleep 10

find ../konfiguration/ -name "*.lock" | xargs rm

. KernsoftwareSystem.bash
sleep 5

cd ${distroot}/distributionspakete/de.bsvrz.dua.guete/skripte-bash/
. prueffall_extra.bash

