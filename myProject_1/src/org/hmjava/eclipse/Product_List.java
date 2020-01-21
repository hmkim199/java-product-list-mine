package org.hmjava.eclipse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.ArrayList;

import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*productList�����. gui ��� �̿��ϱ� ���ؼ� JFrame ���
 * 20172510 ������ 2019.12/15-2020.01/03����
 */
public class Product_List extends JFrame {
	JTable table = new JTable();
	//JTable realtable = new JTable(); �ʿ����!
	String contents[][];
	
	public Product_List() {
		// screenSize ��� ���� Toolkit �̿�
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		 
		//this.setLocation ������ this.�� �ٿ��� ��.
		setLocation(screenSize.width / 2 - 300, screenSize.height / 2 - 100);
		setSize(600,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //��� JFrame ��������Ƿ� �ּ�ó���ص� x �������� �����. Frame�� �ش� �ڵ� �־�߸� ����.
		setTitle("Product List");
		
		//and.png ���� �����ͼ� ������ �������̹����� ����, Toolkit �̿�
		Image img = kit.getImage("and.png");
		setIconImage(img);
		
		String header[] = {"��ǰ��", "��ǰID", "ī�װ�", "����", "����", "�ּ����", "��Ÿ �޸�"};
		
		loadTable();
		
		DefaultTableModel model;
		
		model = new DefaultTableModel(contents, header) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(model);
		JScrollPane scrollpane = new JScrollPane(table);
		
		//������ ��ư 4�� �ټ���.
		JPanel panel = new JPanel();
		GridLayout gridButton = new GridLayout(4,1);
		panel.setLayout(gridButton);
		
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				AddModifyCommon fs = new AddModifyCommon("Add a product list", " ", " ", " ", " ", " ", " ", " ");
				
				fs.doneBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						String inputStr[] = fs.getInputStr();
						
						for(int i = 0; i<7 ; i++) {
							if(inputStr[i]==null) {
								inputStr[i] = " ";
							}
						}
						model.addRow(inputStr);
					}
				});
			}			
		});
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				if(table.getSelectedRow() == -1) {
					return;
				}	
				else {
					model.removeRow(table.getSelectedRow());
				}
			}		
		});
		
		JButton modifyBtn = new JButton("Modify");
		modifyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() == -1) {
					return;
				}	
				else {
					int rowIndex = table.getSelectedRow();
					
					AddModifyCommon fs = new AddModifyCommon("Modify a product line", (String) table.getValueAt(rowIndex, 0), (String) table.getValueAt(rowIndex, 1), (String) table.getValueAt(rowIndex, 2), (String) table.getValueAt(rowIndex, 3), (String) table.getValueAt(rowIndex, 4), (String) table.getValueAt(rowIndex, 5), (String) table.getValueAt(rowIndex, 6));
				
					fs.doneBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							
							String inputStr[] = fs.getInputStr();
							
							for(int i = 0; i<7 ; i++) {
								if(inputStr[i]==null) {
									inputStr[i] = " ";
								}
								table.setValueAt(inputStr[i],rowIndex,i);
							}							
						}												
					});					
				}				
			}			
		});
		
		JButton saveFileBtn = new JButton("Save File");
		saveFileBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
					//table.setModel(model);
					//realtable=table;
					try {
						saveTable();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
			}			
		});
		
		panel.add(addBtn);
		panel.add(deleteBtn);
		panel.add(modifyBtn);
		panel.add(saveFileBtn);
		
		add(scrollpane, BorderLayout.CENTER); //LEFT�� �ȵ�. WEST�� �ϸ� CENTER�� ��. BorderLayout�� ���� ���̾ƿ� ���� ���ϸ� ����Ʈ��.
		add(panel, BorderLayout.EAST);
		
		setVisible(true);
		setResizable(true);				
	}
	
	
	public void saveTable()throws Exception 
	{ 	
		//BufferedWriter�� PrintWriter�� ���. FileWriter�� �ٷ� file�� write�ϴµ� �� �ΰ��� ���ۿ� write�ߴٰ� file�� �Ѳ����� write.
	    BufferedWriter bfw = new BufferedWriter(new FileWriter("Data.txt")); 
	   
	    for (int i = 0 ; i < table.getRowCount(); i++)  // table. ��ſ� realtable.���� �߾��µ� �ʿ����!
	    { 
	     
	    	for(int j = 0 ; j < table.getColumnCount();j++) 
	    	{   
	    		if((String)(table.getValueAt(i,j))==null) {
	    			table.setValueAt(" ",i,j);
	    		}
	    		bfw.write((String)(table.getValueAt(i,j))); 
	    		bfw.write("\t");; 
	    	} 
	    	bfw.newLine();
	    } 
	    bfw.close(); 
	} 	
	
	public void loadTable()
	{
		 try{
	            //���� ��ü ����, "C:\\Users\\hmkim\\eclipse-workspace\\myProject_1\\Data.txt" �ص� ��.
	            File file = new File("Data.txt");
	            //�Է� ��Ʈ�� ����
	            FileReader filereader = new FileReader(file);
	            //�Է� ���� ����
	            BufferedReader bufReader = new BufferedReader(filereader);
	            String line = "";
	            
	            //list�� �������̽�, arraylist�� Ŭ����, List<String[]> contentsLoad = new ArrayList<String[]>(); �ص� ��.
	            ArrayList<String[]> contentsLoad = new ArrayList<String[]>();
	            
	            while((line = bufReader.readLine()) != null) {
	            	//String[] oneLine = line.split("\t"); �̷��� �ص� ��.
	            	String[] oneLine = new String[7];
	            	oneLine = line.split("\t");
	            	
	            	contentsLoad.add(oneLine);
	            }
	            String[][] simpleArray = new String[contentsLoad.size()][7]; //[contentsLoad.size()][]�� �ᵵ ��.
	            for(int i = 0;i<contentsLoad.size();i++) {
	            	String[] row = contentsLoad.get(i);
	            	//simpleArray[i] = new String[row.length];
	            	simpleArray[i] = new String[7];
	            	for(int j = 0; j<7;j++) {
	            		simpleArray[i][j] = row[j];
	            	}
	            }
	            
	            contents = simpleArray;
	           
	            //.readLine()�� ���� ���๮�ڸ� ���� �ʴ´�.            
	            bufReader.close();
	        }catch (FileNotFoundException e) {
	            // TODO: handle exception
	        	System.out.println(e);
	        }catch(IOException e){
	            System.out.println(e);
	        }
	}
	
}
