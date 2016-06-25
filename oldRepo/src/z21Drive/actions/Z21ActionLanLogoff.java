package z21Drive.actions;

import java.util.Arrays;

/**
 * Used to log off from z21 when shutting down the app.
 */
public class Z21ActionLanLogoff extends Z21Action{
    //Made a static one here for better performance.
    private static Byte[] rep = new Byte[]{0x04, 0x00, 0x30, 0x00};

    public Z21ActionLanLogoff(){
        byteRepresentation = Arrays.asList(rep);
    }

    /**
     * Unnecessary here.
     * @param objs Make it null
     */
    @Override
    public void addDataToByteRepresentation(Object[] objs) {}
}
