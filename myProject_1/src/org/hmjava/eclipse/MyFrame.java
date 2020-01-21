package org.hmjava.eclipse;

import java.awt.*;
import javax.swing.*;

// 연습한 클래스
public class MyFrame extends JFrame{
	public MyFrame() {
		String s1 = new String();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		
		this.setLocation(screenSize.width / 2 - 300, screenSize.height / 2 - 100);
		
		setSize(600, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MyFrame");
		
		Image img = kit.getImage("and.png");
		setIconImage(img);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		JLabel label = new JLabel("제품명");
		
		
		JButton button1 = new JButton("Add");
		JButton button2 = new JButton("Delete");
		JButton button3 = new JButton("Modify");
		JButton button4 = new JButton("Save File");
		
		/*
		ImageIcon image = new ImageIcon("and.png");
		JLabel labelImage = new JLabel(image);
		*/
		//button1.setEnabled(false);
		
		JTextField t1 = new JTextField(10);
		JTextField t2 = new JTextField(10);
		t2.setEditable(false);
		t2.setText("해보쟈 빠이티이잉!!");
		
		s1 = t2.getText();
		t1.setText(s1);
		panel.add(t1);
		panel.add(t2);
		
		panel.add(label);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		//panel.add(labelImage);
		add(panel);
		
		this.pack();
		
		setVisible(true);
		setResizable(true);
	}
}
