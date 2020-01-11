# Migrating the database

## What are we going to do in this step

In the previous step we made sure we could deploy the web application on Azure App Service without taking into account that it is using a database. In this step we are going to tackle the migration of the database.

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

*Note if the output tells you the resource group already exists that is fine and continue on.*

## Create the PostgreSQL database on Azure

The initial web application uses an on-premise PostgreSQL database. Of course as a customer you can opt to keep your on-premise database and creating the appropriate connectivity to it. However we recommend moving it to our managed offering Azure Database for PostgreSQL.

To create the PostgreSQL database on Azure you will need a unique id as the DNS space is shared across all Azure subscriptions. In a class room setting ask your proctor what the value of the `<unique-id>` needs to be. If you are doing this workshop by yourself use the current timestamp in YYYYMMDDHHSS format as your `<unique-id>`.

Use the command line below and substitute `<unique-id>` with the appropriate value:

```shell
az postgres server create --resource-group sharearound --name sharearound-<unique-id> --location westus2 --admin-user postgres --admin-password p0stgr@s1 --sku-name B_Gen5_1
```

While this command is running, please feel free to review [Azure Database for PostgreSQL documentation](https://docs.microsoft.com/en-us/azure/postgresql/)

Once the command completes take note of the output and capture the value of the `fullyQualifiedDomainName` JSON property. It will look similar to `sharearound-<unique-id>.postgres.database.azure.com`.

## Determine your own IP address

We need to know what our own external IP address is so we can remotely access the  database.

Execute the following command line:

```shell
echo `curl -s http://whatismyip.akamai.com/`
```

Please capture the external IP address as you will need it later.

## Open the firewall to allow access from your local IP address

Previously you determined your own external IP address. Now it is time to open up the firewall so you can access PostgreSQL remotely.

Replace `<external-ip>` in the command line below with your own external IP address and execute the command:

```shell
az postgres server firewall-rule create --resource-group sharearound --server sharearound-<unique-id> --name AllowMyIP --start-ip-address <external-ip> --end-ip-address <external-ip>
```

## Verifying you can access your database

Now that the firewall has been configured we need to verify that you can actually access the database as we will need to load it up with some setup data.

Execute the following command line:

```shell
docker run --rm -it postgres bash
```

This will show you a command prompt which has the `psql` binrary in its path.

The next step is to use `psql` to connect to your remote database.

Replace `<unique-id>` with your unique id for the database and execute the command line below:

```shell
 psql --host sharearound-<unique-id>.postgres.database.azure.com --username postgres@sharearound-<unique-id> --dbname postgres --password
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

Execute the command line below to switch the `psql` context to the `sharearound` database:

```shell
\c sharearound
```

It will prompt for the password again, please enter the same password as before.

The next step will be to load the database with some data.

Please open `src/main/postgres/load.sql` in a text editor of your choice, copy the contents and then paste its contents into the `psql` session.

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

And then also exit the running Docker container by executing the command below:

```shell
exit
```

## Changing the deployment to use the remote database

As JavaEE applications use JNDI to refer to the data sources used we only have to change the deployment to point the JNDI entry to the new database. So in the following steps we will be changing the deployment to use the remote database.
