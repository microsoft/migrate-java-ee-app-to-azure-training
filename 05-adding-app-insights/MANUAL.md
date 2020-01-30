# Manual provisioning steps

## Create Application Insights application

We need to create the Application Insights application so we have a place where
we can gather our insights.

Please execute the command line below:

```shell
az monitor app-insights component create --app sharearound-app-insights \
  --resource-group sharearound --location westus2 --application-type java
```

## What you accomplished

1. You have created an Application Insights application.

[Back](README.md)
