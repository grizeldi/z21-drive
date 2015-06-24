package responses;

/**
 * Base class for Z21 responses.
 */
public abstract class Z21Response {
    protected byte [] byteRepresentation;
    public ResponseTypes boundType;

    public Z21Response(byte [] initArray){
        byteRepresentation = initArray;
    }

    public byte[] getByteRepresentation() {
        return byteRepresentation;
    }
}
