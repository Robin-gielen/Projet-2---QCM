import java.util.Scanner; 
import java.lang.String; 

/**
 * Write a description of class qcm here.
 * 
 * @author (Julien Banken, Robin Gielen, Victor Baert) 
 * @version (a version number or a date)
 */
public class qcm
{
    public static void separation (){
        System.out.println ("<");
        for (int separation = 0; separation <=20; separation ++){
            System.out.println ("="); 
        } 
        System.out.println (">");
    }
    
    public static void main (String[] args) {    
       
        // [All] Variables a initialiser : 
        
        int type_cotation;
        int colonne; 
        int ligne; 
        int nombre_question = 10; 
        int resultats_entrainement = 0; 
        int resultats_test = 0; 
        boolean again = false;
    
        // [Victor] Afficher les regles : 
        
        System.out.println (" Le mode d'entrainement : + 1 par bonne réponse"); 
        System.out.println (" Le mode de test : + 1 par bonne réponse et - 1 par mauvaise réponse"); 
        
        do {
            
            // [Victor] Choix du type de cotation :
          
            System.out.println ("Veuillez entrer votre mode de cotation : ");
            System.out.println ("\t 1) Mode d'entrainement "); 
            System.out.println ("\t 2) Mode de test "); 
            
            Scanner type_de_cotation = new Scanner (System.in); 
            type_cotation = type_de_cotation.nextInt(); 
            
            while ( type_cotation!= 1 && type_cotation !=2){
                System.out.println ("Veuillez entrer une réponse valide SVP!");
                Scanner test = new Scanner (System.in); 
                type_cotation = test.nextInt(); 
            }
          
            // [Robin] Recuperer les informations depuis le fichier txt et les placer dans un tableau :
            
            int reponse_test[][] = {{0,1,0,1,0,0,0,1,0},{1,0,1,0,0,1,0,1,0},{1,0,0,0,1,0,1,0,1}} ; 
            String justification_test [] = {"test", "test" , "test"}; 
            
            // [Robin] Afficher les questions et les reponses (Utiliser un PRNG !):
            
            System.out.println (" Question n°");
            System.out.println ("\t a)"); 
            System.out.println ("\t b)");
            System.out.println ("\t c)");
            
            // [Victor] Gerer les inputs de l'utilisateur :
            
            int reponse_utilisateur [][] = {{0,1,0,1,0,0,0,1,0},{1,0,1,0,0,1,0,1,0},{1,0,0,0,1,0,1,0,1}} ;
            
            // [Julien] Calculer et afficher les resultats en fonction du mode de cotation par question :
                        
            separation(); 
            
            System.out.println ("Vous avez terminé votre questionnaire en" + "secondes");
            System.out.println ("Nous allons procéder à la vérification de vos réponses ... ");
            
            for (int j = 0; j < nombre_question ; j ++){ // N° de la question
                System.out.println (" Question n°" + (j + 1)+ " :");
                System.out.println (" \t Vous avez répondu : " );   
                if (reponse_utilisateur[0][j] == 1){
                    System.out.print(" A "); 
                }
                else if (reponse_utilisateur[1][j] == 1){
                    System.out.print(" B "); 
                }
                else if (reponse_utilisateur[2][j] == 1){
                    System.out.print(" C "); 
                }
                System.out.println (" La bonne réponse était la/les réponse(s) :");
                if (reponse_test[0][j] == 1){
                    System.out.print(" A "); 
                }
                else if (reponse_test[1][j] == 1){
                    System.out.print(" B "); 
                }
                else if (reponse_test[2][j] == 1){
                    System.out.print(" C "); 
                }
                    
                for (int i = 0; i < 3; i ++){ // Proposition (a,b ou c)
                    // L'utilisateur a cocher la bonne réponse 
                    if ((reponse_test[i][j] == 1 && reponse_utilisateur[i][j] == 1) ){
                        System.out.println ( "\n Vous avez coché une bonne réponse !" ); 
                        resultats_entrainement = resultats_entrainement + 1; 
                        resultats_test = resultats_test + 1; 
                    }
                    // L'utilisateur a cocher une mauvaise réponse
                    else if (reponse_test[i][j] == 0 && reponse_utilisateur[i][j] == 1 ){
                        System.out.println (" \n Vous avez coché une mauvaise réponse !" );
                        resultats_test = resultats_test - 1; 
                    }
                    // l'untilisateur n'a pas coche pas la réponse
                    else if (reponse_test[i][j] == 1 && reponse_utilisateur[i][j] == 0){
                        System.out.println (" \n Vous n'avez pas trouvé la réponse");
                    }
                 
                    else if (i == 2 ){
                        // Si la justification existe :
                        if (justification_test[j] != null) {
                            System.out.println (" Justification : " + justification_test[j]);
                        }
                        else {
                            System.out.println (" Pas de justification disponible pour cette question ... ");
                        }
                    }
                }
            }
            // [Julien] Resumer les points obtenus en fonction du type de cotation:
                   
            separation(); 
            
            if (type_cotation == 1){ // Mode entrainement 
                System.out.println ("Vous avez obtenu :" + resultats_entrainement + " point(s)"); 
                if (resultats_entrainement > 10) {
                    System.out.println ("Félicitation, vous avez obtenu une note supérieur à la moyenne ! "); 
                    System.out.println ("Vous auriez obtenu " + resultats_test + " point(s) en mode test"); 
                }
                else if (resultats_entrainement < 10) {
                    System.out.println ("Attention, vous avez râté ce QCM ! Je vous conseil de vous entrainer avant de passer en mode test..."); 
                }
            }
            
            else if (type_cotation == 2){ // Mode test
                System.out.println ("Bravo ! Vous avez obtenu :" + resultats_test + " point(s)"); 
                System.out.println ("Vous auriez obtenu" + resultats_entrainement + " point(s) en mode entrainement"); 
                if (resultats_test > 10) {
                    System.out.println ("Félicitation, vous avez obtenu une note supérieur à la moyenne ! "); 
                    System.out.println ("Vous auriez obtenu " + resultats_entrainement + " point(s) en mode entrainement"); 
                }
                else if (resultats_test < 10) {
                    System.out.println ("Attention, vous avez râté ce QCM ! Je vous conseil de vous entrainer en mode entrainement..."); 
                }
            }
            
            // [All] Faire une moyenne des résultats obtenus ? 
            
            int moyenne_test [] = {}; 
            System.out.println ("Vous avez une moyenne de ...");
            
            // [All] Reset les tableaux : 
            
            for (int a = 0; a <nombre_question; a++){
                for (int b = 0; b < 3; b++) {
                    reponse_test [a][b] = 0; 
                    reponse_utilisateur [a][b] = 0; 
                }
            }
            
            // [Julien ?] Relancer le questionnaire
            
            System.out.println ("Voulez-vous recommencer ? (true/false)");
            Scanner sc = new Scanner (System.in); 
            again = sc.nextBoolean();   

        }while( again ==  true);   
    }
}
