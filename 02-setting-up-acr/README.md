# Setting up Azure Container Registry

> :stop_sign: **Note each command mentioned in this README should be executed in
> the directory of this README unless specified otherwise**

## What are we going to do in this step

As we are targeting deployment on Azure Kubernetes Service (AKS) we need a Docker
registry from which we can pull our container images. In this step we will verify
we can access the Azure Container Registry (ACR).

## Determine your unique id and set it in your environment

Some of the resources we are going to create need to have a unique id. In a class
room setting ask your proctor what the value of the `UNIQUE_ID` environment
variable needs to be. If you are doing this workshop by yourself use the same
timestamp in `YYYYMMDDHHSS` format as your unique id throughout the training.

Replacing `FILL_THIS_IN` with the value you determined above and execute the
command line below:

```shell
export UNIQUE_ID=FILL_THIS_IN
```

> :bulb: If you are interested to know what steps the ARM template took to
> provision your ACR, see [Manual Provisioning steps](MANUAL.md)

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
