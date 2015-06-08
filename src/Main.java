import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;


public class Main extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JTextField txtPin = new JTextField();
	private JLabel lblPinDb = new JLabel("N/A");
	private JLabel lblAvailableDb = new JLabel("N/A:");
	private JLabel lblUsedDb = new JLabel("N/A:");
	private JButton btnActivate = new JButton("Activate");
	
	public Main(){
		super("GIBX PIN Table v.0.5");
		JPanel pane = new JPanel();
		pane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		JLabel lblSearch = new JLabel("Search PIN:");
		JLabel lblPin = new JLabel("PIN:");
		JLabel lblAvailable = new JLabel("Available:");
		JLabel lblUsed = new JLabel("Used:");
		
		btnActivate.setEnabled(false);
		txtPin.setActionCommand("pin_search");
		txtPin.addActionListener(this);
		btnActivate.setActionCommand("activate");
		btnActivate.addActionListener(this);
		pane.add(lblSearch);
		pane.add(txtPin, "w 200, growx, wrap");
		pane.add(lblPin);
		pane.add(lblPinDb, "wrap");
		pane.add(lblAvailable);
		pane.add(lblAvailableDb, "split 2");
		pane.add(btnActivate, "growx, wrap");
		pane.add(lblUsed);
		pane.add(lblUsedDb);
		add(pane);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("pin_search")){
			btnActivate.setEnabled(false);
			PinConnection pinConnection = new PinConnection();
			pinConnection.search(txtPin.getText());
			if(pinConnection.getAvailable().length() == 0){
				lblPinDb.setText("<html><strong>NO SUCH PIN</strong></html>");
				lblAvailableDb.setText("N/A");
				lblUsedDb.setText("N/A");
				pack();
			}else{
				lblPinDb.setText(txtPin.getText());
				lblAvailableDb.setText(pinConnection.getAvailable());
				lblUsedDb.setText(pinConnection.getUsed());
				if(pinConnection.getAvailable().equals("false")){
					btnActivate.setEnabled(true);
				}
			}
		}else if(ae.getActionCommand().equals("activate")){
			PinConnection pinConnection = new PinConnection();
			pinConnection.activate(txtPin.getText());
			if(pinConnection.isUpdated()){
				btnActivate.setEnabled(false);
				lblAvailableDb.setText("true");
			}else{
				JOptionPane.showMessageDialog(null, "Error activing. Please contact Jim");
			}
			
		}
			
	}
	
	public static boolean confirm(){
		JPasswordField jpf = new JPasswordField();
		int res = JOptionPane.showConfirmDialog(null, jpf, "PIN password:", JOptionPane.CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(res == JOptionPane.OK_OPTION){
			String pwd = new String(jpf.getPassword());
			if(!pwd.equals("gibx-ywc-2015b")){
				JOptionPane.showMessageDialog(null, "Incorrect Password", "GIBX PIN Table v.0.5", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Main();
		//if(confirm()) new Main();
	}

}
