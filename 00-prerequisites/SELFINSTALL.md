# Prerequisites - Self Install option

## What are we going to do in this step

In this step we are going to setup the environment you will need for this
training.

## Install the necessary tooling

You will need to install the following tools:

1. An editor / IDE
2. Docker
3. Docker Compose
4. Azure CLI
5. Azul JDK 8
6. Maven 3.6.3
7. bash
8. curl
9. kubectl
10. psql

> :stop_sign: The training material assumes all commands are run in a bash shell

## Create the sharearound directory

Create an empty directory called `sharearound` on your local filesyatem.

```shell
mkdir sharearound
```

And then execute the command below to change into it:

```shell
cd sharearound
```

## Clone the Git repository

Then clone the repository using:

```shell
git clone https://github.com/microsoft/migrate-java-ee-app-to-azure-training
```

### Login into Azure

To login into Azure execute the following command line:

````shell
az login
````

Follow the directions given and return here when you are done.

### Set your default subscription

We need to set the subscription you want to use for this training.

To get a list of your subscriptions execute the command line below:

````shell
az account list --output table
````

Now replace `subscription-id`  with the `SubscriptionId` you determined you want to use in the command line below.

Execute the command line to set the default subscription id:

```shell
az account set --subscription "subscription-id"
```

### Determine your unique id

Some of the resources we are going to create need to have a unique id. In a class
room setting ask your proctor what the value of the `UNIQUE_ID` environment
variable needs to be. If you are doing this training by yourself use the same
timestamp in `YYYYMMDDHHSS` format as the value for `UNIQUE_ID`.

Replacing `FILL_THIS_IN` with the value for `UNIQUE_ID` you determined above
and execute the command line below:

```shell
export UNIQUE_ID=FILL_THIS_IN
```

Now we are going to set the BASEDIR environment variable:

Execute the command line below:

```shell
export BASEDIR=$PWD
```

You are now ready to start the training!

## What you accomplished

1. You have setup your environment to start the training.

[Next](../01-initial/README.md)
