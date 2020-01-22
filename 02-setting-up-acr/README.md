# Setting up Azure Container Registry

## What are we going to do in this step

As we are targeting deployment on Azure Kubernetes Service (AKS) we need a Docker
registry from which we can pull our container images. We are going to setup an
Azure Container Registry (ACR), which is a Docker registry,

## Create the Resource Group

We need a resource group to host the ACR.

Please use the following command line to create the resource group:

```shell
az group create --name sharearound --location westus2
```

*Note if the output tells you the resource group already exists that is fine and
continue on.*

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

## Create the ACR

Now we are going to create your ACR.

Please execute the command line below:

```shell
az acr create --name sharearoundacr$UNIQUE_ID --location westus2 \
  --resource-group sharearound --sku Basic
```

## Login into your ACR

Now log into your ACR using the command line below:

```shell
az acr login -n sharearoundacr$UNIQUE_ID
```

## More information

For more information about ACR, see:

1. [Azure Container Registry product page](https://azure.microsoft.com/en-us/services/container-registry/)
1. [Azure Container Registry documentation](https://docs.microsoft.com/en-us/azure/container-registry/)
1. [Azure CLI commands for ACR](https://docs.microsoft.com/en-us/cli/azure/acr?view=azure-cli-latest)

[Previous](../01-initial/README.md) &nbsp; [Next](../03-setting-up-aks/README.md)

6m
