package broadcasts;

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
    private boolean f0On, f1On, f2On, f3On, f4On;

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
        //TODO add speed

        boolean [] db4bits = fromByte(byteRepresentation[9]);
        f0On = db4bits[3];
        f1On = db4bits[7];
        f2On = db4bits[6];
        f3On = db4bits[5];
        f4On = db4bits[4];
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
