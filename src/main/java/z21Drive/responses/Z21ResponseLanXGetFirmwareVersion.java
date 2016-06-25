package z21Drive.responses;

/**
 * Received as a response to Z21ActionLanXGetFIrmwareVersion
 * Weird display of firmware versions. Check out official documentation for more info.
 * @see z21Drive.actions.Z21ActionLanXGetFirmwareVersion
 */
public class Z21ResponseLanXGetFirmwareVersion extends Z21Response{
    private int firmwareVersion;

    public Z21ResponseLanXGetFirmwareVersion(byte[] initArray) {
        super(initArray);
        if (initArray != null) {
            firmwareVersion = byteRepresentation[6] << 8 | byteRepresentation[7];
        }
    }

    public int getFirmwareVersion() {
        return firmwareVersion;
    }
}
