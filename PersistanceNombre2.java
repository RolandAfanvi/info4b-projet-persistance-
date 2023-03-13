package Projet;
import java.io.*;

import java.net.*;
import java.util.*;


//////////////////////////////////////////////////////////////////////////////////
///                             CLASS PRINCIPALE                             /////
//////////////////////////////////////////////////////////////////////////////////

public class PersistanceNombre2 {
    public static void main(String[] args){
        int port=8080;
        // Serveur serveur = new Serveur(port);
        // serveur.start();
        System.out.println("moi");
    }
}

//////////////////////////////////////////////////////////////////////////////////
///                             CLASS WORKER                                 /////
//////////////////////////////////////////////////////////////////////////////////

class Worker extends Thread{
    // Attributs 
    private Serveur serveur;
    private Socket WorkerSocket;
    private ObjectOutputStream out; 
    private ObjectInputStream in;
    private int NbCoeur;
    private String id;
    //private int NbCoeurDispo;

    //Constructeur
    public Worker(int nbCoeur,String id,Serveur serveur){
        this.NbCoeur=nbCoeur;
        this.serveur=serveur;
        this.id=id;

        try {
        this.out= new ObjectOutputStream(WorkerSocket.getOutputStream());
        this.in = new ObjectInputStream(WorkerSocket.getInputStream());

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void run(){

    // }


    public void disconnect() throws IOException {
        //Utils.sendMessage(socket, "disconnect");
        WorkerSocket.close();
        serveur.removeWorker(this);
    }

    // public void connect() throws IOException{

    // }

    public String getID(){
        return this.id;
    }


    // Methode de calcul de la persistance d un nombre donné
    public int CalculPersistance(int number){
        int persistance =0;
        int produit = 1;
        while(number>=10){
            produit=1;
            while(number!=0){
                produit=produit * (number % 10);
                number = number/10;
            }
            number=produit;
            persistance++;
        }
        return persistance;
    }



}


//////////////////////////////////////////////////////////////////////////////////
///                             CLASS CLIENT                                 /////
//////////////////////////////////////////////////////////////////////////////////

/*

class Client extends Thread{
    //Attributs 
    private Socket socket;
    private BufferedReader sisr;
    private PrintWriter sisw = new PrintWriter(new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
    

    //Constructeur
    public Client(Socket socket ,){
        this.socket=socket; 
        sisr= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.sisw = new PrintWriter(new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true)
    }

    public void run(){

    }


}
*/

//////////////////////////////////////////////////////////////////////////////////
///                             CLASS SERVEUR                                /////
//////////////////////////////////////////////////////////////////////////////////

class Serveur extends Thread{
    //Attributs
    private Hashtable workers;
    private ArrayList<ConnexionClient> clients;
    private int port;
    private ServerSocket serveurSocket;
    //private Socket WorkerServeur;
    private int NbCourant ;

    //constructeur 
    public Serveur(int port){
        workers= new  Hashtable<String,Worker>();
        clients= new ArrayList<ConnexionClient>();
        this.port=port;
    }

    public void run(){
        try{
            serveurSocket = new ServerSocket(port);
            System.out.println("Serveur lancé sur le port " + port);
            while (true) {
                Socket clientSocket = serveurSocket.accept();
                System.out.println("Nouveau client connecté: " + clientSocket.getInetAddress().getHostAddress());
                ConnexionClient clientConnecte = new ConnexionClient(clientSocket, this);
                clients.add(clientConnecte);
                clientConnecte.start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }


    public synchronized void removeClient(ConnexionClient client) {
        clients.remove(client);
    }

    public synchronized void removeWorker(Worker worker) {
        workers.remove(worker.getID());
    }

}

//////////////////////////////////////////////////////////////////////////////////
///                             CLASS connexionClient                        /////
//////////////////////////////////////////////////////////////////////////////////


class ConnexionClient extends Thread{
    //private int id;
    //private boolean arret=false;
    private String pseudo;
    private Serveur serveur;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ConnexionClient(Socket clientSocket, Serveur serveur){
        this.clientSocket= clientSocket;
        this.serveur=serveur;
        
    }


       public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Entrer votre pseudo:");
            this.pseudo= in.readLine();
            out.println("Bienvenue, " + this.pseudo + "!");

            String saisie;
            while ((saisie = in.readLine()) != null) {
                if (saisie.equals("END")) {
                    break;
                }
                //this.serveur.broadcast(saisie, this);
            }
            this.serveur.removeClient(this);
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EnvoiMessage(String message) {
        out.println(message);
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void Disconnect() throws IOException {
        this.clientSocket.close();
        this.serveur.removeClient(this);
    }














}
