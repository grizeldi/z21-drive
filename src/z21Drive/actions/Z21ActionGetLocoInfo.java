package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

/**
 * Used to retrieve loco status from z21.
 * Supports loco addresses up to 63.
 */
public class Z21ActionGetLocoInfo extends Z21Action{

    /**
     * @param locoAddress Address of the loco to request info of.
     * @throws LocoAddressOutOfRangeException Thrown if loco address is out of supported range.
     */
    public Z21ActionGetLocoInfo(int locoAddress) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        if (locoAddress < 1 || locoAddress > 63)
            throw new LocoAddressOutOfRangeException(locoAddress);
        addDataToByteRepresentation(new Object[]{locoAddress});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        //Add all the data
        byteRepresentation.add(Byte.decode("0xE3"));
        byteRepresentation.add(Byte.decode("0xF0"));
        byte Adr_MSB = (Byte) objs[0];
        byte Adr_LSB = (byte)24;
        byteRepresentation.add(Adr_MSB);
        byteRepresentation.add(Adr_LSB);
        byteRepresentation.add((byte) (byteRepresentation.get(2) ^ byteRepresentation.get(3) ^ byteRepresentation.get(4) ^ byteRepresentation.get(5)));
    }
}
