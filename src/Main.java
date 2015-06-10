import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;


public class Main extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnuFile = new JMenu("File");
	private JMenuItem mnuFileQuit = new JMenuItem("Quit");
	private JMenu mnuExport = new JMenu("Export");
	private JMenuItem mnuExportPins = new JMenuItem("Pins");
	private JMenuItem mnuExportLogs = new JMenuItem("Logs");
	
	private JTextField txtPin = new JTextField();
	private JLabel lblPinDb = new JLabel("N/A");
	private JLabel lblAvailableDb = new JLabel("N/A:");
	private JLabel lblUsedDb = new JLabel("N/A:");
	private JButton btnActivate = new JButton("Activate");
	private JLabel lblStatus = new JLabel("Ready.");
	
	private ImageIcon logo = new ImageIcon(getClass().getResource("/res/logo.png"));
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	public Main(){
		super("GIBX PIN Table v.0.5");
		mnuFile.add(mnuFileQuit);
		menuBar.add(mnuFile);
		mnuExport.add(mnuExportPins);
		mnuExport.add(mnuExportLogs);
		menuBar.add(mnuExport);
		mnuFileQuit.setActionCommand("quit");
		mnuExportPins.setActionCommand("export_pins");
		mnuExportLogs.setActionCommand("export_logs");
		mnuFileQuit.addActionListener(this);
		mnuExportPins.addActionListener(this);
		mnuExportLogs.addActionListener(this);
		setJMenuBar(menuBar);
		setIconImage(logo.getImage());
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
		lblStatus.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		pane.add(lblSearch);
		pane.add(txtPin, "w 200, growx, wrap");
		pane.add(lblPin);
		pane.add(lblPinDb, "wrap");
		pane.add(lblAvailable);
		pane.add(lblAvailableDb, "split 2");
		pane.add(btnActivate, "growx, wrap");
		pane.add(lblUsed);
		pane.add(lblUsedDb, "wrap");
		add(pane);
		add(lblStatus, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("quit")){
			System.exit(0);
		}
		if(ae.getActionCommand().equals("export_pins")){
			JFileChooser fileChooser = new JFileChooser();
			File file = null;
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
			}else{
				return;
			}
			try{
				lblStatus.setText("Exporting PINs database. Please wait...");
				lblStatus.paintImmediately(lblStatus.getVisibleRect());
				Thread.sleep(2000);
			}catch(Exception e){
				
			}
			PinConnection pin = new PinConnection();
			boolean exported = pin.exportPins(file.getAbsolutePath().replace("\\", "/"));
			if(exported){
				lblStatus.setText("PINS exported to: " + file.getAbsolutePath() + ".csv");
			}
		}
		if(ae.getActionCommand().equals("export_logs")){
			JFileChooser fileChooser = new JFileChooser();
			File file = null;
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
			}else{
				return;
			}
			try{
				lblStatus.setText("Exporting LOGS database. Please wait...");
				lblStatus.paintImmediately(lblStatus.getVisibleRect());
				Thread.sleep(2000);
			}catch(Exception e){
				
			}
			PinConnection pin = new PinConnection();
			boolean exported = pin.exportLogs(file.getAbsolutePath().replace("\\", "/"));
			if(exported){
				lblStatus.setText("LOGS exported to: " + file.getAbsolutePath() + ".csv");
			}
		}
		if(ae.getActionCommand().equals("pin_search")){
			try{
				lblStatus.setText("Searching PIN: " + txtPin.getText() + "...");
				lblStatus.paintImmediately(lblStatus.getVisibleRect());
				Thread.sleep(2000);
			}catch(Exception e){
				
			}
			btnActivate.setEnabled(false);
			PinConnection pinConnection = new PinConnection();
			pinConnection.search(txtPin.getText());
			if(pinConnection.getAvailable().length() == 0){
				lblPinDb.setText("NO SUCH PIN");
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
			lblStatus.setText("Done.");
		}else if(ae.getActionCommand().equals("activate")){
			String for_ = JOptionPane.showInputDialog("Care Of:", "Maam Reg");
			if(for_ == null){
				JOptionPane.showMessageDialog(null, "Please specify your \"c/o\" to proceed with PIN activation.", "Error", JOptionPane.ERROR_MESSAGE);
				lblStatus.setText("Done.");
				return;
			}
			try{
				lblStatus.setText("Activating PIN: " + txtPin.getText() + "...");
				lblStatus.paintImmediately(lblStatus.getVisibleRect());
				Thread.sleep(2000);
			}catch(Exception e){
				
			}
			PinConnection pinConnection = new PinConnection();
			pinConnection.activate(txtPin.getText());
			if(pinConnection.isUpdated()){
				btnActivate.setEnabled(false);
				lblAvailableDb.setText("true");
				pinConnection.addHistory(Util.getPCName(), Util.getName(), for_, dateFormat.format(new Date()), txtPin.getText());
			}else{
				JOptionPane.showMessageDialog(null, "Error activing. Please contact Jim");
			}
			lblStatus.setText("Done.");
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
		}else{
			return false;
		}
		return true;
	}

	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		new Main();
		if(confirm()) new Main();
	}
}
