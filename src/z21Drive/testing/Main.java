package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionGetLocoInfo;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.broadcasts.Z21BroadcastLanXLocoInfo;
import z21Drive.broadcasts.Z21BroadcastListener;

public class Main implements Runnable{

    public static void main(String[] args) {
        //Start things up
        new Thread(new Main()).start();
    }

    public void run(){
        //TODO add run code
        //THIS IS NOT HERE, OK?
        /*boolean [] bool = new boolean[]{true, true, true, true, true, true, true, true};
        int b = ((bool[0]?1<<7:0) + (bool[1]?1<<6:0) + (bool[2]?1<<5:0) +
                (bool[3]?1<<4:0) + (bool[4]?1<<3:0) + (bool[5]?1<<2:0) +
                (bool[6]?1<<1:0) + (bool[7]?1:0));
        System.out.println(b);*/

        /*byte Adr_MSB = 10, Adr_LSB = -1;
        System.out.println((Adr_MSB & 0x3F) << (8 + Adr_LSB));

        int x = 0;
        Adr_MSB = (byte)x;
        Adr_LSB = (byte)24;
        System.out.println((Adr_MSB & 0x3F) << (8 + Adr_LSB));*/

        final Z21 z21 = Z21.instance;
        z21.addBroadcastListener(new Z21BroadcastListener() {
            @Override
            public void onBroadCast(BroadcastTypes type, Z21Broadcast broadcast) {
                if (type == BroadcastTypes.LAN_X_LOCO_INFO){
                    Z21BroadcastLanXLocoInfo bc = (Z21BroadcastLanXLocoInfo) broadcast;
                    System.out.println("Loco address: " + bc.getLocoAddress());
                    System.out.println("Lights : " + bc.isF1On());
                    System.out.println("Speed steps : " + bc.getSpeedSteps());
                    System.out.println("Speed : " + bc.getSpeed());
                    System.out.println("Raw data:");
                    for (byte b : bc.getByteRepresentation())
                        System.out.print(b + " ");
                    System.out.println("Array length: " + bc.getByteRepresentation().length);
                }
            }

            @Override
            public BroadcastTypes[] getListenerTypes() {
                return new BroadcastTypes[]{BroadcastTypes.LAN_X_LOCO_INFO};
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        z21.sendActionToZ21(new Z21ActionGetLocoInfo(5));
                        Thread.sleep(3000);
                    } catch (LocoAddressOutOfRangeException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
