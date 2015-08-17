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
    }
}
