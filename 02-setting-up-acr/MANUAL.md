# Manual provisioning steps

## Create the Resource Group

We need a resource group to host the ACR.

Please use the following command line to create the resource group:

```shell
az group create --name sharearound --location westus2
```

*Note if the output tells you the resource group already exists that is fine and
continue on.*

## Create the ACR

Now we are going to create your ACR.

Please execute the command line below:

```shell
az acr create --name sharearoundacr$UNIQUE_ID --location westus2 \
  --resource-group sharearound --sku Basic
```

## What you accomplished

1. You have created a resource group to host your resources.
1. You have created an ACR to host your Docker images.

[Back](README.md)
