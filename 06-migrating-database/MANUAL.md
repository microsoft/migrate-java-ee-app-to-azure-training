# Manual provisioning steps

> :bulb: Any PostgreSQL database on Azure shares the DNS namespace across all
> Azure subscriptions, so the UNIQUE_ID determined previously was used to make its
> name unique.

And now it is time to create the database.

Use the command line below:

```shell
az postgres server create --resource-group sharearound \
  --name sharearound-postgres-$UNIQUE_ID --location westus2 \
  --admin-user $PGUSER --admin-password $PGPASS \
  --sku-name B_Gen5_1
```

## What you accomplished

1. You have created an Azure Database for PostgreSQL.

[Back](README.md)
