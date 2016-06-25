package z21Drive;

public class Z21Constants {
    /**
     * Used in combination with loco related actions and broadcasts.
     * Maps internal ids of speed steps to actual speed steps.
     */
    public static final int SPEED_STEPS_14 = 0, SPEED_STEPS_28 = 2, SPEED_STEPS_128 = 3;
    /**
     * Used for loco direction.
     */
    public static final boolean FORWARD = true, BACKWARD = false;

    /**
     * Used to turn actual amount of speed steps to z21-drive's internal ID.
     * @param speedSteps How many speed steps the loco has
     * @return Internal ID for that amount of speed steps or -1 if it can't find correct one
     */
    public static int resolveSpeedStepsId(int speedSteps){
        switch (speedSteps){
            case 14:
                return SPEED_STEPS_14;
            case 28:
                return SPEED_STEPS_28;
            case 128:
                return SPEED_STEPS_128;
            default:
                return -1;
        }
    }
}
