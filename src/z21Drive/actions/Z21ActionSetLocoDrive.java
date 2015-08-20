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
     * @param direction Which direction the loco should go (1 = forward, 0 = backward).
     * @throws LocoAddressOutOfRangeException Thrown when loco address is too big or negative.
     */
    public Z21ActionSetLocoDrive(int locoAddress, byte speed, byte speedStepsID, boolean direction) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
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
    }
}
