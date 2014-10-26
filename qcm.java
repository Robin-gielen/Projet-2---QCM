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
        
        // 2) Les resultats : 
        int resultats_facile = 0; // Score de l utilisateur en mode facile
        double resultats_intermédiaire = 0; // Score de l utilisateur en mode intermediaire
        int resultats_test = 0; // Score de l utilisateur en mode test
        
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
            String reponsesQuestions[][] = new String[nombreQuestion][nombreReponsesMax]; // Stocke toutes les reponses pour chaque question sous forme de String
            
            // Combien de reponse au total ? Pour calculer la moyenne ? 
            
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

            // [Robin] Afficher les questions et les reponses (Utiliser un PRNG !):

            System.out.println (" Question n°");
            System.out.println ("\t a)"); 
            System.out.println ("\t b)");
            System.out.println ("\t c)");
            System.out.println ("\t d)");
            System.out.println ("\t e)");
            System.out.println ("\t f)");

            // [Victor] Gerer les inputs de l'utilisateur :

            
            // [Julien] Calculer et afficher les resultats en fonction du mode de cotation par question :
            
            separation(); 

            System.out.println ("Maintenant , nous allons procéder à la vérification de vos réponses ... ");
  
            for (int i = 0; i < nombreQuestion; i++)
            {
                System.out.println (" Question n°" + (i + 1)+ " :");
                System.out.println (); 
                System.out.println (" \t" + reponsesQuestions[0][i]); 
                System.out.println (" \t Vous avez répondu :" );
                
                for (int j = 0; j < reponsesEtudiant[i].length(); j++) {
                    System.out.print (" " + reponsesEtudiant[i].charAt(j) + " ") ; 
                }
        
                if (questionnaire[i][1].length() > 1) {
                    System.out.println ("\t Les bonnes réponses étaient : "); 
                    for (int k = 0; k < questionnaire[i][1].length(); k++) {
                        System.out.print (" " + questionnaire[i][1].charAt(k) + " ") ; 
                    }
                }   
                else if (questionnaire[i][1].length() == 1) {
                    System.out.println ("\t La bonne réponse était : ");
                    for (int k = 0; k < questionnaire[i][1].length(); k++) {
                        System.out.print (" " + questionnaire[i][1].charAt(k) + " ") ; 
                    }
                }
                else {
                    System.out.println ("\t Aucune des réponses proposées n'est valide ! ");
                }
                
                for (int l = 0; l < questionnaire[i][1].length() ; l++)
                {
                    if (reponsesEtudiant[i].contains(questionnaire[i][1].charAt(l)))
                    {
                        reponses_vraies = reponses_vraies + questionnaire[i][1].charAt(l);
                    }
                    else 
                    {
                        reponses_fausses = reponses_fausses + questionnaire[i][1].charAt(l);
                    }
                    
                    if (justification_test[i] != null) {
                        System.out.println ("Justification : ");
                        System.out.println ( "\t " + justification_test[i]); 
                    }
                    else {
                        System.out.println ("Aucune justification disponible");
                    }
                }
                
                for (int l = 0; l < reponseEtudiant[1][i].length(); l++)
                {
                    if (!questionnaire[i][1].contains(reponseEtudiant[i].charAt(l)))
                    {
                        reponses_fausses = reponses_fausses + reponseEtudiant[i].charAt(l);
                    }
                }
        
                if (reponses_vraie.length() > 1) {
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
                
                // Resultats de tout les modes
                
                resultats_facile = reponses_vraies.length() ;
                resultats_intermédiaire = reponses_vraies.length() + (0.5*(reponses_fausses.length())); 
                resultats_test = reponses_vraies.length() + reponses_fausses.length(); 
               
                // On reset les reponses vraies et fausses pour chaque iteration
                
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
                    System.out.print ("Epreuve ratée !");
                }
                else {
                    System.out.println ("Epreuve réussie !"); 
                }
                
                System.out.println ("Vous avez obtenu :" + resultats_facile + " point(s)"); 
                
            }

            else if (type_cotation == 2){ // Mode intermédiaire
                
                if (resultats_intermediaire < 10){
                    System.out.print ("Epreuve ratée !");
                }
                else {
                    System.out.println ("Epreuve réussie !"); 
                }
                
                System.out.println ("Vous avez obtenu :" + resultats_intermediaire + " point(s)"); 
                
            }
            
            else if (type_cotation == 3){ // Mode test
                                
                if (resultats_intermediaire < 10){
                    System.out.print ("Epreuve ratée !");
                }
                else {
                    System.out.println ("Epreuve réussie !"); 
                }
  
                System.out.println ("Vous avez obtenu :" + resultats_test + " point(s)"); 
            
            }
            else {
                System.out.println (" Voici les points que vous auriez obtenu dans les différents modes de cotation"); 
                System.out.println (" \t Mode facile : " + resultats_facile + " points"); 
                System.out.println (" \t Mode intermédiaire : " + resultats_intermediaire + " points"); 
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
    
        System.out.println (" A propos ; Ce QCM a été réalisé par Julien Banken, Robin Gielen et Victor Baert dans le cadre du projet 2");
        
    }

    // Les autres methodes : 
    
    public static String reponseUtilisateur ()
    {
        do {
            Scanner x = new Scanner (System.in); 
            char reponse = x.nextChar();
            if (reponse!="a" && reponse!="b" && reponse!="c" && reponse!="d" && reponse!="e" && reponse!="f")
            {
                System.out.println ("Veuillez entrer une réponse valide SVP!");
            }
            else
            {
                if (reponse!="x") 
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
        
    }
