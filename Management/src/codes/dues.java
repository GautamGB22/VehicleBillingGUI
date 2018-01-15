package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class dues extends JFrame {

	private JPanel contentPane;
	Connection connection = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField carnum;
	private JTable table;
	private JTextField cusid;
	private JTextField cname;
	private JTextField last_paid;
	private JTextField tamt;
	private JTextField payment;
	JButton btnNewButton = new JButton("MAKE PAYMENT");
	private JTextField carnumtxt;
	JButton btnSubmit = new JButton("SUBMIT");
	JLabel lblClock = new JLabel("");
	JLabel inm = new JLabel("");
	private JTable table_1;
	
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
					dues frame = new dues();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void refreshTable(){
		try {
			String query = "select Invoice_Number,Customer_Id,Customer_Name,Car_Number as Vehicle_Number,Total_Due,Last_Paid from dues "
					+ "where Total_Due > 0 order by Invoice_Number desc";
			pst = connection.prepareStatement(query);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	public void duedata(){
		try {
			String query1 = "select Customer_Name as 'Customer Name', Last_Paid, "
					+ "Last_Visited as 'Visited Dates', Customer_Id as 'Customer Id' from alldates where Car_Number= '"+carnumtxt.getText()+"' order by Last_Visited desc";
			PreparedStatement pst3 = connection.prepareStatement(query1);
			ResultSet rs3 = pst3.executeQuery();
			table_1.setModel(DbUtils.resultSetToTableModel(rs3)); 
			table_1.getColumnModel().getColumn(0).setPreferredWidth(107);
			table_1.getColumnModel().getColumn(1).setPreferredWidth(205);
			table_1.getColumnModel().getColumn(2).setPreferredWidth(150);
			table_1.getColumnModel().getColumn(3).setPreferredWidth(108);
			pst3.close();
			rs3.close();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage()); 
		}
	}
	
	
	public void clock(){
		Thread clock = new Thread(){
			public void run(){
				try {
					for(;;){
					Calendar cal = new GregorianCalendar();
					int day = cal.get(Calendar.DAY_OF_MONTH);
					int mon = cal.get(Calendar.MONTH);
					int year = cal.get(Calendar.YEAR);
					
					int second = cal.get(Calendar.SECOND);
					int minute = cal.get(Calendar.MINUTE);
					int hour = cal.get(Calendar.HOUR_OF_DAY);
					
					lblClock.setText(day+"/"+mon+"/"+year+"    "+hour+":"+minute+":"+second); 
					sleep(1000);
					}
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, e.getMessage()); 
				}
			}
		};
		clock.start();
	}
	
	public void allDate() {
		try {
			String query1 = "select Customer_Name as 'Customer Name', Last_Paid, "
					+ "Last_Visited as 'Visited Dates', Customer_Id as 'Customer Id' from alldates where Car_Number = '"+carnumtxt.getText()+"' order by Last_Visited desc";
			PreparedStatement pst3 = connection.prepareStatement(query1);
			ResultSet rs3 = pst3.executeQuery();
			table_1.setModel(DbUtils.resultSetToTableModel(rs3)); 
			table_1.getColumnModel().getColumn(0).setPreferredWidth(107);
			table_1.getColumnModel().getColumn(1).setPreferredWidth(205);
			table_1.getColumnModel().getColumn(2).setPreferredWidth(150);
			table_1.getColumnModel().getColumn(3).setPreferredWidth(108);
			pst3.close();
			rs3.close();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage()); 
		}
	}
	
	
	/**
	 * Create the frame.
	 */
	public dues() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				DefaultTableModel model2 = (DefaultTableModel)table_1.getModel();
				while(model2.getRowCount() > 0) {
					for(int i=0;i<model2.getRowCount();i++) {
						model2.removeRow(i); 
					}
				}
			}
		});
		
		
		setResizable(false);
		setTitle("Due Amounts :");
		connection = Dbconn.dbConnection();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1212, 690);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Search Due Amounts :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(10, 11, 311, 95);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCarNumber = new JLabel("Vehicle Number :");
		lblCarNumber.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblCarNumber.setBounds(17, 40, 116, 14);
		panel.add(lblCarNumber);
		
		carnum = new JTextField();
		carnum.setHorizontalAlignment(SwingConstants.CENTER);
		carnum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String query = "select Invoice_Number,Customer_Id,Customer_Name,Car_Number as Vehicle_Number,Total_Due,Last_Paid from dues where Car_Number Like '%"+carnum.getText()+"%' order by Invoice_Number desc";
					pst = connection.prepareStatement(query);
					rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
					rs.close();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage()); 
				}
			}
		});
		carnum.setFont(new Font("Calibri", Font.PLAIN, 14));
		carnum.setBounds(137, 33, 160, 29);
		panel.add(carnum);
		carnum.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search results :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_1.setBounds(10, 109, 584, 541);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 62, 564, 468);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (btnNewButton.isEnabled()==true) { 
					JOptionPane.showMessageDialog(null, "First Click on Make Payment, then proceed. ", "Step Missing", JOptionPane.ERROR_MESSAGE);
					
				} else {
					try {
						int row = table.getSelectedRow();
						String Table_click = (table.getModel().getValueAt(row, 0).toString());
						String sql = "select * from dues where Invoice_Number='"+Table_click+"'";
						pst = connection.prepareStatement(sql);
						rs = pst.executeQuery();
						if (rs.next()) {
							int add1 = rs.getInt("Customer_Id"); 
							String z = Integer.toString(add1);
							cusid.setText(z); 
							String add2 = rs.getString("Customer_Name");
							cname.setText(add2); 
							String add3 = rs.getString("Car_Number");
							carnumtxt.setText(add3);
							Float add4 = rs.getFloat("Total_Due");
							String d = Float.toString(add4); 
							tamt.setText(d);  
							Float add5 = rs.getFloat("Last_Paid");
							String g = Float.toString(add5);
							last_paid.setText(g); 
							int x = rs.getInt("Invoice_Number");
							String f = Integer.toString(x);
							inm.setText(f); 
						}
						allDate();
						pst.close();
						rs.close();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage()); 
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnLoad = new JButton("LOAD");
		btnLoad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					refreshTable();
				} else {
					JOptionPane.showMessageDialog(null, "Press enter key or click the button.."); 
				}
			}
		});
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("/l.png")).getImage();
		btnLoad.setIcon(new ImageIcon(img)); 
		btnLoad.setBounds(10, 22, 89, 37);
		panel_1.add(btnLoad);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Payment made on due amounts :", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 153)));
		panel_2.setBounds(643, 11, 553, 639);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel label = new JLabel("Car Number :");
		label.setFont(new Font("Calibri", Font.PLAIN, 16));
		label.setBounds(96, 32, 94, 14);
		panel_2.add(label);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 57, 533, 14);
		panel_2.add(separator);
		
		JLabel lblCustomerId = new JLabel("Customer ID       :");
		lblCustomerId.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblCustomerId.setBounds(23, 102, 112, 14);
		panel_2.add(lblCustomerId);
		
		JLabel lblCustomerName = new JLabel("Customer Name :");
		lblCustomerName.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblCustomerName.setBounds(23, 134, 112, 14);
		panel_2.add(lblCustomerName);
		
		JLabel lblTotalAmount = new JLabel("Last Due Paid");
		lblTotalAmount.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblTotalAmount.setBounds(23, 165, 94, 14);
		panel_2.add(lblTotalAmount);
		
		cusid = new JTextField();
		cusid.setHorizontalAlignment(SwingConstants.CENTER);
		cusid.setFont(new Font("Calibri", Font.PLAIN, 14));
		cusid.setColumns(10);
		cusid.setBounds(145, 95, 158, 29);
		panel_2.add(cusid);
		
		cname = new JTextField();
		cname.setFont(new Font("Calibri", Font.PLAIN, 14));
		cname.setColumns(10);
		cname.setBounds(145, 127, 382, 29);
		panel_2.add(cname);
		
		JLabel lblTotalAmount_1 = new JLabel("Total Amount");
		lblTotalAmount_1.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblTotalAmount_1.setBounds(23, 197, 94, 14);
		panel_2.add(lblTotalAmount_1);
		
		last_paid = new JTextField();
		last_paid.setHorizontalAlignment(SwingConstants.CENTER);
		last_paid.setFont(new Font("Calibri", Font.PLAIN, 14));
		last_paid.setColumns(10);
		last_paid.setBounds(145, 158, 158, 29);
		panel_2.add(last_paid);
		
		tamt = new JTextField();
		tamt.setHorizontalAlignment(SwingConstants.CENTER);
		tamt.setFont(new Font("Calibri", Font.PLAIN, 14));
		tamt.setColumns(10);
		tamt.setBounds(145, 190, 158, 29);
		panel_2.add(tamt);
		
		JLabel label_1 = new JLabel(":");
		label_1.setFont(new Font("Calibri", Font.PLAIN, 16));
		label_1.setBounds(131, 165, 15, 14);
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel(":");
		label_2.setFont(new Font("Calibri", Font.PLAIN, 16));
		label_2.setBounds(131, 197, 15, 14);
		panel_2.add(label_2);
		
		payment = new JTextField();
		payment.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				}
			});
		
		payment.setHorizontalAlignment(SwingConstants.CENTER);
		payment.setForeground(new Color(0, 0, 204));
		payment.setBackground(new Color(51, 255, 255));
		payment.setFont(new Font("Calibri", Font.BOLD, 18));
		payment.setColumns(10);
		payment.setBounds(145, 281, 213, 29);
		panel_2.add(payment);
		
		JLabel lblPayment = new JLabel("Payment :");
		lblPayment.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblPayment.setBounds(65, 288, 80, 14);
		panel_2.add(lblPayment);
		
		JButton btnPay = new JButton("CALCULATE");
		btnPay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					float a,b,c,s,r,o;
					a = Float.parseFloat(payment.getText());
					b = Float.parseFloat(tamt.getText());
					c = b - a;
					if(c < 0){
						JOptionPane.showMessageDialog(null, "Please enter a good amount", "Amount goes negative.", JOptionPane.ERROR_MESSAGE); 
						
					} else {
						String z = Float.toString(c);
						tamt.setText(z);
						JOptionPane.showMessageDialog(null, "Due amount : Rs.'"+payment.getText()+"' is received from '"+cname.getText()+"', please click SUBMIT for confirm.",
								"Amount Paid :", JOptionPane.INFORMATION_MESSAGE); 
						
					}
					
				}
			}
		});
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float a,b,c,s,r,o;
				a = Float.parseFloat(payment.getText());
				b = Float.parseFloat(tamt.getText());
				c = b - a;
				if(c < 0){
					JOptionPane.showMessageDialog(null, "Please enter a good amount", "Amount goes negative.", JOptionPane.ERROR_MESSAGE); 
					
				} else {
					String z = Float.toString(c);
					tamt.setText(z);
					JOptionPane.showMessageDialog(null, "Due amount : Rs.'"+payment.getText()+"' is received from '"+cname.getText()+"', please click SUBMIT for confirm.",
							"Amount Paid :", JOptionPane.INFORMATION_MESSAGE); 
					
				}
				
			}
		});
		btnPay.setFont(new Font("Calibri", Font.BOLD, 15));
		btnPay.setBounds(368, 277, 112, 38);
		panel_2.add(btnPay);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 248, 533, 14);
		panel_2.add(separator_1);
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					payment.requestFocusInWindow();
					btnNewButton.setEnabled(false); 
					payment.setEditable(true); 
					carnum.setEditable(false); 
					JOptionPane.showMessageDialog(null, "Select any row from table for payment", "Making Payment", JOptionPane.INFORMATION_MESSAGE); 
				} else {
					JOptionPane.showMessageDialog(null, "Press enter key or click the button", "Press the key", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				payment.requestFocusInWindow();
				btnNewButton.setEnabled(false); 
				payment.setEditable(true); 
				carnum.setEditable(false); 
				JOptionPane.showMessageDialog(null, "Select any row from table for payment", "Making Payment", JOptionPane.INFORMATION_MESSAGE); 
			}
		});
		Image img2 = new ImageIcon(this.getClass().getResource("/m.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(img2)); 
		btnNewButton.setBounds(433, 56, 161, 50);
		contentPane.add(btnNewButton);
		setLocationRelativeTo(null);
		
		cusid.setEditable(false);
		cname.setEditable(false); 
		last_paid.setEditable(false);
		tamt.setEditable(false); 
		payment.setEditable(false);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(23, 342, 518, 62);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JButton button = new JButton("EXIT");
		button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					setVisible(false); 
				}
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false); 
			}
		});
		button.setBounds(426, 11, 80, 38);
		panel_3.add(button);
		btnSubmit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					long timeNow = Calendar.getInstance().getTimeInMillis();
					java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
					
					int cid = Integer.parseInt(cusid.getText());
					float total = Float.parseFloat(tamt.getText());
					float pay = Float.parseFloat(payment.getText());
					
					try {
						String query = "update dues set Total_Due="+total+" , Last_Paid="+pay+" where Invoice_Number="+inm.getText()+"";
						pst = connection.prepareStatement(query);
						pst.executeUpdate();
						
						JOptionPane.showMessageDialog(null, "Payment Done..","Paid",JOptionPane.INFORMATION_MESSAGE);  
						pst.close();
						
					} catch (Exception ex) {
						//ex.printStackTrace();
						JOptionPane.showMessageDialog(null, ex.getMessage()); 
					}
					
					try {
						String query2 = "insert into alldates(Car_Number,Customer_Name,Last_Visited,Customer_Id,Last_Paid,Invoice_Id) values(?,?,?,?,?,?)";
						PreparedStatement pst2 = connection.prepareStatement(query2); 
						pst2.setString(1, carnumtxt.getText());
						pst2.setString(2, cname.getText());
						pst2.setTimestamp(3, ts);
						pst2.setInt(4, Integer.parseInt(cusid.getText())); 
						pst2.setFloat(5, Math.round(pay));
						pst2.setInt(6, Integer.parseInt(inm.getText())); 
						pst2.execute();
						pst2.close();
					} catch (Exception e2) { 
						e2.printStackTrace();
						//JOptionPane.showMessageDialog(null, e2.getMessage()); 
					} 
					refreshTable();
					duedata();
				} else {
					JOptionPane.showMessageDialog(null, "Click on button or press the enter key.."); 
				}
			}
		});
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				long timeNow = Calendar.getInstance().getTimeInMillis();
				java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
				
				int cid = Integer.parseInt(cusid.getText());
				float total = Float.parseFloat(tamt.getText());
				float pay = Float.parseFloat(payment.getText());
				try {
					String query = "update dues set Total_Due="+total+" , Last_Paid="+pay+" where Invoice_Number="+inm.getText()+"";
					pst = connection.prepareStatement(query);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Payment Done..","Paid",JOptionPane.INFORMATION_MESSAGE);  
					pst.close();
					
				} catch (Exception ex) {
					//ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage()); 
				}
				
				try {
					String query2 = "insert into alldates(Car_Number,Customer_Name,Last_Visited,Customer_Id,Last_Paid,Invoice_Id) values(?,?,?,?,?,?)";
					PreparedStatement pst2 = connection.prepareStatement(query2); 
					pst2.setString(1, carnumtxt.getText());
					pst2.setString(2, cname.getText());
					pst2.setTimestamp(3, ts);
					pst2.setInt(4, Integer.parseInt(cusid.getText())); 
					pst2.setFloat(5, Math.round(pay));
					pst2.setInt(6, Integer.parseInt(inm.getText())); 
					pst2.execute();
					pst2.close();
				} catch (Exception e2) { 
					e2.printStackTrace();
					//JOptionPane.showMessageDialog(null, e2.getMessage()); 
				} 
				refreshTable();
				duedata();
			}
		});
		btnSubmit.setBounds(61, 11, 107, 38);
		panel_3.add(btnSubmit);
		
		JButton btnPrintReceipt = new JButton("PRINT");
		btnPrintReceipt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					try {
						JasperDesign jd;
						jd = JRXmlLoader.load(getClass().getResourceAsStream("../Bill.jrxml"));
						String sql = "select c.Customer_Name, c.Phone, c.Car_Number, c.Telephone, c.Fax, c.Email, c.Address, c.Series, d.Total_Due, ad.Last_Paid, "
								+ "d.Invoice_Number, ad.Last_Visited from cdetails as c "
								+ "INNER JOIN dues as d on c.Id = d.Customer_Id "
								+ "INNER JOIN alldates as ad on ad.Invoice_Id = d.Invoice_Number "
								+ "where d.Invoice_Number = '"+inm.getText()+"' "
								+ "order by ad.Last_Visited desc;";
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
			}
		});
		btnPrintReceipt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JasperDesign jd;
					jd = JRXmlLoader.load(getClass().getResourceAsStream("../Bill.jrxml"));
					String sql = "select c.Customer_Name, c.Phone, c.Car_Number, c.Telephone, c.Fax, c.Email, c.Address, c.Series, d.Total_Due, ad.Last_Paid, "
							+ "d.Invoice_Number, ad.Last_Visited from cdetails as c "
							+ "INNER JOIN dues as d on c.Id = d.Customer_Id "
							+ "INNER JOIN alldates as ad on ad.Invoice_Id = d.Invoice_Number "
							+ "where d.Invoice_Number = '"+inm.getText()+"' "
							+ "order by ad.Last_Visited desc;";
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
		btnPrintReceipt.setBounds(180, 11, 94, 38);
		panel_3.add(btnPrintReceipt);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(403, 11, 15, 38);
		panel_3.add(separator_3);
		
		JButton btnReset = new JButton("RESET");
		btnReset.setBounds(286, 11, 94, 38);
		panel_3.add(btnReset);
		btnReset.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					btnNewButton.setEnabled(true); 
					cusid.setText(""); 
					cname.setText("");
					last_paid.setText("");
					tamt.setText("");
					carnum.setEditable(true);
					carnum.setText(""); 
					inm.setText("");
					payment.setText(""); 
					payment.setEditable(false); 
					carnumtxt.setText("");
					btnSubmit.setEnabled(true); 
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					while(model.getRowCount() > 0) {
						for(int i=0;i<model.getRowCount();i++) {
							model.removeRow(i); 
						}
					}
					DefaultTableModel model2 = (DefaultTableModel)table_1.getModel();
					while(model2.getRowCount() > 0) {
						for(int i=0;i<model2.getRowCount();i++) {
							model2.removeRow(i); 
						}
					}
				}
			}
		});
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setEnabled(true); 
				cusid.setText(""); 
				cname.setText("");
				last_paid.setText("");
				tamt.setText("");
				carnum.setEditable(true);
				carnum.setText(""); 
				inm.setText(""); 
				payment.setText(""); 
				payment.setEditable(false); 
				carnumtxt.setText("");
				btnSubmit.setEnabled(true); 
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				while(model.getRowCount() > 0) {
					for(int i=0;i<model.getRowCount();i++) {
						model.removeRow(i); 
					}
				}
				DefaultTableModel model2 = (DefaultTableModel)table_1.getModel();
				while(model2.getRowCount() > 0) {
					for(int i=0;i<model2.getRowCount();i++) {
						model2.removeRow(i); 
					}
				}
			}
		});
		
		carnumtxt = new JTextField();
		carnumtxt.setHorizontalAlignment(SwingConstants.CENTER);
		carnumtxt.setEditable(false);
		carnumtxt.setForeground(new Color(255, 0, 51));
		carnumtxt.setText("");
		carnumtxt.setBackground(SystemColor.menu);
		carnumtxt.setFont(new Font("Calibri", Font.PLAIN, 24));
		carnumtxt.setBounds(200, 22, 158, 30);
		panel_2.add(carnumtxt);
		carnumtxt.setColumns(10);
		carnumtxt.setOpaque(false); // added by OP
		carnumtxt.setBorder(BorderFactory.createEmptyBorder());
		carnumtxt.setBackground(new Color(0,0,0,0));
		
		
		lblClock.setBounds(43, 508, 196, 14);
		panel_2.add(lblClock);
		
		
		inm.setBounds(368, 467, 46, 14);
		panel_2.add(inm);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dues were paid in these days :", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 153)));
		panel_4.setBounds(10, 410, 533, 218);
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 43, 513, 164);
		panel_4.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Vehicle Number", "Customer Name", "Last Visited Date", "Customer Id"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(107);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(205);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(108);
		table_1.setAutoResizeMode( JTable.AUTO_RESIZE_OFF ); 
		scrollPane_1.setViewportView(table_1);
		
		JButton btnLoad_1 = new JButton("LOAD");
		btnLoad_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				duedata();
			}
		});
		btnLoad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				duedata();
			}
		});
		btnLoad_1.setBounds(10, 11, 89, 29);
		panel_4.add(btnLoad_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.DARK_GRAY);
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(617, 11, 16, 639);
		contentPane.add(separator_2);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{carnum, btnLoad, btnNewButton, payment, btnPay, btnSubmit, btnPrintReceipt, btnReset, button, btnLoad_1}));
		
		inm.setVisible(false); 
		lblClock.setVisible(false); 
		clock();
	}
}
