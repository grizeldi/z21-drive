package z21Drive.broadcasts;

/**
 * All broadcast flags out there.
 * LocoNet broadcasts aren't implemented, because author doesn't have black z21.
 * @author grizeldi
 */
public enum BroadcastFlags {
    /**
     * If true z21 will send information for all changed locos to this library.
     * Overflows network with UDP packets very easily, so enable only when needed.
     * @see java.net.DatagramPacket
     */
    RECEIVE_ALL_LOCOS,
    /**
     * If this is false z21 won't send any information about changed locos, switches, short circuits etc.
     */
    GLOBAL_BROADCASTS,
    /**
     * If this is true z21 sends data about voltage, current temperature etc. to your app. Only enable it if you really need it.
     */
    CENTRE_STATUS;
}
