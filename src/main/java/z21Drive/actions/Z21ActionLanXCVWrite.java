package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

/**
 *  Write a CV Value
 * 
 * @author sven
 *
 */
public class Z21ActionLanXCVWrite extends Z21Action{

    /**
     * @param cv The CV to read.
     * @throws LocoAddressOutOfRangeException Thrown if loco address is out of supported range.
     */
    public Z21ActionLanXCVWrite(int cv, int value) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        addDataToByteRepresentation(new Object[]{ cv, value});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        //Add all the data
        byteRepresentation.add(Byte.decode("0x24"));
        byteRepresentation.add(Byte.decode("0x12"));
        int cv = (int) objs[0] - 1; // 0 => CV1, ...
        int value = (int) objs[1];
        byteRepresentation.add((byte) (cv >> 8));
        byteRepresentation.add((byte) (cv & 0xFF));
        byteRepresentation.add((byte) (value & 0xFF));
        byteRepresentation.add((byte) (byteRepresentation.get(2) ^ byteRepresentation.get(3) ^ byteRepresentation.get(4) ^ byteRepresentation.get(5) ^ byteRepresentation.get(6)));
    }
}