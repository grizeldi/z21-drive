package z21Drive.testing;

import z21Drive.Z21;
import z21Drive.actions.Z21ActionGetSerialNumber;
import z21Drive.responses.ResponseTypes;
import z21Drive.responses.Z21Response;
import z21Drive.responses.Z21ResponseGetSerialNumber;
import z21Drive.responses.Z21ResponseListener;

/**
 * Send the get Serial Number Request
 * @see z21Drive.Z21
 */
public class Serialnumber implements Runnable{

	public static void main(String[] args) {
		//Start things up
		new Thread(new Serialnumber()).start();
	}

	public void run(){
		final Z21 z21 = Z21.instance;
		z21.addResponseListener(new Z21ResponseListener() {
			@Override
			public void responseReceived(ResponseTypes type, Z21Response response) {
				if (type != ResponseTypes.LAN_GET_SERIAL_NUMBER_RESPONSE){
					return;
				}
				Z21ResponseGetSerialNumber number = (Z21ResponseGetSerialNumber) response;
				System.out.println("Received Response: " + number.serialNumber);
			}

			@Override
			public ResponseTypes[] getListenerTypes() {
				return new ResponseTypes[]{ResponseTypes.LAN_GET_SERIAL_NUMBER_RESPONSE};
			};
		});
		z21.sendActionToZ21(new Z21ActionGetSerialNumber());
	}
}
