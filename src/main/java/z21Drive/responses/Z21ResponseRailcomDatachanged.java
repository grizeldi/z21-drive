package z21Drive.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Handels a RailCom-Response
 * @author sven
 *
 */
public class Z21ResponseRailcomDatachanged extends Z21Response {

	private List<RailComData> data = new ArrayList<RailComData>();
	
    public Z21ResponseRailcomDatachanged(byte[] initArray) {
        super(initArray);
        boundType = ResponseTypes.LAN_RAILCOM_DATACHANGED;
        if (byteRepresentation != null)
            populateFields();
    }

    private void populateFields(){
    	int nrOfDecoders = ((getByteRepresentation()[0] & 0xFF) - 4) / 13; 
    	for (int idx = 0; idx < nrOfDecoders; idx++) {
    		data.add(analyse(4 + idx * 13));
    	}
    }

	private RailComData analyse(int i) {
		Integer[] data = new Integer[13];
		for (int idx = i; idx < (i + 13); idx++) {
//            System.out.print("0x" + String.format("%02X ", byteRepresentation[idx]));
            data[idx - i] = (int) ( byteRepresentation[idx] & 0xFF);
		}

		int locId = data[0] 
				    + (data[1]  << 8);
		int receiveCounter = data[2] 
             			   + (data[3] << 8)
							+ (data[4] << 16)
							+ (data[5] << 24);
		int errorCounter = data[6] 
 			    + (data[7]<< 8)
				+ (data[8] << 16)
				+ (data[9]<< 24);
		int speed = data[10];
		int options = data[11];
		int temp = data[12];
		return new RailComData(locId, receiveCounter, errorCounter, speed, options, temp);
	} 

	public class RailComData {
		public RailComData(int locId, int receiveCounter, int errorCounter, int speed, int options, int temp) {
			super();
			this.locId = locId;
			this.receiveCounter = receiveCounter;
			this.errorCounter = errorCounter;
			this.speed = speed;
			this.options = options;
			this.temp = temp;
		}
		private int locId;
		private int receiveCounter;
		private int errorCounter;
		private int speed;
		private int options;
		private int temp;
		public int getLocId() {
			return locId;
		}
		public int getReceiveCounter() {
			return receiveCounter;
		}
		public int getErrorCounter() {
			return errorCounter;
		}
		public int getSpeed() {
			return speed;
		}
		public int getOptions() {
			return options;
		}
		public int getTemp() {
			return temp;
		}
		
		@Override
		public String toString() {
			return "RailComData [locId=" + locId + ", receiveCounter=" + receiveCounter + ", errorCounter="
					+ errorCounter + ", speed=" + speed + ", options=" + options + ", temp=" + temp + "]";
		}
		
	}

}
