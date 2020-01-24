FROM mcr.microsoft.com/java/maven:8-zulu-debian9

#
# Install common tools
#
RUN apt-get update -y && \
    apt-get upgrade -y && \
    apt-get install -y apt-utils git procps vim wget && \
    apt autoremove -y

#
# Install Azure CLI
#
RUN apt-get update -y && \
    apt-get upgrade -y && \
    apt-get install ca-certificates curl apt-transport-https lsb-release gnupg -y && \
    curl -sL https://packages.microsoft.com/keys/microsoft.asc | \
        gpg --dearmor | \
        tee /etc/apt/trusted.gpg.d/microsoft.asc.gpg > /dev/null && \
    AZ_REPO=$(lsb_release -cs) && \
    echo "deb [arch=amd64] https://packages.microsoft.com/repos/azure-cli/ $AZ_REPO main" | \
        tee /etc/apt/sources.list.d/azure-cli.list && \
    apt-get update -y && \
    apt-get upgrade -y && \
    apt-get install -y azure-cli && \
    apt autoremove -y

#
# Install Docker CLI
#
RUN apt-get update -y && \
    apt-get install -y apt-transport-https ca-certificates curl gnupg2 \
        software-properties-common && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    add-apt-repository \
        "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" && \
    apt-get update -y && \
    apt-get upgrade -y && \
    apt-get install -y docker-ce-cli && \
    apt autoremove -y

#
# Install Docker compose
#
RUN curl -L "https://github.com/docker/compose/releases/download/1.25.3/docker-compose-$(uname -s)-$(uname -m)" \
    -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose

#
# Install PostgreSQL client
#
RUN apt-get install -y postgresql-client && \
    apt autoremove -y

#
# entrypoint that keeps container running and accepting SIGTERM properly
#
COPY entrypoint.sh /
ENTRYPOINT ["/entrypoint.sh"]

#
# Set work directory to /mnt
#
WORKDIR /mnt
