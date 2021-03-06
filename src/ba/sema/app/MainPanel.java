package ba.sema.app;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


@SuppressWarnings("serial")
public class MainPanel extends JPanel 
{
	String serverKey;
	String deviceToken;
	//
	JLabel lblServerKey;
	JTextField txtServerKey;
	JLabel lblDeviceToken;
	JTextField txtDeviceToken;
	JLabel lblNotificationTitle;
	JTextField txtNotificationTitle;
	JLabel lblNotificationText;
	JTextArea txtNotificationText;
	JButton btnSend;
	JLabel lblStatus;
	//
	boolean sendAsync = false;
	
	public MainPanel()
	{
		serverKey = PropertiesHelper.GetPropertyValue("Default_FirebaseServerKey");
		deviceToken = PropertiesHelper.GetPropertyValue("Default_FirebaseDeviceToken");
		// System.out.println("Pročitano:\n" + serverKey + "\n" + deviceToken);
	}
	
	public Dimension velicinaPanela() {
		return new Dimension(435, 355);
	}
	
	public void postaviKomponente() {
		lblServerKey = new JLabel("Server Key:", JLabel.LEFT);
		lblServerKey.setBounds(20, 20, 70, 20);
		
		txtServerKey = new JTextField();
		txtServerKey.setBounds(110, 20, 300, 22);
		txtServerKey.setText(serverKey);
		
		lblDeviceToken = new JLabel("Device Token:", JLabel.LEFT);
		lblDeviceToken.setBounds(20, 50, 80, 20);
		
		txtDeviceToken = new JTextField();
		txtDeviceToken.setBounds(110, 50, 300, 22);
		txtDeviceToken.setText(deviceToken);
		
		lblNotificationTitle = new JLabel("Notification Title:", JLabel.LEFT);
		lblNotificationTitle.setBounds(20, 90, 100, 20);
		
		txtNotificationTitle = new JTextField();
		txtNotificationTitle.setBounds(20, 113, 390, 22);
		
		lblNotificationText = new JLabel("Notification Text:", JLabel.LEFT);
		lblNotificationText.setBounds(20, 143, 100, 20);
		
		txtNotificationText = new JTextArea();
		txtNotificationText.setBounds(20, 165, 390, 100);
		txtNotificationText.setBorder(BorderFactory.createCompoundBorder(txtServerKey.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		txtNotificationText.setLineWrap(true);
		txtNotificationText.setWrapStyleWord(true);
		
		btnSend = new JButton("Send Notification");
		btnSend.setBounds(20, 280, 150, 30);
		btnSend.setFocusPainted(false);
		btnSend.addActionListener(sendListener());
		
		lblStatus = new JLabel("", JLabel.LEFT);
		lblStatus.setBounds(190, 280, 200, 30);
		
		add(lblServerKey);
		add(txtServerKey);
		add(lblDeviceToken);
		add(txtDeviceToken);
		add(lblNotificationTitle);
		add(txtNotificationTitle);
		add(lblNotificationText);
		add(txtNotificationText);
		add(btnSend);
		add(lblStatus);
	}
	
	private ActionListener sendListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtServerKey.getText().trim().equals("") || txtDeviceToken.getText().trim().equals("") || txtNotificationText.getText().trim().equals(""))
				{
					lblStatus.setText("Unesite podatke!");
				}
				else
				{
					String result = "";
					if (sendAsync)
					{
						result = FirebaseHelper.SendNotificationAsync(
									txtServerKey.getText().trim(), 
									txtDeviceToken.getText().trim(), 
									txtNotificationTitle.getText().trim(), 
									txtNotificationText.getText().trim());
					}
					else
					{
						result = FirebaseHelper.SendNotification(
									txtServerKey.getText().trim(), 
									txtDeviceToken.getText().trim(), 
									txtNotificationTitle.getText().trim(), 
									txtNotificationText.getText().trim());
					}
					lblStatus.setText(result);
				}
			}
		};
	}
}
