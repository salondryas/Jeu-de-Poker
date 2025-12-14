import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Scanner;

// Correction d'un problème les tests sur la validité ajoutées les cartes une à une à la fin des tests
//or si la deuxième était fausse la première restait dans la main et causait un bug, maintenant les cartes sont
//ajoutées à cartesUtilisees une fois toute validées (A SUPPRIMMER)

public class EntreeStandard {

    private List<Carte> listeCartes = new ArrayList<>();
    private static List<String> cartesUtilisees = new ArrayList<>();

    private static final Set<Character> lettresAcceptees = Set.of('V', 'D', 'R', 'A');
    private static final Set<String> couleursAcceptees = Set.of("Tr", "Ca", "Co", "Pi");

    public EntreeStandard(int numeroJoueur) {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.print("Main " + numeroJoueur +":");
            input = scanner.nextLine().trim();
        } while (!isInputValid(input));

        String[] brokeDownInput = input.split("\\s+"); //construit cartesUtilisees une fois les cartes vérifiées par isInputValid
        for (String carte : brokeDownInput) {
            cartesUtilisees.add(carte);
        }
        // on ne ferme pas le scanner ici pour ne pas bloquer d'autres entrées ailleurs

        buildHand(cartesUtilisees);
    }

    public List<Carte> getListeCartes() {
        return listeCartes;
    }

    /**
     * Construit la main du joueur avec les 5 dernières cartes validées.
     */
    private void buildHand(List<String> cartesUtilisees) {
        int n = cartesUtilisees.size();
        for (int i = n - 5; i < n; i++) {
            listeCartes.add(new Carte(cartesUtilisees.get(i)));
        }
    }
    //corrige un problème dans les Tests
    public static void resetCartesUtilisees() {
        cartesUtilisees.clear();
    }

    /**
     * Vérifie que l'entrée utilisateur est correcte.
     */
    public static boolean isInputValid(String input) {
        String[] brokeDownInput = input.split("\\s+");

        // On attend 5 cartes
        if (brokeDownInput.length != 5) {
            System.out.println("Il faut entrer exactement 5 cartes.");
            return false;
        }
        List<String> cartesTemp = new ArrayList<>();
        for (String carte : brokeDownInput) {
            // déjà utilisée ?
            if (cartesUtilisees.contains(carte)) {
                System.out.println("La carte " + carte + " est déjà utilisée.");
                return false;
            }

            // longueur incorrecte (3 ou 4 caractères max)
            if (carte.length() < 3 || carte.length() > 4) {
                System.out.println("Format invalide pour " + carte);
                return false;
            }

            // si "10", doit être au début
            if (carte.length() == 4 && !carte.startsWith("10")) {
                System.out.println("Mauvais format pour " + carte);
                return false;
            }

            // valeur de la carte
            char valeur = carte.charAt(0);
            boolean valeurValide = (valeur >= '2' && valeur <= '9') || lettresAcceptees.contains(valeur) || carte.startsWith("10");

            if (!valeurValide) {
                System.out.println("Valeur invalide pour " + carte);
                return false;
            }

            // couleur de la carte (2 derniers caractères)
            String couleur = carte.substring(carte.length() - 2);
            if (!couleursAcceptees.contains(couleur)) {
                System.out.println("Couleur invalide pour " + carte);
                return false;
            }
            cartesTemp.add(carte);
        }
        cartesUtilisees.addAll(cartesTemp);
        return true;
    }
}
