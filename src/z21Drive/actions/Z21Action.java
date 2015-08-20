package z21Drive.actions;

import java.util.ArrayList;
import java.util.List;

public abstract class Z21Action {
    /**
     * Represents the message in z21 understandable form.
     */
    protected List<Byte> byteRepresentation = new ArrayList<Byte>();

    public List<Byte> getByteRepresentation(){
        return byteRepresentation;
    }

    /**
     * Here actual conversion to bytes happens
     * @param objs Whatever objects you might need to determine the bytes.
     */
    public abstract void addDataToByteRepresentation(Object [] objs);

    /**
     * Adds the required length of message bytes.
     */
    protected void addLenByte(){
        byte len = (byte) byteRepresentation.size();
        len += 2;
        byteRepresentation.add(0, (byte) 0);
        byteRepresentation.add(1, len);
    }
}
