package md.mclama.com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JToggleButton;
import javax.swing.JSeparator;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;


public class ModManager extends JFrame{
	
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	static final String McVersion = "0.4.3"; //Build 19
	static final String McCheckVersionPath = "http://mclama.com/McLauncher/McLauncher%20Version.txt";
	static final String McLauncherPath = "http://mclama.com/McLauncher/Downloads/McLauncher.jar";
	static final String McUpdaterPath = "http://mclama.com/McLauncher/Downloads/McLauncher.jar";
	private final boolean testBtnEnabled=false; //a test button that is to be removed on release
	
	public ModManager McLauncher = this;
	public Utility util;

	private JPanel contentPane;
	private DefaultListModel<String> listModel;
	String gamePath;
	private JTextField txtGamePath;
	private String[] modDir;
	private List<Profile> profiles = new ArrayList<Profile>(); 
	private Profile selProfile;
	JButton go;
	private int trow=0;
	   
	JFileChooser chooser;
	String choosertitle;
	private JLabel lblAvailableMods;
	private int modCount;
	private JTextField txtProfile;
	private JButton btnNewProfile;
	private DefaultListModel<String> profileListMdl;
	private JList<String> profileList;
	private JLabel lblModsEnabled;
	private JList<String> enabledModsList;
	private DefaultListModel<String> ModListModel;
	private JList<String> modsList;

	private JLabel lblModVersion;

	private JLabel lblRequiredMods;
	private String reqModsStr = "None";

	private final JButton btnUpdate = new JButton("Update Launcher");

	private JLabel lblModRequires;

	private JButton btnLaunchIgnore;
	private JPanel panelLauncher;
	protected JProgressBar pBarDownloadMod;
	protected JProgressBar pBarExtractMod;
	protected JLabel lblDownloadModInfo;
	private JScrollPane scrollPane_3;
	private JButton btnDownload;
	private JButton btnGotoMod;
	private JTable tableDownloads;
	private BufferedImage modImg;
	private JPanel panelModImg;
	private JTextField txtFilterText;
	protected String[] dlModList;
	private DefaultTableModel dlModel;
	private JLabel lblModDlCounter;
	private JTextArea txtrDMModDescription;
	private JTextArea lblDMModTags;
	private JTextArea lblDMRequiredMods;

	private TableRowSorter<DefaultTableModel> tSorter;

	private String dlModListSelected;

	protected boolean canDownloadMod=false;
	private JLabel lblDLModLicense;
	protected boolean downloading=false;

	private URL modPageUrl;
	private JPanel panelOptions;
	private final JScrollPane scrollPane_4 = new JScrollPane();
	private JPanel panel;
	private JLabel lblCloseMclauncherAfter;
	private JLabel lblCloseMclauncherAfter_1;
	private JLabel lblSortNewestDownloadable;
	private JToggleButton tglbtnNewModsFirst;
	protected JToggleButton tglbtnCloseAfterUpdate;
	private JToggleButton tglbtnCloseAfterLaunch;
	private JToggleButton tglbtnDisplayon;
	private JToggleButton tglbtnDisplayoff;
	private JLabel lblInfo;
	private JComboBox comboBox;
	private JLabel lblNeedrestart;
	protected JToggleButton tglbtnSendAnonData;
	private JLabel lblSendAnonymousUse;
	private JLabel lblWipmod;
	private JLabel lblDeleteOldMod;
	protected JToggleButton tglbtnDeleteBeforeUpdate;
	protected String modPath;
	
	public static Console con;
	private JSeparator separator_4;
	private JToggleButton tglbtnAlertOnModUpdateAvailable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					con = new Console();
					con.setVisible(false);
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				    int x = (int) ((dimension.getWidth() - con.getWidth()) / 2);
				    int y = (int) ((dimension.getHeight() - con.getHeight()) / 2);
				    con.setLocation(x, y);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
					ModManager frame = new ModManager();
					frame.setResizable(false);
					frame.setVisible(true);
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
				    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
				    frame.setLocation(x, y);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	@SuppressWarnings("serial")
	public ModManager() throws MalformedURLException {
		setResizable(false);
		setTitle("McLauncher " + McVersion);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 694, 372);
		contentPane.add(tabbedPane);
		
		
		profileListMdl = new DefaultListModel<String>();
		ModListModel = new DefaultListModel<String>();
		listModel = new DefaultListModel<String>();
		getCurrentMods();
		
		panelLauncher = new JPanel();
		tabbedPane.addTab("Launcher", null, panelLauncher, null);
		panelLauncher.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(556, 36, 132, 248);
		panelLauncher.add(scrollPane);
		profileList = new JList<String>(profileListMdl);
		scrollPane.setViewportView(profileList);
		
		btnNewProfile = new JButton("New");
		btnNewProfile.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnNewProfile.setBounds(479, 4, 76, 20);
		panelLauncher.add(btnNewProfile);
		btnNewProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newProfile(txtProfile.getText());
			}
		});
		btnNewProfile.setToolTipText("Click to create a new profile.");
		
		JButton btnRenameProfile = new JButton("Rename");
		btnRenameProfile.setBounds(479, 25, 76, 20);
		panelLauncher.add(btnRenameProfile);
		btnRenameProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				renameProfile();
			}
		});
		btnRenameProfile.setToolTipText("Click to rename the selected profile");
		
		JButton btnDelProfile = new JButton("Delete");
		btnDelProfile.setBounds(479, 50, 76, 20);
		panelLauncher.add(btnDelProfile);
		btnDelProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteProfile();
			}
		});
		btnDelProfile.setToolTipText("Click to delete the selected profile.");
		
		JButton btnLaunch = new JButton("Launch");
		btnLaunch.setBounds(605, 319, 89, 23);
		panelLauncher.add(btnLaunch);
		btnLaunch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selProfile!=null){
					LaunchFactorioWithSelectedMods(false); //dont ignore
				}
			}
		});
		btnLaunch.setToolTipText("Click to launch factorio with the selected mod profile.");
		
		lblAvailableMods = new JLabel("Available Mods");
		lblAvailableMods.setBounds(4, 155, 144, 14);
		panelLauncher.add(lblAvailableMods);
		lblAvailableMods.setFont(new Font("SansSerif", Font.PLAIN, 10));
		
		lblAvailableMods.setText("Available Mods: " + -1);
		
		txtGamePath = new JTextField();
		txtGamePath.setBounds(4, 5, 211, 23);
		panelLauncher.add(txtGamePath);
		txtGamePath.setToolTipText("Select tha path to your game!");
		txtGamePath.setFont(new Font("Tahoma", Font.PLAIN, 8));
		txtGamePath.setText("Game Path");
		txtGamePath.setColumns(10);
		
		JButton btnFind = new JButton("find");
		btnFind.setBounds(227, 3, 32, 23);
		panelLauncher.add(btnFind);
		
		txtProfile = new JTextField();
		txtProfile.setBounds(556, 2, 132, 22);
		panelLauncher.add(txtProfile);
		txtProfile.setToolTipText("The name of NEW or RENAME profiles");
		txtProfile.setText("Profile1");
		txtProfile.setColumns(10);
		
		lblModsEnabled = new JLabel("Mods Enabled: -1");
		lblModsEnabled.setBounds(335, 155, 95, 16);
		panelLauncher.add(lblModsEnabled);
		lblModsEnabled.setFont(new Font("SansSerif", Font.PLAIN, 10));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 167, 211, 165);
		panelLauncher.add(scrollPane_1);
		modsList = new JList<String>(listModel);
		modsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String modName=util.getModVersion(modsList.getSelectedValue());
				lblModVersion.setText("Mod Version: " + modName);
				checkDependency(modsList);
				if(modName.contains(".zip")){
					new File(System.getProperty("java.io.tmpdir") + modName.replace(".zip", "")).delete();
				}
			}
		});
		scrollPane_1.setViewportView(modsList);
		modsList.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(333, 167, 211, 165);
		panelLauncher.add(scrollPane_2);
		enabledModsList = new JList<String>(ModListModel);
		enabledModsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lblModVersion.setText("Mod Version: " + util.getModVersion(enabledModsList.getSelectedValue()));
				checkDependency(enabledModsList);
			}
		});
		enabledModsList.setFont(new Font("SansSerif", Font.PLAIN, 10));
		scrollPane_2.setViewportView(enabledModsList);
		
		JButton btnEnable = new JButton("Enable");
		btnEnable.setBounds(223, 200, 90, 28);
		panelLauncher.add(btnEnable);
		btnEnable.setToolTipText("Add mod -->");
		
		JButton btnDisable = new JButton("Disable");
		btnDisable.setBounds(223, 240, 90, 28);
		panelLauncher.add(btnDisable);
		btnDisable.setToolTipText("Disable mod ");
		
		JLabel lblModsAvailable = new JLabel("Mods available");
		lblModsAvailable.setBounds(4, 329, 89, 14);
		panelLauncher.add(lblModsAvailable);
		lblModsAvailable.setFont(new Font("SansSerif", Font.PLAIN, 10));
		
		JLabel lblEnabledMods = new JLabel("Enabled Mods");
		lblEnabledMods.setBounds(337, 329, 89, 16);
		panelLauncher.add(lblEnabledMods);
		lblEnabledMods.setFont(new Font("SansSerif", Font.PLAIN, 10));
		
		lblModVersion = new JLabel("Mod Version: (select a mod first)");
		lblModVersion.setBounds(4, 117, 183, 14);
		panelLauncher.add(lblModVersion);
		lblModVersion.setFont(new Font("SansSerif", Font.PLAIN, 10));
		
		lblRequiredMods = new JLabel("Required Mods: " + reqModsStr);
		lblRequiredMods.setBounds(6, 143, 538, 14);
		panelLauncher.add(lblRequiredMods);
		lblRequiredMods.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRequiredMods.setFont(new Font("SansSerif", Font.PLAIN, 10));
		
		JButton btnNewButton = new JButton("TEST");
		btnNewButton.setVisible(testBtnEnabled);
		btnNewButton.setEnabled(testBtnEnabled);
		btnNewButton.setBounds(338, 61, 90, 28);
		panelLauncher.add(btnNewButton);
		btnUpdate.setBounds(218, 322, 103, 20);
		panelLauncher.add(btnUpdate);
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				util.updateLauncher();
			}
		});
		btnUpdate.setVisible(false);
		btnUpdate.setFont(new Font("SansSerif", Font.PLAIN, 9));
		
		lblModRequires = new JLabel("Mod Requires: (Select a mod first)");
		lblModRequires.setBounds(4, 127, 317, 16);
		panelLauncher.add(lblModRequires);
		
		btnLaunchIgnore = new JButton("Launch + ignore");
		btnLaunchIgnore.setBounds(566, 284, 123, 23);
		panelLauncher.add(btnLaunchIgnore);
		
		JButton btnConsole = new JButton("Console");
		btnConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean changeto = !con.isVisible();
				con.setVisible(changeto);
				con.updateConsole();
			}
		});
		btnConsole.setBounds(335, 0, 90, 28);
		panelLauncher.add(btnConsole);
		
		JPanel panelDownloadMods = new JPanel();
		tabbedPane.addTab("Download Mods", null, panelDownloadMods, null);
		panelDownloadMods.setLayout(null);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(0, 0, 397, 303);
		panelDownloadMods.add(scrollPane_3);
		
		dlModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Mod Name", "Author", "Version", "Tags"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, Object.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
						false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				};
			};

		tSorter = new TableRowSorter<DefaultTableModel>(dlModel);
		tableDownloads = new JTable();
		tableDownloads.setRowSorter(tSorter);
		tableDownloads.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				trow = tableDownloads.rowAtPoint(e.getPoint()); //set new table row
				trow = tableDownloads.getRowSorter().convertRowIndexToModel(trow);
				//findModInDownloads(dlModel.getValueAt(tableDownloads.rowAtPoint(e.getPoint(),0).toString());
				getDlModData();
				canDownloadMod=true;
			}
		});
		tableDownloads.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tableDownloads.setShowVerticalLines(true);
		tableDownloads.setShowHorizontalLines(true);
		tableDownloads.setModel(dlModel);
		tableDownloads.getColumnModel().getColumn(0).setPreferredWidth(218);
		tableDownloads.getColumnModel().getColumn(1).setPreferredWidth(97);
		tableDownloads.getColumnModel().getColumn(2).setPreferredWidth(77);
		scrollPane_3.setViewportView(tableDownloads);
		
		btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(canDownloadMod && !downloading){
					String dlUrl = getModDownloadUrl();
					try {
						if(dlUrl.equals("") || dlUrl.equals(" ") || dlUrl==null){
							con.log("Log","No download link for mod, got... '" + dlUrl + "'");
						}else {
							downloading=true;
							Download dler = new Download(new URL(dlUrl),McLauncher);
						}
					} catch (MalformedURLException e1) {
						con.log("Log","Failed to download mod... No download URL?");
					}
				}
			}
		});
		btnDownload.setBounds(307, 308, 90, 28);
		panelDownloadMods.add(btnDownload);
		
		btnGotoMod = new JButton("Mod Page");
		btnGotoMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				util.openWebpage(modPageUrl);
			}
		});
		btnGotoMod.setEnabled(false);
		btnGotoMod.setBounds(205, 308, 90, 28);
		panelDownloadMods.add(btnGotoMod);
		
		pBarDownloadMod = new JProgressBar();
		pBarDownloadMod.setBounds(538, 308, 150, 10);
		panelDownloadMods.add(pBarDownloadMod);
		
		pBarExtractMod = new JProgressBar();
		pBarExtractMod.setBounds(538, 314, 150, 10);
		panelDownloadMods.add(pBarExtractMod);
		
		lblDownloadModInfo = new JLabel("Download progress");
		lblDownloadModInfo.setBounds(538, 326, 150, 16);
		panelDownloadMods.add(lblDownloadModInfo);
		lblDownloadModInfo.setHorizontalAlignment(SwingConstants.CENTER);
		
		panelModImg = new JPanel();
		panelModImg.setBounds(566, 0, 128, 128);
		panelDownloadMods.add(panelModImg);
		
		txtFilterText = new JTextField();
		txtFilterText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtFilterText.getText().equals("Filter Text")){
					txtFilterText.setText("");
					newFilter();
				}
			}
		});
		txtFilterText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!txtFilterText.getText().equals("Filter Text")){
					newFilter();
				}
			}
		});
		txtFilterText.setText("Filter Text");
		txtFilterText.setBounds(0, 308, 122, 28);
		panelDownloadMods.add(txtFilterText);
		txtFilterText.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				newFilter();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"No tag filter", "Vanilla", "Machine", "Mechanic", "New Ore", "Module", "Big Mod", "Power", "GUI", "Map-Gen", "Must-Have", "Equipment"}));
		comboBox.setBounds(403, 44, 150, 26);
		panelDownloadMods.add(comboBox);
		
		lblModDlCounter = new JLabel("Mod database: ");
		lblModDlCounter.setFont(new Font("SansSerif", Font.PLAIN, 10));
		lblModDlCounter.setBounds(403, 5, 162, 16);
		panelDownloadMods.add(lblModDlCounter);
		
		txtrDMModDescription = new JTextArea();
		txtrDMModDescription.setBackground(Color.LIGHT_GRAY);
		txtrDMModDescription.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtrDMModDescription.setFocusable(false);
		txtrDMModDescription.setEditable(false);
		txtrDMModDescription.setLineWrap(true);
		txtrDMModDescription.setWrapStyleWord(true);
		txtrDMModDescription.setText("Mod Description: ");
		txtrDMModDescription.setBounds(403, 132, 285, 75);
		panelDownloadMods.add(txtrDMModDescription);
		
		lblDMModTags = new JTextArea();
		lblDMModTags.setFocusable(false);
		lblDMModTags.setEditable(false);
		lblDMModTags.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDMModTags.setWrapStyleWord(true);
		lblDMModTags.setLineWrap(true);
		lblDMModTags.setBackground(Color.LIGHT_GRAY);
		lblDMModTags.setText("Mod Tags: ");
		lblDMModTags.setBounds(403, 71, 160, 60);
		panelDownloadMods.add(lblDMModTags);
		
		lblDMRequiredMods = new JTextArea();
		lblDMRequiredMods.setFocusable(false);
		lblDMRequiredMods.setEditable(false);
		lblDMRequiredMods.setText("Required Mods: ");
		lblDMRequiredMods.setWrapStyleWord(true);
		lblDMRequiredMods.setLineWrap(true);
		lblDMRequiredMods.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDMRequiredMods.setBackground(Color.LIGHT_GRAY);
		lblDMRequiredMods.setBounds(403, 208, 285, 57);
		panelDownloadMods.add(lblDMRequiredMods);
		
		lblDLModLicense = new JLabel("");
		lblDLModLicense.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDLModLicense.setBounds(403, 294, 285, 16);
		panelDownloadMods.add(lblDLModLicense);
		
		lblWipmod = new JLabel("");
		lblWipmod.setBounds(397, 314, 51, 16);
		panelDownloadMods.add(lblWipmod);
		
		panelOptions = new JPanel();
		tabbedPane.addTab("Options", null, panelOptions, null);
		panelOptions.setLayout(null);
		scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_4.setBounds(0, 0, 694, 342);
		panelOptions.add(scrollPane_4);
		
		panel = new JPanel();
		scrollPane_4.setViewportView(panel);
		panel.setLayout(null);
		
		lblCloseMclauncherAfter = new JLabel("Close McLauncher after launching Factorio?");
		lblCloseMclauncherAfter.setBounds(6, 6, 274, 16);
		panel.add(lblCloseMclauncherAfter);
		
		lblCloseMclauncherAfter_1 = new JLabel("Close McLauncher after updating?");
		lblCloseMclauncherAfter_1.setBounds(6, 34, 274, 16);
		panel.add(lblCloseMclauncherAfter_1);
		
		lblSortNewestDownloadable = new JLabel("Sort newest downloadable mods first?");
		lblSortNewestDownloadable.setBounds(6, 62, 274, 16);
		panel.add(lblSortNewestDownloadable);
		
		tglbtnNewModsFirst = new JToggleButton("Toggle");
		tglbtnNewModsFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNeedrestart.setText("McLauncher needs to restart for that to work");
				writeData();
			}
		});
		tglbtnNewModsFirst.setSelected(true);
		tglbtnNewModsFirst.setBounds(281, 56, 66, 28);
		panel.add(tglbtnNewModsFirst);
		
		tglbtnCloseAfterUpdate = new JToggleButton("Toggle");
		tglbtnCloseAfterUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeData();
			}
		});
		tglbtnCloseAfterUpdate.setBounds(281, 28, 66, 28);
		panel.add(tglbtnCloseAfterUpdate);
		
		tglbtnCloseAfterLaunch = new JToggleButton("Toggle");
		tglbtnCloseAfterLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeData();
			}
		});
		tglbtnCloseAfterLaunch.setBounds(281, 0, 66, 28);
		panel.add(tglbtnCloseAfterLaunch);
		
		tglbtnDisplayon = new JToggleButton("On");
		tglbtnDisplayon.setSelected(true);
		tglbtnDisplayon.setBounds(588, 308, 44, 28);
		panel.add(tglbtnDisplayon);
		
		tglbtnDisplayoff = new JToggleButton("Off");
		tglbtnDisplayoff.setBounds(644, 308, 44, 28);
		panel.add(tglbtnDisplayoff);
		
		lblInfo = new JLabel("What enabled and disabled look like");
		lblInfo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblInfo.setBounds(391, 314, 199, 16);
		panel.add(lblInfo);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 55, 676, 24);
		panel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 27, 676, 18);
		panel.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(6, 84, 676, 24);
		panel.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(346, 0, 16, 336);
		panel.add(separator_3);
		
		lblNeedrestart = new JLabel("");
		lblNeedrestart.setBounds(6, 314, 341, 16);
		panel.add(lblNeedrestart);
		
		tglbtnSendAnonData = new JToggleButton("Toggle");
		tglbtnSendAnonData.setSelected(true); //set enabled by default.
		tglbtnSendAnonData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeData();
			}
		});
		tglbtnSendAnonData.setBounds(622, 0, 66, 28);
		panel.add(tglbtnSendAnonData);
		
		lblSendAnonymousUse = new JLabel("Send anonymous use data?");
		lblSendAnonymousUse.setBounds(359, 6, 231, 16);
		panel.add(lblSendAnonymousUse);
		
		lblDeleteOldMod = new JLabel("Delete old mod before updating?");
		lblDeleteOldMod.setBounds(359, 34, 180, 16);
		panel.add(lblDeleteOldMod);
		
		tglbtnDeleteBeforeUpdate = new JToggleButton("Toggle");
		tglbtnDeleteBeforeUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeData();
			}
		});
		tglbtnDeleteBeforeUpdate.setBounds(622, 28, 66, 28);
		panel.add(tglbtnDeleteBeforeUpdate);
		
		tglbtnAlertOnModUpdateAvailable = new JToggleButton("Toggle");
		tglbtnAlertOnModUpdateAvailable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeData();
			}
		});
		tglbtnAlertOnModUpdateAvailable.setSelected(true);
		tglbtnAlertOnModUpdateAvailable.setBounds(281, 86, 66, 28);
		panel.add(tglbtnAlertOnModUpdateAvailable);
		
		separator_4 = new JSeparator();
		separator_4.setBounds(0, 112, 676, 24);
		panel.add(separator_4);
		
		JLabel lblAlertModHas = new JLabel("Alert mod has update on launch?");
		lblAlertModHas.setBounds(6, 92, 231, 16);
		panel.add(lblAlertModHas);
		btnLaunchIgnore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selProfile!=null){
					LaunchFactorioWithSelectedMods(true); //ignore errors, launch.
				}
			}
		});
		btnLaunchIgnore.setVisible(false);
		//This is my test button. I use this to test things then implement them into the swing.
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testButtonCode(e);
			}
		});
		//Disable mods button. (from profile)
		btnDisable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeMod();
			}
		});
		//Enable mods button. (to profile)
		btnEnable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addMod();
			}
		});
		//Game path button
		btnFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findPath();
			}
		});
		//mouseClick event lister for when you click on a new profile
		profileList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedProfile();
			}
		});


		readData(); //Load settings

		init();     //some extra init
		getMods();  //Get the mods the user has installed
	}

	protected void testButtonCode(ActionEvent e) {
		boolean changeto = !con.isVisible();
		con.setVisible(changeto);
		//con.txtConsole.setText("Wall Of Text \n Test2?");
		
	}

	protected String getModDownloadUrl() {
		String[] modData = dlModList[trow].split("~"); //trow = Download Mods Table Row
		return modData[7]; //Return mod data
	}

	protected void findModInDownloads(String string) {
		boolean foundit=false;
		for(int i=0; i<dlModList.length; i++){
			String[] modData = dlModList[i].split("~");
			if(modData[0].toLowerCase().equals(string)){
				foundit=true;
				dlModListSelected = dlModList[i];
				break;
			}
		}
	}

	//0modname, 1author, 2version, 3mod_tags, 4description, 5required_mods, 
	//6updates, 7download_url, 8update_url, 9icon_url, 10downloads, 11mod_page, 12copyright
	
	protected void getDlModData() { //Display the data from the mod selected in Download Mods
		String[] modData = dlModList[trow].split("~");
		lblDMModTags.setText("Mod Tags: " + modData[3]);
		//Set description text size smaller if too large
		if(util.getStringWidth("Mod Description: " + modData[4])>800){
			txtrDMModDescription.setFont(new Font("SansSerif", Font.PLAIN, 10));
		} else txtrDMModDescription.setFont(new Font("SansSerif", Font.PLAIN, 12));
		con.log("Log","Selected downloadable mod " + modData[0]);
		
		txtrDMModDescription.setText("Mod Description: " + modData[4]);
		lblDMRequiredMods.setText("Required Mods: " + modData[5]);
		
		if(modData[9].equals("")){
			setModImg("http://www.mclama.com/Downloads/ModIcon.png");
			//If we found no icon, use the default icon provided.
		}
		else setModImg(modData[9]);
		if(!modData[11].equals("")){
			try { //If mod has a mod page, enable the button and set the URL
				modPageUrl = new URL(modData[11]);
				btnGotoMod.setEnabled(true);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else btnGotoMod.setEnabled(false); //If we don't have a mod page, disable the button.
		if(!modData[12].equals(" ")) { //Show a license for the mod.
			String str = modData[0] + " is under the " + modData[12] + " license.";
			if(util.getStringWidth(str)>280){
				lblDLModLicense.setFont(new Font("SansSerif", Font.PLAIN, 10));
			} else lblDLModLicense.setFont(new Font("SansSerif", Font.PLAIN, 12));
			lblDLModLicense.setText(str);
		}
		else lblDLModLicense.setText(""); //If no license, Then don't show.
		//Show the user that this is a WIP mod before downloading
		if(modData[3].contains("WIP")) lblWipmod.setText("WIP Mod!");
		else lblWipmod.setText("");
		
		//if user already has mod, Consider this mod an update
		if(util.IsModInstalled(modData[0])){
			btnDownload.setText("Update");
		}
		else btnDownload.setText("Download"); //if not, its a new download.
		
	}
	
	private void newFilter() { //Filter the mods in Download Mods based off mod tags and search filter
		String regex = "";
		String comboText = "";
		if(!txtFilterText.getText().equals("Filter Text")){
			regex = "(?i)" + Pattern.quote(txtFilterText.getText().toLowerCase());
		}
		if(!comboBox.getSelectedItem().toString().equals("No tag filter")){
			 comboText = "(?i)" + Pattern.quote(comboBox.getSelectedItem().toString());
		}
		List<RowFilter<Object,Object>> rfs =  new ArrayList<RowFilter<Object,Object>>(2);
		rfs.add(RowFilter.regexFilter(regex, 0,1,2));
		rfs.add(RowFilter.regexFilter(comboText, 3));

	    RowFilter<DefaultTableModel, Object> rf = null;
	    //If current expression doesn't parse, don't update.
	    try {
	        //rf = RowFilter.regexFilter(regex,0,1,2,3);
	    	rf = RowFilter.andFilter(rfs);
	    } catch (java.util.regex.PatternSyntaxException e) {
	        return;
	    }
	    tSorter.setRowFilter(rf);
	}

	private void init() {
		util = new Utility(this);
		try {
			String out = new Scanner(new URL(McCheckVersionPath).openStream(), "UTF-8").useDelimiter("\\A").next();
			con.log("Log","McLauncher version: " + McVersion + " Checked version: " +out);
			//Compare versions
			if(!util.testSameVersion(out,McVersion) && util.newerVersion(out, McVersion)){
				btnUpdate.setVisible(true);
				//Disabled for now
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Statistics so i can see usage
		util.noteInfo("Launch");
		//Set default mod icon to show
		setModImg("http://www.mclama.com/Downloads/ModIcon.png");
		//Hide the tags from the jTable so we can sort through it later
		int tindex = tableDownloads.getColumnModel().getColumnIndex("Tags");
		TableColumn col = tableDownloads.getColumnModel().getColumn(tindex);
		tableDownloads.getColumnModel().removeColumn(col);
	
		//0modname, 1author, 2version, 3mod_tags, 4description, 5required_mods, 
		//6updates, 7download_url, 8update_url, 9icon_url, 10downloads, 11mod_page, 12copyright
		
		setModDownloads();
		
		/*
		 * Options
		 */
		if(tglbtnNewModsFirst.isSelected()) Collections.reverse(Arrays.asList(dlModList)); //reverse our list of mods data.
	}
	
	private void setModDownloads() {
		String downloadModList = util.getModDownloads();
		dlModList = downloadModList.split(";");
		con.log("Log","Downloadable mods found... " + dlModList.length);
		lblModDlCounter.setText("Downloadable mod Count: " + dlModList.length);
		
		if(tglbtnNewModsFirst.isSelected()){
			for(int i=dlModList.length-1; i>-1; i--){
				addModDownloadToModel(i);
			}
		}
		else {
			for(int i=0; i<dlModList.length; i++){
				addModDownloadToModel(i);
			}
		}
	}

	private void addModDownloadToModel(int i) {
		String[] modData = dlModList[i].split("~");
		dlModel.addRow(new Object[]{modData[0].toLowerCase(), modData[1], modData[2], modData[3]});
	}

	private void setModImg(String modImgPath) {
		try {
			modImg = util.scanImage(modImgPath);
			panelModImg.removeAll();
			panelModImg.add(new JLabel(new ImageIcon(modImg))); 
		} catch (IOException e) {
			con.log("Error", "Failed to set mod image.");
			e.printStackTrace();
		}
	}

	protected void checkDependency(JList<String> list){
		checkDependency(util.getModDependency(list.getSelectedValue()).replace("[","").replace("]","").replace("\"","").split(","));
	}

	protected String checkDependency(String[] depen) {
		String requiredMods = "";
		if(depen[0]!=""){
			//con.log("Log","Dependency.. " + depen[0] + "-------" + depen[1]);
			for(int i=0; i<depen.length; i++){
				boolean optmod = false;
				boolean reqmv = false; //required mod version
				String[] optstr = null;
				String[] reqstr = null;
				byte optbyte=0;
				if(depen[i].contains("?")){
					//Optional mod
					optmod = true;
					optstr = depen[i].split("\\? ");//0 will always be empty
					//con.log("Log",optstr[0]); //0 will always be empty
					//con.log("Log",optstr[1]);
				}
				if(depen[i].contains(">=")){
					//requires mod over version
					reqmv = true;
					if(optmod){
						reqstr = optstr[1].split(" >= ");
					}else
						reqstr = depen[i].split(" >= ");
					//con.log("Log",reqstr[0]);
					//con.log("Log",reqstr[1]);
				}
				else {
					//just requires mod
				}
				String depenMod = "";
				if(optmod){  //optional mod
					if(reqmv){ //required mod version
						depenMod = reqstr[0];
					}
					else depenMod = optstr[1];
				}
				if(reqmv){
					String depenVers = reqstr[1];
					if(!optmod) depenMod = reqstr[0];
					con.log("Log",depenMod + " with vers " + depenVers);
					if(!modIsEnabled(depenMod)){//if mod is not already enabled
						if(!optmod){            //if its not a optional mod
							requiredMods+=(depenMod+"_"+depenVers+", ");
						}
					}
				}
				else {
					//con.log("Log",depen[i]);
					if(!modIsEnabled(depen[i])){
						requiredMods+=depen[i]+", ";
					}
				}
			}//end for loop
			if(requiredMods.equals(", ") || requiredMods.equals("")) requiredMods="None or Already enabled";
			lblModRequires.setText("Mod Requires: " + requiredMods);
			return requiredMods;
		}else con.log("Log","no mod dependency");
		lblModRequires.setText("Mod Requires: None or Already enabled");
		return "None or Already enabled";
	}

	private boolean modIsEnabled(String depenMod) {
		for(int i=selProfile.mods.size()-1; i>=0; i--) {
			String p = selProfile.mods.get(i);
			if(p.equals(depenMod)){  //if the mod is enabled
				return true;
			}
		}
		return false;
	}

	protected void deleteProfile() {
		String str = (String) profileList.getSelectedValue();
		profileListMdl.removeAllElements();

		for(int i=0; i<=profiles.size()-1; i++) {
			Profile p = profiles.get(i);
			if(p.name.equals(str)){
				selProfile=null;
				profiles.remove(i); //remove profile from list
				con.log("Log","Deleted profile... " + str);
				writeData();
			}
			else {
				profileListMdl.addElement(p.name);
			}
		}
	}


	protected void renameProfile() {
		boolean doit = true;
		for(int i=profiles.size()-1; i>=0; i--) {
			Profile p = profiles.get(i);
			if(p.name.equals(txtProfile.getText())){
				doit = false; //if we find a duplicate name, don't do it!
			}
		}
		if(doit){
			selProfile.name = txtProfile.getText();
			profileListMdl.removeAllElements();
	
			for(int i=0; i<=profiles.size()-1; i++) {
				Profile p = profiles.get(i);
				profileListMdl.addElement(p.name);
				if(p.name.equals(txtProfile.getText())){
					profileList.setSelectedValue(profileListMdl.lastElement(), true);
					selectedProfile();
					con.log("Log","Renamed profile");
					writeData();
				}
			}
		}
	}

	private void selectedProfile() {
		try {
			String name = profileList.getSelectedValue().toString();
			con.log("Log","Selected profile " + name);
			for(int i=profiles.size()-1; i>=0; i--) {
				Profile p = profiles.get(i);
				if(name.equals(p.name)){
					selProfile = p; //selected profile
					i=0;
					lblModsEnabled.setText("Enabled Mods: "+selProfile.mods.size());
					updateEnabledMods();
				}
			}
		} catch (Exception e) {
			con.log("Warning", "Failed to select the previously selected profile on launch");
		}
	}

	protected void removeMod() {
		if(enabledModsList.getSelectedValue()==null){
			con.log("Log","please select a mod to remove first");
		}
		else 
		{ //No problems, lets go
			String str = enabledModsList.getSelectedValue().toString();
			selProfile.removeMod(str);
			updateEnabledMods();
		}
		writeData();
	}

	protected void addMod() {
		if(modsList.getSelectedValue()==null){
			con.log("Log","please select a mod to add first");
		}
		else if(profileList.getSelectedValue()==null){
			con.log("Log","please select a profile before adding a mod");
		}else 
		{ //No problems, lets go
			String str = modsList.getSelectedValue().toString();

			if(selProfile.findMod(str)){
				selProfile.mods.add(str);
				updateEnabledMods();
			}
		}
		writeData();
	}


	protected void updateEnabledMods() {
		ModListModel.removeAllElements();
		for(int i=selProfile.mods.size()-1; i>=0; i--) {
			String p = selProfile.mods.get(i);
			ModListModel.addElement(p);
		}
		lblModsEnabled.setText("Enabled Mods: "+selProfile.mods.size());
	}


	protected void newProfile(String text) {
		boolean doit = true;
		for(int i=profiles.size()-1; i>=0; i--) {
			Profile p = profiles.get(i);
			if(p.name.equals(text)){
				doit = false;
			}
		}
        if(doit && text.length()>2){
			profiles.add(new Profile(text,"base"));
			profileListMdl.addElement(text);
			txtProfile.setText("Profile"+(profiles.size()+1));
        }
        writeData();
	}

	@SuppressWarnings("unchecked")
	private void readData() {
		JSONParser parser = new JSONParser();
		 
		try {
		 
			Object obj = parser.parse(new FileReader("McLauncher.json"));
			con.log("Log","Running McLauncher from " + new File(ModManager.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
			 
			JSONObject jsonObject = (JSONObject) obj;
			 
			JSONArray msg = (JSONArray) jsonObject.get("data");
			Iterator<JSONObject> iterator = msg.iterator();
			JSONObject factObj = iterator.next();
			
			gamePath = (String) factObj.get("path");
			if(gamePath==null) gamePath = (String) parser.parse(new FileReader("."));
			txtGamePath.setText(gamePath);
			con.log("Log","game path set to..." + gamePath);
			checkAccess();
			
			String tempPath = System.getenv("APPDATA") + "\\factorio\\mods\\";
			con.log("Log","Mods folder set to ... " + tempPath);
			if(new File(tempPath).isDirectory()){
				modPath = tempPath;
				con.log("Log","Using installer factorio.");
			}
			else {
				modPath = gamePath + "\\mods\\";
				con.log("Log","Using zip factorio.");
			}
			//This method fails if the user has both installed, and is running from the zip.
			con.log("Log","Using mod path " + modPath);
			
			
			String lastProfile = (String) factObj.get("lastprofile");
			//Options tab
			try {
				tglbtnNewModsFirst.setSelected((boolean) factObj.get("newModsFirst"));
				tglbtnCloseAfterLaunch.setSelected((boolean) factObj.get("closeAfterLaunch"));
				tglbtnCloseAfterUpdate.setSelected((boolean) factObj.get("closeAfterUpdate"));
				tglbtnSendAnonData.setSelected((boolean) factObj.get("sendAnonData"));
				tglbtnDeleteBeforeUpdate.setSelected((boolean) factObj.get("deleteBeforeUpdate"));
				tglbtnAlertOnModUpdateAvailable.setSelected((boolean) factObj.get("AlertOnModUpdateAvailable"));
			} catch (Exception e1) {
				con.log("Warning","Failed to read some data. Was McLauncher updated?");
			}
			
			
			msg = (JSONArray) jsonObject.get("profiles");
			iterator = msg.iterator();
			int count=0;
			factObj = iterator.next();
			
			msg = (JSONArray) factObj.get("profile" + count);
			Iterator<JSONObject> iter = msg.iterator();
			while(iter.hasNext()){
				JSONObject pObj = iter.next();
				 
				String name = (String) pObj.get("name");
				String mods = (String) pObj.get("mods");
				con.log("Log","Profile: " + name + " with " + mods + " loaded.");
				 
				Profile pfile = new Profile(name, null);
				profiles.add(pfile);
				profileListMdl.addElement(name);
				//Lets add its mods now
				//con.log("Log",mods.replace("[", "").replace("]", ""));
				String[] pmods = mods.replace("[", "").replace("]", "").split(", ");
				for(int i=0; i<pmods.length; i++){
					pfile.mods.add(pmods[i]);
				}
	
				try{
					if(lastProfile.equals(name)){
						profileList.setSelectedValue(profileListMdl.lastElement(), true);
						selectedProfile();
					}
				}catch(Exception e){
					con.log("Error"," Last profile was null");
				}
				 
				count++;
				try {
					msg = (JSONArray) factObj.get("profile" + count);
					iter = msg.iterator();
				}
				catch (NullPointerException e){
					con.log("Log",count + " profiles loaded.");
					break;
				}
			}
			txtProfile.setText("Profile"+(profiles.size()+1));
			
			 
		} catch (FileNotFoundException e) {
			con.log("Log", "McLauncher.json not found, Saving first-time info.");
			newProfile("First profile");
			profileList.setSelectedValue(profileListMdl.lastElement(), true);
			selectedProfile();
			writeData();
		 } catch (IOException e) {
		 e.printStackTrace();
		 } catch (ParseException e) {
		 e.printStackTrace();
		 
		}
	}

	@SuppressWarnings("unchecked")
	private void writeData() {
		JSONObject obj = new JSONObject();
		JSONArray dlist = new JSONArray();
		JSONObject data = new JSONObject();
		//Data
		data.put("path", gamePath);
		data.put("lastprofile", profileList.getSelectedValue());
		//Options
		data.put("newModsFirst", tglbtnNewModsFirst.isSelected());
		data.put("closeAfterLaunch", tglbtnCloseAfterLaunch.isSelected());
		data.put("closeAfterUpdate", tglbtnCloseAfterUpdate.isSelected());
		data.put("sendAnonData", tglbtnSendAnonData.isSelected());
		data.put("deleteBeforeUpdate", tglbtnDeleteBeforeUpdate.isSelected());
		data.put("AlertOnModUpdateAvailable", tglbtnAlertOnModUpdateAvailable.isSelected());
		//put list into data
		dlist.add(data);
		
		obj.put("data", dlist);
		
		//Profiles
		JSONArray profileList = new JSONArray(); //profiles list
		JSONObject profileListObj = new JSONObject(); //profiles
		JSONArray profileDataList = new JSONArray(); //profile data list
		JSONObject profileData = new JSONObject(); //profile data
		
		//We now have to cycle every profile and create the data, then add it to the list.
		for(int i=profiles.size()-1; i>=0; i--) {
			Profile p = profiles.get(i);
			profileData = new JSONObject();
			profileDataList = new JSONArray();
			profileData.put("name", p.name);
			profileData.put("mods", p.mods.toString());
			//con.log("Log",p.name + "..." + p.mods.toString());
			profileDataList.add(profileData);
			//con.log("Log","list.." + profileDataList.toString());
			profileListObj.put("profile"+i, profileDataList);
		}
		profileList.add(profileListObj);
		obj.put("profiles", profileList);
		
		 try {
			 FileWriter file = new FileWriter("McLauncher.json");
			 file.write(obj.toJSONString());
			 file.flush();
			 file.close();
		 } catch (IOException e) {
			 con.log("Severe","FAILED to save McLauncher data");
			 e.printStackTrace();
		 }
		 con.log("Log","Saved McLauncher data.");
	}
	
	private void getModCount() {
		if(modPath == null){
			getMods();
		}
		else if(modDir!=null){
			modCount = modDir.length+1; //+1 for base
			con.log("Log","Available Mods: " + modCount);
			lblAvailableMods.setText("Available Mods: " + modCount);
		}
	}
	
	protected void getMods() {
		if(modPath != null){
			File file = new File(modPath);
			modDir = file.list(new FilenameFilter() {
			  @Override
			  public boolean accept(File current, String name) {
				  File mfile = new File(current, name);
				  if(name.endsWith(".zip") || mfile.isDirectory())
			    return true;
				  else return false;
			  }
			});
			//con.log("Log",Arrays.toString(modDir));
			getModCount();
			getCurrentMods();
		}
	}
	
	
	public void findPath() {
	    chooser = new JFileChooser(); 
	    if(gamePath!= null) chooser.setCurrentDirectory(new java.io.File(gamePath));
	    else chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(choosertitle);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	      con.log("Log","getCurrentDirectory(): " 
	         +  chooser.getCurrentDirectory());
	      
	      con.log("Log","getSelectedFile() : " 
	         +  chooser.getSelectedFile());
	      
	      gamePath=""+chooser.getSelectedFile();
	      if(gamePath.endsWith("\\mods")) {gamePath = gamePath.replace("\\mods", "");}
	      con.log("Log",gamePath);
	      txtGamePath.setText(gamePath);
	      
	      getMods();
	      }
	    else {
	      con.log("Log","No Selection ");
	      }
	}


	private void getCurrentMods() {
		listModel.removeAllElements();
		String updateableMods="";
		if(modDir!=null){
			listModel.addElement("base");
			for(int i=0; i<modDir.length; i++){
				listModel.addElement(modDir[i]);
				String modname = modDir[i];
				if(tglbtnAlertOnModUpdateAvailable.isSelected()) {updateableMods += util.checkModForUpdate(modname);} //Check if the mod has an update
			}
			if(tglbtnAlertOnModUpdateAvailable.isSelected()){
				if(!updateableMods.equals(""))
					JOptionPane.showMessageDialog(null, "The following mods can be updated.\n" + updateableMods);
			}
		}
		else {
			listModel.addElement("Please select a Game Path.");
			listModel.addElement("An example would be:");
			listModel.addElement("G:\\Games\\Factorio_0.9.8.9400");
		}
		
	}
	
	private void checkAccess(){
		if(!(gamePath.equals("") | gamePath==null)){
			File f = new File(gamePath); //Check if we have read and write access
			if(f.canWrite() & f.canRead()) {
			  con.log("Log","Access to read and write allowed");
			} else {
				JOptionPane.showMessageDialog(null, "McLauncher does not have access to read or write files.\nMclauncher wont work without access.");
				System.exit(0);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void LaunchFactorioWithSelectedMods(boolean ignore) {
		if(enabledModsDependencyCheck(ignore) || ignore){
			if(selProfile.mods.size()!=0){
				JSONObject obj = new JSONObject();
				JSONArray dlist = new JSONArray();
				JSONObject data = new JSONObject();
				//Data
				for(int i=selProfile.mods.size()-1; i>=0; i--) {
					String str = selProfile.mods.get(i);
					data = new JSONObject();
					data.put("name", util.remVer(str.replace(".zip","")));
					data.put("enabled", "true");
					dlist.add(data);
				}
			
				obj.put("mods", dlist);
			
				 try {
					 FileWriter file = new FileWriter(modPath+"mod-list.json");
					 file.write(obj.toJSONString());
					 file.flush();
					 file.close();
					 con.log("Log","Saved mod-list.json    " + modPath+"mod-list.json");
				 } catch (IOException e) {
					 con.log("Severe", "Failed to save mod-list.json, Maybe no write access?");
				 }
			}
			 
			File fWindows = new File(gamePath + "\\bin\\win32\\Factorio.exe");
			File fWindows64 = new File(gamePath + "\\bin\\x64\\Factorio.exe");
			File fLinux = new File(gamePath+ "\\bin\\i386\\factorio");
			File fLinux64 = new File(gamePath + "\\bin\\x64\\factorio");
			File fMac = new File(gamePath); 
			 
			if(System.getProperty("os.name").toLowerCase().contains("windows")){
				if(fWindows.exists()){
					runGame(fWindows.toString());
				}
				else if(fWindows64.exists()){
					runGame(fWindows64.toString());
				}
			}
			else  if(System.getProperty("os.name").toLowerCase().contains("linux")){
				if(fLinux.exists()){
					runGame(fLinux.toString());
				}
				else if(fLinux64.exists()){
					runGame(fLinux64.toString());
				}
			}
			else  if(System.getProperty("os.name").toLowerCase().contains("mac")){
				if(fMac.exists()){
					runGame(fMac.toString());
				}
			}
			 
			con.log("Log","Launching game with profile..." + selProfile.name);
			//Statistics
			util.noteInfo("GameLaunch");
			if(tglbtnCloseAfterLaunch.isSelected()){ //Option 
				System.exit(0); //close launcher if enabled
			}
		}
	}

	private boolean enabledModsDependencyCheck(boolean ignore) {
		String checkStr="";
		boolean canLaunch=true;
		for(int i=selProfile.mods.size()-1; i>=0; i--) {
			String p = selProfile.mods.get(i);
			//String str = checkDependency(p);
			String str = checkDependency(util.getModDependency(p).replace("[","").replace("]","").replace("\"","").split(","));
			if(!str.equals("") && !str.equals("None or Already enabled")){
				String[] str2 = str.split("_");
				if(!checkStr.contains(str2[0])){
					checkStr += str2[0]+"\n";
				}
			}
		}
		if(!checkStr.equals("") && !checkStr.equals("None or Already enabled")){
			canLaunch=false;
			con.log("Severe","Launch failure with.. " + checkStr);
			if(!ignore)
			{JOptionPane.showMessageDialog(null, "You must enable the following mods before this profile can be launched\n" + checkStr);}
			lblRequiredMods.setText("Required Mods: " + checkStr.replace("\\n",", "));
			btnLaunchIgnore.setVisible(true);
		}
		return canLaunch;
	}

	@SuppressWarnings("unused")
	private void runGame(String string) {
		try {
			con.log("Log","Launched game at... " + string);
			Process pFactorio = Runtime.getRuntime().exec(string);
		} catch (IOException e) {
			con.log("Severe","Failed to launch game at " + string);
		}
	}
}

