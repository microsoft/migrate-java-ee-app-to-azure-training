FROM jboss/wildfly
COPY target/sharearound.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

#
# Setting up PostgreSQL
#
COPY src/main/aks/postgres* /usr/local/postgresql/
ENV PGJDBCURL jdbc://localhost:5432
ENV PGUSER postgres
ENV PGPASS postgres
RUN /bin/bash -c '/opt/jboss/wildfly/bin/standalone.sh --start-mode admin-only &' && \
    sleep 10 && \
    /opt/jboss/wildfly/bin/jboss-cli.sh -c --file=/usr/local/postgresql/postgres.cli && \
    sleep 10
