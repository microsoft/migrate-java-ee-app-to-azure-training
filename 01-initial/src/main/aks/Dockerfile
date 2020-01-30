#
# Officially supported Zulu JDK 
#
# For support or general questions go to:
#
# https://support.microsoft.com/en-us/help/4026305/sql-contact-microsoft-azure-support
#
FROM mcr.microsoft.com/java/jdk:8u242-zulu-centos

#
# WildFly 
#

# Install packages necessary to run WildFly
RUN yum update -y && yum -y install bsdtar unzip && yum clean all

# Create a user and group used to launch processes
# The user ID 1000 is the default for the first "regular" user on Fedora/RHEL,
# so there is a high chance that this ID will be equal to the current user
# making it easier to use volumes (no permission issues)
RUN groupadd -r jboss -g 1000 && useradd -u 1000 -r -g jboss -m -d /opt/jboss -s /sbin/nologin -c "JBoss user" jboss && \
    chmod 755 /opt/jboss

# Set the working directory to jboss' user home directory
WORKDIR /opt/jboss

# Specify the user which should be used to execute all commands below
USER jboss

# Set the WILDFLY_VERSION env variable
ENV WILDFLY_VERSION 18.0.1.Final
ENV WILDFLY_SHA1 ef0372589a0f08c36b15360fe7291721a7e3f7d9
ENV JBOSS_HOME /opt/jboss/wildfly

USER root

# Add the WildFly distribution to /opt, and make wildfly the owner of the extracted tar content
# Make sure the distribution is available from a well-known place
RUN cd $HOME \
    && curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz \
    && sha1sum wildfly-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 \
    && tar xf wildfly-$WILDFLY_VERSION.tar.gz \
    && mv $HOME/wildfly-$WILDFLY_VERSION $JBOSS_HOME \
    && rm wildfly-$WILDFLY_VERSION.tar.gz \
    && chown -R jboss:0 ${JBOSS_HOME} \
    && chmod -R g+rw ${JBOSS_HOME}

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

USER jboss

# Expose the ports we're interested in
EXPOSE 8080

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]

#
# Application 
#
# Copy the web application into deployment directory.
#

COPY target/sharearound.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

#
# Set up PostgreSQL
#
# To enable PostgreSQL remove the # from each line in the block below.
#

#COPY src/main/aks/postgres* /usr/local/postgresql/
#ENV PGJDBCURL jdbc:postgresql://localhost:5432/sharearound?sslmode=require
#ENV PGFULLUSER username@localhost
#ENV PGPASS password
#RUN /bin/bash -c '/opt/jboss/wildfly/bin/standalone.sh --start-mode admin-only &' && \
#    sleep 15 && \
#    /opt/jboss/wildfly/bin/jboss-cli.sh -c --file=/usr/local/postgresql/postgres.cli && \
#    sleep 15
