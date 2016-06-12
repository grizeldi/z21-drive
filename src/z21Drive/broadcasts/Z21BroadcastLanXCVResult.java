package z21Drive.broadcasts;

/**
 * Probably the most important broadcast, because it represents the current state of a loco.
 */
public class Z21BroadcastLanXCVResult extends Z21Broadcast{
	private int cvadr;
	private int value;
    

    public Z21BroadcastLanXCVResult(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_CV_RESULT;
        if (byteRepresentation != null)
            populateFields();
    }

    private void populateFields(){
        byte cvadr_MSB = byteRepresentation [6];
        byte cvadr_LSB = byteRepresentation [7];
        
        cvadr = ((cvadr_MSB & 0x3F) << 8 | cvadr_LSB) + 1; // 0 = CV1, ...
        value = (int) (byteRepresentation [8] & 255); 
    } 

	public int getCvadr() {
		return cvadr;
	}


	public int getValue() {
		return value;
	}

    
    

}
