Application de Livraison de Repas - Conception Préliminaire 2
Introduction
L'application de livraison de repas est une application Java avec une interface graphique permettant aux clients de commander des repas, aux livreurs de gérer les livraisons et à un administrateur de superviser les utilisateurs et le catalogue des repas. Cette phase de conception préliminaire raffine le diagramme de classes, modélise le cycle de vie des objets clés et traduit les associations, agrégations et diagrammes de séquence en attributs et algorithmes.
Raffinement du Diagramme de Classes
a. Navigabilité des Associations

Client --- passe --- Invoice : Unidirectionnelle (Client vers Invoice). Un client accède à ses factures, mais une facture n'a pas besoin de pointer directement vers le client (le username suffit).
DeliveryWorker --- gère --- Invoice : Unidirectionnelle (DeliveryWorker vers Invoice). Un livreur accède aux factures gérées, mais une facture n'a pas besoin de pointer vers le livreur.
Invoice --- contient --- Order : Unidirectionnelle (Invoice vers Order). Une facture contient plusieurs commandes, mais une commande n'a pas besoin de pointer vers la facture.
Order --- référence --- Meal : Unidirectionnelle (Order vers Meal). Une commande référence un repas, mais un repas n'a pas besoin de pointer vers les commandes.

b. Encapsulation et Visibilité

Attributs : Les attributs comme username, password (dans User), nom, prix (dans Meal) sont privés (- en UML).
Opérations : Les méthodes comme commander(), annulerCommande(), supprimerFacture() sont publiques (+ en UML).
Constructeurs : Les constructeurs protégés dans les sous-classes (Client, DeliveryWorker) permettent une redéfinition à partir de User.

c. Contraintes Supplémentaires

Unicité : Les attributs username (dans User) et nom (dans Meal) doivent être uniques.
Statut de la facture : L'attribut status de Invoice est limité à "Pending", "Delivered", "Fake Client".
Annulation : Seules les factures avec status = "Pending" peuvent être annulées.

d. Diagramme de Classes Raffiné

Classes :

User (abstraite) :
-username: String [unique]
-password: String
+getUsername(): String
+getPassword(): String


Client (hérite de User) :
-nom: String
-nbCommandes: Integer
+commander()
+annulerCommande()


DeliveryWorker (hérite de User) :
-nom: String
+mettreAJourStatut()
+supprimerFacture()


Meal :
-nom: String [unique]
-prix: Double
+getNom(): String
+getPrix(): Double


Invoice :
-invoiceID: String
-username: String
-total: Double
-status: String ["Pending", "Delivered", "Fake Client"]
+getStatus(): String
+setStatus(String)


Order :
-mealName: String
-quantity: Integer
+getMealName(): String
+getQuantity(): Integer



Associations :

Client --- passe --- Invoice [1 --- *] (unidirectionnelle)
DeliveryWorker --- gère --- Invoice [1 --- *] (unidirectionnelle)
Invoice --- contient --- Order [1 --- *] (unidirectionnelle)
Order --- référence --- Meal [* --- 1] (unidirectionnelle)

Modélisation du Cycle de Vie des Objets
Diagramme de Machine à États pour Invoice
Le cycle de vie d'un objet Invoice est modélisé comme une machine à états, reflétant les interactions entre clients et livreurs.

États Significatifs :

EnConstruction : État initial lors de la création de la facture (commande).
Pending : Facture en attente de livraison (status = "Pending").
Delivered : Facture livrée (status = "Delivered").
FakeClient : Client marqué comme faux (status = "Fake Client").
EnDestruction : État final lors de la suppression de la facture.

Transitions :

EnConstruction → Pending : Après création (créerFacture()).
Pending → Delivered : Événement mettreAJourStatut("Delivered"), condition : status = "Pending", action : status = "Delivered".
Pending → FakeClient : Événement `mettreAJourStatut(...)System: 

Application de Livraison de Repas - Conception Préliminaire 2
Introduction
L'application de livraison de repas est une application Java avec une interface graphique conçue pour permettre aux clients de commander des repas, aux livreurs de gérer les livraisons, et à un administrateur de superviser les utilisateurs et le catalogue des repas. Cette phase de conception préliminaire raffine le diagramme de classes, modélise le cycle de vie des objets clés, et traduit les associations, agrégations et diagrammes de séquence en attributs et algorithmes. Une base de données MySQL est utilisée pour la persistance des données (utilisateurs, repas, commandes).
Raffinement du Diagramme de Classes
a. Navigabilité des Associations

Client --- passe --- Invoice : Unidirectionnelle (Client vers Invoice). Un client accède à ses factures, mais une facture n'a pas besoin de pointer directement vers le client (le username suffit).
DeliveryWorker --- gère --- Invoice : Unidirectionnelle (DeliveryWorker vers Invoice). Un livreur accède aux factures gérées, mais une facture n'a pas besoin de pointer vers le livreur.
Invoice --- contient --- Order : Unidirectionnelle (Invoice vers Order). Une facture contient plusieurs commandes, mais une commande n'a pas besoin de pointer vers la facture.
Order --- référence --- Meal : Unidirectionnelle (Order vers Meal). Une commande référence un repas, mais un repas n'a pas besoin de pointer vers les commandes.

b. Encapsulation et Visibilité

Attributs : Les attributs comme username, password (dans User), nom, prix (dans Meal) sont privés (- en UML).
Opérations : Les méthodes comme commander(), annulerCommande(), supprimerFacture() sont publiques (+ en UML).
Constructeurs : Les constructeurs protégés dans les sous-classes (Client, DeliveryWorker) permettent une redéfinition à partir de User.

c. Contraintes Supplémentaires

Unicité : Les attributs username (dans User) et nom (dans Meal) doivent être uniques.
Statut de la facture : L'attribut status de Invoice est limité à "Pending", "Delivered", "Fake Client".
Annulation : Seules les factures avec status = "Pending" peuvent être annulées.

d. Diagramme de Classes Raffiné

Classes :

User (abstraite) :
-username: String [unique]
-password: String
+getUsername(): String
+getPassword(): String


Client (hérite de User) :
-nom: String
-nbCommandes: Integer
-factures: List<Invoice>
+commander()
+annulerCommande()


DeliveryWorker (hérite de User) :
-nom: String
-facturesGérées: List<Invoice> [optionnel]
+mettreAJourStatut()
+supprimerFacture()


Meal :
-nom: String [unique]
-prix: Double
+getNom(): String
+getPrix(): Double


Invoice :
-invoiceID: String
-username: String
-total: Double
-status: String ["Pending", "Delivered", "Fake Client"]
-commandes: List<Order>
+getStatus(): String
+setStatus(String)


Order :
-mealName: String
-quantity: Integer
+getMealName(): String
+getQuantity(): Integer



Associations :

Client --- passe --- Invoice [1 --- *] (unidirectionnelle)
DeliveryWorker --- gère --- Invoice [1 --- *] (unidirectionnelle)
Invoice --- contient --- Order [1 --- *] (unidirectionnelle)
Order --- référence --- Meal [* --- 1] (unidirectionnelle)

Modélisation du Cycle de Vie des Objets
Diagramme de Machine à États pour Invoice
Le cycle de vie d'un objet Invoice est modélisé comme une machine à états, reflétant les interactions entre clients et livreurs.

États Significatifs :

EnConstruction : État initial lors de la création de la facture (commande).
Pending : Facture en attente de livraison (status = "Pending").
Delivered : Facture livrée (status = "Delivered").
FakeClient : Client marqué comme faux (status = "Fake Client").
EnDestruction : État final lors de la suppression de la facture (annulation ou suppression).

Transitions :

EnConstruction → Pending : Après création de la facture (créerFacture()).
Pending → Delivered : Événement mettreAJourStatut("Delivered"), condition : status = "Pending", action : status = "Delivered".
Pending → FakeClient : Événement mettreAJourStatut("Fake Client"), condition : status = "Pending", action : status = "Fake Client".
Pending → EnDestruction : Événement annulerCommande() (client) ou supprimerFacture() (livreur), condition : status = "Pending".
{Delivered, FakeClient} → EnDestruction : Événement supprimerFacture() (livreur).

Traduction des Associations en Attributs

Client --- passe --- Invoice : Ajout de factures: List<Invoice> dans Client. Aucun attribut inverse dans Invoice (le username suffit).
DeliveryWorker --- gère --- Invoice : Ajout de facturesGérées: List<Invoice> dans DeliveryWorker (optionnel, selon l'implémentation).
Invoice --- contient --- Order : Ajout de commandes: List<Order> dans Invoice. Aucun attribut inverse dans Order.
Order --- référence --- Meal : L'attribut mealName: String dans Order référence le repas. Aucun attribut inverse dans Meal.

Traduction des Agrégations/Compositions

Invoice --- contient --- Order : Composition. Les commandes n'existent que dans le contexte d'une facture. La suppression d'une facture entraîne la suppression des commandes associées.
Traduction : Invoice possède commandes: List<Order>. Le destructeur de Invoice supprime les objets Order associés.

Traduction des Diagrammes de Séquence en Algorithmes
DSUC1 : Client Commande des Repas

Description :

Le client sélectionne des repas et clique sur "Commander".
La façade crée une facture avec le total.
Pour chaque repas sélectionné, une commande est ajoutée avec la quantité.
La facture est affichée au client.

Algorithme :
commander(List<RepasSelectionnés> repasSelectionnés):
    si repasSelectionnés est vide ou ∃ quantité ≤ 0 alors
        lever une exception "Sélection invalide"
    facture = nouvelle Invoice()
    facture.invoiceID = générerID()
    facture.username = this.username
    facture.status = "Pending"
    total = 0
    pour chaque repasSélectionné dans repasSelectionnés:
        commande = nouvelle Order()
        commande.mealName = repasSélectionné.nom
        commande.quantity = repasSélectionné.quantité
        total += repasSélectionné.prix * repasSélectionné.quantité
        facture.commandes.ajouter(commande)
    facture.total = total
    this.factures.ajouter(facture)
    sauvegarderDansBaseDeDonnées(facture)
    retourner facture

DSUC2 : Client Annule une Commande

Description :

Le client sélectionne une facture et clique sur "Annuler".
La façade vérifie si status = "Pending".
Si oui, les commandes associées et la facture sont supprimées.
Un message de confirmation ou d'erreur est affiché.

Algorithme :
annulerCommande(Invoice facture):
    si facture.status ≠ "Pending" alors
        lever une exception "Impossible d'annuler : facture non en attente"
    this.factures.retirer(facture)
    supprimerDansBaseDeDonnées(facture) // Supprime aussi les commandes associées
    retourner "Commande annulée"

DSUC3 : Livreur Supprime une Facture

Description :

Le livreur sélectionne une facture et clique sur "Supprimer".
Une confirmation est demandée.
Si confirmée, les commandes associées et la facture sont supprimées.
Un message de confirmation est affiché.

Algorithme :
supprimerFacture(Invoice facture):
    confirmation = demanderConfirmation()
    si confirmation = "Oui" alors
        supprimerDansBaseDeDonnées(facture) // Supprime aussi les commandes associées
        retourner "Facture supprimée"
    sinon
        retourner "Suppression annulée"

