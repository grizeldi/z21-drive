import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

public class Z21 {
    public static final Z21 instance = new Z21();
    private static final String host = "192.168.0.111";
    private static final int port = 21105;

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
}
