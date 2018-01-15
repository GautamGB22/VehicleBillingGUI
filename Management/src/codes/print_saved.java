package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;


import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.Font;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JButton;

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
import net.sf.jasperreports.engine.xml.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Frame;

import javax.swing.LayoutStyle.ComponentPlacement;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.util.Date;
import java.util.Locale;

public class print_saved extends JFrame {

	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JPanel contentPane;
	private JTable table;
	private JTextField invoiceid;
	private JButton btnDelete;
	private JButton btnLoad;
	private JButton btnClear;
	private JDateChooser from; 
	private JDateChooser to;
	private JButton btnSearch;
	//public static final String FILE_PATH = "src/Invoice_1.jrxml";
	
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
					print_saved frame = new print_saved();
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
	public print_saved() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				while(model.getRowCount() > 0) {
					for(int i=0;i<model.getRowCount();i++) {
						model.removeRow(i); 
					}
				}
				invoiceid.setText(""); 
			}
		});
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1123, 527);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		//Database Connection
		connection = Dbconn.dbConnection();
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.setFont(new Font("SansSerif", Font.PLAIN, 13));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Invoice Number", "Vehicle Number", "Item Name", "Quantity", "Total Amount", "Customer Name", "Phone Number", "Invoice Date-Time", "Customer_Id"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(131);
		table.getColumnModel().getColumn(1).setPreferredWidth(117);
		table.getColumnModel().getColumn(2).setPreferredWidth(329);
		table.getColumnModel().getColumn(4).setPreferredWidth(96);
		table.getColumnModel().getColumn(5).setPreferredWidth(210);
		table.getColumnModel().getColumn(6).setPreferredWidth(126);
		table.getColumnModel().getColumn(7).setPreferredWidth(140);
		table.getColumnModel().getColumn(8).setPreferredWidth(84);
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
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage()); 
				}
			}
		});
		scrollPane.setViewportView(table);
		
		invoiceid = new JTextField();
		invoiceid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					//String query = "select Invoice_number, Customer_Id,Date_of_Invoice,Total_Amount,Car_Number as Vehicle_Number from invoiceDetails where Car_Number Like '%"+invoiceid.getText()+"%' order by Invoice_Number desc";
					String query = "select invoicedetails.Invoice_number as 'Invoice Number', invoicedetails.Car_Number as 'Vehicle Number', isolds.Item_Name as 'Item Name', isolds.Pieces as 'Quantity', invoicedetails.Total_Amount as 'Total Amount',"
							+ "cdetails.Customer_Name as 'Customer Name', cdetails.Phone as 'Phone' , invoicedetails.Date_of_Invoice as 'Invoice Date-Time',  "
							+ "invoicedetails.Customer_Id as 'Customer Id' from invoicedetails "
							+ "INNER JOIN isolds ON invoicedetails.Invoice_Number = isolds.Invoice_Id "
							+ "INNER JOIN cdetails ON invoicedetails.Customer_Id = cdetails.Id where invoicedetails.Car_Number Like '%"+invoiceid.getText()+"%' order by invoicedetails.Invoice_Number desc";
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
		invoiceid.setFont(new Font("Arial", Font.PLAIN, 18));
		invoiceid.setColumns(10);
		
		JLabel lblSearchByInvoice = new JLabel("Search By Vehicle Number :");
		
		JButton btnPrint = new JButton("PRINT");
		btnPrint.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					try {
						JasperDesign jd;
						jd = JRXmlLoader.load(getClass().getResourceAsStream("../Servicing_Invoice.jrxml"));
						String sql = "select f.Des , sc.Service_at_KM, sc.Next_Servicing , c.Id ,c.Customer_Name, c.Phone, c.Car_Number, c.Telephone, c.Fax, c.Email, "
								+ "c.Address, c.Series, i.Invoice_Number, i.Date_of_Invoice, i.Total_Amount, i.Grand_Total, lc.Total as labour_total, lc.Discounted_Amount, "
								+ "ic.Quantity , ic.Rate as item_category_rate, ic.Category , ic.Item, i.Payment_Made, i.Due from cdetails as c "
								+ "INNER JOIN invoicedetails as i on c.Id = i.Customer_Id "
								+ "INNER JOIN labour_charge as lc on lc.Invoice_Id = i.Invoice_Number "
								+ "INNER JOIN isolds AS s on s.Invoice_Id = i.Invoice_Number "
								+ "INNER JOIN item_category as ic on i.Invoice_Number = ic.Invoice_Id "
								+ "INNER JOIN service_info as sc on i.Invoice_Number = sc.Invoice_Number "
								+ "INNER JOIN fdes as f on f.Invoice_Id = i.Invoice_Number "
								+ "WHERE i.Invoice_Number= '"+invoiceid.getText()+"' "
										+ "group by ic.Item order by ic.Category desc";
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
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JasperDesign jd;
					jd = JRXmlLoader.load(getClass().getResourceAsStream("../Servicing_Invoice.jrxml"));
					String sql = "select f.Des , sc.Service_at_KM, sc.Next_Servicing , c.Id ,c.Customer_Name, c.Phone, c.Car_Number, c.Telephone, c.Fax, c.Email, "
							+ "c.Address, c.Series, i.Invoice_Number, i.Date_of_Invoice, i.Total_Amount, i.Grand_Total, lc.Total as labour_total, lc.Discounted_Amount, "
							+ "ic.Quantity , ic.Rate as item_category_rate, ic.Category , ic.Item, i.Payment_Made, i.Due from cdetails as c "
							+ "INNER JOIN invoicedetails as i on c.Id = i.Customer_Id "
							+ "INNER JOIN labour_charge as lc on lc.Invoice_Id = i.Invoice_Number "
							+ "INNER JOIN isolds AS s on s.Invoice_Id = i.Invoice_Number "
							+ "INNER JOIN item_category as ic on i.Invoice_Number = ic.Invoice_Id "
							+ "INNER JOIN service_info as sc on i.Invoice_Number = sc.Invoice_Number "
							+ "INNER JOIN fdes as f on f.Invoice_Id = i.Invoice_Number "
							+ "WHERE i.Invoice_Number= '"+invoiceid.getText()+"' "
									+ "group by ic.Item order by ic.Category desc";
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
		
		btnDelete = new JButton("DELETE");
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
					invoiceid.setText(""); 
				}
			}
		});
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
				invoiceid.setText(""); 
			}
		});
		
		btnLoad = new JButton("Load");
		btnLoad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					loadData();
				}
			}
		});
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData();
			}
		});
		
		btnClear = new JButton("CLEAR");
		btnClear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					invoiceid.setText("");
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					while(model.getRowCount() > 0) {
						for(int i=0;i<model.getRowCount();i++) {
							model.removeRow(i); 
						}
					}
				}
			}
		});
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invoiceid.setText(""); 
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				while(model.getRowCount() > 0) {
					for(int i=0;i<model.getRowCount();i++) {
						model.removeRow(i); 
					}
				}
			}
		});
		
		JLabel lblSearchByDate = new JLabel("Search By Date :");
		
		JLabel lblFrom = new JLabel("FROM :");
		
		from = new JDateChooser();
		from.setDateFormatString("yyyyMMdd");
		
		to = new JDateChooser();
		to.setDateFormatString("yyyyMMdd");
		
		JLabel lblTo = new JLabel("TO :");
		
		btnSearch = new JButton("SEARCH");
		btnSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
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
			}
		});
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
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClear)
							.addGap(331)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSearchByInvoice, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(165)
									.addComponent(invoiceid, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
							.addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
					.addGap(5))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblSearchByDate, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(28)
							.addComponent(lblFrom, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(from, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addGap(48)
							.addComponent(lblTo, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(to, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(btnSearch)
					.addContainerGap(474, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addComponent(lblSearchByInvoice, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(invoiceid, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSearchByDate, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblFrom, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(to, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTo, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnLoad, btnClear, invoiceid, btnPrint, btnDelete, from, to, btnSearch}));
	}
}
