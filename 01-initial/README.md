# The initial Sharearound web application

If you want to see the initial Sharearound web application in action, please
build the web application using:

```shell
mvn package
```

The next step is to build a Docker image for the web application.

```
docker build -t sharearound -f src/main/docker/Dockerfile .
```

The final step is to start up the application server and the database by means
of Docker compose. From the `src/main/docker-compose/sharearound` directory
issue the following command.

```shell
docker-compose up -d
```

Once the command completes we are going to load the database with some data.

Please execute the following commands from base directory:

```shell
docker cp src/main/postgresql/load.sql postgres:/mnt
docker exec postgres bash -c 'psql --username postgres < /mnt/load.sql'
```

Once the command completes open your browser to 
<http://localhost:9090/sharearound/>

Feel free to browse around to get familiar with the application you are going to
migrate.

Once you are done looking around you can leave it up and running, or if you want
to shut it down issue the following command:

```shell
docker-compose down
```

[Next](../02-migrating-web-pages/README.md)
[Up](../README.md)
