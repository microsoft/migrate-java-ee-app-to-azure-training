#
# Make sure an unique id is supplied.
#
if [ $# -eq 0 ]
  then
    echo "Please supply your the unique id."
fi

#
# Create the Resource Group
#

az group create --name sharearound --location westus2

#
# Create the Azure Container Registry
#

az acr create --name sharearoundacr$1 --location westus2 --resource-group sharearound --sku Basic

#
# Create the AKS cluster and associate the Azure Container Registry
#

az aks create --verbose --name sharearound-aks-$1 --resource-group sharearound --attach-acr sharearoundacr$1 --node-count 1 --generate-ssh-keys

#
# Create the Application Insights application used by our web application.
#

az monitor app-insights component create --app sharearound-app-insights --resource-group sharearound --location westus2 --application-type java

#
# Create the PostgreSQL on Azure database.
#

az postgres server create --resource-group sharearound --name sharearound-postgres-$1 --location westus2 --admin-user postgres --admin-password p0stgr@s1 --sku-name B_Gen5_1
