package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JPanel {
    private Display display;

    private JLabel errorMessage;
    private JPasswordField passwordField;
    private JTextField userNameField;
    private JButton loginButton;

    private final String usernameFieldText = "Username\uFEFF";
    private final String passwordFieldText = "Password\uFEFF";

    public Login(Display display) {
        this.display = display;
        setComponents();
        setDefaultValues();
    }

    private void setComponents() {
        errorMessage = new JLabel();
        errorMessage.setFocusable(false);
        errorMessage.setBorder(new EmptyBorder(0,0,0,0));
        errorMessage.setForeground(Color.RED);

        userNameField = new JTextField(usernameFieldText);
        userNameField.setForeground(Color.GRAY);
        userNameField.setBorder(new EmptyBorder(0,0,0,0));
        userNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userNameField.getText().equals(usernameFieldText)) {
                    userNameField.setText("");
                    userNameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userNameField.getText().isEmpty()) {
                    userNameField.setText(usernameFieldText);
                    userNameField.setForeground(Color.GRAY);
                }
            }
        });

        passwordField = new JPasswordField(passwordFieldText);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setBorder(new EmptyBorder(0,0,0,0));
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(passwordFieldText)) {
                    passwordField.setEchoChar('*');
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setText(passwordFieldText);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        loginButton = new JButton("Login");
        loginButton.setFocusable(false);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    login();
                }
            }
        });
    }

    //check username and password
    private void login(){
        String username = userNameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if(username.equals(usernameFieldText) || password.equals(passwordFieldText)){
            errorMessage.setText("Username and password are required");
        }else{
            User user = UserManager.getUserByUsername(username);
            if(user == null || user.getPassword() != password.hashCode()){
                errorMessage.setText("Wrong username or password");
            }else{
                display.updateCurrentScreen(1);
            }
        }
    }

    private void addComponents() {
        this.add(userNameField);
        this.add(passwordField);
        this.add(loginButton);
        this.add(errorMessage);
    }

    private void setDefaultValues() {
        GridLayout layout = new GridLayout(4,1);
        layout.setVgap(5);
        this.setLayout(layout);
        this.setBackground(new Color(0x60A3D9));
        this.setBorder(new EmptyBorder(200, 200, 200, 200));
        addComponents();
    }
}
