package codes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class servicing_details extends JFrame {

	private JPanel contentPane;
	
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
					servicing_details frame = new servicing_details();
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
	public servicing_details() {
		Tabb t = new Tabb();
		services ss = new services();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 525, 197);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null); 
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), ": Select :", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 153)));
		panel.setBounds(10, 11, 489, 136);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnServicing = new JButton("SERVICING");
		Image img = new ImageIcon(this.getClass().getResource("/Receipt_48px.png")).getImage();
		btnServicing.setIcon(new ImageIcon(img)); 
		btnServicing.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					t.setVisible(true); 
				}
			}
		});
		btnServicing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.setVisible(true);
			}
		});
		btnServicing.setBounds(19, 28, 197, 84);
		panel.add(btnServicing);
		
		JButton btnServicingDetails = new JButton("SERVICING DETAILS");
		Image img2 = new ImageIcon(this.getClass().getResource("/searchService.png")).getImage();
		btnServicingDetails.setIcon(new ImageIcon(img2)); 
		btnServicingDetails.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) { 
					ss.setVisible(true); 
				}
			}
		});
		btnServicingDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ss.setVisible(true); 
			}
		});
		btnServicingDetails.setBounds(266, 28, 206, 84);
		panel.add(btnServicingDetails);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(239, 28, 15, 84);
		panel.add(separator);
	}
}
