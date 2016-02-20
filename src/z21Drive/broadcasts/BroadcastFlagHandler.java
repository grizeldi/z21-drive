package z21Drive.broadcasts;

import z21Drive.Z21;
import z21Drive.actions.Z21Action;

/**
 * Used to register for specific broadcasts.
 * @author grizeldi
 */
public class BroadcastFlagHandler {
    public static boolean receiveGlobalBroadcasts, receiveAllLocos;

    public static void setReceive(BroadcastFlags flag, boolean receive){
        switch (flag){
            case GLOBAL_BROADCASTS:
                receiveGlobalBroadcasts = receive;
                break;
            case RECEIVE_ALL_LOCOS:
                receiveAllLocos = receive;
                break;
        }
        //Send updated data to z21
        Z21 z21 = Z21.instance;
        z21.sendActionToZ21(new Z21ActionLanSetBroadcastFlags(receiveGlobalBroadcasts, receiveAllLocos));
    }
}

class Z21ActionLanSetBroadcastFlags extends Z21Action{
    public Z21ActionLanSetBroadcastFlags(boolean receiveGlobalBroadcasts, boolean receiveAllLocos) {
        byteRepresentation.add((byte) 0x50);
        byteRepresentation.add((byte) 0x00);
        addDataToByteRepresentation(new Object[]{receiveGlobalBroadcasts, receiveAllLocos});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {

    }
}
