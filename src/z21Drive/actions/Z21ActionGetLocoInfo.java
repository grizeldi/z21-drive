package z21Drive.actions;

@Deprecated //TODO unfinished
public class Z21ActionGetLocoInfo extends Z21Action{

    public Z21ActionGetLocoInfo(int locoAddress){
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        addDataToByteRepresentation(new Object[]{locoAddress});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        //Add all the data
        byteRepresentation.add(Byte.decode("0xE3"));
        byteRepresentation.add(Byte.decode("0xF0"));
        //TODO add loco address
    }
}
