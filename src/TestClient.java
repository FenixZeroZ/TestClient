import javafx.scene.control.ScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TestClient extends JFrame {

    JScrollPane jScrollPane;
    JEditorPane editorPane = new JEditorPane();
    JTextField jTextField = new JTextField();
    JLabel label = new JLabel("Окно общения");
    JLabel label2 = new JLabel("Окно ввода");

    String text = "";
    String textIn = "";
    String textOut = "";

    Socket fromServer;

    private void clientStart() throws IOException {
        this.setTitle("Client ver.1");
        this.setSize(500,500);
        this.setLocation(500,300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2,2,5,5));
        this.add(jScrollPane = new JScrollPane(editorPane));
        editorPane.setEditable(false);
        jScrollPane.getVerticalScrollBar().setValue(100);

        this.add(label);
        this.add(jTextField);
        this.add(label2);
        this.setVisible(true);
        try {
            fromServer = new Socket("31.44.56.213", 7777);
            text+=("\n"+"Подключен к  IP 31.44.56.213");
            editorPane.setText(text);
        } catch (Exception e){
            text+=("\n"+"Сервер не отвечает на IP 31.44.56.213");
            editorPane.setText(text);
        }

        BufferedReader in  = new
                BufferedReader(new
                InputStreamReader(fromServer.getInputStream()));


        PrintWriter out = new
                PrintWriter(fromServer.getOutputStream(),true);



        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textOut = jTextField.getText();
                text+=("\n"+"Client: "+textOut);
                jTextField.setText("");
                editorPane.setText(text);
                out.println(textOut);

            }
        });
                while (!textOut.equals("exit")){
                    textIn = in.readLine();
                    text += ("\n"+"Server: "+textIn);
                    editorPane.setText(text);
                }



        out.close();
        in.close();
        fromServer.close();
        System.out.println("Клиент закрыт");


    }

    public static void main(String[] args) throws IOException {
        TestClient testClient = new TestClient();
        testClient.clientStart();

    }

}
