import javax.swing.*;
import java.awt.*;

public class GUI {
    public static JTextField user_inp;
    public static JLabel text;
    public static JLabel[] labels;
    public static JLabel text2;
    public static JFrame frame;

    private static JPanel panel;




    public GUI(){

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();


        //Wordle window
        panel.setLayout(null);
        frame.setTitle("Wordle");
        frame.setSize(500, 500);
        frame.setResizable(false); // Makes the window of the GUI not resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        //Wordle instruction text
        text = new JLabel("Type a five letter word");
        text.setBounds(100, 20, 250, 30);
        text.setFont(new Font("Neue Helvetica", Font.BOLD,20));
        panel.add(text);


        //User input text box
        user_inp = new JTextField();
        user_inp.addActionListener(new Main());
        user_inp.setBounds(100,300,180,45);
        user_inp.setFont(new Font("Neue Helvetica", Font.BOLD,40));
        panel.add(user_inp);

        //Button
        JButton button = new JButton("Enter");
        button.setBounds(305, 310, 80, 25);
        button.addActionListener(new Main());
        panel.add(button);

        //Input
        labels = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            labels[i] = new JLabel(" - - - - -");
            labels[i].setFont(new Font("Neue Helvetica", Font.BOLD,40));
            labels[i].setBounds(100, 70 + (i * 35), 180, 55);
            panel.add(labels[i]);
        }


        //Invalid text
        text2 = new JLabel("");
        text2.setBounds(100, 350, 250, 30);
        text2.setFont(new Font("Neue Helvetica", Font.BOLD,20));
        panel.add(text2);



        frame.setVisible(true);








    }




}
