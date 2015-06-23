package actions;

/**
 * Used to log off from z21 when shutting down the app.
 */
public class Z21ActionLanLogoff extends Z21Action{

    public Z21ActionLanLogoff(){
        byteRepresentation.add(Byte.decode("0"));
        byteRepresentation.add(Byte.decode("30"));
        addLenByte();
    }

    /**
     * Unnecessary here.
     * @param objs Make it null
     */
    @Override
    public void addDataToByteRepresentation(Object[] objs) {}
}
