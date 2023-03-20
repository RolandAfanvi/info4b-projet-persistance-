import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

//////////////////////////////////////////////////////////////////////////////////
///                             CLASS PRINCIPALE                             /////
//////////////////////////////////////////////////////////////////////////////////

public class Client {
    static boolean arreter = false;
    static int port = 8080;
    static String ip = "127.0.0.1";

    public static void main(String args[]) throws IOException {
        if (args.length != 0) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }

        Socket socket = new Socket(ip, port);

        BufferedReader sisr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter sisw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        while ((sisr.readLine())!=null) {
            System.out.println(sisr.readLine());}

        GererSaisie saisie = new GererSaisie(sisw);
        saisie.run();

        String str;
        try {
            while (arreter != true) {
                str = sisr.readLine();
                System.out.println(str);
            }

            System.out.println("Vous etes deconnecté");

            sisr.close();
            sisw.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

//////////////////////////////////////////////////////////////////////////////////
/// CLASS GERERSAISIE /////
//////////////////////////////////////////////////////////////////////////////////

class GererSaisie extends Thread {
    private BufferedReader entreeClavier;
    private PrintWriter pw;

    public GererSaisie(PrintWriter pw) {
        entreeClavier = new BufferedReader(new InputStreamReader(System.in));
        this.pw = pw;
    }

    public void run() {
        String str;
        try {
            while (!(str = entreeClavier.readLine()).equals("END")) {
                pw.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Client.arreter = true;
    }
}


//////////////////////////////////////////////////////////////////////////////////
/// CLASS COMMANDE /////
//////////////////////////////////////////////////////////////////////////////////


enum Commande implements Serializable {
    ListWorker("LIST WORKER"),
    Mediane("MEDIANE"),
    NombreDoccurence("NB CONCURENCE"),
    Vide("") ; 

    public String texte = "";
    
    private Commande(String texte) {
        this.texte = texte;
    }
}




//////////////////////////////////////////////////////////////////////////////////
/// CLASS Message /////
//////////////////////////////////////////////////////////////////////////////////

class Message implements Serializable {

    public  int idClient  ; 
    public Commande c ; 
    Message(int id , Commande c )  
    {
        this.idClient  =  id ; 
        this.c = c  ; 
    }





    public String toString()
    {
        return " Le Client "+this.idClient + "fait la demande "+ this.c   ; 
    }


    
}









