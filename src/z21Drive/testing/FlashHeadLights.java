package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionGetLocoInfo;
import z21Drive.actions.Z21ActionSetLocoFunction;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.broadcasts.Z21BroadcastLanXLocoInfo;
import z21Drive.broadcasts.Z21BroadcastListener;

/**
 * Used to test if setLocoFunction works.
 * If it's working properly, loco keeps it's headlights flashing.
 * @see z21Drive.actions.Z21ActionSetLocoFunction
 * @author grizeldi
 */
public class FlashHeadLights implements Runnable{
    private boolean exit;

    public static void main(String [] args){
        new Thread(new FlashHeadLights()).start();
    }

    private FlashHeadLights(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                exit = true;
            }
        }).start();
        Z21.instance.addBroadcastListener(new Z21BroadcastListener() {
            @Override
            public void onBroadCast(BroadcastTypes type, Z21Broadcast broadcast) {
                if (type == BroadcastTypes.LAN_X_LOCO_INFO) {
                    Z21BroadcastLanXLocoInfo bc = (Z21BroadcastLanXLocoInfo) broadcast;
                    for (boolean b : bc.getFunctionsAsArray()){
                        System.out.print(b + " ");
                    }
                    System.out.print("\n");
                }
            }

            @Override
            public BroadcastTypes[] getListenerTypes() {
                return new BroadcastTypes[]{BroadcastTypes.LAN_X_LOCO_INFO};
            }
        });
        try {
            Z21.instance.sendActionToZ21(new Z21ActionGetLocoInfo(5));
        } catch (LocoAddressOutOfRangeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Z21 z21 = Z21.instance;
        while (!exit){
            try {
                z21.sendActionToZ21(new Z21ActionSetLocoFunction(5, 0, true));
                Thread.sleep(2000);
                z21.sendActionToZ21(new Z21ActionSetLocoFunction(5, 0, false));
                Thread.sleep(2000);
                System.out.println("boo!");
            } catch (LocoAddressOutOfRangeException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        z21.shutdown();
    }
}
