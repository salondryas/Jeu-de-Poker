import java.util.Map;

public class Carte {
    private int numero; // Valeur de la carte (2 à 14)
    private String couleur; // "Coeur", "Carreau", "Trèfle", "Pique"

    private static final Map<Character, Integer> lettreToValue = Map.of('V', 11,  //équivalence lettre-nombre
            'D', 12,
            'R', 13,
            'A', 14);

    public Carte(int numero, String couleur) {
        this.numero = numero;
        this.couleur = couleur;
    }

    public Carte(String card) {
        numero = translateIntoValue(card);
        couleur = card.substring(card.length() - 2); //renvoie les deux derniers caractères
    }

    private int translateIntoValue(String card) { //renvoie le numero de la carte et utilise le dictionnaire si nécessaire
        if (lettreToValue.containsKey(card.charAt(0))) //s'il possède une lettre (clé)
            return lettreToValue.get(card.charAt(0)); //alors renvoie la valeur associée

        if (!card.startsWith("10"))
            return card.charAt(0) - '0'; // si c'est un nombre

        return 10;
    }

    public int getNumero() {
        return numero;
    }

    public String getCouleur() {
        return couleur;
    }

    @Override
    public String toString() {
        return couleur + " " + numero;
    }

    public static String numeroToString(int numero) { // risque de violation du principe d'absraction des classes en
                                                      // POO
        return switch (numero) {
            case 11 -> "Valet"; // Valet
            case 12 -> "Dame"; // Dame
            case 13 -> "Roi"; // Roi
            case 14 -> "As"; // As
            default -> String.valueOf(numero);
        };
    }
}
