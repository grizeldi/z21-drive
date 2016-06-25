package z21Drive.responses;

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
