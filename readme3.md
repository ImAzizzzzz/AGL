# Conception Préliminaire 2 : Application de Livraison de Repas
**Auteur** : ABED Mohamed Aziz  
**Date** : 20 avril 2025  

## 1. Raffinement du diagramme de classes pour faciliter la programmation

### a. Navigabilité des associations
- **Client --- passe --- Invoice** : Association unidirectionnelle (de Client vers Invoice). Un client peut accéder à ses factures, mais une facture n’a pas besoin de pointer directement vers un client (le `username` dans Invoice suffit). Cela simplifie la gestion des références.
- **DeliveryWorker --- gère --- Invoice** : Unidirectionnelle (de DeliveryWorker vers Invoice). Un livreur accède aux factures qu’il gère, mais une facture n’a pas besoin de pointer vers le livreur.
- **Invoice --- contient --- Order** : Unidirectionnelle (de Invoice vers Order). Une facture contient plusieurs commandes, mais une commande n’a pas besoin de pointer directement vers la facture.
- **Order --- référence --- Meal** : Unidirectionnelle (de Order vers Meal). Une commande référence un repas, mais un repas n’a pas besoin de pointer vers les commandes.

### b. Encapsulation et visibilité
- **Attributs** : Les attributs doivent être protégés avec une visibilité appropriée :
  - Attributs comme `username`, `password` (dans User), `nom`, `prix` (dans Meal), etc., doivent être **privés** (- en UML).
  - Opérations comme `commander()`, `annulerCommande()`, ou `supprimerFacture()` doivent être **publiques** (+ en UML).
- **Constructeurs** : Les constructeurs protégés peuvent être utilisés dans les sous-classes (`Client`, `DeliveryWorker`) pour redéfinir la construction à partir de `User`.

### c. Contraintes supplémentaires
- **Unicité** : Les attributs `username` (dans User) et `nom` (dans Meal) doivent être uniques, comme spécifié dans les contraintes du projet.
- **Statut de la facture** : L’attribut `status` de `Invoice` est limité aux valeurs `"Pending"`, `"Delivered"`, `"Fake Client"`.
- **Annulation** : Seules les factures en statut `"Pending"` peuvent être annulées (contrainte fonctionnelle à intégrer dans le modèle).

### d. Diagramme de classes raffiné
*(À représenter graphiquement dans un outil UML comme Enterprise Architect ou Lucidchart, non inclus dans ce document textuel)*

## 2. Modélisation du cycle de vie des objets de la classe `Invoice`

La classe `Invoice` est centrale dans les interactions entre clients et livreurs. Son cycle de vie est modélisé via un diagramme de machines à états.

### a. Identification des états significatifs
- **EnConstruction** : État initial lors de la création de la facture (lors d’une commande).
- **Pending** : La facture est en attente de livraison (`status = "Pending"`).
- **Delivered** : La facture est livrée (`status = "Delivered"`).
- **FakeClient** : Le client est marqué comme faux (`status = "Fake Client"`).
- **EnDestruction** : État final lors de la suppression de la facture (annulation ou suppression).

### b. Transitions entre états
- **EnConstruction → Pending** : Après la création de la facture (lors de la commande).
- **Pending → Delivered** : Événement `mettreAJourStatut("Delivered")` par le livreur, condition : `status = "Pending"`, action : `status = "Delivered"`.
- **Pending → FakeClient** : Événement `mettreAJourStatut("Fake Client")` par le livreur, condition : `status = "Pending"`, action : `status = "Fake Client"`.
- **Pending → EnDestruction** : Événement `annulerCommande()` par le client ou `supprimerFacture()` par le livreur, condition : `status = "Pending"`.
- **{Delivered, FakeClient} → EnDestruction** : Événement `supprimerFacture()` par le livreur.

## 3. Deuxième raffinement

### a. Traduction des associations en attributs
- **Client --- passe --- Invoice** : Unidirectionnelle, multiplicité `1 --- *`. Dans `Client`, ajout d’un attribut `factures: List<Invoice>`. Pas d’attribut inverse dans `Invoice` (le `username` suffit).
- **DeliveryWorker --- gère --- Invoice** : Unidirectionnelle, multiplicité `1 --- *`. Dans `DeliveryWorker`, ajout d’un attribut `facturesGérées: List<Invoice>` (optionnel, selon l’implémentation).
- **Invoice --- contient --- Order** : Unidirectionnelle, multiplicité `1 --- *`. Dans `Invoice`, ajout d’un attribut `commandes: List<Order>`. Pas d’attribut inverse dans `Order`.
- **Order --- référence --- Meal** : Unidirectionnelle, multiplicité `* --- 1`. Dans `Order`, l’attribut `mealName: String` est une référence au repas (par son nom). Pas d’attribut inverse dans `Meal`.

**Classes mises à jour** :
- **Client** : Ajout de `factures: List<Invoice>`.
- **DeliveryWorker** : Ajout de `facturesGérées: List<Invoice>` (optionnel).
- **Invoice** : Ajout de `commandes: List<Order>`.

### b. Traduction des agrégations/compositions
- **Invoice --- contient --- Order** : Composition, car les commandes (`Order`) n’ont de sens que dans le contexte d’une facture (`Invoice`). Si une facture est supprimée, les commandes associées doivent l’être aussi.
  - **Traduction** : `Invoice` a un attribut `commandes: List<Order>`, et le destructeur de `Invoice` doit supprimer les objets `Order` associés.

### c. Traduction des diagrammes de séquence et machines-états en algorithmes

#### DSUC1 : Client Commande des Repas
**Description textuelle** :
- Le client sélectionne des repas et clique sur "Commander".
- La façade crée une facture avec le total.
- Pour chaque repas sélectionné, une commande est ajoutée avec la quantité.
- La facture est affichée au client.

**Algorithme** :
```pseudo
PROCEDURE commanderRepas(client: Client, repasSelectionnes: List<RepasQuantite>)
  facture = NOUVELLE Invoice(client.username, "Pending")
  total = 0
  POUR CHAQUE repasQuantite DANS repasSelectionnes
      commande = NOUVELLE Order(repasQuantite.nomRepas, repasQuantite.quantite)
      facture.commandes.ajouter(commande)
      total = total + (repasQuantite.prix * repasQuantite.quantite)
  FIN POUR
  facture.total = total
  client.factures.ajouter(facture)
  AFFICHER facture
FIN PROCEDURE
```

#### DSUC2 : Client Annule une Commande
**Description textuelle** :
- Le client sélectionne une commande et clique sur "Cancel".
- La façade vérifie si l’état est `"Pending"`.
- Si oui, les commandes liées et la facture sont supprimées.
- Un message de confirmation ou d’erreur est affiché.

**Algorithme** :
```pseudo
PROCEDURE annulerCommande(client: Client, facture: Invoice)
  SI facture.status == "Pending" ALORS
      client.factures.supprimer(facture)
      POUR CHAQUE commande DANS facture.commandes
          SUPPRIMER commande
      FIN POUR
      SUPPRIMER facture
      AFFICHER "Commande annulée avec succès"
  SINON
      AFFICHER "Erreur : Seules les factures en attente peuvent être annulées"
  FIN SI
FIN PROCEDURE
```

#### DSUC3 : Livreur Supprime une Facture
**Description textuelle** :
- Le livreur sélectionne une facture et clique sur "Delete".
- Une confirmation est demandée.
- Si "Oui", les commandes liées et la facture sont supprimées.
- Un message de confirmation est affiché.

**Algorithme** :
```pseudo
PROCEDURE supprimerFacture(livreur: DeliveryWorker, facture: Invoice)
  confirmation = DEMANDER_CONFIRMATION("Voulez-vous supprimer cette facture ?")
  SI confirmation == "Oui" ALORS
      livreur.facturesGérées.supprimer(facture)
      POUR CHAQUE commande DANS facture.commandes
          SUPPRIMER commande
      FIN POUR
      SUPPRIMER facture
      AFFICHER "Facture supprimée avec succès"
  SINON
      AFFICHER "Suppression annulée"
  FIN SI
FIN PROCEDURE
```