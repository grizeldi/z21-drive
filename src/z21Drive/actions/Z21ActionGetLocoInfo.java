package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

/**
 * Used to retrieve loco status from z21.
 * Supports loco addresses up to 128.
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
        byteRepresentation.add((byte)(int)Integer.decode("0xE3"));
        byteRepresentation.add((byte)(int)Integer.decode("0xF0"));
        byte Adr_MSB;
        byte Adr_LSB;
        //Made working in attempt number 3
        String binary = String.format("%16s", Integer.toBinaryString((Integer) objs[0])).replace(' ', '0');
        String binaryMSB = binary.substring(0, 8);
        String binaryLSB = binary.substring(8);

        if (binary.replaceFirst ("^0*", "").toCharArray().length <= 8)
            Adr_MSB = 0;
        else
            Adr_MSB = (byte) Integer.parseInt(binaryMSB, 2);
        Adr_LSB = (byte) Integer.parseInt(binaryLSB, 2);

        System.out.println("Array length: " + binary.toCharArray().length);
        System.out.println("MSB: " + binaryMSB);
        System.out.println("LSB: " + binaryLSB);

        byteRepresentation.add(Adr_MSB);
        byteRepresentation.add(Adr_LSB);
        byteRepresentation.add((byte) (byteRepresentation.get(2) ^ byteRepresentation.get(3) ^ byteRepresentation.get(4) ^ byteRepresentation.get(5)));
    }
}
