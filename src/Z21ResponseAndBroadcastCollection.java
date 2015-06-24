import broadcasts.Z21BroadcastLanXLocoInfo;

/**
 * Used as a collection of dummy objects for comparing responses and broadcasts with ones received from Z21.
 * Required because you can't override static variables -.-
 */
class Z21ResponseAndBroadcastCollection {
    public static final Z21BroadcastLanXLocoInfo lanXLocoInfo = new Z21BroadcastLanXLocoInfo(null);
}
