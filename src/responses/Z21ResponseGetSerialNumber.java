package responses;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Represents the response with serial number of z21.
 */
public class Z21ResponseGetSerialNumber extends Z21Response{
    public int serialNumber;

    public Z21ResponseGetSerialNumber(byte[] initArray) {
        super(initArray);
        boundType = ResponseTypes.LAN_GET_SERIAL_NUMBER_RESPONSE;

        if (byteRepresentation != null) {
            byte[] serialBytes = new byte[4];
            serialBytes[0] = byteRepresentation[4];
            serialBytes[1] = byteRepresentation[5];
            serialBytes[2] = byteRepresentation[6];
            serialBytes[3] = byteRepresentation[7];

            ByteBuffer buffer = ByteBuffer.wrap(serialBytes);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            serialNumber = buffer.getInt();
        }
    }
}
