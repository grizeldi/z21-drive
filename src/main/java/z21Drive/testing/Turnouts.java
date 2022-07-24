package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionGetLanXSetTurnout;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.broadcasts.Z21BroadcastLanXTurnoutsInfo;
import z21Drive.broadcasts.Z21BroadcastListener;

/**
 * Sends request for info of loco #5 and then keeps printing any changes.
 * @see z21Drive.Z21
 */
public class Turnouts implements Runnable{
    boolean finished;
    int address;

    public static void main(String[] args) {
        //Start things up
        new Thread(new Turnouts(1)).start();
    }

    public Turnouts(int address) {
        this.address = address;
    }

    public void run(){
        final Z21 z21 = Z21.instance;
        z21.addBroadcastListener(new Z21BroadcastListener() {
            @Override
            public void onBroadCast(BroadcastTypes type, Z21Broadcast broadcast) {
                if (type == BroadcastTypes.LAN_X_GET_TURNOUT_INFO){
                	
                	
                    Z21BroadcastLanXTurnoutsInfo t = (Z21BroadcastLanXTurnoutsInfo) broadcast;
                    System.out.println("Turnout address: " + t.getTurnoutAddress());
                    System.out.println("Position: " + t.getPosition());
                    finished = true;
                }
            }

            @Override
            public BroadcastTypes[] getListenerTypes() {
                return new BroadcastTypes[]{BroadcastTypes.LAN_X_GET_TURNOUT_INFO};
            }
        });

        
        try {
            z21.sendActionToZ21(new Z21ActionGetLanXSetTurnout(address, (byte) 1, true));
            Thread.sleep(150);
            z21.sendActionToZ21(new Z21ActionGetLanXSetTurnout(address, (byte) 1, false));
            
            Thread.sleep(1500);
            
            
            z21.sendActionToZ21(new Z21ActionGetLanXSetTurnout(address, (byte) 0, true));
            Thread.sleep(150);
            z21.sendActionToZ21(new Z21ActionGetLanXSetTurnout(address, (byte) 0, false));

            
            
        } catch (LocoAddressOutOfRangeException | InterruptedException e) {
            e.printStackTrace();
        }
        
        while (!finished){}
        
        System.out.println("shuddown");
        z21.shutdown();
    }
}
