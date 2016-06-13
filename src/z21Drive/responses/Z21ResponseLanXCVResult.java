package z21Drive.responses;

/**
 * Result for the Actoin_Lan_X_CV_Read
 */
public class Z21ResponseLanXCVResult extends Z21Response{
	private int cvadr;
	private int value;
    

    public Z21ResponseLanXCVResult(byte[] initArray) {
        super(initArray);
        boundType = ResponseTypes.LAN_X_CV_RESULT;
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
