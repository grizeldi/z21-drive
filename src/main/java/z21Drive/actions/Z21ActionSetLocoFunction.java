package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

/**
 * Sent to z21 to change a loco function. Supports functions from F0 to F12.
 * With some more work it could be made to allow use of more functions.
 * Supports loco addresses from 1 to 128.
 */
public class Z21ActionSetLocoFunction extends Z21Action{

    public Z21ActionSetLocoFunction(int locoAddress, int functionNo, boolean on) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        if (locoAddress < 1)
            throw new LocoAddressOutOfRangeException(locoAddress);
        addDataToByteRepresentation(new Object[]{Integer.valueOf(locoAddress), functionNo, Boolean.valueOf(on)});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        byteRepresentation.add((byte)0xE4);
        byteRepresentation.add((byte)0xF8);
        byte Adr_MSB = (byte) (((Integer)objs[0]) >> 8);
        byte Adr_LSB = (byte) (((Integer)objs[0]) & 0b11111111);
        if (Adr_MSB != 0){
            Adr_MSB |= 0b11000000;
        }
        byteRepresentation.add(Adr_MSB);
        byteRepresentation.add(Adr_LSB);

        //Generate data byte
        int dataByte;
        if ((Boolean) objs[2])
            dataByte = ((Integer) objs[1] & 63) | 128;
        else
            dataByte = (Integer) objs[1] & 63;

        byteRepresentation.add((byte)dataByte);

        //Add the XOR byte
        byte xor;
        xor = (byte) (byteRepresentation.get(2) ^ byteRepresentation.get(3) ^ byteRepresentation.get(4) ^ byteRepresentation.get(5) ^ byteRepresentation.get(6));
        byteRepresentation.add(xor);
    }
}
