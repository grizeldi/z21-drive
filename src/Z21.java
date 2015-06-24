import actions.Z21Action;
import actions.Z21ActionLanLogoff;
import broadcasts.BroadcastTypes;
import broadcasts.Z21Broadcast;
import broadcasts.Z21BroadcastLanXLocoInfo;
import broadcasts.Z21BroadcastListener;
import responses.Z21Response;
import responses.Z21ResponseListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Main class in this library which represents Z21.
 */
public class Z21 implements Runnable{
    public static final Z21 instance = new Z21();
    private static final String host = "192.168.0.111";
    private static final int port = 21105;
    private boolean exit = false;
    private List<Z21ResponseListener> responseListeners = new ArrayList<Z21ResponseListener>();
    private List<Z21BroadcastListener> broadcastListeners = new ArrayList<Z21BroadcastListener>();
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
     * Also delivers packets to responseListeners.
     * @see Z21ResponseListener
     * @see Z21BroadcastListener
     */
    @Override
    public void run() {
        while (!exit){
            try {
                DatagramSocket socket = new DatagramSocket(port);
                DatagramPacket packet = new DatagramPacket(new byte [510], 510);
                socket.receive(packet);
                //Determine if it's a response or a broadcast
                if (PacketConverter.responseFromPacket(packet) != null){
                    Z21Response response = PacketConverter.responseFromPacket(packet);
                    //TODO deliver the response to listeners
                }else {
                    Z21Broadcast broadcast = PacketConverter.broadcastFromPacket(packet);
                    //Narrow the class definition
                    if (broadcast.boundType == Z21ResponseAndBroadcastCollection.lanXLocoInfo.boundType){
                        //It's a loco info broadcast
                        Z21BroadcastLanXLocoInfo z21BroadcastLanXLocoInfo = (Z21BroadcastLanXLocoInfo) broadcast;
                        //Deliver the broadcast
                        for (Z21BroadcastListener listener : broadcastListeners){
                            for (BroadcastTypes type : listener.getListenerTypes()){
                                if (type == BroadcastTypes.LAN_X_LOCO_INFO)
                                    listener.onBroadCast(BroadcastTypes.LAN_X_LOCO_INFO, z21BroadcastLanXLocoInfo);
                            }
                        }
                    }
                }
            }catch (IOException e){
                Logger.getLogger("Z21 Receiver").warning("Failed to get a message from z21... " + e);
            }
        }
    }

    public void addResponseListener(Z21ResponseListener listener){
        responseListeners.add(listener);
    }

    public void removeResponseListener(Z21ResponseListener listener){
        if (responseListeners.contains(listener))
            responseListeners.remove(listener);
    }

    public void addBroadcastListener(Z21BroadcastListener listener){
        broadcastListeners.add(listener);
    }

    public void removeBroadcastListener(Z21BroadcastListener listener){
        if (broadcastListeners.contains(listener))
            broadcastListeners.remove(listener);
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
        return new DatagramPacket(packetContent, action.getByteRepresentation().size());
    }

    /**
     * Here the magic of turning bytes into objects happens.
     * @param packet UDP packet received from Z21
     * @return Z21 response object which represents the byte array.
     */
    @Deprecated //Unfinished
    public static Z21Response responseFromPacket(DatagramPacket packet){
        byte [] array = packet.getData();
        byte header1 = array[2], header2 = array[3];
        return null;
    }

    /**
     * Same as for responses, but for broadcasts. See method responseFromPacket(DatagramPacket packet).
     * @param packet UDP packet received from Z21
     * @return Z21 broadcast object which represents the broadcast sent from Z21.
     */
    //TODO add more broadcast types
    public static Z21Broadcast broadcastFromPacket(DatagramPacket packet){
        byte [] data = packet.getData();
        byte header1 = data[2], header2 = data[3];
        if (header1 == 0 && header2 == 40){
            return new Z21BroadcastLanXLocoInfo(data);
        }
        return null;
    }

    /**
     * Unboxes Byte array to a primitive byte array.
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
