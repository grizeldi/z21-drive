package testing;

public class Main implements Runnable{

    public static void main(String[] args) {
        //Start things up
        new Thread(new Main()).start();
    }

    /*public void run(){
        JFrame frame = new JFrame("Z21 Test Console");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textField = new JTextField();
        textField.setColumns(20);

        JTextArea textArea = new JTextArea();
        //textArea.setPreferredSize(new Dimension(200, 100));

        JPanel textEntry = new JPanel();
        textEntry.add(textField);
        textEntry.add(new JButton("Send"));

        frame.add(textArea);
        frame.add(textEntry);

        frame.pack();
        frame.setVisible(true);
    }*/

    public void run(){
        //TODO add run code
        //THIS IS NOT HERE, OK?
        /*boolean [] bool = new boolean[]{true, true, true, true, true, true, true, true};
        int b = ((bool[0]?1<<7:0) + (bool[1]?1<<6:0) + (bool[2]?1<<5:0) +
                (bool[3]?1<<4:0) + (bool[4]?1<<3:0) + (bool[5]?1<<2:0) +
                (bool[6]?1<<1:0) + (bool[7]?1:0));
        System.out.println(b);*/

        byte Adr_MSB = 10, Adr_LSB = -1;
        System.out.println((Adr_MSB & 0x3F) << (8 + Adr_LSB));
    }
}
