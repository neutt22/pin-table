import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Main(){
		super("GIBX PIN Table v.0.5");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		if(confirm()) new Main();
	}

}
