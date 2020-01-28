# Migrating the web pages

> :stop_sign: **Note each command mentioned in this README should be executed in
> the directory of this README unless specified otherwise**

## Prerequisites

It is assumed you have completed the following steps:

1. [Setting up ACR](../02-setting-up-acr/README.md)
2. [Setting up AKS](../03-setting-up-aks/README.md)

## What are we going to do in this step

As we have chosen the deployment target for this training to be Azure Kubernetes
Service (AKS) we need to make some changes to make it possible to deploy this web
application onto AKS. For simplicity sake we are going to ignore the fact that
the application is using a database for now.

## Setting up

To start the application migration we are going to copy the application from the
`01-initial` directory into this directory.

To do so please issue the following command line in your terminal:

```shell
mvn antrun:run@setup
```

## Changes needed to the web pages

We need to verify if there is a server specific deployment descriptor that sets a
specific context root. In this particular case we have one, you can find the
`glassfish-web.xml` file in the `src/main/webapp/WEB-INF` directory.

Look at the contents and you will  notice it specifies `/sharearound`.

As we are targeting AKS and a specific context root was used we now need to make
sure the application does NOT use it anywhere in a hard-coded way.

Luckily in this application there is only one spot in the application that has the
value hard-coded and that is in the index.jsp page. Please remove `/sharearound/`
to make the links relative.

## Build the web application

Now we are ready to build the web application.

Use the following command line:

```shell
mvn package
```

## Build the Docker image locally

Now that we have the web application we need to build a Docker image with both
WildFly and your web application in it.

To do so execute the following command line:

```shell
docker build -t test -f src/main/aks/Dockerfile .
```

## Test the Docker image locally

Now we are going to validate that the image works.

Please execute the following command line:

```shell
docker run --rm -it -p 8080:8080 test
```

You should see something like the output below:

```shell
Full 18.0.1.Final (WildFly Core 10.0.3.Final) started in 5058ms - Started 315 of 577 services (369 services are lazy, passive or on-demand)
```

Now open Microsoft Edge to [http://localhost:8080/](http://localhost:8080/)

You should see the home page of the *Sharearound* application.

Now shutdown the Docker container using:

```shell
Ctrl+C
```

## Build the image on ACR

Since our AKS cluster needs to be able to pull the image from a Docker registry
we are going to build it using Azure CLI and target our ACR.

Execute the following command line to do so:

```shell
az acr build --registry sharearoundacr$UNIQUE_ID --image sharearound \
  --file src/main/aks/Dockerfile .
```

## Deploy to the AKS cluster

Determine the name of your ACR by executing the following command line:

```shell
echo sharearoundacr$UNIQUE_ID
```

Now open `src/main/aks/sharearound.yml` in your editor and replace REGISTRY with
the value of the previous command (which is the name of your ACR).

And then finally deploy the application by using the following command line:

```shell
kubectl apply -f src/main/aks/sharearound.yml
```

The command will quickly return, but the deployment will still be going on.

We are going to use `kubectl` to wait for the service to become available:

Execute the following command line:

```shell
kubectl get service/sharearound --output wide -w
```

Now wait until you see the EXTERNAL-IP column populated with an IP address.

> :stop_sign: Note if the command does not show the EXTERNAL-IP after a couple of
> minutes, please use `Ctrl+C` to cancel the command and then reissue the command
> without `-w` at the end.

Once the IP address is there you are ready to open Microsoft Edge to
`http://EXTERNAL-IP:8080/`

You should see the same page as before, but now it is running on AKS!

## What you accomplished

1. You have migrated the web pages in the web application.
1. You have build a Docker image containing WildFly and your web application.
1. You have tested the Docker image locally.
1. You have build the Docker image using ACR.
1. You have deployed the Docker image to AKS.
1. You have verified the application works on AKS.

## Troubleshooting

If you made a mistake and something does not work, please start over with this
step using the following command line:

```shell
mvn clean
```

And then start back at the top of this README.

## Additional troubleshooting commands

1. `kubectl get pods` will show the status of your pods.
1. `kubectl logs -f service/sharearound` will show logs for the `sharearound`
   service.
1. `kubectl describe deployment/sharearound --output yaml` will show your 
   deployment YAML.

[Previous](../03-setting-up-aks/README.md) &nbsp; [Next](../05-adding-app-insights/README.md)

12m
