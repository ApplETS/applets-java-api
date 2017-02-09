[![Build Status](https://travis-ci.org/ApplETS/applets-java-api.svg?branch=master)](https://travis-ci.org/ApplETS/applets-java-api) [![Coverage Status](https://coveralls.io/repos/github/ApplETS/applets-java-api/badge.svg?branch=master)](https://coveralls.io/github/ApplETS/applets-java-api?branch=master)



Vue d'ensemble
========
Nous utilisons cette API pour centraliser la distribution d'informations vers les clients mobiles.

Liens rapides :
  * [Requêtes actuelles dans l'API](#requêtes-actuelles-dans-lapi)
  * [Outils nécessaires](#outils-nécessaires)
  * [Configuration](#configuration)
  * [Déploiement rapide](#déploiement-rapide)
  * [Schéma de la base de données](#schéma-de-la-base-de-données)


Requêtes actuelles dans l'API
========
Disponibles dans la collection Postman suivante :
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/8bb16d836b6212da7c4f)


Outils nécessaires
==========
1) Téléchargez et installez [Tomcat 9](https://tomcat.apache.org/ )

2) Téléchargez et installez [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

:information_source:  Vous pouvez obtenir l'Ultimate Edition d'IntelliJ avec votre adresse courriel de l'ÉTS en appliquant [ici](https://www.jetbrains.com/student/)

Configuration
==========

1) Importez le projet dans IntelliJ

2) Configurez le projet dans IntelliJ :

(Onglet Run, Edit configurations)

![](http://i.imgur.com/6VkXJ7Y.png)

* Application server : ici, on renseigne le répertoire de Tomcat
* VM Options : on rajoute un paramètre pour désactiver le [support SNI](http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0)

```
-Djsse.enableSNIExtension=false
```
* Before launch : on roule gradle en premier pour produire le fichier .war

![](http://i.imgur.com/1uREKZW.png?1)

Le fichier war est compilé dans : `build/libs/applets-java-api.war`

* Exécutez le projet pour vérifier qu'il fonctionne correctement
```
curl http://localhost:8080/rest/partners
```

Pour configurer les accès à la base de données postgresql, on utilise les variables d'environnement.
Elles peuvent être renseignées dans les Run Configurations de IntelliJ, onglet Startup/Connection.
Les variables d'environnement à définir sont : 
* POSTGRESQL_USER
* POSTGRESQL_PASS
* POSTGRESQL_DB_NAME
* POSTGRESQL_HOST
* POSTGRESQL_PORT
* [FACEBOOK_ACCESS_TOKEN](https://developers.facebook.com/tools/accesstoken/)

Déploiement rapide
==========

Il est possible de déployer le projet en une ligne de commande grâce à l'utilisation de Docker. Cette fonctionnalité permet de déployer automatiquement le serveur web et la base de données déjà peuplée d'événements/nouvelles de l'ÉTS.

### Déployer uniquement la base de données pour tester

Exécutez le script __setup_db_standalone.sh__ à la racine du projet.

La base de données est alors démarrée et peuplée de données de test.

Renseignez ensuite les variables d'environnement suivantes dans votre IDE : 
```
POSTGRESQL_USER = postgres
POSTGRESQL_PASS = postgres
POSTGRESQL_DB_NAME = applets_api_db
POSTGRESQL_HOST = 0.0.0.0
POSTGRESQL_PORT = 5432
```
(Dans IntelliJ : Run, Edit configurations, onglet Startup/Connection, Environment Variables )


### Déployer toute la solution

Renseignez dans un premier temps les variables d'environnement dans le fichier docker-dir/docker-compose.yml, placez-vous à la racine du projet, puis utilisez la commande suivante :
```bash
./auto_run.sh
```
Cela peut prendre un peu de temps la première fois.
Connectez-vous ensuite par navigateur à l'adresse : http://{VOTRE_HOST}:8080/applets-java-api/rest/partners

Il existe également un [plugin intellij pour docker](https://plugins.jetbrains.com/plugin/7724?pr=mps) que vous pouvez télécharger directement depuis le serveur d'applications intégré. Ce plugin requiert docker-machine.

Schéma de la base de données
==========

![Schéma de la base de données](http://i.imgur.com/y1qUU34.png)

