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

    int mid=7;

   /**
    * @param j le jeu
    * @return toujours le 1er choix
    */
   public int choix(Jeu j) {
       //getbonzes: savoir où sont les bonzes et à quel avancement
       //getnbChoix: savoir ton nb de choix
       //getlesChoix: les colonnes sélectionnables
       //lesChoix: 6 par 2
       //pour chaque colonne, vérifier siun bonze dans chaque
       int bonzesPosés = 3 - j.getBonzesRestants();
       int choixpossibles[][] = j.getLesChoix();
       int positionBonzes[][] = j.getBonzes();


       //si 1 seul bonze posé, correspond, jouer celui-là
       //si 1 bonze posé et un des choix de paire correspond, jouer
       if(bonzesPosés == 1){
           for(int i=0; i<j.getNbChoix(); i++){
               if(positionBonzes[0][0] == choixpossibles[i][1] && positionBonzes[0][0] == choixpossibles[i][0]){
                   return i;
               }
           }
           for(int i=0; i<j.getNbChoix(); i++){
               if((positionBonzes[0][0] == choixpossibles[i][0] && choixpossibles[i][1] == 0) || (positionBonzes[0][0] == choixpossibles[i][1] && choixpossibles[i][0] == 0)){
                   return i;
               }
           }
           for(int i=0; i<j.getNbChoix(); i++){
               if(positionBonzes[0][0] == choixpossibles[i][0] || positionBonzes[0][0] == choixpossibles[i][1]){
                   return i;
               }
           }
       }
       //si 2 bonzes posés correspondent à une paire, la jouer
       if(bonzesPosés == 2){
           for(int i=0; i<j.getNbChoix(); i++){
               if(positionBonzes[0][0] == choixpossibles[i][0] && positionBonzes[1][0] == choixpossibles[i][1] || positionBonzes[1][0] == choixpossibles[i][0] && positionBonzes[0][0] == choixpossibles[i][1]){
                   return i;
               }
           }
       }


       //si une paire, la jouer
       for(int i=0; i< j.getNbChoix(); i++){
           if(choixpossibles[i][0] == choixpossibles[i][1]){
               return i;
           }
       }





           return 0;
   }

   /**
    * @param j le jeu
    * @return toujours vrai (pour s'arrêter)
    */
   public boolean stop(Jeu j) {
       //on continue s'il reste des bonzes à poser
       if (j.getBonzesRestants() != 0) {
           return false;
       } else {
           for (int i = 0; i < 11; i++) {
               if (j.getBloque()[i]) {
                   return true;
               }
           }
           //à changer
           if(j.getNbCoup()<4){
               return false;
           }
           return true;
       }
   }

   /**
    * @return vos noms
    */
   public String getName(){
       return "CLARICE GOULET";
   }
}
