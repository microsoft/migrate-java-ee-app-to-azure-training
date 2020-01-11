# Migrating the web pages

## What are we going to do in this step

The first step in any migration to Azure is to migrate the deployment process.

As we have chosen the deployment target for this training to be Azure App Service
we need to make some changes to make it possible to deploy this web application
onto Azure. For simplicity sake we are going to ignore the fact that the
application is using a database for now.

## Setting up

To start the migration we are going to copy the application from the `01-initial`
directory into this directory.

To do so please issue the following command line
in your terminal:

```shell
  mvn antrun:run@setup
```

## Changes needed to the web pages

We need to verify if there is a server specific deployment descriptor that sets a
specific context root. In this particular case we have one, you can find the
`glassfish-web.xml` file in the `src/main/webapp/WEB-INF` directory.

Look at the contents and you will  notice it specifies `/sharearound`.

As we are targetting Azure App Service using WildFly and a specific context root
was used we now need to make sure the application does NOT use it anywhere in a
hard-coded way.

Luckily in this application there is only one spot in the application that has the
value hard-coded and that is in the index.jsp page. Please change `/sharearound`
to `TODO`.

## Build the web application

Now we are ready to build the web application.

Use the following command line:

```shell
  mvn package
```

## Add the Azure Web App Maven plugin for deployment

Please add the following XML snippet just before </plugins> in the POM file.

```xml
<plugin>
    <groupId>com.microsoft.azure</groupId>
    <artifactId>azure-webapp-maven-plugin</artifactId>
    <version>1.8.0</version>
    <configuration>
        <schemaVersion>v2</schemaVersion>
        <resourceGroup>${resourceGroup}</resourceGroup>
        <region>${region}</region>
        <appName>${appName}</appName>
        <appServicePlanName>${appServicePlan}</appServicePlanName>
        <runtime>
            <os>linux</os>
            <javaVersion>jre8</javaVersion>
            <webContainer>wildfly 14</webContainer>
        </runtime>
        <deployment>
            <resources>
                <resource>
                    <directory>${project.basedir}/target</directory>
                    <includes>
                        <include>*.war</include>
                    </includes>
                </resource>
            </resources>
        </deployment>
    </configuration>
</plugin>
```

By adding the Maven plugin above you will be able to use Maven to deploy your
web application while you are developing. Note for production deployment we
recommend setting up a CI/CD pipeline!

Each of the relevant pieces in the `<configuration>` block has been parameterized
so you can override them using the Maven command line.

Note that for a successful Azure App Service deployment you will need to have a
least a `resource group`, a `region`, an `appName` and an `appServicePlan`.

We are going to add a `<properties>` section to the POM file that will set some
defaults for these.

Please add the following XML snippet just before `</project>` in the POM file.

```xml
<properties>
    <appName>sharearound-${maven.build.timestamp}</appName>
    <appServicePlan>sharearound-appserviceplan</appServicePlan>
    <maven.build.timestamp.format>yyyyMMddHHmm</maven.build.timestamp.format>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <resourceGroup>sharearound</resourceGroup>
    <region>westus2</region>
</properties>
```

## Deploy the web application

To deploy the web application you will need a unique id as the URL space of
Azure App Service is shared across Azure subscriptions . In a class room setting
ask your proctor what the value of the `<unique-id>` needs to be. If you are doing
this workshop by yourself you can omit the `-DappName=sharearound-<unique-id>` and
a unique id will be generated for you.

Use the following commandline:

```shell
  mvn azure-webapp:deploy -DappName=sharearound-<unique-id>
```

While this command is running, please feel free to review [App Service on Linux Documentation](https://docs.microsoft.com/en-us/azure/app-service/containers/)

Once the command completes it will show you the URL of the deployed web
application, it will look similar to `https://sharearound-<unique-id>.azurewebsites.net`. Please capture this URL as you will need it later.

Open your browser to the shown URL to verify that you have successfully deployed
web application.

[Next](../03-migrating-database/README.md)
[Up](../README.md)
