# Meal Delivery Application

**Auteur :** ABED Mohamed Aziz  
**Date :** 10 avril 2025  

## Conception Préliminaire : Meal Delivery Application

---

### 1) Étape 1 : Aspects Statiques

#### a. Liste des Classes et Diagramme de Classes

**Les classes métier :**

1. **User (classe abstraite)**  
   - **Attributs :** username (texte), password (texte)  
   - **Description :** Classe parente pour Client et DeliveryWorker.

2. **Client (hérite de User)**  
   - **Attributs :** nom (texte), nbCommandes (nombre)  
   - **Description :** Utilisateur qui passe des commandes.

3. **DeliveryWorker (hérite de User)**  
   - **Attributs :** nom (texte)  
   - **Description :** Utilisateur qui gère les livraisons.

4. **Meal**  
   - **Attributs :** nom (texte, unique), prix (nombre)  
   - **Description :** Repas disponible à la commande.

5. **Invoice (Facture)**  
   - **Attributs :** invoiceID (texte), username (texte), total (nombre), status (texte : "Pending", "Delivered", "Fake Client")  
   - **Description :** Représente une commande avec son état.

6. **Order (classe d’association)**  
   - **Attributs :** mealName (texte), quantity (nombre)  
   - **Description :** Relie une facture à un repas avec la quantité commandée.

**Associations :**
- **Client --- passe --- Invoice :** Multiplicité 1 --- * (un client peut avoir plusieurs factures).  
- **DeliveryWorker --- gère --- Invoice :** Multiplicité 1 --- * (un livreur peut gérer plusieurs factures).  
- **Invoice --- contient --- Order :** Multiplicité 1 --- * (une facture contient plusieurs commandes).  
- **Order --- référence --- Meal :** Multiplicité * --- 1 (plusieurs commandes peuvent référencer un même repas).

**Diagramme de classes :**  
![Diagramme De Classes](Diagrammes/Diagramme%20De%20Classes.svg)

---

### 2) Étape 2 : Aspects Dynamiques

#### a. Diagrammes de Séquence

1. **DSUC1 : Client Commande des Repas**  
   **Description textuelle :**  
   - Le client sélectionne des repas et clique sur "Commander".  
   - La façade crée une facture avec le total.  
   - Pour chaque repas sélectionné, une commande est ajoutée avec la quantité.  
   - La facture est affichée au client.  
   ![Diagramme De Séquence - Client Commande Des Repas](Diagrammes/Diagramme%20De%20Séquence%20-%20Client%20Commande%20Des%20Repas.svg)

2. **DSUC2 : Client Annule une Commande**  
   **Description textuelle :**  
   - Le client sélectionne une commande et clique sur "Cancel".  
   - La façade vérifie si l’état est "Pending".  
   - Si oui, les commandes liées et la facture sont supprimées.  
   - Un message de confirmation ou d’erreur est affiché.  
   ![Diagramme De Séquence - Client Annule Une Commande](Diagrammes/Diagramme%20De%20Séquence%20-%20Client%20Annule%20Une%20Commande.svg)

3. **DSUC3 : Livreur Supprime une Facture**  
   **Description textuelle :**  
   - Le livreur sélectionne une facture et clique sur "Delete".  
   - Une confirmation est demandée.  
   - Si "Oui", les commandes liées et la facture sont supprimées.  
   - Un message de confirmation est affiché.  
   ![Diagramme De Séquence - Livreur Supprime Une Facture](Diagrammes/Diagramme%20De%20Séquence%20-%20Livreur%20Supprime%20Une%20Facture.svg)

---

### Document Complet
- **Conception Préliminaire complète :** [Meal Delivery Application - AGL - Rendu 2](Meal%20Delivery%20Application%20-%20AGL%20-%20Rendu%202.pdf)

