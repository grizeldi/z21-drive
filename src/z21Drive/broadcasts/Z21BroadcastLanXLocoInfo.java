package z21Drive.broadcasts;

/**
 * Probably the most important broadcast, because it represents the current state of a loco.
 */
public class Z21BroadcastLanXLocoInfo extends Z21Broadcast{
    private int locoAddress;
    private boolean locoInUse;
    private int speedSteps;
    /**
     * Represents direction in which loco is driving.
     * true = forward, false = backward
     */
    private boolean direction;
    private byte speed;
    private boolean f0On, f1On, f2On, f3On, f4On, f5On, f6On, f7On, f8On, f9On, f10On, f11On, f12On;

    public Z21BroadcastLanXLocoInfo(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_LOCO_INFO;
        if (byteRepresentation != null)
            populateFields();
    }

    private void populateFields(){
        byte adr_MSB = byteRepresentation [5];
        byte adr_LSB = byteRepresentation [6];
        locoAddress = (adr_MSB & 0x3F) << 8 + adr_LSB;

        boolean [] db2bits = fromByte(byteRepresentation[7]);
        locoInUse = db2bits[4];
        if (!db2bits[5] && !db2bits[6] && !db2bits[7])
            speedSteps = 14;
        if (!db2bits[5] && db2bits[6] && !db2bits[7])
            speedSteps = 28;
        if (db2bits[5] && !db2bits[6] && !db2bits[7])
            speedSteps = 128;

        boolean [] db3bits = fromByte(byteRepresentation[8]);
        direction = db3bits[0];
        boolean [] speedArray = db3bits.clone();
        speedArray [0] = false;
        speed = (byte)((speedArray[0]?1<<7:0) + (speedArray[1]?1<<6:0) + (speedArray[2]?1<<5:0) +
                (speedArray[3]?1<<4:0) + (speedArray[4]?1<<3:0) + (speedArray[5]?1<<2:0) +
                (speedArray[6]?1<<1:0) + (speedArray[7]?1:0));

        boolean [] db4bits = fromByte(byteRepresentation[9]);
        f0On = db4bits[3];
        f1On = db4bits[7];
        f2On = db4bits[6];
        f3On = db4bits[5];
        f4On = db4bits[4];
        boolean [] db5Bits = fromByte(byteRepresentation[10]);
        f5On = db5Bits[0];
        f6On = db5Bits[1];
        f7On = db5Bits[2];
        f8On = db5Bits[3];
        f9On = db5Bits[4];
        f10On = db5Bits[5];
        f11On = db5Bits[6];
        f12On = db5Bits[7];
    }


    //Getters for all locomotive properties
    public boolean isLocoInUse(){
        return locoInUse;
    }

    public int getLocoAddress() {
        return locoAddress;
    }

    public int getSpeedSteps() {
        return speedSteps;
    }

    public boolean getDirection() {
        return direction;
    }

    public byte getSpeed() {
        return speed;
    }

    public boolean isF0On() {
        return f0On;
    }

    public boolean isF1On() {
        return f1On;
    }

    public boolean isF2On() {
        return f2On;
    }

    public boolean isF3On() {
        return f3On;
    }

    public boolean isF4On() {
        return f4On;
    }

    public boolean isF5On() {
        return f5On;
    }

    public boolean isF6On() {
        return f6On;
    }

    public boolean isF7On() {
        return f7On;
    }

    public boolean isF8On() {
        return f8On;
    }

    public boolean isF9On() {
        return f9On;
    }

    public boolean isF10On() {
        return f10On;
    }

    public boolean isF11On() {
        return f11On;
    }

    public boolean isF12On() {
        return f12On;
    }

    /**
     * Array of all function values.
     * Because I was lazy it contains just functions from F0 to F4.
     * @return Array of function values.
     */
    public boolean [] getFunctionsAsArray(){
        boolean [] array = new boolean[5];
        array [0] = f0On;
        array [1] = f1On;
        array [2] = f2On;
        array [3] = f3On;
        array [4] = f4On;
        return array;
    }
}
