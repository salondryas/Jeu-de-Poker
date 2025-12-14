### Slice 0 : La Fondation (Modèle de 5 cartes)

- **Objectif** : Créer les classes de base pour représenter le jeu.
- **Implémentation** :
  - Créer `ValeurCarte` (enum), `CouleurCarte` (enum).
  - Créer `Carte.java` (qui utilise les enums).
  - Créer `MainJoueur.java` qui contient une `List<Carte>` de **5 cartes**.
  - Créer `EntreeStandard.java` qui lit une chaîne de 5 cartes (ex: "APi RCa 2Tr 3Co 5Tr").
- **Résultat** : Le programme peut lire deux mains de 5 cartes, mais ne peut pas les comparer.

---

### Slice 1 : Règle "Plus Haute Carte"

**Objectif** : Implémenter la règle de comparaison la plus simple

- **Implémentation** :
  - Créer `Combinaison.java`.
  - Implémenter `comparerHauteur()` : trier les 5 cartes de chaque main et les comparer une par une.
  - Gérer les égalités (kickers) en comparant les 5 cartes.
    **Résultat** : Le programme peut résoudre les exemples 1 et 3 du l'énoncé (As bat Roi, et Égalité) .

---

### Slice 2 : Règle "Paire"

**Objectif** : Ajouter la détection et la comparaison de la "Paire".

- **Implémentation** :
  - Dans `Combinaison.java`, implémenter `trouverValeurPaire()` .
  - Mettre à jour la logique principale (`comparerMains`) pour :
    1.  Vérifier si un joueur a une Paire.
    2.  `Paire` bat toujours `Plus Haute Carte`.
    3.  Si deux Paires : comparer la valeur de la paire.
    4.  Si Paires égales : comparer les 3 "kickers" (en réutilisant `comparerHauteur` sur les kickers).
        **Résultat** : Le programme peut résoudre l'exemple 2 (Paire de 5 bat Haute carte As) .

---

### Slice 3 : Règle "Deux Paires"

**Objectif** : Ajouter la détection des "Deux Paires".

- **Implémentation** :
  - Mettre à jour `comparerMains` pour que `Deux Paires` batte `Paire` et `Haute Carte`.
  - Gérer les égalités : comparer la paire la plus élevée, puis la seconde paire, puis le kicker.

---

### Slice 4 : Règle "Brelan"

**Objectif** : Ajouter la détection du "Brelan" (3 cartes identiques) et vérification des cartes saisies par les joueurs

- **Implémentation** :
  - Mettre à jour `comparerMains` pour que `Brelan` batte `Deux Paires`.
  - Gérer les égalités : comparer la valeur des 3 cartes.
  - Création d'une classe EntreeStandard : vérifie la validité des cartes saisies par les joueurs

---

### Slice 5 : Gestion de l'entrée standard

**Objectif** : Vérifier la validité des cartes saisies par les joueurs

- **Implémentation** :
  - Création d'une classe EntreeStandard
  - Méthodes: le constructeur de la classe et la méthode `isInputvalid()`
  - Ensemble/conditions de cartes valides modélisés avec des Map et Set

---

### Slice 6 : Gestion de la hiérarchie des combinaisons de cartes

**Objectif** : Attrbuer des scores pour chaque type de combinaison de cartes pour les hiérarchiser

- **Implémentation** :
  - Création d'une énumération TypeMain
  - Méthodes: constructeur, getters et setters liés à l'enum

---

### Slice 7 : Généralisation de l'ensemble du code précédent pour des mains de 5 cartes chacunes

**Objectif** : Adapter les classes Cartes, MainJoueur, Comparaison et EntreeStandard pour des mains de 5 cartes.

- **Implémentation** :
  - Ajustement du nombre de cartes à saisir dans l'entrée standard

---

### Slice 8 : Structuration des identification de combinaisons de cartes

**Objectif** : Structurer les tests qui permettent d'identifier quelle combinaison de cartes une main possède

- **Implémentation** :
  - Création d'une classe Combinaison
  - Implémentation des attrbuts, méthode de comptage d'occurence des valeurs de chaque carte, constructeur
  - Déplacement des méthodes qui servent à identifer les "plus hautes cartes", les "paires", les "deux paires" et les "brelans"
  - méthode de description des combinaisons

---

### Slice 8 : Mise en place de deuxièmes versions des classes Combinaison et Comparaison

**Objectif** : Explorer et implémenter des méthodes/raisonnements potentiellement plus efficiente que les versions 1

- **Implémentation** :
  - Création d'une classe Combinaison.2 et Comparaison.v2
  - Implémentation des attributs, méthode de comptage d'occurence des valeurs de chaque carte, constructeur
  - Déplacement des méthodes qui servent à identifer les "plus hautes cartes", les "paires", les "deux paires" et les "brelans"

---

### Slice 9 : Trier les mains dans un ordre spécifique

**Objectif** : Trier les mains afin de faciliter l'identification des combinaisons de cartes que possède une main

- **Implémentation** :
  - Méthodes de tris par ordre décroissant des valeurs des cartes et par ordre de ces valeurs + par priorités des combinaisons

---

### Slice 10 : Règle "Suite"

**Objectif** : Trouver les main qui possède la combinaison "Suite"

- **Implémentation** :
  - Méthode estSuite ou isSuite dans les classes combinaisons
  - Raisonnement par l'absurde (on suppose que la main possède cette combinaison jusqu'à preuve du contraire)

---

### Slice 11 : Règle "Couleur"

**Objectif** : Trouver les main qui possède la combinaison "Couleur"

- **Implémentation** :
  - Méthode estCouleur ou isSameColour dans les classes combinaisons
  - getter de couleur

---

### Slice 12 : Règle "Full"

**Objectif** : Trouver les main qui possède la combinaison "Full"

- **Implémentation** :
  - Méthodes isFull dans Combinaison_v2 et condition caracteristique d'un full dans Comparaison

---

### Slice 13 : Règle "Carré"

**Objectif** : Trouver les main qui possède la combinaison "Couleur"

- **Implémentation** :
  - Méthodes isCarre dans Combinaison_v2 et condition caracteristique d'un carré dans Comparaison

---

### Slice 14 : Règle "Quinte Flush "

**Objectif** : Trouver les main qui possède la combinaison "Quinte Flush"

- **Implémentation** :
  - Condition: si la main possède une suite et des cartes de même couleur alors c'est une quinte flush

---

### Slice 15 : Règle "Quinte Flush Royale"

**Objectif** : Trouver les main qui possède la combinaison "Quinte Flush Royale"

- **Implémentation** :
  - Condition: si la main possède une suite et des cartes de même couleur et que le premier élément est une valeur associée à 14 alors c'est une quinte flush royale

---


### Slice 16 : Gestion des kickers

**Objectif** : Vérifier si l'on peut passer d'une simple égalité à une main gagnante. On cherche à résoudre les cas d'égalité entre deux mains pour déterminer la main gagnante tout compte fait et donc voir si l'on peut dépasser la simple égalité.

- **Implémentation** :
  - Méthodes: casEgaliteParfaite, resoudreEgalite, resoudreEgaliteDeuxPaires, comparerKickers, comparerListesKickers ...

---

### Slice 17 : Redaction du code executable final

**Objectif** : Exécuter le code pour pouvoir lancer le jeu

- **Implémentation** :
  - Instanciation des classes MainJoueur, Combinaison(resp. Compraison) et Comparaion(resp. Comparaison_v2) dans mla classe exécutable jeuPoker

# NB: On utilise surtout les versions numéro 1 en priorité. Les versions numéro 2 sont utilisées pour observer les améliorations potentielles.

---
