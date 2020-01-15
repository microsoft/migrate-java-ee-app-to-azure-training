# Migrating the database

## What are we going to do in this step

In the previous step we made sure we could deploy the web application on Azure App
Service without taking into account that it is using a database. In this step we
are going to tackle the migration of the database.

## Setting up

To start the migration we are going to copy the application from the `01-initial`
directory into this directory.

To do so please issue the following command line
in your terminal:

```shell
mvn antrun:run@setup
```

## Create the resource group

```shell
az group create --name sharearound --location westus2
```

*Note if the output tells you the resource group already exists that is fine and
continue on.*

## Create the PostgreSQL database on Azure

The initial web application uses an on-premise PostgreSQL database. Of course as a
customer you can opt to keep your on-premise database and creating the appropriate
connectivity to it. However we recommend moving it to our managed offering Azure
Database for PostgreSQL.

To create the PostgreSQL database on Azure you will need a unique id as the DNS
space is shared across all Azure subscriptions. In a class room setting ask your
proctor what the value of the `<unique-id>` needs to be. If you are doing this
workshop by yourself use the current timestamp in YYYYMMDDHHSS format as your
`<unique-id>`.

Use the command line below and substitute `<unique-id>` with the appropriate
value:

```shell
az postgres server create --resource-group sharearound --name sharearound-<unique-id> --location westus2 --admin-user postgres --admin-password p0stgr@s1 --sku-name B_Gen5_1
```

While this command is running, please feel free to review the
[Azure Database for PostgreSQL documentation](https://docs.microsoft.com/en-us/azure/postgresql/)

Once the command completes take note of the output and capture the value of the
`fullyQualifiedDomainName` JSON property. It will look similar to
`sharearound-<unique-id>.postgres.database.azure.com`.

## Determine your own IP address

We need to know what our own external IP address is so we can remotely access the
database.

Execute the following command line:

```shell
echo `curl -s http://whatismyip.akamai.com/`
```

Please capture the external IP address as you will need it later.

## Open the firewall to allow access from your local IP address

Previously you determined your own external IP address. Now it is time to open up
the firewall so you can access PostgreSQL remotely.

Replace `<external-ip>` in the command line below with your own external IP
address and execute the command:

```shell
az postgres server firewall-rule create --resource-group sharearound --server sharearound-<unique-id> --name AllowMyIP --start-ip-address <external-ip> --end-ip-address <external-ip>
```

*Note if you install the PostgreSQL extension for Azure CLI you can simplify
creation of the database a bit, see
[az postgres](https://docs.microsoft.com/en-us/cli/azure/ext/db-up/postgres?view=azure-cli-latest)
for more information*

## Verifying you can access your database

Now that the firewall has been configured we need to verify that you can actually
access the database as we will need to load it up with some setup data.

Replace `<unique-id>` with your unique id for the database and execute the command
line below:

```shell
 psql --host sharearound-<unique-id>.postgres.database.azure.com --username postgres@sharearound-<unique-id> --dbname postgres --password
```

Note it will prompt for the password. Use the same password as the one you used to
create the database.

If you connected successfully you will see something similar to:

```shell
psql (12.1 (Debian 12.1-1.pgdg100+1), server 9.6.16)
SSL connection (protocol: TLSv1.2, cipher: ECDHE-RSA-AES256-GCM-SHA384, bits: 256, compression: off)
Type "help" for help.

postgres=>
```

## Populate the database with data

We are now going to start the process of populating the database.

Execute the command line below:

```sql
CREATE DATABASE sharearound;
```

This will create the `sharearound` database.

Note this might take a short while to complete.

Next we are going to use the database.

Execute the command line below to switch the `psql` context to the `sharearound`
database:

```shell
\c sharearound
```

It will prompt for the password again, please enter the same password as before.

The next step will be to load the database with some data.

```shell
\i src/main/postgres/load.sql
```

This will populate the database with our setup data.

To verify it worked you can execute the following command line:

```sql
SELECT COUNT(1) FROM item;
```

It should echo a number higher than zero.

Now exit the `psql` session using the following command line:

```shell
\q
```

## Changing the deployment to use the remote database

As JavaEE applications use JNDI to refer to their data sources we only have to
change the deployment to point the JNDI entry to the new database. So in the
following steps we will be changing the deployment to use the remote database.

Please add the following XML snippet to the `<configuration>` block just before the
end as denoted by `</configuration>` of the `azure-webapp-maven-plugin` plugin in
the pom.xml file.

```xml
<appSettings>
    <property>
        <name>POSTGRES_JDBC_URL</name>
        <value>${postgresJdbcUrl}</value>
    </property>
    <property>
        <name>POSTGRES_USERNAME</name>
        <value>${postgresUsername}</value>
    </property>
    <property>
        <name>POSTGRES_PASSWORD</name>
        <value>${postgresPassword}</value>
    </property>
</appSettings>
```

These will make it so that when you redeploy the Maven plugin will convey the app
settings to Azure App Service with the following names, POSTGRES_JDBC_URL,
POSTGRES_USERNAME and POSTGRES_PASSWORD.

Note we are using place holders here so we will have to add the defaults to the
end of the `<properties>` block in the pom.xml file. Please use the XML snippet
below for that and make sure you replace `UNIQUE_ID` with the unique id for the
database.

```xml
<postgresJdbcUrl>FILL_IN_JDBC_URL</postgresJdbcUrl>
<postgresUsername>FILL_IN_USERNAME</postgresUsername>
<postgresPassword>FILL_IN_PASSWORD</postgresPassword>
```

The next step is to update the `src/main/resources/META-INF/persistence.xml` file
to point to our database on the cloud.

Replace the `<jta-data-source>` block with the one below:

```xml
<jta-data-source>java:jboss/datasources/sharearoundDS</jta-data-source>
```

Now we need the JDBC driver for PostgreSQL as we need to upload it to App Service.

Please execute the following command line:

```shell
mvn initialize
```

Now all the files are staged in the `src/main/appservice` directory.

The next steps is to see if you have set any deployment credentials before:

```shell
az webapp deployment user show
```

If `publishingUsername` is not set to `null` you have previously set deployment
credentials, please use them in the following steps. Otherwise we are now going
to set the deployment credentials.

```shell
az webapp deployment user set --user-name sharearound-user
```

It will prompt for a password. Pick any password you want. If it does not conform
to the minimum requirements it will tell you. If that happens pick a password
that does conform.

We need to know the hostname of the FTP server we need to upload to. Please
replace `<unique-id>` with your unique id and execute the command line below.

```shell
az webapp deployment list-publishing-profiles --name sharearound-<unique-id> --resource-group sharearound
```

Look for `profileName` similar to `sharearound-<unique-id> - FTP`. Once you have
found it then look for the `publishUrl` in the same block. That will be the
correct FTP server URL for your web application.

So now you should have the the following:

1. The FTP username
2. The FTP password
3. The FTP hostname

Note when logging in you will have to prefix the FTP username with the appName,
e.g. `sharearound-<unique-id>\sharearound-user`

Now we are going to connect to the FTP server and upload the files from the
`src/main/appservice` directory into to the remote directory
`/site/deployments/tools/`.

From the `src/main/appservice` directory execute the following
command lines replacing `<unique-id>` with your unique id and the `<ftp-hostname>`
with the FTP hostname.

```shell
ncftp -u sharearound-<unique-id>\\sharearound-user <ftp-hostname>
cd /site/deployments/tools
put *
exit
```

## Build the web application

Execute the following command line:

```shell
mvn package
```

## Deploy the application

*Note if you have successfully completed "Migrating the web pages" you can skip
this step.*

To deploy the web application please replace `<unique-id>` with your unique id,
the `<postgres-username>` with your PostgreSQL username, the `<postgres-password>`
with the PostgreSQL password, the `<postgres-jdbc-url>` with the PostgreSQL JDBC
url and use the following commandline:

Note the postgresJdbcUrl should be similar to
`jdbc:postgresql://sharearound-UNIQUE_ID.postgres.database.azure.com:5432/sharearound?sslmode=require`

```shell
mvn azure-webapp:deploy -DappName=sharearound-<unique-id> -DpostgresUsername=<postgres-username> -DpostgresPassword=<postgres-password> -DpostgresJdbcUrl=<postgres-jdbc-url>
```

## Update the startup script

The next step is to set the startup script to our custom startup script.

Replace `<unique-id>` with your unique id and execute the following command line:

```shell
az webapp config set -g sharearound -n sharearound-<unique-id> --startup-file /home/site/deployments/tools/startup_script.sh
```

## Redeploy the application

To redeploy the web application please replace `<unique-id>` with your unique id,
the `<postgres-username>` with your PostgreSQL username, the `<postgres-password>`
with the PostgreSQL password, the `<postgres-jdbc-url>` with the PostgreSQL JDBC
url and use the following commandline:

Note the postgresJdbcUrl should be similar to
`jdbc:postgresql://sharearound-UNIQUE_ID.postgres.database.azure.com:5432/sharearound?sslmode=require`

```shell
mvn azure-webapp:deploy -DappName=sharearound-<unique-id> -DpostgresUsername=<postgres-username> -DpostgresPassword=<postgres-password> -DpostgresJdbcUrl=<postgres-jdbc-url>
```

Once the command completes it will show you the URL of the deployed web
application, it will look similar to
`https://sharearound-<unique-id>.azurewebsites.net`. Please capture this URL as
you will need it later.

Open your browser to the shown URL to verify that you have successfully deployed
web application.

[Previous](../02-migrating-web-pages/README.md) &nbsp; [Next](../04-adding-app-insights/README.md)
