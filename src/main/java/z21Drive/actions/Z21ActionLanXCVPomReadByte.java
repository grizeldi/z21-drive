package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

public class Z21ActionLanXCVPomReadByte extends Z21Action{

    /**
     * Reads a CV via RailCom on the MainTrack
     * 
     * @param cv The CV to read.
     * @param locoAddress the Adress of the Loco
     * @throws LocoAddressOutOfRangeException Thrown if loco address is out of supported range.
     */
    public Z21ActionLanXCVPomReadByte(int locoAddress, int cv) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        if (locoAddress < 1 || locoAddress > 63)
            throw new LocoAddressOutOfRangeException(locoAddress);
        addDataToByteRepresentation(new Object[]{ locoAddress, cv});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        //Add all the data
        byteRepresentation.add((byte) 0xE6); // X-Header
        byteRepresentation.add(Byte.decode("0x30")); // DB 0

        // Adding Loco-Addr
        byte Adr_MSB;
        byte Adr_LSB;
        String binary = String.format("%16s", Integer.toBinaryString((Integer) objs[0])).replace(' ', '0');
        String binaryMSB = binary.substring(0, 8);
        String binaryLSB = binary.substring(8);

        if (binary.replaceFirst ("^0*", "").toCharArray().length <= 8)
            Adr_MSB = 0;
        else
            Adr_MSB = (byte) Integer.parseInt(binaryMSB, 2);
        Adr_LSB = (byte) Integer.parseInt(binaryLSB, 2);
        byteRepresentation.add((byte) (Adr_MSB)); // DB 1
        byteRepresentation.add(Adr_LSB); // DB 2
        

        // Adding CV
        int cv = (int) objs[1] - 1; // 0 => CV1, ...
        byteRepresentation.add((byte) (0xE4 | cv >> 8 )); // DB3 
        byteRepresentation.add((byte) (cv & 0xFF)); // DB4
        byteRepresentation.add((byte) 0); // DB5
        byteRepresentation.add((byte) (byteRepresentation.get(2) & 0xff ^ byteRepresentation.get(3) & 0xff^ byteRepresentation.get(4) & 0xff^ byteRepresentation.get(5) & 0xff^ byteRepresentation.get(6) & 0xff^ byteRepresentation.get(7) & 0xff^ byteRepresentation.get(8) & 0xff));
    }
}