package responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a response from z21.
 */
public abstract class Z21Response {
    protected List<Byte> byteRepresentation = new ArrayList<Byte>();

    public List<Byte> getByteRepresentation() {
        return byteRepresentation;
    }
}
