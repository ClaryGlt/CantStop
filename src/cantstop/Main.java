package cantstop;

/**
 * Lancement d'un tournoi ou d'une partie
 * @author afleury 
 */
public class Main {
    public static void main(String[] args) {
        // Mettez dans ce tableau les strats à tester
        // Vous pouvez ajouter la vôtre avec votre numéro de stratégie (cf StratX.java pour voir comment le calculer)
        int[] stratToTest = {0,1};
        // On instancie le jeu
        Jeu j = new Jeu();
        // On lance un tournoi avec 10 000 exécutions à chaque fois (entre chaque couple d'IA).
        //j.runIA(stratToTest,10000);
        j.run();
    }
}
