# Getting started

This workshop assumes you have the following ready for use.

1. Azure CLI
1. Zulu OpenJDK 1.8
1. Maven 3.6.3
1. Visual Studio Code (with Java Extension Pack and the Remote Containers Extension Pack)
1. Docker
1. Docker Compose

## Install and Configure Azure CLI

To install the Azure CLI, please visit 
[Install the Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli).
And once you are done come back to this README and follow the remainder of the 
steps below to configure your environment.

### Login into Azure

````shell
  az login
````

### Set your default subscription

Get a list of your subscriptions

````shell
  az account list --output table
````

Set your default subscription for this session using the subscription id from the previous output

````shell
  az account set --subscription "subscription-id"
````

## Install Zulu OpenJDK 1.8

Download [Zulu OpenJDK 1.8](https://www.azul.com/downloads/zulu-community/?&version=java-8-lts&package=jdk)
from Azul Systems. And install it locally and setup your PATH to point to the installed JDK.

## Install Maven 3.6.3

Download [Maven 3.6.3](http://mirrors.ibiblio.org/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz).
And install it locally and setup your PATH to point to the installed Maven.

## Install Visual Studio Code

Download [Visual Studio Code](https://code.visualstudio.com/Download) and install it locally.

Then also install the following Extension Packs

1. [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
1. [Remote Development](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.vscode-remote-extensionpack)
