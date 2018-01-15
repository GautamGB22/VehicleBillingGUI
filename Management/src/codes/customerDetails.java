package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.border.TitledBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ListSelectionModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class customerDetails extends JFrame {

	private JPanel contentPane;
	private JTextField cname;
	Connection connection = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTable table;
	private JTextField car;
	JLabel confirmlbl = new JLabel("");
	private JTextField c_id;
	private JTextField crn;
	private JTextField cn;

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
					customerDetails frame = new customerDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void refreshTable() {
		try {
			String query = "select Id,Customer_Name,Phone,Car_Number as Vehicle_Number,Date_of_join from cdetails order by Id desc";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs)); 
			pst.close();
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	/**
	 * Create the frame.
	 */
	public customerDetails() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				confirmlbl.setText(""); 
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				while(model.getRowCount() > 0) {
					for(int i=0;i<model.getRowCount();i++) {
						model.removeRow(i); 
					}
				}
			}
		});
		setTitle("Customer Details :");
		
		//Database Connection
		connection = Dbconn.dbConnection();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1137, 709);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 96, 1111, 7);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 154, 1111, 519);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setShowGrid(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					int row = table.getSelectedRow();
					String Table_click = (table.getModel().getValueAt(row, 0).toString());
					String sql = "select * from cdetails where Id='"+Table_click+"'";
					pst = connection.prepareStatement(sql);
					rs = pst.executeQuery();
					if (rs.next()) {
						int add1 = rs.getInt("Id"); 
						String z = Integer.toString(add1);
						c_id.setText(z); 
						String add2 = rs.getString("Customer_Name");
						cn.setText(add2); 
						String add3 = rs.getString("Car_Number");
						crn.setText(add3);
						confirmlbl.setText("[ "+z+", "+add2+", "+add3+" "+"is selected. Click Delete button for deleting the record. ]");  
					}
					pst.close();
					rs.close();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage()); 
				}
			}
		});
		scrollPane.setViewportView(table);
		Image img = new ImageIcon(this.getClass().getResource("/l.png")).getImage();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Search Fields :", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLUE));
		panel.setBounds(10, 11, 1111, 74);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("LOAD");
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					refreshTable();
					confirmlbl.setText(" "); 
				} else {
					
				}
			}
		});
		btnNewButton.setBounds(16, 23, 89, 33);
		panel.add(btnNewButton);
		btnNewButton.setIcon(new ImageIcon(img));
		
		JLabel lblCustomerName = new JLabel("Customer Name :");
		lblCustomerName.setBounds(132, 23, 156, 34);
		panel.add(lblCustomerName);
		lblCustomerName.setFont(new Font("Consolas", Font.PLAIN, 18));
		
		cname = new JTextField();
		cname.setBounds(287, 23, 287, 27);
		panel.add(cname);
		cname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cname.setText(cname.getText().toUpperCase());
				String str = cname.getText();
				if ( !(str.matches("[a-zA-Z0-9]+"))) {
	                  JOptionPane.showMessageDialog(null,"No SPACES Allowed. Please insert only characters.");
	                  cname.setText("");
	            } else {
				try {
					String query = "select Id,Customer_Name,Phone,Car_Number as Vehicle_Number,Date_of_join from cdetails where Customer_Name Like '%"+cname.getText()+"%' order by Id desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
	          }
			}
		});
		cname.setFont(new Font("Cambria", Font.PLAIN, 18));
		cname.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Car Number :");
		lblPhoneNumber.setBounds(678, 21, 138, 27);
		panel.add(lblPhoneNumber);
		lblPhoneNumber.setFont(new Font("Consolas", Font.PLAIN, 18));
		
		car = new JTextField();
		car.setBounds(815, 23, 287, 27);
		panel.add(car);
		car.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				car.setText(car.getText().toUpperCase());
				String str = car.getText();
				if ( !(str.matches("[a-zA-Z0-9]+"))) {
	                  JOptionPane.showMessageDialog(null,"No SPACES Allowed. Please insert only characters.");
	                  car.setText("");
	            } else {
				try {
					String query = "select Id,Customer_Name,Phone,Car_Number as Vehicle_Number,Date_of_join from cdetails where Car_Number Like '%"+car.getText()+"%' order by Id desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
	            }
			}
		});
		car.setFont(new Font("Cambria", Font.PLAIN, 18));
		car.setColumns(10);
		
		crn = new JTextField();
		crn.setBounds(582, 43, 86, 20);
		panel.add(crn);
		crn.setColumns(10);
		
		cn = new JTextField();
		cn.setBounds(584, 17, 86, 20);
		panel.add(cn);
		cn.setColumns(10);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblCustomerName, btnNewButton, cname, car, lblPhoneNumber}));
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete from cdetails where ID = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, c_id.getText()); 
					pst.execute();
					pst.close();
					confirmlbl.setText("[ "+c_id.getText()+", "+cn.getText()+", "+crn.getText()+" "+"is deleted successfully. ]");  
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				refreshTable();
			}
		});
		btnDelete.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					try {
						String query = "delete from cdetails where ID = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, c_id.getText()); 
						pst.execute();
						pst.close();
						confirmlbl.setText("[ "+c_id.getText()+", "+cn.getText()+", "+crn.getText()+" "+"is deleted successfully. ]");  
						
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
					refreshTable();
				}
			}
		});
		btnDelete.setBounds(20, 105, 89, 37);
		contentPane.add(btnDelete);
		confirmlbl.setFont(new Font("Cambria", Font.BOLD, 15));
		confirmlbl.setForeground(Color.RED);
		
		
		confirmlbl.setBounds(134, 114, 987, 29);
		contentPane.add(confirmlbl);
		
		c_id = new JTextField();
		c_id.setBounds(964, 0, 86, 20);
		contentPane.add(c_id);
		c_id.setColumns(10);
		c_id.setVisible(false); 
		cn.setVisible(false);
		crn.setVisible(false); 
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnNewButton, cname, car, btnDelete}));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
				confirmlbl.setText(" "); 
			}
		});
	}
}
