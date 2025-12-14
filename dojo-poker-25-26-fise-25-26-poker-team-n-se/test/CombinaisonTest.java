import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CombinaisonTest {
    private Combinaison evaluateur;
    // Cette méthode est lancée avant chaque @Test
    @BeforeEach public void setUp() { evaluateur = new Combinaison();
    }
    /**
     *	Crée une liste de cartes à partir d'une chaîne.
     *	C'est une méthode "helper" pour ce test, elle n'utilise pas MainJoueur.
     */ private List<Carte> creerCartes(String mainStr) {
        List<Carte> cartes = new ArrayList<>();
        String[] notationsCartes = mainStr.split(" ");
        for (String notation : notationsCartes) { cartes.add(new Carte(notation)); // Utilise le constructeur de Carte.java
        }
        return cartes;
    }
    @Test
    public void testCompterCartes_FullHouse() {
// Teste un Full House (Brelan d'As, Paire de Rois)
        List<Carte> cartes = creerCartes("APi ACo ATr RCa RTr");
        Map<Integer, Integer> compteur = evaluateur.compterCartes(cartes);
        assertNotNull(compteur);
        assertEquals(2, compteur.size()); // Doit contenir 2 groupes (As et Rois)//
        assertEquals(3, compteur.get(14)); // Doit compter 3 As (valeur 14)
        assertEquals(2, compteur.get(13)); // Doit compter 2 Rois (valeur 13)
    }
    @Test
    public void testCompterCartes_HauteCarte() {
// Teste une main sans paires
        List<Carte> cartes = creerCartes("2Pi 5Co 7Tr 9Ca ACo");
        Map<Integer, Integer> compteur = evaluateur.compterCartes(cartes);
        assertNotNull(compteur);
        assertEquals(5, compteur.size()); // Doit contenir 5 groupes
        assertEquals(1, compteur.get(14)); // Doit compter 1 As
        assertEquals(1, compteur.get(5)); // Doit compter 1 Cinq
    }
    @Test
    public void testTrouverPaire() {
// Teste Paire de 5
        Map<Integer, Integer> compteur = evaluateur.compterCartes(creerCartes("2Pi 5Co 5Tr 9Ca ACo"));
        assertEquals(5, evaluateur.trouverValeurPaire(compteur));
    }
    @Test
    public void testTrouverBrelan() {
// Teste Brelan d'As
        Map<Integer, Integer> compteur = evaluateur.compterCartes(creerCartes("APi ACo ATr 9Ca 5Co"));
        assertEquals(14, evaluateur.trouverValeurBrelan(compteur));
    }
    @Test
    public void testTrouverCarre() {
// Teste Carré de 9
        Map<Integer, Integer> compteur = evaluateur.compterCartes(creerCartes("9Pi 9Co 9Tr 9Ca ACo"));
        assertEquals(9, evaluateur.trouverValeurCarre(compteur));
    }
    @Test
    public void testTrouverDeuxPaires() {
// Teste Deux Paires (Rois et Valets)
        Map<Integer, Integer> compteur = evaluateur.compterCartes(creerCartes("RPi RCo VTr VCa ACo"));
// La méthode doit renvoyer la paire la plus HAUTE
        assertEquals(13, evaluateur.trouverValeurDeuxPaires(compteur));
    }
    @Test
    public void testTrouverDeuxPaires_UneSeulePaire() {
// S'il n'y a qu'une paire, doit renvoyer 0
        Map<Integer, Integer> compteur = evaluateur.compterCartes(creerCartes("RPi RCo 2Tr VCa ACo"));
        assertEquals(0, evaluateur.trouverValeurDeuxPaires(compteur));
    }
    @Test
    public void testEstSuite_Standard() {
// Teste une suite standard (Roi haute)
// (Note: estSuite attend une main triée par ordre décroissant)
    List<Carte> cartes = creerCartes("RCa DPi VCo 10Tr 9Ca");
    assertTrue(evaluateur.estSuite(cartes));
    }
    @Test
    public void testTrouverValeurDeuxiemePaire() {
        // Main avec Rois (13) et Valets (11)
        List<Carte> cartes = creerCartes("RPi RCo VTr VCa ACo");
        Map<Integer, Integer> compteur = evaluateur.compterCartes(cartes);

        // On sait que la paire haute est 13 (Rois)
        int paireHaute = 13;

        // La méthode doit trouver la paire de Valets (11)
        assertEquals(11, evaluateur.trouverValeurDeuxiemePaire(compteur, paireHaute));
    }
    @Test
    public void testDecrireMain_Carre() {
        MainJoueur main = new MainJoueur("9Pi 9Co 9Tr 9Ca ACo");
        assertEquals("carré de 9", evaluateur.decrireMain(main));
    }

    @Test
    public void testDecrireMain_Full() {
        MainJoueur main = new MainJoueur("RTr RCo RPi 5Ca 5Pi");
        assertEquals("full (brelan de Roi + paire de 5)", evaluateur.decrireMain(main));
    }

    @Test
    public void testDecrireMain_Couleur() {
        // Note: votre code affiche "couleur : " + la couleur. Vérifions ça.
        MainJoueur main = new MainJoueur("2Tr 5Tr 9Tr VTr ATr");
        // Attention : votre méthode trouverCouleurMain renvoie "Tr", "Pi", etc.
        assertEquals("couleur : Tr", evaluateur.decrireMain(main));
    }

    @Test
    public void testDecrireMain_DoublePaire() {
        MainJoueur main = new MainJoueur("RTr RCo VPi VCa ACo");
        assertEquals("double paire : paire de Roi et paire de Valet", evaluateur.decrireMain(main));
    }

    @Test
    public void testDecrireMain_HauteCarte() {
        MainJoueur main = new MainJoueur("ATr RCo DPi VCa 9Co");
        assertEquals("carte la plus élevée : As", evaluateur.decrireMain(main));
    }
}
