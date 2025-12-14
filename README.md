# 🃏 Jeu de Poker (Texas Hold'em) - Java Edition

Une implémentation robuste du Texas Hold'em Poker en **Java**. Ce projet met en œuvre une architecture Orientée Objet stricte pour gérer la logique complexe des combinaisons de cartes.

## 🚀 Fonctionnalités
- **Analyse de Main Complète :** Détection automatique de toutes les combinaisons (Quinte Flush Royale, Carré, Full, Couleur, Suite, Brelan, etc.).
- **Moteur de Comparaison :** Algorithme capable de départager deux mains, incluant la gestion fine des "kickers" (cartes de départage).
- **Architecture Solide :** Utilisation de `Enum` pour les types de mains, `HashMap` pour le comptage des occurrences, et `Comparator` pour les tris.
- **Tests Unitaires :** Validation de la logique via JUnit 4/5.

## 🛠️ Stack Technique
- **Langage :** Java 21
- **Tests :** JUnit
- **Outils :** IntelliJ IDEA, Git

## 💻 Comment lancer le projet

### Option 1 : Via IntelliJ IDEA (Recommandé)
1. Clonez le dépôt : `git clone https://github.com/salondryas/Jeu-de-Poker.git`
2. Ouvrez le dossier dans IntelliJ.
3. Faites un clic droit sur `src/JeuPoker.java` et sélectionnez **"Run 'JeuPoker.main()'"**.

### Option 2 : Via la ligne de commande
Compilez et exécutez les sources directement :

```bash
cd dojo-poker-25-26-fise-25-26-poker-team-n-se
javac -d bin src/*.java
java -cp bin JeuPoker
