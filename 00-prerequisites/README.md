# Prerequisites

**Note each command mentioned in a README should be executed in the directory of
that README unless specified otherwise**

You have 2 options to satisfy the prerequisites needed to complete this training.

1. [Docker Container option](#docker-container-option)
1. [Alternate option](#alternate-option)

## Docker Container option

1. Install Visual Studio Code (VSCode)
1. Install the Remote Containers extension for VSCode
1. Install Docker Desktop
1. Run the Docker container
1. Log into Azure
1. Set your default subscription

### Install Visual Studio Code

Open your browser to [Download Visual Studio Code](https://code.visualstudio.com/Download).

Click on the button appropriate for your OS.

### Install the Remote Containers extension for VSCode

Open your browser to [Remote - Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers)

Click on the `Install` button.

It will ask you if you want to open this URL in VSCode. Confirm that you want to
open it in VSCode.

Click on the `Install` button.

### Install Docker

*Note if you do not have a Docker ID yet you will need to sign up as you need it
to download Docker Desktop*

*Note you DO not have to go through the tutorial, you only need to download the
Docker installer*

Go to [Get Started with Docker](https://www.docker.com/get-started)

And follow the directions there.

### Run the Docker container

#### If you are running on non-Windows OS

Replace `DIRECTORY` below with the base directory of the training material.

And execute the command line:

```shell
docker run --privileged --name devenv -v DIRECTORY:/mnt \
 -v /var/run/docker.sock:/var/run/docker.sock -d \
 azurejavalab.azurecr.io/azurejavalab:2020.01
```

#### If you are running on Windows 

Open the settings for Docker Desktop for Windows

Make sure `Expose daemon on tcp://localhost:2375 without TLS` is checked. 

*Note this only needs to be checked for the duration of the training*

Replace `DIRECTORY` below with the base directory of the training material.

And execute the command line:

```shell
docker run --name devenv -v DIRECTORY:/mnt \
 -e DOCKER_HOST=tcp://docker.for.win.localhost:2375 -d \
 azurejavalab.azurecr.io/azurejavalab:2020.01
```

If Docker Desktop for Windows asks to share enter the proper credentials
to allow it to do so.

#### Next steps are for all OS-es

Now start VSCode

Click on the green icon on the bottom left of VSCode.

Enter the following in the prompt that shows up:

```shell
Remote-Containers: Attach to Running Container...
```

Then select the `devenv` remote container.

This will open a 2nd window of VSCode which will be attached to the running Docker 
container.

This is the VSCode window we will use for the duration of the training.

Open up a Terminal using the Terminal | New Terminal menu.

### Login into Azure

````shell
az login
````

### Set your default subscription

Get a list of your subscriptions

````shell
az account list --output table
````

Set your default subscription using the subscription id from the previous output

````shell
az account set --subscription "subscription-id"
````

Now you are ready to start the training!

## Alternate option

*Note we DO not recommend using this option*

You will need to install the following tools:

1. An editor / IDE
1. Docker Desktop
1. Docker Compose
1. Azure CLI
1. Azul JDK 8
1. Maven 3.6.3
1. curl
1. kubectl
1. psql

[Next](../01-initial/README.md)
