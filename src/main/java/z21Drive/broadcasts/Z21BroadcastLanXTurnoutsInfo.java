package z21Drive.broadcasts;

/**
 * It represents the state of a turnout.
 */
public class Z21BroadcastLanXTurnoutsInfo extends Z21Broadcast{
    private int turnoutAddres;
    private int position;

    public Z21BroadcastLanXTurnoutsInfo(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_GET_TURNOUT_INFO;
        if (byteRepresentation != null)
            populateFields();
    }

    private void populateFields(){
        
    	byte adr_MSB = byteRepresentation [5];
        byte adr_LSB = byteRepresentation [6];
        
        turnoutAddres = (adr_MSB & 0x3F) << 8 | adr_LSB;
        position =  byteRepresentation [7] & 0x03;
    }

    public int getTurnoutAddress()
    {
    	return turnoutAddres;
    }
    
    public int getPosition()
    {
    	return position;
    }
}
