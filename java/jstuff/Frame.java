package jstuff;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;

public class Frame {

	public static void main(String[] args) {
		
		// Instanzieren
		JFrame fenster = new JFrame(); // Objekt eines JFrames erstellen
		
		JPanel panel1 = new JPanel(); // Objekt eines JPanels erstellen
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		JButton button1 = new JButton();
		JTextField textField1 = new JTextField();
		JProgressBar progressBar1 = new JProgressBar();
		JSlider slider1 = new JSlider();
		JTable table1 = new JTable();
		JRadioButton radio1 = new JRadioButton();
		
		
		// Zuweisen
		panel1.setBackground(Color.red);
		panel1.setPreferredSize(new Dimension(400,300));
		panel1.setLayout(new BorderLayout());
		panel1.add(button1,BorderLayout.EAST);
		panel1.add(slider1,BorderLayout.SOUTH);
		
		panel2.setBackground(Color.green);
		panel2.setPreferredSize(new Dimension(400,300));
		panel2.setLayout(new GridLayout(1,2));
		panel2.add(textField1);
		panel2.add(table1);
		
		panel3.setBackground(Color.blue);
		panel3.setPreferredSize(new Dimension(400,300));
		panel3.setLayout(null);
		panel3.add(progressBar1);
		panel3.add(radio1);
		
		
		
		
		
		fenster.setTitle("Demonstration"); // Titel des Fensters einstellen
		fenster.setVisible(true); // Das Fenster sichtbar machen
		fenster.setResizable(true); // Es nicht ermoeglichen die Fenstergroesse einzustellen
		fenster.setSize(1000,500); // Groesse des Fensters einstellen
		fenster.setLocationRelativeTo(null); // Programm in der Mitte des Bildschirms positionieren
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Programm bei "x" schließen
		fenster.setLayout(new GridLayout(1,3));
		fenster.add(panel1);
		fenster.add(panel2);
		fenster.add(panel3);
		
		//fenster.pack();
		
	}

}
