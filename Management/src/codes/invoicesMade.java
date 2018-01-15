package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;

import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.ListSelectionModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Frame;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;

import com.toedter.calendar.JDateChooser;

import javax.swing.SwingConstants;

public class invoicesMade extends JFrame {

	private JPanel contentPane;
	Connection connection = null;
	private JTable table;
	private JTextField inum;
	private JTextField carnum;
	private JDateChooser from;
	private JDateChooser to;
	private JButton btnSearch;
	private JButton btnShowAllInvoices;
	private JButton btnClear;
	private JLabel totalLbl;
	private JLabel dueLbl;
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
					invoicesMade frame = new invoicesMade();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public invoicesMade() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				while(model.getRowCount() > 0) {
					for(int i=0;i<model.getRowCount();i++) {
						model.removeRow(i); 
					}
				}
				dueLbl.setText(""); 
				totalLbl.setText(""); 
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(invoicesMade.class.getResource("/com/sun/javafx/scene/web/skin/AlignJustified_16x16_JFX.png")));
		connection = Dbconn.dbConnection();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 874, 681);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Search Invoices :", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Invoice Number :");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblNewLabel.setBounds(109, 35, 115, 14);
		panel.add(lblNewLabel);
		
		inum = new JTextField();
		inum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String query = "select Invoice_Number,Customer_Id,Date_of_Invoice as Invoice_Made,Total_Amount,Payment_Made,Due,Car_Number as Vehicle_Number"
							+ " from invoiceDetails where Invoice_Number Like '%"+inum.getText()+"%' order by Invoice_Number desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		inum.setBounds(226, 28, 149, 29);
		panel.add(inum);
		inum.setColumns(10);
		
		JLabel carnumtxt = new JLabel("Vehicle Number :");
		carnumtxt.setBounds(461, 36, 115, 14);
		panel.add(carnumtxt);
		carnumtxt.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		JLabel lblOr = new JLabel("OR");
		lblOr.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblOr.setBounds(403, 36, 31, 14);
		panel.add(lblOr);
		
		carnum = new JTextField();
		carnum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String query = "select Invoice_Number,Customer_Id,Date_of_Invoice as Invoice_Made,Total_Amount,Payment_Made,Due,Car_Number as Vehicle_Number from invoiceDetails where Car_Number Like '%"+carnum.getText()+"%' order by Invoice_Number desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		carnum.setColumns(10);
		carnum.setBounds(581, 28, 154, 29);
		panel.add(carnum);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		btnShowAllInvoices = new JButton("Show All Invoices");
		btnShowAllInvoices.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						String query = "select Invoice_Number,Customer_Id,Date_of_Invoice as Invoice_Made,Total_Amount,Payment_Made,Car_Number as Vehicle_Number,Due "
								+ "from invoiceDetails order by Invoice_Number desc";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs)); 
						pst.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Press enter to continue.."); 
				}
			}
		});
		btnShowAllInvoices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select Invoice_Number,Customer_Id,Date_of_Invoice as Invoice_Made,Total_Amount,Payment_Made,Car_Number as Vehicle_Number,Due from invoiceDetails order by Invoice_Number desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalLbl.setText("");
				dueLbl.setText(""); 
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				while(model.getRowCount() > 0) {
					for(int i=0;i<model.getRowCount();i++) {
						model.removeRow(i); 
					}
				}
			}
		});
		btnClear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					totalLbl.setText("");
					dueLbl.setText("");
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					while(model.getRowCount() > 0) {
						for(int i=0;i<model.getRowCount();i++) {
							model.removeRow(i); 
						}
					}
				}
			}
		});
		
		totalLbl = new JLabel("");
		totalLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		totalLbl.setFont(new Font("Calibri", Font.BOLD, 20));
		totalLbl.setForeground(Color.RED);
		
		dueLbl = new JLabel("");
		dueLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		dueLbl.setForeground(Color.BLUE);
		dueLbl.setFont(new Font("Calibri", Font.BOLD, 20));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnShowAllInvoices, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
							.addComponent(dueLbl, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(totalLbl, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 837, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 837, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnShowAllInvoices, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(totalLbl)
						.addComponent(dueLbl, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(17, 70, 803, 14);
		panel.add(separator);
		
		JLabel lblSearchByDate = new JLabel("Search By Date :");
		lblSearchByDate.setForeground(new Color(0, 0, 51));
		lblSearchByDate.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblSearchByDate.setBounds(17, 82, 115, 14);
		panel.add(lblSearchByDate);
		
		JLabel lblFrom = new JLabel("FROM :");
		lblFrom.setForeground(new Color(0, 0, 51));
		lblFrom.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblFrom.setBounds(49, 108, 60, 20);
		panel.add(lblFrom);
		
		from = new JDateChooser();
		from.setDateFormatString("yyyyMMdd");
		from.setBounds(109, 106, 180, 28);
		panel.add(from);
		
		JLabel lblTo = new JLabel("TO :");
		lblTo.setForeground(new Color(0, 0, 51));
		lblTo.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblTo.setBounds(332, 108, 43, 20);
		panel.add(lblTo);
		
		to = new JDateChooser();
		to.setDateFormatString("yyyyMMdd");
		to.setBounds(378, 106, 180, 28);
		panel.add(to);
		
		btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
			    String d = sdf.format(from.getDate());
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
			    String d2 = sdf2.format(to.getDate());
				try {
					String query = "select Invoice_Number,Customer_Id,Date_of_Invoice as Invoice_Made,Payment_Made,Total_Amount,Due,Car_Number as Vehicle_Number "
							+ "from invoicedetails where date(Date_of_Invoice) between "+d+" and "+d2+" "
							+ "order by Invoice_Number desc";
					
					PreparedStatement pst3 = connection.prepareStatement(query);
					ResultSet rs3 = pst3.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs3)); 
					rs3.close();
					pst3.close();
					
					String query2 = "select sum(Payment_Made), sum(Due) "
							+ "from invoicedetails where date(Date_of_Invoice) between "+d+" and "+d2+" "
							+ "order by Invoice_Number desc";
					
					PreparedStatement pst32 = connection.prepareStatement(query2);
					ResultSet rs32 = pst32.executeQuery();
					if (rs32.next()) {
						String add1 = rs32.getString("sum(Due)"); 
						dueLbl.setText("Total Due: Rs. "+add1); 
						String add2 = rs32.getString("sum(Payment_Made)");
						totalLbl.setText("Total Income: Rs. "+add2); 
					}
					rs32.close();
					pst3.close();
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
				    String d = sdf.format(from.getDate());
				    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
				    String d2 = sdf2.format(to.getDate());
					try {
						String query = "select Invoice_Number,Customer_Id,Date_of_Invoice as Invoice_Made,Total_Amount,Due,Car_Number as Vehicle_Number "
								+ "from invoiceDetails where date(Date_of_Invoice) between "+d+" and "+d2+" "
								+ "order by Invoice_Number desc";
						
						PreparedStatement pst3 = connection.prepareStatement(query);
						ResultSet rs3 = pst3.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs3)); 
						rs3.close();
						pst3.close();
						
						String query2 = "select sum(Payment_Made), sum(Due) "
								+ "from invoicedetails where date(Date_of_Invoice) between "+d+" and "+d2+" "
								+ "order by Invoice_Number desc";
						
						PreparedStatement pst32 = connection.prepareStatement(query2);
						ResultSet rs32 = pst32.executeQuery();
						if (rs32.next()) {
							String add1 = rs32.getString("sum(Due)"); 
							dueLbl.setText("Total Due: Rs. "+add1); 
							String add2 = rs32.getString("sum(Payment_Made)");
							totalLbl.setText("Total Income: Rs. "+add2); 
						}
						rs32.close();
						pst3.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnSearch.setBackground(new Color(173, 216, 230));
		btnSearch.setBounds(570, 103, 89, 30);
		panel.add(btnSearch);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{inum, carnum, from, to, btnSearch, btnShowAllInvoices, btnClear}));
	}
}
