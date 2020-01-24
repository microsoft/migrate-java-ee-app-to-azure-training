# The initial Sharearound web application

## What are we going to do in this step

We are going to be looking at an JavaEE application that we are going to migrate.
Note this is an imperfect application that uses an unsupported version of the JDK,
an unsupported version of a JavaEE application server. So we will need to do some
migrating!

## Building the web application

Please build the web application using:

```shell
mvn package
```

## Building the Docker image

Next we build a Docker image for the web application using:

```shell
docker build -t sharearound -f src/main/compose/Dockerfile .
```

## Starting the application server and database locally

Finally we are going to start up the application server and the database by means
of Docker compose. From the `src/main/compose/sharearound` directory
issue the following command.

```shell
docker-compose up -d
```

Once the command completes we are going to load the database with some data.

Please execute the following commands from base directory:

```shell
docker cp src/main/postgres/load.sql postgres:/mnt
docker exec postgres bash -c 'psql --username postgres < /mnt/load.sql'
```

Once the command completes open your browser to
<http://localhost:9090/sharearound/>

Feel free to browse around to get familiar with the application you are going to
migrate.

Once you are done looking around you can leave it up and running, or if you want
to shut it down issue the following command from the
`src/main/compose/sharearound` directory:

```shell
docker-compose down
```

## What did you accomplish

1. You have build the web application locally.
1. You have deployed the web application locally using Docker Compose.
1. You have verified the web application works.

[Next](../02-setting-up-acr/README.md)

6m
