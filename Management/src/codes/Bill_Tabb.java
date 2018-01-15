package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

public class Bill_Tabb extends JFrame {

	private JPanel contentPane;
	Connection connection = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField cname;
	private JTextField mob;
	private JTextField tel;
	private JTextField fax;
	private JTextField email;
	private JTextField carnum;
	private JTextField cseries;
	private JTextField invoiceid;
	private JButton btnSearch;
	private JButton button_2;
	private JButton btnStartBilling;
	private JButton btnAddCustomer;
	private JButton btnUpdateCustomer;
	private JButton button_5;
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JLabel cid;
	private JTextArea caddress;
	private JTextField iname;
	private JTextField np;
	private JTextField price;
	private JTable table;
	private JTextField tamt;
	private JTextField tdue;
	private JTextField nq;
	private JTextField nr;
	private JTextField sa;
	private JTextField ess;
	private JTextField payment;
	private JComboBox itype;
	private JButton btnAddItem;
	private JButton btnDelete;
	private JButton button_3;
	private JLabel invoiceId;
	private JButton btnSubmit;
	private JPanel panel_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bill_Tabb frame = new Bill_Tabb();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void invoiceIdData(){
		try {
			int maxid;
			String query = "select MAX(Invoice_Number) as max_id from invoicedetails";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				maxid = rs.getInt("max_id");
				if (maxid==0) { 
					maxid = 1001;
					invoiceId.setText(maxid+"");
					//inv.setText(maxid+""); 
				}
				else {
					maxid = rs.getInt("max_id") + 1;
					invoiceId.setText(maxid+"");
					//inv.setText(maxid+"");
				}
			}
			pst.close();
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void resetFields(){
		btnSearch.setEnabled(true); 
		btnAddCustomer.setEnabled(true); 
		carnum.setEditable(true); 
		cname.setEditable(false); 
		mob.setEditable(false); 
		cseries.setEditable(false);  
		tel.setEditable(false); 
		fax.setEditable(false); 
		email.setEditable(false); 
		caddress.setEditable(false);
		btnStartBilling.setEnabled(true); 
		btnUpdateCustomer.setEnabled(true); 
		carnum.setText(""); 
		cname.setText(""); 
		mob.setText("");
		tel.setText("");
		fax.setText("");
		cseries.setText(""); 
		email.setText("");
		caddress.setText(""); 
		cid.setText(""); 
		carnum.requestFocusInWindow();
	}
	
	public void resetData(){
		resetFields();
		itype.setSelectedIndex(0); 
		btnAddCustomer.setEnabled(true);
		btnSearch.setEnabled(true); 
		btnDelete.setEnabled(true); 
		btnSubmit.setEnabled(true); 
		tamt.setText(""); 
		tdue.setText(""); 
		payment.setText("");
		cname.setEditable(false);
		iname.setEditable(true);
		np.setEditable(true);
		price.setEditable(true); 
		payment.setEditable(true);
		invoiceIdData();
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		while(model.getRowCount() > 0) {
			for(int i=0;i<model.getRowCount();i++) {
				model.removeRow(i); 
			}
		}
		carnum.requestFocusInWindow(); 
	}
	
	public void comboselect() {
		int d = itype.getSelectedIndex();
		if (d==0) { 
			JOptionPane.showMessageDialog(null, "Select any type to proceed."); 
		}
//		if (d==1) { 
//			Tabb s = new Tabb();
//			s.setVisible(true); 
//			this.setVisible(false); 
//		}
	}
	
	/**
	 * Create the frame.
	 */
	public Bill_Tabb() {
		Bill_print_saved ps = new Bill_print_saved();
		
		setResizable(false);
		setTitle("Billing :");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				tabbedPane.setSelectedIndex(0); 
				carnum.requestFocusInWindow(); 
				invoiceIdData();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				resetData();
			}
		});
		//Database Connection
				connection = Dbconn.dbConnection();
				
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1287, 702);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		tabbedPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("SansSerif", Font.BOLD, 15));
		panel.setBackground(new Color(0, 0, 51));
		tabbedPane.addTab("CUSTOMER", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(255, 255, 204));
		panel_2.setBounds(70, 52, 663, 471);
		panel.add(panel_2);
		
		JLabel label = new JLabel("Customer Name");
		label.setFont(new Font("Calibri", Font.BOLD, 14));
		label.setBounds(16, 102, 104, 16);
		panel_2.add(label);
		
		JLabel label_1 = new JLabel("Address");
		label_1.setFont(new Font("Calibri", Font.BOLD, 14));
		label_1.setBounds(16, 259, 62, 16);
		panel_2.add(label_1);
		
		cname = new JTextField();
		cname.setHorizontalAlignment(SwingConstants.LEFT);
		cname.setFont(new Font("Calibri", Font.BOLD, 15));
		cname.setEditable(false);
		cname.setColumns(10);
		cname.setBorder(new EmptyBorder(0, 5, 0, 0));
		cname.setBackground(new Color(153, 255, 255));
		cname.setBounds(173, 98, 461, 26);
		panel_2.add(cname);
		
		JLabel label_2 = new JLabel("Mobile");
		label_2.setFont(new Font("Calibri", Font.BOLD, 14));
		label_2.setBounds(16, 133, 68, 16);
		panel_2.add(label_2);
		
		mob = new JTextField();
		mob.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char c=e.getKeyChar();
				boolean max = mob.getText().length() >= 10;
				if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) )){ 
					getToolkit().beep();
					e.consume();
				}
				if(max){
					getToolkit().beep();
					e.consume();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				char c=e.getKeyChar();
				boolean max = mob.getText().length() >= 10;
				if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) )){ 
					getToolkit().beep();
					e.consume();
				}
				if(max){ 
					getToolkit().beep();
					e.consume();
				}
			}
		});
		mob.setHorizontalAlignment(SwingConstants.LEFT);
		mob.setFont(new Font("Calibri", Font.BOLD, 15));
		mob.setEditable(false);
		mob.setColumns(10);
		mob.setBorder(new EmptyBorder(0, 5, 0, 0));
		mob.setBackground(new Color(153, 255, 255));
		mob.setBounds(173, 129, 145, 26);
		panel_2.add(mob);
		
		JLabel label_3 = new JLabel("Telephone");
		label_3.setFont(new Font("Calibri", Font.BOLD, 14));
		label_3.setBounds(16, 165, 78, 16);
		panel_2.add(label_3);
		
		JLabel label_4 = new JLabel("Fax");
		label_4.setFont(new Font("Calibri", Font.BOLD, 14));
		label_4.setBounds(16, 197, 62, 16);
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("E-mail ID");
		label_5.setFont(new Font("Calibri", Font.BOLD, 14));
		label_5.setBounds(16, 228, 78, 16);
		panel_2.add(label_5);
		
		tel = new JTextField();
		tel.setHorizontalAlignment(SwingConstants.LEFT);
		tel.setFont(new Font("Calibri", Font.BOLD, 15));
		tel.setEditable(false);
		tel.setColumns(10);
		tel.setBorder(new EmptyBorder(0, 5, 0, 0));
		tel.setBackground(new Color(153, 255, 255));
		tel.setBounds(173, 161, 145, 26);
		panel_2.add(tel);
		
		fax = new JTextField();
		fax.setHorizontalAlignment(SwingConstants.LEFT);
		fax.setFont(new Font("Calibri", Font.BOLD, 15));
		fax.setEditable(false);
		fax.setColumns(10);
		fax.setBorder(new EmptyBorder(0, 5, 0, 0));
		fax.setBackground(new Color(153, 255, 255));
		fax.setBounds(173, 193, 145, 26);
		panel_2.add(fax);
		
		email = new JTextField();
		email.setHorizontalAlignment(SwingConstants.LEFT);
		email.setFont(new Font("Calibri", Font.BOLD, 15));
		email.setEditable(false);
		email.setColumns(10);
		email.setBorder(new EmptyBorder(0, 5, 0, 0));
		email.setBackground(new Color(153, 255, 255));
		email.setBounds(173, 224, 227, 26);
		panel_2.add(email);
		
		JLabel label_6 = new JLabel("ENTRIES");
		label_6.setForeground(Color.RED);
		label_6.setFont(new Font("Calibri", Font.BOLD, 18));
		label_6.setBounds(300, 17, 111, 16);
		panel_2.add(label_6);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 33, 650, 16);
		panel_2.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 392, 650, 16);
		panel_2.add(separator_1);
		
		carnum = new JTextField();
		carnum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				carnum.setText(carnum.getText().toUpperCase());
				String str = carnum.getText();
				if ( !(str.matches("[a-zA-Z0-9]+"))) {
	                  JOptionPane.showMessageDialog(null,"No SPACES Allowed. Please insert only characters.");
	                  carnum.setText("");
	            }
			}
		});
		carnum.setHorizontalAlignment(SwingConstants.LEFT);
		carnum.setForeground(Color.BLUE);
		carnum.setFont(new Font("Calibri", Font.BOLD, 15));
		carnum.setColumns(10);
		carnum.setBorder(new EmptyBorder(0, 5, 0, 0));
		carnum.setBackground(new Color(153, 255, 255));
		carnum.setBounds(173, 50, 145, 26);
		panel_2.add(carnum);
		
		JLabel label_7 = new JLabel("Vehicle Number");
		label_7.setFont(new Font("Calibri", Font.BOLD, 14));
		label_7.setBounds(16, 54, 93, 16);
		panel_2.add(label_7);
		
		JLabel label_8 = new JLabel(":");
		label_8.setFont(new Font("Calibri", Font.BOLD, 14));
		label_8.setBounds(132, 102, 9, 16);
		panel_2.add(label_8);
		
		JLabel label_9 = new JLabel(":");
		label_9.setFont(new Font("Calibri", Font.BOLD, 14));
		label_9.setBounds(131, 54, 9, 16);
		panel_2.add(label_9);
		
		JLabel label_10 = new JLabel(":");
		label_10.setFont(new Font("Calibri", Font.BOLD, 14));
		label_10.setBounds(131, 132, 10, 16);
		panel_2.add(label_10);
		
		JLabel label_11 = new JLabel(":");
		label_11.setFont(new Font("Calibri", Font.BOLD, 14));
		label_11.setBounds(132, 164, 9, 16);
		panel_2.add(label_11);
		
		JLabel label_12 = new JLabel(":");
		label_12.setFont(new Font("Calibri", Font.BOLD, 14));
		label_12.setBounds(132, 196, 9, 16);
		panel_2.add(label_12);
		
		JLabel label_13 = new JLabel(":");
		label_13.setFont(new Font("Calibri", Font.BOLD, 14));
		label_13.setBounds(132, 227, 9, 16);
		panel_2.add(label_13);
		
		JLabel label_14 = new JLabel(":");
		label_14.setFont(new Font("Calibri", Font.BOLD, 14));
		label_14.setBounds(131, 258, 10, 16);
		panel_2.add(label_14);
		
		btnAddCustomer = new JButton("ADD CUSTOMER");
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timeNow = Calendar.getInstance().getTimeInMillis();
				java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
				btnStartBilling.setEnabled(true); 
				btnStartBilling.requestFocusInWindow();
				btnAddCustomer.setEnabled(false); 
				
				try {
					String query3 = "insert into cdetails(Customer_Name,Phone,Car_Number,Date_of_join,Telephone,Fax,Email,Address,Series) values(?,?,?,?,?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query3);
					pst.setString(1, cname.getText());
					pst.setString(2, mob.getText());  
					pst.setString(3, carnum.getText());
					pst.setTimestamp(4, ts); 
					
					pst.setString(5, tel.getText());
					pst.setString(6, fax.getText()); 
					
					pst.setString(7, email.getText()); 
					pst.setString(8, caddress.getText()); 
					pst.setString(9, cseries.getText()); 
					pst.execute();
					pst.close();
					JOptionPane.showMessageDialog(null, "New Customer added successfully...", "Customer added in record :", JOptionPane.INFORMATION_MESSAGE); 
					
					String query2 = "select Id from cdetails where Car_Number='"+carnum.getText()+"'";
					PreparedStatement pst2 = connection.prepareStatement(query2);
					ResultSet rs2 = pst2.executeQuery();
					if(rs2.next()){
						int add1 = rs2.getInt("Id"); 
						String z = Integer.toString(add1);
						cid.setText(z); 
						cid.setVisible(true); 
					}
					pst2.close();
					rs2.close();
					JOptionPane.showMessageDialog(null, "Now, you can start billing...", "All OK", JOptionPane.INFORMATION_MESSAGE); 
					
				} catch (Exception e2) { 
					e2.printStackTrace();
					//JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		btnAddCustomer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					long timeNow = Calendar.getInstance().getTimeInMillis();
					java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
					btnStartBilling.setEnabled(true); 
					btnStartBilling.requestFocusInWindow();
					btnAddCustomer.setEnabled(false); 
					
					try {
						String query3 = "insert into cdetails(Customer_Name,Phone,Car_Number,Date_of_join,Telephone,Fax,Email,Address,Series) values(?,?,?,?,?,?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query3);
						pst.setString(1, cname.getText());
						pst.setString(2, mob.getText());  
						pst.setString(3, carnum.getText());
						pst.setTimestamp(4, ts); 
						
						pst.setString(5, tel.getText());
						pst.setString(6, fax.getText()); 
						
						pst.setString(7, email.getText()); 
						pst.setString(8, caddress.getText()); 
						pst.setString(9, cseries.getText()); 
						pst.execute();
						pst.close();
						JOptionPane.showMessageDialog(null, "New Customer added successfully...", "Customer added in record :", JOptionPane.INFORMATION_MESSAGE); 
						
						String query2 = "select Id from cdetails where Car_Number='"+carnum.getText()+"'";
						PreparedStatement pst2 = connection.prepareStatement(query2);
						ResultSet rs2 = pst2.executeQuery();
						if(rs2.next()){
							int add1 = rs2.getInt("Id"); 
							String z = Integer.toString(add1);
							cid.setText(z); 
							cid.setVisible(true); 
						}
						pst2.close();
						rs2.close();
						JOptionPane.showMessageDialog(null, "Now, you can start billing...", "All OK", JOptionPane.INFORMATION_MESSAGE); 
						
					} catch (Exception e2) { 
						e2.printStackTrace();
						//JOptionPane.showMessageDialog(null, e2.getMessage());
					}
				}
			}
		});
		btnAddCustomer.setBounds(276, 407, 124, 35);
		panel_2.add(btnAddCustomer);
		
		btnUpdateCustomer = new JButton("UPDATE CUSTOMER");
		btnUpdateCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String q = "update cdetails set Customer_Name=?, Phone=?, Telephone=?, Fax=?, Email=?, Address=?, Series=? where Id = '"+cid.getText()+"'";
					PreparedStatement p = connection.prepareStatement(q);
					p.setString(1, cname.getText());
					p.setString(2, mob.getText());
					
					if (tel.getText().equals("")) {  
						p.setInt(3, Integer.parseInt("0"));
					} else {
						p.setInt(3, Integer.parseInt(tel.getText()));
					}
					if (fax.getText().equals("")) { 
						p.setInt(4, Integer.parseInt("0")); 
					} else {
						p.setInt(4, Integer.parseInt(fax.getText()));  
					}
					p.setString(5, email.getText());
					p.setString(6, caddress.getText()); 
					p.setString(7, cseries.getText()); 
					p.executeUpdate();
					p.close();
					JOptionPane.showMessageDialog(null, "Customer data of: '"+cname.getText()+"' is edited successfully", "Data Altered", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) {
					//e2.printStackTrace();
					JOptionPane.showMessageDialog(null, e2.getMessage()); 
				}
				
				cname.setEditable(false); 
				mob.setEditable(false); 
				tel.setEditable(false); 
				fax.setEditable(false); 
				cseries.setEnabled(false);
				email.setEditable(false); 
				caddress.setEditable(false);
				btnUpdateCustomer.setEnabled(false); 
				btnStartBilling.setEnabled(true); 
				btnStartBilling.requestFocusInWindow();
			}
		});
		btnUpdateCustomer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					try {
						String q = "update cdetails set Customer_Name=?, Phone=?, Telephone=?, Fax=?, Email=?, Address=?, Series=? where Id = '"+cid.getText()+"'";
						PreparedStatement p = connection.prepareStatement(q);
						p.setString(1, cname.getText());
						p.setString(2, mob.getText());
						
						if (tel.getText().equals("")) {  
							p.setInt(3, Integer.parseInt("0"));
						} else {
							p.setInt(3, Integer.parseInt(tel.getText()));
						}
						if (fax.getText().equals("")) { 
							p.setInt(4, Integer.parseInt("0")); 
						} else {
							p.setInt(4, Integer.parseInt(fax.getText()));  
						}
						p.setString(5, email.getText());
						p.setString(6, caddress.getText()); 
						p.setString(7, cseries.getText()); 
						p.executeUpdate();
						p.close();
						JOptionPane.showMessageDialog(null, "Customer data of: '"+cname.getText()+"' is edited successfully", "Data Altered", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e2) {
						//e2.printStackTrace();
						JOptionPane.showMessageDialog(null, e2.getMessage()); 
					}
					
					cname.setEditable(false); 
					mob.setEditable(false); 
					tel.setEditable(false); 
					fax.setEditable(false); 
					cseries.setEnabled(false); 
					email.setEditable(false); 
					caddress.setEditable(false);
					btnUpdateCustomer.setEnabled(false); 
					btnStartBilling.setEnabled(true); 
					btnStartBilling.requestFocusInWindow();
				}
			}
		});
		btnUpdateCustomer.setBounds(400, 407, 147, 35);
		panel_2.add(btnUpdateCustomer);
		
		button_2 = new JButton("EDIT");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cname.setEditable(true); 
				mob.setEditable(true); 
				tel.setEditable(true); 
				fax.setEditable(true); 
				cseries.setEnabled(true); 
				email.setEditable(true); 
				caddress.setEditable(true);
				btnStartBilling.setEnabled(false); 
				btnUpdateCustomer.setEnabled(true); 
				cseries.requestFocusInWindow();
			}
		});
		button_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					cname.setEditable(true); 
					mob.setEditable(true); 
					tel.setEditable(true); 
					fax.setEditable(true); 
					cseries.setEnabled(true); 
					email.setEditable(true); 
					caddress.setEditable(true);
					btnStartBilling.setEnabled(false); 
					btnUpdateCustomer.setEnabled(true); 
					cseries.requestFocusInWindow();
				}
			}
		});
		button_2.setFont(new Font("SansSerif", Font.BOLD, 12));
		button_2.setBounds(590, 6, 67, 28);
		panel_2.add(button_2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(6, 82, 650, 10);
		panel_2.add(separator_2);
		
		btnSearch = new JButton("Search");
		btnSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					if (carnum.getText().equals("") || carnum.getText().isEmpty()) { 
						JOptionPane.showMessageDialog(null, "Enter Vehicle number to search.", "Missing field", JOptionPane.ERROR_MESSAGE); 
					} else {
						carnum.setEditable(false); 
						try {
							String query = "select * from cdetails where Car_Number='"+carnum.getText()+"'";
							PreparedStatement pst1 = connection.prepareStatement(query);
							ResultSet rs1 = pst1.executeQuery();
							if(rs1.next()){
								int add1 = rs1.getInt("Id"); 
								String z = Integer.toString(add1);
								cid.setText(z); 
								
								String add2 = rs1.getString("Customer_Name");
								cname.setText(add2); 
								
								String add3 = rs1.getString("Phone");
								mob.setText(add3);
								
								String add4 = rs1.getString("Telephone");
								tel.setText(add4);
								
								String add5 = rs1.getString("Fax");
								fax.setText(add5);
								
								String add6 = rs1.getString("Email");
								email.setText(add6);
								
								String add7 = rs1.getString("Address");
								caddress.setText(add7);
								
								String add8 = rs1.getString("Series");
								cseries.setText(add8); 
								
								//invoiceIdData();
								btnSearch.setEnabled(false); 
								btnAddCustomer.setEnabled(false);
								cname.setEditable(false); 
								mob.setEditable(false); 
								tel.setEditable(false); 
								fax.setEditable(false); 
								cseries.setEnabled(false); 
								email.setEditable(false); 
								caddress.setEditable(false);
								btnStartBilling.requestFocusInWindow();
							}
							else {
								JOptionPane.showMessageDialog(null, "Its a new customer"); 
								btnStartBilling.setEnabled(false); 
								cname.setEditable(true); 
								mob.setEditable(true); 
								tel.setEditable(true); 
								fax.setEditable(true); 
								cseries.setEditable(true);  
								email.setEditable(true); 
								caddress.setEditable(true);
								cseries.requestFocusInWindow();
							}
							pst1.close();
							rs1.close();
							
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					}
				}
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (carnum.getText().equals("") || carnum.getText().isEmpty()) { 
					JOptionPane.showMessageDialog(null, "Enter Vehicle number to search.", "Missing field", JOptionPane.ERROR_MESSAGE); 
				} else {
					carnum.setEditable(false); 
					try {
						String query = "select * from cdetails where Car_Number='"+carnum.getText()+"'";
						PreparedStatement pst1 = connection.prepareStatement(query);
						ResultSet rs1 = pst1.executeQuery();
						if(rs1.next()){
							int add1 = rs1.getInt("Id"); 
							String z = Integer.toString(add1);
							cid.setText(z); 
							
							String add2 = rs1.getString("Customer_Name");
							cname.setText(add2); 
							
							String add3 = rs1.getString("Phone");
							mob.setText(add3);
							
							String add4 = rs1.getString("Telephone");
							tel.setText(add4);
							
							String add5 = rs1.getString("Fax");
							fax.setText(add5);
							
							String add6 = rs1.getString("Email");
							email.setText(add6);
							
							String add7 = rs1.getString("Address");
							caddress.setText(add7);
							
							String add8 = rs1.getString("Series");
							cseries.setText(add8); 
							
							//invoiceIdData();
							btnSearch.setEnabled(false); 
							btnAddCustomer.setEnabled(false);
							cname.setEditable(false); 
							mob.setEditable(false); 
							cseries.setEnabled(false); 
							tel.setEditable(false); 
							fax.setEditable(false); 
							email.setEditable(false); 
							caddress.setEditable(false);
							btnStartBilling.requestFocusInWindow();
						}
						else {
							JOptionPane.showMessageDialog(null, "Its a new customer"); 
							btnStartBilling.setEnabled(false); 
							cname.setEditable(true); 
							mob.setEditable(true); 
							tel.setEditable(true); 
							cseries.setEditable(true); 
							fax.setEditable(true); 
							email.setEditable(true); 
							caddress.setEditable(true);
							cseries.requestFocusInWindow();
						}
						pst1.close();
						rs1.close();
						
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}
		});
		btnSearch.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setBounds(330, 49, 81, 28);
		panel_2.add(btnSearch);
		
		btnStartBilling = new JButton("Start Billing");
		btnStartBilling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (carnum.getText().equals("") || carnum.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Bill will made for a particular customer. Add one or search the available customer in record.", "Customer details missing :", JOptionPane.ERROR_MESSAGE); 
				} else {
					tabbedPane.setSelectedIndex(1); 
					if (tabbedPane.getSelectedIndex()==1) { 
						itype.requestFocusInWindow();
					}
				}
			}
		});
		btnStartBilling.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					if (carnum.getText().equals("") || carnum.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Bill will made for a particular customer. Add one or search the available customer in record.", "Customer details missing :", JOptionPane.ERROR_MESSAGE); 
					} else {
						tabbedPane.setSelectedIndex(1); 
						if (tabbedPane.getSelectedIndex()==1) { 
							itype.requestFocusInWindow();
						}
					}
				} 
			}
		});
		btnStartBilling.setForeground(Color.BLACK);
		btnStartBilling.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnStartBilling.setBackground(new Color(0, 204, 255));
		btnStartBilling.setBounds(173, 407, 104, 35);
		panel_2.add(btnStartBilling);
		
		button_5 = new JButton("RESET THIS");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFields();
			}
		});
		button_5.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					resetFields();
				}
			}
		});
		button_5.setBounds(547, 407, 98, 35);
		panel_2.add(button_5);
		
		JLabel label_15 = new JLabel("*");
		label_15.setForeground(Color.RED);
		label_15.setFont(new Font("Calibri", Font.BOLD, 15));
		label_15.setBounds(163, 50, 9, 20);
		panel_2.add(label_15);
		
		JLabel label_16 = new JLabel("*");
		label_16.setForeground(Color.RED);
		label_16.setFont(new Font("Calibri", Font.BOLD, 15));
		label_16.setBounds(163, 101, 9, 20);
		panel_2.add(label_16);
		
		JLabel label_17 = new JLabel("*");
		label_17.setForeground(Color.RED);
		label_17.setFont(new Font("Calibri", Font.BOLD, 15));
		label_17.setBounds(163, 258, 9, 20);
		panel_2.add(label_17);
		
		JLabel label_18 = new JLabel("*");
		label_18.setForeground(Color.RED);
		label_18.setFont(new Font("Calibri", Font.BOLD, 15));
		label_18.setBounds(163, 132, 9, 20);
		panel_2.add(label_18);
		
		JLabel label_19 = new JLabel("Series");
		label_19.setFont(new Font("Calibri", Font.BOLD, 14));
		label_19.setBounds(454, 54, 41, 16);
		panel_2.add(label_19);
		
		JLabel label_20 = new JLabel(":");
		label_20.setFont(new Font("Calibri", Font.BOLD, 14));
		label_20.setBounds(492, 54, 9, 16);
		panel_2.add(label_20);
		
		cseries = new JTextField();
		cseries.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cseries.setText(cseries.getText().toUpperCase()); 
			}
		});
		cseries.setHorizontalAlignment(SwingConstants.LEFT);
		cseries.setFont(new Font("Calibri", Font.BOLD, 15));
		cseries.setEditable(false);
		cseries.setColumns(10);
		cseries.setBorder(new EmptyBorder(0, 5, 0, 0));
		cseries.setBackground(new Color(153, 255, 255));
		cseries.setBounds(507, 50, 145, 26);
		panel_2.add(cseries);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setBounds(173, 259, 461, 109);
		panel_2.add(scrollPane);
		
		caddress = new JTextArea();
		caddress.setEditable(false);
		caddress.setBorder(new EmptyBorder(0, 5, 0, 0));
		caddress.setBackground(new Color(153, 255, 255));
		scrollPane.setViewportView(caddress);
		
		JLabel label_21 = new JLabel("CUSTOMER ID :");
		label_21.setHorizontalAlignment(SwingConstants.CENTER);
		label_21.setForeground(Color.YELLOW);
		label_21.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 24));
		label_21.setBounds(888, 138, 251, 43);
		panel.add(label_21);
		
		cid = new JLabel("");
		cid.setHorizontalAlignment(SwingConstants.CENTER);
		cid.setForeground(Color.YELLOW);
		cid.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 24));
		cid.setBounds(888, 182, 251, 43);
		panel.add(cid);
		
		invoiceid = new JTextField();
		invoiceid.setVisible(false);
		invoiceid.setBounds(158, 559, 122, 28);
		panel.add(invoiceid);
		invoiceid.setColumns(10);
		
		JLabel label_23 = new JLabel("ADD ALL CUSTOMER DETAILS HERE :-");
		label_23.setForeground(new Color(255, 255, 51));
		label_23.setFont(new Font("Calibri", Font.BOLD, 20));
		label_23.setBounds(535, 6, 323, 22);
		panel.add(label_23);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("BILLING", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label_22 = new JLabel("BILLING :");
		label_22.setHorizontalAlignment(SwingConstants.CENTER);
		label_22.setForeground(new Color(102, 0, 153));
		label_22.setFont(new Font("Centaur", Font.BOLD, 24));
		label_22.setBounds(6, 6, 174, 42);
		panel_1.add(label_22);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		separator_3.setBounds(6, 44, 174, 17);
		panel_1.add(separator_3);
		
		JButton btnDues = new JButton("PRINT SAVED INVOICE");
		btnDues.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				ps.setVisible(true); 
			}
		});
		btnDues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ps.setVisible(true);
			}
		});
		btnDues.setBounds(6, 60, 174, 34);
		panel_1.add(btnDues);
		
		JLabel label_24 = new JLabel("Invoice ID :");
		label_24.setHorizontalAlignment(SwingConstants.CENTER);
		label_24.setFont(new Font("Cambria", Font.PLAIN, 16));
		label_24.setBounds(38, 106, 104, 26);
		panel_1.add(label_24);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setBounds(192, 6, 2, 613);
		panel_1.add(separator_4);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(204, 6, 440, 187);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		itype = new JComboBox();
		itype.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				comboselect();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		itype.setModel(new DefaultComboBoxModel(new String[] {"", "WASH", "POLISH"}));
		itype.setBounds(119, 6, 150, 27);
		panel_3.add(itype);
		
		JLabel label_25 = new JLabel("Type :");
		label_25.setBounds(16, 11, 52, 14);
		panel_3.add(label_25);
		
		JLabel label_26 = new JLabel("Item Name :");
		label_26.setBounds(16, 48, 85, 14);
		panel_3.add(label_26);
		
		iname = new JTextField();
		iname.setForeground(Color.BLUE);
		iname.setColumns(10);
		iname.setBounds(119, 41, 297, 27);
		panel_3.add(iname);
		
		JLabel label_27 = new JLabel("No. of pieces :");
		label_27.setBounds(16, 74, 85, 14);
		panel_3.add(label_27);
		
		np = new JTextField();
		np.setForeground(Color.BLUE);
		np.setFont(new Font("Tahoma", Font.BOLD, 14));
		np.setColumns(10);
		np.setBounds(119, 67, 135, 27);
		panel_3.add(np);
		
		JLabel label_28 = new JLabel("Per piece price :");
		label_28.setBounds(16, 100, 96, 14);
		panel_3.add(label_28);
		
		price = new JTextField();
		price.setForeground(Color.BLUE);
		price.setFont(new Font("Tahoma", Font.BOLD, 14));
		price.setColumns(10);
		price.setBounds(119, 94, 135, 27);
		panel_3.add(price);
		
		btnAddItem = new JButton("ADD ITEM");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timeNow = Calendar.getInstance().getTimeInMillis();
				java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
				ess.setText(iname.getText()+" ("+itype.getSelectedItem()+")");
				float n,p,r = 0,t;  
				n = Float.parseFloat(np.getText());
				p = Float.parseFloat(price.getText());
				
				if (tamt.getText().isEmpty()) {
					r = (n * p);
					tamt.setText(r+"");
					
				} else if (!tamt.getText().isEmpty()){ 
					t = Float.parseFloat(tamt.getText()); 
					r = (n * p) + t;
					tamt.setText(r+"");
				} 
				
		/*	try {
					String query = "insert into isolds(Car_Number,Item_Name,Pieces,Price_per_item,Date_of_sold,Invoice_Id) values(?,?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, carnum.getText());
					pst.setString(2, ess.getText());
					pst.setFloat(3, Float.parseFloat(np.getText()));
					pst.setFloat(4, Float.parseFloat(price.getText())); 
					pst.setString(5, ts); 
					pst.setInt(6, Integer.parseInt(invoiceid.getText()));  
					pst.execute();
					pst.close();
					np.setText(" ");
					price.setText(" ");
					iname.setText(" "); 
					refreshtable();
					iname.requestFocusInWindow();
				} catch (Exception e2) { 
					e2.printStackTrace();
				}
			*/
		 
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.addRow(new Object[]{carnum.getText(),ess.getText(),np.getText(),price.getText(),ts,invoiceId.getText()}); 
				np.setText("");
				price.setText("");
				iname.setText("");
				iname.requestFocusInWindow();
			}
		});
		btnAddItem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					long timeNow = Calendar.getInstance().getTimeInMillis();
					java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
					ess.setText(iname.getText()+" ("+itype.getSelectedItem()+")");
					float n,p,r = 0,t;  
					n = Float.parseFloat(np.getText());
					p = Float.parseFloat(price.getText());
					
					if (tamt.getText().isEmpty()) {
						r = (n * p);
						tamt.setText(r+"");
						
					} else if (!tamt.getText().isEmpty()){ 
						t = Float.parseFloat(tamt.getText()); 
						r = (n * p) + t;
						tamt.setText(r+"");
					} 
					
			/*	try {
						String query = "insert into isolds(Car_Number,Item_Name,Pieces,Price_per_item,Date_of_sold,Invoice_Id) values(?,?,?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, carnum.getText());
						pst.setString(2, ess.getText());
						pst.setFloat(3, Float.parseFloat(np.getText()));
						pst.setFloat(4, Float.parseFloat(price.getText())); 
						pst.setString(5, ts); 
						pst.setInt(6, Integer.parseInt(invoiceid.getText()));  
						pst.execute();
						pst.close();
						np.setText(" ");
						price.setText(" ");
						iname.setText(" "); 
						refreshtable();
						iname.requestFocusInWindow();
					} catch (Exception e2) { 
						e2.printStackTrace();
					}
				*/
			 
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					model.addRow(new Object[]{carnum.getText(),ess.getText(),np.getText(),price.getText(),ts,invoiceId.getText()}); 
					np.setText("");
					price.setText("");
					iname.setText("");
					iname.requestFocusInWindow();
			
				} else {
				JOptionPane.showMessageDialog(null, "Press enter to continue.");
			}
			}
		});
		btnAddItem.setBounds(119, 133, 135, 35);
		panel_3.add(btnAddItem);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(206, 202, 1047, 417);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(16, 47, 1014, 354);
		panel_4.add(scrollPane_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int s = table.getSelectedRow();
				
				nq.setText(model.getValueAt(s, 2).toString());
				nr.setText(model.getValueAt(s, 3).toString()); 
				
				float a,b,c,r=0;
				a = Float.parseFloat(nq.getText());
				b = Float.parseFloat(nr.getText());
				c = Float.parseFloat(tamt.getText());
				r = a * b;
				sa.setText(""+r); 
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Vehicle Number", "Item Used", "Quantity", "Rate", "Date-Time", "Invoice ID"
				}
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(96);
			table.getColumnModel().getColumn(1).setPreferredWidth(194);
			table.getColumnModel().getColumn(3).setPreferredWidth(60);
			table.getColumnModel().getColumn(4).setPreferredWidth(115); 
		scrollPane_1.setViewportView(table);
		
		btnDelete = new JButton("Delete Item sold ");
		btnDelete.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					float a,b,c=0;
					a = Float.parseFloat(sa.getText());
					b = Float.parseFloat(tamt.getText());
					c = b - a;
					tamt.setText(""+c); 
					
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					try {
						int selectedRowIndex = table.getSelectedRow();
						model.removeRow(selectedRowIndex); 
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage()); 
					}	
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float a,b,c=0;
				a = Float.parseFloat(sa.getText());
				b = Float.parseFloat(tamt.getText());
				c = b - a;
				tamt.setText(""+c); 
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				try {
					int selectedRowIndex = table.getSelectedRow();
					model.removeRow(selectedRowIndex); 
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage()); 
				}
			}
		});
		btnDelete.setBounds(16, 6, 121, 36);
		panel_4.add(btnDelete);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(656, 6, 597, 187);
		panel_1.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel label_29 = new JLabel("Total Amount :");
		label_29.setBounds(314, 31, 85, 14);
		panel_5.add(label_29);
		
		tamt = new JTextField();
		tamt.setHorizontalAlignment(SwingConstants.CENTER);
		tamt.setForeground(Color.BLACK);
		tamt.setFont(new Font("Tahoma", Font.BOLD, 14));
		tamt.setEditable(false);
		tamt.setColumns(10);
		tamt.setBounds(395, 23, 153, 27);
		panel_5.add(tamt);
		
		JLabel label_30 = new JLabel("Total Due :");
		label_30.setBounds(314, 59, 70, 14);
		panel_5.add(label_30);
		
		tdue = new JTextField();
		tdue.setHorizontalAlignment(SwingConstants.CENTER);
		tdue.setForeground(Color.BLACK);
		tdue.setFont(new Font("Tahoma", Font.BOLD, 14));
		tdue.setEditable(false);
		tdue.setColumns(10);
		tdue.setBounds(395, 52, 153, 27);
		panel_5.add(tdue);
		
		button_3 = new JButton("Calculate");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float a,b,c;
				a = Float.parseFloat(payment.getText());
				b = Float.parseFloat(tamt.getText());
				c = b - a;
				if(c < 0){
					JOptionPane.showMessageDialog(null, "Please enter a good amount", "Amount goes negative.", JOptionPane.ERROR_MESSAGE);  
				} else {
					String z = Float.toString(c);
					tdue.setText(z);
				}
				
			
			}
		});
		button_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					float a,b,c;
					a = Float.parseFloat(payment.getText());
					b = Float.parseFloat(tamt.getText());
					c = b - a;
					if(c < 0){
						JOptionPane.showMessageDialog(null, "Please enter a good amount", "Amount goes negative.", JOptionPane.ERROR_MESSAGE);  
					} else {
						String z = Float.toString(c);
						tdue.setText(z);
					}
					
				}
			}
		});
		button_3.setBounds(99, 59, 121, 36);
		panel_5.add(button_3);
		
		JLabel label_31 = new JLabel("Payment :");
		label_31.setBounds(26, 31, 63, 14);
		panel_5.add(label_31);
		
		payment = new JTextField();
		payment.setHorizontalAlignment(SwingConstants.CENTER);
		payment.setForeground(Color.BLACK);
		payment.setFont(new Font("Tahoma", Font.BOLD, 14));
		payment.setColumns(10);
		payment.setBackground(new Color(51, 255, 255));
		payment.setBounds(99, 23, 153, 27);
		panel_5.add(payment);
		
		JButton button_4 = new JButton("PRINT");
		button_4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					try {
						JasperDesign jd = null; 
						jd = JRXmlLoader.load(getClass().getResourceAsStream("../Invoice_1.jrxml"));
						String sql = "SELECT i.Total_Amount, c.Customer_Name, i.Date_of_Invoice, i.Car_Number, s.Item_Name, s.Pieces, s.Price_per_item , i.Invoice_Number, d.Last_Paid, d.Total_Due, "
								+ "c.Phone, c.Telephone, c.Fax, c.Email, c.Address, c.Series FROM invoicedetails as i "
								+ "INNER JOIN isolds as s ON s.Invoice_Id=i.Invoice_Number "
								+ "INNER JOIN cdetails as c ON c.Id = i.Customer_Id "
								+ "INNER JOIN dues as d on d.Invoice_Number = i.Invoice_Number "
								+ "where i.Invoice_Number  = '"+invoiceId.getText()+"'";
						JRDesignQuery newQuery = new JRDesignQuery();
						newQuery.setText(sql); 
						jd.setQuery(newQuery);
						//JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("src/Invoice_1.jrxml")); 
						JasperReport jr = JasperCompileManager.compileReport(jd);
						JasperPrint jp = JasperFillManager.fillReport(jr, null,connection);
						JasperViewer.viewReport(jp,false); 
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				} else {
					
				}
			}
		});
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JasperDesign jd;
					jd = JRXmlLoader.load(getClass().getResourceAsStream("../Invoice_1.jrxml"));
					String sql = "SELECT i.Total_Amount, c.Customer_Name, i.Date_of_Invoice, i.Car_Number, s.Item_Name, s.Pieces, s.Price_per_item , i.Invoice_Number, d.Last_Paid, d.Total_Due, "
							+ "c.Phone, c.Telephone, c.Fax, c.Email, c.Address, c.Series FROM invoicedetails as i "
							+ "INNER JOIN isolds as s ON s.Invoice_Id=i.Invoice_Number "
							+ "INNER JOIN cdetails as c ON c.Id = i.Customer_Id "
							+ "INNER JOIN dues as d on d.Invoice_Number = i.Invoice_Number "
							+ "where i.Invoice_Number  = '"+invoiceId.getText()+"'";
					JRDesignQuery newQuery = new JRDesignQuery();
					newQuery.setText(sql); 
					jd.setQuery(newQuery);
					JasperReport jr = JasperCompileManager.compileReport(jd);
					JasperPrint jp = JasperFillManager.fillReport(jr, null,connection);
					JasperViewer.viewReport(jp,false); 
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_4.setBackground(new Color(204, 204, 204));
		button_4.setBounds(338, 131, 135, 36);
		panel_5.add(button_4);
		
		JButton button_6 = new JButton("RESET");
		button_6.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					resetData();
				}
			}
		});
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetData();
			}
		});
		button_6.setBounds(483, 131, 96, 36);
		panel_5.add(button_6);
		
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					long timeNow = Calendar.getInstance().getTimeInMillis();
					java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
					if (tdue.getText().equals("0.0")) {  
						JOptionPane.showMessageDialog(null, "This customer has no due amount to be paid : Rs.'"+tdue.getText()+"'");
						
						try {
							String query2 = "insert into dues(Customer_Id,Customer_Name,Car_Number,Total_Due,Last_Paid,Invoice_Number) values(?,?,?,?,?,?)";
							PreparedStatement pst2 = connection.prepareStatement(query2);
							pst2.setInt(1, Integer.parseInt(cid.getText()));
							pst2.setString(2, cname.getText());
							pst2.setString(3, carnum.getText());
							pst2.setFloat(4, Float.parseFloat(tdue.getText())); 
							pst2.setFloat(5, Float.parseFloat(payment.getText()));
							pst2.setInt(6, Integer.parseInt(invoiceId.getText()));
							pst2.execute();
							pst2.close();
							if (!tdue.getText().isEmpty()) {  
								JOptionPane.showMessageDialog(null, "This customer have due amount to be paid : Rs.'"+tdue.getText()+"'"); 
							}
						} catch (Exception e2) {
							//JOptionPane.showMessageDialog(null, e2.getMessage()); 
							e2.printStackTrace();
						}
					} else {
						try {
							String query2 = "insert into dues(Customer_Id,Customer_Name,Car_Number,Total_Due,Last_Paid,Invoice_Number) values(?,?,?,?,?,?)";
							PreparedStatement pst2 = connection.prepareStatement(query2);
							pst2.setInt(1, Integer.parseInt(cid.getText()));
							pst2.setString(2, cname.getText());
							pst2.setString(3, carnum.getText());
							pst2.setFloat(4, Float.parseFloat(tdue.getText())); 
							pst2.setFloat(5, Float.parseFloat(payment.getText()));
							pst2.setInt(6, Integer.parseInt(invoiceId.getText()));
							pst2.execute();
							pst2.close();
							if (!tdue.getText().isEmpty()) {  
								JOptionPane.showMessageDialog(null, "This customer have due amount to be paid : Rs.'"+tdue.getText()+"'"); 
							}
						} catch (Exception e2) {
							//JOptionPane.showMessageDialog(null, e2.getMessage()); 
							e2.printStackTrace();
						}
					}
					
					try {
						String query3 = "insert into invoiceDetails(Invoice_Number,Customer_Id,Date_of_Invoice,Total_Amount,Car_Number,Payment_Made,Grand_Total,Due) values(?,?,?,?,?,?,?,?)";
						PreparedStatement pst3 = connection.prepareStatement(query3);
						pst3.setInt(1, Integer.parseInt(invoiceId.getText()));  
						pst3.setInt(2, Integer.parseInt(cid.getText()));  
						pst3.setTimestamp(3, ts);
						pst3.setFloat(4, Float.parseFloat(tamt.getText())); 
						pst3.setString(5, carnum.getText()); 
						pst3.setFloat(6, Float.parseFloat(payment.getText()));
						pst3.setFloat(7, Float.parseFloat(tamt.getText()));
						pst3.setFloat(8, Float.parseFloat(tdue.getText()));
						pst3.execute();
						pst3.close();
						
					} catch (Exception e2) { 
						e2.printStackTrace();
					}
									
					//batch Insert starts her
					int rows = table.getRowCount();
					
					for(int row = 0; row<rows ; row++)
					{
						String car = (String) table.getValueAt(row, 0);
						String name = (String) table.getValueAt(row, 1);
						String piece = (String) table.getValueAt(row, 2);
						String price = (String) table.getValueAt(row, 3);
						String in_id = (String) table.getValueAt(row, 5);
							
						try{
							String query = "insert into isolds(Car_Number,Item_Name,Pieces,Price_per_Item,Date_of_sold,Invoice_Id) values(?,?,?,?,?,?)" ;
							connection.setAutoCommit(false);
							PreparedStatement stmt = connection.prepareStatement(query);
							stmt.setString(1, car); 
							stmt.setString(2, name); 
							stmt.setString(3, piece); 
							stmt.setString(4,price); 
							stmt.setTimestamp(5,ts);
							stmt.setString(6,in_id);
							stmt.addBatch();
							stmt.executeBatch();
							connection.commit();
					 }
					 catch(Exception ex) {
						 ex.printStackTrace();
						 //JOptionPane.showMessageDialog(null, "Cannot save. "+ ex.getMessage());
					 }   
					}
					//batch insert ends here
					btnSubmit.setEnabled(false); 
					btnDelete.setEnabled(false); 
					
				}
			}
		});
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timeNow = Calendar.getInstance().getTimeInMillis();
				java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
				if (tdue.getText().equals("0.0")) {  
					JOptionPane.showMessageDialog(null, "This customer has no due amount to be paid : Rs.'"+tdue.getText()+"'");
					
					try {
						String query2 = "insert into dues(Customer_Id,Customer_Name,Car_Number,Total_Due,Last_Paid,Invoice_Number) values(?,?,?,?,?,?)";
						PreparedStatement pst2 = connection.prepareStatement(query2);
						pst2.setInt(1, Integer.parseInt(cid.getText()));
						pst2.setString(2, cname.getText());
						pst2.setString(3, carnum.getText());
						pst2.setFloat(4, Float.parseFloat(tdue.getText())); 
						pst2.setFloat(5, Float.parseFloat(payment.getText()));
						pst2.setInt(6, Integer.parseInt(invoiceId.getText()));
						pst2.execute();
						pst2.close();
						if (!tdue.getText().isEmpty()) {  
							JOptionPane.showMessageDialog(null, "This customer have due amount to be paid : Rs.'"+tdue.getText()+"'"); 
						}
					} catch (Exception e2) {
						//JOptionPane.showMessageDialog(null, e2.getMessage()); 
						e2.printStackTrace();
					}
				} else {
					try {
						String query2 = "insert into dues(Customer_Id,Customer_Name,Car_Number,Total_Due,Last_Paid,Invoice_Number) values(?,?,?,?,?,?)";
						PreparedStatement pst2 = connection.prepareStatement(query2);
						pst2.setInt(1, Integer.parseInt(cid.getText()));
						pst2.setString(2, cname.getText());
						pst2.setString(3, carnum.getText());
						pst2.setFloat(4, Float.parseFloat(tdue.getText())); 
						pst2.setFloat(5, Float.parseFloat(payment.getText()));
						pst2.setInt(6, Integer.parseInt(invoiceId.getText()));
						pst2.execute();
						pst2.close();
						if (!tdue.getText().isEmpty()) {  
							JOptionPane.showMessageDialog(null, "This customer have due amount to be paid : Rs.'"+tdue.getText()+"'"); 
						}
					} catch (Exception e2) {
						e2.printStackTrace();
						//JOptionPane.showMessageDialog(null, e2.getMessage()); 
					}
				}
				
				try {
					String query3 = "insert into invoiceDetails(Invoice_Number,Customer_Id,Date_of_Invoice,Total_Amount,Car_Number,Payment_Made,Grand_Total,Due) values(?,?,?,?,?,?,?,?)";
					PreparedStatement pst3 = connection.prepareStatement(query3);
					pst3.setInt(1, Integer.parseInt(invoiceId.getText()));  
					pst3.setInt(2, Integer.parseInt(cid.getText()));  
					pst3.setTimestamp(3, ts);
					pst3.setFloat(4, Float.parseFloat(tamt.getText())); 
					pst3.setString(5, carnum.getText()); 
					pst3.setFloat(6, Float.parseFloat(payment.getText()));
					pst3.setFloat(7, Float.parseFloat(tamt.getText()));
					pst3.setFloat(8, Float.parseFloat(tdue.getText()));
					pst3.execute();
					pst3.close();
					
				} catch (Exception e2) { 
					e2.printStackTrace();
				}
								
				//batch Insert starts her
				int rows = table.getRowCount();
				
				for(int row = 0; row<rows ; row++)
				{
					String car = (String) table.getValueAt(row, 0);
					String name = (String) table.getValueAt(row, 1);
					String piece = (String) table.getValueAt(row, 2);
					String price = (String) table.getValueAt(row, 3);
					String in_id = (String) table.getValueAt(row, 5);
						
					try{
						String query = "insert into isolds(Car_Number,Item_Name,Pieces,Price_per_Item,Date_of_sold,Invoice_Id) values(?,?,?,?,?,?)" ;
						connection.setAutoCommit(false);
						PreparedStatement stmt = connection.prepareStatement(query);
						stmt.setString(1, car); 
						stmt.setString(2, name); 
						stmt.setString(3, piece); 
						stmt.setString(4,price); 
						stmt.setTimestamp(5,ts);
						stmt.setString(6,in_id);
						stmt.addBatch();
						stmt.executeBatch();
						connection.commit();
				 }
				 catch(Exception ex) {
					 ex.printStackTrace();
					 //JOptionPane.showMessageDialog(null, "Cannot save. "+ ex.getMessage());
				 }   
				}
				//batch insert ends here
				btnSubmit.setEnabled(false); 
				btnDelete.setEnabled(false); 
			}
		});
		btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnSubmit.setBounds(224, 131, 102, 36);
		panel_5.add(btnSubmit);
		
		nq = new JTextField();
		nq.setVisible(false);
		nq.setBounds(6, 367, 163, 34);
		panel_1.add(nq);
		nq.setColumns(10);
		
		nr = new JTextField();
		nr.setVisible(false);
		nr.setColumns(10);
		nr.setBounds(6, 415, 163, 34);
		panel_1.add(nr);
		
		sa = new JTextField();
		sa.setVisible(false);
		sa.setColumns(10);
		sa.setBounds(6, 461, 163, 34);
		panel_1.add(sa);
		
		ess = new JTextField();
		ess.setVisible(false);
		ess.setColumns(10);
		ess.setBounds(6, 516, 163, 34);
		panel_1.add(ess);
		
		invoiceId = new JLabel("");
		invoiceId.setFont(new Font("Century Gothic", Font.BOLD, 17));
		invoiceId.setHorizontalAlignment(SwingConstants.CENTER);
		invoiceId.setBounds(6, 137, 174, 34);
		panel_1.add(invoiceId);
		panel_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{itype, iname, np, price, btnAddItem, payment, button_3, btnSubmit, button_4, button_6}));
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{carnum, btnSearch, cseries, cname, mob, tel, fax, email, caddress, btnStartBilling, btnAddCustomer, btnUpdateCustomer, button_5, button_2, 
				carnum, itype, iname, np, price, btnAddItem, payment, button_3, btnSubmit, button_4, button_6, btnDues, itype}));
	}
}
