# Migrating the database

## What are we going to do in this step

In the previous step we made sure we could deploy the web application on Azure App Service without taking into account that it is using a database. In this step we are going to tackle the migration of the database.

## Create the PostgreSQL database on Azure

The initial web application uses an on-premise PostgreSQL database. Of course as a customer you can opt to keep your on-premise database and creating the appropriate connectivity to it. However we recommend moving it to our managed offering, Azure Database for PostgreSQL.

Lets create the PostgreSQL database on Azure

```shell
  az 
```
