# Migrating the database

## What are we going to do in this step

In the previous step we made sure we could deploy the web application on Azure
Kubernetes Service (AKS) without taking into account that it is using a database.
In this step we are going to tackle the migration of the database.

## Setting up

To start the migration we are going to copy the application from the `01-initial`
directory into this directory.

To do so please issue the following command line
in your terminal:

```shell
mvn antrun:run@setup
```

## Determine your unique id and set it in your environment

Some of the resources we are going to create need to have a unique id. In a class
room setting ask your proctor what the value of the `UNIQUE_ID` needs to be. If
you are doing this workshop by yourself use the same timestamp in `YYYYMMDDHHSS`
format as your unique id throughout the training.

Replacing `FILL_THIS_IN` with the value you determined above and execute the
command line below:

```shell
export UNIQUE_ID=FILL_THIS_IN
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

> Any PostgreSQL database on Azure shares the DNS namespace across all Azure
> subscriptions, so the UNIQUE_ID determined previously is used to make its name
> unique.

Before we go ahead and create the database we are going to set the Postgres admin
username and password in your environment. In a classroom setting ask your
proctor what the value of `PGUSER` and `PGPASS` needs to be. If you are doing this
workshop by yourself use the any combination you like.

To set the `PGUSER` environment variable use the following command line, replacing
`FILL_THIS_IN` with the appropriate value:

```shell
export PGUSER=FILL_THIS_IN
```

And for the `PGPASS` environment variable use the following command line,
replacing `FILL_THIS_IN` with the appropriate value:

```shell
export PGPASS=FILL_THIS_IN
```

And now it is time to create the database.

Use the command line below:

```shell
az postgres server create --resource-group sharearound \
  --name sharearound-postgres-$UNIQUE_ID --location westus2 \
  --admin-user $PGUSER --admin-password $PGPASS \
  --sku-name B_Gen5_1
```

While this command is running, please feel free to review the documentation
listed below at [More information](#more-information).

Now lets set the `PGHOST` environment variable so you can use it later.

Execute the following command line:

```shell
export PGHOST=sharearound-postgres-$UNIQUE_ID.postgres.database.azure.com
```

## Determine your own IP address

We need to know what our own external IP address is so we can remotely access the
database.

Execute the following command line:

```shell
export EXTERNAL_IP=`curl -s http://whatismyip.akamai.com/`
```

This will set the `EXTERNAL_IP` environment variable to our own external IP.

## Open the firewall to allow access from your own external IP address

Previously you determined your own external IP address.

Now it is time to open up the firewall so you can access PostgreSQL remotely.

Execute the following command line:

```shell
az postgres server firewall-rule create --resource-group sharearound \
  --server sharearound-postgres-$UNIQUE_ID --name AllowMyIP \
  --start-ip-address $EXTERNAL_IP --end-ip-address $EXTERNAL_IP
```

> Note if you install the PostgreSQL extension for Azure CLI you can simplify
> creation of the database a bit, see
> [az postgres](https://docs.microsoft.com/en-us/cli/azure/ext/db-up/postgres?view=azure-cli-latest)
> for more information

## Turn off requiring SSL connections

> In a production environment you should NOT disable requiring SSL connections
> between your application and the database. As the target of this training is
> migrating your JavaEE application, we are going to turn off requiring SSL
> connections.

Please execute the following command line:

```shell
az postgres server update --resource-group sharearound \
 --name sharearound-postgres-$UNIQUE_ID --ssl-enforcement Disabled
```

## Set the PGFULLUSER environment variable

Accessing the PostgreSQL database on Azure requires a longer version of your username which we will construct now.

Execute the command line below to set the PGFULLUSER environment variable:

```shell
export PGFULLUSER=$PGUSER@sharearound-postgres-$UNIQUE_ID
```

## Verifying you can access your database

Now that the firewall has been configured we need to verify that you can actually
access the database as we will need to load it up with some setup data.

Execute the command line below:

```shell
psql "dbname=postgres user=$PGFULLUSER"
```

Note it will prompt for the password.

Use the same password as the one you used to create the database.

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

Please update the `src/main/resources/META-INF/persistence.xml` file
to point to our database on the cloud.

Replace the `<jta-data-source>` block with the one below:

```xml
<jta-data-source>java:jboss/datasources/sharearoundDS</jta-data-source>
```

## Build the web application

Execute the following command line:

```shell
mvn package
```

In the `src/main/aks/Dockerfile` file we are now going to enable the database
section.

Follow the instructions in the file.

## Build the image on ACR

Since our AKS cluster needs to be able to pull the image from a Docker registry
we are going to build it using Azure CLI and target our ACR.

Execute the following command line to do so:

```shell
az acr build --registry sharearoundacr$UNIQUE_ID --image sharearound \
  --file src/main/aks/Dockerfile .
```

## Deploy to the AKS cluster

Determine the name of your ACR by executing the following command line:

```shell
echo sharearoundacr$UNIQUE_ID
```

Now open `src/main/aks/deployment.yml` in your editor and replace REGISTRY with
the value of the previous command (which is the name of your ACR).

As we want to be able to point the WildFly server to a PostgreSQL database without
having to rebuild the image we also have to update the
`src/main/aks/sharearound.yml` file to use environment variables.

> Note as this is a YAML file you have to be VERY careful with formatting.
> When you copy and paste it make sure the same amount of spaces are present
> as below.

Copy the text below and paste it into the file `src/main/aks/sharearound.yml` just
below `image`:

```yaml
        env:
        - name: PGJDBCURL
          value: FILL_IN
        - name: PGFULLUSER
          value: FILL_IN
        - name: PGPASS
          value: FILL_IN
```

And then replace each `FILL_IN` with the values of the corresponding environment
variables with the same name.

If you need your environment variables use the command line below:

```shell
export
```

> Note that exposing the JDBC URL, username and password in the YAML file is not
> a recommended practice. For the training it is sufficient to show that a JavaEE
> application can talk to the PostgreSQL database. In a production environment we
> recommend using either Kubernetes secrets, or Azure KeyVault.

And then finally deploy the application by using the following command line:

```shell
kubectl apply -f src/main/aks/sharearound.yml
```

The command will quickly return, but the deployment will still be going on.

We are going to use `kubectl` to wait for the service to become available:

Execute the following command line:

```shell
kubectl get service/sharearound --output wide -w
```

Now wait until you see the EXTERNAL-IP column populated with an IP address.

> Note if the command does not show the EXTERNAL-IP after a long while, please
> use `Ctrl+C` to cancel the command and then reissue the command without `-w`.

Once the IP address is there you are ready to open Microsoft Edge to
`http://EXTERNAL-IP:8080/`

You should see the same page as before, but now it is running on AKS!

## More information

1. [Azure Database for PostgreSQL documentation](https://docs.microsoft.com/en-us/azure/postgresql/)
1. [Azure CLI postgres extension documentation](https://docs.microsoft.com/en-us/cli/azure/ext/db-up/postgres?view=azure-cli-latest)
1. [Azure CLI commands for ACR](https://docs.microsoft.com/en-us/cli/azure/acr?view=azure-cli-latest)
1. [Kubectl Reference Documentation](https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands)
1. [psql — PostgreSQL interactive terminal documentation](https://www.postgresql.org/docs/current/app-psql.html)

[Previous](../02-migrating-web-pages/README.md) &nbsp; [Next](../04-adding-app-insights/README.md)

33m

TODO

1. Write snippet for PGJDBCURL
2. Reverify last deployment step
