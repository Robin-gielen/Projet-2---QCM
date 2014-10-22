import java.util.Scanner; 
import java.lang.String; 
import java.io.*;

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
        int nombreQuestion; // Mis a jour lors de la lecture du fichier txt
        int resultatsEntrainement;
        int resultatsTest;
        int resultatsIntermediaires;
        boolean again = false;
        String indexReponses = "abcdefghij";
                int countQuestion; // Variable de test pour le compte des questions
                
        int nombreReponsesMax; // Stock le nombre de reponses max pour une question
    
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
            String currentLine;
            BufferedReader br;
            
            try
            {
                br = new BufferedReader(new FileReader("qcm.txt"));
                br.mark(1000000);
                currentLine = br.readLine();
                int countA = 0;
                while (currentLine != null)
                {
                    countQuestion++;
                    if (currentLine.charAt(0) == 'Q')
                    {
                        if (countA > nombreReponsesMax)
                        {
                            nombreReponsesMax = countA; 
                            System.out.println(nombreReponsesMax); // Test afin de verifier la concordance
                        }
                        nombreQuestion++;
                        System.out.println(countQuestion); // Test afin de verifier la concordance
                        countA =0;
                    }
                    else if (currentLine.charAt(0) == 'A')
                    {
                        countA++;
                    }
                    currentLine = br.readLine();
                }
            } catch (IOException e) { System.exit(0);}
            String questionnaire[][] = new String[nombreQuestion][2]; // Va stocker les questions et les reponses correctes pour chaque question
            String reponsesEtudiant[] = new String[nombreQuestion]; // Va stocker les reponses de l etudiant
            int nombreReponses[] = new int[nombreQuestion]; // Va stocker le nombre de reponses pour chaque question
            String reponsesQuestions[][] = new String[nombreQuestion][nombreReponsesMax]; // Va stocker toutes les reponses pour chaque question
            
            try
            {
                br.reset();
                currentLine = br.readLine();
                int countQ = 0; // Compte a quelle question nous sommes actuellement
                int countA = 0; // Compte a quelle reponse nous sommes dans la question en cours
                while (currentLine != null)
                {
                    if (currentLine.charAt(0) == 'Q')
                    {
                        if (countQ !=0)
                        {
                            nombreReponses[countQ-1] = countA;
                            countA = 0;
                        }
                        currentLine = currentLine.substring(1);
                        questionnaire[countQ][1] = currentLine;
                        countQ++;
                    }
                    else if (currentLine.charAt(0) == 'A')
                    {
                        reponsesQuestions[countQ-1][countA] = currentLine.substring(2 , currentLine.indexOf('|', 2) - 1);
                        if (currentLine.charAt(currentLine.indexOf('|', 2) + 1) == 'V')
                        {
                            questionnaire[countQ-1][2] += indexReponses.charAt(countA);
                        }
                        countA++;
                        System.out.println(questionnaire[countQ-1][2]);
                    }
                    currentLine = br.readLine();
                }
            } catch (IOException e) { System.exit(0);}
            
            try
            {
                br.reset();
                currentLine = br.readLine();
                int countQ = 0; // Compte a quelle question nous sommes actuellement
                int countA = 0; // Compte a quelle reponse nous sommes dans la question en cours
                while (currentLine != null)
                {
                    
                
                    currentLine = br.readLine();
                }
            } catch (IOException e) { System.exit(0);}
            
            /*
             * A ce stade-ci, nous avons deux tableaux representatifs de la situation. Le premier contient les questions ainsi que les references des bonnes reponses et le deuxiemes contient toutes les reponses possible. 
             * Ces deux tableaux vont nous servir non seulement afin d'afficher les questions et les reponses a l utilisateur afin qu il puisse y repondre, mais ils vont egalement nous servir a la correction des reponses donnes par l utilisateur.
             */
            
            // int reponse_test[] = new int[8]; 
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
            
            for (int j = 0; j < nombreQuestion ; j ++){ // N° de la question
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
