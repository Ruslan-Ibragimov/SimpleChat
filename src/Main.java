import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;

    Main() throws IOException {
        this.serverSocket = new ServerSocket(1234);

    }

    void sendAll(String message, Client sender){
        for (Client client : clients){
            if(!client.equals(sender))
                client.receive(message);
        }
    }

    public void run(){
        while (true) {
            System.out.println("Waiting...");

            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                clients.add(new Client(socket, this));

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}
