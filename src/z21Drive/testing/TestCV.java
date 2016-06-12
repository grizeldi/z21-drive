package z21Drive.testing;

import java.util.ArrayList;
import java.util.Arrays;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionLanXCVRead;
import z21Drive.actions.Z21ActionLanXTrackPowerOn;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.broadcasts.Z21BroadcastLanXCVResult;
import z21Drive.broadcasts.Z21BroadcastListener;

/**
 * Reads the CV Number of the Loco 
 * @see z21Drive.Z21
 */
public class TestCV implements Runnable{

	ArrayList<Integer> cvs =  new ArrayList<Integer>(Arrays.asList(new Integer[]{ 1, 27, 28, 29, }));
    public static void main(String[] args) {
        //Start things up
        new Thread(new TestCV()).start();
    }

    public void run(){
        final Z21 z21 = Z21.instance;
        z21.addBroadcastListener(new Z21BroadcastListener() {
            @Override
            public void onBroadCast(BroadcastTypes type, Z21Broadcast broadcast) {
                if (type == BroadcastTypes.LAN_X_CV_RESULT){
                	Z21BroadcastLanXCVResult bc = (Z21BroadcastLanXCVResult) broadcast;
                    String o = Integer.toBinaryString(bc.getValue());
                    while (o.length() < 8) {
                    	o = "0" + o;
                    }
                    System.out.println(String.format("%3d: %3d %s", bc.getCvadr(), bc.getValue(), o));
                    sendNext(z21);
                } else if (type == BroadcastTypes.LAN_X_CV_NACK){
                	System.out.println("NACK");
                }
            }

            @Override
            public BroadcastTypes[] getListenerTypes() {
                return new BroadcastTypes[]{BroadcastTypes.LAN_X_CV_RESULT, BroadcastTypes.LAN_X_CV_NACK};
            }
        });
        sendNext(z21);
    }

	private void sendNext(Z21 z21) {
		if (cvs.size() == 0) {
			z21.sendActionToZ21(new Z21ActionLanXTrackPowerOn());
			return;
		}
		Integer cv = cvs.get(0);
		cvs.remove(0);
        try {
			z21.sendActionToZ21(new Z21ActionLanXCVRead(cv));
		} catch (LocoAddressOutOfRangeException e) {
			e.printStackTrace();
		}
	}
}
