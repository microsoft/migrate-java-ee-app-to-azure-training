# Setting up Azure Kubernetes Service

## What are we going to do in this step

In this step we are going to setup your Azure Kubernetes Service (AKS) cluster.

## Create the Resource Group

We need a resource group to host the AKS cluster.

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

## Create the AKS cluster

Now we are going to create your AKS cluster, please execute the command line below:

```shell
az aks create --verbose --name sharearound-aks-$UNIQUE_ID \
  --resource-group sharearound --attach-acr sharearoundacr$UNIQUE_ID
```

Note this step will take a while.

In the meanwhile feel free to read the
documentation mentioned below in the [More information](#more-information)
section.

## Get the Kubernetes config file

In order to access the Kubernetes cluster using `kubectl` you will need a Kube config file.

> Note as the purpose of this training is migrating the JavaEE application to
> AKS we are concentrating on that and we are using admin access to the AKS
> cluster. For production environments we recommend you configure RBAC to limit
> access to your Kubernetes cluster based on roles.

Execute the following command line:

```shell
az aks get-credentials --resource-group sharearound \
  --name sharearound-aks-$UNIQUE_ID --admin --file kubeconfig
```

This will create the Kube config file in `kubeconfig`

## Set the KUBECONFIG environment variable

In the directory where you executed the previous command execute the command line
below:

```shell
export KUBECONFIG=$PWD/kubeconfig
```

This set the KUBECONFIG environment variable to point to the `kubeconfig` file
so `kubectl` will be able to interact with your AKS cluster.

## List the nodes in your AKS cluster

To verify you can work with your cluster we are going to ask the cluster for the
information on the Kubernetes nodes in your cluster.

Execute the following command line:

```shell
kubectl get nodes
```

This should show you the list of nodes in your Kubernetes cluster.

## More information

For more information about AKS, see:

1. [Azure Kubernetes Service product page](https://azure.microsoft.com/en-us/services/kubernetes-service/)
1. [Azure Kubernetes Service documentation](https://docs.microsoft.com/en-us/azure/aks/)
1. [Azure CLI commands for AKS](https://docs.microsoft.com/en-us/cli/azure/aks?view=azure-cli-latest)
1. [Kubectl Reference Documentation](https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands)

[Previous](../02-setting-up-acr/README.md) &nbsp; [Next](../04-migrating-web-pages/README.md)

22m
