package ohjelmaSwing;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import fi.jyu.mit.gui.EditPanel;
import fi.jyu.mit.gui.ListChooser;
import ohjelmaTaulut.TietojaTaulu;

/**
 * Swing luokka joka pitää huolen GUI:sta ja juoksuttaa tulostekisteriä
 * @author Ville
 */

public class OhjelmaSwing {
	private Tulosrekisteri rekisteri;
	private EditPanel PanelJoukkue;
	private EditPanel PanelNimi;
	private EditPanel PanelLiittymisVuosi;
	private EditPanel PanelPuhelin;
	private EditPanel PanelSlogan;
	private JTable jTableKausitiedot;
	
	/**
	 * Muodostaja luokka
	 */
	public OhjelmaSwing() {
		try {
			rekisteri = new Tulosrekisteri("joukkueet.DAT", "kaudet.DAT");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Asettaa muutamia viitteitä niin ei tarvi GUIn joka kutsun yhteydessä pitkää listaa lähettää
	 * @param PanelJoukkue
	 * @param PanelNimi
	 * @param PanelLiittymisVuosi
	 * @param PanelPuhelin
	 * @param PanelSlogan
	 */
	public void setLinks(EditPanel PanelJoukkue,EditPanel PanelNimi, EditPanel PanelLiittymisVuosi, EditPanel PanelPuhelin, EditPanel PanelSlogan, JTable jTableKausitiedot) {
		this.PanelJoukkue = PanelJoukkue;
		this.PanelNimi = PanelNimi;
		this.PanelLiittymisVuosi = PanelLiittymisVuosi;
		this.PanelPuhelin = PanelPuhelin;
		this.PanelSlogan = PanelSlogan;
		this.jTableKausitiedot = jTableKausitiedot;
	}
	
	/**
	 * Asettaa Hall of fame joukkueet annettuun Listchooseriin
	 * @param listHallOfFame viite
	 */
	public void setHallOfFame(ListChooser listHallOfFame) {		
		listHallOfFame.setKohteet(rekisteri.palautaHallofFame());
	}
	

	/**
	 * Asettaa listchooseriin oikeat joukkueet
	 * @param listJoukkueet
	 * @param comboBoxValikko
	 * @param hakuKentta
	 */
	public void setListJoukkueet(ListChooser listJoukkueet, JComboBox<String> comboBoxValikko, JTextField hakuKentta) {
		int ehto1 = 1;
		String valinta = comboBoxValikko.getSelectedItem().toString().toLowerCase();
		if (valinta.contains("manageri")) {
			ehto1 = 2;
		}
		listJoukkueet.setKohteet(rekisteri.palautaJoukkueet(ehto1, hakuKentta.getText()));
		
	}
	
	/**
	 * Asettaa joukkueen tiedot kenttiin
	 * @param listJoukkueet
	 * @param comboBoxValikko
	 */
	public void setJoukkuetiedot(ListChooser listJoukkueet, JComboBox<String> comboBoxValikko) {		 
		
		String alasvetoValikko = comboBoxValikko.getSelectedItem().toString().toLowerCase(); //combobox
		String klikattu = listJoukkueet.getSelectedText(); // hakuteksti		
		
		int ehto1 = 1;
		if (alasvetoValikko.contains("manageri")) { // tiedetään, ei mikään kovin kestävä ratkaisu
			ehto1 = 2;
		}	
		try {
			String[] joukkue = rekisteri.palautaJoukkue(ehto1, klikattu);
			if(joukkue == null) { // Ei pitäisi olla mahdollista
				JOptionPane.showMessageDialog(null, "Hakuehdoilla ei löydetä joukkuetta"); 
				return;
			}			
			PanelJoukkue.setText(joukkue[1]);
			PanelNimi.setText(joukkue[2]);
			PanelLiittymisVuosi.setText(joukkue[3]);
			PanelPuhelin.setText(joukkue[4]);
			PanelSlogan.setText(joukkue[5]);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Lista tyhjä, ei joukkueita");
		}		
	}
	
	/**
	 * Pävittää tiedot olioihin ja lopulta tallentaa tietokantaan
	 */
	public void talleta() {		
			try {
				if(rekisteri.tarkistaId()) {
					rekisteri.paivitaJoukkue(PanelJoukkue.getText(), PanelNimi.getText(), PanelLiittymisVuosi.getText(), PanelPuhelin.getText(), PanelSlogan.getText());				
					if(tarkistaTaulukko()) {
						rekisteri.paivitaKaudet(jTableKausitiedot.getModel());				
						JOptionPane.showMessageDialog(null, "Muutokset joukkueen tietoihin tallennettu");
					} else {
						JOptionPane.showMessageDialog(null, "Muutokset joukkuetietoihin tallennettu mutta kausitietoja ei\n  -> Korjaa kausitiedot (vain tekstiä ja oikeita ID:tä)");
					}
				}
				rekisteri.talleta("joukkueet.DAT", "kaudet.DAT");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Tietokantaan tallentaminen epäonnistui");
			}		 			
	}
	
	/**
	 * Tarkistetaan, että kausitaulukossa vain numeroita ja olemassa olevia ID-numeroita. Jos ei niin tulee herja
	 * @return true tai false
	 */
	private boolean tarkistaTaulukko() {
		DefaultTableModel model = (DefaultTableModel) jTableKausitiedot.getModel();
		int luku = model.getRowCount();
		for (int a = 0; a < luku; a++) {
			for (int b = 0; b < 5; b++) {
				try {
					// Joudutaan tekemään pieni object/String/int kikkailu kun ei muuten toiminut
					Object solu = model.getValueAt(a, b);
					String tekstina = solu.toString().trim();					
					@SuppressWarnings("unused")
					int vuosi = Integer.parseInt(tekstina);	
			
					// Tarkistetaan vielä, että myös id olemassa. Ettei tietokantaan pysty tallentamaan haamukausi, jotka ei näy ohjelmaas
					Object idObject = model.getValueAt(a, 0); 
					String idTekstina = idObject.toString().trim();
					int idInt = Integer.parseInt(idTekstina);						
					if(!rekisteri.tarkistaId(idInt)) {
						return false;
					}
				} catch (NumberFormatException e) {
					return false;
				}			
			}
		}
		return true;
	}
	
	/**
	 * Lopetetaan ohjelma dialogi
	 * @return true tai false
	 */
	public boolean lopeta() {
		int dialogResult = JOptionPane.showConfirmDialog (null, "Olethan tallentanut muutokset?","Huomio!",JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			return true;
		} else {
			return false;
		}
			
	}
	
	/**
	 * Avaa tietoja-taulun
	 */
	public void tietoja() {
		TietojaTaulu tietoTaulu = new TietojaTaulu();
		tietoTaulu.setVisible(true);
	}
	
	/**
	 * Lisätään uusi joukkue
	 */
	public void lisaaJoukkue() {		
		try {
			rekisteri.uusiJoukkue();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}		
	}
	
	/**
	 * Poistetaan valittu joukkue. Jos valittuna tyhjä/poistettu joukkuen niin ei tehdä mitään
	 */
	public void poistaJoukkue() {				
		if(rekisteri.tarkistaId()) {
			int dialogResult = JOptionPane.showConfirmDialog (null, "Haluatko varmasti poistaa valitun joukkueen?","Huomio!",JOptionPane.YES_NO_OPTION);
			if(dialogResult == JOptionPane.YES_OPTION){
				rekisteri.poistaJoukkue();
				tyhjennaJoukkueTiedot();
			}
		}			
	}
	
	/**
	 * Lisätään uusi kausi
	 */
	public void lisaaKausi() {
		rekisteri.lisaaKausi();		
	}
	
	/**
	 * Poistetaan kausi
	 * @param jTableKausitiedot viite
	 */
	public void poistaKausi(JTable jTableKausitiedot) {					
		if(rekisteri.tarkistaId()) { // Jos kautta ei olemassa niin ei tehdä mitään
			int rivi = jTableKausitiedot.getSelectedRow();
			if (rivi >= 0) {				
				try {
					Object solu = jTableKausitiedot.getModel().getValueAt(rivi, 1);
					String tekstina = solu.toString().trim();
					int vuosi = Integer.parseInt(tekstina);
					int dialogResult = JOptionPane.showConfirmDialog (null, "Haluatko varmasti poistaa kauden " + vuosi +"?","Huomio!",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION){
						rekisteri.poistaKausi(vuosi);
					}						
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Valitun kauden vuosiluku ei ole asianmukainen");
				}			
			}
		}
	}
	
	/**
	 * Avaa ohjeita tiedoston selaimessa
	 */
	public void ohjeita() {
		try {
			File file = new File("res/ohjeita.html");
			URI uri = file.toURI();
			uri.normalize();
			Desktop.getDesktop().browse(uri);			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Avaa nhl.com sivun selaimessa
	 */
	public void nhl() {
		try {
			URI uri = new URI("https://www.nhl.com/");
			uri.normalize();
			Desktop.getDesktop().browse(uri);			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		} catch (URISyntaxException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	/**
	 * Asettaa oletustekstit joukkuekenttiin
	 */
	public void tyhjennaJoukkueTiedot() {
		PanelJoukkue.setText("Valitse vasemmalta");
		PanelNimi.setText("joku joukkue");
		PanelLiittymisVuosi.setText("tai lisää");
		PanelPuhelin.setText("uusi joukkue");
		PanelSlogan.setText("Kiitos!");
	}
	
	/**
	 * Päivittää GUI:hin kausitiedot
	 * @param jTableKausitiedot
	 */
	public void setKausiTiedot(JTable jTableKausitiedot) {
		DefaultTableModel tableModel = (DefaultTableModel) jTableKausitiedot.getModel();
		tableModel.setRowCount(0);
		ArrayList<Kausi> kaudet = rekisteri.palautaKaudet();
		int koko = kaudet.size();
		if(koko != 0) {
			for(Kausi kausi: kaudet) {
				tableModel.addRow(kausi.palautaTaulukko());		
			}						
		jTableKausitiedot.setModel(tableModel);
		tableModel.fireTableDataChanged();		
		
		}
		
		
	}


}
