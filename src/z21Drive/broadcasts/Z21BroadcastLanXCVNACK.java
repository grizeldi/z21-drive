package z21Drive.broadcasts;

/**
 * Sent from z21 when some client switches track power off or the button on z21 is pressed.
 */
public class Z21BroadcastLanXCVNACK extends Z21Broadcast{
    public Z21BroadcastLanXCVNACK(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_CV_NACK;
    }
}
