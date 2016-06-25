package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionSetLocoFunction;

/**
 * Used to test if setLocoFunction works.
 * If it's working properly, loco keeps it's headlights flashing.
 * @see z21Drive.actions.Z21ActionSetLocoFunction
 * @author grizeldi
 */
public class FlashHeadLights implements Runnable{
    private boolean exit;

    public static void main(String [] args){
        new Thread(new FlashHeadLights()).start();
    }

    private FlashHeadLights(){
        new Thread(() -> {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            exit = true;
        }).start();
    }

    @Override
    public void run() {
        Z21 z21 = Z21.instance;
        while (!exit){
            try {
                z21.sendActionToZ21(new Z21ActionSetLocoFunction(5, 0, true));
                Thread.sleep(2000);
                z21.sendActionToZ21(new Z21ActionSetLocoFunction(5, 0, false));
                Thread.sleep(2000);
            } catch (LocoAddressOutOfRangeException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        z21.shutdown();
    }
}
