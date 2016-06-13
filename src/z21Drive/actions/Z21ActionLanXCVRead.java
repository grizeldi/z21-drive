package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

/**
 * Request a CV Value
 */
public class Z21ActionLanXCVRead extends Z21Action{

    /**
     * @param locoAddress Address of the loco to request info of.
     * @throws LocoAddressOutOfRangeException Thrown if loco address is out of supported range.
     */
    public Z21ActionLanXCVRead(int cv) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        addDataToByteRepresentation(new Object[]{ cv});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        //Add all the data
        byteRepresentation.add((byte)(int)Integer.decode("0x23"));
        byteRepresentation.add((byte)(int)Integer.decode("0x11"));
        int cv = (int) objs[0] - 1; // 0 => CV1, ...
        byteRepresentation.add((byte) (cv >> 8));
        byteRepresentation.add((byte) (cv & 255));
        byteRepresentation.add((byte) (byteRepresentation.get(2) ^ byteRepresentation.get(3) ^ byteRepresentation.get(4) ^ byteRepresentation.get(5)));
    }
}