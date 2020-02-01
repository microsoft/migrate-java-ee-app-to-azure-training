---
page_type: sample
languages:
- java
products:
- azure-kubernetes-service
description: "Migrate a JavaEE application to AKS"
urlFragment: "migrate-javaee-to-aks"
---

# Official Microsoft Sample

<!-- 
Guidelines on README format: https://review.docs.microsoft.com/help/onboard/admin/samples/concepts/readme-template?branch=master

Guidance on onboarding samples to docs.microsoft.com/samples: https://review.docs.microsoft.com/help/onboard/admin/samples/process/onboarding?branch=master

Taxonomies for products and languages: https://review.docs.microsoft.com/new-hope/information-architecture/metadata/taxonomies?branch=master
-->

This training is a demonstration on what you can expect when looking at migrating a JavaEE application to Azure Kubernetes Service.

## Prerequisites

> ---
>
> You need an Azure subscription for this training. If you haven't signed
> up for Azure, please do so now.
>
> Go to
> [Create your Azure free account today](https://azure.microsoft.com/en-us/free/)
>
> ---

## How to start this training

To start the guided training please go to [00-prerequisites/README.md](00-prerequisites/README.md).

## Brief summary about each step

Steps that have to be executed in order:

| Step                  | Summary                                       |
|-----------------------|-----------------------------------------------|
| 00-prerequisites      | setup your environment                        |
| 01-initial            | see the application to be migrated in action  |
| 02-setting-up-acr     | set up your Azure Container Registry          |
| 03-setting-up-aks     | set up your Azure Kubernetes Service cluster  |

Steps the can be done in any order once the steps above are done:

| Step                      | Summary                                           |
|---------------------------|---------------------------------------------------|
| 04-migrating-web-pages    | migrating of the web pages only                   |
| 05-adding-app-insights    | adding Application Insights to the application    |
| 06-migrating-database     | migrating of the PostgreSQL database to Azure     |

Step that has to be executed at the conclusion of the training:

| Step                      | Summary                                           |
|---------------------------|---------------------------------------------------|
| 99-cleanup                | Clean up of environment and resources             |

## Key concepts

Each of the steps in this training covers one aspect of migrating a JavaEE application to Azure Kubernetes Service.

## Special note

> :stop_sign: Because this is training material it is optimized to demonstrate
> some of the aspects you will run into when migrating a JavaEE application to Azure.

## Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.opensource.microsoft.com.

When you submit a pull request, a CLA bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., status check, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
