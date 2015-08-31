package z21Drive;

import z21Drive.broadcasts.*;
import z21Drive.responses.Z21ResponseGetSerialNumber;

import javax.naming.OperationNotSupportedException;

/**
 * Used as a collection of dummy objects for comparing responses and broadcasts with ones received from Z21.
 * Required because you can't override static variables -.-
 */
class Z21ResponseAndBroadcastCollection {
    public static final Z21BroadcastLanXLocoInfo lanXLocoInfo = new Z21BroadcastLanXLocoInfo(null);
    public static final Z21BroadcastLanXUnknownCommand lanXUnknownCommand = new Z21BroadcastLanXUnknownCommand(null);
    public static final Z21BroadcastLanXTrackPowerOff lanXTrackPowerOff = new Z21BroadcastLanXTrackPowerOff(null);
    public static final Z21BroadcastLanXTrackPowerOn lanXTrackPowerOn = new Z21BroadcastLanXTrackPowerOn(null);

    public static final Z21ResponseGetSerialNumber getSerialNumber = new Z21ResponseGetSerialNumber(null);

    public Z21ResponseAndBroadcastCollection() throws OperationNotSupportedException{
        throw new OperationNotSupportedException("No objects of this type allowed.");
    }
}
