# Setting up Azure Container Registry

## What are we going to do in this step

As we are targeting deployment on Azure Kubernetes Service (AKS) we need a Docker
registry from which we can pull our container images. In this step we will verify
we can access the Azure Container Registry (ACR).

## Start in the correct directory

Please execute the command below:

```shell
cd $BASEDIR/02-setting-up-acr
```

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

## Login into your ACR

Now log into your ACR using the command line below:

```shell
az acr login -n sharearoundacr$UNIQUE_ID
```

## What you accomplished

1. You have created a resource group to host your resources.
1. You have created an ACR to host your Docker images.
1. You have verified you can login into your ACR.

## More information

For more information about ACR, see:

1. [Azure Container Registry product page](https://azure.microsoft.com/en-us/services/container-registry/)
1. [Azure Container Registry documentation](https://docs.microsoft.com/en-us/azure/container-registry/)
1. [Azure CLI commands for ACR](https://docs.microsoft.com/en-us/cli/azure/acr?view=azure-cli-latest)

[Previous](../01-initial/README.md) &nbsp; [Next](../03-setting-up-aks/README.md)
