package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ListSelectionModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class items_sold extends JFrame {

	private JPanel contentPane;
	Connection connection = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField searchtxt;
	private JTable table;
	private JTextField idtxt;
	private JDateChooser to;
	private JButton btnSearch;
	private JDateChooser from;
	private JButton btnLoadData;
	private JButton btnClear;
	private JButton btnDelete;

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
					items_sold frame = new items_sold();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void refreshTable() {
		try {
			String query = "select Id,Car_Number as Vehicle_Number,Item_Name,Pieces as Quantity,Price_per_item,Date_of_sold,Invoice_id "
					+ "from isolds order by Id desc";
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
	public items_sold() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				while(model.getRowCount() > 0) {
					for(int i=0;i<model.getRowCount();i++) {
						model.removeRow(i); 
					}
				}
			}
		});
		setTitle("Items Sold :");
		connection = Dbconn.dbConnection();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1143, 688);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Search Data :", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 153)));
		panel.setBounds(10, 0, 1123, 136);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSearchByInvoice = new JLabel("Search By Vehicle Number :");
		lblSearchByInvoice.setFont(new Font("Calibri", Font.BOLD, 16));
		lblSearchByInvoice.setBounds(164, 43, 225, 14);
		panel.add(lblSearchByInvoice);
		
		searchtxt = new JTextField();
		searchtxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String query = "select * from isolds where Car_Number Like '%"+searchtxt.getText()+"%' order by Id desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage()); 
				}
			}
		});
		searchtxt.setToolTipText("Search sold items.");
		searchtxt.setBounds(164, 57, 234, 30);
		panel.add(searchtxt);
		searchtxt.setColumns(10); 
		
		btnLoadData = new JButton("LOAD DATA");
		btnLoadData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					try {
						String query = "select Id,Car_Number as Vehicle_Number,Item_Name,Pieces as Quantity,Price_per_item,Date_of_sold,Invoice_id from isolds order by Id desc";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs)); 
						pst.close();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage()); 
					}
				} else {

				}
			}
		});
		btnLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select Id,Car_Number as Vehicle_Number,Item_Name,Pieces as Quantity,Price_per_item,Date_of_sold,Invoice_id from isolds order by Id desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); 
					pst.close();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage()); 
				}
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("/l.png")).getImage();
		btnLoadData.setIcon(new ImageIcon(img));
		btnLoadData.setBounds(16, 12, 106, 33);
		panel.add(btnLoadData);
		
		btnDelete = new JButton("DELETE");
		btnDelete.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					try {
						String query = "delete from isolds where ID = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, idtxt.getText()); 
						pst.execute();
						pst.close();
						JOptionPane.showMessageDialog(null, "Item with Id = '"+idtxt.getText()+"' is deleted successfully."); 
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
					refreshTable();
					idtxt.setText(""); 
					
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete from isolds where ID = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, idtxt.getText()); 
					pst.execute();
					pst.close();
					JOptionPane.showMessageDialog(null, "Item with Id = '"+idtxt.getText()+"' is deleted successfully."); 
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				refreshTable();
				idtxt.setText("");  
			}
		});
		btnDelete.setBounds(16, 86, 106, 33);
		panel.add(btnDelete);
		
		idtxt = new JTextField();
		idtxt.setBounds(1018, 12, 86, 20);
		panel.add(idtxt);
		idtxt.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(134, 22, 18, 91);
		panel.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(434, 22, 18, 91);
		panel.add(separator_2);
		
		JLabel lblSearchByDate = new JLabel("Search By Date :");
		lblSearchByDate.setFont(new Font("Calibri", Font.BOLD, 16));
		lblSearchByDate.setBounds(448, 41, 202, 14);
		panel.add(lblSearchByDate);
		
		JLabel lblFrom = new JLabel("FROM :");
		lblFrom.setFont(new Font("Calibri", Font.BOLD, 16));
		lblFrom.setBounds(498, 73, 56, 14);
		panel.add(lblFrom);
		
		JLabel lblTo = new JLabel("TO :");
		lblTo.setFont(new Font("Calibri", Font.BOLD, 16));
		lblTo.setBounds(768, 73, 32, 14);
		panel.add(lblTo);
		
		from = new JDateChooser();
		from.setBounds(552, 64, 167, 28);
		panel.add(from);
		
		to = new JDateChooser();
		to.setBounds(806, 64, 167, 28);
		panel.add(to);
		
		btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
			    String d = sdf.format(from.getDate());
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
			    String d2 = sdf2.format(to.getDate());
				try {
					String query = "select Id,Car_Number as Vehicle_Number,Item_Name,Pieces as Quantity,Price_per_item,Date_of_sold,Invoice_id "
							+ "from isolds where Date(Date_of_sold) between "+d+" and "+d2+" "
							+ "order by Id desc";
					
					PreparedStatement pst3 = connection.prepareStatement(query);
					ResultSet rs3 = pst3.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs3)); 
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
						String query = "select Id,Car_Number as Vehicle_Number,Item_Name,Pieces as Quantity,Price_per_item,Date_of_sold,Invoice_id "
								+ "from isolds where Date(Date_of_sold) between "+d+" and "+d2+" "
								+ "order by Id desc";
						
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
		btnSearch.setBackground(new Color(173, 216, 230));
		btnSearch.setBounds(999, 57, 90, 38);
		panel.add(btnSearch);
		
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
		btnClear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					while(model.getRowCount() > 0) {
						for(int i=0;i<model.getRowCount();i++) {
							model.removeRow(i); 
						}
					}
				}
			}
		});
		btnClear.setBounds(16, 43, 68, 33);
		panel.add(btnClear);
		idtxt.setVisible(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 148, 1117, 500);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					int row = table.getSelectedRow();
					String Table_click = (table.getModel().getValueAt(row, 0).toString());
					String sql = "select * from isolds where Id='"+Table_click+"'";
					pst = connection.prepareStatement(sql);
					rs = pst.executeQuery();
					if (rs.next()) {
						int add1 = rs.getInt("Id"); 
						String z = Integer.toString(add1);
						idtxt.setText(z);   
					}
					pst.close();
					rs.close();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage()); 
				}
			}
		});
		scrollPane.setViewportView(table);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnLoadData, btnClear, btnDelete, searchtxt, btnSearch}));
	}
}
