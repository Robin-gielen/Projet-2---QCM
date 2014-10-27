import java.util.Scanner; 
import java.lang.String; 
import java.io.*;
import java.util.Random;

/**
 * Write a description of class qcm here.
 * 
 * @author (Julien Banken, Robin Gielen, Victor Baert) 
 * @version (a version number or a date)
 */
public class qcm
{
    public static void main (String[] args) {    

        // [All] Initialisation des variables a initialiser : 
        
        int type_cotation; // Choix du type de cotation
        int colonne; // Le numero de la colonne
        int ligne; // Le numero de la ligne
        int nombreQuestion; // Mise-a-jour lors de la lecture du fichier txt
        int nombreReponsesTot; // Nombre de reponses total, afin de calculer les resultats
        String indexReponses = "abcdefghij"; // Variable pour avoir un acces facile aux 10 premieres lettre de l'alphabet, pour l'index des reponses
        int countQuestion; // Variable de test pour le compte des questions

        // 1) Les reponses : 
        int nombreReponsesMax; // Stock le nombre de reponses max pour une question
        String reponses_vraies; // Variable qui conserve les reponses vraies 
        String reponses_fausses; // Variable qui conserve les reponses de l'utilisateur
        int reponsesVraies; // Variable qui concerve le nombre de reponses vraies
        int reponsesFausses; // Variable qui conserve le nombre de reponses fausses
        
        // 2) Les resultats : 
        int resultats_facile = 0; // Score de l utilisateur en mode facile
        double resultats_intermediaire = 0; // Score de l utilisateur en mode intermediaire
        int resultats_test = 0; // Score de l utilisateur en mode test
        
        int resultatsFacile;
        double resultatsIntermediaire;
        int resultatsTest;
        
        // 3) Recommencer a la fin du questionnaire ? 
        boolean again = false; 

        // [Victor] Afficher les regles : 

        System.out.println ("/n Les règles :");
        System.out.println (" Chaque question s'affichera séparement avec toutes ses proposition. Vous devrez alors entrer au minimim UNE réponse.");
        System.out.println (" Vous pouvez entrer plusieurs réponses simultanément ");
        System.out.println (" Attention : Une question, qui demande deux bonnes réponses en entrée et que vous ne répondez que par une seule bonne réponse, sera considerée comme fausse");
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
                        nombreReponsesTot++;
                    }
                    currentLine = br.readLine();
                }
            } catch (IOException e) { System.exit(0);}
            
            System.out.println(nombreReponsesMax); // Test afin de verifier la concordance
            
            String questionnaire[][] = new String[nombreQuestion][2]; // Stocke les questions et les reponses correctes pour chaque question
            String reponsesEtudiant[] = new String[nombreQuestion]; // Stocke les reponses de l etudiant
            int nombreReponses[] = new int[nombreQuestion]; // Stocke le nombre de reponses pour chaque question
            String reponsesQuestions[][] = new String[nombreQuestion][nombreReponsesMax]; // Stocke toutes les reponses pour chaque question sous forme de String et si la reponse est vraie ou fausse
            double resultatsFinaux[][] = new double[nombreQuestion][3]; // Stocke les resultats de chaque question dans chaque mode de correction dans cet ordre : facile, intermediaire, test.
            
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
                        questionnaire[countQ][0] = currentLine;
                        countQ++;
                    }
                    else if (currentLine.charAt(0) == 'A')
                    {
                        reponsesQuestions[countQ-1][countA] = currentLine.substring(2); // Stock la reponse et si celle ci est vraies ou fausse 
                        // if (currentLine.charAt(currentLine.indexOf('|', 2) + 1) == 'V')
                        // {
                            // questionnaire[countQ-1][2] += indexReponses.charAt(countA);
                        // }
                        countA++;
                    }
                    currentLine = br.readLine();
                }
            } catch (IOException e) { System.exit(0);}
            
            // Methode melangeant les reponses aleatoirement par question
            
            Random rgen = new Random();
            for (int j=0; j < nombreQuestion; j++) 
            {   
                for (int i=0; i < nombreReponsesMax; i++) 
                {
                    int randomPosition = rgen.nextInt(nombreReponsesMax+1);
                    String temp = reponsesQuestions[j][i];
                    reponsesQuestions[j][i] = reponsesQuestions[j][randomPosition];
                    reponsesQuestions[j][randomPosition] = temp;
                }
            }
            
            // Methode replacant, apres le melange des reponses, toutes les reponses non vides au debut du tableau afin de rassembler toutes les reponses
            
            for (int j=0; j < nombreQuestion; j++) 
            {   
                for (int i=0; i < nombreReponsesMax; i++) 
                {
                    if (reponsesQuestions[j][i] == null)
                    {
                        reponsesQuestions[j][i] = reponsesQuestions[j][i+1];
                        reponsesQuestions[j][i+1] = null;
                    }
                }
            }
            
            // Methode stockant les reponses correctes dans le tableau reponsesQuestions suivant le nouvel index
            
            for (int i=0; i < nombreQuestion; i++)
            {
                for (int j=0; j < nombreReponsesMax; j++)
                {
                    if (reponsesQuestions[i][j] != null && reponsesQuestions[i][j].charAt(reponsesQuestions[i][j].indexOf('|', 2) + 1) == 'V')
                    {
                        questionnaire[i][1] = questionnaire[i][1] + indexReponses.charAt(j);
                    }
                }
            }
            
            /**
             * A ce stade-ci, nous avons deux tableaux representatifs de la situation. Le premier contient les questions ainsi que les references des bonnes reponses et le deuxiemes contient toutes les reponses possible. 
             * Ces deux tableaux vont nous servir non seulement afin d'afficher les questions et les reponses a l utilisateur afin qu il puisse y repondre, mais ils vont egalement nous servir a la correction des reponses donnes par l utilisateur.
             */

            // [Robin] Afficher les questions et les reponses (Utiliser un PRNG !):

            for (int i=1; i < nombreQuestion + 1; i++)
            {
                System.out.println(" Question n°" + i + ":");
                System.out.println(questionnaire[i-1][0]);
                for (int j=0; !(reponsesQuestions[i-1][j] == null); j++)
                {
                    System.out.println("Reponse " + indexReponses.charAt(j) + ")");
                    System.out.println(reponsesQuestions[i-1][j].substring(2, reponsesQuestions[i-1][j].indexOf('|', 2) - 1));
                    reponsesEtudiant[i-1] = reponseUtilisateur();
                }
                
            }
            

            // [Julien] Calculer et afficher les resultats en fonction du mode de cotation par question :
            
            separation(); 

            System.out.println ("Maintenant , nous allons procéder à la vérification de vos réponses ... ");
  
            /**
             * Methode calculant les points obtenus par l etudiant pour chaque question dans chaque mode de difficulte et les stockant dans un tableau
             */
            
            for(int i = 0; i < nombreQuestion; i++)
            {
                for (int j = 0; j < reponsesEtudiant[i].length(); j++)
                {
                    if(contain(questionnaire[i][1], reponsesEtudiant[i].charAt(j))) // On check si l etudiant a choisis de bonnes reponses
                    {
                        reponsesVraies++;
                    }
                    else if (!(contain(questionnaire[i][1], reponsesEtudiant[i].charAt(j)))) // On check si l etudiant n a pas donne de fausses reponses
                    {
                        reponsesFausses++;
                    }
                    else if (!(contain(reponsesEtudiant[i], questionnaire[i][1].charAt(j)))) // On check si l etudiant n a pas oublie une ou plusieurs bonnes reponses
                    {
                        reponsesFausses++;
                    }
                }
                
                // Mode facile +1/0
                if (reponsesFausses > 0)
                {
                    resultatsFinaux[i][0] = 0;
                }
                else if (reponsesFausses == 0 && reponsesVraies == questionnaire[i][1].length())
                {
                    resultatsFinaux[i][0] = 1;
                }
                else if (reponsesFausses == 0 && reponsesVraies == 0)
                {
                    resultatsFinaux[i][0] = 0;
                }
                else 
                {
                    resultatsFinaux[i][0] = 0;
                }
                
                // Mode intermediaire +1/-0.5
                if (reponsesFausses > 0)
                {
                    resultatsFinaux[i][1] = -0.5;
                }
                else if (reponsesFausses == 0 && reponsesVraies == questionnaire[i][1].length())
                {
                    resultatsFinaux[i][1] = 1;
                }
                else if (reponsesFausses == 0 && reponsesVraies == 0)
                {
                    resultatsFinaux[i][1] = 0;
                }
                else 
                {
                    resultatsFinaux[i][1] = -0.5;
                }
                
                // Mode test +1/-1
                if (reponsesFausses > 0)
                {
                    resultatsFinaux[i][2] = -1;
                }
                else if (reponsesFausses == 0 && reponsesVraies == questionnaire[i][1].length())
                {
                    resultatsFinaux[i][2] = 1;
                }
                else if (reponsesFausses == 0 && reponsesVraies == 0)
                {
                    resultatsFinaux[i][2] = 0;
                }
                else 
                {
                    resultatsFinaux[i][2] = -1;
                }
                for (int j = 0; j < nombreQuestion; j++)
                {
                    resultatsFacile += resultatsFinaux[j][0];
                    resultatsIntermediaire += resultatsFinaux[j][1];
                    resultatsTest += resultatsFinaux[j][2];
                }
                
            }
            
            /**
             * Methode d affichage des points pour chaque question et pour le total, plus affichage des justifications dans le cas des questions qui en comportent
             */
            
            // Si la methode de correction choisie est le mode facile
            
            if (type_cotation == 1)
            {
                for (int i = 1; i < nombreQuestion+1; i ++)
                {
                    System.out.println("A la question numero " + i + ", vous avez obtenu :" + resultatsFinaux[i][0] + " point(s) dans le mode de correction facile");
                }
                System.out.println("Ce qui fait un score de " + resultatsFacile + " points sur un total de " + nombreQuestion + " points");
            }
            
            // Si la methode de correction choisie est le mode intermediaire
            
            else if (type_cotation == 2)
            {
                for (int i = 1; i < nombreQuestion+1; i ++)
                {
                    System.out.println("A la question numero " + i + ", vous avez obtenu :" + resultatsFinaux[i][1] + " point(s) dans le mode de correction intermediaire");
                }
                System.out.println("Ce qui fait un score de " + resultatsIntermediaire + " points sur un total de " + nombreQuestion + " points"); 
            }
            
            // Si la methode de correction choisie est le mode test
            
            else if (type_cotation == 3)
            {
                for (int i = 1; i < nombreQuestion+1; i ++)
                {
                    System.out.println("A la question numero " + i + ", vous avez obtenu :" + resultatsFinaux[i][2] + " point(s) dans le mode de correction test");
                }
                System.out.println("Ce qui fait un score de " + resultatsTest + " points sur un total de " + nombreQuestion + " points"); 
            }
            
            // Si la methode de correction choisie est l affichage de tous les resultats
            
            else if (type_cotation == 4)
            {
                for (int i = 1; i < nombreQuestion+1; i ++)
                {
                    System.out.println("A la question numero " + i + ", vous avez obtenu : ");
                    System.out.println(resultatsFinaux[i][0] + " point(s) dans le mode de correction facile");
                    System.out.println(resultatsFinaux[i][1] + " point(s) dans le mode de correction intermediaire");
                    System.out.println(resultatsFinaux[i][2] + " point(s) dans le mode de correction test");
                }
                System.out.println("Ce qui fait un score de : ");
                System.out.println(resultatsFacile + " points sur un total de " + nombreQuestion + " points pour le mode facile");
                System.out.println(resultatsIntermediaire + " points sur un total de " + nombreQuestion + " points pour le mode intermediaire");
                System.out.println(resultatsTest + " points sur un total de " + nombreQuestion + " points pour le mode test");
            }
            

            
            
            
            //             for (int i = 0; i < nombreQuestion; i++)
            //             {
            //                 System.out.println (" Question n°" + (i + 1) + " :");
            //                 System.out.println (); 
            //                 System.out.println (" \t" + reponsesQuestions[i][1]); 
            //                 System.out.println (" \t Vous avez répondu :" );
            //                 
            //                 for (int j = 0; j < reponsesEtudiant[i].length(); j++) {
            //                     System.out.print (" " + reponsesEtudiant[i].charAt(j) + " ") ; 
            //                 }
            //         
            //                 if (questionnaire[i][1].length() > 1) {
            //                     System.out.println ("\t Les bonnes réponses étaient : "); 
            //                     for (int k = 0; k < questionnaire[i][1].length(); k++) {
            //                         System.out.print (" " + questionnaire[i][1].charAt(k) + " ") ; 
            //                     }
            //                 }   
            //                 else if (questionnaire[i][1].length() == 1) {
            //                     System.out.println ("\t La bonne réponse était : ");
            //                     for (int k = 0; k < questionnaire[i][1].length(); k++) {
            //                         System.out.print (" " + questionnaire[i][1].charAt(k) + " ") ; 
            //                     }
            //                 }
            //                 else {
            //                     System.out.println ("\t Aucune des réponses proposées n'est valide ! ");
            //                 }
            //                 
            //                 for (int l = 0; l < questionnaire[i][1].length() ; l++)
            //                 {
            //                     if (contain(reponsesEtudiant[i], questionnaire[i][1].charAt(l)))      
            //                     {
            //                         reponses_vraies = reponses_vraies + questionnaire[i][1].charAt(l);
            //                     }
            //                     else 
            //                     {
            //                         reponses_fausses = reponses_fausses + questionnaire[i][1].charAt(l);
            //                     }
            //                 }
            //                 
            //                 if (justification_test[i] != null) {
            //                     System.out.println ("Justification : ");
            //                     System.out.println ( "\t " + justification_test[i]); 
            //                 }
            //                 else {
            //                     System.out.println ("Pas de justification disponible");
            //                 }
            //                 
            //                 for (int l = 0; l < reponseEtudiant[1][i].length(); l++)
            //                 {
            //                     if (!questionnaire[i][1].contains(reponseEtudiant[i].charAt(l)))
            //                     {
            //                         reponses_fausses = reponses_fausses + reponseEtudiant[i].charAt(l);
            //                     }
            //                 }
            //         
            //                 if (reponses_vraie.length() > 1) {
            //                     System.out.println ("\t Vous avez " + reponses_vraie.length  +" bonnes réponses"); 
            //                 }   
            //                 else if (reponses_vraie.length == 1) {
            //                     System.out.println ("\t Vous avez " + reponses_vraie.length  +" bonne réponse");
            //                 }
            //                 else {
            //                     System.out.println ("\t Vous n'avez aucune bonne réponse");
            //                 }
            //                 
            //                 if (reponses_fausses.length  > 1) {
            //                     System.out.println ("\t Vous avez " + reponses_fausses.length +" mauvaises réponses"); 
            //                 }   
            //                 else if (reponses_fausses.length  == 1) {
            //                     System.out.println ("\t Vous avez " + reponses_fausses.length +" mauvaise réponse");
            //                 }
            //                 else {
            //                     System.out.println ("\t Vous n'avez pas de mauvaise réponse");
            //                 }
            //                 
            //                 // Resultats de tout les modes
            //                 
            //                 resultats_facile = reponses_vraies.length() ;
            //                 resultats_intermédiaire = reponses_vraies.length() + (0.5*(reponses_fausses.length())); 
            //                 resultats_test = reponses_vraies.length() + reponses_fausses.length(); 
            //                
            //                 // On reset les reponses vraies et fausses pour chaque iteration
            //                 
            //                 reponses_vraies =""; 
            //                 reponses_fausses =""; 
            //                 
            //                 }

            // [Julien] Resumer les points obtenus en fonction du type de cotation:
            
            separation(); 
            // 
            //             if (type_cotation == 1){ // Mode facile 
            //                 
            //                 if (resultats_facile == 0) {
            //                     System.out.println ("Attention : Aucune bonne réponse !"); 
            //                 }
            //                 else if (resultats_facile == 1){    
            //                     System.out.println ("Attention : Une seule bonne réponse !");
            //                 }
            //                 else if (resultats_facile > 1 && resultats_facile < 10){
            //                     System.out.print ("Epreuve ratée !");
            //                 }
            //                 else {
            //                     System.out.println ("Epreuve réussie !"); 
            //                 }
            //                 
            //                 System.out.println ("Vous avez obtenu :" + resultats_facile + " point(s)"); 
            //                 
            //             }
            // 
            //             else if (type_cotation == 2){ // Mode intermédiaire
            //                 
            //                 if (resultats_intermediaire < 10){
            //                     System.out.print ("Epreuve ratée !");
            //                 }
            //                 else {
            //                     System.out.println ("Epreuve réussie !"); 
            //                 }
            //                 
            //                 System.out.println ("Vous avez obtenu :" + resultats_intermediaire + " point(s)"); 
            //                 
            //             }
            //             
            //             else if (type_cotation == 3){ // Mode test
            //                                 
            //                 if (resultats_intermediaire < 10){
            //                     System.out.print ("Epreuve ratée !");
            //                 }
            //                 else {
            //                     System.out.println ("Epreuve réussie !"); 
            //                 }
            //   
            //                 System.out.println ("Vous avez obtenu :" + resultats_test + " point(s)"); 
            //             
            //             }
            //             else {
            //                 System.out.println (" Voici les points que vous auriez obtenu dans les différents modes de cotation"); 
            //                 System.out.println (" \t Mode facile : " + resultats_facile + " points"); 
            //                 System.out.println (" \t Mode intermédiaire : " + resultats_intermediaire + " points"); 
            //                 System.out.println (" \t Mode test :" + resultats_test + " points"); 
            //             }
            //             
            //             // [All] Faire une moyenne des résultats obtenus ? 
            // 
            //             int moyenne_test [] = {}; 
            //             System.out.println ("Vous avez une moyenne de ...");

            // [All] Reset les tableaux : 

            // [Julien ?] Relancer le questionnaire

            System.out.println ("Voulez-vous recommencer ? (true/false)");
            Scanner sc = new Scanner (System.in); 
            again = sc.nextBoolean();   

        }while( again ==  true);   
    
        System.out.println (" A propos ; Ce QCM a été réalisé par Julien Banken, Robin Gielen et Victor Baert dans le cadre du projet 2");
        
    }

    // Les autres methodes : 
    
    /**
     * Methode recuperant l ensemble des reponses de l utilisateur pour une question sous le format "abcde..." ou les lettres sont les indexs des reponses a la question que l utilisateur a choisis
     */
    public static String reponseUtilisateur ()
    {
        String reponseEtudiant;
        do {
            Scanner x = new Scanner (System.in); 
            char reponse = x.nextChar();
            if (reponse!="a" && reponse!="b" && reponse!="c" && reponse!="d" && reponse!="e" && reponse!="f" && reponse!="g" && reponse!="h" && reponse!="i" && reponse!="j")
            {
                System.out.println ("Veuillez entrer une réponse valide SVP!");
            }
            else
            {
                if (reponse!="x" && !contain(reponseEtudiant, reponse)) 
                {
                    reponseEtudiant = reponseEtudiant + reponse;
                }
            }
        } while (reponse != "x");
        return reponseEtudiant;
    }
   
    public static void separation (){
        for (int separation = 0; separation <= 20; separation ++){
            System.out.println ("="); 
        } 
        }   
        
    /**
     * Methode permettant de savoir si un char est contenu dans une String
     */
    public static boolean contain(String actual, char isIn) 
    {
        boolean contient = false;
        for (i = 0; i < actual.length(); i++)
        {
            if (actual.charAt(i) == isIn)
            {
                contient = true;
            }
        }
        return (contient);
    }
    
    /**
     * Methode comptant le nombre de fois ou un char est contenu dans une String
     */
    public static int occurence(String actual, char isIn)
    {
        int count = 0;
        for (int i = 0; i<actual.length();i++)
        {
            if (actual.charAt(i) == isIn)
            {
                count++;
            }
        }
        return count;
    }
    
    }
    
    
    
    
    
    
    
    
    
    