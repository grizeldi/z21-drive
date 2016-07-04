package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

public class Z21ActionLanXCVPomWriteByte extends Z21Action{

    /**
     * Writing a CV on the MainTrack
     * 
     * @param cv The CV to read.
     * @param value Value
     * @param locoAddress the Adress of the Loco
     * @throws LocoAddressOutOfRangeException Thrown if loco address is out of supported range.
     */
    public Z21ActionLanXCVPomWriteByte(int locoAddress, int cv, int value) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        if (locoAddress < 1)
            throw new LocoAddressOutOfRangeException(locoAddress);
        addDataToByteRepresentation(new Object[]{ locoAddress, cv, value});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        //Add all the data
        byteRepresentation.add((byte) 0xE6); // X-Header
        byteRepresentation.add(Byte.decode("0x30")); // DB 0

        // Adding Loco-Addr
        byte Adr_MSB = (byte) (((Integer)objs[0]) >> 8);
        byte Adr_LSB = (byte) (((Integer)objs[0]) & 0b11111111);
        if (Adr_MSB != 0){
            Adr_MSB |= 0b11000000;
        }
        byteRepresentation.add(Adr_MSB);
        byteRepresentation.add(Adr_LSB);

        // Adding CV
        int cv = (int) objs[1] - 1; // 0 => CV1, ...
        int value = (int) objs[2];
        byteRepresentation.add((byte) (0xEC | cv >> 8 )); // DB3 
        byteRepresentation.add((byte) (cv & 0xFF)); // DB4
        byteRepresentation.add((byte) (value & 0xFF)); // DB5
        byteRepresentation.add((byte) (byteRepresentation.get(2) & 0xff ^ byteRepresentation.get(3) & 0xff^ byteRepresentation.get(4) & 0xff^ byteRepresentation.get(5) & 0xff^ byteRepresentation.get(6) & 0xff^ byteRepresentation.get(7) & 0xff^ byteRepresentation.get(8) & 0xff));
    }
}