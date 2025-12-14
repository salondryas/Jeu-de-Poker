public enum TypeMain {
    CARTE_HAUTE(1),
    PAIRE(2),
    DEUX_PAIRES(3),
    BRELAN(4),
    SUITE(5),
    COULEUR(6),
    FULL(7),
    CARRE(8),
    QUINTE_FLUSH(9),
    QUINTE_FLUSH_ROYALE(10);

    private final int rang;

    TypeMain(int rang) {
        this.rang = rang;
    }

    public int getRang() {
        return rang;
    }
}
