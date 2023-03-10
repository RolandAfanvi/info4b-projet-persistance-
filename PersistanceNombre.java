package Projet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Hashtable;


//////////////////////////////////////////////////////////////////////////////////
///                             CLASS PRINCIPALE                             /////
//////////////////////////////////////////////////////////////////////////////////

public class PersistanceNombre {
    public static void main(String[] args){
        Serveur serveur = new Serveur(port : 8080); 

    }
}

//////////////////////////////////////////////////////////////////////////////////
///                             CLASS WORKER                                 /////
//////////////////////////////////////////////////////////////////////////////////

class Worker extends Thread{
    // Attributs 
    private Serveur serveur;
    private int NbCoeurTotal;
    private int NbCoeurDispo;
    private Socket socket; 
    private ObjectOutputStream out; 
    private ObjectInputStream in; 
    

    //Constructeur
    public Worker(int nbCoeur){
        this.NbCoeurDispo = nbCoeur; 
        this.out = new ObjectOutputStream(socket.getOutputStream()); 
        this.in = new ObjectOutputStream(socket.getInputStream()); 
    }

    public void run()
    {

    }


    // Methode de calcul de la persistance d un nombre donné
    public int CalculPersistance(int number){
        BigInteger nombre = new BigInteger();
        int persistance = 0;
        int TotalPersistance = 0; 

        while(nombre.compareTo(BigInteger.TEN)>=0){
            BigInteger produit= BigInteger.ONE;
            while(nombre.compareTo(BigInteger.ZERO)>0){
                BigInteger chiffre = nombre.mod(BigInteger.TEN); 
                nombre = nombre.divide(BigInteger.TEN); 
                produit = produit.multiply(chiffre); 
            }
            nombre=produit;
            persistance++;
            TotalPersistance +=persistance;
        }
        return persistance;  
    }
    //calcul moyenne des persistances
    double moyennePersistance = (double) TotalPersistance / nombre.length ; 

    //calcul medianne des persistances
    double medianePeristance = 

    public getMoyenne(){

    }

    public getMediane(){

    }

    public getOccurrence(){

    }
}


//////////////////////////////////////////////////////////////////////////////////
///                             CLASS CLIENT                                 /////
//////////////////////////////////////////////////////////////////////////////////



class Client extends Thread{
    //Attributs 
    private Socket socket; 
    private BufferedReader sisr; 
    private PrintWriter sisw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); 

    //Constructeur
    public Client(Socket socket){
        this.socket = socket; 
        this.sisr = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        this.sisw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); 
    }

    public void run()
    {

    }


}


//////////////////////////////////////////////////////////////////////////////////
///                             CLASS SERVEUR                                /////
//////////////////////////////////////////////////////////////////////////////////

class Serveur extends Thread{
    //Attributs
    private Hashtable workerDispo;
    final static int port; 
    private ArrayList<Tache> taches; 
    private Socket ClientDispo; 
    private Hashtable<Integer, Integer> resultats; 
    private Socket WorkerServeur; 
    private int nombreTachesParWorkers; 

    //constructeur 
    public Serveur(int port){
        workerDispo = new Hashtable<Worker, Integer>(); 
        this.port = port; 
    }

    public void ajouterWorker(String nom, int nbCoeur){
        workerDispo.put(nom, nbCoeur); 
        System.out.print("Le worker "+ nom +" est disponible avec "+nbCoeur+" coeurs"); 
        repartirTaches();
    }

    public void enleveWorker(String nom){
        workerDispo.remove(nom); 
        System.out.print("Le worker "+nom+" n'est plus disponible."); 
        repartirTaches();
    }

    public void ajouteTaches(int debut, int fin){
        taches = new Taches(debut, fin); 
        system.out.print("la tache [ "+debut+" , "+fin+" ] a été ajoutée"); 
        repartirTaches();
    }

    public void repartirTaches(){

    }

    public void run()
    {

    }



}

//////////////////////////////////////////////////////////////////////////////////
///                             CLASS CLASSEMENT                                /////
//////////////////////////////////////////////////////////////////////////////////

class Classement{
    private int[][] 
}
