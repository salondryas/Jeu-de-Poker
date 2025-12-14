import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntreeStandardTest {

    @Test
    public void testMainValide() {
        EntreeStandard.resetCartesUtilisees(); // réinitialise avant les tests
        String input = "2Tr 3Ca 4Co 5Pi 6Tr";
        assertTrue(EntreeStandard.isInputValid(input));
    }

    @Test
    public void testTropDeCartes() {
        String input = "2Tr 3Ca 4Co 5Pi 6Tr 7Ca";
        assertFalse(EntreeStandard.isInputValid(input));
    }

    @Test
    public void testPasAssezDeCartes() {
        String input = "2Tr 3Ca 4Co";
        assertFalse(EntreeStandard.isInputValid(input));
    }

    @Test
    public void testCarteDejaUtilisee() {
        String input1 = "2Tr 3Ca 4Co 5Pi 6Tr";
        assertTrue(EntreeStandard.isInputValid(input1));

        // même carte réutilisée
        String input2 = "2Tr 7Ca 8Co 9Pi 10Tr";
        assertFalse(EntreeStandard.isInputValid(input2));
    }

    @Test
    public void testValeurInvalide() {
        String input = "11Co 9Ca 8Tr VD AsPi"; // 11Co n’existe pas
        assertFalse(EntreeStandard.isInputValid(input));
    }

    @Test
    public void testCouleurInvalide() {
        String input = "2Co 3Ca 4Co 5Pi 6Tr"; // "2C" n’est pas une couleur valide
        assertFalse(EntreeStandard.isInputValid(input));
    }

    @Test
    public void testFormatInvalide() {
        String input = "TTr 3Ca 4Co 5Pi 6Tr"; // "TTr" n’a pas de valeur valide
        assertFalse(EntreeStandard.isInputValid(input));
    }

    @Test
    public void testAvecFigures() {
        String input = "VCo DPi RCa ATr 10Ca"; // Valet, Dame, Roi, As, Dix
        assertTrue(EntreeStandard.isInputValid(input));
    }
}
