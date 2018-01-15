package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.JobAttributes;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Passwrd extends JFrame {

	private JPanel contentPane;
	private JPasswordField pwd;
	Connection connection = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
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
					Passwrd frame = new Passwrd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void del(){
		try {
			String query = "select * from Ptable where passwrd=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, pwd.getText());
			ResultSet rs = ps.executeQuery();
			int count=0;
			while(rs.next()){
				count = count + 1;
			}
			if(count==1){
				JOptionPane.showMessageDialog(null,"All RECORDS IS DELETED", "Data Trucated by the user:", JOptionPane.INFORMATION_MESSAGE); 
				try {
					String queryy = "delete from alldates";
					PreparedStatement pstt = connection.prepareStatement(queryy);
					pstt.execute();
					pstt.close();
					
					String queryy2 = "delete from cdetails";
					PreparedStatement pstt2 = connection.prepareStatement(queryy2);
					pstt2.execute();
					pstt2.close();
					
					String queryy3 = "delete from dues";
					PreparedStatement pstt3 = connection.prepareStatement(queryy3);
					pstt3.execute();
					pstt3.close();
					
					String queryy4 = "delete from ftable";
					PreparedStatement pstt4 = connection.prepareStatement(queryy4);
					pstt4.execute();
					pstt4.close();
					
					String queryy5 = "delete from invoicedetails";
					PreparedStatement pstt5 = connection.prepareStatement(queryy5);
					pstt5.execute();
					pstt5.close();
					
					String queryy6 = "delete from isolds";
					PreparedStatement pstt6 = connection.prepareStatement(queryy6);
					pstt6.execute();
					pstt6.close();
					
					String queryy7 = "delete from labour_charge";
					PreparedStatement pstt7 = connection.prepareStatement(queryy7);
					pstt7.execute();
					pstt7.close();
					
					String queryy8 = "delete from labour_discount";
					PreparedStatement pstt8 = connection.prepareStatement(queryy8);
					pstt8.execute();
					pstt8.close();
					
					String queryy10 = "delete from service_info";
					PreparedStatement pstt10 = connection.prepareStatement(queryy10);
					pstt10.execute();
					pstt10.close();
					
					String query11 = "delete from item_category";
					PreparedStatement pstt11 = connection.prepareStatement(query11);
					pstt11.execute();
					pstt11.close();
					
					String query12 = "delete from fdes";
					PreparedStatement pstt12 = connection.prepareStatement(query12);
					pstt12.execute();
					pstt12.close();
					
					connection.commit();
				} catch (Exception e) {
					
				}
			}
			else if(count > 1){
				JOptionPane.showMessageDialog(null,"Dublicate Username and Password");
			}
			else {
				JOptionPane.showMessageDialog(null,"Username and Password is not correct. Try Again!");
			}
			rs.close();
			ps.close();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2); 
		}
	}
	
	/**
	 * Create the frame.
	 */
	public Passwrd() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				pwd.setText(""); 
			}
		});
		setTitle("DELETE ALL RECORDS:");
		//Database Connection
		connection = Dbconn.dbConnection();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setResizable(false);
		setBounds(100, 100, 417, 140);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null); 
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(255, 255, 0));
		panel.setBackground(new Color(0, 0, 51));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblEnterPassword_1 = new JLabel("ENTER PASSWORD :");
		lblEnterPassword_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEnterPassword_1.setForeground(new Color(255, 255, 0));
		lblEnterPassword_1.setBounds(15, 15, 135, 14);
		panel.add(lblEnterPassword_1);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure to delete all data from record?", "WARNING", 
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					del();
				} else {
				    // no option
				}
			}
		});
		btnOk.setBounds(167, 37, 90, 31);
		panel.add(btnOk);
		
		pwd = new JPasswordField();
		pwd.setEchoChar('#');
		pwd.setForeground(new Color(102, 0, 153));
		pwd.setFont(new Font("SansSerif", Font.BOLD, 14));
		pwd.setHorizontalAlignment(SwingConstants.CENTER);
		pwd.setBounds(14, 36, 148, 32);
		panel.add(pwd);
		
		JLabel im = new JLabel("");
		Image img6 = new ImageIcon(this.getClass().getResource("/delall.png")).getImage();
		im.setIcon(new ImageIcon(img6)); 
		im.setForeground(new Color(51, 255, 0));
		im.setBounds(281, 6, 99, 89);
		panel.add(im);
		//ldpwd.setVisible(false); 
	}
}
