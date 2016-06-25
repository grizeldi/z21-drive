package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionGetLocoInfo;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.broadcasts.Z21BroadcastLanXLocoInfo;
import z21Drive.broadcasts.Z21BroadcastListener;

/**
 * Sends request for info of loco #5 and then keeps printing any changes.
 * @see z21Drive.Z21
 */
public class PrintInfo implements Runnable{
    boolean finished;
    int locoAdress;

    public static void main(String[] args) {
        //Start things up
        new Thread(new PrintInfo(Integer.parseInt(args[0]))).start();
    }

    public PrintInfo(int locoAdress) {
        this.locoAdress = locoAdress;
    }

    public void run(){
        final Z21 z21 = Z21.instance;
        z21.addBroadcastListener(new Z21BroadcastListener() {
            @Override
            public void onBroadCast(BroadcastTypes type, Z21Broadcast broadcast) {
                if (type == BroadcastTypes.LAN_X_LOCO_INFO){
                    Z21BroadcastLanXLocoInfo bc = (Z21BroadcastLanXLocoInfo) broadcast;
                    System.out.println("Loco address: " + bc.getLocoAddress());
                    System.out.println("Lights: " + bc.isF0On());
                    System.out.println("Speed steps: " + bc.getSpeedSteps());
                    System.out.println("Direction: " + bc.getDirection());
                    System.out.println("Speed: " + bc.getSpeed());
                    System.out.println("Raw data:");
                    for (byte b : bc.getByteRepresentation())
                        System.out.print(b + " ");
                    System.out.print("\n");
                    System.out.println("Array length: " + bc.getByteRepresentation().length);
                    finished = true;
                }
            }

            @Override
            public BroadcastTypes[] getListenerTypes() {
                return new BroadcastTypes[]{BroadcastTypes.LAN_X_LOCO_INFO};
            }
        });
        try {
            z21.sendActionToZ21(new Z21ActionGetLocoInfo(locoAdress));
        } catch (LocoAddressOutOfRangeException e) {
            e.printStackTrace();
        }
        while (!finished){}
    }
}
