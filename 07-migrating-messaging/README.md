# Migrating messaging

IN PROGRES

## What are we going to do in this step

In this step we are going to migrate to use of messaging to Azure Service Bus.

## Setting up

To start the migration we are going to copy the application from the `01-initial`
directory into this directory.

To do so please issue the following command line
in your terminal:

```shell
  mvn antrun:run@setup
```

## Determine your unique id and set it in your environment

Some of the resources we are going to create need to have a unique id. In a class
room setting ask your proctor what the value of the `UNIQUE_ID` needs to be. If
you are doing this workshop by yourself use the same timestamp in `YYYYMMDDHHSS`
format as your unique id.

Replacing `FILL_THIS_IN` with the value you determined above and execute the
command line below:

```shell
export UNIQUE_ID=FILL_THIS_IN
```

## Build the web application

Now we are ready to build the web application.

Use the following command line:

```shell
mvn package
```

## Deploy the web application

> Note the DNS space of Azure App Service is shared across all Azure subscriptions
> so your unique id is used to make `appName` in your `pom.xml` unique.

Use the following commandline:

```shell
mvn azure-webapp:deploy
```

While this command is running, please feel free to review our
[App Service on Linux Documentation](https://docs.microsoft.com/en-us/azure/app-service/containers/)

Once the command completes it will show you the URL of the deployed web
application, it will look similar to
`https://sharearound-ZZZZZ.azurewebsites.net`.

Please capture this URL as you will need it later.

## Creating the Azure Service Bus

We are going to create the Azure Service Bus.

> Note the DNS namespace of Azure Service Bus is shared across all Azure
> subscriptions so your unique id will be used to make it unique.

Please execute the command line:

```shell
az servicebus namespace create --resource-group sharearound --name sharearound-$UNQIUE_ID --location westus2
```

This command will take some time to execute.

While you are waiting feel free to review our
[Azure Service Bus Messaging documentation](https://docs.microsoft.com/en-us/azure/service-bus-messaging/).

The next step is to create the topic on the service bus.

Execute the command below:

```shell
az servicebus queue create --resource-group sharearound --namespace-name sharearound-$UNIQUE_ID --name EmailQueue
```

The next step is to get the connection string to the queue.

Execute the command line below:

```shell
az servicebus namespace authorization-rule keys list --resource-group sharearound --namespace-name sharearound-$UNIQUE_ID --name RootManageSharedAccessKey --query primaryConnectionString --output tsv
````

Please capture the connection string as you will need it later.

[Previous](../04-adding-app-insights/README.md)
