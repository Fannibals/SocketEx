package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.UUID;

public class UDPProvider {
    public static void main(String[] args) throws IOException {
        // 生成一份唯一标识
        String sn = UUID.randomUUID().toString();
        Provider provider= new Provider(sn);
        provider.start();

        // 读取任意键盘信息后exit
        System.in.read();
        provider.exit();

    }

    private static class Provider extends Thread {
        private final String sn;
        private boolean done = false;
        private DatagramSocket ds = null;

        public Provider(String sn) {
            super();
            this.sn = sn;
        }

        @Override
        public void run() {
            super.run();

            System.out.println("UDPProvider Started");

            try {
                // as a receiver, appoint a port for receiving data
                ds = new DatagramSocket(20000);

                while(!done){

                    // construct a receiving entity
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf,buf.length);

                    // receiving
                    ds.receive(receivePack);

                    // print out info
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int dataLen = receivePack.getLength();
                    String data = new String(receivePack.getData(),0,dataLen);

                    System.out.println("UDPProvider receive from ip:"
                            + ip + "\tport:"+ port+"\tdata:"+data);

                    // parse port number

                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1){
                        // construct a resp data
                        String responseData = "Receive data with len:"+dataLen;
                        byte[] responseDataBytes = responseData.getBytes();

                        //
                        DatagramPacket responsePacket = new DatagramPacket(
                                responseDataBytes,
                                responseDataBytes.length,
                                receivePack.getAddress(),
                                receivePack.getPort());

                        ds.send(responsePacket);
                    }
                }
            } catch (IOException e) {
                
            } finally {
                // finish
                System.out.println("UDPProvider Finished");
                close();
            }

        }

        private void close(){
            if (ds != null){
                ds.close();
                ds = null;
            }
        }

        void exit(){
            done = true;
            close();
        }
    }
}
