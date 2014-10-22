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
    public static void main (String[] args) {    

        // [All] Variables a initialiser : 
        
        int type_cotation;
        int colonne; 
        int ligne; 
<<<<<<< HEAD
        int nombreQuestion; // Mis a jour lors de la lecture du fichier txt
        int resultatsEntrainement;
        int resultatsTest;
        int resultatsIntermediaires;
        boolean again = false;
        String indexReponses = "abcdefghij";
                int countQuestion; // Variable de test pour le compte des questions
                
        int nombreReponsesMax; // Stock le nombre de reponses max pour une question
    
        // [Victor] Afficher les regles : 
=======
        int nombre_question = 10; 
>>>>>>> origin/master
        
        // Les reponses : 
        String reponses_vraies;
        String reponses_fausses;
        
        // Les resultats : 
        int resultats_facile = 0;
        double resultats_intermédiaire = 0;
        int resultats_test = 0;
        
        // Recommencer ?
        boolean again = false;

        // [Victor] Afficher les regles : 

        System.out.println ("/n Les regles :");
        System.out.println (" Chaque question s'affichera séparement avec toutes ses réponses et vous devrez alors entrer UNE réponse possible.");
        System.out.println (" Vous aurez alors la possibilité de rentrer d'autres réponses possibles.");
        System.out.println (" Une question, qui demande deux bonnes réponses en entrée et que vous ne répondez que par une seule bonne réponse, sera considerée comme fausse");
        System.out.println (" Il vous sera présenté 4 modes de correction différents."); 
        System.out.println (" \t 1) Le mode facile : + 1 par bonne réponse et 0 par mauvaise réponse"); 
        System.out.println (" \t 2) Le mode intermédiaire : + 1 par bonne réponse et - 0,5 par mauvaise réponse"); 
        System.out.println (" \t 3) Le mode de test : + 1 par bonne réponse et - 1 par mauvaise réponse"); 
        System.out.println (" \t 4) le mode d'entrainement qui affichera vos points pour chacun des modes de correction. "); 

        do {

            // [Victor] Choix du type de cotation :

            System.out.println ("Veuillez entrer votre mode de cotation : ");
            System.out.println ("\t 1) Le mode facile"); 
            System.out.println ("\t 2) Le mode intermediaire"); 
            System.out.println ("\t 3) Le mode test"); 
            System.out.println ("\t 4) Le mode entrainement"); 

            Scanner type_de_cotation = new Scanner (System.in); 
            type_cotation = type_de_cotation.nextInt(); 

            while ( type_cotation!= 1 && type_cotation!=2 && type_cotation!=3 && type_cotation!=4){
                System.out.println ("Veuillez entrer une réponse valide comprise entre 1 et 4: ");
                Scanner test = new Scanner (System.in); 
                type_cotation = test.nextInt(); 
            }

            // [Robin] Recuperer les informations depuis le fichier txt et les placer dans un tableau :
<<<<<<< HEAD
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
=======

            int reponse_test[] = {"abc", "bcd" , "def"}; 
>>>>>>> origin/master
            String justification_test [] = {"test", "test" , "test"}; 

            // [Robin] Afficher les questions et les reponses (Utiliser un PRNG !):

            System.out.println (" Question n°");
            System.out.println ("\t a)"); 
            System.out.println ("\t b)");
            System.out.println ("\t c)");
            System.out.println ("\t d)");
            System.out.println ("\t e)");
            System.out.println ("\t f)");

            // [Victor] Gerer les inputs de l'utilisateur :

            int reponse_utilisateur [] = {"cde", "abc", "bc"} ;

            // [Julien] Calculer et afficher les resultats en fonction du mode de cotation par question :
            
            separation(); 
            
            System.out.println ("Vous avez terminé votre questionnaire en" + "secondes");
            System.out.println ("Nous allons procéder à la vérification de vos réponses ... ");
<<<<<<< HEAD
            
            for (int j = 0; j < nombreQuestion ; j ++){ // N° de la question
                System.out.println (" Question n°" + (j + 1)+ " :");
                System.out.println (" \t Vous avez répondu : " );   
                if (reponse_utilisateur[0][j] == 1){
                    System.out.print(" A "); 
                }
                else if (reponse_utilisateur[1][j] == 1){
                    System.out.print(" B "); 
=======
  
            for (int i = 0; i < nombre_question; i++)
            {
                System.out.println (" Question n°" + (i + 1)+ " :");
                System.out.println (" \t Vous avez répondu : " + reponse_utilisateur[i]);
                if (reponse_test[i].length > 1) {
                    System.out.println ("\t Les bonnes réponses étaient : " + reponse_test[i]); 
                }   
                else if (reponses_test[i].length == 1) {
                    System.out.println ("\t La bonne réponse était :" + reponse_test[i] +");
>>>>>>> origin/master
                }
                else {
                    System.out.println ("\t Aucune des réponses porposées n'est valide ! ");
                }
                
                for (int j = 0; j < reponse[i].length(); j++)
                {
                    if (reponse_utilisateur[i].contains(reponse.charAt(j)))
                    {
                        reponses_vraies = reponses_vraies + reponse[i].charAt(j);
                    }
                    else 
                    {
                        reponses_fausses = reponses_fausses + reponse[i].charAt(j);
                    }
                    
                    if (justification_test[i] != null) {
                        System.out.println ("Justification : ");
                        System.out.println ( "\t " + justification_test[i]); 
                    }
                    else {
                        System.out.println ("Aucune justification disponible");
                    }
                }
                
                for (int j = 0; j < reponse_utilisateur[i].length(); j++)
                {
                    if (!reponse[i].contains(reponse_utilisateur[i].charAt(j)))
                    {
                        reponses_fausses = reponses_fausses + reponse_utilisateur[i].charAt(j);
                    }
                }
                
                if (reponses_vraie.length > 1) {
                    System.out.println ("\t Vous avez " + reponses_vraie.length  +" bonnes réponses"); 
                }   
                else if (reponses_vraie.length == 1) {
                    System.out.println ("\t Vous avez " + reponses_vraie.length  +" bonne réponse");
                }
                else {
                    System.out.println ("\t Vous n'avez aucune bonne réponse");
                }
                
                if (reponses_fausses.length  > 1) {
                    System.out.println ("\t Vous avez " + reponses_fausses.length +" mauvaises réponses"); 
                }   
                else if (reponses_fausses.length  == 1) {
                    System.out.println ("\t Vous avez " + reponses_fausses.length +" mauvaise réponse");
                }
                else {
                    System.out.println ("\t Vous n'avez pas de mauvaise réponse");
                }
                
                // Calcul du resultat dans tout les mode de cotation : 
                
                resultats_facile = reponses_vraies.length ;
                resultats_intermédiaire = reponses_vraies.length + (0.5*(reponses_fausses.length)); 
                resultats_test = reponses_vraies.length + reponses_fausses.length; 
               
                // On reset les reponses de la question
                
                reponses_vraies =""; 
                reponses_fausses =""; 
                
            }

            // [Julien] Resumer les points obtenus en fonction du type de cotation:
            
            separation(); 

            if (type_cotation == 1){ // Mode facile 
                
                if (resultats_facile == 0) {
                    System.out.println (" Attention : Aucune bonne réponse !"); 
                }
                else if (resultats_facile == 1){    
                    System.out.println ("Attention : Une seule bonne réponse !");
                }
                else if (resultats_facile > 1 && resultats_facile < 10){
                    System.out.print ("Attention : Vous êtes en échec ! Je vous conseil de passer en mode entrainement");
                }
                else {
                    System.out.println ("Félicitation, je vous conseil de passer en mode intermédiaire ! "); 
                }
                
                System.out.println ("Vous avez obtenu :" + resultats_facile + " point(s)"); 
                
            }

            else if (type_cotation == 2){ // Mode intermédiaire
                
                 if (resultats_intermediaire > 10) {
                     System.out.println ("Bravo ! Je vous conseil de passer en mode test ! "); 
                    }
                else {
                    System.out.println ("Attention : Vous êtes en échec ! Je vous conseil de vous entrainer en mode entrainement..."); 
                }
                
                System.out.println ("Vous avez obtenu :" + resultats_intermediaire + " point(s)"); 
                
            }
            
            else if (type_cotation == 3){ // Mode test
                
                if (resultats_test > 10) {
                     System.out.println ("Félicitation ! Vous avez réussi ce QCM en difficulté maximale "); 
                    }
                else {
                    System.out.println ("Attention : Vous êtes en échec ! Je vous conseil de vous entrainer en mode entrainement..."); 
                }
                
                System.out.println ("Vous avez obtenu :" + resultats_intermediaire + " point(s)"); 
            
            }
            else {
                System.out.println (" Voici les points que vous auriez obtenu dans les différents modes de cotation"); 
                System.out.println (" \t Mode facile : " + resultats_facile + " points"); 
                System.out.println (" \t Mode intermediaire : " + resultats_intermediaire + " points"); 
                System.out.println (" \t Mode test :" + resultats_test + " points"); 
            }
            
            // [All] Faire une moyenne des résultats obtenus ? 

            int moyenne_test [] = {}; 
            System.out.println ("Vous avez une moyenne de ...");

            // [All] Reset les tableaux : 

            // [Julien ?] Relancer le questionnaire

            System.out.println ("Voulez-vous recommencer ? (true/false)");
            Scanner sc = new Scanner (System.in); 
            again = sc.nextBoolean();   

        }while( again ==  true);   
    
        System.out.println (" A propos, ce QCM a été réalisé par Julien Banken, Robin Gielen et Victor Baert");
        
    }

    public static String reponseUtilisateur ()
    {
        do {
            String reponse_utilisateur;
            char reponse = TextIO.getChar();
            if (reponse!="a" && reponse!="b" && reponse!="c" && reponse!="d" && reponse!="e" && reponse!="f")
            {
                System.out.println ("Veuillez entrer une réponse valide SVP!");
            }
            else
            {
                if (reponse!="x") 
                {
                    reponse_utilisateur = reponse_utilisateur + reponse;
                }
            }
        } while (reponse != "x");
        return reponse_utilisateur;
    }
   
    public static void separation (){
        
        System.out.println ("<");
        for (int separation = 0; separation <=20; separation ++){
            System.out.println ("="); 
        } 
        System.out.println (">");
        
        }  
    }
