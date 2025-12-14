import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComparaisonTest {

    private Comparaison comparateur;

    @BeforeEach
    public void setUp() {
        comparateur = new Comparaison();
        // Réinitialiser les cartes utilisées avant chaque test
        EntreeStandard.resetCartesUtilisees();
    }

    // Méthode simple pour créer une main avec notre nouveau constructeur
    private MainJoueur creerMain(String mainStr) {
        return new MainJoueur(mainStr);
    }

    @Test
    public void testHauteCarte_AsBatRoi() {
        MainJoueur main1 = creerMain("2Tr 6Ca 7Ca 8Tr APi");
        MainJoueur main2 = creerMain("3Tr 5Ca 9Ca DCo RCo");
        assertEquals(1, comparateur.comparerMains(main1, main2));
    }

    @Test
    public void testPaire_PaireBatHauteCarte() {
        MainJoueur main1 = creerMain("2Tr 6Ca 7Ca 8Tr APi");
        MainJoueur main2 = creerMain("3Tr 5Ca 5Co DCo RCo");
        assertEquals(2, comparateur.comparerMains(main1, main2));
    }

    @Test
    public void testPaire_KickerGagne() {
        MainJoueur main1 = creerMain("5Tr 5Co ATr RCa DCo"); // Paire de 5, Kicker As
        MainJoueur main2 = creerMain("5Pi 5Ca RTr DPi VCo"); // Paire de 5, Kicker Roi
        assertEquals(1, comparateur.comparerMains(main1, main2));
    }

    @Test
    public void testFull_FullBatBrelan() {
        MainJoueur main1 = creerMain("5Tr 5Co 5Pi RCa RCo"); // Full (5 par les Rois)
        MainJoueur main2 = creerMain("ATr ACo APi RPi DPi"); // Brelan d'As
        assertEquals(1, comparateur.comparerMains(main1, main2));
    }

    @Test
    public void testCarre_CarreBatFull() {
        MainJoueur main1 = creerMain("5Tr 5Co 5Pi 5Ca RCo"); // Carré de 5
        MainJoueur main2 = creerMain("ATr ACo APi RPi RTr"); // Full aux As par les Rois
        assertEquals(1, comparateur.comparerMains(main1, main2));
    }

    @Test
    public void testDeuxPaires_bat_Paire() {
        // "Deux Paires" (rang 3) bat "Paire" (rang 2)
        // même si la Paire est plus élevée (As).
        MainJoueur main1 = creerMain("5Tr 5Co 2Pi 2Ca ACo"); // Deux Paires (5 et 2)
        MainJoueur main2 = creerMain("ATr ACo RPi DCa 3Co"); // Paire d'As
        assertEquals(1, comparateur.comparerMains(main1, main2));
    }

    @Test
    public void testBrelan_bat_DeuxPaires() {
        // "Brelan" (rang 4) bat "Deux Paires" (rang 3)
        MainJoueur main1 = creerMain("2Tr 2Co 2Pi APi RCo"); // Brelan de 2
        MainJoueur main2 = creerMain("ATr ACo RPi RCa DCo"); // Deux Paires (As et Rois)
        assertEquals(1, comparateur.comparerMains(main1, main2));
    }

    @Test
    public void testSuite_bat_Brelan() {
        // "Suite" (rang 5) bat "Brelan" (rang 4)
        MainJoueur main1 = creerMain("2Tr 3Co 4Pi 5Ca 6Co"); // Suite (haute 6)
        MainJoueur main2 = creerMain("ATr ACo APi RPi DCo"); // Brelan d'As
        assertEquals(1, comparateur.comparerMains(main1, main2));
    }

    @Test
    public void testCouleur_bat_Suite() {
        // "Couleur" (rang 6) bat "Suite" (rang 5)
        MainJoueur main1 = creerMain("2Tr 5Tr 8Tr RTr ATr"); // Couleur (Trèfle)
        MainJoueur main2 = creerMain("9Co 10Pi VCa DTr RCo"); // Suite (haute Roi)
        assertEquals(1, comparateur.comparerMains(main1, main2));
    }

}