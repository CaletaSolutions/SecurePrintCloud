pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Secure Print Cloud"
include(":single-tenant")
include(":workpathLib")
include(":auth:data")
include(":auth:domain")
include(":auth:presentation")
include(":core:domain")
include(":core:connectivity")
include(":core:connectivity:printer")
include(":core:connectivity:networking")
include(":core:presentation")
include(":core:presentation:designsystem")
include(":core:data")
include(":core:connectivity:domain")
