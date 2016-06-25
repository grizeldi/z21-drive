package z21Drive.responses;

/**
 * No response from the loco-decoder.
 * Programming/reading was unsuccessful.
 */
public class Z21ResponseLanXCVNACK extends Z21Response{
    public Z21ResponseLanXCVNACK(byte[] initArray) {
        super(initArray);
        boundType = ResponseTypes.LAN_X_CV_NACK;
    }
}
