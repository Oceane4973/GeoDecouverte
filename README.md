# GéoDécouverte : Atelier Technique

## Description
**GéoDécouverte** est une application mobile développée dans le cadre du cours _Atelier technique_ dans la licence professionnelle LP DL - DAM (Développement d'Application Mobile) à l'IUT Sophia Antipolis. Cette application permet aux utilisateurs de découvrir des lieux grâce à une carte interactive, tout en leur offrant la possibilité de filtrer les résultats par ville, pays, ou rayon autour de leur position géographique actuelle.

![image](https://github.com/user-attachments/assets/0c187882-5f82-49a2-ba25-80a4b6b526ec)

## Fonctionnalités principales
- **Géolocalisation** : Demande de permission, stockage des positions en `LatLng`, et suivi en temps réel.
- **Appareil photo** : Capture et enregistre des photos avec confirmation d'enregistrement.
- **Mise en cache** : Gestion des publications sauvegardées localement.
- **WebService et JSON** : Utilisation d'une API Rest hébergée sur Vercel pour récupérer des données au format JSON.
- **Filtres** : Filtrage par ville, pays et rayon.
- **Gestion des permissions** : Autorisations pour accéder à la caméra et la géolocalisation.
- **Mode Administrateur** : Redirection vers une interface dédiée à l'administration.

## Contrainte technique
Lors du développement de l'application **GéoDécouverte**, plusieurs contraintes ont été impossés par l'enseignant :
- **BootAnimation** : Animation au démarrage de l'application. L'animation est lancée lors de l'ouverture de l'application, sans retour possible à cette étape.
- **WebService** : Utilisation d'une API Rest hébergée.
- **Mise en cache** : Gestion des publications sauvegardées pour permettre une consultation hors-ligne. Les publications sont stockées localement, optimisant ainsi les performances de l'application lors de l'affichage des données.
- **Singleton** : Implémentation du pattern Singleton.
- **Gestion des permissions** : Gestion fine des autorisations nécessaires (caméra, géolocalisation) pour s'assurer que les utilisateurs sont informés et donnent leur consentement avant d'accéder à certaines fonctionnalités sensibles de l'application.
- **Notifications** : Bien que partiellement implémentée, cette fonctionnalité vise à notifier l'utilisateur de l'état de la publication de sa photo et d'autres événements importants.
- **Mode Administrateur** : Une interface dédiée aux administrateurs permettant de gérer le contenu de l'application via un panneau de contrôle accessible par un lien spécifique vers l'administration de l'API.

## Taux de réalisation
- **Taux de réalisation total : 86%**
- Géolocalisation : 100%
- Appareil photo : 100%
- Notifications : Initialement prévu pour avertir de l’état de publication de la photo
- WebService : Hébergé sur Vercel, accès aux publications JSON

## Résultats obtenus
![image](https://github.com/user-attachments/assets/13bc0ce0-1687-42a5-9852-226a02e2ba8c)

## Lien vers l'API
Le projet utilise une API Rest hébergée sur Vercel. Vous pouvez accéder au dépôt de l'API ici :  
[https://github.com/Oceane4973/API-GeoDecouverte](https://github.com/Oceane4973/API-GeoDecouverte)

## Gestion de projet
Le projet a été planifié et géré via un fichier Google Sheets, incluant la répartition des tâches, la couverture des tâches, ainsi que le chiffrage. Vous pouvez consulter ces informations via le lien suivant :  
[https://docs.google.com/spreadsheets/d/1aq_uPRx9mTyllAWVlimuC0lG3B5flH3hkVxBObYr9NU/edit?gid=0#gid=0](https://docs.google.com/spreadsheets/d/1aq_uPRx9mTyllAWVlimuC0lG3B5flH3hkVxBObYr9NU/edit?gid=0#gid=0)

## Collaborateurs
Ce projet a été réalisé par :
- **Valentin Montel**
- **Lothaire Nobili**
- **Océane Monges**

## Conclusion
L'application **GéoDécouverte** est fonctionnelle sur la majorité des appareils (API 28+). Bien que le projet soit principalement complété, certaines fonctionnalités peuvent encore être améliorées, notamment les notifications. Le respect des utilisateurs et la gestion des permissions sont au cœur de cette application.
