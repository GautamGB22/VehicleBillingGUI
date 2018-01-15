package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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

import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Frame;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.LayoutStyle.ComponentPlacement;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.util.Date;
import java.text.*;

public class Bill_print_saved extends JFrame {

	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JPanel contentPane;
	private JTextField carnum;
	private JTable table;
	private JTextField invoiceid;
	private JTextField carnum2;
	private JButton btnClear;
	private JButton btnSearch;
	private JDateChooser from;
	private JDateChooser to;

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
					Bill_print_saved frame = new Bill_print_saved();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadData() {
		try {
			String query = "select invoicedetails.Invoice_number as 'Invoice Number', invoicedetails.Car_Number as 'Vehicle Number', isolds.Item_Name as 'Item Name', isolds.Pieces as 'Quantity', invoicedetails.Total_Amount as 'Total Amount',"
					+ "cdetails.Customer_Name as 'Customer Name', cdetails.Phone as 'Phone' , invoicedetails.Date_of_Invoice as 'Invoice Date-Time',  "
					+ "invoicedetails.Customer_Id as 'Customer Id' from invoicedetails "
					+ "INNER JOIN isolds ON invoicedetails.Invoice_Number = isolds.Invoice_Id "
					+ "INNER JOIN cdetails ON invoicedetails.Customer_Id = cdetails.Id order by invoicedetails.Invoice_Number desc";
			
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs)); 
			table.getColumnModel().getColumn(0).setPreferredWidth(131);
			table.getColumnModel().getColumn(1).setPreferredWidth(117);
			table.getColumnModel().getColumn(2).setPreferredWidth(329);
			table.getColumnModel().getColumn(4).setPreferredWidth(96);
			table.getColumnModel().getColumn(5).setPreferredWidth(210);
			table.getColumnModel().getColumn(6).setPreferredWidth(126);
			table.getColumnModel().getColumn(7).setPreferredWidth(140);
			table.getColumnModel().getColumn(8).setPreferredWidth(84);
			pst.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public Bill_print_saved() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				while(model.getRowCount() > 0) {
					for(int i=0;i<model.getRowCount();i++) {
						model.removeRow(i); 
					}
				}
				carnum.setText("");
				carnum2.setText("");
				invoiceid.setText(""); 
			}
		});
		setExtendedState(Frame.MAXIMIZED_BOTH);
		//Database Connection
				connection = Dbconn.dbConnection();
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				setBounds(100, 100, 1123, 527);
				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				
				JScrollPane scrollPane = new JScrollPane();
				
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						try {
							int row = table.getSelectedRow();
							String Table_click = (table.getModel().getValueAt(row, 0).toString());
							String sql = "select * from invoiceDetails where Invoice_Number='"+Table_click+"' order by Invoice_Number desc";
							pst = connection.prepareStatement(sql);
							rs = pst.executeQuery();
							if (rs.next()) {
								int add1 = rs.getInt("Invoice_Number"); 
								String z = Integer.toString(add1);
								invoiceid.setText(z);
							}
							pst.close();
							rs.close();
							
							int row2 = table.getSelectedRow();
							String Table_click2 = (table.getModel().getValueAt(row2, 1).toString());
							String sql2 = "select * from invoiceDetails where Car_Number='"+Table_click2+"' order by Invoice_Number desc";
							PreparedStatement pst2 = connection.prepareStatement(sql2);
							ResultSet rs2 = pst2.executeQuery();
							if (rs2.next()) {
								String add2 = rs2.getString("Car_Number");
								carnum2.setText(add2); 
							}
							pst2.close();
							rs2.close();
							
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage()); 
						}
					}
				});
				scrollPane.setViewportView(table);
				
				carnum = new JTextField();
				carnum.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						try {
							//String query = "select Invoice_number, Customer_Id,Date_of_Invoice,Total_Amount,Car_Number as Vehicle_Number from invoiceDetails where Car_Number Like '%"+invoiceid.getText()+"%' order by Invoice_Number desc";
							String query = "select invoicedetails.Invoice_number as 'Invoice Number', invoicedetails.Car_Number as 'Vehicle Number', isolds.Item_Name as 'Item Name', isolds.Pieces as 'Quantity', invoicedetails.Total_Amount as 'Total Amount',"
									+ "cdetails.Customer_Name as 'Customer Name', cdetails.Phone as 'Phone' , invoicedetails.Date_of_Invoice as 'Invoice Date-Time',  "
									+ "invoicedetails.Customer_Id as 'Customer Id' from invoicedetails "
									+ "INNER JOIN isolds ON invoicedetails.Invoice_Number = isolds.Invoice_Id "
									+ "INNER JOIN cdetails ON invoicedetails.Customer_Id = cdetails.Id where invoicedetails.Car_Number Like '%"+carnum.getText()+"%' order by invoicedetails.Invoice_Number desc";
							PreparedStatement pst = connection.prepareStatement(query);
							ResultSet rs = pst.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs)); 
							table.getColumnModel().getColumn(0).setPreferredWidth(131);
							table.getColumnModel().getColumn(1).setPreferredWidth(117);
							table.getColumnModel().getColumn(2).setPreferredWidth(329);
							table.getColumnModel().getColumn(4).setPreferredWidth(96);
							table.getColumnModel().getColumn(5).setPreferredWidth(210);
							table.getColumnModel().getColumn(6).setPreferredWidth(126);
							table.getColumnModel().getColumn(7).setPreferredWidth(140);
							table.getColumnModel().getColumn(8).setPreferredWidth(84);
							pst.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				carnum.setFont(new Font("Arial", Font.PLAIN, 18));
				carnum.setColumns(10);
				
				JLabel lblSearchByInvoice = new JLabel("Search By Vehicle Number :");
				
				JButton btnPrint = new JButton("PRINT");
				btnPrint.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							JasperDesign jd;
							jd = JRXmlLoader.load(getClass().getResourceAsStream("../Invoice_1.jrxml"));
							String sql = "SELECT i.Total_Amount, c.Customer_Name, i.Date_of_Invoice, i.Car_Number, s.Item_Name, s.Pieces, s.Price_per_item , i.Invoice_Number, d.Last_Paid, d.Total_Due, "
									+ "c.Phone, c.Telephone, c.Fax, c.Email, c.Address, c.Series FROM invoicedetails as i "
									+ "INNER JOIN isolds as s ON s.Invoice_Id=i.Invoice_Number "
									+ "INNER JOIN cdetails as c ON c.Id = i.Customer_Id "
									+ "INNER JOIN dues as d on d.Invoice_Number = i.Invoice_Number "
									+ "where i.Invoice_Number  = '"+invoiceid.getText()+"'";
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
				btnPrint.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
							try {
								JasperDesign jd;
								jd = JRXmlLoader.load(getClass().getResourceAsStream("../Invoice_1.jrxml"));
								String sql = "SELECT i.Total_Amount, c.Customer_Name, i.Date_of_Invoice, i.Car_Number, s.Item_Name, s.Pieces, s.Price_per_item , i.Invoice_Number, d.Last_Paid, d.Total_Due, "
										+ "c.Phone, c.Telephone, c.Fax, c.Email, c.Address, c.Series FROM invoicedetails as i "
										+ "INNER JOIN isolds as s ON s.Invoice_Id=i.Invoice_Number "
										+ "INNER JOIN cdetails as c ON c.Id = i.Customer_Id "
										+ "INNER JOIN dues as d on d.Invoice_Number = i.Invoice_Number "
										+ "where i.Invoice_Number  = '"+invoiceid.getText()+"'";
								JRDesignQuery newQuery = new JRDesignQuery();
								newQuery.setText(sql); 
								jd.setQuery(newQuery);
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
				
				JButton btnDelete = new JButton("DELETE");
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String query = "delete from invoicedetails where Invoice_Number = '"+invoiceid.getText()+"'";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.execute();
							pst.close();
							JOptionPane.showMessageDialog(null, "Invoice deleted having ID = '"+invoiceid.getText()+"'", "Record Deleted", JOptionPane.INFORMATION_MESSAGE);  
							
							String query2 = "delete from isolds where Invoice_Id = '"+invoiceid.getText()+"'";
							PreparedStatement pst2 = connection.prepareStatement(query2);
							pst2.execute();
							pst2.close(); 
							
							String query3 = "delete from dues where Invoice_Number = '"+invoiceid.getText()+"'";
							PreparedStatement pst3 = connection.prepareStatement(query3);
							pst3.execute();
							pst3.close(); 

							String query4 = "delete from fdes where Invoice_Id = '"+invoiceid.getText()+"'";
							PreparedStatement pst4 = connection.prepareStatement(query4);
							pst4.execute();
							pst4.close(); 

							String query5 = "delete from ftable where Invoice_Id = '"+invoiceid.getText()+"'";
							PreparedStatement pst5 = connection.prepareStatement(query5);
							pst5.execute();
							pst5.close(); 

							String query6 = "delete from item_category where Invoice_Id = '"+invoiceid.getText()+"'";
							PreparedStatement pst6 = connection.prepareStatement(query6);
							pst6.execute();
							pst6.close(); 

							String query7 = "delete from labour_charge where Invoice_Id = '"+invoiceid.getText()+"'";
							PreparedStatement pst7 = connection.prepareStatement(query7);
							pst7.execute();
							pst7.close(); 

							String query8 = "delete from labour_discount where Invoice_Id = '"+invoiceid.getText()+"'";
							PreparedStatement pst8 = connection.prepareStatement(query8);
							pst8.execute();
							pst8.close(); 
							
							String query9 = "delete from service_info where Invoice_Number = '"+invoiceid.getText()+"'";
							PreparedStatement pst9 = connection.prepareStatement(query9);
							pst9.execute();
							pst9.close(); 
							
							String query19 = "delete from alldates where Car_Number = '"+carnum2.getText()+"'";
							PreparedStatement pst19 = connection.prepareStatement(query19);
							pst19.execute();
							pst19.close(); 
							
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					/*	try {
							String query = "delete from isolds where Invoice_Id = '"+invoiceid.getText()+"'";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.execute();
							pst.close();  
							
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						} */
						loadData();
						carnum.setText(""); 
						invoiceid.setText(""); 
						carnum2.setText(""); 
					}
				});
				btnDelete.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
							try {
								String query = "delete from invoicedetails where Invoice_Number = '"+invoiceid.getText()+"'";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.execute();
								pst.close();
								JOptionPane.showMessageDialog(null, "Invoice deleted having ID = '"+invoiceid.getText()+"'", "Record Deleted", JOptionPane.INFORMATION_MESSAGE);  
								
								String query2 = "delete from isolds where Invoice_Id = '"+invoiceid.getText()+"'";
								PreparedStatement pst2 = connection.prepareStatement(query2);
								pst2.execute();
								pst2.close(); 
								
								String query3 = "delete from dues where Invoice_Number = '"+invoiceid.getText()+"'";
								PreparedStatement pst3 = connection.prepareStatement(query3);
								pst3.execute();
								pst3.close(); 

								String query4 = "delete from fdes where Invoice_Id = '"+invoiceid.getText()+"'";
								PreparedStatement pst4 = connection.prepareStatement(query4);
								pst4.execute();
								pst4.close(); 

								String query5 = "delete from ftable where Invoice_Id = '"+invoiceid.getText()+"'";
								PreparedStatement pst5 = connection.prepareStatement(query5);
								pst5.execute();
								pst5.close(); 

								String query6 = "delete from item_category where Invoice_Id = '"+invoiceid.getText()+"'";
								PreparedStatement pst6 = connection.prepareStatement(query6);
								pst6.execute();
								pst6.close(); 

								String query7 = "delete from labour_charge where Invoice_Id = '"+invoiceid.getText()+"'";
								PreparedStatement pst7 = connection.prepareStatement(query7);
								pst7.execute();
								pst7.close(); 

								String query8 = "delete from labour_discount where Invoice_Id = '"+invoiceid.getText()+"'";
								PreparedStatement pst8 = connection.prepareStatement(query8);
								pst8.execute();
								pst8.close(); 

								String query9 = "delete from service_info where Invoice_Number = '"+invoiceid.getText()+"'";
								PreparedStatement pst9 = connection.prepareStatement(query9);
								pst9.execute();
								pst9.close(); 

								String query19 = "delete from alldates where Car_Number = '"+carnum2.getText()+"'";
								PreparedStatement pst19 = connection.prepareStatement(query19);
								pst19.execute();
								pst19.close(); 
								
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, ex.getMessage());
							}
						/*	try {
								String query = "delete from isolds where Invoice_Id = '"+invoiceid.getText()+"'";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.execute();
								pst.close();  
								
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, ex.getMessage());
							} */
							loadData();
							carnum.setText(""); 
							invoiceid.setText(""); 
							carnum2.setText("");
						}
					}
				});
				
				JButton btnLoad = new JButton("Load");
				btnLoad.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						loadData();
					}
				});
				btnLoad.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
							loadData();
						}
					}
				});
				
				invoiceid = new JTextField();
				invoiceid.setVisible(false);
				invoiceid.setColumns(10);
				
				carnum2 = new JTextField();
				carnum2.setVisible(false);
				carnum2.setColumns(10);
				
				btnClear = new JButton("CLEAR");
				btnClear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DefaultTableModel model = (DefaultTableModel)table.getModel();
						while(model.getRowCount() > 0) {
							for(int i=0;i<model.getRowCount();i++) {
								model.removeRow(i); 
							}
						}
					}
				});
				
				JPanel panel = new JPanel();
				panel.setBorder(new TitledBorder(null, "Search by DATE :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 102, 204)));
				GroupLayout gl_contentPane = new GroupLayout(contentPane);
				gl_contentPane.setHorizontalGroup(
					gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(7)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1097, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
									.addGap(71)
									.addComponent(invoiceid, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(carnum2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblSearchByInvoice, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(165)
											.addComponent(carnum, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 681, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(410, Short.MAX_VALUE))
				);
				gl_contentPane.setVerticalGroup(
					gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(8)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
									.addComponent(invoiceid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(carnum2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(11)
									.addComponent(lblSearchByInvoice, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addComponent(carnum, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
							.addGap(3))
				);
				panel.setLayout(null);
				
				JLabel lblFrom = new JLabel("FROM :");
				lblFrom.setBounds(32, 30, 51, 14);
				panel.add(lblFrom);
				
				JLabel lblTo = new JLabel("TO :");
				lblTo.setBounds(312, 29, 29, 14);
				panel.add(lblTo);
				
				from = new JDateChooser();
				from.setDateFormatString("yyyyMMdd");
				from.setBounds(81, 23, 196, 28);
				panel.add(from);
				
				to = new JDateChooser();
				to.setDateFormatString("yyyyMMdd");
				to.setBounds(340, 23, 196, 28);
				panel.add(to);
				
				btnSearch = new JButton("SEARCH");
				btnSearch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
					    String d = sdf.format(from.getDate());
					    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
					    String d2 = sdf2.format(to.getDate());
						try {
							String query = "select invoicedetails.Invoice_number as 'Invoice Number', invoicedetails.Car_Number as 'Vehicle Number', isolds.Item_Name as 'Item Name', isolds.Pieces as 'Quantity', invoicedetails.Total_Amount as 'Total Amount',"
									+ "cdetails.Customer_Name as 'Customer Name', cdetails.Phone as 'Phone' , invoicedetails.Date_of_Invoice as 'Invoice Date-Time',  "
									+ "invoicedetails.Customer_Id as 'Customer Id' from invoicedetails "
									+ "INNER JOIN isolds ON invoicedetails.Invoice_Number = isolds.Invoice_Id "
									+ "INNER JOIN cdetails ON invoicedetails.Customer_Id = cdetails.Id "
									+ "where date(invoicedetails.Date_of_Invoice) between "+d+" and "+d2+" "
											+ "order by invoicedetails.Invoice_Number desc";
							
							PreparedStatement pst3 = connection.prepareStatement(query);
							ResultSet rs3 = pst3.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs3)); 
							pst3.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				btnSearch.setBackground(new Color(173, 216, 230));
				btnSearch.setBounds(548, 20, 85, 34);
				panel.add(btnSearch);
				contentPane.setLayout(gl_contentPane);
				setLocationRelativeTo(null);
				setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnLoad, btnClear, carnum, btnPrint, btnDelete, from, to, btnSearch}));
	}
}
