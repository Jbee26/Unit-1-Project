import org.w3c.dom.Text;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class Window implements ActionListener {
    private JFrame mainFrame;
    private JPanel controlPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea ta; //typing area
    private JTextArea ta2;
    private JTextArea ta3;
    private JScrollPane sp;

    private int WIDTH = 800;
    private int HEIGHT = 700;
    private JButton B1;
    private JButton B2;


    public Window() {
        prepareGUI();
    }


    public static void main(String[] args) {
        Window E1 = new Window();

    }

    private void prepareGUI() {
        mainFrame = new JFrame("Link Finder 3000");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(1, 1));

         B1 = new JButton("ENTER");
         B2 = new JButton("CLEAR");

        ta = new JTextArea("ENTER LINK HERE");
        ta.setBounds(50, 5, WIDTH - 100, HEIGHT - 50);
        ta.setBorder(new LineBorder(Color.BLACK));
        ta2 = new JTextArea("ENTER SEARCHTERM HERE");
        ta2.setBounds(50, 800, WIDTH - 100, HEIGHT - 50);
        ta2.setBorder(new LineBorder(Color.BLACK));
        ta3 = new JTextArea();
        ta3.setBorder(new LineBorder(Color.BLACK));
        sp = new JScrollPane(ta3);
        B1.setForeground(Color.green);
        B2.setForeground(Color.red);
        Font f = new Font( "Chalkboard", Font.ITALIC, 15 );
        Font f2 = new Font( "Chalkboard", Font.ITALIC, 12 );
        ta.setFont( f );
        ta2.setFont( f2 );
        B1.setFont( f );
        B2.setFont( f );












        mainFrame.add(ta);

        mainFrame.add(ta2);

        mainFrame.add(sp);


        mainFrame.add(B1);
        mainFrame.add(B2);





        //menu at top
        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);
        //end menu at top


        mainFrame.add(mb);
        mainFrame.setJMenuBar(mb);



        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());


        B1.setActionCommand("ENTER");
        B2.setActionCommand("CLEAR");


        B1.addActionListener(new ButtonClickListener());
        B2.addActionListener(new ButtonClickListener());



        mainFrame.setVisible(true);


    }


    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("ENTER")) {

                System.out.println();
                HtmlRead();


            } else if (command.equals("CLEAR")) {

                ta.setText("ENTER LINK HERE");
                ta2.setText("ENTER SEARCHTERM HERE");
                ta3.setText("");







            }
        }
    }








    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            ta.cut();
        if (e.getSource() == paste)
            ta.paste();
        if (e.getSource() == copy)
            ta.copy();
        if (e.getSource() == selectAll)
            ta.selectAll();
    }



    public void HtmlRead() {

        boolean invaild = false;

        try {

            String Text = ta.getText();
            System.out.println(Text);


            String TextTa2 = ta2.getText();
            System.out.println(TextTa2);


            URL url = new URL(Text);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;

            while ( (line = reader.readLine()) != null ) {
                if (line.contains("href") && line.contains(TextTa2)) {
//                    System.out.println(line);
                    line = line.substring(line.indexOf("href")+ 6);
                    int end = line.indexOf("\"");
                    int oEnd = line.indexOf("\'");




                    if(end> -1){

                        if (line.substring(0, end).contains(TextTa2)){
                            System.out.print(line.substring(0, end)+"\n");
                            ta3.append(line.substring(0, end)+"\n");
                            invaild = true;

                        }





                    } else{

                        if (line.substring(0, oEnd).contains(TextTa2)) {
                            System.out.print(line.substring(0, oEnd)+"\n");
                            ta3.append(line.substring(0, oEnd)+"\n");
                            invaild = true;
                        }



                    }







                }



            }

            if (!invaild){
                ta3.setText("Hmmmm... Invalid Serchterm or Link!");
            }





            reader.close();



        } catch(Exception ex) {
            System.out.println(ex);
        }

    }





}





