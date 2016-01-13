package gui.javagui;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ElectionHandlingGUI extends JPanel {

    private JFrame frame;
    private static JLabel label;
    private static JOptionPane optionPane;
    private JButton secureButton;
    private JButton directoryButton;
    private JButton countButton;
    private MyPanel panel1;
    private JFileChooser chooser;
    private JComboBox votingSystems;

    public JButton getSecureButton() {
        return secureButton;
    }

    public JButton getCountButton() {
        return countButton;
    }

    public JComboBox  getVotingSystems() {
        return votingSystems;
    }

    public JButton getDirectoryButton() {
        return directoryButton;
    }

    public ElectionHandlingGUI(String text) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        frame = new JFrame("Election");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(
                BorderFactory.createEmptyBorder(2, 2, 2, 2));
        contentPane.setLayout(new CardLayout());
        contentPane.setPreferredSize(new Dimension((int)width/2,(int)height/8));

        panel1 = new MyPanel(text);
        contentPane.add(panel1, "Panel 1");
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String findBox(){
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Ballot box directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (!(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)){
            return "-1";
        }
        return chooser.getSelectedFile().toString();
    }

    public static void setText(String text){
        label.setText(text);
    }

    class MyPanel extends JPanel
    {
        public MyPanel(String text)
        {
            setOpaque(true);
            setBackground(Color.WHITE.darker());
            //construct components
            label = new JLabel("");
            secureButton = new JButton("Secure votes");
            directoryButton = new JButton("Pick a directory");
            countButton = new JButton("Count votes");
            add(directoryButton);
            String[] systems = { "1. First-ran-the-post", "2. Range Voting" , "3. Approval Voting"};
            votingSystems = new JComboBox(systems);
            votingSystems.setSelectedIndex(0);
            add(votingSystems);
            setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            add(secureButton);
            add(countButton);
            add(label);
        }
    }

    public static void errorMessage(String error) {
        String messageTwo = "Voice weren't secured. ";
        String messageThree = "No valid votes to check. ";
        JOptionPane.showMessageDialog(new JFrame(), error, "Dialog",
            JOptionPane.ERROR_MESSAGE);
    }
}
