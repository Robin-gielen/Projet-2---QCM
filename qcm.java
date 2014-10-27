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

        // Initialisation des variables a initialiser : 
        
        int type_cotation; // Choix du type de cotation
        int nombreQuestion=0; // Mise-a-jour lors de la lecture du fichier txt
        String indexReponses = "abcdefghij"; // Variable pour avoir un acces facile aux 10 premieres lettre de l'alphabet, pour l'index des reponses
        int countQuestion=0; // Variable de test pour le compte des questions

        // Les reponses : 
        int nombreReponsesMax=0; // Stock le nombre de reponses max pour une question
        String reponses_vraies; // Variable qui conserve les reponses vraies 
        String reponses_fausses; // Variable qui conserve les reponses de l'utilisateur
        int reponsesVraies=0; // Variable qui concerve le nombre de reponses vraies
        int reponsesFausses=0; // Variable qui conserve le nombre de reponses fausses
        
        // Les resultats :         
        double resultatsFacile=0;
        double resultatsIntermediaire=0;
        double resultatsTest=0;
        
        // Recommencer a la fin du questionnaire ? 
        boolean again = false; 

        // Afficher les regles : 
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

            //Choix du type de cotation :

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
            
            String currentLine;
            BufferedReader br = null;
            
            try
            {
                br = new BufferedReader(new FileReader("qcm.txt"));
                br.mark(1000000);
                currentLine = br.readLine();
                System.out.println(currentLine);
                int countA = 0;
                while (currentLine != null)
                {
                    if (!(currentLine.length() == 0))
                    {
                        System.out.println("countQuestion" + countQuestion);
                        System.out.println(currentLine.charAt(0));
                        if (currentLine.charAt(0) == 'Q')
                        {
                            countQuestion++;
                            if (countA > nombreReponsesMax)
                            {
                                nombreReponsesMax = countA; 
                            }
                            nombreQuestion++;
                            countA =0;
                        }
                        else if (currentLine.charAt(0) == 'A')
                        {
                            countA++;
                        }
                    }
                    currentLine = br.readLine();
                }
            } catch (IOException e) { System.exit(0);}
            
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
                    if (!(currentLine.length() == 0))
                    {
                        if (currentLine.charAt(0) == 'Q')
                        {
                            if (countQ !=0)
                            {
                                countA = 0;
                            }
                            currentLine = currentLine.substring(2);
                            questionnaire[countQ][0] = currentLine;
                            countQ++;
                        }
                        else if (currentLine.charAt(0) == 'A')
                        {
                            reponsesQuestions[countQ-1][countA] = currentLine.substring(2); // Stock la reponse et si celle ci est vraies ou fausse 
                            countA++;
                        }
                    }
                    nombreReponses[countQ-1] = countA;
                    currentLine = br.readLine();
                }
                br.close();
            } catch (IOException e) { System.exit(0);}
            
            
            // Methode melangeant les reponses aleatoirement par question
            
            Random rgen = new Random();
            for (int j=0; j < nombreQuestion; j++) 
            {   
                for (int i=0; i < nombreReponsesMax; i++) 
                {
                    int randomPosition = rgen.nextInt(nombreReponsesMax);
                    String temp = reponsesQuestions[j][i];
                    reponsesQuestions[j][i] = reponsesQuestions[j][randomPosition];
                    reponsesQuestions[j][randomPosition] = temp;
                }
            }
            
            // Methode replacant, apres le melange des reponses, toutes les reponses non vides au debut du tableau afin de rassembler toutes les reponses
            
            for (int j=0; j < nombreQuestion; j++) 
            {   
                boolean sorted = false;
                while (!sorted)
                {
                    sorted = true;
                    for (int i=0; i < nombreReponsesMax-1; i++) 
                    {
                        if (reponsesQuestions[j][i] == null && reponsesQuestions[j][i+1] != null)
                        {
                            reponsesQuestions[j][i] = reponsesQuestions[j][i+1];
                            reponsesQuestions[j][i+1] = null;
                            sorted = false;
                        }
                    }
                }
            }

            // Methode stockant les reponses correctes dans le tableau reponsesQuestions suivant le nouvel index
            
            for (int i=0; i < nombreQuestion; i++)
            {
                boolean first = true;
                for (int j=0; j < nombreReponsesMax; j++)
                {
                    if (first && reponsesQuestions[i][j] != null && reponsesQuestions[i][j].charAt(reponsesQuestions[i][j].indexOf('|', 2) + 1) == 'V')
                    {
                        questionnaire[i][1] =  "" + indexReponses.charAt(j);  //questionnaire[i][1]
                        first = false;
                    }
                    else if (!first && reponsesQuestions[i][j] != null && reponsesQuestions[i][j].charAt(reponsesQuestions[i][j].indexOf('|', 2) + 1) == 'V')
                    {
                        questionnaire[i][1] = questionnaire[i][1] + indexReponses.charAt(j);
                    }
                    
                }
            }
            
            /**
             * A ce stade-ci, nous avons deux tableaux representatifs de la situation. Le premier contient les questions ainsi que les references des bonnes reponses et le deuxiemes contient toutes les reponses possible. 
             * Ces deux tableaux vont nous servir non seulement afin d'afficher les questions et les reponses a l utilisateur afin qu il puisse y repondre, mais ils vont egalement nous servir a la correction des reponses donnees par l utilisateur.
             */

            for (int i=1; i < nombreQuestion + 1; i++)
            {
                System.out.println(" Question n°" + i + ":");
                System.out.println(questionnaire[i-1][0]);
                for (int j=0;   j < nombreReponsesMax && !(reponsesQuestions[i-1][j] == null); j++)
                {
                    System.out.println("Reponse " + (j+1) + ")");
                    System.out.println(reponsesQuestions[i-1][j].substring(0, reponsesQuestions[i-1][j].indexOf('|', 2)));
                }
                System.out.println("Veuillez entrer vos reponses en entrant les numeros de celle-ci un par un, lorsque vous avez finis de choisir toutes vos reponses, entrez " + (nombreReponses[i-1]+1) + " pour passer a la question suivante");
                reponsesEtudiant[i-1] = reponseUtilisateur(nombreReponses[i-1], indexReponses);
            }
            
            //Calculer et afficher les resultats en fonction du mode de cotation par question :
            
            separation(); 

            System.out.println ("Maintenant , nous allons procéder à la vérification de vos réponses ... ");
  
            /**
             * Methode calculant les points obtenus par l etudiant pour chaque question dans chaque mode de difficulte et les stockant dans un tableau
             */
            
            for(int i = 0; i < nombreQuestion; i++)
            {
                if (!(reponsesEtudiant[i] == null))
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
                }
                
                System.out.println(questionnaire[i][1].length());
                
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
                reponsesVraies = 0;
                reponsesFausses = 0;
            }
            for (int j = 0; j < nombreQuestion; j++)
            {
                resultatsFacile = resultatsFacile + resultatsFinaux[j][0];
                resultatsIntermediaire = resultatsIntermediaire + resultatsFinaux[j][1];
                resultatsTest = resultatsTest + resultatsFinaux[j][2];
            }
            
            /**
             * Methode d affichage des points pour chaque question et pour le total, plus affichage des justifications dans le cas des questions qui en comportent
             */
            
            // Si la methode de correction choisie est le mode facile
            
            if (type_cotation == 1)
            {
                for (int i = 1; i < nombreQuestion+1; i ++)
                {
                    System.out.println("A la question numero " + i + ", vous avez obtenu : " + resultatsFinaux[i-1][0] + " point(s) dans le mode de correction facile");
                }
                System.out.println("Ce qui fait un score de " + resultatsFacile + " points sur un total de " + nombreQuestion + " points");
            }
            
            // Si la methode de correction choisie est le mode intermediaire
            
            else if (type_cotation == 2)
            {
                for (int i = 1; i < nombreQuestion+1; i ++)
                {
                    System.out.println("A la question numero " + i + ", vous avez obtenu : " + resultatsFinaux[i-1][1] + " point(s) dans le mode de correction intermediaire");
                }
                System.out.println("Ce qui fait un score de " + resultatsIntermediaire + " points sur un total de " + nombreQuestion + " points"); 
            }
            
            // Si la methode de correction choisie est le mode test
            
            else if (type_cotation == 3)
            {
                for (int i = 1; i < nombreQuestion+1; i ++)
                {
                    System.out.println("A la question numero " + i + ", vous avez obtenu : " + resultatsFinaux[i-1][2] + " point(s) dans le mode de correction test");
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
            
            separation(); 
            
            System.out.println ("Voulez-vous recommencer ? (true/false)");
            try 
            {
                Scanner sc = new Scanner (System.in); 
            } catch (Exception e) {}


        }while( again ==  true);   
    
        System.out.println (" A propos ; Ce QCM a été réalisé par Julien Banken, Robin Gielen et Victor Baert dans le cadre du projet 2");
        
    }

    // Les autres methodes : 
    
    /**
     * Methode recuperant l ensemble des reponses de l utilisateur pour une question sous le format "abcde..." ou les lettres sont les indexs des reponses a la question que l utilisateur a choisis
     */
    public static String reponseUtilisateur (int nombreReponse, String indexReponses)
    {
        boolean answered = false;
        int reponse = 0;
        String reponseEtudiant = null;
        do {
            Scanner x = new Scanner (System.in); 
            try 
            {
                reponse = x.nextInt();
            } catch (Exception e) {}
            if (reponse < 1 || reponse > nombreReponse)
            {
                System.out.println ("Veuillez entrer une réponse valide !");
            }
            else if (!answered)
            {
                reponseEtudiant = "" + indexReponses.charAt(reponse-1);
                answered = true;
            }
            else if (answered)
            {
                reponseEtudiant = reponseEtudiant + indexReponses.charAt(reponse-1);
            }
        } while (reponse != (nombreReponse+1));
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
        for (int i = 0; i < actual.length(); i++)
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
    
    
    
    
    
    
    
    
    
    