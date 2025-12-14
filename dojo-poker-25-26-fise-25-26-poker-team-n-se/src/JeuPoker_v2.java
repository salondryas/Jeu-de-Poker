public class JeuPoker_v2 {
    public static void main(String[] args) {
        MainJoueur Main_1 = new MainJoueur();
        MainJoueur Main_2 = new MainJoueur();

        Main_1.triDecroissantSpecifique();
        Main_2.triDecroissantSpecifique();

        Combinaison_v2 combinaison_1 = new Combinaison_v2(Main_1);
        Combinaison_v2 combinaison_2 = new Combinaison_v2(Main_2);

        Comparaison_v2 Evaluateur = new Comparaison_v2(combinaison_1, combinaison_2);

        System.out.println(Evaluateur);
    }
}
