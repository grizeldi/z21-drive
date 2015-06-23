import actions.Z21Action;
import actions.Z21ActionLanLogoff;
import responses.ResponseTypes;
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
     * @param action Action to send.
     */
    public boolean sendActionToZ21(Z21Action action){
        DatagramPacket packet = PacketConverter.convert(action);
        try {
            InetAddress address = InetAddress.getByName(host);
            packet.setAddress(address);
            packet.setPort(port);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
        }catch (IOException e){
            Logger.getLogger("Z21 sender").warning("Failed to send message to z21... " + e);
            return false;
        }
        return true;
    }

    /**
     * Used as a listener for any packets sent by Z21.
     * Also delivers packets to listeners.
     */
    @Override
    public void run() {
        while (!exit){
            try {
                DatagramSocket socket = new DatagramSocket(port);
                DatagramPacket packet = new DatagramPacket(new byte [510], 510);
                socket.receive(packet);
            }catch (IOException e){
                Logger.getLogger("Z21 Receiver").warning("Failed to get a message from z21... " + e);
            }
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
        sendActionToZ21(new Z21ActionLanLogoff());
    }
}

class PacketConverter {
    public static DatagramPacket convert(Z21Action action){
        byte [] packetContent = toPrimitive((Byte [])action.getByteRepresentation().toArray());
        DatagramPacket packet = new DatagramPacket(packetContent, action.getByteRepresentation().size());
        return packet;
    }

    public static ResponseTypes fromPacket(byte [] packet){
        return null;
    }

    /**
     * WTF seriously???
     * @param in Byte array to primitivize
     * @return primitivized array
     */
    private static byte[] toPrimitive(Byte [] in){
        byte [] out = new byte [in.length];
        int i=0;
        for(Byte b: in)
            out[i++] = b.byteValue();
        return out;
    }
}
