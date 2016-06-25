package z21Drive;

public class LocoAddressOutOfRangeException extends Exception{
    private final int address;

    public LocoAddressOutOfRangeException(int invalidAddress){
        address = invalidAddress;
    }

    public int getInvalidAddress(){return address;}
}
