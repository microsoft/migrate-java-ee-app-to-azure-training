FROM jboss/wildfly

#
# Copy web application into deployment directory
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
