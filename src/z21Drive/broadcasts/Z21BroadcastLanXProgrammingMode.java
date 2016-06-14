package z21Drive.broadcasts;

/**
 * Received when z21 enters programming mode.
 */
public class Z21BroadcastLanXProgrammingMode extends Z21Broadcast{
    public Z21BroadcastLanXProgrammingMode(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_PROGRAMMING_MODE;
    }
}
