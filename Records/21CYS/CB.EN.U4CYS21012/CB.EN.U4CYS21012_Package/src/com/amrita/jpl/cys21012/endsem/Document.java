package com.amrita.jpl.cys21012.endsem;

import javax.swing.*;
import java.awt.*;
import java.util.*;
public class Document extends file{
    String documentType;

    int getDocumentType(){
        JFrame frame = new JFrame("AddingFile");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JTextField docType = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Document Type:"));
        panel.add(docType);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Add File", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            documentType = docType.getText().trim();
            return 1;
        }
        else{
            return 0;
        }

    }

    void displayFileDetails(){
        System.out.print(documentType);
    }

}
