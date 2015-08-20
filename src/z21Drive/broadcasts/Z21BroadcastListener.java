package z21Drive.broadcasts;

public interface Z21BroadcastListener {
    void onBroadCast(BroadcastTypes type, Z21Broadcast broadcast);
    BroadcastTypes[] getListenerTypes();
}
