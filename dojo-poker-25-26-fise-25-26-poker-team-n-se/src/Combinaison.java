import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Combinaison {

    // Crée une map de comptage pour une main.
    public Map<Integer, Integer> compterCartes(List<Carte> main) {
        Map<Integer, Integer> compteur = new HashMap<>();
        for (Carte c : main) {
            int valeur = c.getNumero();
            compteur.put(valeur, compteur.getOrDefault(valeur, 0) + 1);
        }
        return compteur;
    }


    public Map<String, Integer> compterCouleurCartes(List<Carte> main) {
        Map<String, Integer> compteur = new HashMap<>();
        for (Carte c : main) {
            String couleur = c.getCouleur();
            compteur.put(couleur, compteur.getOrDefault(couleur, 0) + 1);
        }
        return compteur;
    }

    //détermine si une main possède une couleur et renvoie cette couleur
    public String trouverCouleurMain(Map<String, Integer> compteur) {
        for (Map.Entry<String, Integer> e : compteur.entrySet()) {
            if (e.getValue() == 5) {
                return e.getKey();
            }
        }
        return null;
    }


    /**
     * Trouve la valeur de la paire (ou 0 si aucune paire).
     */
    public int trouverValeurPaire(Map<Integer, Integer> compteur) {
        for (Map.Entry<Integer, Integer> e : compteur.entrySet()) {
            if (e.getValue() == 2) {
                return e.getKey();
            }
        }
        return 0; // aucune paire
    }

    /**
     * Trouve la valeur du brelan (ou 0 si aucun).
     */
    public int trouverValeurBrelan(Map<Integer, Integer> compteur) {
        for (Map.Entry<Integer, Integer> e : compteur.entrySet()) {
            if (e.getValue() == 3) {
                return e.getKey();
            }
        }
        return 0; // aucun brelan
    }

    /**
     * Trouve la valeur du carré (ou 0 si aucun).
     */
    public int trouverValeurCarre(Map<Integer, Integer> compteur) {
        for (Map.Entry<Integer, Integer> e : compteur.entrySet()) {
            if (e.getValue() == 4) {
                return e.getKey();
            }
        }
        return 0; // aucun carré
    }

    //détermine si la main possède une suite
    public boolean estSuite(List<Carte> main) {

        if (main.size() != 5) return false;

        // Vérifier qu’il n’y a pas de doublons
        for (int i = 0; i < 4; i++) {
            if (main.get(i).getNumero() == main.get(i + 1).getNumero()) {
                return false;
            }
        }

        // Suite normale (ex : 9-8-7-6-5)
        boolean suiteNormale = true;
        for (int i = 0; i < 4; i++) {
            int v1 = main.get(i).getNumero();
            int v2 = main.get(i + 1).getNumero();
            if (v1 - 1 != v2) {
                suiteNormale = false;
                break;
            }
        }

        if (suiteNormale) return true;

        // Cas spécial : A-5-4-3-2
        // main est triée ⇒ A doit être à l'index 0 et valoir 14
        boolean wheel =
                main.get(0).getNumero() == 14 &&  // As
                        main.get(1).getNumero() == 5 &&
                        main.get(2).getNumero() == 4 &&
                        main.get(3).getNumero() == 3 &&
                        main.get(4).getNumero() == 2;

        return wheel;
    }


    //méthode qui renvoie grâce à quelle combinaison le joueur gagne
    public String decrireMain(MainJoueur main) {
        main.trierDecroissant();
        Map<Integer, Integer> compteur = compterCartes(main.getCartes());
        int carre = trouverValeurCarre(compteur);
        int brelan = trouverValeurBrelan(compteur);
        int paire = trouverValeurPaire(compteur);
        int doublePaire = trouverValeurDeuxPaires(compteur);
        int deuxiemePaire = trouverValeurDeuxiemePaire(compteur, doublePaire);
        boolean suite = estSuite(main.getCartes());
        boolean couleur = trouverCouleurMain(compterCouleurCartes(main.getCartes())) != null;
        if (couleur && suite && main.getCartes().get(0).getNumero()==14) return "quinte flush royale";
        if (couleur && suite) return "quinte flush";
        if (carre > 0) return "carré de " + Carte.numeroToString(carre);
        if (brelan > 0 && paire > 0) return "full (brelan de " + Carte.numeroToString(brelan) + " + paire de " + Carte.numeroToString(paire) + ")";
        if (couleur) return "couleur : "+ trouverCouleurMain(compterCouleurCartes(main.getCartes()));
        if (suite) return "suite";
        if (brelan > 0) return "brelan de " + Carte.numeroToString(brelan);
        if (doublePaire>0) return "double paire : paire de " + Carte.numeroToString(doublePaire) + " et paire de "+ Carte.numeroToString(deuxiemePaire);
        if (paire > 0) return "paire de " + Carte.numeroToString(paire);
        return "carte la plus élevée : " + Carte.numeroToString(main.getCartes().get(0).getNumero());
    }

    public int trouverValeurDeuxPaires(Map<Integer, Integer> compteur) {
        int pairesTrouvees = 0;
        int valeurPaireHaute = 0;
        for (Map.Entry<Integer, Integer> e : compteur.entrySet()) {
            if (e.getValue() == 2) {
                pairesTrouvees++;
                if (e.getKey() > valeurPaireHaute) {
                    valeurPaireHaute = e.getKey();
                }
            }
        }
        if (pairesTrouvees == 2) {
            return valeurPaireHaute;
        }
        return 0;
    }
    /**
     * Trouve la valeur de la DEUXIÈME paire (la plus basse).
     * Appelée seulement si on sait déjà qu'on a deux paires.
     */
    public int trouverValeurDeuxiemePaire(Map<Integer, Integer> compteur, int valeurPaireHaute) {
        for (Map.Entry<Integer, Integer> e : compteur.entrySet()) {
            // Si c'est une paire, ET que ce n'est pas la paire haute qu'on a déjà trouvée
            if (e.getValue() == 2 && e.getKey() != valeurPaireHaute) {
                return e.getKey(); // Renvoie la valeur de la seconde paire
            }
        }
        return 0; // (Ne devrait pas arriver si on a "Deux Paires")
    }
}