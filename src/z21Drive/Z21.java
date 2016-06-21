package z21Drive;

import z21Drive.actions.Z21Action;
import z21Drive.actions.Z21ActionGetSerialNumber;
import z21Drive.actions.Z21ActionLanLogoff;
import z21Drive.broadcasts.*;
import z21Drive.responses.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Main class in this library which represents Z21 and handles all communication with it.
 * @author grizeldi
 */
public class Z21 implements Runnable{
    public static final Z21 instance = new Z21();
    private static final String host = "192.168.0.111";
    private static final int port = 21105;
    private boolean exit = false;
    private List<Z21ResponseListener> responseListeners = new ArrayList<Z21ResponseListener>();
    private List<Z21BroadcastListener> broadcastListeners = new ArrayList<Z21BroadcastListener>();
    private DatagramSocket socket;
	private Timer keepAliveTimer;

    private Z21() {
        Logger.getLogger("Z21 init").info("Z21 initializing");
        Thread listenerThread = new Thread(this);
        try {
            socket = new DatagramSocket(port);
        }catch (SocketException e){
            Logger.getLogger("Z21 init").warning("Failed to open socket to Z21..." + e);
        }
        listenerThread.start();
        addBroadcastListener(new Z21BroadcastListener() {
            @Override
            public void onBroadCast(BroadcastTypes type, Z21Broadcast broadcast) {
                if (type == BroadcastTypes.LAN_X_UNKNOWN_COMMAND)
                    Logger.getLogger("Z21 monitor").warning("Z21 reported receiving an unknown command.");
                else
                    Logger.getLogger("Z21 monitor").severe("Broadcast delivery messed up. Please report immediately to gitHub issues what have you done.");
            }

            @Override
            public BroadcastTypes[] getListenerTypes() {
                return new BroadcastTypes[]{BroadcastTypes.LAN_X_UNKNOWN_COMMAND};
            }
        });
        initKeepAliveTimer();
        //Make sure z21 shuts down communication gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shutdown();
            }
        }));
    }

    public void setKeepAliveTimer(int delay) {
    	keepAliveTimer.setInitialDelay(delay);
        keepAliveTimer.setDelay(delay);
        keepAliveTimer.restart();
    }
    
    private void initKeepAliveTimer(){
        keepAliveTimer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendActionToZ21(new Z21ActionGetSerialNumber());
            }
        });
        keepAliveTimer.setRepeats(true);
        keepAliveTimer.start();
    }

    /**
     * Used to send the packet to z21.
     * @param action Action to send.
     * @return returns true if action is sent successfully and false if it fails
     */
    public synchronized boolean sendActionToZ21(Z21Action action){
        DatagramPacket packet = PacketConverter.convert(action);
        try {
            InetAddress address = InetAddress.getByName(host);
            packet.setAddress(address);
            packet.setPort(port);
            socket.send(packet);
        }catch (IOException e){
            Logger.getLogger("Z21 sender").warning("Failed to send message to z21... " + e);
            return false;
        }
        return true;
    }

    /**
     * Used as a listener for any packets sent by Z21.
     * Also delivers packets to responseListeners.
     * @see Z21ResponseListener
     * @see Z21BroadcastListener
     */
    @Override
    public void run() {
        while (!exit){
            try {
                DatagramPacket packet = new DatagramPacket(new byte [510], 510);
                socket.receive(packet);
                //Determine if it's a response or a broadcast
                if (PacketConverter.responseFromPacket(packet) != null){
                    Z21Response response = PacketConverter.responseFromPacket(packet);
                    //Narrow the class definition
                    if (response.boundType == Z21ResponseAndBroadcastCollection.getSerialNumber.boundType){
                        //It's a serial number
                        Z21ResponseGetSerialNumber z21ResponseGetSerialNumber = (Z21ResponseGetSerialNumber) response;
                        //Deliver it
                        for (Z21ResponseListener listener : responseListeners){
                            for (ResponseTypes type : listener.getListenerTypes()){
                                if (type == ResponseTypes.LAN_GET_SERIAL_NUMBER_RESPONSE)
                                    listener.responseReceived(ResponseTypes.LAN_GET_SERIAL_NUMBER_RESPONSE, z21ResponseGetSerialNumber);
                            }
                        }
                    } else if (response.boundType == Z21ResponseAndBroadcastCollection.lanXGetFirmwareVersion.boundType){
                        //It's firmware
                        Z21ResponseLanXGetFirmwareVersion z21ResponseLanXGetFirmwareVersion = (Z21ResponseLanXGetFirmwareVersion) response;
                        //Deliver it
                        for (Z21ResponseListener listener : responseListeners){
                            for (ResponseTypes type : listener.getListenerTypes()){
                                if (type == ResponseTypes.LAN_X_GET_FIRMWARE_VERSION)
                                    listener.responseReceived(ResponseTypes.LAN_X_GET_FIRMWARE_VERSION, z21ResponseLanXGetFirmwareVersion);
                            }
                        }
                    } else if (response.boundType == Z21ResponseAndBroadcastCollection.lanXCvRead.boundType) {
                        //It's a CV Result
                    	Z21ResponseLanXCVResult z21BroadcastLanXCVRead = (Z21ResponseLanXCVResult) response;
                        for (Z21ResponseListener listener : responseListeners){
                            for (ResponseTypes type : listener.getListenerTypes()){
                                if (type == ResponseTypes.LAN_X_CV_RESULT)
                                    listener.responseReceived(ResponseTypes.LAN_X_CV_RESULT, z21BroadcastLanXCVRead);
                            }
                        }
                    } else if (response.boundType == Z21ResponseAndBroadcastCollection.lanXCvNACK.boundType) {
                        //It's a message that CV manipulation went wrong.
                        Z21ResponseLanXCVNACK z21BroadcastLanXCVNACK = (Z21ResponseLanXCVNACK) response;
                        for (Z21ResponseListener listener : responseListeners){
                            for (ResponseTypes type : listener.getListenerTypes()){
                                if (type == ResponseTypes.LAN_X_CV_NACK)
                                    listener.responseReceived(ResponseTypes.LAN_X_CV_NACK, z21BroadcastLanXCVNACK);
                            }
                        }
                    }
                }else {
                    Z21Broadcast broadcast = PacketConverter.broadcastFromPacket(packet);
                    if (broadcast != null) {
                        //Narrow the class definition
                        if (broadcast.boundType == Z21ResponseAndBroadcastCollection.lanXLocoInfo.boundType) {
                            //It's a loco info broadcast
                            Z21BroadcastLanXLocoInfo z21BroadcastLanXLocoInfo = (Z21BroadcastLanXLocoInfo) broadcast;
                            for (Z21BroadcastListener listener : broadcastListeners) {
                                for (BroadcastTypes type : listener.getListenerTypes()) {
                                    if (type == BroadcastTypes.LAN_X_LOCO_INFO)
                                        listener.onBroadCast(BroadcastTypes.LAN_X_LOCO_INFO, z21BroadcastLanXLocoInfo);
                                }
                            }
                        } else if (broadcast.boundType == Z21ResponseAndBroadcastCollection.lanXUnknownCommand.boundType) {
                            //It's an unknown command broadcast
                            Z21BroadcastLanXUnknownCommand z21BroadcastLanXUnknownCommand = (Z21BroadcastLanXUnknownCommand) broadcast;
                            for (Z21BroadcastListener listener : broadcastListeners) {
                                for (BroadcastTypes type : listener.getListenerTypes()) {
                                    if (type == BroadcastTypes.LAN_X_UNKNOWN_COMMAND)
                                        listener.onBroadCast(BroadcastTypes.LAN_X_UNKNOWN_COMMAND, z21BroadcastLanXUnknownCommand);
                                }
                            }
                        } else if (broadcast.boundType == Z21ResponseAndBroadcastCollection.lanXTrackPowerOff.boundType) {
                            //It's a track power off broadcast
                            Z21BroadcastLanXTrackPowerOff z21BroadcastLanXTrackPowerOff = (Z21BroadcastLanXTrackPowerOff) broadcast;
                            for (Z21BroadcastListener listener : broadcastListeners) {
                                for (BroadcastTypes type : listener.getListenerTypes()) {
                                    if (type == BroadcastTypes.LAN_X_TRACK_POWER_OFF)
                                        listener.onBroadCast(BroadcastTypes.LAN_X_TRACK_POWER_OFF, z21BroadcastLanXTrackPowerOff);
                                }
                            }
                        } else if (broadcast.boundType == Z21ResponseAndBroadcastCollection.lanXTrackPowerOn.boundType) {
                            //It's a track power on broadcast
                            Z21BroadcastLanXTrackPowerOn z21BroadcastLanXTrackPowerOn = (Z21BroadcastLanXTrackPowerOn) broadcast;
                            for (Z21BroadcastListener listener : broadcastListeners) {
                                for (BroadcastTypes type : listener.getListenerTypes()) {
                                    if (type == BroadcastTypes.LAN_X_TRACK_POWER_ON)
                                        listener.onBroadCast(BroadcastTypes.LAN_X_TRACK_POWER_ON, z21BroadcastLanXTrackPowerOn);
                                }
                            }
                        } else if (broadcast.boundType == Z21ResponseAndBroadcastCollection.lanXProgrammingMode.boundType){
                            //It's a programming mode broadcast
                            Z21BroadcastLanXProgrammingMode z21BroadcastLanXProgrammingMode = (Z21BroadcastLanXProgrammingMode) broadcast;
                            for (Z21BroadcastListener listener : broadcastListeners) {
                                for (BroadcastTypes type : listener.getListenerTypes()) {
                                    if (type == BroadcastTypes.LAN_X_PROGRAMMING_MODE)
                                        listener.onBroadCast(BroadcastTypes.LAN_X_PROGRAMMING_MODE, z21BroadcastLanXProgrammingMode);
                                }
                            }
                        }
                    }
                }
            }catch (IOException e){
                if (!exit)
                    Logger.getLogger("Z21 Receiver").warning("Failed to get a message from z21... " + e);
            }
        }
    }

    public void addResponseListener(Z21ResponseListener listener){
        responseListeners.add(listener);
    }

    public void removeResponseListener(Z21ResponseListener listener){
        if (responseListeners.contains(listener))
            responseListeners.remove(listener);
    }

    public void addBroadcastListener(Z21BroadcastListener listener){
        broadcastListeners.add(listener);
    }

    public void removeBroadcastListener(Z21BroadcastListener listener){
        if (broadcastListeners.contains(listener))
            broadcastListeners.remove(listener);
    }

    /**
     * Used to gracefully stop all communications.
     */
    public void shutdown(){
        Logger.getLogger("Z21").info("Shutting down all communication.");
        sendActionToZ21(new Z21ActionLanLogoff());
        keepAliveTimer.stop();
        exit = true;
        socket.close();
    }

    @Override
    protected void finalize() throws Throwable {
        shutdown();
        super.finalize();
    }
}

/**
 * Converting packets to objects and back.
 */
class PacketConverter {
    static DatagramPacket convert(Z21Action action){
        byte [] packetContent = toPrimitive(action.getByteRepresentation().toArray(new Byte [0]));
        return new DatagramPacket(packetContent, action.getByteRepresentation().size());
    }

    /**
     * Here the magic of turning bytes into objects happens.
     * @param packet UDP packet received from Z21
     * @return Z21 response object which represents the byte array.
     */
    static Z21Response responseFromPacket(DatagramPacket packet){
        byte [] array = packet.getData();
        byte header1 = array[2], header2 = array[3];
        int xHeader = array[4] & 255;
        if (header1 == 0x10 && header2 == 0x00)
            return new Z21ResponseGetSerialNumber(array);
        else if (header1 == 0x40 && header2 == 0x00 && xHeader == 0xF1)
            return  new Z21ResponseLanXGetFirmwareVersion(array);
        else if (header1 == 0x40 && header2 == 0x00 && xHeader == 0x64 && (array[5] & 255) == 0x14)
            return new Z21ResponseLanXCVResult(array);
        else if (header1 == 0x40 && header2 == 0x00 && xHeader == 0x61 && (array[5] & 255) == 0x13)
            return new Z21ResponseLanXCVNACK(array);
        return null;
    }

    /**
     * Same as for responses, but for broadcasts. See method responseFromPacket(DatagramPacket packet).
     * @param packet UDP packet received from Z21
     * @return Z21 broadcast object which represents the broadcast sent from Z21.
     */
    static Z21Broadcast broadcastFromPacket(DatagramPacket packet){
        byte [] data = packet.getData();
        //Get headers
        byte header1 = data[2], header2 = data[3];
        int xHeader = data[4] & 255;
        //Discard all zeros
        byte [] newArray = new byte[data[0]];
        System.arraycopy(data, 0, newArray, 0, newArray.length);
        if (data[data[0] + 1] != 0){
            //We got two messages in one packet.
            //Don't know yet what to do. TODO
            Logger.getLogger("Z21 Receiver").info("Received two messages in one packet. Multiple messages not supported yet.");
        }

        if (header1 == 0x40 && header2 == 0x00 && xHeader == 239)
            return new Z21BroadcastLanXLocoInfo(newArray);
        else if (header1 == 0x40 && header2 == 0x00 && xHeader == 0x61 && (data[5] & 255) == 0x82)
            return new Z21BroadcastLanXUnknownCommand(newArray);
        else if (header1 == 0x40 && header2 == 0x00 && xHeader == 0x61 && (data[5] & 255) == 0x00)
            return new Z21BroadcastLanXTrackPowerOff(newArray);
        else if (header1 == 0x40 && header2 == 0x00 && xHeader == 0x61 && (data[5] & 255) == 0x01)
            return new Z21BroadcastLanXTrackPowerOn(newArray);
        else if (header1 == 0x40 && header2 == 0x00 && xHeader == 0x61 && (data[5] & 255) == 0x02)
            return new Z21BroadcastLanXProgrammingMode(newArray);
        else {
            Logger.getLogger("Z21 Receiver").warning("Received unknown message. Array:");
            for (byte b : newArray)
                System.out.print("0x" + String.format("%02X ", b));
            System.out.println();
        }
        return null;
    }

    /**
     * Unboxes Byte array to a primitive byte array.
     * @param in Byte array to primitivize
     * @return primitivized array
     */
    private static byte[] toPrimitive(Byte [] in){
        byte [] out = new byte [in.length];
        int i=0;
        for(Byte b: in)
            out[i++] = b.byteValue();
        return out;
    }
}