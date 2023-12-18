package org.example;

import com.google.gson.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static spark.Spark.*;


//link github: https://github.com/ClarissaProenca/DR3_AT_2_Servidor
public class Main {
    public static void main(String[] args) throws InterruptedException{
        port(8080);

        JFrame frame = new JFrame("Recebe código | Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label1 = new JLabel("Código cartão: ", SwingConstants.CENTER);
        label1.setFont((new Font("Arial", Font.BOLD, 30)));
        JTextField inputField1 = new JTextField();
        inputField1.setFont((new Font("Arial", Font.PLAIN, 30)));


        JButton buttonSair = new JButton("Fechar");
        buttonSair.setFont((new Font("Arial", Font.PLAIN, 30)));

        buttonSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(label1);
        panel.add(inputField1);
        panel.add(buttonSair);

        post("/api", (req, res) -> {
            String corpoRequisicao = req.body();
//            System.out.println("Corpo Jason: " + corpoRequisicao);

            JsonElement jsonElement = JsonParser.parseString(corpoRequisicao);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
//            inputField1.setText(jsonObject.get("ACK").getAsString());

//            String codValidado = inputField1.getText();

            String codValidado = jsonObject.get("ACK").getAsString();

            if(inputField1.getText().equals(codValidado)){
//                System.out.println("{\"ACK\": \"1\"}");
                return "{\"ACK\": \"1\"}";
            } else {
//                System.out.println("{\"ACK\": \"0\"}");
                return "{\"ACK\": \"0\"}";
            }

        });

        frame.add(panel);
        frame.setVisible(true);
    }
}