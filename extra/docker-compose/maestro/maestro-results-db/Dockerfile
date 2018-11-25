FROM centos:7 AS buildimg
MAINTAINER Otavio Rodolfo Piske <angusyoung@gmail.com>
ARG MAESTRO_VERSION
ENV MAESTRO_VERSION ${MAESTRO_VERSION:-1.5.0-SNAPSHOT}
ARG MAESTRO_BRANCH
ENV MAESTRO_BRANCH ${MAESTRO_BRANCH:-devel}
LABEL MAESTRO_VERSION=${MAESTRO_VERSION}
RUN yum install -y java-1.8.0-openjdk-devel which unzip zip wget
ENV JAVA_HOME /etc/alternatives/jre
WORKDIR /root/build
RUN wget https://github.com/maestro-performance/maestro-java/archive/${MAESTRO_BRANCH}.zip -O maestro-java.zip && unzip maestro-java.zip && ln -s maestro-java-${MAESTRO_BRANCH} maestro-java
RUN cd maestro-java && ./mvnw -DskipTests=true clean install
WORKDIR /root/build
RUN wget https://github.com/maestro-performance/maestro-results-db/archive/${MAESTRO_BRANCH}.zip -O maestro-results-db.zip && unzip maestro-results-db.zip && ln -s maestro-results-db-${MAESTRO_BRANCH} maestro-results-db
RUN cd maestro-results-db && ./mvnw -DskipTests=true -PPackage clean package

ENV MAESTRO_APP_ROOT /opt/maestro
RUN mkdir -p ${MAESTRO_APP_ROOT}
WORKDIR ${MAESTRO_APP_ROOT}
ENV MAESTRO_WORKER_TYPE results-results-server
RUN mkdir -p maestro-${MAESTRO_WORKER_TYPE} && tar --strip-components=1 -xvf /root/build/maestro-results-db/maestro-${MAESTRO_WORKER_TYPE}/target/maestro-${MAESTRO_WORKER_TYPE}-${MAESTRO_VERSION}-bin.tar.gz -C maestro-${MAESTRO_WORKER_TYPE}

# Base image for everything
FROM centos:7 AS maestro-results-db-base
MAINTAINER Otavio Rodolfo Piske <angusyoung@gmail.com>
ARG MAESTRO_VERSION
ENV MAESTRO_VERSION ${MAESTRO_VERSION:-1.5.0-SNAPSHOT}
LABEL MAESTRO_VERSION=${MAESTRO_VERSION}
ENV MAESTRO_APP_ROOT /opt/maestro
ENV JAVA_HOME /etc/alternatives/jre
RUN mkdir -p ${MAESTRO_APP_ROOT} && mkdir -p /maestro
WORKDIR ${MAESTRO_APP_ROOT}
RUN yum install -y java-1.8.0-openjdk-headless unzip which ntp rsync && yum clean all

## Worker ##
FROM maestro-base AS maestro-results-server
ENV MAESTRO_WORKER_TYPE maestro-results-server
MAINTAINER Otavio Rodolfo Piske <angusyoung@gmail.com>
LABEL MAESTRO_WORKER_TYPE=${MAESTRO_WORKER_TYPE}
COPY --from=buildimg ${MAESTRO_APP_ROOT}/maestro-${MAESTRO_WORKER_TYPE} maestro-${MAESTRO_WORKER_TYPE}

ADD ${MAESTRO_WORKER_TYPE}/log4j.properties ${MAESTRO_APP_ROOT}/maestro-${MAESTRO_WORKER_TYPE}/config/log4j.properties
ADD ${MAESTRO_WORKER_TYPE}/maestro-${MAESTRO_WORKER_TYPE}.properties ${MAESTRO_APP_ROOT}/maestro-${MAESTRO_WORKER_TYPE}/config/maestro-${MAESTRO_WORKER_TYPE}.properties
# ADD ${MAESTRO_WORKER_TYPE}/maestro-container-wrapper.sh /usr/bin/maestro-container-wrapper
CMD [ "sh", "-c", "/usr/bin/maestro-container-wrapper"]