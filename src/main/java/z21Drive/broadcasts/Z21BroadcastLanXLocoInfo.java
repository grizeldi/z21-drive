package z21Drive.broadcasts;

/**
 * Probably the most important broadcast, because it represents the current state of a loco.
 * Supports up to 28 functions.
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
    private int speed;
    private boolean f0On, f1On, f2On, f3On, f4On, f5On, f6On, f7On, f8On, f9On, f10On, f11On, f12On, f13On,
            f14On, f15On, f16On, f17On, f18On, f19On, f20On, f21On, f22On, f23On, f24On, f25On, f26On, f27On, f28On;

    public Z21BroadcastLanXLocoInfo(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_LOCO_INFO;
        if (byteRepresentation != null)
            populateFields();
    }

    private void populateFields(){
        byte adr_MSB = byteRepresentation [5];
        byte adr_LSB = byteRepresentation [6];
        locoAddress = (adr_MSB & 0x3F) << 8 | adr_LSB;

        boolean [] db2bits = fromByte(byteRepresentation[7]);
        locoInUse = db2bits[4];
        String binary = String.format("%8s", Integer.toBinaryString(byteRepresentation[7])).replace(' ', '0');
        if (binary.equals("00000000") || binary.equals("00001000"))
            speedSteps = 14;
        else if (binary.equals("00000010") || binary.equals("00001010"))
            speedSteps = 28;
        else if (binary.equals("00000100") || binary.equals("00001100"))
            speedSteps = 128;

        boolean [] db3bits = fromByte(byteRepresentation[8]);
        direction = byteRepresentation[8] < 0;
        boolean [] speedArray = db3bits.clone();

        if (direction) {
            speedArray = fromByte((byte) (byteRepresentation[8] + 128));
        }
        speedArray [0] = false;
        speed = ((speedArray[0]?1<<7:0) + (speedArray[1]?1<<6:0) + (speedArray[2]?1<<5:0) +
                (speedArray[3]?1<<4:0) + (speedArray[4]?1<<3:0) + (speedArray[5]?1<<2:0) +
                (speedArray[6]?1<<1:0) + (speedArray[7]?1:0));

        //Set all functions.
        //Not really a good design choice having so many variables...
        //// FIXME: 19.2.2016 one day when I have too much time change this into an array
        boolean [] db4bits = fromByte(byteRepresentation[9]);
        f0On = db4bits[3];
        f1On = db4bits[7];
        f2On = db4bits[6];
        f3On = db4bits[5];
        f4On = db4bits[4];
        boolean [] db5bits = fromByte(byteRepresentation[10]);
        f5On = db5bits[0];
        f6On = db5bits[1];
        f7On = db5bits[2];
        f8On = db5bits[3];
        f9On = db5bits[4];
        f10On = db5bits[5];
        f11On = db5bits[6];
        f12On = db5bits[7];
        boolean [] db6bits = fromByte(byteRepresentation[11]);
        f13On = db6bits[0];
        f14On = db6bits[1];
        f15On = db6bits[2];
        f16On = db6bits[3];
        f17On = db6bits[4];
        f18On = db6bits[5];
        f19On = db6bits[6];
        f20On = db6bits[7];
        boolean [] db7bits = fromByte(byteRepresentation[12]);
        f21On = db7bits[0];
        f22On = db7bits[1];
        f23On = db7bits[2];
        f24On = db7bits[3];
        f25On = db7bits[4];
        f26On = db7bits[5];
        f27On = db7bits[6];
        f28On = db7bits[7];
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

    /**
     * Represents direction in which loco is driving.
     * true = forward, false = backward
     * @return boolean following those rules
     */
    public boolean getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    //Getters for all them functions
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

    public boolean isF13On() {
        return f13On;
    }

    public boolean isF14On() {
        return f14On;
    }

    public boolean isF15On() {
        return f15On;
    }

    public boolean isF16On() {
        return f16On;
    }

    public boolean isF17On() {
        return f17On;
    }

    public boolean isF18On() {
        return f18On;
    }

    public boolean isF19On() {
        return f19On;
    }

    public boolean isF20On() {
        return f20On;
    }

    public boolean isF21On() {
        return f21On;
    }

    public boolean isF22On() {
        return f22On;
    }

    public boolean isF23On() {
        return f23On;
    }

    public boolean isF24On() {
        return f24On;
    }

    public boolean isF25On() {
        return f25On;
    }

    public boolean isF26On() {
        return f26On;
    }

    public boolean isF27On() {
        return f27On;
    }

    public boolean isF28On() {
        return f28On;
    }

    /**
     * Array of functions F0 to F12. I was too lazy to add all other functions.
     * @return Array of function values.
     */
    public boolean [] getFunctionsAsArray(){
        boolean [] array = new boolean[13];
        array [0] = f0On;
        array [1] = f1On;
        array [2] = f2On;
        array [3] = f3On;
        array [4] = f4On;
        array [5] = f5On;
        array [6] = f6On;
        array [7] = f7On;
        array [8] = f8On;
        array [9] = f9On;
        array [10] = f10On;
        array [11] = f11On;
        array [12] = f12On;
        return array;
    }
}
