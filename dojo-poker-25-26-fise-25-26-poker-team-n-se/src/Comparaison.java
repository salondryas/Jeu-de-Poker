import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Comparaison {

    private Combinaison evaluateur;

    // AJOUT : Une variable pour stocker le message détaillé (kicker, etc.)
    private String messageVictoireSpecific = null;

    public Comparaison() {
        this.evaluateur = new Combinaison();
    }

    // AJOUT : Un getter pour récupérer ce message
    public String getMessageVictoireSpecific() {
        return messageVictoireSpecific;
    }

    public int comparerMains(MainJoueur main1, MainJoueur main2) {
        // AJOUT : On remet le message à null au début de chaque comparaison
        this.messageVictoireSpecific = null;

        // 1. Trier
        main1.trierDecroissant();
        main2.trierDecroissant();

        // ... (Le reste du début de la méthode reste identique : Compter, Evaluer Force) ...
        Map<Integer, Integer> compteur1 = evaluateur.compterCartes(main1.getCartes());
        Map<Integer, Integer> compteur2 = evaluateur.compterCartes(main2.getCartes());
        Map<String, Integer> compteurCouleur1 = evaluateur.compterCouleurCartes(main1.getCartes());
        Map<String, Integer> compteurCouleur2 = evaluateur.compterCouleurCartes(main2.getCartes());

        TypeMain forceMain1 = evaluerForce(main1, compteur1, compteurCouleur1);
        TypeMain forceMain2 = evaluerForce(main2, compteur2, compteurCouleur2);

        // 4. Comparer les rangs
        if (forceMain1.getRang() > forceMain2.getRang()) return 1;
        if (forceMain2.getRang() > forceMain1.getRang()) return 2;

        // 5. Gérer les égalités
        return resoudreEgalite(main1, main2, forceMain1, compteur1, compteur2);
    }

    // ... (La méthode evaluerForce reste inchangée) ...
    private TypeMain evaluerForce(MainJoueur main, Map<Integer, Integer> compteurValeur, Map<String, Integer> compteurCouleur) {
        // Copiez-collez votre méthode existante ici
        String couleur = evaluateur.trouverCouleurMain(compteurCouleur);
        boolean suite = evaluateur.estSuite(main.getCartes());

        if (couleur != null && suite) {
            return (main.getCartes().get(0).getNumero() == 14) ? TypeMain.QUINTE_FLUSH_ROYALE : TypeMain.QUINTE_FLUSH;
        }
        if (evaluateur.trouverValeurCarre(compteurValeur) > 0) return TypeMain.CARRE;
        if (evaluateur.trouverValeurBrelan(compteurValeur) > 0 && evaluateur.trouverValeurPaire(compteurValeur) > 0) return TypeMain.FULL;
        if (couleur != null) return TypeMain.COULEUR;
        if (suite) return TypeMain.SUITE;
        if (evaluateur.trouverValeurBrelan(compteurValeur) > 0) return TypeMain.BRELAN;
        if (evaluateur.trouverValeurDeuxPaires(compteurValeur) > 0) return TypeMain.DEUX_PAIRES;
        if (evaluateur.trouverValeurPaire(compteurValeur) > 0) return TypeMain.PAIRE;

        return TypeMain.CARTE_HAUTE;
    }

    // ... (La méthode resoudreEgalite reste inchangée) ...
    private int resoudreEgalite(MainJoueur main1, MainJoueur main2, TypeMain force, Map<Integer, Integer> c1, Map<Integer, Integer> c2) {
        switch (force) {
            case QUINTE_FLUSH, SUITE:
                return comparerValeurCarteHaute(main1, main2);
            case CARRE:
                return comparerValeurCombinaison(evaluateur.trouverValeurCarre(c1), evaluateur.trouverValeurCarre(c2));
            case FULL, BRELAN:
                return comparerValeurCombinaison(evaluateur.trouverValeurBrelan(c1), evaluateur.trouverValeurBrelan(c2));
            case COULEUR, CARTE_HAUTE:
                return comparerHauteur(main1, main2);
            case DEUX_PAIRES:
                return resoudreEgaliteDeuxPaires(main1, main2, c1, c2);
            case PAIRE:
                return resoudreEgalitePaire(main1, main2, c1, c2);
            default:
                return 0;
        }
    }

    // ... (comparerValeurCombinaison, comparerValeurCarteHaute restent inchangés) ...
    private int comparerValeurCombinaison(int val1, int val2) {
        if (val1 > val2) return 1;
        if (val2 > val1) return 2;
        return 0;
    }

    private int comparerValeurCarteHaute(MainJoueur main1, MainJoueur main2) {
        int v1 = main1.getCartes().get(0).getNumero();
        int v2 = main2.getCartes().get(0).getNumero();
        return comparerValeurCombinaison(v1, v2);
    }


    // === MODIFICATION MAJEURE ICI ===
    private int resoudreEgalitePaire(MainJoueur main1, MainJoueur main2, Map<Integer, Integer> c1, Map<Integer, Integer> c2) {
        int paire1 = evaluateur.trouverValeurPaire(c1);
        int paire2 = evaluateur.trouverValeurPaire(c2);

        int resultatPaire = comparerValeurCombinaison(paire1, paire2);
        if (resultatPaire != 0) return resultatPaire;

        // 1. Déterminer le résultat du tie-break par kickers
        int resultatKickers = comparerKickers(main1, main2, paire1);

        // 2. Enregistrer le message DANS LA VARIABLE au lieu d'afficher
        if (resultatKickers == 1) {
            int kickerVainqueur = extraireKickers(main1, paire1).get(0).getNumero();
            this.messageVictoireSpecific = "Main 1 gagne avec Paire de " + Carte.numeroToString(paire1) +
                    " grâce au kicker " + Carte.numeroToString(kickerVainqueur);
        } else if (resultatKickers == 2) {
            int kickerVainqueur = extraireKickers(main2, paire2).get(0).getNumero();
            this.messageVictoireSpecific = "Main 2 gagne avec Paire de " + Carte.numeroToString(paire2) +
                    " grâce au kicker " + Carte.numeroToString(kickerVainqueur);
        }

        return resultatKickers;
    }

    // ... (resoudreEgaliteDeuxPaires reste inchangé ou vous pouvez appliquer la même logique) ...
    private int resoudreEgaliteDeuxPaires(MainJoueur main1, MainJoueur main2, Map<Integer, Integer> c1, Map<Integer, Integer> c2) {
        int ph1 = evaluateur.trouverValeurDeuxPaires(c1);
        int ph2 = evaluateur.trouverValeurDeuxPaires(c2);

        int resHaute = comparerValeurCombinaison(ph1, ph2);
        if (resHaute != 0) return resHaute;

        int pb1 = evaluateur.trouverValeurDeuxiemePaire(c1, ph1);
        int pb2 = evaluateur.trouverValeurDeuxiemePaire(c2, ph2);

        int resBasse = comparerValeurCombinaison(pb1, pb2);
        if (resBasse != 0) return resBasse;

        return comparerKickersDeuxPaires(main1, main2, ph1, pb1);
    }

    // ... (Toutes les méthodes 'comparerKickers', 'extraireKickers', 'comparerHauteur' restent inchangées) ...
    private int comparerKickers(MainJoueur main1, MainJoueur main2, int valeurCombinaison) {
        List<Carte> k1 = extraireKickers(main1, valeurCombinaison);
        List<Carte> k2 = extraireKickers(main2, valeurCombinaison);
        return comparerListesKickers(k1, k2);
    }

    private int comparerKickersDeuxPaires(MainJoueur main1, MainJoueur main2, int p1, int p2) {
        List<Carte> k1 = extraireKickersDeuxPaires(main1, p1, p2);
        List<Carte> k2 = extraireKickersDeuxPaires(main2, p1, p2);
        return comparerListesKickers(k1, k2);
    }

    private int comparerListesKickers(List<Carte> k1, List<Carte> k2) {
        for (int i = 0; i < k1.size(); i++) {
            int v1 = k1.get(i).getNumero();
            int v2 = k2.get(i).getNumero();
            if (v1 > v2) return 1;
            if (v2 > v1) return 2;
        }
        return 0;
    }

    private List<Carte> extraireKickers(MainJoueur main, int val) {
        List<Carte> kickers = new ArrayList<>();
        for (Carte c : main.getCartes()) {
            if (c.getNumero() != val) kickers.add(c);
        }
        return kickers;
    }

    private List<Carte> extraireKickersDeuxPaires(MainJoueur main, int p1, int p2) {
        List<Carte> kickers = new ArrayList<>();
        for (Carte c : main.getCartes()) {
            if (c.getNumero() != p1 && c.getNumero() != p2) kickers.add(c);
        }
        return kickers;
    }

    public int comparerHauteur(MainJoueur main1, MainJoueur main2) {
        for (int i = 0; i < 5; i++) {
            int v1 = main1.getCartes().get(i).getNumero();
            int v2 = main2.getCartes().get(i).getNumero();
            if (v1 > v2) return 1;
            if (v2 > v1) return 2;
        }
        return 0;
    }
}