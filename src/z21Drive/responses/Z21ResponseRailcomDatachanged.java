package z21Drive.responses;

import java.util.Arrays;
import java.util.logging.Logger;

public class Z21ResponseRailcomDatachanged extends Z21Response {

    public Z21ResponseRailcomDatachanged(byte[] initArray) {
        super(initArray);
        boundType = ResponseTypes.LAN_RAILCOM_DATACHANGED;
        if (byteRepresentation != null)
            populateFields();
    }

    private void populateFields(){
    	int nrOfDecoders = ((getByteRepresentation()[0] & 0xFF) - 4) / 13; 
    	System.out.println("Found " + nrOfDecoders);
    	for (int idx = 0; idx < nrOfDecoders; idx++) {
    		analyse(4 + idx * 13);
    	}
//        byte cvadr_MSB = byteRepresentation [6];
//        byte cvadr_LSB = byteRepresentation [7];
//        
//        cvAdr = ((cvadr_MSB & 0x3F) << 8 | cvadr_LSB) + 1; // 0 = CV1, ...
//        value = byteRepresentation [8] & 255;
    }

	private void analyse(int i) {
		Integer[] data = new Integer[13];
		for (int idx = i; idx < (i + 13); idx++) {
            System.out.print("0x" + String.format("%02X ", byteRepresentation[idx]));
            data[idx - i] = (int) ( byteRepresentation[idx] & 0xFF);
		}
		System.out.println();

		int locId = data[0] 
				    + (data[1]  << 8);
		int receiveCounter = data[2] 
             			   + (data[3] << 8)
							+ (data[4] << 16)
							+ (data[5] << 24);
		int errorCounter = data[6] & 0xFF
 			    + (data[7]<< 8)
				+ (data[8] << 16)
				+ (data[9]<< 24);
		int speed = data[10];
		int options = data[11];
		int temp = data[12];
		System.out.println("Id: " + locId + " Received: " + receiveCounter + " Error: " + errorCounter + " Speed: " + speed + " Options: " + options + " Temp: " + temp);
		// TODO Auto-generated method stub
		
	} 

//	public int getCVadr() {
//		return cvAdr;
//	}
//
//	public int getValue() {
//		return value;
//	}

}
