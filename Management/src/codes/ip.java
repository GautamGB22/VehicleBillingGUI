package codes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Cursor;

public class ip {
	
	private JFrame frame;
	servicing_details s = new servicing_details();
	customerDetails cd = new customerDetails();
	Bill_Tabb b = new Bill_Tabb();
	items_sold is = new items_sold();
	invoicesMade im = new invoicesMade();
	dues d = new dues();
	Passwrd p = new Passwrd();
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
					ip window = new ip();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	/**
	 * Create the application.
	 */
	public ip() {
		initialize();
		connection = Dbconn.dbConnection();
		if (connection==null) { 
			System.exit(0); 
		}else {
			JOptionPane.showMessageDialog(null, "Server created & connected successfully....","Server Connected"
								, JOptionPane.INFORMATION_MESSAGE); 
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 975, 584);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 0, 153));
		panel.setBounds(0, 0, 969, 203);
		frame.getContentPane().add(panel);
		
		JLabel label = new JLabel("VEHICLE MAINTENANCE SOFTWARE");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 25));
		label.setBounds(43, 33, 513, 45);
		panel.add(label);
		
		JLabel label_1 = new JLabel("By- Gautam Bhattacharya");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Arial", Font.PLAIN, 14));
		label_1.setBounds(798, 176, 171, 27);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("One Click Solution...");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 18));
		label_2.setBounds(43, 89, 182, 27);
		panel.add(label_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(43, 78, 897, 2);
		panel.add(separator);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/a.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img)); 
		lblNewLabel.setBounds(818, 11, 122, 67);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 203, 969, 352);
		frame.getContentPane().add(panel_1);
		
		JButton button = new JButton("ITEMS SOLD");
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					
					is.setVisible(true); 
				}
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				is.setVisible(true); 
			}
		});
		Image img4 = new ImageIcon(this.getClass().getResource("/i.png")).getImage();
		button.setIcon(new ImageIcon(img4)); 
		button.setForeground(new Color(0, 0, 153));
		button.setFont(new Font("Cambria", Font.PLAIN, 13));
		button.setBounds(270, 203, 201, 80);
		panel_1.add(button);
		Image img3 = new ImageIcon(this.getClass().getResource("/b.png")).getImage();
		
		JButton btnCustomerDetails = new JButton("CUSTOMER DETAILS");
		btnCustomerDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCustomerDetails.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					
					cd.setVisible(true); 
				}
			}
		});
		Image img2 = new ImageIcon(this.getClass().getResource("/c.png")).getImage();
		btnCustomerDetails.setIcon(new ImageIcon(img2)); 
		btnCustomerDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cd.setVisible(true); 
			}
		});
		btnCustomerDetails.setForeground(new Color(0, 0, 153));
		btnCustomerDetails.setFont(new Font("Cambria", Font.PLAIN, 13));
		btnCustomerDetails.setBounds(162, 83, 201, 80);
		panel_1.add(btnCustomerDetails);
		
		JButton btnSales = new JButton("SALES");
		btnSales.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSales.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
				
					im.setVisible(true); 
				}
			}
		});
		btnSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				im.setVisible(true); 
			}
		});
		Image img5 = new ImageIcon(this.getClass().getResource("/im.png")).getImage();
		btnSales.setIcon(new ImageIcon(img5)); 
		btnSales.setForeground(new Color(0, 0, 153));
		btnSales.setFont(new Font("Cambria", Font.PLAIN, 13));
		btnSales.setBounds(541, 203, 178, 80);
		panel_1.add(btnSales);
		
		JButton button_4 = new JButton("DUE AMOUNTS");
		button_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					
					d.setVisible(true);
				}
			}
		});
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				d.setVisible(true); 
			}
		});
		Image img6 = new ImageIcon(this.getClass().getResource("/d.png")).getImage();
		button_4.setIcon(new ImageIcon(img6)); 
		button_4.setForeground(new Color(0, 0, 153));
		button_4.setFont(new Font("Cambria", Font.PLAIN, 13));
		button_4.setBounds(651, 83, 182, 80);
		panel_1.add(button_4);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(391, 67, 12, 109);
		panel_1.add(separator_1);
		separator_1.setBackground(Color.WHITE);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBackground(Color.WHITE);
		separator_2.setBounds(623, 67, 12, 109);
		panel_1.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setVisible(false);
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBackground(Color.WHITE);
		separator_3.setBounds(24, 118, 12, 85);
		panel_1.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(136, 187, 724, 16);
		panel_1.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setBackground(Color.WHITE);
		separator_5.setBounds(505, 203, 16, 85);
		panel_1.add(separator_5);
		
		JButton btnNewButton = new JButton("SERVICING");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				s.setVisible(true);
			}
		});
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					s.setVisible(true); 
				}
			}
		});
		Image img8 = new ImageIcon(this.getClass().getResource("/s.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(img8));
		btnNewButton.setFont(new Font("Cambria", Font.PLAIN, 13));
		btnNewButton.setForeground(new Color(0, 0, 153));
		btnNewButton.setBounds(415, 83, 182, 80);
		panel_1.add(btnNewButton);
		
		JButton btnTabb = new JButton("< Master Reset Button >");
		btnTabb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.setVisible(true); 
			}
		});
		btnTabb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTabb.setToolTipText("DELETE ALL DATA FROM RECORD.");
		btnTabb.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnTabb.setForeground(new Color(255, 255, 0));
		btnTabb.setBackground(new Color(255, 0, 0));
		btnTabb.setBounds(10, 313, 178, 28);
		panel_1.add(btnTabb);
		
		JButton button_1 = new JButton("BILLING");
		button_1.setVisible(false);
		button_1.setBounds(10, 6, 182, 80);
		panel_1.add(button_1);
		button_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					
					b.setVisible(true);
				}
			}
		});
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				b.setVisible(true); 
			}
		});
		button_1.setIcon(new ImageIcon(img3)); 
		button_1.setForeground(new Color(0, 0, 153));
		button_1.setFont(new Font("Cambria", Font.PLAIN, 13));
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnCustomerDetails, btnNewButton, button_4, button, btnSales}));
	}
}
