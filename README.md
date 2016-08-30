[![Build Status](https://travis-ci.org/ApplETS/applets-java-api.svg?branch=master)](https://travis-ci.org/ApplETS/applets-java-api) [![Coverage Status](https://coveralls.io/repos/github/ApplETS/applets-java-api/badge.svg?branch=master)](https://coveralls.io/github/ApplETS/applets-java-api?branch=master)

Vue d'ensemble
========
Nous utilisons cette API pour centraliser la distribution d'informations vers les clients mobiles.

Requêtes actuelles dans l'API
========
- Afficher les partenaires : https://api3.clubapplets.ca/partners 
- Cooptel : https://api3.clubapplets.ca/cooptel?phase=[1 à 4]&appt=[num]
- Event sources : https://api3.clubapplets.ca/events/sources
- Events : https://api3.clubapplets.ca/events/list/[nomsource]
- Nouvelle sources : https://api3.clubapplets.ca/news/sources
- Nouvelles : https://api3.clubapplets.ca/news/list/[nomsource]

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

Renseignez dans un premier temps les variables d'environnement dans le fichier docker-dir/docker-compose.yml, placez-vous à la racine du projet, puis utilisez la commande suivante :
```bash
./auto_run.sh
```
Cela peut prendre un peu de temps la première fois.
Connectez-vous ensuite par navigateur à l'adresse : http://{VOTRE_HOST}:8080/applets-java-api/rest/partners

Il existe également un [plugin intellij pour docker](https://plugins.jetbrains.com/plugin/7724?pr=mps) que vous pouvez télécharger directement depuis le serveur d'applications intégré. Ce plugin requiert docker-machine.
