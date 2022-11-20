import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    Scanner in;
    PrintStream out;
    Main server;
    String nickName;

    public Client(Socket socket, Main server){
        this.server = server;
        this.socket = socket;
        new Thread(this).start();
    }

    void receive(String message){
        out.println(message);
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            in = new Scanner(is);
            out = new PrintStream(os);

            out.println("Please enter your nick name:");
            nickName = in.nextLine();
            out.println("Welcome to the chat, " + nickName + "!");
            out.println("Please type something below");
            String input = in.nextLine();
            while (!input.equals("bye")) {

                server.sendAll(nickName + ": " + input, this);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
