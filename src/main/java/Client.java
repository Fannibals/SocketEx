import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.Buffer;

public class Client {
    public static void main(String[] args) {
        Socket socket = new Socket();

        try {
            socket.setSoTimeout(3000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),2000),3000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("The Client has initiated a connection");
        System.out.println("Client info: " + socket.getLocalAddress() + ":" + socket.getLocalPort());
        System.out.println("Server info: " + socket.getInetAddress() + ":" + socket.getPort());

        try {
            todo(socket);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client has closed");

    }

    private static void todo(Socket socket){
        // construct input stream
        InputStream is = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(is));

        try {
            // get output stream
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);

            // get socket input stream
            InputStream socketIs = socket.getInputStream();
            BufferedReader socketBR = new BufferedReader(new InputStreamReader(socketIs));


            boolean flag = true;
            do {
                // read one line from keyboard
                String str = input.readLine();
                ps.println(str);

                // from server
                String echo = socketBR.readLine();
                if ("bye".equalsIgnoreCase(echo)){
                    flag = false;
                }else{
                    System.out.println(echo);
                }
            }while (flag);

            socketIs.close();
            socketBR.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
