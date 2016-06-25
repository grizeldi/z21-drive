package z21Drive.broadcasts;

/**
 * Sent from z21 when some client switches track power on or the button on z21 is pressed.
 */
public class Z21BroadcastLanXTrackPowerOn extends Z21Broadcast{
    public Z21BroadcastLanXTrackPowerOn(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_TRACK_POWER_ON;
    }
}
