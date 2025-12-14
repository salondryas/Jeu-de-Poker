import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainJoueur {
    private static int numero_mainJoueur = 1;
    private List<Carte> cartes;
    private Map<Integer, Integer> tab_occurrences_valeurs = new HashMap<>();

    // Constructeur : on entre les cartes une par une manuellement
    public MainJoueur() {
        this.cartes = new EntreeStandard(numero_mainJoueur).getListeCartes();
        // on affecte à cartes la liste de cartes entrée avec l'entrée standard
        numero_mainJoueur++; // incrementation du numero du joueur par 1
    }

    public MainJoueur(String mainPourTest) {
        this.cartes = new ArrayList<>();
        String[] notationsCartes = mainPourTest.split(" ");
        for (String notation : notationsCartes) {
            this.cartes.add(new Carte(notation));
        }

    }

    // Retourner la liste complète des cartes
    public List<Carte> getCartes() {
        return cartes;
    }

    public Map<Integer, Integer> getTab_occurrences_valeurs() {
        return tab_occurrences_valeurs;
    }

    public void trierDecroissant() {
        for (int i = 0; i < cartes.size() - 1; i++) {
            for (int j = i + 1; j < cartes.size(); j++) {
                if (cartes.get(i).getNumero() < cartes.get(j).getNumero()) {
                    // Échanger les deux cartes
                    Carte temp = cartes.get(i);
                    cartes.set(i, cartes.get(j));
                    cartes.set(j, temp);
                }
            }
        }
    }

    private void getOccurrencesValeurs() {
        for (Carte card : cartes) {
            if (tab_occurrences_valeurs.containsKey(card.getNumero()))
                tab_occurrences_valeurs.put(card.getNumero(), tab_occurrences_valeurs.get(card.getNumero()) + 1);
            else
                tab_occurrences_valeurs.put(card.getNumero(), 1);
        }
    }

    public void triDecroissantSpecifique() {
        getOccurrencesValeurs();

        for (int i = 0; i < cartes.size() - 1; i++) {
            for (int j = i + 1; j < cartes.size(); j++) {
                int occ1 = tab_occurrences_valeurs.get(cartes.get(i).getNumero());
                int occ2 = tab_occurrences_valeurs.get(cartes.get(j).getNumero());

                if (occ2 > occ1 || (occ2 == occ1 && cartes.get(j).getNumero() > cartes.get(i).getNumero())) {
                    Carte temp = cartes.get(i);
                    cartes.set(i, cartes.get(j));
                    cartes.set(j, temp);
                }
            }
        }
    }

    @Override
    public String toString() {
        return cartes.toString();
    }

}