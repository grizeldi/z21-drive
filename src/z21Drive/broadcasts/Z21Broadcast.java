package z21Drive.broadcasts;

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
        String binaryString = String.format("%8s", Integer.toString(x, 2)).replace(' ', '0');
        if (binaryString.charAt(1) == '1')
            binaryString = binaryString.replace("-", "");
        char [] binary = binaryString.toCharArray();
        boolean bs[] = new boolean[8];
        for (int i = 0; i < binary.length; i++){
            if (binary[i] == '0')
                bs[i] = false;
            else if (binary[i] == '1')
                bs[i] = true;
        }
        return bs;
    }
}
