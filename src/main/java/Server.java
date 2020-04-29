import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(2000);

            System.out.println("The Server has prepared");
            System.out.println("Server info: " + server.getInetAddress() + ":" + server.getLocalPort());

            for (;;){
                Socket client = server.accept();

                ClientHandler handler = new ClientHandler(client);

                handler.start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("New Client info: " + socket.getInetAddress() + ":" + socket.getPort());

            try {
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                do {
                    String str = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(str)){
                        flag = false;
                        socketOutput.println("bye");
                    }else{
                        System.out.println(str);
                        socketOutput.println("回送："+ str.length());
                    }
                }while(flag);

                socketInput.close();
                socketOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Client has closed " + socket.getInetAddress() + ":" + socket.getPort());

        }
    }
}
