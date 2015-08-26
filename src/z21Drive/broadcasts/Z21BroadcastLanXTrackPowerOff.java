package z21Drive.broadcasts;

/**
 * Sent from z21 when some client switches track power off or the button on z21 is pressed.
 */
public class Z21BroadcastLanXTrackPowerOff extends Z21Broadcast{
    public Z21BroadcastLanXTrackPowerOff(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_TRACK_POWER_OFF;
    }
}
