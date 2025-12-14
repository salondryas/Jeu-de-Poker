public class JeuPoker {

    public static void main(String[] args) {
        MainJoueur main1 = new MainJoueur();
        MainJoueur main2 = new MainJoueur();

        Comparaison comparateur = new Comparaison();
        Combinaison evaluateur = new Combinaison();

        int resultat = comparateur.comparerMains(main1, main2);

        // 1. Vérifier d'abord si on a un message de victoire précis (ex: Kicker)
        String messageSpecifique = comparateur.getMessageVictoireSpecific();

        if (messageSpecifique != null) {
            // S'il y a un message spécifique (tie-break kicker), on l'affiche LUI SEUL
            System.out.println(messageSpecifique);
        } else {
            // Sinon, on utilise l'affichage standard générique
            String description1 = evaluateur.decrireMain(main1);
            String description2 = evaluateur.decrireMain(main2);

            if (resultat == 1) {
                System.out.println("Main 1 gagne avec " + description1);
            } else if (resultat == 2) {
                System.out.println("Main 2 gagne avec " + description2);
            } else {
                System.out.println("Egalite !");
            }
        }
    }
}