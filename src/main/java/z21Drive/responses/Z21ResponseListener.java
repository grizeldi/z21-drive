package z21Drive.responses;

public interface Z21ResponseListener {
    void responseReceived(ResponseTypes type, Z21Response response);
    ResponseTypes [] getListenerTypes();
}
