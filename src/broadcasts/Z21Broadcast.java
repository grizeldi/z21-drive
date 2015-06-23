package broadcasts;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for Z21 broadcasts
 */
public abstract class Z21Broadcast {
    protected List<Byte> byteRepresentation = new ArrayList<Byte>();
    public BroadcastTypes boundType;

    public List<Byte> getByteRepresentation(){return byteRepresentation;}
}
