package Projet;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Hashtable;


//////////////////////////////////////////////////////////////////////////////////
///                             CLASS PRINCIPALE                             /////
//////////////////////////////////////////////////////////////////////////////////

public class PersistanceNombre {
    public static void main(String[] args){

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
    double moyennePersistance = (double) TotalPersistance / nombre.length ; 
}


//////////////////////////////////////////////////////////////////////////////////
///                             CLASS CLIENT                                 /////
//////////////////////////////////////////////////////////////////////////////////



class Client extends Thread{
    //Attributs 
    private Serveur serveur;
    static int port = 8080; 

    //Constructeur
    public Client(){

    }

    public 


}


//////////////////////////////////////////////////////////////////////////////////
///                             CLASS SERVEUR                                /////
//////////////////////////////////////////////////////////////////////////////////

class Serveur extends Thread{
    //Attributs
    private Hashtable workerDispo;
    private int NbCourant ;
    final static int port = 8080; 

    //constructeur 
    public Serveur(){

    }



}

//////////////////////////////////////////////////////////////////////////////////
///                             CLASS CLASSEMENT                                /////
//////////////////////////////////////////////////////////////////////////////////

class Classement{
    private int[][] 
}