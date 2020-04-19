package ohjelmaTaulut;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import fi.jyu.mit.gui.ListChooser;
import ohjelmaSwing.OhjelmaSwing;
import javax.swing.JTable;
import fi.jyu.mit.gui.EditPanel;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PaaTaulu extends JFrame {

	/**
	 * 	GUI-Luokka
	 *  @version 24.2.2020
	 *  @author villeh
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField hakuKentta;
	private DefaultTableModel tableKausitiedot;
	private JTable jTableKausitiedot;	
	private OhjelmaSwing ohjelmaSwing;
	private ListChooser listHallOfFame;
	private JComboBox<String> comboBoxValikko;
	private ListChooser listJoukkueet;
	private EditPanel editPanelJoukkue;
	private EditPanel editPanelNimi;
	private EditPanel editPanelLiittymisVuosi;
	private EditPanel editPanelPuhelin;
	private EditPanel editPanelSlogan;
	private Integer dataKaudet[][];
	private JScrollPane scrollKausitiedot;
	
	private final Action actionLisaaJoukkue = new actionLisaaJoukkue();
	private final Action actionPoistaJoukkue = new actionPoistaJoukkue();
	private final Action actionLisaaKausi = new actionLisaaKausi();
	private final Action actionPoistaKausi = new actionPoistaKausi();
	private final Action actionTallennaMuutokset = new actionTallennaMuutokset();
	private final Action actionNaytaOhjeet = new actionNaytaOhjeet();
	private final Action actionNaytaTietoja = new actionNaytaTietoja();
	private final Action actionSuljeOhjelma = new actionSuljeOhjelma();
	private final Action actionNhl = new actionNhl();
	

	/**
	 * Launch the whole application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					int dialogResult = JOptionPane.showConfirmDialog (null, "Tervetuloa NHL Fantasy Liigan viralliseen tulosrekisteriin.\nTulosrekisteri on pyhä, joten sitä pitää kunnioittaa.\nOletko luottamuksen arvoinen?","Bienvenido!",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION){
						PaaTaulu frame = new PaaTaulu();
						frame.setVisible(true);	
					}													
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}									 
		});
	}	
	
	/**
	 * Create the frame and objects
	 */
	public PaaTaulu() {		
		//Muodostetaan ohjelmaswing 
		ohjelmaSwing = new OhjelmaSwing();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitIso = new JSplitPane();
		splitIso.setBackground((Color) null);
		contentPane.add(splitIso, BorderLayout.CENTER);
		
		JSplitPane splitOikea = new JSplitPane();
		splitIso.setRightComponent(splitOikea);
		splitOikea.setBackground((Color) null);
		
		JPanel panelJoukkuetiedot = new JPanel();
		panelJoukkuetiedot.setMinimumSize(new Dimension(250, 10));
		splitOikea.setLeftComponent(panelJoukkuetiedot);
		panelJoukkuetiedot.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOtsikko3 = new JPanel();
		panelJoukkuetiedot.add(panelOtsikko3, BorderLayout.NORTH);
		
		JLabel lblJoukkuetiedot = new JLabel("JOUKKUETIEDOT");
		panelOtsikko3.add(lblJoukkuetiedot);
		
		JPanel panelJoukkueKentat = new JPanel();
		panelJoukkuetiedot.add(panelJoukkueKentat, BorderLayout.CENTER);
		panelJoukkueKentat.setLayout(new BoxLayout(panelJoukkueKentat, BoxLayout.Y_AXIS));
		
		editPanelJoukkue = new EditPanel();
		editPanelJoukkue.getEdit().setText("Valitse");	// muuttuva
		editPanelJoukkue.getLabel().setMinimumSize(new Dimension(20, 16));
		editPanelJoukkue.getLabel().setHorizontalAlignment(SwingConstants.RIGHT);
		editPanelJoukkue.setMaximumSize(new Dimension(2147483647, 20));
		editPanelJoukkue.getLabel().setText("Joukkue");
		panelJoukkueKentat.add(editPanelJoukkue);
		
		editPanelNimi = new EditPanel();
		editPanelNimi.getEdit().setText("vasemmalta"); // muuttuva
		editPanelNimi.getLabel().setMinimumSize(new Dimension(20, 16));
		editPanelNimi.getLabel().setHorizontalAlignment(SwingConstants.RIGHT);
		editPanelNimi.setMaximumSize(new Dimension(2147483647, 20));
		editPanelNimi.getLabel().setText("Manageri");
		panelJoukkueKentat.add(editPanelNimi);
		
		editPanelLiittymisVuosi = new EditPanel();
		editPanelLiittymisVuosi.getEdit().setText("haluamasi");	// muuttuva
		editPanelLiittymisVuosi.getLabel().setMinimumSize(new Dimension(20, 16));
		editPanelLiittymisVuosi.getLabel().setHorizontalAlignment(SwingConstants.RIGHT);
		editPanelLiittymisVuosi.setMaximumSize(new Dimension(2147483647, 20));
		editPanelLiittymisVuosi.getLabel().setText("Puhelin");
		panelJoukkueKentat.add(editPanelLiittymisVuosi);
		
		editPanelPuhelin = new EditPanel();
		editPanelPuhelin.getEdit().setText("joukkue");	// muuttuva
		editPanelPuhelin.getLabel().setMinimumSize(new Dimension(20, 16));
		editPanelPuhelin.getLabel().setHorizontalAlignment(SwingConstants.RIGHT);
		editPanelPuhelin.setMaximumSize(new Dimension(2147483647, 20));
		editPanelPuhelin.getLabel().setText("Liittynyt");
		panelJoukkueKentat.add(editPanelPuhelin);
		
		editPanelSlogan = new EditPanel();
		editPanelSlogan.getEdit().setText("Kiitos!");	// muuttuva
		editPanelSlogan.getLabel().setMinimumSize(new Dimension(20, 16));
		editPanelSlogan.getLabel().setHorizontalAlignment(SwingConstants.RIGHT);
		editPanelSlogan.setMaximumSize(new Dimension(2147483647, 20));
		editPanelSlogan.getLabel().setText("Slogan");
		panelJoukkueKentat.add(editPanelSlogan);
		
		JPanel panelJoukkueNappulat = new JPanel();
		panelJoukkuetiedot.add(panelJoukkueNappulat, BorderLayout.SOUTH);
		panelJoukkueNappulat.setLayout(new BoxLayout(panelJoukkueNappulat, BoxLayout.X_AXIS));
		
		JButton btnLisaaJoukkue = new JButton("Lisää joukkue");
		btnLisaaJoukkue.setAction(actionLisaaJoukkue);
		panelJoukkueNappulat.add(btnLisaaJoukkue);
		
		JButton btnPoistaJoukkue = new JButton("Poista joukkue");
		btnPoistaJoukkue.setAction(actionPoistaJoukkue);
		panelJoukkueNappulat.add(btnPoistaJoukkue);
		
		JPanel panelKausitiedot = new JPanel();
		panelKausitiedot.setMinimumSize(new Dimension(250, 10));
		splitOikea.setRightComponent(panelKausitiedot);
		panelKausitiedot.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOtsikko4 = new JPanel();
		panelKausitiedot.add(panelOtsikko4, BorderLayout.NORTH);
		
		JLabel lblKausitedot = new JLabel("KAUSITEDOT");
		panelOtsikko4.add(lblKausitedot);
		
		scrollKausitiedot = new JScrollPane();
		panelKausitiedot.add(scrollKausitiedot, BorderLayout.CENTER);
		
	    dataKaudet = new Integer [10][5];
	    String column[]={"ID", "Kausi","Sijoitus","Pisteet","Varausvuoro"}; 
		tableKausitiedot = new DefaultTableModel(dataKaudet, column);
		jTableKausitiedot = new JTable(tableKausitiedot);
		scrollKausitiedot.setViewportView(jTableKausitiedot);
		jTableKausitiedot.setAutoCreateRowSorter(true);
		
		// Kerrotaan Swingille tarpeelliset viitteet
		ohjelmaSwing.setLinks(editPanelJoukkue, editPanelNimi, editPanelLiittymisVuosi, editPanelPuhelin, editPanelSlogan, jTableKausitiedot);						
		
		JPanel panelKausiNappulat = new JPanel();
		panelKausitiedot.add(panelKausiNappulat, BorderLayout.SOUTH);
		panelKausiNappulat.setLayout(new BoxLayout(panelKausiNappulat, BoxLayout.X_AXIS));
		
		JButton btnLisaaKausi = new JButton("Lisää kausi");
		btnLisaaKausi.setAction(actionLisaaKausi);	
		
		panelKausiNappulat.add(btnLisaaKausi);
		
		JButton btnPoistaKausi = new JButton("Poista kausi");
		btnPoistaKausi.setAction(actionPoistaKausi);
		panelKausiNappulat.add(btnPoistaKausi);
		
		JSplitPane splitVasen = new JSplitPane();
		splitIso.setLeftComponent(splitVasen);
		
		JPanel panelHallOfFame = new JPanel();
		panelHallOfFame.setMinimumSize(new Dimension(150, 10));
		splitVasen.setLeftComponent(panelHallOfFame);
		panelHallOfFame.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOtsikko1 = new JPanel();
		panelHallOfFame.add(panelOtsikko1, BorderLayout.NORTH);
		
		JLabel lblHallOfFame = new JLabel("HALL OF FAME");
		panelOtsikko1.add(lblHallOfFame);		
		
		listHallOfFame = new ListChooser();		
		ohjelmaSwing.setHallOfFame(listHallOfFame);
		listHallOfFame.setCaption("");
		panelHallOfFame.add(listHallOfFame, BorderLayout.CENTER);
		
		JPanel panelHakukentta = new JPanel();
		panelHakukentta.setMinimumSize(new Dimension(150, 10));
		splitVasen.setRightComponent(panelHakukentta);
		panelHakukentta.setLayout(new BorderLayout(0, 0));
		
		JPanel panelKentta = new JPanel();
		panelHakukentta.add(panelKentta, BorderLayout.NORTH);
		panelKentta.setLayout(new BoxLayout(panelKentta, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("HAKUKENTTÄ");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelKentta.add(lblNewLabel);
		
		comboBoxValikko = new JComboBox<String>();	
		comboBoxValikko.addItem("Joukkueen nimi");
		comboBoxValikko.addItem("Managerin nimi");
		
		comboBoxValikko.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ohjelmaSwing.setListJoukkueet(listJoukkueet, comboBoxValikko, hakuKentta); // Jos tila vaihtunut
			}
		});
		
		panelKentta.add(comboBoxValikko);
		
		
		hakuKentta = new JTextField();
		hakuKentta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				ohjelmaSwing.setListJoukkueet(listJoukkueet, comboBoxValikko, hakuKentta); // Jos kenttään syötetty tekstiä
			}
		});
		panelKentta.add(hakuKentta);
		hakuKentta.setColumns(10);
		
		JLabel lblJoukkueet = new JLabel("Joukkue #id");
		lblJoukkueet.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelKentta.add(lblJoukkueet);
			
		listJoukkueet = new ListChooser();
		listJoukkueet.getList().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				ohjelmaSwing.setJoukkuetiedot(listJoukkueet, comboBoxValikko);
				ohjelmaSwing.setKausiTiedot(jTableKausitiedot);
				
			}
		});
		listJoukkueet.getList().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ohjelmaSwing.setJoukkuetiedot(listJoukkueet, comboBoxValikko);
				ohjelmaSwing.setKausiTiedot(jTableKausitiedot);
				
			}
		});

		ohjelmaSwing.setListJoukkueet(listJoukkueet, comboBoxValikko, hakuKentta);
		listJoukkueet.setCaption("");
		panelHakukentta.add(listJoukkueet, BorderLayout.CENTER);
		
		
		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		JMenu mnTiedosto = new JMenu("Tiedosto");
		menuBar.add(mnTiedosto);
		
		JMenuItem mntmTallennaMuutokset = new JMenuItem("Tallenna muutokset");
		mntmTallennaMuutokset.setAction(actionTallennaMuutokset);
		mnTiedosto.add(mntmTallennaMuutokset);
		
		JMenuItem mntmSuljeOhjelma = new JMenuItem("Sulje ohjelma");
		mntmSuljeOhjelma.setAction(actionSuljeOhjelma);
		mnTiedosto.add(mntmSuljeOhjelma);
		
		JMenu mnMuokkaa = new JMenu("Muokkaa");
		menuBar.add(mnMuokkaa);
		
		JMenuItem mntmLisaaJoukkue = new JMenuItem("Lisää joukkue");
		mntmLisaaJoukkue.setAction(actionLisaaJoukkue);
		mnMuokkaa.add(mntmLisaaJoukkue);
		
		JMenuItem mntmLisaaKausi = new JMenuItem("Lisää kausi");
		mntmLisaaKausi.setAction(actionLisaaKausi);
		mnMuokkaa.add(mntmLisaaKausi);
		
		JMenuItem mntmPoistaJoukkue = new JMenuItem("Poista joukkue");
		mntmPoistaJoukkue.setAction(actionPoistaJoukkue);
		mnMuokkaa.add(mntmPoistaJoukkue);
		
		JMenuItem mntmPoistaKausi = new JMenuItem("Poista kausi");
		mntmPoistaKausi.setAction(actionPoistaKausi);
		mnMuokkaa.add(mntmPoistaKausi);
		
		JMenu mnApua = new JMenu("Apua!");
		menuBar.add(mnApua);
		
		JMenuItem mntmOhjeita = new JMenuItem("Ohjeita");
		mntmOhjeita.setAction(actionNaytaTietoja);
		mnApua.add(mntmOhjeita);
		
		JMenuItem mntmTietojaOhjelmasta = new JMenuItem("Tietoja ohjelmasta");
		mntmTietojaOhjelmasta.setAction(actionNaytaOhjeet);
		mnApua.add(mntmTietojaOhjelmasta);
		
		JMenuItem mntmNhlcom = new JMenuItem("NHL.com");
		mntmNhlcom.setAction(actionNhl);
		mnApua.add(mntmNhlcom);
		
		JPanel panelTallenna = new JPanel();
		contentPane.add(panelTallenna, BorderLayout.SOUTH);
		panelTallenna.setLayout(new BoxLayout(panelTallenna, BoxLayout.X_AXIS));
		
		JButton btnTallennaMuutokset = new JButton("Tallenna muutokset");
		btnTallennaMuutokset.setAction(actionTallennaMuutokset);
		btnTallennaMuutokset.setHorizontalAlignment(SwingConstants.RIGHT);
		panelTallenna.add(btnTallennaMuutokset);
		
	
	}
	
	// Alla Swingin action luokat. Ei tarvetta kommentoida niitä
	private class actionLisaaJoukkue extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public actionLisaaJoukkue() {
			putValue(NAME, "Lisaa joukkue");
			putValue(SHORT_DESCRIPTION, "Lisää uuden tyhjän joukkueen");
		}
		public void actionPerformed(ActionEvent e) {
			ohjelmaSwing.lisaaJoukkue();
			ohjelmaSwing.setListJoukkueet(listJoukkueet, comboBoxValikko, hakuKentta);	
		}
	}
	private class actionPoistaJoukkue extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public actionPoistaJoukkue() {
			putValue(NAME, "Poista joukkue");
			putValue(SHORT_DESCRIPTION, "Poistaa valitun joukkueen kaikki tiedot");
		}
		public void actionPerformed(ActionEvent e) {
			ohjelmaSwing.poistaJoukkue();
			ohjelmaSwing.setListJoukkueet(listJoukkueet, comboBoxValikko, hakuKentta);
			ohjelmaSwing.setKausiTiedot(jTableKausitiedot);
			ohjelmaSwing.setHallOfFame(listHallOfFame);
		}
	}
	private class actionLisaaKausi extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public actionLisaaKausi() {
			putValue(NAME, "Lisää kausi");
			putValue(SHORT_DESCRIPTION, "Lisää uuden tyhjän kauden");
		}
		public void actionPerformed(ActionEvent e) {
			ohjelmaSwing.lisaaKausi();
			ohjelmaSwing.setKausiTiedot(jTableKausitiedot);
		}
	}
	private class actionPoistaKausi extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public actionPoistaKausi() {
			putValue(NAME, "Poista kausi");
			putValue(SHORT_DESCRIPTION, "Poistaa valitun kauden tiedot");
		}
		public void actionPerformed(ActionEvent e) {
			ohjelmaSwing.poistaKausi(jTableKausitiedot);
			ohjelmaSwing.setKausiTiedot(jTableKausitiedot);
			ohjelmaSwing.setHallOfFame(listHallOfFame);
		}
	}
	private class actionTallennaMuutokset extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public actionTallennaMuutokset() {
			putValue(NAME, "Tallenna muutokset");
			putValue(SHORT_DESCRIPTION, "Tallentaa muutokset");
		}
		public void actionPerformed(ActionEvent e) {
			ohjelmaSwing.talleta();
			ohjelmaSwing.setHallOfFame(listHallOfFame);
		}
	}
	private class actionNaytaOhjeet extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public actionNaytaOhjeet() {
			putValue(NAME, "Ohjeita");
			putValue(SHORT_DESCRIPTION, "Lyhyt käyttöohje");
		}
		public void actionPerformed(ActionEvent e) {
			ohjelmaSwing.ohjeita();	
		}
	}
	private class actionNaytaTietoja extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public actionNaytaTietoja() {
			putValue(NAME, "Tietoja");
			putValue(SHORT_DESCRIPTION, "Näyttää ohjelman tiedot");
		}
		public void actionPerformed(ActionEvent e) {
			ohjelmaSwing.tietoja();
		}
	}
	private class actionSuljeOhjelma extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public actionSuljeOhjelma() {
			putValue(NAME, "Sulje ohjelma");
			putValue(SHORT_DESCRIPTION, "Sulkee ohjelman tallentamatta muutoksia");
		}
		public void actionPerformed(ActionEvent e) {

			if(ohjelmaSwing.lopeta()) {
				dispose();
			}
		}
	}
	private class actionNhl extends AbstractAction {


		private static final long serialVersionUID = 1L;
		public actionNhl() {
			putValue(NAME, "NHL.com");
			putValue(SHORT_DESCRIPTION, "Suora linkki NHL.com sivustolle");
		}
		public void actionPerformed(ActionEvent e) {
			ohjelmaSwing.nhl();
		}
	}
}
