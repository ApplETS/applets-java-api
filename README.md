Vue d'ensemble
========
Nous utilisons cette API pour centraliser la distribution d'informations vers les clients mobiles.
Les fonctionnalités actuellement implémentées sont :

* Partenaires : Pour obtenir la liste à jour de nos partenaires à partir de notre [site web](clubapplets.ca/partenaires).

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

