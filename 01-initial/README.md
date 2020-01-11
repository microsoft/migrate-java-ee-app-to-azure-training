# The initial Sharearound web application

If you want to see the initial Sharearound web application in action, please
build the web application using:

```shell
  mvn package
```

And the deploy it using:

```shell
  mvn antrun:run@deploy
```

Once the command completes you can point your browser to <http://localhost:9090/sharearound/>

Feel free to browse around to get familiar with the application you are going to
migrate.

Once you are done looking around you can leave it up and running, or if you want
to shut it down issue the following command:

```shell
  mvn antrun:run@undeploy
```

[Next](../02-migrating-web-pages/README.md)
[Up](../README.md)
