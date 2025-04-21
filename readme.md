# Application de Livraison de Repas

## Introduction
L'application de livraison de repas est une application basée sur Java avec une interface graphique conçue pour permettre aux clients de commander des repas, aux livreurs de gérer les livraisons, et à un administrateur de superviser les utilisateurs et le catalogue des repas. L'application utilise une base de données MySQL pour la persistance des données (utilisateurs, repas, commandes) et fournit des interfaces spécifiques à chaque rôle pour la navigation et la gestion des commandes.

## Acteurs et Cas d'Utilisation

### Client
- **Connexion** : S'authentifier ou s'inscrire avec un nom d'utilisateur, un mot de passe et une adresse.
- **Voir les repas** : Consulter une liste de repas avec leurs prix et sélectionner des quantités.
- **Commander des repas** : Sélectionner des repas avec des quantités, passer une commande et voir une facture.
- **Voir les commandes** : Consulter une liste de factures et leurs détails.
- **Annuler une commande** : Annuler les commandes ayant le statut "En attente".

### Livreur
- **Connexion** : S'authentifier ou s'inscrire avec un nom d'utilisateur, un mot de passe, un nom et un numéro de téléphone.
- **Voir les factures** : Consulter toutes les factures avec leurs détails (double-clic pour voir les repas et quantités).
- **Mettre à jour le statut des factures** : Modifier le statut d'une facture (par exemple, Livré, En attente, Client fictif).
- **Supprimer une facture** : Supprimer une facture après confirmation.

### Administrateur
- **Connexion** : S'authentifier avec des identifiants prédéfinis (Aziz/Aziz).
- **Gérer les clients** : Ajouter ou supprimer des clients (nom d'utilisateur, mot de passe, adresse).
- **Gérer les livreurs** : Ajouter ou supprimer des livreurs (nom d'utilisateur, mot de passe, nom, téléphone).
- **Gérer les repas** : Ajouter ou supprimer des repas (nom, prix).
- **Voir les factures** : Consulter toutes les factures.
- **Mettre à jour le statut des factures** : Modifier le statut des factures.
- **Supprimer une facture** : Supprimer des factures.

## Diagramme des Cas d'Utilisation
![Diagramme des Cas d'Utilisation](Diagrammes/Diagramme%20De%20Cas%20D'Utilisation.png)

## Priorités des Cas d'Utilisation
- **Priorité élevée** :
  - Connexion (tous les acteurs)
  - Commander des repas (Client)
- **Priorité moyenne** :
  - Voir les repas (Client)
  - Voir les commandes (Client)
  - Voir les factures (Livreur)
  - Mettre à jour le statut des factures (Livreur)
- **Priorité faible** :
  - Annuler une commande (Client)
  - Supprimer une facture (Livreur)
  - Gérer les clients, livreurs, repas (Administrateur)

## Tests de Validation pour les Cas d'Utilisation à Haute Priorité

### Cas d'Utilisation : Connexion

**Pré-conditions** :
- Base de données contient utilisateurs (clients, livreurs, admin prédéfini).
- Champs username, password, rôle remplis.
  
**Post-conditions** 
- Utilisateur authentifié → Ouvre page correspondante (AdminPage, ClientPage, DeliveryWorkerPage).
- Échec → Message d’erreur ou proposition d’inscription.

| Condition               | C1 : Admin Valide | C2 : Client Valide | C3 : Livreur Valide | C4 : Invalide |
|-------------------------|-------------------|--------------------|---------------------|---------------|
| Nom d'utilisateur correct | Oui               | Oui                | Oui                 | Non           |
| Mot de passe correct     | Oui               | Oui                | Oui                 | Non           |
| Rôle sélectionné         | Admin             | Client             | Livreur             | N'importe     |
| Résultat attendu         | PageAdmin         | PageClient         | PageLivreur         | Erreur        |
| ID du test               | T1                | T2                 | T3                  | T4            |

### Cas d'Utilisation : Commander des repas

**Pré-conditions** :
- Client connecté.
- Liste des repas chargée.
- Au moins un repas sélectionné avec quantité > 0.
  
**Post-conditions** :
- Facture créée dans invoices.
- Commandes ajoutées dans orders avec quantités.
- Facture affichée à l’utilisateur.

| Condition               | C1 : Sélection Valide | C2 : Aucune Sélection | C3 : Quantité Négative |
|-------------------------|-----------------------|-----------------------|------------------------|
| Repas sélectionnés      | Oui                   | Non                   | Oui                    |
| Quantité > 0            | Oui                   | N/A                   | Non                    |
| Résultat attendu        | Facture affichée      | Erreur "Sélectionner un repas" | Erreur (implicite) |
| ID du test              | T5                    | T6                    | T7
