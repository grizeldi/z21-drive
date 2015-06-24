package broadcasts;

/**
 * Probably the most important broadcast, because it represents the current state of a loco.
 */
public class Z21BroadcastLanXLocoInfo extends Z21Broadcast{

    public Z21BroadcastLanXLocoInfo(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_LOCO_INFO;
    }
}
