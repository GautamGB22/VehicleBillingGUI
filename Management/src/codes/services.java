package codes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.JInternalFrame;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.MatteBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Frame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class services extends JFrame {

	private JPanel contentPane;
	Connection connection = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField vno;
	private JTextField inv;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	private JTable table_4;
	private JScrollPane scrollPane;
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
					services frame = new services();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loaddata(){
		try {
			String query = "select * from service_info where Invoice_Number = '"+inv.getText()+"' or Vehicle_Number = '"+vno.getText()+"' order by Id desc";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs)); 
			pst.close();
			
			String query2 = "select * from invoicedetails where Invoice_Number = '"+inv.getText()+"' or Car_Number = '"+vno.getText()+"' order by Date_of_Invoice desc";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			ResultSet rs2 = pst2.executeQuery();
			table_1.setModel(DbUtils.resultSetToTableModel(rs2)); 
			pst2.close();
			
			String query3 = "select * from cdetails where Car_Number = '"+vno.getText()+"' order by Id desc";
			PreparedStatement pst3 = connection.prepareStatement(query3);
			ResultSet rs3 = pst3.executeQuery();
			table_2.setModel(DbUtils.resultSetToTableModel(rs3)); 
			pst3.close();
			
			String query4 = "select * from labour_discount where Invoice_Id = '"+inv.getText()+"' or Vehicle_Number = '"+vno.getText()+"' order by Date_Time desc";
			PreparedStatement pst4 = connection.prepareStatement(query4);
			ResultSet rs4 = pst4.executeQuery();
			table_3.setModel(DbUtils.resultSetToTableModel(rs4)); 
			pst4.close();
			
			String query5 = "select * from labour_charge where Invoice_Id = '"+inv.getText()+"' or Vehicle_Number = '"+vno.getText()+"' order by Id desc";
			PreparedStatement pst5 = connection.prepareStatement(query5);
			ResultSet rs5 = pst5.executeQuery();
			table_4.setModel(DbUtils.resultSetToTableModel(rs5)); 
			pst5.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void reset() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		while(model.getRowCount() > 0) {
			for(int i=0;i<model.getRowCount();i++) {
				model.removeRow(i); 
			}
		}
		
		DefaultTableModel model1 = (DefaultTableModel)table_1.getModel();
		while(model1.getRowCount() > 0) {
			for(int i=0;i<model1.getRowCount();i++) {
				model1.removeRow(i); 
			}
		}
		
		DefaultTableModel model2 = (DefaultTableModel)table_2.getModel();
		while(model2.getRowCount() > 0) {
			for(int i=0;i<model2.getRowCount();i++) {
				model2.removeRow(i); 
			}
		}
		
		DefaultTableModel model3 = (DefaultTableModel)table_3.getModel();
		while(model3.getRowCount() > 0) {
			for(int i=0;i<model3.getRowCount();i++) {
				model3.removeRow(i); 
			}
		}
		
		DefaultTableModel model4 = (DefaultTableModel)table_4.getModel();
		while(model4.getRowCount() > 0) {
			for(int i=0;i<model4.getRowCount();i++) {
				model4.removeRow(i); 
			}
		}
	}
	/**
	 * Create the frame.
	 */
	public services() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				reset();
			}
		});
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("SERVICING DETAILS :");
		//Database connection
		connection = Dbconn.dbConnection();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1396, 950);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 51, 102));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(15);
		flowLayout.setVgap(25);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("VEHICLE NUMBER :");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel.setForeground(new Color(255, 255, 153));
		panel.add(lblNewLabel);
		
		vno = new JTextField();
		vno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				vno.setText(vno.getText().toUpperCase()); 
				if (e.getKeyCode()==KeyEvent.VK_BACK_SPACE) { 
					try {
						String query = "select * from service_info where Vehicle_Number = '"+vno.getText()+"' order by Id desc";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs)); 
						pst.close();
						
						String query2 = "select * from invoicedetails where Car_Number = '"+vno.getText()+"' order by Date_of_Invoice desc";
						PreparedStatement pst2 = connection.prepareStatement(query2);
						ResultSet rs2 = pst2.executeQuery();
						table_1.setModel(DbUtils.resultSetToTableModel(rs2)); 
						pst2.close();
						
						String query3 = "select * from cdetails where Car_Number = '"+vno.getText()+"' order by Id desc";
						PreparedStatement pst3 = connection.prepareStatement(query3);
						ResultSet rs3 = pst3.executeQuery();
						table_2.setModel(DbUtils.resultSetToTableModel(rs3)); 
						pst3.close();
						
						String query4 = "select * from labour_discount where Vehicle_Number = '"+vno.getText()+"' order by Date_Time desc";
						PreparedStatement pst4 = connection.prepareStatement(query4);
						ResultSet rs4 = pst4.executeQuery();
						table_3.setModel(DbUtils.resultSetToTableModel(rs4)); 
						pst4.close();
						
						String query5 = "select * from labour_charge where Vehicle_Number = '"+vno.getText()+"' order by Id desc";
						PreparedStatement pst5 = connection.prepareStatement(query5);
						ResultSet rs5 = pst5.executeQuery();
						table_4.setModel(DbUtils.resultSetToTableModel(rs5)); 
						pst5.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
				try {
					String query = "select * from service_info where Vehicle_Number = '"+vno.getText()+"' order by Id desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
					
					String query2 = "select * from invoicedetails where Car_Number = '"+vno.getText()+"' order by Date_of_Invoice desc";
					PreparedStatement pst2 = connection.prepareStatement(query2);
					ResultSet rs2 = pst2.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(rs2)); 
					pst2.close();
					
					String query3 = "select * from cdetails where Car_Number = '"+vno.getText()+"' order by Id desc";
					PreparedStatement pst3 = connection.prepareStatement(query3);
					ResultSet rs3 = pst3.executeQuery();
					table_2.setModel(DbUtils.resultSetToTableModel(rs3)); 
					pst3.close();
					
					String query4 = "select * from labour_discount where Vehicle_Number = '"+vno.getText()+"' order by Date_Time desc";
					PreparedStatement pst4 = connection.prepareStatement(query4);
					ResultSet rs4 = pst4.executeQuery();
					table_3.setModel(DbUtils.resultSetToTableModel(rs4)); 
					pst4.close();
					
					String query5 = "select * from labour_charge where Vehicle_Number = '"+vno.getText()+"' order by Id desc";
					PreparedStatement pst5 = connection.prepareStatement(query5);
					ResultSet rs5 = pst5.executeQuery();
					table_4.setModel(DbUtils.resultSetToTableModel(rs5)); 
					pst5.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			}
		});
		vno.setFont(new Font("SansSerif", Font.BOLD, 15));
		panel.add(vno);
		vno.setColumns(14);
			
		JLabel lblInvoiceNumber = new JLabel("INVOICE NUMBER :");
		lblInvoiceNumber.setForeground(new Color(255, 255, 153));
		lblInvoiceNumber.setFont(new Font("SansSerif", Font.BOLD, 12));
		panel.add(lblInvoiceNumber);
		
		inv = new JTextField();
		inv.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_BACK_SPACE) { 
					try {
						String query = "select * from service_info where Invoice_Number = '"+inv.getText()+"' order by Id desc";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs)); 
						pst.close();
						
						String query2 = "select * from invoicedetails where Invoice_Number = '"+inv.getText()+"' order by Date_of_Invoice desc";
						PreparedStatement pst2 = connection.prepareStatement(query2);
						ResultSet rs2 = pst2.executeQuery();
						table_1.setModel(DbUtils.resultSetToTableModel(rs2)); 
						pst2.close();
						
						String query3 = "select * from cdetails where Car_Number = '"+vno.getText()+"' order by Id desc";
						PreparedStatement pst3 = connection.prepareStatement(query3);
						ResultSet rs3 = pst3.executeQuery();
						table_2.setModel(DbUtils.resultSetToTableModel(rs3)); 
						pst3.close();
						
						String query4 = "select * from labour_discount where Invoice_Id = '"+inv.getText()+"' order by Date_Time desc";
						PreparedStatement pst4 = connection.prepareStatement(query4);
						ResultSet rs4 = pst4.executeQuery();
						table_3.setModel(DbUtils.resultSetToTableModel(rs4)); 
						pst4.close();
						
						String query5 = "select * from labour_charge where Invoice_Id = '"+inv.getText()+"' order by Id desc";
						PreparedStatement pst5 = connection.prepareStatement(query5);
						ResultSet rs5 = pst5.executeQuery();
						table_4.setModel(DbUtils.resultSetToTableModel(rs5)); 
						pst5.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
				try {
					String query = "select * from service_info where Invoice_Number = '"+inv.getText()+"' order by Id desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
					
					String query2 = "select * from invoicedetails where Invoice_Number = '"+inv.getText()+"' order by Date_of_Invoice desc";
					PreparedStatement pst2 = connection.prepareStatement(query2);
					ResultSet rs2 = pst2.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(rs2)); 
					pst2.close();
					
					String query3 = "select * from cdetails where Car_Number = '"+vno.getText()+"' order by Id desc";
					PreparedStatement pst3 = connection.prepareStatement(query3);
					ResultSet rs3 = pst3.executeQuery();
					table_2.setModel(DbUtils.resultSetToTableModel(rs3)); 
					pst3.close();
					
					String query4 = "select * from labour_discount where Invoice_Id = '"+inv.getText()+"' order by Date_Time desc";
					PreparedStatement pst4 = connection.prepareStatement(query4);
					ResultSet rs4 = pst4.executeQuery();
					table_3.setModel(DbUtils.resultSetToTableModel(rs4)); 
					pst4.close();
					
					String query5 = "select * from labour_charge where Invoice_Id = '"+inv.getText()+"' order by Id desc";
					PreparedStatement pst5 = connection.prepareStatement(query5);
					ResultSet rs5 = pst5.executeQuery();
					table_4.setModel(DbUtils.resultSetToTableModel(rs5)); 
					pst5.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			}
		});
		inv.setFont(new Font("SansSerif", Font.BOLD, 15));
		inv.setColumns(14);
		panel.add(inv);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 102));
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		contentPane.add(panel_1, BorderLayout.WEST);
		
		JButton btnNewButton = new JButton("LOAD ALL");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loaddata();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_1.add(btnNewButton);
		
		JButton btnClearAll = new JButton("CLEAR ALL");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				
				DefaultTableModel model4 = (DefaultTableModel)table_3.getModel();
				while(model4.getRowCount() > 0) {
					for(int i=0;i<model4.getRowCount();i++) {
						model4.removeRow(i); 
					}
				}
				
				DefaultTableModel model5 = (DefaultTableModel)table_4.getModel();
				while(model5.getRowCount() > 0) {
					for(int i=0;i<model5.getRowCount();i++) {
						model5.removeRow(i); 
					}
				}
			}
		});
		panel_1.add(btnClearAll);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
					.addGap(0))
		);
		
		JPanel panel_3 = new JPanel();
		scrollPane.setViewportView(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(null, "Service Info. :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 139)));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(17, 23, 615, 295);
		panel_4.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Invoice Details :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table_1 = new JTable();
		scrollPane_2.setViewportView(table_1);
		panel_5.setLayout(gl_panel_5);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "Customer Details :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, "Labour Discount :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(139, 0, 0)));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Labour Charges :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(139, 0, 0)));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_6, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1221, Short.MAX_VALUE)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 652, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_8, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 651, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(31))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_5, GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_8.setVerticalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(11, Short.MAX_VALUE))
		);
		
		table_4 = new JTable();
		scrollPane_5.setViewportView(table_4);
		panel_8.setLayout(gl_panel_8);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_7.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 616, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(7, Short.MAX_VALUE))
		);
		
		table_3 = new JTable();
		scrollPane_4.setViewportView(table_3);
		panel_7.setLayout(gl_panel_7);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(47, Short.MAX_VALUE))
		);
		
		table_2 = new JTable();
		scrollPane_3.setViewportView(table_2);
		panel_6.setLayout(gl_panel_6);
		panel_3.setLayout(gl_panel_3);
		panel_2.setLayout(gl_panel_2);
		setLocationRelativeTo(null);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{vno, inv, btnNewButton, btnClearAll}));
	}
}
