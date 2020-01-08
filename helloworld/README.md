# Migrating the Hello World web application

## Migrating the web application

As this web application is very simple the only thing to take note of is the fact
that the WebLogic deployment descriptor specifies a context-root of `/helloworld`.

If you see an application server specific deployment descriptor that specifies a
context-root you will need to make sure the hard-coded value is NOT used anywhere.
If it is used you will have to replace it by using relative links.

## Build the web application

To build the web application use the following command line:

```
  mvn package
```

## Deploy the web application

Use the following commandline:

```
  mvn azure-webapp:deploy
```

Once the command completes it will show you the URL of the deployed web application,
it will look similar to `https://javaee-workshop-XXXXX.azurewebsites.net`.

Open the URL in the browser to verify that you have successfully migrated the 
simple web application.

## Update the web application

Now make a small change in the web application itself by change the index.html
page to say 'Hello Azure' instead of 'Hello World'.

Build the web application again, see above for the command line.

Once that completes we are going to update the web application and deploy it 
again, use the command line below replacing XXXXX with the correct numbers

```
  mvn azure-webapp:deploy -DappName=javaee-workshop-XXXXX  
```

During the deploy you should now see something similar to

```
[INFO] Updating app service plan
[INFO] Updating target Web App...
[INFO] Successfully updated Web App.  
```
