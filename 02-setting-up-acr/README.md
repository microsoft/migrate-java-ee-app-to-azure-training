# Setting up Azure Container Registry

5m

## What are we going to do in this step

As we are targeting deployment on Azure Kubernetes Service we need a Docker
registry from which we can pull our container images. We are going to setup an
Azure Container Registry, which is a Docker registry,

## Create the Resource Group

We need a resource group to host the Azure Container Registry. Please use the
following command line to create the resource group.

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

## Create the Azure Container Registry (ACR)

> Note because this is a training in which we are migrating an existing JavaEE
> application to Azure Kubernetes Service we are concentrating on the migration.
> In a production environment you should use RBAC to limit access to your Azure
> Container Registry. Here we are creating your ACR with admin access enabled.

Now we are going to create your ACR, please execute the command line below:

```shell
az acr create --name sharearoundacr$UNIQUE_ID --location westus2 --resource-group sharearound --sku Basic --admin-enabled
```

## Determine your ACR username and password

To be able to push to your ACR you need the admin username.

Please execute the command line below:

```shell
export ACR_USERNAME=`az acr credential show --name sharearoundacr$UNIQUE_ID --resource-group sharearound --query username --output tsv`
```

This will set the environment variable ACR_USERNAME to contain the admin username.

We will also need the password.

Please execute the command line below:

```shell
export ACR_PASSWORD=`az acr credential show --name  sharearoundacr$UNIQUE_ID --resource-group sharearound --query passwords[0].value --output tsv`
```

This will set the environment variable ACR_PASSWORD to contain the admin password.

## More information

For more information about Azure Container Registry, see:

1. [Azure Container Registry](https://azure.microsoft.com/en-us/services/container-registry/)
1. [Azure Container Registry documentation](https://docs.microsoft.com/en-us/azure/container-registry/)
1. [Azure CLI commands](https://docs.microsoft.com/en-us/cli/azure/acr?view=azure-cli-latest)

[Previous](../01-initial/README.md) &nbsp; [Next](../04-setting-up-aks/README.md)
