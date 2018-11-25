#!/bin/bash

if [[ ${MAESTRO_TYME_SYNC} == "true" ]] ; then
    echo "Synchronizing time"
    ntpd -d -q -n -p pool.ntp.org
fi

MAESTRO_BROKER=${MAESTRO_BROKER:-mqtt://broker:1883}
MAESTRO_DATA_DIR=${MAESTRO_DATA_DIR:-/maestro/maestro-results-server/data}

${MAESTRO_APP_ROOT}/maestro-results-server/bin/maestro-results-server -m ${MAESTRO_BROKER} -d ${MAESTRO_DATA_DIR}