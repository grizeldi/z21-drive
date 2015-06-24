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

    /**
     * Converts byte to bits
     * @param x byte to convert
     * @return array of bits
     */
    protected boolean [] fromByte(byte x){
        boolean bs[] = new boolean[8];
        bs[0] = ((x & 0x01) != 0);
        bs[1] = ((x & 0x02) != 0);
        bs[2] = ((x & 0x04) != 0);
        bs[3] = ((x & 0x08) != 0);
        bs[4] = ((x & 0x16) != 0);
        bs[5] = ((x & 0x32) != 0);
        bs[6] = ((x & 0x64) != 0);
        bs[7] = ((x & 0x128) != 0);
        return bs;
    }
}
