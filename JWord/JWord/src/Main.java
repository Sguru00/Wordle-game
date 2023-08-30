import javax.swing.*;
import javax.swing.Timer;
import javax.xml.stream.events.StartDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main extends JFrame implements ActionListener {

    //a list of all possible 5 letter words in English
    public static HashSet<String> dictionary = new HashSet<>();

    //a smaller list of words for selecting the target word from (i.e. the word the player needs to guess)
    public static ArrayList<String> targetWords = new ArrayList<>();




    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";



    static long time_taken;
    static int  attempts;
    static boolean game_over;
    static String answer_picked;
    static char[] answer;
    static char[] inp;



    public static void main(String[] args) {
    //load in the two word lists
        try{
            Scanner in_dict  = new Scanner(new File("JWord/gameDictionary.txt"));
            while(in_dict.hasNext()){
                dictionary.add(in_dict.next());
            }

            Scanner in_targets = new Scanner(new File("JWord/targetWords.txt"));
            while(in_targets.hasNext()){
                targetWords.add(in_targets.next());
            }
            in_dict.close();
            in_targets.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        new GUI();

        Main main = new Main();
        Start();

    }

    public static int[] PlayWordle(String InputWordleWord) {
        game_over = false;
        attempts++;

        String R1 = InputWordleWord.toLowerCase();

        for (int i = 0; i < 5; i++ ) {
            inp[i] = R1.charAt(i);
        }


        for (int i = 0; i < 5; i++ ) answer[i] = answer_picked.charAt(i);
        return ColorOfLetters(inp, answer);
    }

    public static void Start(){
        time_taken = System.currentTimeMillis();
        attempts = 0;
        answer_picked = getTarget();

        answer = new char[5];
        for (int i = 0; i < 5; i++ ) answer[i] = answer_picked.charAt(i);

        inp = new char[5];

    }

    public static void End(){



        GUI.user_inp.setEnabled(false);
        GUI.user_inp.setVisible(false);



        if(!game_over) {
            GUI.text.setText("The Answer Was: " + new String(answer_picked) + ". You spent \n " + ((System.currentTimeMillis() - time_taken) / 1000) + " seconds :" );
            GUI.text.setBounds(100, 20, 250, 50);
            GUI.text.setFont(new Font("Neue Helvetica", Font.BOLD,10));
            GUI.text2.setText("");
//            new Timer(10_000, (e) -> { GUI.frame.setVisible(false); GUI.frame.dispose(); }).start();
//            terminate();


        } else{
            GUI.text.setText( "You Found The Answer in \n " + ((System.currentTimeMillis() - time_taken) / 1000) + " seconds and " + attempts + " tries." );
            GUI.text.setBounds(100, 20, 250, 50);
            GUI.text.setFont(new Font("Neue Helvetica", Font.BOLD,10));
            GUI.text2.setText("");
//            new Timer(10_000, (e) -> { GUI.frame.setVisible(false); GUI.frame.dispose(); }).start();
//            terminate();
        }
    }
//    public static void terminate(){
//
//    }






    //use this method for selecting a word. It's important for marking that the word you have selected is printed out to the console!
    public static String getTarget(){
        Random r = new Random();
        String target = targetWords.get(r.nextInt(targetWords.size()));

        //don't delete this line.
        System.out.println(target);
        return target;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Input_Word();
    }


    public static void Input_Word(){
        if(WordCheck(GUI.user_inp.getText(),targetWords)){
            Input();
        }
        if(WordCheck2(GUI.user_inp.getText(),dictionary)){
            Input();
        }


    }

    private static void Input() {
        String userInput = GUI.user_inp.getText();
        game_over=true;

        int[] colorOfLetters = PlayWordle(userInput);

        for (int i : colorOfLetters) {
            if (i != 2) game_over = false;
        }
        if (game_over || attempts > 5) End();


        String[] numsToColors = new String[5];
        for (int i = 0; i < 5; i++) {
            if (colorOfLetters[i] == 0) numsToColors[i] = "black";
            else if (colorOfLetters[i] == 1) numsToColors[i] = "orange";
            else if (colorOfLetters[i] == 2) numsToColors[i] = "green";
        }
        if(numsToColors[0] == "green"&& numsToColors[1] == "green"&& numsToColors[2] == "green"&&numsToColors[3] == "green"&& numsToColors[4] == "green"){
            game_over=true;
            End();

        }

        String finalString = (
                        "<html><font size='8' color=" + numsToColors[0] + "> " + userInput.charAt(0) + "</font> <font            " +
                        "<html><font size='8' color=" + numsToColors[1] + "> " + userInput.charAt(1) + "</font> <font            " +
                        "<html><font size='8' color=" + numsToColors[2] + "> " + userInput.charAt(2) + "</font> <font            " +
                        "<html><font size='8' color=" + numsToColors[3] + "> " + userInput.charAt(3) + "</font> <font            " +
                        "<html><font size='8' color=" + numsToColors[4] + "> " + userInput.charAt(4) + "</font> <font            ");
        NextLabel(finalString);

        GUI.user_inp.setText("");



    }

    public static boolean WordCheck(String input, ArrayList<String> possibleWords) {
        if (input.length() != 5) {
            if(game_over){
                GUI.text2.setText("");
            }else{
                GUI.text2.setText("Invalid word");
                return false;
            }
        }
        for (String string : possibleWords) {
            if (string.equals(input)) {
                return true;
            }
        }

        if(game_over){
            GUI.text2.setText("");
        }
        return false;

    }



    public static boolean WordCheck2(String input, HashSet<String> dictionaryWords) {
        if (input.length() != 5) {
            if(game_over){
                GUI.text2.setText("");
            }else {
                GUI.text2.setText("Invalid word");
                return false;
            }

        }
        for (String string : dictionaryWords) {
            if (string.equals(input)) {
                return true;
            }
        }



        return false;

    }

    public static void NextLabel(String string){
        GUI.labels[attempts - 1].setText(string);
    }

    public static int[] ColorOfLetters(char[] inputWord, char[] correctWord) {
        char[] answerTemp = correctWord;
        int[] colorForLetter = new int[5];

        for (int i = 0; i < 5; i++) {
            if (inputWord[i] == answerTemp[i]) {
                answerTemp[i] = '-';
                colorForLetter[i] = 2;
            }
        }

        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 5; k++){
                if (inputWord[j] == answerTemp[k] && colorForLetter[j] != 2) {
                    colorForLetter[j] = 1;
                    answerTemp[k] = '-';
                }
            }
        }

        for (int m = 0; m < 5; m++) {
            if (colorForLetter[m] == 0) System.out.print(inputWord[m]);
            if (colorForLetter[m] == 1) System.out.print(ANSI_YELLOW + inputWord[m] + ANSI_RESET);
            if (colorForLetter[m] == 2) System.out.print(ANSI_GREEN + inputWord[m] + ANSI_RESET);
        }

        System.out.println("");
        return colorForLetter;
    }






}
