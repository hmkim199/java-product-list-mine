package org.hmjava.eclipse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.ArrayList;

import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*productList만들기. gui 기능 이용하기 위해서 JFrame 상속
 * 20172510 김혜민 2019.12/15-2020.01/03까지
 */
public class Product_List extends JFrame {
	JTable table = new JTable();
	//JTable realtable = new JTable(); 필요없네!
	String contents[][];
	
	public Product_List() {
		// screenSize 얻기 위해 Toolkit 이용
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		 
		//this.setLocation 등으로 this.를 붙여도 됨.
		setLocation(screenSize.width / 2 - 300, screenSize.height / 2 - 100);
		setSize(600,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //사실 JFrame 상속했으므로 주석처리해도 x 눌렀을때 종료됨. Frame은 해당 코드 있어야만 종료.
		setTitle("Product List");
		
		//and.png 파일 가져와서 프레임 아이콘이미지로 설정, Toolkit 이용
		Image img = kit.getImage("and.png");
		setIconImage(img);
		
		String header[] = {"제품명", "제품ID", "카테고리", "가격", "재고수", "최소재고량", "기타 메모"};
		
		loadTable();
		
		DefaultTableModel model;
		
		model = new DefaultTableModel(contents, header) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(model);
		JScrollPane scrollpane = new JScrollPane(table);
		
		//오른쪽 버튼 4개 줄세움.
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
		
		add(scrollpane, BorderLayout.CENTER); //LEFT는 안됨. WEST로 하면 CENTER가 빔. BorderLayout은 따로 레이아웃 지정 안하면 디폴트값.
		add(panel, BorderLayout.EAST);
		
		setVisible(true);
		setResizable(true);				
	}
	
	
	public void saveTable()throws Exception 
	{ 	
		//BufferedWriter은 PrintWriter과 비슷. FileWriter은 바로 file에 write하는데 앞 두개는 버퍼에 write했다가 file에 한꺼번에 write.
	    BufferedWriter bfw = new BufferedWriter(new FileWriter("Data.txt")); 
	   
	    for (int i = 0 ; i < table.getRowCount(); i++)  // table. 대신에 realtable.으로 했었는데 필요없네!
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
	            //파일 객체 생성, "C:\\Users\\hmkim\\eclipse-workspace\\myProject_1\\Data.txt" 해도 됨.
	            File file = new File("Data.txt");
	            //입력 스트림 생성
	            FileReader filereader = new FileReader(file);
	            //입력 버퍼 생성
	            BufferedReader bufReader = new BufferedReader(filereader);
	            String line = "";
	            
	            //list는 인터페이스, arraylist는 클래스, List<String[]> contentsLoad = new ArrayList<String[]>(); 해도 됨.
	            ArrayList<String[]> contentsLoad = new ArrayList<String[]>();
	            
	            while((line = bufReader.readLine()) != null) {
	            	//String[] oneLine = line.split("\t"); 이렇게 해도 됨.
	            	String[] oneLine = new String[7];
	            	oneLine = line.split("\t");
	            	
	            	contentsLoad.add(oneLine);
	            }
	            String[][] simpleArray = new String[contentsLoad.size()][7]; //[contentsLoad.size()][]로 써도 됨.
	            for(int i = 0;i<contentsLoad.size();i++) {
	            	String[] row = contentsLoad.get(i);
	            	//simpleArray[i] = new String[row.length];
	            	simpleArray[i] = new String[7];
	            	for(int j = 0; j<7;j++) {
	            		simpleArray[i][j] = row[j];
	            	}
	            }
	            
	            contents = simpleArray;
	           
	            //.readLine()은 끝에 개행문자를 읽지 않는다.            
	            bufReader.close();
	        }catch (FileNotFoundException e) {
	            // TODO: handle exception
	        	System.out.println(e);
	        }catch(IOException e){
	            System.out.println(e);
	        }
	}
	
}
