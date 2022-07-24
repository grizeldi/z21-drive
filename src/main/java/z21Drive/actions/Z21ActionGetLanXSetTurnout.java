package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

/**
 * Used to retrieve loco status from z21.
 * Supports loco addresses up to 128.
 */
public class Z21ActionGetLanXSetTurnout extends Z21Action{

    /**
     * @param turnoutAddress Address of the turnout to request info of.
     * @param position is the position of the turnout 
     * @param active sets the decoder status
     * @throws LocoAddressOutOfRangeException Thrown if loco address is out of supported range.
     */
    public Z21ActionGetLanXSetTurnout(int turnoutAddress, byte position, boolean active) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        if (turnoutAddress < 1)
            throw new LocoAddressOutOfRangeException(turnoutAddress);
        addDataToByteRepresentation(new Object[]{turnoutAddress, position, active});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        //Add all the data
        byteRepresentation.add((byte)(int)Integer.decode("0x53"));
        
        byte Adr_MSB = (byte) (((Integer)objs[0]) >> 8);
        byte Adr_LSB = (byte) (((Integer)objs[0]) & 0b11111111);
        if (Adr_MSB != 0){
            Adr_MSB |= 0b11000000;
        }

        byteRepresentation.add(Adr_MSB);
        byteRepresentation.add(Adr_LSB);
 
        //data byte, with Q=1
        byte db2 = (byte) 0xA0;
        
        //add position
        db2 |= (byte)((Byte)objs[1]); 
        
        //add activate
        if(((Boolean)objs[2]))
        	db2 |= (byte)(0x08);
        	
        byteRepresentation.add(db2);
        byteRepresentation.add((byte) (byteRepresentation.get(2) ^ 
        		                       byteRepresentation.get(3) ^ 
        		                       byteRepresentation.get(4) ^ 
        		                       byteRepresentation.get(5)));
    }
}
