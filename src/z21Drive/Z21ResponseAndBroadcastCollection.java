package z21Drive;

import z21Drive.broadcasts.Z21BroadcastLanXLocoInfo;
import z21Drive.responses.Z21ResponseGetSerialNumber;

/**
 * Used as a collection of dummy objects for comparing responses and broadcasts with ones received from Z21.
 * Required because you can't override static variables -.-
 */
class Z21ResponseAndBroadcastCollection {
    public static final Z21BroadcastLanXLocoInfo lanXLocoInfo = new Z21BroadcastLanXLocoInfo(null);
    public static final Z21ResponseGetSerialNumber getSerialNumber = new Z21ResponseGetSerialNumber(null);
}
