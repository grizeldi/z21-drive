package z21Drive.broadcasts;

import z21Drive.Z21;
import z21Drive.actions.Z21Action;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Used to register for specific broadcasts.
 * @author grizeldi
 */
public class BroadcastFlagHandler {
    private static boolean receiveGlobalBroadcasts = true, receiveAllLocos, receiveCentreStatus;

    public static void setReceive(BroadcastFlags flag, boolean receive){
        switch (flag){
            case GLOBAL_BROADCASTS:
                receiveGlobalBroadcasts = receive;
                break;
            case RECEIVE_ALL_LOCOS:
                receiveAllLocos = receive;
                break;
            case CENTRE_STATUS:
                receiveCentreStatus = receive;
                break;
        }
        //Send updated data to z21
        Z21 z21 = Z21.instance;
        z21.sendActionToZ21(new Z21ActionLanSetBroadcastFlags(receiveGlobalBroadcasts, receiveAllLocos, receiveCentreStatus));
    }

    public static boolean isReceiveGlobalBroadcasts() {
        return receiveGlobalBroadcasts;
    }

    public static boolean isReceiveAllLocos() {
        return receiveAllLocos;
    }

    public static boolean isReceiveCentreStatus() {
        return receiveCentreStatus;
    }
}

class Z21ActionLanSetBroadcastFlags extends Z21Action{
    public Z21ActionLanSetBroadcastFlags(boolean receiveGlobalBroadcasts, boolean receiveAllLocos, boolean receiveCentreStatus) {
        byteRepresentation.add((byte) 0x50);
        byteRepresentation.add((byte) 0x00);
        addDataToByteRepresentation(new Object[]{receiveGlobalBroadcasts, receiveAllLocos, receiveCentreStatus});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        int data = 0;
        if ((Boolean) objs[0]){
            data |= 0x00000001;
        }
        if ((Boolean) objs[1]){
            data |= 0x00010000;
        }
        if ((Boolean) objs[2]){
            data |= 0x00000100;
        }
        //Change order to little endian
        ByteBuffer buff = ByteBuffer.allocate(4);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putInt(data);
        buff.flip();
        for (byte i = 0; i < 4; i++){
            byteRepresentation.add(buff.get());
        }
    }
}
