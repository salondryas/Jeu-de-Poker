public class Comparaison_v2 {
    private Combinaison_v2 combinaison_1;
    private Combinaison_v2 combinaison_2;
    private String game_status; // variable d'instance qui gère le cas où il y a winner ou celui d'égalité

    public Comparaison_v2(Combinaison_v2 combinaison_1, Combinaison_v2 combinaison_2) {
        this.combinaison_1 = combinaison_1;
        this.combinaison_2 = combinaison_2;
    }

    private String comparaison() {
        if (combinaison_1.getScore() > combinaison_2.getScore())
            return "combinaison_1";
        else if (combinaison_1.getScore() < combinaison_2.getScore())
            return "combinaison_2";
        // on a ce qui suit quand le score de la main 1 et celui de la main 2 sont égaux
        else if (combinaison_1.getValeurAcomparer() > combinaison_2.getValeurAcomparer())
            return "combinaison_1";
        else if (combinaison_1.getValeurAcomparer() < combinaison_2.getValeurAcomparer())
            return "combinaison_2";
        else if (combinaison_1.getValeurAcomparer() == combinaison_2.getValeurAcomparer())
            return casEgaliteParfaite();
        return ""; // aucune utilité pratique mais il est ici sueleement pour assurer que le return
                   // type soit String
    }

    private String casEgaliteParfaite() { // inclut gestion des kickers
        int length = combinaison_1.getHand().size();
        // on pourrait aussi evaluer avec le score de la main 2
        // (combinaison_2.getScore()) et cela reviendrait à la même chose
        // recompare au dela d'une égalité simple si et seulment si les deux mains à la
        // fois possèdent les combinaisons suivantes:
        // CARTE_HAUTE, PAIRE, DEUX_PAIRES, COULEUR
        if (combinaison_1.getScore() == 1 || combinaison_1.getScore() == 2 || combinaison_1.getScore() == 3
                || combinaison_1.getScore() == 6) {

            for (int position = combinaison_1.getOccuValeur(); position < length; position += combinaison_1
                    .getOccuValeur()) {
                combinaison_1.setValeurAcomparer(position);
                combinaison_2.setValeurAcomparer(position);

                if (combinaison_1.getValeurAcomparer() > combinaison_2.getValeurAcomparer()) {
                    if (combinaison_1.getOccuValeur() == 2) {
                        combinaison_1.setRaison("paire de "
                                + combinaison_1.getValueToDisplay(combinaison_1.getValeurAcomparer()) + " (kicker)");
                        return "combinaison_1";
                    }
                    combinaison_1.setRaison("carte la plus élevée : "
                            + combinaison_1.getValueToDisplay(combinaison_1.getValeurAcomparer()) + " (kicker)");
                    return "combinaison_1";
                } else if (combinaison_1.getValeurAcomparer() < combinaison_2.getValeurAcomparer()) {
                    if (combinaison_2.getOccuValeur() == 2) {
                        combinaison_2.setRaison("paire de "
                                + combinaison_2.getValueToDisplay(combinaison_2.getValeurAcomparer()) + " (kicker)");
                        return "combinaison_2";
                    }
                    combinaison_2.setRaison("carte la plus élevée : "
                            + combinaison_2.getValueToDisplay(combinaison_2.getValeurAcomparer()) + " (kicker)");
                    return "combinaison_2";
                }
            }
        }
        return "match nul";
    }

    @Override
    public String toString() {
        game_status = comparaison();
        if (game_status.equals("combinaison_1"))
            return "La main 1 a gagné avec " + combinaison_1.getRaison();
        else if (game_status.equals("combinaison_2"))
            return "La main 2 a gagné avec " + combinaison_2.getRaison();
        else
            return "Egalite";
    }

}
