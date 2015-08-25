package z21Drive.broadcasts;

/**
 * Shipped from z21 if it receives an invalid command.
 * @see z21Drive.Z21
 */
public class Z21BroadcastLanXUnknownCommand extends Z21Broadcast{
    public Z21BroadcastLanXUnknownCommand(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_UNKNOWN_COMMAND;
    }
}
