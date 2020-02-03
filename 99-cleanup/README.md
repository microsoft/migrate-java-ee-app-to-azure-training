# Cleanup

Once you are done with the training you should cleanup any resources you created.

To cleanup the Azure resources use the following command line:

```shell
az group delete --name sharearound
```

To shutdown and remove the Docker container use the following command lines
(outside of the container):

```shell
docker kill sharearound
docker rm sharearound
```

## If you did the training on Windows

Make sure to uncheck `Expose daemon on tcp://localhost:2375 without TLS`
in the Docker Desktop for Windows settings.

If you changed Docker Desktop for Windows to run Linux containers you can switch
it back to run Windows containers if so desired.

And uncheck sharing of your `C` drive.
