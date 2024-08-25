package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import server.Server;

public class Client extends JFrame{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    public static final int POSX = 1000;
    public static final int POSY = 250;

    private Server server;
    private boolean connected;
    private String name;

    JTextArea log;
    JTextField ipField = new JTextField("ip adress");
    JTextField portField = new JTextField("port:");
    JTextField loginField = new JTextField("login");
    JTextArea messageField = new JTextArea();

    JPasswordField password = new JPasswordField("password:");
    JButton btnLogin = new JButton("Login");
    JButton btnSend = new JButton("Send");
    JPanel headerPanel = new JPanel(new GridLayout(2,3));

    public Client(Server server){
        this.server = server;
        setLocation(POSX,POSY);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");

        createPanel();

        setVisible(true);
    }

    public void answer(String text){
        appendLog(text);
    }

    private void connectToServer() {
        if (server.connectUser(this)){
            appendLog("Вы успешно подключились!\n");
            headerPanel.setVisible(false);
            connected = true;
            name = loginField.getText();
            String log = server.getLog();
            if (log != null){
                appendLog(log);
            }
        } else {
            appendLog("Подключение не удалось");
        }
    }

    public void disconnectFromServer() {
        if (connected) {
            headerPanel.setVisible(true);
            connected = false;
            server.disconnectUser(this);
            appendLog("Вы были отключены от сервера!");

        }
    }

    public void message(){
        if (connected){
            String text = messageField.getText();
            if (!text.equals("")){
                server.message(name + ": " + text);
                messageField.setText("");
            }
        } else {
            appendLog("Нет подключения к серверу");
        }

    }

    private void appendLog(String text){
        log.append(text + "\n");
    }

    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    private Component createHeaderPanel(){
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        headerPanel.add(ipField);
        headerPanel.add(portField);
        headerPanel.add(new JPanel());
        headerPanel.add(loginField);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }

    private Component createLog(){
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    message();
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });
        panel.add(messageField);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            disconnectFromServer();
        }
        super.processWindowEvent(e);
    }
    }
















