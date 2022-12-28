package strategies;

import cantstop.Jeu;
        
/**
 * Ma stratégie:
 * Poser les bonzes le plus tard possible, choisir où poser ses bonzes en fonction de la rentabilité de la colonne et des pions déjà validés
 * Le stop s'effectue lorsque l'un des bonzes remplit plus d'un tiers de sa colonne, ou lorsqu'une colonne est bloquée.
 *
 * Votre Stratégie (copie de la Strat0 pour l'instant)
 *
 * Expliquez votre stratégie en une 20aine de lignes maximum.
 *
 * RENDU: Ce fichier, correctement nommé et rempli.
 * Votre Stratégie aura un numéro (pour être similaire à Strat0 qui sera votre position dans l'alphabet de la promo + 14. 
 * (attention calcul compliqué) Le premier aura donc pour numéro 15 et le dernier 334
 * Pour "préparer" votre stratégie, sur le fichier StratX.java, vous cliquez sur Bouton Droit, Refactor, Rename et vous 
 * nommez bien votre stratégie genre Strat245.java (pour le 231e).
 *
 * @author VOTRE NOM
 */

public class Strat143 implements Strategie {

    //le nombre de cases restantes avant de bloquer la colonne, pour chaque bonze placé :
    static int[] nbCasesRestantes = new int[3];

    //On associe à chaque colonne un score en fonction de sa rentabilité (probabilité d'obtention podéré par le nombre de cases à remplir)
    static int[][] colonnes = {{7,328},{12,230},{2,230},{6,172},{8,172},{9,73},{10,73},{5,73},{4,73},{11,69},{3,69}, {0,0}};

    //le nb de coups recommandés pour la combinaison de bonzes actuelle :
    int b = 0;

    //le tableau donnant le nb de coups recommandés pour quelques combinaisons de 3 bonzes : {bonze1, bonze2, bonze3, nbCoups}
    static int[][] nbCoupsStop = {
            {6,7,8,12},
            {4,6,8,10},
            {5,7,8,10},
            {6,7,9,10},
            {6,8,10,10},
            {4,7,8,9},
            {5,6,8,9},
            {6,7,10,9},
            {6,8,9,9},
            {2,7,8,8},
            {3,7,8,8},
            {4,6,7,8},
            {4,7,9,8},
            {5,6,7,8},
            {5,7,10,8},
            {6,7,11,8},
            {6,7,12,8},
            {7,8,9,8},
            {7,8,10,8},
            {4,6,10,7},
            {4,7,10,7},
            {4,8,10,7},
            {5,6,9,7},
            {5,8,9,7},
            {6,8,12,7},
            {2,7,6,6},
            {3,6,7,6},
            {3,6,8,6},
            {4,5,7,6},
            {4,5,8,6},
            {4,6,9,6},
            {4,8,9,6},
            {5,6,10,6},
            {5,7,9,6},
            {5,8,10,6},
            {6,8,11,6},
            {6,9,10,6},
            {7,8,11,6},
            {7,8,12,6},
            {7,9,10,6},
    };

   /**
    * @param j le jeu
    * @return toujours le 1er choix
    */
   public int choix(Jeu j) {
       //le nombre de bonzes posés sur le plateau
       int bonzesPoses = 3 - j.getBonzesRestants();
       //les paires de choix possibles : [1er choix] [2e choix si existant, 0 sinon]
       int choixpossibles[][] = j.getLesChoix();
       // position des bonzes : [colonne] [avancement]

        //Tableau permettant de repérer quelle paire de choix obtient le plus haut score de rentabilité
        int[] max = new int[j.getNbChoix()];
        //scores de rentabilité de chaque choix dans une paire :
        int max1 = 0;
        int max2 = 0;
        //indice du choix le plus rentable
        int maximum = 0;
        //indice du bonze le plus avancé
        int BestBonze = 0;


        //s'il reste des bonzes à poser, faire d'abord en fonction des colonnes déjà faites

        //si un seul bonze est posé et qu'au moins 1 choix lui correspond
       if(bonzesPoses == 1){
           //si c'est une paire double
           for(int i=0; i<j.getNbChoix(); i++){
               if(j.getBonzes()[0][0] == choixpossibles[i][1] && j.getBonzes()[0][0] == choixpossibles[i][0]){
                   return i;
               }
           }
           //si l'un des 2 choix correspond, et que l'autre est nul (=on ne pose pas de nouveaux bonzes)
           for(int i=0; i<j.getNbChoix(); i++){
               if((j.getBonzes()[0][0] == choixpossibles[i][0] && choixpossibles[i][1] == 0)){
                   return i;
               }
           }
           //si un seul des choix correspond, on va poser un nouveau bonze
           for(int i=0; i<j.getNbChoix(); i++) {
               //on réduit les choix à ceux contenant le bonze déjà posé
               if (j.getBonzes()[0][0] == choixpossibles[i][0] || j.getBonzes()[0][0] == choixpossibles[i][1]) {
                   //si plusieurs choix contiennent le bonze, on choisit en fonction du score de rentabilité
                   for (int x = 0; x < 11; x++) {
                       //score de rentabilité de la première colonne
                       if (choixpossibles[i][0] == colonnes[x][0]) {
                           max1 = colonnes[x][1];
                       }
                       //score de retabilité de la deuxième colonne
                       if (choixpossibles[i][1] == colonnes[x][0]) {
                           max2 = colonnes[x][1];
                       }
                   }
                   //score de rentabilité de la paire
                   max[i] = max1 + max2;
               }
           }
           //on sélectionne le choix avec le plus haut score de rentabilité
           for (int i = 0; i < j.getNbChoix() - 1; i++) {
               if (max[i] < max[i + 1]) {
                   maximum = i+1;
               } else {
                   maximum = i;
               }
           }
               return maximum;
           }

       //si 2 bonzes posés
       if(bonzesPoses == 2){
           //paire correspond à celle posée
           for(int i=0; i<j.getNbChoix(); i++) {
               if ((j.getBonzes()[0][0] == choixpossibles[i][0] && j.getBonzes()[1][0] == choixpossibles[i][1]) || (j.getBonzes()[1][0] == choixpossibles[i][0] && j.getBonzes()[0][0] == choixpossibles[i][1])) {
                   return i;
               }
           }
           //si l'un des 2 choix correspond, et que l'autre est nul (=on ne pose pas de nouveaux bonzes)
           for(int i=0; i<j.getNbChoix(); i++){
               if((j.getBonzes()[0][0] == choixpossibles[i][0] && choixpossibles[i][1] == 0) || (j.getBonzes()[1][0] == choixpossibles[i][0] && choixpossibles[i][1] == 0)){
                   return i;
               }
           }
           //un choix sur les deux correspond à un bonze posé
           for(int i=0; i<j.getNbChoix(); i++) {
               if (j.getBonzes()[0][0] == choixpossibles[i][0] || j.getBonzes()[1][0] == choixpossibles[i][1] || j.getBonzes()[1][0] == choixpossibles[i][0] || j.getBonzes()[0][0] == choixpossibles[i][1]) {
                   //si plusieurs choix contiennent le bonze, on choisit en fonction du score de rentabilité
                   for (int x = 0; x < 11; x++) {
                       //score de rentabilité du premier choix de la paire
                       if (choixpossibles[i][0] == colonnes[x][0]) {
                           max1 = colonnes[x][1];
                       }
                       //score de rentabilité du 2e choix de la paire
                       if (choixpossibles[i][1] == colonnes[x][0]) {
                           max2 = colonnes[x][1];
                       }
                   }
                   //score de rentabilité de la paire
                   max[i] = max1 + max2;
               }
           }
           //on sélectionne le choix avec le plus haut score de rentabilité
           for (int i = 0; i < j.getNbChoix() - 1; i++) {
               if (max[i] < max[i + 1]) {
                   maximum = i+1;
               } else {
                   maximum = i;
               }
           }

           for(int i=0 ; i < nbCoupsStop.length ; i++){
               if(choixpossibles[maximum][0] == j.getBonzes()[0][0] || choixpossibles[maximum][0] == j.getBonzes()[1][0]){
                   //le 3e bonze est choixpossibles[maximum][1]
                   if(((nbCoupsStop[i][0] == j.getBonzes()[0][0]) || (nbCoupsStop[i][0] == j.getBonzes()[1][0]) || (nbCoupsStop[i][0] == choixpossibles[maximum][1])) && ((nbCoupsStop[i][1] == j.getBonzes()[0][0]) || (nbCoupsStop[i][1] == j.getBonzes()[1][0]) || (nbCoupsStop[i][1] == choixpossibles[maximum][1])) && ((nbCoupsStop[i][2] == j.getBonzes()[0][0]) || (nbCoupsStop[i][2] == j.getBonzes()[1][0]) || (nbCoupsStop[i][2] == choixpossibles[maximum][1]))){
                       b = nbCoupsStop[i][3];
                   }
               } else {
                   if(((nbCoupsStop[i][0] == j.getBonzes()[0][0]) || (nbCoupsStop[i][0] == j.getBonzes()[1][0]) || (nbCoupsStop[i][0] == choixpossibles[maximum][0])) && ((nbCoupsStop[i][1] == j.getBonzes()[0][0]) || (nbCoupsStop[i][1] == j.getBonzes()[1][0]) || (nbCoupsStop[i][1] == choixpossibles[maximum][0])) && ((nbCoupsStop[i][2] == j.getBonzes()[0][0]) || (nbCoupsStop[i][2] == j.getBonzes()[1][0]) || (nbCoupsStop[i][2] == choixpossibles[maximum][0]))){
                       b = nbCoupsStop[i][3];
                   }
               }
           }

           return maximum;
       }

       //si aucun bonze posé ou tous les bonzes posés

       //si une paire, la jouer
       for(int i=0; i< j.getNbChoix(); i++){
           if(choixpossibles[i][0] == choixpossibles[i][1]){
               return i;
           }
       }

       //si pas une paire, poser selon les préférences de colonne

       //si 0 bonzes posés:
       if (bonzesPoses == 0) {
           for(int i=0; i< j.getNbChoix(); i++){
               for(int x=0; x<11; x++){
                   if(choixpossibles[i][0] == colonnes[x][0]){
                       max1 = colonnes[x][1];
                   }
                   if(choixpossibles[i][1] == colonnes[x][0]){
                       max2 = colonnes[x][1];
                   }
               }
               max[i] = max1 + max2;
           }
           for (int i = 0; i < j.getNbChoix() - 1; i++) {
               if (max[i] < max[i + 1]) {
                   maximum = i+1;
               } else {
                   maximum = i;
               }
           }
           return maximum;
       }

       //si 3 bonzes posés

       //On cherche le nb de cases restantes avant de bloquer la colonne, pour chaque bonze :
       if(bonzesPoses == 3){
           for(int i=0; i<3; i++){
               nbCasesRestantes[i] = j.getMaximum()[(j.getBonzes()[i][0])-2] - j.getBonzes()[i][1];
           }
           //On trouve le bonze avec le moins de cases restantes
           for(int i=0; i<2; i++){
               if(nbCasesRestantes[i] < nbCasesRestantes[i+1]){
                   BestBonze = i;
               }
               else {
                   BestBonze = i+1;
               }
           }
           //On prend le choix permettant de faire avancer le meilleur bonze :
           for (int i=0; i< j.getNbChoix(); i++){
               if(j.getBonzes()[BestBonze][0] == choixpossibles[i][0] || j.getBonzes()[BestBonze][0] == choixpossibles[i][1]){
                   return i;
               }
           }
       }
       return 0;
   }

   /**
    * @param j le jeu
    * @return toujours vrai (pour s'arrêter)
    */
   public boolean stop(Jeu j) {

       //exprime le nombre de cases parcourues depuis le début du tour par chaque bonze
       int avancee[] = new int[3];

       //on continue s'il reste des bonzes à poser
       if (j.getBonzesRestants() != 0) {
           return false;
       }
       else {
           //si une colonne est bloquée, on s'arrête
           for (int i = 0; i < 11; i++) {
               if (j.getBloque()[i]) {
                   b = 0;
                   return true;
               }
           }

           if(b != 0){
               if(b == j.getNbCoup()){
                   b = 0;
                   return true;
               }
               return false;
           } else {
               //si progrès = 1/3 d'une colonne, stop
               for(int i=0; i<3; i++){
                   avancee[i] = j.getBonzes()[i][1] - j.avancementJoueurEnCours()[j.getBonzes()[i][0]-2];
                   if(avancee[i] > (j.getMaximum()[(j.getBonzes()[i][0])-2])/3){
                       b = 0;
                       return true;
                   }
               }
           }

           return false;
       }
   }

   /**
    * @return vos noms
    */
   public String getName(){
       return "CLARICE GOULET";
   }
}
