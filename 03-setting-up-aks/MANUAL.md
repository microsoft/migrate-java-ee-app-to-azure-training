# Manual provisioning steps

## Create the Resource Group

We need a resource group to host the AKS cluster.

Please use the following command line to create the resource group:

```shell
az group create --name sharearound --location westus2
```

*Note if the output tells you the resource group already exists that is fine and
continue on.*

## Create the AKS cluster

Now we are going to create your AKS cluster.

Please execute the command line below:

```shell
az aks create --verbose --name sharearound-aks-$UNIQUE_ID \
  --resource-group sharearound --attach-acr sharearoundacr$UNIQUE_ID \
  --node-count 1 --generate-ssh-keys
```

Note this step will take a while.

## What you accomplished

1. You have created a resource group to host your resources.
1. You have created an AKS cluster to host your containers.

[Back](README.md)
