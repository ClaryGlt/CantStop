package strategies;

import cantstop.Jeu;
        
/**
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
    int nbCasesRestantes[] = new int[4];

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

       //On associe à chaque colonne un score en fonction de sa rentabilité
        int[][] colonnes = {{7,11},{12,10},{2,9},{6,8},{8,7},{9,6},{5,5},{10,4},{11,3},{4,2},{3,1}};
        //Tableau permettant de repérer quelle paire de choix obtient le plus haut score de rentabilité
        int[] max = new int[j.getNbChoix()];
        //scores de rentabilité de chaque choix dans une paire :
        int max1 = 0;
        int max2 = 0;
        //indice du choix le plus rentable
        int maximum = 0;
        //indice du bonze le plus avancé
        int BestBonze = 0;


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
               if((j.getBonzes()[0][0] == choixpossibles[i][0] && choixpossibles[i][1] == 0) || (j.getBonzes()[0][0] == choixpossibles[i][1] && choixpossibles[i][0] == 0)){
                   return i;
               }
           }
           for(int i=0; i<j.getNbChoix(); i++){
               //l'un des choix correspond
               if(j.getBonzes()[0][0] == choixpossibles[i][0] || j.getBonzes()[0][0] == choixpossibles[i][1]){
                   return i;
               }
           }
       }
       //si 2 bonzes posés
       if(bonzesPoses == 2){
           //paire correspond à celle posée
           for(int i=0; i<j.getNbChoix(); i++) {
               if ((j.getBonzes()[0][0] == choixpossibles[i][0] && j.getBonzes()[1][0] == choixpossibles[i][1]) || (j.getBonzes()[1][0] == choixpossibles[i][0] && j.getBonzes()[0][0] == choixpossibles[i][1])) {
                   return i;
               }
           }
           //un choix correspond à un bonze posé
           for(int i=0; i<j.getNbChoix(); i++) {
               if (j.getBonzes()[0][0] == choixpossibles[i][0] || j.getBonzes()[1][0] == choixpossibles[i][1] || j.getBonzes()[1][0] == choixpossibles[i][0] || j.getBonzes()[0][0] == choixpossibles[i][1]) {
                   return i;
               }
           }
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
                   return true;
               }
           }
           //si progrès = 1/3 d'une colonne, stop
           for(int i=0; i<3; i++){
               avancee[i] = j.getBonzes()[i][1] - j.avancementJoueurEnCours()[j.getBonzes()[i][0]-2];
               if(avancee[i] == (j.getMaximum()[(j.getBonzes()[i][0])-2])/3){
                   return true;
               }
           }
           //sinon, on continue
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
