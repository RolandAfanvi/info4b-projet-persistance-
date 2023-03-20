import java.io.*;
import java.net.*;
import java.util.*;

//////////////////////////////////////////////////////////////////////////////////
///						 CLASS PRINCIPALE						       	     /////
//////////////////////////////////////////////////////////////////////////////////

public class Serveur {
    static int port1 = 8080;
    static int port2 = 8024;
    static final int MaxClient = 100;

    static boolean arretClient = false;
    static final int MaxWorker = 20;
    static PrintWriter pwClient[];
    static PrintWriter pwWorker[];
    // static int IdClient = 0;
    // static int IdWorker =0 ;

    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            port1 = Integer.parseInt(args[0]);
            port2 = Integer.parseInt(args[1]);
        }

        pwClient = new PrintWriter[MaxClient];
        pwWorker = new PrintWriter[MaxWorker];

        serveurClient servec = new serveurClient(port1);
        servec.start();

    }

}

//////////////////////////////////////////////////////////////////////////////////
/// CLASS serveurClient /////
//////////////////////////////////////////////////////////////////////////////////

class serveurClient extends Thread {
    private int port;
    private ServerSocket socket;
    private int idClient;

    public serveurClient(int port) {
        this.idClient = 0;
        this.port = port;
        try {
            this.socket = new ServerSocket(this.port);
        } catch (Exception e) {
        }

        System.out.println("SOCKET ECOUTE CREE => " + this.socket);
        System.out.println("Serveur client lancé sur le port " + port);
    }

    public void run() {
        try {

            while (idClient < Serveur.MaxClient) {
                /*
                 * 2 - Attente d'une connexion client (la méthode s.accept() est bloquante
                 * tant qu'un client ne se connecte pas)
                 */
                Socket soc = socket.accept();
                /*
                 * 3 - Pour gérer plusieurs clients simultanément, le serveur attend que les
                 * clients se connectent,
                 * et dédie un thread à chacun d'entre eux afin de le gérer indépendamment des
                 * autres clients
                 */
                ConnexionClient cc = new ConnexionClient(idClient, soc);
                System.out.println("NOUVELLE CONNEXION - SOCKET => " + soc);
                idClient++;
                cc.start();
            }
        } catch (Exception e) {
        }
    }

}

//////////////////////////////////////////////////////////////////////////////////
/// CLASS serveurWorker /////
//////////////////////////////////////////////////////////////////////////////////

class serveurWorker extends Thread {

    private int port;
    private ServerSocket socket;
    private int idWorker;

    public serveurWorker(int port) {
        this.idWorker = 0;
        this.port = port;
        this.socket = new ServerSocket(port);
        System.out.println("SOCKET ECOUTE CREE => " + this.socket);
        System.out.println("Serveur Worker lancé sur le port " + port);
    }

    public void run() {
        while (idWorker < Serveur.MaxWorker) {
            /*
             * 2 - Attente d'une connexion client (la méthode s.accept() est bloquante
             * tant qu'un client ne se connecte pas)
             */
            // Socket soc = socket.accept();
            /*
             * 3 - Pour gérer plusieurs clients simultanément, le serveur attend que les
             * clients se connectent,
             * et dédie un thread à chacun d'entre eux afin de le gérer indépendamment des
             * autres clients
             */
            // ConnexionClient cc = new ConnexionClient(idClient, soc);
            // System.out.println("NOUVELLE CONNEXION - SOCKET => " + soc);
            idWorker++;
            // cc.start();
        }
    }

}

//////////////////////////////////////////////////////////////////////////////////
/// CLASS ConnexionClient /////
//////////////////////////////////////////////////////////////////////////////////

class ConnexionClient extends Thread {

    private int id;
    private String pseudo;
    // private boolean arret = false;
    private Socket s;
    private BufferedReader sisr;
    private PrintWriter sisw;

    public ConnexionClient(int id, Socket s) {
        this.id = id;
        this.s = s;
        // permet de lire par ligne
        try {
            sisr = new BufferedReader(new InputStreamReader(s.getInputStream()));
            sisw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
            
            String apercu= "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n";
            sisw.println(apercu);

           	 apercu = "Vous etes connectés au serveur !!\n";
           	 sisw.println(apercu);
        	apercu = "Veuiller suivre les instructions suivantes pour pouvoir communiquer avec le serveur\n";
        	sisw.println(apercu);
        	apercu = "Saisir :\n";
        	sisw.println(apercu);
        	apercu = "PERSISTANCE -----> Pour consulter la persistance d'un nombre\n";
        	sisw.println(apercu);
        	apercu = "LISTE AVEC GRANDE PERSISTANCE -----> Pour consulter la liste des nombres avec la plus grande persistance\n";
        	sisw.println(apercu);
        	apercu = "MOYENNE -----> Pour consulter la moyenne de la persistance \n";
        	sisw.println(apercu);
        	apercu = "MEDIANE -----> Pour consulter la mediane de la  persistance \n";

        	apercu = "OCCURRENCE -----> Pour consulter le nombre d'occurrence par valeur de persistance\n\n";
        	sisw.println(apercu);
        	
        	apercu = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n";
        	sisw.println(apercu);
        	apercu ="Entrer votre pseudo:";
            sisw.println(apercu);
            this.pseudo = sisr.readLine();
            sisw.println("Bienvenue, " + this.pseudo + "!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Serveur.pwClient[id] = sisw;
        // le client envoie son pseudo en premier
        // try {
        // ServeurMC.Clientpw[id].println("Entrer votre pseudo:");
        // this.pseudo = sisr.readLine();
        // ServeurMC.Clientpw[id].println("Bienvenue, " + this.pseudo + "!");
        // System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n\n");

        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    public void run() {
        try {

            String saisie;
            while ((saisie = sisr.readLine()) != null) {
                if (saisie.equals("END")) {
                    break;
                }

                // on envoi a tous
                // for(int i=0; i<ServeurMC.numClient; i++){
                // if (ServeurMC.pw[i]!=null && i!=id)
                // ServeurMC.pw[i].println(pseudo+"=>"+str); } // envoi à tous
            }

            sisr.close();
            sisw.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String s) {
        pseudo = s;
    }

    public BufferedReader getSisr() {
        return sisr;
    }

}

//////////////////////////////////////////////////////////////////////////////////
/// CLASS connexionWorker /////
//////////////////////////////////////////////////////////////////////////////////
