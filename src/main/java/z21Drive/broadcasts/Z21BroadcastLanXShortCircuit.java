package z21Drive.broadcasts;

/**
 * Sent from z21 when a short circuit occurs somewhere on the layout. When this happens, z21
 * turns track power off and status LED starts blinking red.
 */
public class Z21BroadcastLanXShortCircuit extends Z21Broadcast{
	public Z21BroadcastLanXShortCircuit(byte[] initArray) {
		super(initArray);
		boundType = BroadcastTypes.LAN_X_SHORT_CIRCUIT;
	}
}
