MAESTRO_VERSION?=1.5.3-SNAPSHOT
MAESTRO_BRANCH?=master
MAESTRO_LABEL_VERSION?=${MAESTRO_BRANCH}

default: build

clean:
	docker rmi -f maestroperf/maestro-results-server:${MAESTRO_VERSION}
	docker rmi -f maestroperf/maestro-results-server:${MAESTRO_LABEL_VERSION}
	docker rmi -f maestro-results-server

build:
	cd maestro-results-db && docker build --build-arg MAESTRO_VERSION=${MAESTRO_VERSION} --build-arg MAESTRO_BRANCH=${MAESTRO_BRANCH} -t maestroperf/maestro-results-server:${MAESTRO_VERSION} -t maestroperf/maestro-results-server:${MAESTRO_LABEL_VERSION}  --target maestro-results-server .

push: build
	docker push maestroperf/maestro-results-server:${MAESTRO_VERSION}
	docker push maestroperf/maestro-results-server:${MAESTRO_LABEL_VERSION}

all: push

.PHONY: broker clean all client exporter maestro suts