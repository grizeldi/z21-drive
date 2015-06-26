package actions;

/**
 * Mostly used to poll the keepAlive timer, but feel free to use it if you really want to know
 * what is the serial number of your z21...
 */
public class Z21ActionGetSerialNumber extends Z21Action{

    public Z21ActionGetSerialNumber(){
        byteRepresentation.add((byte) 0x00);
        byteRepresentation.add((byte) 0x10);
        addLenByte();
    }

    //Not necessary here
    @Override
    public void addDataToByteRepresentation(Object[] objs) {}
}
