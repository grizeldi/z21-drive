package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

/**
 * Sent to z21 to make a loco move. Supports loco addresses up to 63.
 */
public class Z21ActionSetLocoDrive extends Z21Action{
    /**
     * Default constructor for this.
     * @param locoAddress Address of the loco that this action targets.
     * @param speed New speed value.
     * @param speedStepsID How many speed steps loco has (IDs: 0 = 14 speed steps, 2 = 28 speed steps, 3 = 128 speed steps).
     * @param direction Which direction the loco should go (true = forward, false = backward).
     * @throws LocoAddressOutOfRangeException Thrown when loco address is too big or negative.
     */
    public Z21ActionSetLocoDrive(int locoAddress, byte speed, byte speedStepsID, boolean direction) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        if (locoAddress < 1 || locoAddress > 63)
            throw new LocoAddressOutOfRangeException(locoAddress);
        addDataToByteRepresentation(new Object[]{locoAddress, speed, speedStepsID, direction});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        byteRepresentation.add(Byte.decode("0xE4"));
        switch ((Byte) objs[2]){
            case 0:
                byteRepresentation.add((byte)16);
                break;
            case 2:
                byteRepresentation.add((byte)18);
                break;
            case 3:
                byteRepresentation.add((byte)19);
                break;
            default:
                System.err.println("Constructing new SetLocoDrive action: Unknown speed step ID");
        }
        byte Adr_MSB = (Byte) objs[0];
        byte Adr_LSB = (byte)24;
        byteRepresentation.add(Adr_MSB);
        byteRepresentation.add(Adr_LSB);
        
        boolean [] speedAndDirection = fromByte((Byte) objs[1]);
        speedAndDirection[0] = (Boolean) objs[3];
        byteRepresentation.add((byte)((speedAndDirection[0]?1<<7:0) + (speedAndDirection[1]?1<<6:0) + (speedAndDirection[2]?1<<5:0) +
                (speedAndDirection[3]?1<<4:0) + (speedAndDirection[4]?1<<3:0) + (speedAndDirection[5]?1<<2:0) +
                (speedAndDirection[6]?1<<1:0) + (speedAndDirection[7]?1:0)));
        byteRepresentation.add((byte) (byteRepresentation.get(2) ^ byteRepresentation.get(3)
                ^ byteRepresentation.get(4) ^ byteRepresentation.get(5) ^ byteRepresentation.get(6) ^ byteRepresentation.get(7)));
    }

    private boolean [] fromByte(byte x){
        boolean bs[] = new boolean[8];
        bs[0] = ((x & 0x01) != 0);
        bs[1] = ((x & 0x02) != 0);
        bs[2] = ((x & 0x04) != 0);
        bs[3] = ((x & 0x08) != 0);
        bs[4] = ((x & 0x16) != 0);
        bs[5] = ((x & 0x32) != 0);
        bs[6] = ((x & 0x64) != 0);
        bs[7] = ((x & 0x128) != 0);
        return bs;
    }
}
