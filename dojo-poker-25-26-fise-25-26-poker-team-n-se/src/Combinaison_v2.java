import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Combinaison_v2 {
    private List<Carte> Hand; // reçoit la main trié par ordre décroissants des valeurs
    private int score;
    private int valeurAcomparer;
    private String raison; // avec quoi on gagne potentiellement ?

    private Map<Integer, Integer> tab_occurrences_valeurs = new HashMap<>();
    private boolean is_same_colour;
    private boolean is_suite;
    private boolean is_non_suite_couleur;

    public Combinaison_v2(MainJoueur hand) {
        Hand = hand.getCartes(); // main triée

        score = TypeMain.CARTE_HAUTE.getRang(); // par defaut on considere par l'absurde que la main ne correspond à
                                                // aucune
        // combinaison spéciale
        valeurAcomparer = Hand.get(0).getNumero(); // tout le temps vrai apres le tri spécifique
        raison = "carte la plus élevée : " + getValueToDisplay(valeurAcomparer);

        this.tab_occurrences_valeurs = hand.getTab_occurrences_valeurs();
        is_same_colour = isSameColour();
        is_suite = isSuite();
        is_non_suite_couleur = isNonSuiteCouleur();
        // compterOccurrencesValeurs();
        // on raisonne par l'absurde ici aussi: on considere jusqu'à preuve du contraire
        // que la main n'a aucune combinaison speciale
        // d'où le fait qu'on initialise score et valeurAcomparer avec CARTE_HAUTE
        combinaisonParticuliereCheck(is_same_colour, is_suite, is_non_suite_couleur);
    }

    public int getScore() {
        return score;
    }

    public List<Carte> getHand() {
        return Hand;
    }

    public int getValeurAcomparer() {
        return valeurAcomparer;
    }

    public void setValeurAcomparer(int indice) {
        valeurAcomparer = Hand.get(indice).getNumero();
    }

    public int getOccuValeur() { // obtenir le nombre d'occurrences de la valeurAcomparer
        return tab_occurrences_valeurs.get(valeurAcomparer);
    }

    public String getValueToDisplay(int valeurAcomparer) {
        if (valeurAcomparer > 10)
            return "" + Carte.numeroToString(valeurAcomparer); // encore un fois rique de violation du principe
                                                               // d'abstraction de POO, utilisé pour simplifer
                                                               // l'écriture de valeurAcomparer
        return "" + valeurAcomparer;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    private void combinaisonParticuliereCheck(boolean is_same_colour, boolean is_suite, boolean is_non_suite_couleur) {
        if (is_suite && is_same_colour) {
            if (Hand.get(0).getNumero() == 14) { // comme la main est trié par ordre croissant des valeurs de ses cartes
                                                 // et que c'est une suite dans ce cas donc si le premier élément est 14
                                                 // alors necessairement les elements suivants seront 13, 12 ,etc => pas
                                                 // besoin de parcourir toute la main pour checker la quinte flush
                                                 // royale
                score = TypeMain.QUINTE_FLUSH_ROYALE.getRang();
                raison = "quinte flush royale";
            } else {
                score = TypeMain.QUINTE_FLUSH.getRang();
                raison = "quinte flush de valeur : " + getValueToDisplay(valeurAcomparer);
            }

        }

        else if (is_same_colour) {
            score = TypeMain.COULEUR.getRang();
            raison = "cartes de même couleur de valeur : " + getValueToDisplay(valeurAcomparer);
        }

        else if (is_suite) {
            score = TypeMain.SUITE.getRang();
            raison = "suite de valeur : " + getValueToDisplay(valeurAcomparer);
        }

        else if (is_non_suite_couleur) {
            if (isCarre()) {
                score = TypeMain.CARRE.getRang();
                raison = "carré de valeur : " + getValueToDisplay(valeurAcomparer);
            }

            else if (isPaire() && isBrelan()) {
                score = TypeMain.FULL.getRang();
                raison = "full de valeur : " + getValueToDisplay(valeurAcomparer);
            }

            else if (isDeuxPaires()) {
                score = TypeMain.DEUX_PAIRES.getRang();
                raison = "deux paires différentes de valeur : " + getValueToDisplay(valeurAcomparer);
            }

            else if (isBrelan()) {
                score = TypeMain.BRELAN.getRang();
                raison = "brelan de " + getValueToDisplay(valeurAcomparer);
            }

            // si ce n'est ni un full ni une paire ni un brelan alors c'est forcément une
            // deux_paires etant donné qu'on est dans les non_suite_couleur
            else {
                score = TypeMain.PAIRE.getRang();
                raison = "paire de " + getValueToDisplay(valeurAcomparer);
            }
        }

    }

    private boolean isSameColour() {
        for (Carte card : Hand) { // on suppose par l'absurde que toutes les cartes sont de la même couleur et si
                                  // on trouve une couleur differente alors la condition entière est fausse
            if (!card.getCouleur().equals(Hand.get(0).getCouleur()))
                return false;
        }
        return true;
    }

    private boolean isSuite() {
        for (int k = 0; k < Hand.size() - 1; ++k) { // on suppose par l'absurde que la main est une suite et si
            // on trouve une valeur qui casse la suite alors la condition entière est fausse
            if (Hand.get(k + 1).getNumero() + 1 != Hand.get(k).getNumero())
                return false;
        }
        return true;
    }

    private boolean isNonSuiteCouleur() {// si une occurrence d'une valeur est supérieure à 1, c'est-à-dire qu'une
                                         // valeur apparaît plus d'une fois dans la main alors la main est
                                         // necessairmeent ni une suite ni une couleur (c'est independant de la
                                         // condition SameColour car si ce n'était pas le cas alors la main {4Pi 4Pi 5Pi
                                         // 9Pi 2Pi} serait valide ce qui n'est pas possible car on ne peut pas posseder
                                         // des doublons d'une même carte (ici on retrouve deux fois 4Pi ce qui défie
                                         // toute logique du jeu))
        for (int index : tab_occurrences_valeurs.values()) {
            if (index > 1)
                return true;
        }
        return false;
    }

    private boolean isPaire() {
        if (tab_occurrences_valeurs.containsValue(2))
            return true;
        return false;
    }

    private boolean isDeuxPaires() {
        if (tab_occurrences_valeurs.get(valeurAcomparer) == 2
                && tab_occurrences_valeurs.get(Hand.get(2).getNumero()) == 2)
            return true;
        return false;
    }

    private boolean isBrelan() {
        if (tab_occurrences_valeurs.get(valeurAcomparer) == 3)
            return true;
        return false;
    }

    private boolean isCarre() {
        if (tab_occurrences_valeurs.get(valeurAcomparer) == 4)
            return true;
        return false;
    }

}
