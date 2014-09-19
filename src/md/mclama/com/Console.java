package md.mclama.com;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Console extends JFrame {

	private JPanel contentPane;
	public JTextArea txtConsole;
	private JLabel lblFilterBy;
	private JComboBox comboBox;
	
	private List<String> logs = new ArrayList<String>(); 
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Console frame = new Console();
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
	public Console() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				scrollConsole();
			}
		});
		setTitle("McLauncher - Console");
		setLocationRelativeTo(null);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Rectangle bnds = getBounds();
				scrollPane_1.setBounds(0,22,bnds.width-28,bnds.height-72);
				lblFilterBy.setBounds(bnds.width-145, 3, 44, 14);
				comboBox.setBounds(bnds.width-93, 0, 65, 20);
				
			}
		});
		setBounds(100, 100, 450, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateConsole();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"All", "Log", "Warning", "Error", "Severe"}));
		comboBox.setBounds(357, 0, 65, 20);
		panel.add(comboBox);
		
		lblFilterBy = new JLabel("Filter by");
		lblFilterBy.setBounds(305, 3, 42, 14);
		panel.add(lblFilterBy);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 22, 422, 258);
		panel.add(scrollPane_1);
		
		txtConsole = new JTextArea();
		scrollPane_1.setViewportView(txtConsole);
		txtConsole.setEditable(false);
	}
	
	public void log(String log, String text){ //add a log which we can classify as error/warning/severe or just a log
		log = "[" + log + "]-";
		
		addlog(log + text);
	}
	
	public void addlog(String text){
		logs.add(text);
		System.out.println(text);
		updateConsole();
	}
		
	public void updateConsole(){
		String str="";
		for(int i=0; i<logs.size(); i++){
			String line = logs.get(i);
			String filterStr = comboBox.getSelectedItem().toString();
			if(filterStr.equals("All") || line.contains("[" + filterStr + "]-")){ //if all, or filter contains string. Show
				str += line + "\n";
			}
		}
		txtConsole.setText(str);
		scrollConsole();
	}

	public void scrollConsole() {
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
}
