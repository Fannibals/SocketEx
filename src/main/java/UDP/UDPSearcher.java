package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class UDPSearcher {

    private static final int LISTEN_PORT = 30000;
    public static void main(String[] args) throws IOException,InterruptedException {
        System.out.println("UDPSearcher Started");

        Listener listener = listen();
        sendBroadcast();

        System.in.read();

        List<Device> devices = listener.getDevicesAndClose();

        for (Device device : devices) {
            System.out.println(device.toString());
        }

        System.out.println("UDPSearcher Finished");


    }

    private static Listener listen() throws InterruptedException{
        System.out.println("UDPSearcher start listen");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT,countDownLatch);
        listener.start();

        countDownLatch.await();
        return listener;

    }

    private static void sendBroadcast() throws IOException{
        System.out.println("UDPSearcher sendBroadcast Started");

        // as a searcher, let sys to allocate
        DatagramSocket ds = new DatagramSocket();


        // construct a resp data
        String requestData = MessageCreator.buildWithPort(LISTEN_PORT);
        byte[] requestDataBytes = requestData.getBytes();

        //  construct a request datagram packet
        DatagramPacket requestPacket = new DatagramPacket(requestDataBytes,
                requestDataBytes.length);
        requestPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        requestPacket.setPort(20000);

        // send
        ds.send(requestPacket);

        // finish
        System.out.println("UDPProvider broadcast Finished");
        ds.close();
    }

    private static class Device {
        final int port;
        final String ip;
        final String sn;

        public Device(int port, String ip, String sn) {
            this.port = port;
            this.ip = ip;
            this.sn = sn;
        }

        @Override
        public String toString() {
            return "Device{" +
                    "port=" + port +
                    ", ip='" + ip + '\'' +
                    ", sn='" + sn + '\'' +
                    '}';
        }
    }

    private static class Listener extends Thread{

        private final int listenPort;
        private final CountDownLatch countDownLatch;
        private final List<Device> devices = new ArrayList<>();
        private boolean done;
        private DatagramSocket ds = null;

        public Listener(int listenPort, CountDownLatch countDownLatch) {
            super();
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();

            // ???
            countDownLatch.countDown();

            try {
                ds = new DatagramSocket(listenPort);

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

                    System.out.println("UDPSearcher receive from ip:"
                            + ip + "\tport:"+ port+"\tdata:"+data);

                    String sn = MessageCreator.parseSn(data);
                    if (sn != null) {
                        Device device = new Device(port,ip,sn);
                        devices.add(device);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // finish
                System.out.println("UDPSearcher Listener Finished");
                close();
            }
        }

        private void close(){
            if (ds != null){
                ds.close();
                ds = null;
            }
        }

        List<Device> getDevicesAndClose(){
            done = true;
            close();
            return devices;
        }
    }
}
