package broadcasts;

/**
 * Base class for Z21 broadcasts.
 */
public abstract class Z21Broadcast {
    protected byte [] byteRepresentation;
    public BroadcastTypes boundType;

    public Z21Broadcast(byte [] initArray){
        byteRepresentation = initArray;
    }

    public byte [] getByteRepresentation(){return byteRepresentation;}
}
