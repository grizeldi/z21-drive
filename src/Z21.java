import responses.Z21ResponseListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Z21 implements Runnable{
    public static final Z21 instance = new Z21();
    private static final String host = "192.168.0.111";
    private static final int port = 21105;
    private boolean exit = false;
    private List<Z21ResponseListener> listeners = new ArrayList<Z21ResponseListener>();
    private Thread listenerThread;

    public Z21(){
        listenerThread = new Thread(this);
        listenerThread.start();
    }

    /**
     * Used to send the packet to z21.
     * @param packet Whatever bytes to send.
     */
    public void sendPacketToZ21(DatagramPacket packet){
        try {
            InetAddress address = InetAddress.getByName(host);
            packet.setAddress(address);
            packet.setPort(port);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
        }catch (IOException e){
            Logger.getLogger("Z21 sender").warning("Failed to send message to z21... " + e);
        }
    }

    /**
     * Used as a listener for any packets sent by Z21.
     * Also delivers packets to listeners.
     */
    @Override
    public void run() {
        while (!exit){
            //TODO implement listener thread
        }
    }

    public void addResponseListener(Z21ResponseListener listener){
        listeners.add(listener);
    }

    public void removeResponseListener(Z21ResponseListener listener){
        if (listeners.contains(listener))
            listeners.remove(listener);
    }

    /**
     * Used to gracefully stop all communications.
     */
    public void shutdown(){
        exit = true;
        listenerThread.interrupt();
        //TODO send the LAN_X_LOGOFF packet
    }
}
