package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.dnd.Autoscroll;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.awt.Frame;

import javax.swing.JTextField;

import java.awt.Cursor;

import javax.swing.JTree;

import java.awt.Dialog.ModalExclusionType;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;

import javax.swing.JDesktopPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.awt.Font;

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

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.hibernate.property.Getter;

import java.awt.Component;

import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JSeparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Tabb extends JFrame {

	Connection connection = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JPanel contentPane;
	private JTextField cname;
	private JTextField mob;
	private JTextField tel;
	private JTextField fax;
	private JTextField email;
	private JTextField carnum;
	JPanel lbc = new JPanel();
	JPanel servicePanel = new JPanel();
	private JPanel cpanel;
	private JTextField invoiceid;
	private JButton btnStartBilling;
	private JButton btnAddCustomer;
	private JButton btnUpdateCustomer;
	private JTextArea caddress;
	private JButton btnSearch;
	private JLabel cid;
	private JLabel vno;
	private JTextField des;
	private JTextField lrate;
	private JLabel totalLabourcharge;
	public static final String RUPEES  = "\u20B9";
	private JButton btnLabourChargeAdd;
	private JButton labourChargeDeleted;
	private JTextField lnr;
	private JTable lcTable;
	JButton btnNextStep = new JButton("NEXT STEP >>");
	private JButton btnLastStep;
	ArrayList<String> dbItem = new ArrayList<String>();
	ArrayList<String> fsitem = new ArrayList<String>();
	private JTextField servicekm;
	private JTextField iname;
	private JTextField np;
	private JTextField price;
	private JTextField fitem;
	private JLabel inv;
	private JTable table;
	private JTable table_1;
	private JTextField nq;
	private JTextField nr;
	private JTextField sa;
	private JTextField di;
	private JLabel tamt;
	private JLabel tlc;
	private JLabel tdue;
	private JTextField payment;
	DefaultListModel<String> dlm = new DefaultListModel<String>();
	private JTextField nservice;
	private JTable table_2;
	private JTextField item;
	private JLabel gt;
	private JTextField lbcharge;
	private JTextField ldiscount;
	private JLabel dlb;
	private JButton btnUnlock;
	private JButton btnCalculate;
	private JButton btnAddItem_1;
	private JButton delItem;
	private JButton fnew;
	private JButton fdel;
	private JButton button_1;
	private JTextField cseries;
	private JButton btnAddItem;
	private JButton btnLock;
	private JButton btnSubmit;
	private JButton btnPrintBill;
	private JButton btnResetAll;
	private JButton button;
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JButton btnDiscount;
	private JComboBox comboBoxItem;
	
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
					Tabb frame = new Tabb();
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
					invoiceid.setText(maxid+"");
					inv.setText(maxid+""); 
				}
				else {
					maxid = rs.getInt("max_id") + 1;
					invoiceid.setText(maxid+"");
					inv.setText(maxid+"");
				}
			}
			pst.close();
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void del(){
		try {
			String query7 = "DELETE FROM ftable WHERE Car_Number = '"+carnum.getText()+"'";
			PreparedStatement pst6 = connection.prepareStatement(query7);
			pst6.execute();
			pst6.close();
			connection.commit();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
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
	
	public void resetall(){
		resetFields();
		
		dbItem.clear();
		fsitem.clear();
		
		DefaultTableModel model1 = (DefaultTableModel)lcTable.getModel();
		while(model1.getRowCount() > 0) {
			for(int i=0;i<model1.getRowCount();i++) {
				model1.removeRow(i); 
			}
		}
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
		DefaultTableModel model3 = (DefaultTableModel)table_2.getModel();
		while(model3.getRowCount() > 0) {
			for(int i=0;i<model3.getRowCount();i++) {
				model3.removeRow(i); 
			}
		}
		
		vno.setText("");
		invoiceid.setText("");
		des.setText("");
		lrate.setText("");
		lbcharge.setText("");
		lnr.setText("");
		totalLabourcharge.setText("");
		
		labourChargeDeleted.setEnabled(true);
		des.setEditable(true); 
		lrate.setEditable(true); 
		btnSubmit.setEnabled(true); 
		btnLabourChargeAdd.setEnabled(true);
		labourChargeDeleted.setEnabled(true); 

		servicekm.setText("");
		nservice.setText("");
		ldiscount.setText("");
		payment.setText("");
		tamt.setText("");
		tlc.setText("");
		tdue.setText("");
		dlb.setText("");
		gt.setText("");
		nq.setText("");
		nr.setText("");
		sa.setText("");
		di.setText("");
		item.setText("");
		iname.setText("");
		np.setText("");
		price.setText("");
		fitem.setText("");
		comboBoxItem.setSelectedIndex(0); 
		
		fitem.setEditable(true); 
		btnCalculate.setEnabled(true); 
		servicekm.setEditable(true); 
		nservice.setEditable(true); 
		ldiscount.setEditable(true);
		payment.setEditable(true);
		btnUnlock.setEnabled(true);
		fnew.setEnabled(true);
		fdel.setEnabled(true);
		delItem.setEnabled(true);
		btnAddItem_1.setEnabled(true);
		invoiceIdData();
		tabbedPane.setSelectedIndex(0); 
	}
	
	public void loaddata(){
		try {
			String query = "select Item_Name from ftable where Car_Number = '"+carnum.getText()+"' order by Id desc";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs)); 
			pst.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public Tabb() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				tabbedPane.setSelectedIndex(0); 
				carnum.requestFocusInWindow(); 
				invoiceIdData();
				button_1.setVisible(false); 
				comboBoxItem.setSelectedIndex(0); 
//				if(s1==null){
//				    s1 =  new servicing();
//				    internalFrame.setContentPane(s1.getContentPane());   
//				}
			}
			@Override
			public void windowClosed(WindowEvent e) {
				resetall();
			}
		});
		setTitle("Working Area :");
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//Database Connection
		connection = Dbconn.dbConnection();
		
		print_saved ps = new print_saved();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1518, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
		);
		//Speed up scrollpane mouse scrolling
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); 
		
		
		tabbedPane.setFont(new Font("Calibri", Font.BOLD, 15));
		tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		tabbedPane.setBackground(Color.YELLOW);
		scrollPane.setViewportView(tabbedPane);
		
		cpanel = new JPanel();
		tabbedPane.addTab("ADD CUSTOMER", new ImageIcon(Tabb.class.getResource("/com/sun/javafx/scene/web/skin/FontBackgroundColor_16x16_JFX.png")), cpanel, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_cpanel = new GroupLayout(cpanel);
		gl_cpanel.setHorizontalGroup(
			gl_cpanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 1393, Short.MAX_VALUE)
		);
		gl_cpanel.setVerticalGroup(
			gl_cpanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
		);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 102));
		scrollPane_1.setColumnHeaderView(panel_3);
		
		JLabel label = new JLabel("ADD ALL CUSTOMER DETAILS HERE :-");
		label.setForeground(new Color(255, 255, 51));
		label.setFont(new Font("Calibri", Font.BOLD, 17));
		panel_3.add(label);
		
		JDesktopPane desktopPane = new JDesktopPane();
		scrollPane_1.setViewportView(desktopPane);
		
		JPanel addCustomerPanel = new JPanel();
		addCustomerPanel.setBackground(new Color(255, 255, 204));
		addCustomerPanel.setBounds(37, 29, 663, 471);
		desktopPane.add(addCustomerPanel);
		addCustomerPanel.setLayout(null);
		
		JLabel lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setFont(new Font("Calibri", Font.BOLD, 14));
		lblCustomerName.setBounds(16, 102, 104, 16);
		addCustomerPanel.add(lblCustomerName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Calibri", Font.BOLD, 14));
		lblAddress.setBounds(16, 259, 62, 16);
		addCustomerPanel.add(lblAddress);
		
		cname = new JTextField();
		cname.setEditable(false);
		cname.setHorizontalAlignment(SwingConstants.LEFT);
		cname.setBorder(new EmptyBorder(0, 5, 0, 0));
		cname.setBackground(new Color(153, 255, 255));
		cname.setFont(new Font("Calibri", Font.BOLD, 15));
		cname.setBounds(173, 98, 461, 26);
		addCustomerPanel.add(cname);
		cname.setColumns(10);
		
		JLabel lblMobile = new JLabel("Mobile");
		lblMobile.setFont(new Font("Calibri", Font.BOLD, 14));
		lblMobile.setBounds(16, 133, 68, 16);
		addCustomerPanel.add(lblMobile);
		
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
		mob.setEditable(false);
		mob.setHorizontalAlignment(SwingConstants.LEFT);
		mob.setFont(new Font("Calibri", Font.BOLD, 15));
		mob.setColumns(10);
		mob.setBorder(new EmptyBorder(0, 5, 0, 0));
		mob.setBackground(new Color(153, 255, 255));
		mob.setBounds(173, 129, 145, 26);
		addCustomerPanel.add(mob);
		
		JLabel lblTelephone = new JLabel("Telephone");
		lblTelephone.setFont(new Font("Calibri", Font.BOLD, 14));
		lblTelephone.setBounds(16, 165, 78, 16);
		addCustomerPanel.add(lblTelephone);
		
		JLabel lblFax = new JLabel("Fax");
		lblFax.setFont(new Font("Calibri", Font.BOLD, 14));
		lblFax.setBounds(16, 197, 62, 16);
		addCustomerPanel.add(lblFax);
		
		JLabel lblEmailId = new JLabel("E-mail ID");
		lblEmailId.setFont(new Font("Calibri", Font.BOLD, 14));
		lblEmailId.setBounds(16, 228, 78, 16);
		addCustomerPanel.add(lblEmailId);
		
		tel = new JTextField();
		tel.setEditable(false);
		tel.setHorizontalAlignment(SwingConstants.LEFT);
		tel.setFont(new Font("Calibri", Font.BOLD, 15));
		tel.setColumns(10);
		tel.setBorder(new EmptyBorder(0, 5, 0, 0));
		tel.setBackground(new Color(153, 255, 255));
		tel.setBounds(173, 161, 145, 26);
		addCustomerPanel.add(tel);
		
		fax = new JTextField();
		fax.setEditable(false);
		fax.setHorizontalAlignment(SwingConstants.LEFT);
		fax.setFont(new Font("Calibri", Font.BOLD, 15));
		fax.setColumns(10);
		fax.setBorder(new EmptyBorder(0, 5, 0, 0));
		fax.setBackground(new Color(153, 255, 255));
		fax.setBounds(173, 193, 145, 26);
		addCustomerPanel.add(fax);
		
		email = new JTextField();
		email.setEditable(false);
		email.setHorizontalAlignment(SwingConstants.LEFT);
		email.setFont(new Font("Calibri", Font.BOLD, 15));
		email.setColumns(10);
		email.setBorder(new EmptyBorder(0, 5, 0, 0));
		email.setBackground(new Color(153, 255, 255));
		email.setBounds(173, 224, 227, 26);
		addCustomerPanel.add(email);
		
		JLabel lblEntries = new JLabel("ENTRIES");
		lblEntries.setForeground(Color.RED);
		lblEntries.setFont(new Font("Calibri", Font.BOLD, 18));
		lblEntries.setBounds(300, 17, 111, 16);
		addCustomerPanel.add(lblEntries);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 33, 650, 16);
		addCustomerPanel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 392, 650, 16);
		addCustomerPanel.add(separator_1);
		
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
		carnum.setForeground(Color.BLUE);
		carnum.setHorizontalAlignment(SwingConstants.LEFT);
		carnum.setFont(new Font("Calibri", Font.BOLD, 15));
		carnum.setColumns(10);
		carnum.setBorder(new EmptyBorder(0, 5, 0, 0));
		carnum.setBackground(new Color(153, 255, 255));
		carnum.setBounds(173, 50, 145, 26);
		addCustomerPanel.add(carnum);
		
		JLabel lblVehicleNumber = new JLabel("Vehicle Number");
		lblVehicleNumber.setFont(new Font("Calibri", Font.BOLD, 14));
		lblVehicleNumber.setBounds(16, 54, 93, 16);
		addCustomerPanel.add(lblVehicleNumber);
		
		JLabel label_1 = new JLabel(":");
		label_1.setFont(new Font("Calibri", Font.BOLD, 14));
		label_1.setBounds(132, 102, 9, 16);
		addCustomerPanel.add(label_1);
		
		JLabel label_2 = new JLabel(":");
		label_2.setFont(new Font("Calibri", Font.BOLD, 14));
		label_2.setBounds(131, 54, 9, 16);
		addCustomerPanel.add(label_2);
		
		JLabel label_3 = new JLabel(":");
		label_3.setFont(new Font("Calibri", Font.BOLD, 14));
		label_3.setBounds(131, 132, 10, 16);
		addCustomerPanel.add(label_3);
		
		JLabel label_4 = new JLabel(":");
		label_4.setFont(new Font("Calibri", Font.BOLD, 14));
		label_4.setBounds(132, 164, 9, 16);
		addCustomerPanel.add(label_4);
		
		JLabel label_5 = new JLabel(":");
		label_5.setFont(new Font("Calibri", Font.BOLD, 14));
		label_5.setBounds(132, 196, 9, 16);
		addCustomerPanel.add(label_5);
		
		JLabel label_6 = new JLabel(":");
		label_6.setFont(new Font("Calibri", Font.BOLD, 14));
		label_6.setBounds(132, 227, 9, 16);
		addCustomerPanel.add(label_6);
		
		JLabel label_7 = new JLabel(":");
		label_7.setFont(new Font("Calibri", Font.BOLD, 14));
		label_7.setBounds(131, 258, 10, 16);
		addCustomerPanel.add(label_7);
		
		btnAddCustomer = new JButton("ADD CUSTOMER");
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
		btnAddCustomer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddCustomer.setBounds(276, 407, 124, 35);
		addCustomerPanel.add(btnAddCustomer);
		
		btnUpdateCustomer = new JButton("UPDATE CUSTOMER");
		btnUpdateCustomer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					try {
						String q = "update cdetails set Customer_Name=?, Phone=?, Telephone=?, Fax=?, Email=?, Address=?, Series=? where Id = '"+cid.getText()+"'";
						PreparedStatement p = connection.prepareStatement(q);
						p.setString(1, cname.getText());
						p.setString(2, mob.getText());
						p.setString(3, tel.getText());
						p.setString(4, fax.getText());
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
		btnUpdateCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String q = "update cdetails set Customer_Name=?, Phone=?, Telephone=?, Fax=?, Email=?, Address=?, Series=? where Id = '"+cid.getText()+"'";
					PreparedStatement p = connection.prepareStatement(q);
					p.setString(1, cname.getText());
					p.setString(2, mob.getText());
					p.setString(3, tel.getText());
					p.setString(4, fax.getText());
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
		btnUpdateCustomer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdateCustomer.setBounds(400, 407, 147, 35);
		addCustomerPanel.add(btnUpdateCustomer);
		
		JButton btnNewButton = new JButton("EDIT");
		btnNewButton.addKeyListener(new KeyAdapter() {
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
		btnNewButton.addActionListener(new ActionListener() {
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
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnNewButton.setBounds(590, 6, 67, 28);
		addCustomerPanel.add(btnNewButton);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(6, 82, 650, 10);
		addCustomerPanel.add(separator_2);
		
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
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnSearch.setBounds(330, 49, 81, 28);
		addCustomerPanel.add(btnSearch);
		
		btnStartBilling = new JButton("Start Billing");
		btnStartBilling.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					if (carnum.getText().equals("") || carnum.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Bill will made for a particular customer. Add one or search the available customer in record.", "Customer details missing :", JOptionPane.ERROR_MESSAGE); 
					} else {
						vno.setText(carnum.getText()); 
						tabbedPane.setSelectedIndex(1); 
						if (tabbedPane.getSelectedIndex()==1) { 
							des.requestFocusInWindow();
						}
					}
				} 
			}
		});
		btnStartBilling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (carnum.getText().equals("") || carnum.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Bill will made for a particular customer. Add one or search the available customer in record.", "Customer details missing :", JOptionPane.ERROR_MESSAGE); 
				} else {
					vno.setText(carnum.getText()); 
					tabbedPane.setSelectedIndex(1); 
					if (tabbedPane.getSelectedIndex()==1) { 
						des.requestFocusInWindow();
					}
				}
			}
		});
		btnStartBilling.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnStartBilling.setForeground(new Color(0, 0, 0));
		btnStartBilling.setBackground(new Color(0, 204, 255));
		btnStartBilling.setBounds(173, 407, 104, 35);
		addCustomerPanel.add(btnStartBilling);
		
		JButton btnResetThis = new JButton("RESET THIS");
		btnResetThis.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					resetFields();
				}
			}
		});
		btnResetThis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFields();
			}
		});
		btnResetThis.setBounds(547, 407, 98, 35);
		addCustomerPanel.add(btnResetThis);
		
		JLabel label_8 = new JLabel("*");
		label_8.setBounds(163, 50, 9, 20);
		addCustomerPanel.add(label_8);
		label_8.setForeground(Color.RED);
		label_8.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel label_9 = new JLabel("*");
		label_9.setForeground(Color.RED);
		label_9.setFont(new Font("Calibri", Font.BOLD, 15));
		label_9.setBounds(163, 101, 9, 20);
		addCustomerPanel.add(label_9);
		
		JLabel label_10 = new JLabel("*");
		label_10.setForeground(Color.RED);
		label_10.setFont(new Font("Calibri", Font.BOLD, 15));
		label_10.setBounds(163, 258, 9, 20);
		addCustomerPanel.add(label_10);
		
		JLabel label_11 = new JLabel("*");
		label_11.setForeground(Color.RED);
		label_11.setFont(new Font("Calibri", Font.BOLD, 15));
		label_11.setBounds(163, 132, 9, 20);
		addCustomerPanel.add(label_11);
		
		JLabel lblSeries = new JLabel("Series");
		lblSeries.setBounds(454, 54, 41, 16);
		addCustomerPanel.add(lblSeries);
		lblSeries.setFont(new Font("Calibri", Font.BOLD, 14));
		
		JLabel label_15 = new JLabel(":");
		label_15.setBounds(492, 54, 9, 16);
		addCustomerPanel.add(label_15);
		label_15.setFont(new Font("Calibri", Font.BOLD, 14));
		
		cseries = new JTextField();
		cseries.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cseries.setText(cseries.getText().toUpperCase()); 
			}
		});
		cseries.setBounds(507, 50, 145, 26);
		addCustomerPanel.add(cseries);
		cseries.setHorizontalAlignment(SwingConstants.LEFT);
		cseries.setFont(new Font("Calibri", Font.BOLD, 15));
		cseries.setEditable(false);
		cseries.setColumns(10);
		cseries.setBorder(new EmptyBorder(0, 5, 0, 0));
		cseries.setBackground(new Color(153, 255, 255));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane_2.setBounds(173, 259, 461, 109);
		addCustomerPanel.add(scrollPane_2);
		
		caddress = new JTextArea();
		scrollPane_2.setViewportView(caddress);
		caddress.setEditable(false);
		caddress.setBorder(new EmptyBorder(0, 5, 0, 0));
		caddress.setBackground(new Color(153, 255, 255));
		
		invoiceid = new JTextField();
		invoiceid.setVisible(false);
		invoiceid.setBounds(76, 545, 100, 28);
		desktopPane.add(invoiceid);
		invoiceid.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(null, "RULES", TitledBorder.CENTER, TitledBorder.TOP, null, Color.RED));
		panel.setBounds(712, 29, 596, 106);
		desktopPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNoSpacesAllowed = new JLabel("* NO SPACES ALLOWED IN VEHICLE NUMBER.");
		lblNoSpacesAllowed.setForeground(Color.RED);
		lblNoSpacesAllowed.setFont(new Font("Calibri", Font.BOLD, 15));
		lblNoSpacesAllowed.setBounds(18, 27, 315, 16);
		panel.add(lblNoSpacesAllowed);
		
		JLabel lblFirstSearch = new JLabel("* First search with vehicle number.");
		lblFirstSearch.setForeground(Color.RED);
		lblFirstSearch.setFont(new Font("Calibri", Font.BOLD, 15));
		lblFirstSearch.setBounds(18, 44, 315, 16);
		panel.add(lblFirstSearch);
		
		JLabel lblToEdit = new JLabel("* To edit any customer details, search by its vehicle number->EDIT->UPDATE CUSTOMER.");
		lblToEdit.setForeground(Color.RED);
		lblToEdit.setFont(new Font("Calibri", Font.BOLD, 15));
		lblToEdit.setBounds(18, 60, 561, 16);
		panel.add(lblToEdit);
		
		JLabel lblStartBilling = new JLabel("* Start billing process from tab 1, i.e Add Customer to other processes.");
		lblStartBilling.setForeground(Color.RED);
		lblStartBilling.setFont(new Font("Calibri", Font.BOLD, 15));
		lblStartBilling.setBounds(18, 76, 518, 16);
		panel.add(lblStartBilling);
		
		JLabel lblForAny = new JLabel("* For any other query or problem, contact developer.");
		lblForAny.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForAny.setForeground(Color.WHITE);
		lblForAny.setFont(new Font("Calibri", Font.BOLD, 15));
		lblForAny.setBounds(977, 147, 331, 16);
		desktopPane.add(lblForAny);
		
		JLabel lblCustomerId = new JLabel("CUSTOMER ID :");
		lblCustomerId.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomerId.setForeground(Color.YELLOW);
		lblCustomerId.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 24));
		lblCustomerId.setBounds(904, 205, 251, 43);
		desktopPane.add(lblCustomerId);
		
		cid = new JLabel("");
		cid.setHorizontalAlignment(SwingConstants.CENTER);
		cid.setForeground(Color.YELLOW);
		cid.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 24));
		cid.setBounds(904, 249, 251, 43);
		desktopPane.add(cid);
		cpanel.setLayout(gl_cpanel);
		lbc.setBackground(new Color(255, 245, 238));
		
		
		tabbedPane.addTab("LABOUR CHARGE", new ImageIcon(Tabb.class.getResource("/net/sf/jasperreports/view/images/print.GIF")), lbc, null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		lnr = new JTextField();
		lnr.setVisible(false);
		lnr.setColumns(10);
		
		JLabel ltlc = new JLabel("");
		
		lbcharge = new JTextField();
		lbcharge.setVisible(false);
		lbcharge.setColumns(10);
		GroupLayout gl_lbc = new GroupLayout(lbc);
		gl_lbc.setHorizontalGroup(
			gl_lbc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_lbc.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_lbc.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_lbc.createSequentialGroup()
							.addComponent(ltlc, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
							.addGap(346))
						.addGroup(gl_lbc.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 1020, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_lbc.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_lbc.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_lbc.createSequentialGroup()
									.addGap(47)
									.addGroup(gl_lbc.createParallelGroup(Alignment.LEADING)
										.addComponent(lbcharge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lnr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addContainerGap(260, Short.MAX_VALUE))))
		);
		gl_lbc.setVerticalGroup(
			gl_lbc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_lbc.createSequentialGroup()
					.addGap(17)
					.addGroup(gl_lbc.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_lbc.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
							.addGap(72))
						.addGroup(gl_lbc.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lbcharge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lnr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(81)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ltlc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(104))
		);
		panel_2.setLayout(null);
		
		JLabel lblTotalLabourCharge = new JLabel("TOTAL");
		lblTotalLabourCharge.setBounds(16, 6, 251, 26);
		panel_2.add(lblTotalLabourCharge);
		lblTotalLabourCharge.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalLabourCharge.setForeground(Color.GRAY);
		lblTotalLabourCharge.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 20));
		
		JLabel lblLabourCharge = new JLabel(" LABOUR CHARGE :");
		lblLabourCharge.setBounds(16, 38, 251, 32);
		panel_2.add(lblLabourCharge);
		lblLabourCharge.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabourCharge.setForeground(Color.GRAY);
		lblLabourCharge.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 20));
		
		totalLabourcharge = new JLabel("");
		totalLabourcharge.setBounds(16, 76, 251, 32);
		panel_2.add(totalLabourcharge);
		totalLabourcharge.setHorizontalAlignment(SwingConstants.CENTER);
		totalLabourcharge.setForeground(Color.DARK_GRAY);
		totalLabourcharge.setFont(new Font("Calibri", Font.BOLD, 20));
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Vehicle Number :");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel.setBounds(739, 12, 103, 16);
		panel_1.add(lblNewLabel);
		
		vno = new JLabel("");
		vno.setBorder(new LineBorder(new Color(0, 0, 0)));
		vno.setHorizontalAlignment(SwingConstants.CENTER);
		vno.setForeground(new Color(51, 0, 102));
		vno.setFont(new Font("Calibri", Font.BOLD, 18));
		vno.setBounds(847, 6, 167, 28);
		panel_1.add(vno);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(6, 37, 1008, 7);
		panel_1.add(separator_3);
		
		JLabel lblDescription = new JLabel("Description :");
		lblDescription.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblDescription.setBounds(16, 57, 84, 16);
		panel_1.add(lblDescription);
		
		des = new JTextField();
		des.setForeground(new Color(0, 0, 102));
		des.setFont(new Font("Calibri", Font.PLAIN, 14));
		des.setBorder(new EmptyBorder(0, 5, 0, 0));
		des.setBounds(99, 51, 322, 28);
		panel_1.add(des);
		des.setColumns(10);
		
		lrate = new JTextField();
		lrate.setForeground(new Color(0, 0, 102));
		lrate.setFont(new Font("Calibri", Font.PLAIN, 14));
		lrate.setColumns(10);
		lrate.setBorder(new EmptyBorder(0, 5, 0, 0));
		lrate.setBounds(521, 51, 124, 28);
		panel_1.add(lrate);
		
		JLabel lblRateper = new JLabel("Rate (per) :");
		lblRateper.setHorizontalAlignment(SwingConstants.LEFT);
		lblRateper.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblRateper.setBounds(447, 57, 62, 16);
		panel_1.add(lblRateper);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(6, 85, 1008, 7);
		panel_1.add(separator_4);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(6, 104, 758, 373);
		panel_1.add(scrollPane_3);
		
		lcTable = new JTable();
		lcTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)lcTable.getModel();
				int s = lcTable.getSelectedRow();
				lnr.setText(model.getValueAt(s, 1).toString()); 
//				
//				
//				float b,c,r=0;
//				b = Float.parseFloat(lnr.getText());
//				c = Float.parseFloat(lgt.getText());
//				r = c - b;
//				sla.setText(Math.round(r)+"");  
			}
		});
		lcTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Description", "Rate"
			}
		));
		lcTable.getColumnModel().getColumn(0).setPreferredWidth(349);
		lcTable.getColumnModel().getColumn(1).setPreferredWidth(115);
		scrollPane_3.setViewportView(lcTable);
		scrollPane_3.getVerticalScrollBar().setUnitIncrement(16);
		
		labourChargeDeleted = new JButton("DELETE SELECTED");
		labourChargeDeleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel tm = (DefaultTableModel)lcTable.getModel();
				if (lcTable.getRowCount()<1) { 
					JOptionPane.showMessageDialog(null, "No more data in selling list."); 
				}
				else {
					float a,b,c=0;
					a = Float.parseFloat(lnr.getText());
					b = Float.parseFloat(lbcharge.getText());
					c = b - a;
					lbcharge.setText(Math.round(c)+""); 
					totalLabourcharge.setText(RUPEES+" "+Math.round(c)); 
				}
				try {
					int selectedRowIndex = lcTable.getSelectedRow();
					tm.removeRow(selectedRowIndex); 
				} catch (Exception e2) {
					e2.printStackTrace();
					//JOptionPane.showMessageDialog(null, e2.getMessage()); 
				} 
			}
		});
		labourChargeDeleted.setFont(new Font("Calibri", Font.BOLD, 15));
		labourChargeDeleted.setForeground(Color.WHITE);
		labourChargeDeleted.setBackground(Color.RED);
		labourChargeDeleted.setBounds(6, 482, 153, 35);
		panel_1.add(labourChargeDeleted);
		
		btnLabourChargeAdd = new JButton("Add");
		btnLabourChargeAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					float r,res,t;
					//q = Float.parseFloat(lqty.getText());
					r = Float.parseFloat(lrate.getText());
					if (totalLabourcharge.getText().isEmpty()) { 
						totalLabourcharge.setText(RUPEES+" "+Math.round(r));
						lbcharge.setText(r+""); 
					} else {
						t = Float.parseFloat(lbcharge.getText());
						res = t + r;
						totalLabourcharge.setText(RUPEES+" "+Math.round(res));  
						lbcharge.setText(res+""); 
					}
					
					try {
						DefaultTableModel model = (DefaultTableModel)lcTable.getModel();
						model.addRow(new Object[]{des.getText(),lrate.getText()});
						des.setText("");
						lrate.setText(""); 
						des.requestFocusInWindow();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage()); 
					}
				}
			}
		});
		btnLabourChargeAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float r,res,t;
				//q = Float.parseFloat(lqty.getText());
				r = Float.parseFloat(lrate.getText());
				if (totalLabourcharge.getText().isEmpty()) { 
					totalLabourcharge.setText(RUPEES+" "+Math.round(r));
					lbcharge.setText(r+""); 
				} else {
					t = Float.parseFloat(lbcharge.getText());
					res = t + r;
					totalLabourcharge.setText(RUPEES+" "+Math.round(res));  
					lbcharge.setText(res+""); 
				}
				
				try {
					DefaultTableModel model = (DefaultTableModel)lcTable.getModel();
					model.addRow(new Object[]{des.getText(),lrate.getText()});
					des.setText("");
					lrate.setText(""); 
					des.requestFocusInWindow();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage()); 
				}
			}
		});
		btnLabourChargeAdd.setHorizontalAlignment(SwingConstants.LEFT);
		Image img = new ImageIcon(this.getClass().getResource("/add.png")).getImage();
		btnLabourChargeAdd.setIcon(new ImageIcon(img));
		btnLabourChargeAdd.setBounds(655, 49, 109, 35);
		panel_1.add(btnLabourChargeAdd);
		btnLabourChargeAdd.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Actions :", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLUE));
		panel_4.setBounds(776, 109, 226, 132);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		btnNextStep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					if (cid.getText().equals("")) {  
						JOptionPane.showMessageDialog(null, "ADD the customer OR Search the available customer first.", "Customer details missing :", JOptionPane.ERROR_MESSAGE); 
					} else {
						if (JOptionPane.showConfirmDialog(null, "Are you sure to proceed?", "WARNING", 
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							
							if (lcTable.getRowCount()==0) {
								JOptionPane.showMessageDialog(null, "Atleast add 0 in each column.", "Details missing :", JOptionPane.ERROR_MESSAGE);
							} else {
								des.setEditable(false); 
								lrate.setEditable(false); 
								btnLabourChargeAdd.setEnabled(false);
								labourChargeDeleted.setEnabled(false); 
								tlc.setText(lbcharge.getText()); 
								
								JOptionPane.showMessageDialog(null, "Labour Charges added to "+vno.getText()+""); 
								tabbedPane.setSelectedIndex(2);
								loaddata();
								servicekm.requestFocusInWindow();
							}
							
						} else {
						    //labourChargeDeleted.setEnabled(true); 
						}
					}
				}
			}
		});
		
		btnNextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cid.getText().equals("")) {  
					JOptionPane.showMessageDialog(null, "ADD the customer OR Search the available customer first.", "Customer details missing :", JOptionPane.ERROR_MESSAGE); 
				} else {
					if (JOptionPane.showConfirmDialog(null, "Are you sure to proceed?", "WARNING", 
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						
						if (lcTable.getRowCount()==0) {
							JOptionPane.showMessageDialog(null, "Atleast add 0 in each column.", "Details missing :", JOptionPane.ERROR_MESSAGE);
						} else {
							des.setEditable(false); 
							lrate.setEditable(false); 
							btnLabourChargeAdd.setEnabled(false);
							labourChargeDeleted.setEnabled(false); 
							tlc.setText(lbcharge.getText()); 
							
							JOptionPane.showMessageDialog(null, "Labour Charges added to "+vno.getText()+""); 
							tabbedPane.setSelectedIndex(2);
							loaddata();
							servicekm.requestFocusInWindow();
						}
						
					} else {
					    //labourChargeDeleted.setEnabled(true); 
					}
				}
			}
		});
		btnNextStep.setBounds(54, 28, 117, 36);
		panel_4.add(btnNextStep);
		
		btnLastStep = new JButton("<< LAST STEP");
		btnLastStep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					tabbedPane.setSelectedIndex(0);
					carnum.requestFocusInWindow();
				}
			}
		});
		btnLastStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(0); 
				carnum.requestFocusInWindow();
			}
		});
		btnLastStep.setBounds(54, 76, 117, 36);
		panel_4.add(btnLastStep);
		lbc.setLayout(gl_lbc);
		tabbedPane.addTab("SERVICING", new ImageIcon(Tabb.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")), servicePanel, null);
		servicePanel.setLayout(null);
		
		JLabel label_13 = new JLabel("SERVICING :");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setForeground(new Color(102, 0, 153));
		label_13.setFont(new Font("Centaur", Font.BOLD, 24));
		label_13.setBounds(6, 12, 188, 42);
		servicePanel.add(label_13);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setForeground(Color.BLACK);
		separator_5.setBounds(6, 52, 188, 13);
		servicePanel.add(separator_5);
		
		button = new JButton("PRINT SAVED INVOICE");
		button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					ps.setVisible(true);
				}
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ps.setVisible(true);
			}
		});
		button.setBounds(6, 62, 182, 34);
		servicePanel.add(button);
		
		JLabel label_14 = new JLabel("Invoice ID :");
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setFont(new Font("Cambria", Font.PLAIN, 16));
		label_14.setBounds(6, 99, 188, 26);
		servicePanel.add(label_14);
		
		inv = new JLabel("");
		inv.setHorizontalAlignment(SwingConstants.CENTER);
		inv.setFont(new Font("Calibri", Font.BOLD, 16));
		inv.setBounds(6, 125, 188, 42);
		servicePanel.add(inv);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(6, 168, 188, 13);
		servicePanel.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setOrientation(SwingConstants.VERTICAL);
		separator_7.setBounds(192, 6, 13, 165);
		servicePanel.add(separator_7);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Item needed for servicing :", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 102)));
		panel_5.setBounds(6, 176, 206, 517);
		servicePanel.add(panel_5);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(16, 64, 173, 435);
		panel_5.add(scrollPane_4);
		
		table = new JTable();
		table.setFont(new Font("Arial", Font.BOLD, 16));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int s = table.getSelectedRow();
				item.setText(model.getValueAt(s, 0).toString()); 
			}
		});
		scrollPane_4.setViewportView(table);
		
		btnAddItem_1 = new JButton("ADD ITEM");
		btnAddItem_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					if (dbItem.contains(item.getText())) { 
						iname.setText(item.getText());
					} else {
						dbItem.add(item.getText());
						iname.setText(item.getText());
					}
					JOptionPane.showMessageDialog(null, "Item selected.");
					np.requestFocusInWindow();
					button_1.setVisible(true); 
				}
			}
		});
		btnAddItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dbItem.contains(item.getText())) { 
					iname.setText(item.getText());
				} else {
					dbItem.add(item.getText());
					iname.setText(item.getText());
				}
				JOptionPane.showMessageDialog(null, "Item selected.");
				np.requestFocusInWindow();
				button_1.setVisible(true); 
			}
		});
		btnAddItem_1.setBounds(49, 22, 91, 35);
		panel_5.add(btnAddItem_1);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(255, 204, 204));
		panel_6.setLayout(null);
		panel_6.setBorder(new TitledBorder(null, "Items :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 153)));
		panel_6.setBounds(224, 86, 782, 127);
		servicePanel.add(panel_6);
		
		JLabel lblItemName = new JLabel("Item Name      :");
		lblItemName.setBounds(255, 34, 85, 14);
		panel_6.add(lblItemName);
		
		iname = new JTextField();
		iname.setFont(new Font("Calibri", Font.PLAIN, 16));
		iname.setBorder(new EmptyBorder(0, 5, 0, 0));
		iname.setForeground(Color.BLUE);
		iname.setColumns(10);
		iname.setBounds(345, 29, 283, 27);
		panel_6.add(iname);
		
		button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = iname.getText();
				if (dbItem.contains(a)) {   
					dbItem.remove(a);
					JOptionPane.showMessageDialog(null, "Array have : '"+dbItem+"'"); 
					iname.setText("");
					button_1.setVisible(false); 
				} else {
					iname.setText(""); 
				}
			}
		});
		Image img2 = new ImageIcon(this.getClass().getResource("/del.png")).getImage();
		button_1.setIcon(new ImageIcon(img2));
		button_1.setBounds(628, 29, 30, 27);
		panel_6.add(button_1);
		
		JLabel label_18 = new JLabel("Quantities        :");
		label_18.setBounds(255, 76, 85, 14);
		panel_6.add(label_18);
		
		np = new JTextField();
		np.setBorder(new EmptyBorder(0, 5, 0, 0));
		np.setForeground(Color.BLUE);
		np.setFont(new Font("Calibri", Font.PLAIN, 16));
		np.setColumns(10);
		np.setBounds(345, 69, 121, 27);
		panel_6.add(np);
		
		JLabel label_19 = new JLabel("Rate :");
		label_19.setBounds(510, 76, 46, 14);
		panel_6.add(label_19);
		
		price = new JTextField();
		price.setBorder(new EmptyBorder(0, 5, 0, 0));
		price.setForeground(Color.BLUE);
		price.setFont(new Font("Calibri", Font.PLAIN, 16));
		price.setColumns(10);
		price.setBounds(557, 69, 96, 27);
		panel_6.add(price);
		
		btnAddItem = new JButton("ADD ITEM");
		btnAddItem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					long timeNow = Calendar.getInstance().getTimeInMillis();
					java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
					if (invoiceid.getText().equals("")) {  
						JOptionPane.showMessageDialog(null, "First set the labour charges to generate INVOICE KEY", "INVOICE ID MISSING", JOptionPane.ERROR_MESSAGE);
					} else {
						if (comboBoxItem.getSelectedIndex()==1) { 
							try {
								float n,p,r = 0,t,b,d,res=0,ta,lc;  
								n = Float.parseFloat(np.getText());
								p = Float.parseFloat(price.getText());
								
								if (tamt.getText().isEmpty()) {
									r = p;
									tamt.setText(r+"");
									
									ta = Float.parseFloat(tamt.getText());
									lc = Float.parseFloat(tlc.getText());
									res = ta + lc;
									gt.setText(res+"");
									
								} else if (!tamt.getText().isEmpty()){ 
									t = Float.parseFloat(tamt.getText()); 
									r = p + t;
									tamt.setText(r+"");
									
									ta = Float.parseFloat(tamt.getText());
									lc = Float.parseFloat(tlc.getText());
									res = ta + lc;
									gt.setText(res+"");
								}
								
								
								DefaultTableModel model = (DefaultTableModel)table_1.getModel();
								model.addRow(new Object[]{carnum.getText(),iname.getText(),np.getText()+" Ltr",price.getText(),ts,invoiceid.getText()});
								np.setText("");
								price.setText("");
								comboBoxItem.requestFocusInWindow();
								nservice.setEditable(false); 
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, ex.getMessage()); 
							}
						} 
						else if(comboBoxItem.getSelectedIndex()==2) {
							float n,p,r = 0,t,b,d,res=0,ta,lc;  
							n = Float.parseFloat(np.getText());
							p = Float.parseFloat(price.getText());
							
							if (tamt.getText().isEmpty()) {
								r = (n * p);
								tamt.setText(r+"");
								
								ta = Float.parseFloat(tamt.getText());
								lc = Float.parseFloat(tlc.getText());
								res = ta + lc;
								gt.setText(res+"");
								
							} else if (!tamt.getText().isEmpty()){ 
								t = Float.parseFloat(tamt.getText()); 
								r = (n * p) + t;
								tamt.setText(r+"");
								
								ta = Float.parseFloat(tamt.getText());
								lc = Float.parseFloat(tlc.getText());
								res = ta + lc;
								gt.setText(res+"");
							}
							
							try {
								DefaultTableModel model = (DefaultTableModel)table_1.getModel();
								model.addRow(new Object[]{carnum.getText(),iname.getText(),np.getText(),price.getText(),ts,invoiceid.getText()});
								np.setText("");
								price.setText("");
								comboBoxItem.requestFocusInWindow();
								nservice.setEditable(false); 
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, ex.getMessage()); 
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Select Item Type", "ITEM TYPE MISSING :", JOptionPane.ERROR_MESSAGE);
						}
						
						if (dbItem.contains(iname.getText())) { 
							DefaultTableModel model = (DefaultTableModel)table.getModel();
							try {
								int selectedRowIndex = table.getSelectedRow();
								model.removeRow(selectedRowIndex); 
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(null, e2.getMessage()); 
							}
						}
						
						iname.setText("");
						comboBoxItem.setSelectedIndex(0); 
						button_1.setVisible(false);
					}
				}
			}
		});
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timeNow = Calendar.getInstance().getTimeInMillis();
				java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
				if (invoiceid.getText().equals("")) {  
					JOptionPane.showMessageDialog(null, "First set the labour charges to generate INVOICE KEY", "INVOICE ID MISSING", JOptionPane.ERROR_MESSAGE);
				} else {
					if (comboBoxItem.getSelectedIndex()==1) { 
						try {
							float n,p,r = 0,t,b,d,res=0,ta,lc;  
							n = Float.parseFloat(np.getText());
							p = Float.parseFloat(price.getText());
							
							if (tamt.getText().isEmpty()) {
								r = p;
								tamt.setText(r+"");
								
								ta = Float.parseFloat(tamt.getText());
								lc = Float.parseFloat(tlc.getText());
								res = ta + lc;
								gt.setText(res+"");
								
							} else if (!tamt.getText().isEmpty()){ 
								t = Float.parseFloat(tamt.getText()); 
								r = p + t;
								tamt.setText(r+"");
								
								ta = Float.parseFloat(tamt.getText());
								lc = Float.parseFloat(tlc.getText());
								res = ta + lc;
								gt.setText(res+"");
							}
							
							
							DefaultTableModel model = (DefaultTableModel)table_1.getModel();
							model.addRow(new Object[]{carnum.getText(),iname.getText(),np.getText()+" Ltr",price.getText(),ts,invoiceid.getText()});
							np.setText("");
							price.setText("");
							comboBoxItem.requestFocusInWindow();
							nservice.setEditable(false); 
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage()); 
						}
					} 
					else if(comboBoxItem.getSelectedIndex()==2) {
						float n,p,r = 0,t,b,d,res=0,ta,lc;  
						n = Float.parseFloat(np.getText());
						p = Float.parseFloat(price.getText());
						
						if (tamt.getText().isEmpty()) {
							r = (n * p);
							tamt.setText(r+"");
							
							ta = Float.parseFloat(tamt.getText());
							lc = Float.parseFloat(tlc.getText());
							res = ta + lc;
							gt.setText(res+"");
							
						} else if (!tamt.getText().isEmpty()){ 
							t = Float.parseFloat(tamt.getText()); 
							r = (n * p) + t;
							tamt.setText(r+"");
							
							ta = Float.parseFloat(tamt.getText());
							lc = Float.parseFloat(tlc.getText());
							res = ta + lc;
							gt.setText(res+"");
						}
						
						try {
							DefaultTableModel model = (DefaultTableModel)table_1.getModel();
							model.addRow(new Object[]{carnum.getText(),iname.getText(),np.getText(),price.getText(),ts,invoiceid.getText()});
							np.setText("");
							price.setText("");
							comboBoxItem.requestFocusInWindow();
							nservice.setEditable(false); 
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage()); 
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Select Item Type", "ITEM TYPE MISSING :", JOptionPane.ERROR_MESSAGE);
					}
					
					if (dbItem.contains(iname.getText())) { 
						DefaultTableModel model = (DefaultTableModel)table.getModel();
						try {
							int selectedRowIndex = table.getSelectedRow();
							model.removeRow(selectedRowIndex); 
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage()); 
						}
					}
					
					iname.setText("");
					comboBoxItem.setSelectedIndex(0); 
					button_1.setVisible(false);
				}
			}
		});
		btnAddItem.setBounds(680, 52, 85, 33);
		panel_6.add(btnAddItem);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setOrientation(SwingConstants.VERTICAL);
		separator_8.setBounds(670, 6, 13, 115);
		panel_6.add(separator_8);
		
		JLabel lblItemType = new JLabel("ITEM TYPE :");
		lblItemType.setBounds(18, 33, 85, 14);
		panel_6.add(lblItemType);
		
		comboBoxItem = new JComboBox();
		comboBoxItem.setFont(new Font("Calibri", Font.BOLD, 16));
		comboBoxItem.setModel(new DefaultComboBoxModel(new String[] {"", "LIQUID", "NOT LIQUID"}));
		comboBoxItem.setBounds(16, 52, 170, 27);
		panel_6.add(comboBoxItem);
		
		JSeparator separator_17 = new JSeparator();
		separator_17.setOrientation(SwingConstants.VERTICAL);
		separator_17.setBounds(222, 6, 13, 115);
		panel_6.add(separator_17);
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBorder(new TitledBorder(null, "Items Used :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 153)));
		panel_7.setBounds(223, 225, 789, 468);
		servicePanel.add(panel_7);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(17, 56, 754, 394);
		panel_7.add(scrollPane_5);
		
		table_1 = new JTable();
		table_1.setFont(new Font("Arial", Font.PLAIN, 15));
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table_1.getModel();
				int s = table_1.getSelectedRow();
				nq.setText(model.getValueAt(s, 2).toString());
				nr.setText(model.getValueAt(s, 3).toString()); 
				
				di.setText(model.getValueAt(s, 1).toString());
				
				if (nq.getText().toString().contains("Ltr")) {   
					float a,b,c,r=0;
					//a = Float.parseFloat(nq.getText());
					b = Float.parseFloat(nr.getText());
					r = b;
					sa.setText(""+r);
				} else {
					float a,b,c,r=0;
					a = Float.parseFloat(nq.getText());
					b = Float.parseFloat(nr.getText());
					c = Float.parseFloat(tamt.getText());
					r = a * b;
					sa.setText(""+r);
				}
				 
			
			}
		});
		table_1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Vehicle Number", "Item Name", "Quantity", "Rate", "Date-Time", "Invoice Number"
				}
			));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(134);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(300);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(78);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(147);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(104);
		table_1.setAutoResizeMode( JTable.AUTO_RESIZE_OFF ); 
		scrollPane_5.setViewportView(table_1);
		
		
		delItem = new JButton("DELETE ITEM");
		delItem.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)table_1.getModel();
				DefaultTableModel tm = (DefaultTableModel)table.getModel();
				DefaultTableModel model3 = (DefaultTableModel)table_1.getModel();
				
				if (table_1.getRowCount()<1) { 
					JOptionPane.showMessageDialog(null, "No more data in selling list."); 
				}
				else {
					float a,b,c=0,ta,lc,res=0;
					int i;
					a = Float.parseFloat(sa.getText());
					b = Float.parseFloat(tamt.getText());
					c = b - a;
					tamt.setText(""+c);
					
					ta = Float.parseFloat(tamt.getText());
					lc = Float.parseFloat(tlc.getText());
					res = ta + lc;
					gt.setText(res+"");
				}
				
				if (dbItem.contains(di.getText())) { 
					tm.addRow(new Object[]{di.getText()}); 
					di.setText(""); 
					try {
						int selectedRowIndex = table_1.getSelectedRow();
						model.removeRow(selectedRowIndex); 
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage()); 
					} 
				} else {
					try {
						int selectedRowIndex = table_1.getSelectedRow();
						model3.removeRow(selectedRowIndex); 
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage()); 
					} 
				}
			}
		});
		Image img3 = new ImageIcon(this.getClass().getResource("/di.png")).getImage();
		delItem.setIcon(new ImageIcon(img3));
		delItem.setBounds(17, 20, 135, 34);
		panel_7.add(delItem);
		
		JPanel panel_8 = new JPanel();
		panel_8.setLayout(null);
		panel_8.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Enter Items for Next Servicing :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 153)));
		panel_8.setBounds(1024, 12, 282, 306);
		servicePanel.add(panel_8);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(17, 21, 248, 206);
		panel_8.add(scrollPane_6);
		
		table_2 = new JTable();
		table_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table_2.getModel();
				int s = table_2.getSelectedRow();
				fitem.setText(model.getValueAt(s, 0).toString());
			}
		});
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Item"
			}
		));
		table_2.getColumnModel().getColumn(0).setPreferredWidth(124);
		scrollPane_6.setViewportView(table_2);
		
		JLabel label_20 = new JLabel("Item Name :");
		label_20.setBounds(17, 237, 73, 14);
		panel_8.add(label_20);
		
		fitem = new JTextField();
		fitem.setHorizontalAlignment(SwingConstants.LEFT);
		fitem.setForeground(Color.BLUE);
		fitem.setColumns(10);
		fitem.setBounds(85, 230, 144, 27);
		panel_8.add(fitem);
		
		fnew = new JButton("ADD ITEM");
		fnew.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					DefaultTableModel model = (DefaultTableModel)table_2.getModel();
					model.addRow(new Object[]{fitem.getText()});
					
					fsitem.add(fitem.getText());
					//JOptionPane.showMessageDialog(null, fsitem);
					
					fitem.setText(""); 
					fitem.requestFocusInWindow();
				}
			}
		});
		fnew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				flist_1.setModel(dlm); 
//				dlm.addElement(fitem.getText()); 
				
				DefaultTableModel model = (DefaultTableModel)table_2.getModel();
				model.addRow(new Object[]{fitem.getText()});
				
				fsitem.add(fitem.getText());
				//JOptionPane.showMessageDialog(null, fsitem);
				
				fitem.setText(""); 
				fitem.requestFocusInWindow();
			}
		});
		fnew.setBounds(63, 263, 85, 33);
		panel_8.add(fnew);
		
		fdel = new JButton("DELETE");
		fdel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String a = fitem.getText();
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					if (fsitem.contains(a)) {   
						fsitem.remove(a);
						//JOptionPane.showMessageDialog(null, fsitem);
						DefaultTableModel model = (DefaultTableModel)table_2.getModel();
						try {
							int selectedRowIndex = table_2.getSelectedRow();
							model.removeRow(selectedRowIndex); 
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage()); 
						}
						fitem.setText(""); 
					} else {
						DefaultTableModel model = (DefaultTableModel)table_2.getModel();
						try {
							int selectedRowIndex = table_2.getSelectedRow();
							model.removeRow(selectedRowIndex); 
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage()); 
						}
						fitem.setText(""); 
					}
				}
			}
		});
		fdel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				int index1 = flist_1.getSelectedIndex();
//				dlm.removeElementAt(index1); 
				String a = fitem.getText();
				if (fsitem.contains(a)) {   
					fsitem.remove(a);
					//JOptionPane.showMessageDialog(null, fsitem);
					DefaultTableModel model = (DefaultTableModel)table_2.getModel();
					try {
						int selectedRowIndex = table_2.getSelectedRow();
						model.removeRow(selectedRowIndex); 
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage()); 
					}
					fitem.setText(""); 
				} else {
					DefaultTableModel model = (DefaultTableModel)table_2.getModel();
					try {
						int selectedRowIndex = table_2.getSelectedRow();
						model.removeRow(selectedRowIndex); 
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage()); 
					}
					fitem.setText(""); 
				}
					
			}
		});
		fdel.setBounds(156, 263, 85, 33);
		panel_8.add(fdel);
		
		nq = new JTextField();
		nq.setVisible(false);
		nq.setBounds(1334, 441, 122, 28);
		servicePanel.add(nq);
		nq.setColumns(10);
		
		nr = new JTextField();
		nr.setVisible(false);
		nr.setColumns(10);
		nr.setBounds(1334, 471, 122, 28);
		servicePanel.add(nr);
		
		sa = new JTextField();
		sa.setVisible(false);
		sa.setColumns(10);
		sa.setBounds(1334, 511, 122, 28);
		servicePanel.add(sa);
		
		di = new JTextField();
		di.setVisible(false);
		di.setColumns(10);
		di.setBounds(1334, 551, 122, 28);
		servicePanel.add(di);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(null, "Amounts :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_9.setBounds(224, 692, 1083, 158);
		servicePanel.add(panel_9);
		panel_9.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Service Amount :");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1.setBounds(17, 40, 131, 16);
		panel_9.add(lblNewLabel_1);
		
		tamt = new JLabel("");
		tamt.setHorizontalAlignment(SwingConstants.CENTER);
		tamt.setForeground(new Color(255, 0, 0));
		tamt.setFont(new Font("Arial", Font.BOLD, 18));
		tamt.setBounds(151, 41, 159, 16);
		panel_9.add(tamt);
		
		JLabel lblTotalLabourCharge_1 = new JLabel("Total Labour Charge :");
		lblTotalLabourCharge_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotalLabourCharge_1.setBounds(350, 40, 167, 16);
		panel_9.add(lblTotalLabourCharge_1);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(312, 21, 24, 59);
		panel_9.add(separator_9);
		separator_9.setOrientation(SwingConstants.VERTICAL);
		
		tlc = new JLabel("");
		tlc.setHorizontalAlignment(SwingConstants.CENTER);
		tlc.setForeground(Color.RED);
		tlc.setFont(new Font("Arial", Font.BOLD, 18));
		tlc.setBounds(529, 41, 159, 16);
		panel_9.add(tlc);
		
		JSeparator separator_10 = new JSeparator();
		separator_10.setOrientation(SwingConstants.VERTICAL);
		separator_10.setBounds(690, 21, 24, 59);
		panel_9.add(separator_10);
		
		JLabel lblPaymentDue = new JLabel("Payment Due :");
		lblPaymentDue.setFont(new Font("Arial", Font.BOLD, 16));
		lblPaymentDue.setBounds(743, 40, 120, 16);
		panel_9.add(lblPaymentDue);
		
		tdue = new JLabel("");
		tdue.setHorizontalAlignment(SwingConstants.CENTER);
		tdue.setForeground(Color.RED);
		tdue.setFont(new Font("Arial", Font.BOLD, 18));
		tdue.setBounds(875, 41, 159, 16);
		panel_9.add(tdue);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setBounds(17, 81, 1045, 13);
		panel_9.add(separator_11);
		
		JLabel lblGrandTotal = new JLabel("GRAND TOTAL :");
		lblGrandTotal.setBounds(743, 106, 131, 16);
		panel_9.add(lblGrandTotal);
		lblGrandTotal.setFont(new Font("Arial", Font.BOLD, 16));
		
		gt = new JLabel("");
		gt.setHorizontalAlignment(SwingConstants.CENTER);
		gt.setForeground(new Color(51, 0, 102));
		gt.setFont(new Font("Arial", Font.BOLD, 22));
		gt.setBounds(875, 106, 187, 16);
		panel_9.add(gt);
		
		JSeparator separator_13 = new JSeparator();
		separator_13.setFont(new Font("SansSerif", Font.BOLD, 14));
		separator_13.setBounds(875, 125, 187, 16);
		panel_9.add(separator_13);
		
		JLabel lblDiscountedLabourCharge = new JLabel("Discounted Labour Charge :");
		lblDiscountedLabourCharge.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiscountedLabourCharge.setBounds(17, 105, 217, 16);
		panel_9.add(lblDiscountedLabourCharge);
		
		dlb = new JLabel("");
		dlb.setHorizontalAlignment(SwingConstants.CENTER);
		dlb.setForeground(Color.RED);
		dlb.setFont(new Font("Arial", Font.BOLD, 18));
		dlb.setBounds(246, 106, 159, 16);
		panel_9.add(dlb);
		
		JSeparator separator_16 = new JSeparator();
		separator_16.setOrientation(SwingConstants.VERTICAL);
		separator_16.setBounds(510, 93, 24, 48);
		panel_9.add(separator_16);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(null, "Finalize actions :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_10.setBounds(1024, 330, 282, 363);
		servicePanel.add(panel_10);
		panel_10.setLayout(null);
		
		JLabel lblPaid = new JLabel("Paid :");
		lblPaid.setFont(new Font("Arial", Font.BOLD, 16));
		lblPaid.setBounds(13, 117, 89, 16);
		panel_10.add(lblPaid);
		
		payment = new JTextField();
		payment.setForeground(Color.BLUE);
		payment.setBackground(Color.CYAN);
		payment.setHorizontalAlignment(SwingConstants.RIGHT);
		payment.setFont(new Font("Calibri", Font.BOLD, 18));
		payment.setBorder(new EmptyBorder(0, 0, 0, 5));
		payment.setBounds(68, 132, 192, 28);
		panel_10.add(payment);
		payment.setColumns(10);
		
		JSeparator separator_12 = new JSeparator();
		separator_12.setFont(new Font("SansSerif", Font.BOLD, 14));
		separator_12.setBounds(18, 172, 244, 16);
		panel_10.add(separator_12);
		
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					if (JOptionPane.showConfirmDialog(null, "You won't able to come back. Are you sure to proceed?", "WARNING", 
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (tdue.getText().equals("") || tdue.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Calculate the amount first!", "Calculation missing :", JOptionPane.ERROR_MESSAGE); 
					} else {
						long timeNow = Calendar.getInstance().getTimeInMillis();
						java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
						int rows1 = lcTable.getRowCount();
						for(int row = 0; row<rows1 ; row++) {
							String name = (String) lcTable.getValueAt(row, 0);
							String rate = (String) lcTable.getValueAt(row, 1);
							try{
								String query = "insert into labour_charge(Invoice_Id,Description,Rate,Total,Discounted_Amount,Vehicle_Number,Date_Time) values(?,?,?,?,?,?,?)";
								connection.setAutoCommit(false);
								PreparedStatement stmt = connection.prepareStatement(query);
								stmt.setInt(1, Integer.parseInt(invoiceid.getText()));  
								stmt.setString(2, name); 
								stmt.setString(3, rate); 
								stmt.setFloat(4, Float.parseFloat(lbcharge.getText())); 
								if (ldiscount.getText().isEmpty()) { 
									stmt.setFloat(3, 0); 
								} else {
									stmt.setFloat(3, Float.parseFloat(ldiscount.getText())); 
								}
								stmt.setString(6, vno.getText()); 
								stmt.setTimestamp(7, ts);
								//stmt.setFloat(8, Float.parseFloat(tlc.getText())); 
								stmt.addBatch();
								stmt.executeBatch();
								connection.commit();
								
								String quer = "insert into labour_discount(Invoice_Id,Vehicle_Number,Discount,Date_Time) values(?,?,?,?)";
								PreparedStatement pt = connection.prepareStatement(quer); 
								pt.setString(1, invoiceid.getText());
								pt.setString(2, vno.getText());
								if (ldiscount.getText().isEmpty()) { 
									pt.setFloat(3, 0); 
								} else {
									pt.setFloat(3, Float.parseFloat(ldiscount.getText())); 
								}
								pt.setTimestamp(4, ts); 
								pt.execute();
								pt.close();
								connection.commit();
								}
								catch(Exception ex) {
									ex.printStackTrace();
									//JOptionPane.showMessageDialog(null, "Cannot save. "+ ex);
								}   
							}
						
						//batch Insert starts her
						int rows = table_1.getRowCount();
						for(int row = 0; row<rows ; row++) {
							String name = (String) table_1.getValueAt(row, 1);
							String piece = (String) table_1.getValueAt(row, 2);
							String price = (String) table_1.getValueAt(row, 3);
							try{
								String query = "insert into isolds(Car_Number,Item_Name,Pieces,Price_per_item,Date_of_sold,Invoice_Id) values(?,?,?,?,?,?)" ;
								connection.setAutoCommit(false);
								PreparedStatement stmtt = connection.prepareStatement(query);
								stmtt.setString(1, carnum.getText()); 
								stmtt.setString(2, name); 
								stmtt.setString(3, piece);
								stmtt.setString(4, price); 
								stmtt.setTimestamp(5, ts);
								stmtt.setInt(6, Integer.parseInt(invoiceid.getText()));
								stmtt.addBatch();
								stmtt.executeBatch();
								connection.commit();
								}
								catch(Exception ex) {
									//JOptionPane.showMessageDialog(null, "Cannot save. "+ ex);
									ex.printStackTrace();
								}   
							}
						
						if (table.getRowCount()>0 && table_2.getRowCount()>0) { 
							del();
							
							for (int i = 0; i < table.getRowCount(); i++) {
								String a = (String) table.getValueAt(i, 0);
								fsitem.add(a);
						}
							
							//batch2 insert starts here
							int rows2 = table.getRowCount();
							for (int r = 0; r < rows2; r++) {
								String itname = (String)table.getValueAt(r, 0);
								try {
									String query2 = "insert into ftable(Invoice_Id,Item_Name,Car_Number) values(?,?,?)";
									connection.setAutoCommit(false); 
									PreparedStatement pst2 = connection.prepareStatement(query2);
									pst2.setInt(1, Integer.parseInt(invoiceid.getText()));
									pst2.setString(2, itname); 
									pst2.setString(3, carnum.getText()); 
									pst2.addBatch();
									pst2.executeBatch();
									connection.commit();
								} catch (Exception e2) {
									//JOptionPane.showMessageDialog(null, e2.getMessage());
									e2.printStackTrace();
								}
							}
							//batch2 insert ends here
							
							//batch3 insert starts here
							int nrows = table_2.getRowCount();
							for (int re = 0; re < nrows; re++) {
								String ntname = (String)table_2.getValueAt(re, 0);
								try {
									String query2w = "insert into ftable(Invoice_Id,Item_Name,Car_Number) values(?,?,?)";
									connection.setAutoCommit(false); 
									PreparedStatement pst2w = connection.prepareStatement(query2w);
									pst2w.setInt(1, Integer.parseInt(invoiceid.getText()));
									pst2w.setString(2, ntname); 
									pst2w.setString(3, carnum.getText()); 
									pst2w.addBatch();
									pst2w.executeBatch();
									connection.commit();
								} catch (Exception e2) {
									//JOptionPane.showMessageDialog(null, e2.getMessage());
									e2.printStackTrace();
								}
							}
							//batch3 insert ends here
							
						} else if(table.getRowCount()<1 && table_2.getRowCount()>0) {
							del();
							
							fsitem.add(",");
							//batch4 insert starts here
							int nerows = table_2.getRowCount();
							for (int ree = 0; ree < nerows; ree++) {
								String ntname = (String)table_2.getValueAt(ree, 0);
								try {
									String query2e = "insert into ftable(Invoice_Id,Item_Name,Car_Number) values(?,?,?)";
									connection.setAutoCommit(false); 
									PreparedStatement pst2e = connection.prepareStatement(query2e);
									pst2e.setInt(1, Integer.parseInt(invoiceid.getText()));  
									pst2e.setString(2, ntname); 
									pst2e.setString(3, carnum.getText()); 
									pst2e.addBatch();
									pst2e.executeBatch();
									connection.commit();
								} catch (Exception e2) {
									//JOptionPane.showMessageDialog(null, e2.getMessage());
									e2.printStackTrace();
								}
							}
							//batch4 insert ends here
						}
						else if(table.getRowCount()>0 && table_2.getRowCount()<1) {
							del();
							
							for (int i = 0; i < table.getRowCount(); i++) {
								String a = (String) table.getValueAt(i, 0);
								fsitem.add(a);
						}
							
							//batch5 insert starts here
							int rows2q = table.getRowCount();
							for (int rq = 0; rq < rows2q; rq++) {
								String itname = (String)table.getValueAt(rq, 0);
								try {
									String query2q = "insert into ftable(Invoice_Id,Item_Name,Car_Number) values(?,?,?)";
									connection.setAutoCommit(false); 
									PreparedStatement pst2q = connection.prepareStatement(query2q);
									pst2q.setInt(1, Integer.parseInt(invoiceid.getText()));  
									pst2q.setString(2, itname); 
									pst2q.setString(3, carnum.getText()); 
									pst2q.addBatch();
									pst2q.executeBatch();
									connection.commit();
								} catch (Exception e2) {
									//JOptionPane.showMessageDialog(null, e2.getMessage());
									e2.printStackTrace();
								}
							}
							//batch5 insert ends here
						}
						else {
							del();
						}
						
						try {
																						//Dues
							String query2c = "insert into dues(Customer_Id,Customer_Name,Car_Number,Total_Due,Last_Paid,Invoice_Number) values(?,?,?,?,?,?)";
							PreparedStatement pst2y = connection.prepareStatement(query2c);
							pst2y.setInt(1, Integer.parseInt(cid.getText()));
							pst2y.setString(2, cname.getText());
							pst2y.setString(3, carnum.getText());
							pst2y.setFloat(4, Float.parseFloat(tdue.getText())); 
							pst2y.setFloat(5, Float.parseFloat(payment.getText()));
							pst2y.setInt(6, Integer.parseInt(invoiceid.getText()));
							pst2y.execute();
							pst2y.close();
							if (!tdue.getText().isEmpty()) {  
								JOptionPane.showMessageDialog(null, "This customer have due amount to be paid : Rs.'"+tdue.getText()+"'"); 
							}

																					//Invoice_Details
							String query3c = "insert into invoicedetails(Invoice_Number,Customer_Id,Date_of_Invoice,Total_Amount,Car_Number,Payment_Made,Grand_Total,Due) values(?,?,?,?,?,?,?,?)";
							PreparedStatement pst3 = connection.prepareStatement(query3c);
							pst3.setInt(1, Integer.parseInt(invoiceid.getText()));  
							pst3.setInt(2, Integer.parseInt(cid.getText()));  
							pst3.setTimestamp(3, ts);
							pst3.setFloat(4, Float.parseFloat(tamt.getText())); 
							pst3.setString(5, carnum.getText()); 
							pst3.setString(6, payment.getText()); 
							pst3.setString(7, gt.getText()); 
							pst3.setString(8, tdue.getText());
							pst3.execute();
							pst3.close();
							
																				//Service@KM Information
							String squery = "insert into service_info(Service_at_KM,Vehicle_Number,Date_Time,Next_Servicing,Invoice_Number) values(?,?,?,?,?)";
							PreparedStatement spst = connection.prepareStatement(squery);
							spst.setInt(1, Integer.parseInt(servicekm.getText()));
							spst.setString(2, carnum.getText()); 
							spst.setTimestamp(3, ts); 
							spst.setInt(4, Integer.parseInt(nservice.getText())); 
							spst.setInt(5, Integer.parseInt(invoiceid.getText())); 
							spst.execute();
							spst.close();
							
																				//Service@KM Information
//							String squerys = "insert into item_category(Invoice_Id,Item,Quantity,Category,Rate) values(?,?,?,?,?)";
//							PreparedStatement st = connection.prepareStatement(squerys);
//							st.setInt(1, Integer.parseInt(invoiceid.getText()));
//							st.setString(2, carnum.getText()); 
//							st.setTimestamp(3, ts); 
//							st.setInt(4, Integer.parseInt(nservice.getText())); 
//							st.setInt(5, Integer.parseInt(invoiceid.getText())); 
//							st.execute();
//							st.close();
							
							int rows22 = lcTable.getRowCount();
							for(int row = 0; row<rows22 ; row++) {
								String name = (String) lcTable.getValueAt(row, 0);
								String price = (String) lcTable.getValueAt(row, 1);
								try{
									String query34 = "insert into item_category(Invoice_Id,Item,Quantity,Category,Rate) values(?,?,?,?,?)";
									connection.setAutoCommit(false);
									PreparedStatement stmt23 = connection.prepareStatement(query34);
									stmt23.setString(1, invoiceid.getText()); 
									stmt23.setString(2, name); 
									stmt23.setInt(3, 0); 
									stmt23.setString(4, "Labour Charges");
									stmt23.setString(5, price);
									stmt23.addBatch();
									stmt23.executeBatch();
									connection.commit();
									}
									catch(Exception exx) {
										exx.printStackTrace();
										//JOptionPane.showMessageDialog(null, "Cannot save. "+ ex);
									}   
								}
							
							int rows24 = table_1.getRowCount();
							for(int rowd = 0; rowd<rows24 ; rowd++) {
								String name = (String) table_1.getValueAt(rowd, 1);
								String piece = (String) table_1.getValueAt(rowd, 2);
								String price = (String) table_1.getValueAt(rowd, 3);
								try{
									String query35 = "insert into item_category(Invoice_Id,Item,Quantity,Category,Rate) values(?,?,?,?,?)";
									connection.setAutoCommit(false);
									PreparedStatement stmt33 = connection.prepareStatement(query35);
									stmt33.setString(1, invoiceid.getText()); 
									stmt33.setString(2, name); 
									stmt33.setString(3, piece);
									stmt33.setString(4, "Part Charges"); 
									stmt33.setString(5, price); 
									stmt33.addBatch();
									stmt33.executeBatch();
									connection.commit();
									}
									catch(Exception ex) {
										//JOptionPane.showMessageDialog(null, "Cannot save. "+ ex);
										ex.printStackTrace();
									}   
								}
							
							try {
								String quer = "insert into fdes(Invoice_Id,Des) values(?,?)";
								PreparedStatement pts = connection.prepareStatement(quer);
								pts.setInt(1, Integer.parseInt(invoiceid.getText())); 
								pts.setString(2, fsitem.toString()); 
								pts.execute();
								pts.close();
							} catch (Exception e62) {
								e62.printStackTrace();
							}
							
							connection.commit();
						} catch (Exception e2t) {
							//JOptionPane.showMessageDialog(null, e2t.getMessage()); 
							e2t.printStackTrace();
						}
						
						JOptionPane.showMessageDialog(null, "Bill made to : '"+cname.getText()+"' , Bill number : '"+invoiceid.getText()+"'. Print the Bill."); 
						fitem.setEditable(false);
						payment.setEditable(false);
						ldiscount.setEditable(false);
						delItem.setEnabled(false);
						servicekm.setEditable(false);
						nservice.setEditable(false);
						btnSubmit.setEnabled(false); 
						btnAddItem_1.setEnabled(false);
						btnCalculate.setEnabled(false);
						btnUnlock.setEnabled(false);
						fdel.setEnabled(false);
						fnew.setEnabled(false);
					}
				}
				}
			}
		});
		btnSubmit.setForeground(Color.RED);
		btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "You won't able to come back. Are you sure to proceed?", "WARNING", 
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				if (tdue.getText().equals("") || tdue.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Calculate the amount first!", "Calculation missing :", JOptionPane.ERROR_MESSAGE); 
				} else {
					long timeNow = Calendar.getInstance().getTimeInMillis();
					java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
					int rows1 = lcTable.getRowCount();
					for(int row = 0; row<rows1 ; row++) {
						String name = (String) lcTable.getValueAt(row, 0);
						String rate = (String) lcTable.getValueAt(row, 1);
						try{
							String query = "insert into labour_charge(Invoice_Id,Description,Rate,Total,Discounted_Amount,Vehicle_Number,Date_Time) values(?,?,?,?,?,?,?)";
							connection.setAutoCommit(false);
							PreparedStatement stmt = connection.prepareStatement(query);
							stmt.setInt(1, Integer.parseInt(invoiceid.getText()));  
							stmt.setString(2, name); 
							stmt.setString(3, rate); 
							stmt.setFloat(4, Float.parseFloat(lbcharge.getText())); 
							if (ldiscount.getText().isEmpty()) { 
								stmt.setFloat(5, 0); 
							} else {
								stmt.setFloat(5, Float.parseFloat(ldiscount.getText())); 
							}
							stmt.setString(6, vno.getText()); 
							stmt.setTimestamp(7, ts);
							//stmt.setFloat(8, Float.parseFloat(tlc.getText())); 
							stmt.addBatch();
							stmt.executeBatch();
							connection.commit();
							
							String quer = "insert into labour_discount(Invoice_Id,Vehicle_Number,Discount,Date_Time) values(?,?,?,?)";
							PreparedStatement pt = connection.prepareStatement(quer); 
							pt.setString(1, invoiceid.getText());
							pt.setString(2, vno.getText());
							if (ldiscount.getText().isEmpty()) { 
								pt.setFloat(3, 0); 
							} else {
								pt.setFloat(3, Float.parseFloat(ldiscount.getText())); 
							}
							pt.setTimestamp(4, ts); 
							pt.execute();
							pt.close();
							connection.commit();
							}
							catch(Exception ex) {
								ex.printStackTrace();
								//JOptionPane.showMessageDialog(null, "Cannot save. "+ ex);
							}   
						}
					
					//batch Insert starts her
					int rows = table_1.getRowCount();
					for(int row = 0; row<rows ; row++) {
						String name = (String) table_1.getValueAt(row, 1);
						String piece = (String) table_1.getValueAt(row, 2);
						String price = (String) table_1.getValueAt(row, 3);
						try{
							String query = "insert into isolds(Car_Number,Item_Name,Pieces,Price_per_item,Date_of_sold,Invoice_Id) values(?,?,?,?,?,?)" ;
							connection.setAutoCommit(false);
							PreparedStatement stmtt = connection.prepareStatement(query);
							stmtt.setString(1, carnum.getText()); 
							stmtt.setString(2, name); 
							stmtt.setString(3, piece);
							stmtt.setString(4, price); 
							stmtt.setTimestamp(5, ts);
							stmtt.setInt(6, Integer.parseInt(invoiceid.getText()));
							stmtt.addBatch();
							stmtt.executeBatch();
							connection.commit();
							}
							catch(Exception ex) {
								//JOptionPane.showMessageDialog(null, "Cannot save. "+ ex);
								ex.printStackTrace();
							}   
						}
					
					if (table.getRowCount()>0 && table_2.getRowCount()>0) { 
						del();
						
						 for (int i = 0; i < table.getRowCount(); i++) {
								String a = (String) table.getValueAt(i, 0);
								fsitem.add(a);
						}
						 JOptionPane.showMessageDialog(null, fsitem); 
						//batch2 insert starts here
						int rows2 = table.getRowCount();
						for (int r = 0; r < rows2; r++) {
							String itname = (String)table.getValueAt(r, 0);
							try {
								String query2 = "insert into ftable(Invoice_Id,Item_Name,Car_Number) values(?,?,?)";
								connection.setAutoCommit(false); 
								PreparedStatement pst2 = connection.prepareStatement(query2);
								pst2.setInt(1, Integer.parseInt(invoiceid.getText()));
								pst2.setString(2, itname); 
								pst2.setString(3, carnum.getText()); 
								pst2.addBatch();
								pst2.executeBatch();
								connection.commit();
							} catch (Exception e2) {
								//JOptionPane.showMessageDialog(null, e2.getMessage());
								e2.printStackTrace();
							}
						}
						//batch2 insert ends here
						
						//batch3 insert starts here
						int nrows = table_2.getRowCount();
						for (int re = 0; re < nrows; re++) {
							String ntname = (String)table_2.getValueAt(re, 0);
							try {
								String query2w = "insert into ftable(Invoice_Id,Item_Name,Car_Number) values(?,?,?)";
								connection.setAutoCommit(false); 
								PreparedStatement pst2w = connection.prepareStatement(query2w);
								pst2w.setInt(1, Integer.parseInt(invoiceid.getText()));
								pst2w.setString(2, ntname); 
								pst2w.setString(3, carnum.getText()); 
								pst2w.addBatch();
								pst2w.executeBatch();
								connection.commit();
							} catch (Exception e2) {
								//JOptionPane.showMessageDialog(null, e2.getMessage());
								e2.printStackTrace();
							}
						}
						//batch3 insert ends here
						
					} else if(table.getRowCount()<1 && table_2.getRowCount()>0) {
						del();
						//batch4 insert starts here
						int nerows = table_2.getRowCount();
						for (int ree = 0; ree < nerows; ree++) {
							String ntname = (String)table_2.getValueAt(ree, 0);
							try {
								String query2e = "insert into ftable(Invoice_Id,Item_Name,Car_Number) values(?,?,?)";
								connection.setAutoCommit(false); 
								PreparedStatement pst2e = connection.prepareStatement(query2e);
								pst2e.setInt(1, Integer.parseInt(invoiceid.getText()));  
								pst2e.setString(2, ntname); 
								pst2e.setString(3, carnum.getText()); 
								pst2e.addBatch();
								pst2e.executeBatch();
								connection.commit();
							} catch (Exception e2) {
								//JOptionPane.showMessageDialog(null, e2.getMessage());
								e2.printStackTrace();
							}
						}
						//batch4 insert ends here
					}
					else if(table.getRowCount()>0 && table_2.getRowCount()<1) {
						del();
						
						 for (int i = 0; i < table.getRowCount(); i++) {
								String a = (String) table.getValueAt(i, 0);
								fsitem.add(a);
						}
						JOptionPane.showMessageDialog(null, fsitem);  
						//batch5 insert starts here
						int rows2q = table.getRowCount();
						for (int rq = 0; rq < rows2q; rq++) {
							String itname = (String)table.getValueAt(rq, 0);
							try {
								String query2q = "insert into ftable(Invoice_Id,Item_Name,Car_Number) values(?,?,?)";
								connection.setAutoCommit(false); 
								PreparedStatement pst2q = connection.prepareStatement(query2q);
								pst2q.setInt(1, Integer.parseInt(invoiceid.getText()));  
								pst2q.setString(2, itname); 
								pst2q.setString(3, carnum.getText()); 
								pst2q.addBatch();
								pst2q.executeBatch();
								connection.commit();
							} catch (Exception e2) {
								//JOptionPane.showMessageDialog(null, e2.getMessage());
								e2.printStackTrace();
							}
						}
						//batch5 insert ends here
					}
					else {
						del();
					}
					
					try {
																					//Dues
						String query2c = "insert into dues(Customer_Id,Customer_Name,Car_Number,Total_Due,Last_Paid,Invoice_Number) values(?,?,?,?,?,?)";
						PreparedStatement pst2y = connection.prepareStatement(query2c);
						pst2y.setInt(1, Integer.parseInt(cid.getText()));
						pst2y.setString(2, cname.getText());
						pst2y.setString(3, carnum.getText());
						pst2y.setFloat(4, Float.parseFloat(tdue.getText())); 
						pst2y.setFloat(5, Float.parseFloat(payment.getText()));
						pst2y.setInt(6, Integer.parseInt(invoiceid.getText()));
						pst2y.execute();
						pst2y.close();
						if (!tdue.getText().isEmpty()) {  
							JOptionPane.showMessageDialog(null, "This customer have due amount to be paid : Rs.'"+tdue.getText()+"'"); 
						}

																				//Invoice_Details
						String query3c = "insert into invoicedetails(Invoice_Number,Customer_Id,Date_of_Invoice,Total_Amount,Car_Number,Payment_Made,Grand_Total,Due) values(?,?,?,?,?,?,?,?)";
						PreparedStatement pst3 = connection.prepareStatement(query3c);
						pst3.setInt(1, Integer.parseInt(invoiceid.getText()));  
						pst3.setInt(2, Integer.parseInt(cid.getText()));  
						pst3.setTimestamp(3, ts);
						pst3.setFloat(4, Float.parseFloat(tamt.getText())); 
						pst3.setString(5, carnum.getText()); 
						pst3.setString(6, payment.getText()); 
						pst3.setString(7, gt.getText()); 
						pst3.setString(8, tdue.getText());
						pst3.execute();
						pst3.close();
						
																			//Service@KM Information
						String squery = "insert into service_info(Service_at_KM,Vehicle_Number,Date_Time,Next_Servicing,Invoice_Number) values(?,?,?,?,?)";
						PreparedStatement spst = connection.prepareStatement(squery);
						spst.setInt(1, Integer.parseInt(servicekm.getText()));
						spst.setString(2, carnum.getText()); 
						spst.setTimestamp(3, ts); 
						spst.setInt(4, Integer.parseInt(nservice.getText())); 
						spst.setInt(5, Integer.parseInt(invoiceid.getText())); 
						spst.execute();
						spst.close();
						
																			//Service@KM Information
//						String squerys = "insert into item_category(Invoice_Id,Item,Quantity,Category,Rate) values(?,?,?,?,?)";
//						PreparedStatement st = connection.prepareStatement(squerys);
//						st.setInt(1, Integer.parseInt(invoiceid.getText()));
//						st.setString(2, carnum.getText()); 
//						st.setTimestamp(3, ts); 
//						st.setInt(4, Integer.parseInt(nservice.getText())); 
//						st.setInt(5, Integer.parseInt(invoiceid.getText())); 
//						st.execute();
//						st.close();
						
						int rows22 = lcTable.getRowCount();
						for(int row = 0; row<rows22 ; row++) {
							String name = (String) lcTable.getValueAt(row, 0);
							String price = (String) lcTable.getValueAt(row, 1);
							try{
								String query34 = "insert into item_category(Invoice_Id,Item,Quantity,Category,Rate) values(?,?,?,?,?)";
								connection.setAutoCommit(false);
								PreparedStatement stmt23 = connection.prepareStatement(query34);
								stmt23.setString(1, invoiceid.getText()); 
								stmt23.setString(2, name); 
								stmt23.setInt(3, 0); 
								stmt23.setString(4, "Labour Charges");
								stmt23.setString(5, price);
								stmt23.addBatch();
								stmt23.executeBatch();
								connection.commit();
								}
								catch(Exception exx) {
									exx.printStackTrace();
									//JOptionPane.showMessageDialog(null, "Cannot save. "+ ex);
								}   
							}
						
						int rows24 = table_1.getRowCount();
						for(int rowd = 0; rowd<rows24 ; rowd++) {
							String name = (String) table_1.getValueAt(rowd, 1);
							String piece = (String) table_1.getValueAt(rowd, 2);
							String price = (String) table_1.getValueAt(rowd, 3);
							try{
								String query35 = "insert into item_category(Invoice_Id,Item,Quantity,Category,Rate) values(?,?,?,?,?)";
								connection.setAutoCommit(false);
								PreparedStatement stmt33 = connection.prepareStatement(query35);
								stmt33.setString(1, invoiceid.getText()); 
								stmt33.setString(2, name); 
								stmt33.setString(3, piece);
								stmt33.setString(4, "Part Charges"); 
								stmt33.setString(5, price); 
								stmt33.addBatch();
								stmt33.executeBatch();
								connection.commit();
								}
								catch(Exception ex) {
									//JOptionPane.showMessageDialog(null, "Cannot save. "+ ex);
									ex.printStackTrace();
								}   
							}
						
						try {
							String quer = "insert into fdes(Invoice_Id,Des) values(?,?)";
							PreparedStatement pts = connection.prepareStatement(quer);
							pts.setInt(1, Integer.parseInt(invoiceid.getText())); 
							pts.setString(2, fsitem.toString()); 
							pts.execute();
							pts.close();
						} catch (Exception e62) {
							e62.printStackTrace();
						}
						
						connection.commit();
					} catch (Exception e2t) {
						//JOptionPane.showMessageDialog(null, e2t.getMessage()); 
						e2t.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "Bill made to : '"+cname.getText()+"' , Bill number : '"+invoiceid.getText()+"'. Print the Bill."); 
					fitem.setEditable(false);
					payment.setEditable(false);
					ldiscount.setEditable(false);
					delItem.setEnabled(false);
					servicekm.setEditable(false);
					nservice.setEditable(false);
					btnSubmit.setEnabled(false); 
					btnAddItem_1.setEnabled(false);
					btnCalculate.setEnabled(false);
					btnUnlock.setEnabled(false);
					fdel.setEnabled(false);
					fnew.setEnabled(false);
				}
			}
		 }
		});
		btnSubmit.setBounds(68, 220, 145, 38);
		panel_10.add(btnSubmit);
		
		btnCalculate = new JButton("CALCULATE");
		btnCalculate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					if (JOptionPane.showConfirmDialog(null, "You won't able to come back. Are you sure to proceed?", "WARNING", 
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						
						float p,d,g,tl,ldis,dl,ta,grt;
//						tl = Float.parseFloat(tlc.getText());
//						ldis = Float.parseFloat(ldiscount.getText());
//						dl = tl - ldis;
//						dlb.setText(dl+"");  
						
//						ta = Float.parseFloat(tamt.getText());
//						dl = Float.parseFloat(dlb.getText());
//						grt = ta + dl;
//						gt.setText(grt+"");  
						
						g = Float.parseFloat(gt.getText());
						p = Float.parseFloat(payment.getText());
						d = g - p;
						if(d < 0){
							JOptionPane.showMessageDialog(null, "Please enter a good amount", "Amount goes negative.", JOptionPane.ERROR_MESSAGE);  
						} else {
							tdue.setText(d+"");
						}
					}
					else {
						//Nothing will happen here. Bad Luck!
					}
				}
			}
		});
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "You won't able to come back. Are you sure to proceed?", "WARNING", 
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					
					float p,d,g,tl,ldis,dl,ta,grt;
//					tl = Float.parseFloat(tlc.getText());
//					ldis = Float.parseFloat(ldiscount.getText());
//					dl = tl - ldis;
//					dlb.setText(dl+"");  
					
//					ta = Float.parseFloat(tamt.getText());
//					dl = Float.parseFloat(dlb.getText());
//					grt = ta + dl;
//					gt.setText(grt+"");  
					
					g = Float.parseFloat(gt.getText());
					p = Float.parseFloat(payment.getText());
					d = g - p;
					if(d < 0){
						JOptionPane.showMessageDialog(null, "Please enter a good amount", "Amount goes negative.", JOptionPane.ERROR_MESSAGE);  
					} else {
						tdue.setText(d+"");
					}
					
				}
				else {
					//Nothing will happen here. Bad Luck!
				}
				
			}
		});
		btnCalculate.setForeground(Color.BLUE);
		btnCalculate.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnCalculate.setBounds(68, 180, 145, 38);
		panel_10.add(btnCalculate);
		
		btnPrintBill = new JButton("PRINT BILL");
		btnPrintBill.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
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
		btnPrintBill.addActionListener(new ActionListener() {
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
		btnPrintBill.setBackground(new Color(204, 204, 204));
		btnPrintBill.setForeground(new Color(0, 153, 255));
		btnPrintBill.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnPrintBill.setBounds(68, 261, 145, 38);
		panel_10.add(btnPrintBill);
		
		btnResetAll = new JButton("RESET ALL");
		btnResetAll.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					resetall();
				}
			}
		});
		btnResetAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetall();
			}
		});
		btnResetAll.setFont(new Font("Calibri", Font.BOLD, 17));
		btnResetAll.setBounds(68, 303, 145, 38);
		panel_10.add(btnResetAll);
		
		JSeparator separator_14 = new JSeparator();
		separator_14.setOrientation(SwingConstants.VERTICAL);
		separator_14.setBounds(28, 180, 13, 161);
		panel_10.add(separator_14);
		
		JSeparator separator_15 = new JSeparator();
		separator_15.setOrientation(SwingConstants.VERTICAL);
		separator_15.setBounds(242, 176, 13, 165);
		panel_10.add(separator_15);
		
		ldiscount = new JTextField();
		ldiscount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
//				float tl,ldis,dl,ta,grt;
//				
//				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
//					if (ldiscount.getText().equals("")) {
//						ldiscount.setText("0"); 
//					} else {
//						tl = Float.parseFloat(tlc.getText());
//						ldis = Float.parseFloat(ldiscount.getText());
//						if (ldis>tl) { 
//							JOptionPane.showMessageDialog(null, "Please enter correct amount. Discount is more than actual amount i.e. [Rs."+tl+"]", "Amount goes negative", JOptionPane.ERROR_MESSAGE); 
//						} else {
//							dl = tl - ldis;
//							dlb.setText(dl+"");
//							
//							ta = Float.parseFloat(tamt.getText());
//							dl = Float.parseFloat(dlb.getText());
//							grt = ta + dl;
//							gt.setText(grt+"");
//						}
//					}
//				}
//				
//				if (e.getKeyCode()==KeyEvent.VK_BACK_SPACE) { 
//					if (ldiscount.getText().equals("") || ldiscount.getText().isEmpty()) { 
//						ldiscount.setText("0");
//						
//						tl = Float.parseFloat(tlc.getText());
//						ldis = Float.parseFloat(ldiscount.getText());
//						dl = tl - ldis;
//						dlb.setText(dl+"");
//						
//						ta = Float.parseFloat(tamt.getText());
//						dl = Float.parseFloat(dlb.getText());
//						grt = ta + dl;
//						gt.setText(grt+"");
//					}
//				}
			}
		});
		ldiscount.setBounds(68, 36, 192, 28);
		panel_10.add(ldiscount);
		ldiscount.setHorizontalAlignment(SwingConstants.RIGHT);
		ldiscount.setForeground(Color.BLUE);
		ldiscount.setFont(new Font("Calibri", Font.BOLD, 18));
		ldiscount.setColumns(10);
		ldiscount.setBorder(new EmptyBorder(0, 0, 0, 5));
		ldiscount.setBackground(Color.CYAN);
		
		JLabel lblDiscountonLabour = new JLabel("Discount (on Labour Charge) :");
		lblDiscountonLabour.setBounds(13, 20, 247, 16);
		panel_10.add(lblDiscountonLabour);
		lblDiscountonLabour.setFont(new Font("Arial", Font.BOLD, 14));
		
		btnDiscount = new JButton("( - ) Discount");
		btnDiscount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					float tl,ldis,dl,ta,grt;
					if (ldiscount.getText().equals("")) {
						ldiscount.setText("0"); 
					} else {
						tl = Float.parseFloat(tlc.getText());
						ldis = Float.parseFloat(ldiscount.getText());
						if (ldis>tl) { 
							JOptionPane.showMessageDialog(null, "Please enter correct amount. Discount is more than actual amount i.e. [Rs."+tl+"]", "Amount goes negative", JOptionPane.ERROR_MESSAGE); 
						} else {
							dl = tl - ldis;
							dlb.setText(dl+"");
							
							ta = Float.parseFloat(tamt.getText());
							dl = Float.parseFloat(dlb.getText());
							grt = ta + dl;
							gt.setText(grt+"");
						}
					}
				}
			}
		});
		btnDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float tl,ldis,dl,ta,grt;
				if (ldiscount.getText().equals("")) {
					ldiscount.setText("0"); 
				} else {
					tl = Float.parseFloat(tlc.getText());
					ldis = Float.parseFloat(ldiscount.getText());
					if (ldis>tl) { 
						JOptionPane.showMessageDialog(null, "Please enter correct amount. Discount is more than actual amount i.e. [Rs."+tl+"]", "Amount goes negative", JOptionPane.ERROR_MESSAGE); 
					} else {
						dl = tl - ldis;
						dlb.setText(dl+"");
						
						ta = Float.parseFloat(tamt.getText());
						dl = Float.parseFloat(dlb.getText());
						grt = ta + dl;
						gt.setText(grt+"");
					}
				}
			}
		});
		btnDiscount.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnDiscount.setBounds(146, 64, 114, 38);
		panel_10.add(btnDiscount);
		
		item = new JTextField();
		item.setVisible(false);
		item.setBounds(1334, 591, 98, 26);
		servicePanel.add(item);
		item.setColumns(10);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(null, "Service @ KM (Now & Next) :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_11.setBounds(224, 12, 782, 74);
		servicePanel.add(panel_11);
		panel_11.setLayout(null);
		
		JLabel label_16 = new JLabel("Service @ KM :");
		label_16.setBounds(21, 26, 85, 26);
		panel_11.add(label_16);
		
		servicekm = new JTextField();
		servicekm.setBounds(111, 26, 121, 27);
		panel_11.add(servicekm);
		servicekm.setFont(new Font("Calibri", Font.PLAIN, 16));
		servicekm.setBorder(new EmptyBorder(0, 5, 0, 0));
		servicekm.setHorizontalAlignment(SwingConstants.LEFT);
		servicekm.setForeground(Color.BLUE);
		servicekm.setColumns(10);
		
		JLabel lblNextServicingkm = new JLabel("Next Servicing (Km) :");
		lblNextServicingkm.setBounds(258, 26, 121, 26);
		panel_11.add(lblNextServicingkm);
		lblNextServicingkm.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		nservice = new JTextField();
		nservice.setBounds(388, 25, 121, 27);
		panel_11.add(nservice);
		nservice.setHorizontalAlignment(SwingConstants.LEFT);
		nservice.setForeground(Color.BLUE);
		nservice.setFont(new Font("Calibri", Font.PLAIN, 16));
		nservice.setColumns(10);
		nservice.setBorder(new EmptyBorder(0, 5, 0, 0));
		
		btnLock = new JButton("LOCK");
		btnLock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					servicekm.setEditable(false);
					nservice.setEditable(false); 
					JOptionPane.showMessageDialog(null, "Fields are LOCKED.", "Set Lock:", JOptionPane.INFORMATION_MESSAGE); 
					comboBoxItem.requestFocusInWindow();
				}
			}
		});
		btnLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servicekm.setEditable(false);
				nservice.setEditable(false); 
				JOptionPane.showMessageDialog(null, "Fields are LOCKED.", "Set Lock:", JOptionPane.INFORMATION_MESSAGE); 
				comboBoxItem.requestFocusInWindow();
			}
		});
		btnLock.setBounds(555, 22, 87, 34);
		panel_11.add(btnLock);
		
		btnUnlock = new JButton("UN-LOCK");
		btnUnlock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					servicekm.setEditable(true);
					nservice.setEditable(true); 
					servicekm.requestFocusInWindow();
					JOptionPane.showMessageDialog(null, "Fields are UNLOCKED.", "Set Lock:", JOptionPane.INFORMATION_MESSAGE);
					servicekm.requestFocusInWindow();
				}
			}
		});
		btnUnlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servicekm.setEditable(true);
				nservice.setEditable(true); 
				servicekm.requestFocusInWindow();
				JOptionPane.showMessageDialog(null, "Fields are UNLOCKED.", "Set Lock:", JOptionPane.INFORMATION_MESSAGE);
				servicekm.requestFocusInWindow();
			}
		});
		btnUnlock.setBounds(654, 22, 87, 34);
		panel_11.add(btnUnlock);
		
		contentPane.setLayout(gl_contentPane);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{carnum, btnSearch, cseries, cname, mob, tel, fax, email, caddress, des, lrate, btnLabourChargeAdd, btnNextStep, btnLastStep, des,
				servicekm, nservice, btnLock, btnUnlock, comboBoxItem, iname, np, price, btnAddItem, fitem, fnew, fdel, ldiscount, btnDiscount, payment, btnCalculate, btnSubmit, btnPrintBill, btnResetAll, btnAddItem_1, button, iname}));
		
	}
}