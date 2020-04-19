package ohjelmaSwing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.TableModel;

/**
 * Luokka joka hallitsee kaudet ja joukkueet luokkia
 * @author Ville
 * @version 24.2.2020 
 */
public class Tulosrekisteri {
	
	private Kaudet kaudet;
	private Joukkueet joukkueet;
	private int valittuId = -1;
	
	/**
	 * Alustetaan Joukkueet ja Kausitiedot
	 * @throws IOException virheviesti jos alustuksessa jotain ongelmia
	 * @param tiedostonNimiJoukkue
	 * @param tiedostonNimiKaudet
	 */
	public Tulosrekisteri(String tiedostonNimiJoukkue, String tiedostonNimiKaudet) throws IOException {
		joukkueet = new Joukkueet();
		joukkueet.alusta(tiedostonNimiJoukkue);
		kaudet = new Kaudet();
		kaudet.alusta(tiedostonNimiKaudet);		
	}
	
	/**
	 * Lisää uuden joukkueen
	 * @throws IOException Jos rekisteri on täynnä niin tulee virheviesti
	 */
	public void uusiJoukkue() throws IOException {
		joukkueet.uusiJoukkue();
	}
	
	/**
	 * Palauttaa joukkueet taulukkona
	 * @return joukkueet Joukkue[] taulukossa
	 */
	public Joukkue[] palautaJoukkueet() {
		return joukkueet.palautaTaulukko();
	}
	
	/**
	 * Palauttaa joukkueet filttereillä, ylösalaisin
	 * @return 
	 * @param ehto1 1=joukkueen tai 2=managerin perusteella
	 * @param ehto2 hakuteksti
	 * @return filtteröidyt joukkueet String[] taulukossa
	 */
	public String[] palautaJoukkueet(int ehto1, String ehto2) {
		int a = joukkueet.maaraJoukkueet();
		int b = 0;
		// Luodaan uusi taulukko, lisätään taulukkoon vain jos täyttää ehdot
		String[] lista = new String[a]; 
		for(Joukkue joukkue: joukkueet.palautaTaulukko() ) {
			if (joukkue == null) {
				continue;
			}
			if(ehto1 == 1) {
				if (joukkue.getNimi().toLowerCase().contains(ehto2.toLowerCase()) || ehto2.equals("")) {
					lista[a-1] = joukkue.getNimi() + " #" + joukkue.getId();
					a--;
					b++;
				}
				
			} else if(ehto1 == 2) {
				if (joukkue.getManageri().toLowerCase().contains(ehto2.toLowerCase()) || ehto2.equals(""))  {
					lista[a-1] = joukkue.getManageri() + " #" + joukkue.getId();
					a--;
					b++;
				}	
			}			
		} 
		// Tehdään vielä yksi uusi taulukko ilman tyhjiä paikkoja
		String[] listaIlmanTyhjia = new String[b];
		int c = 0;
		for(String rivi: lista) {
			if(rivi != null) {
				listaIlmanTyhjia[c] = rivi;
				c++;
			}
		}		
		return listaIlmanTyhjia;
	}
		
	/**
	 * Palauttaa yhden joukkueen hakuehtojen mukaisesti
	 * @param ehto1 1=joukkueen tai 2=managerin perusteella
	 * @param ehto2 hakuteksti
	 * @return Joukkueen tiedot String[] taulukossa
	 */
	public String[] palautaJoukkue(int ehto1, String ehto2) throws NumberFormatException{
		String[] taulukko = null;
		
		StringBuilder klikattu = new StringBuilder(ehto2);
		erota(klikattu, '#', false);
		int id = Integer.parseInt(klikattu.toString());
		
		for(Joukkue joukkue: joukkueet.palautaTaulukko() ) {
			if (joukkue == null) {
				continue;
			}
			if (joukkue.getId() == id) {
				taulukko = joukkue.palautaTaulukko();
				valittuId = id;
				break;
			}					
		} 	
		return taulukko;
	}

	/**
	 * Poistaa halutun joukkueen ja kaudet valitun kauden id:n perusteella
	 */
	public void poistaJoukkue() {
		joukkueet.poistaJoukkue(valittuId);
		kaudet.poistaKaudet(valittuId);
	}
	
	/**
	 * Metodi joka palauttaa String taulukkona HallOfFame joukkueet
	 * @return String[] HallOfFame joukkueet
	 */
	public String[] palautaHallofFame () {		
		int[][] aputaulukko = new int[joukkueet.maaraJoukkueet()][2];
		
		// Lisätään aputaulukkoon joukkueiden id ja mestaruuksien määrä
		for (int i=0; i < joukkueet.maaraJoukkueet(); i++) {			
			aputaulukko[i][0] = 0;
			aputaulukko[i][1] = 0;
			if (joukkueet.palautaTaulukko()[i] != null) {					
				int id = joukkueet.palautaTaulukko()[i].getId();
				aputaulukko[i][0] = id;
				for(Kausi kausi: kaudet.mestaruudet()) {					
					if(kausi.getId()==id) {
						aputaulukko[i][1]++;
					}
				}
			}			
		}		
		int mestaruudet = 0;
		
		//Lasketaan paljonko mestaruuksia on yhteensä
		for(int i = 0; i < aputaulukko.length; i++) {
			if (aputaulukko[i][1] > 0) {
				mestaruudet++;
			}
		}
		
		// Lisätään arraylistaan kaikki joukkueet joilla mestaruus
		ArrayList<String> lista = new ArrayList<>();			
		for(int i = 0; i < aputaulukko.length; i++) {
			if (aputaulukko[i][1] > 0) {
				lista.add(aputaulukko[i][1] + " * " + joukkueet.getNimi(aputaulukko[i][0]));
				
			}
		}		
		// Sortataan vielä lista
		Collections.sort(lista, Collections.reverseOrder());
		
		// Lisätään lista takaisin taulukkoon. Pieni kikkailu nyt tuli taulukoiden kanssa mutta toimii
		String[] hallOfFame = new String[mestaruudet];
		for(int i = 0; i < mestaruudet; i++) {
			hallOfFame[i] = lista.get(i);
		}	
		return hallOfFame;	
	}
	
    /**
     * Erottaa jonosta ensimmäisen sanan
     * @param jono eli rivi tekstiä
     * @param merkki millä erotetaan sanat
     * @param etsitakaperin, aina false eli käydään läpi alusta loppuun päin
     * @return erotettu jonon osa
     */
    public static String erota(StringBuilder jono, char merkki, boolean etsitakaperin) {
        int p;
        if (!etsitakaperin) 
            p = jono.indexOf("" + merkki); // NOPMD
        else
            p = jono.lastIndexOf("" + merkki);
        String alku;
        if (p < 0) { // oli alunperin <= 
        	alku = jono.toString();
            jono.delete(0, jono.length());
            return alku;
        }
        alku = jono.substring(0, p);
        jono.delete(0, p + 1);
        return alku;
    }
    
    /**
     * tarkistaa löytyykö joukkuetta 
     * @return true tai false
     */
	public boolean tarkistaId() {
		return joukkueet.tarkistaId(valittuId);
	}
	
	/**
	 * tarkistaa löytyykö joukkuetta tietyllä id:llä
	 * @param idInt
	 * @return true tai false
	 */
	public boolean tarkistaId(int idInt) {
		return joukkueet.tarkistaId(idInt);	
	}
	
	/**
	 * Päivitetään joukkueen tiedot kenttiin
	 * @param text Joukkueen nimi
	 * @param text2 Managerin nimi
	 * @param text3 Puhelinnumero
	 * @param text4 Liittymisvuosi
	 * @param text5 Slogan
	 * @throws IOException jos herjoja
	 */
	public void paivitaJoukkue(String text, String text2, String text3, String text4, String text5) throws IOException {
		String [] taulukko = new String []{"" + valittuId, text, text2, text3, text4, text5};
		joukkueet.paivitaJoukkue(taulukko);
		
	}
	/**
	 * Palauttaa valitun joukkueen kaudet
	 * @return kausien määrä
	 */
	public int joukkueenKaudet() {
		return kaudet.joukkueenKaudet(valittuId);
	}
	
	/**
	 * Palauttaa viitteen listaan jossa vain valitun joukkueen kaudet 
	 * @return ArrayList<Kausi> 
	 */
	public ArrayList<Kausi> palautaKaudet() {
		ArrayList<Kausi> sortatut = new ArrayList<>();
		for(Kausi kausi: kaudet.palautaTaulukko()) {
			if(kausi.getId() == valittuId) {
				sortatut.add(kausi);
			}
		}		
		return sortatut;
	}
	
	/**
	 * Palauttaa valitun joukkueen id:n
	 * @return id
	 */
	public int palautaId() {
		return valittuId;
	}
	
	/**
	 * Lisätään uusi kausi valitulle joukkueelle
	 */
	public void lisaaKausi() {
		if(tarkistaId()) {
			kaudet.uusiKausi(valittuId);
		}		
	}
	
	/**
	 * Poistaa valitun kauden, kaikki jos monta samaa
	 * @param rivi
	 */
	public void poistaKausi(int rivi) {
		
		if(tarkistaId()) { // Tää tarkistus turha kun tarkistetaan jo ylempänä mutta olkoon
			while(true) {
				boolean lopetetaan = true;
				for(Kausi kausi: kaudet.palautaTaulukko()) {
					if(kausi.getId() == valittuId) {
						if (kausi.getVuosi() == rivi) {
							kaudet.palautaTaulukko().remove(kausi);
							lopetetaan = false;
							break;
						}
					}
				}
				if(lopetetaan) { // Jos ei enää poistettavia kausia
					break;
				}
			}			
		}		
	}	
	/**
	 * Päivittää joukkueen kausitiedot
	 * @param TableModel model viite
	 */
	public void paivitaKaudet(TableModel model) {		
		int i = 0;
		for(Kausi kausi: kaudet.palautaTaulukko()) {
			if(kausi.getId() == valittuId) {
				Integer[] taulukko = new Integer[5];							
				taulukko[0] = Integer.parseInt(model.getValueAt(i, 0).toString());						
				taulukko[1] = Integer.parseInt(model.getValueAt(i, 1).toString());	
				taulukko[2] = Integer.parseInt(model.getValueAt(i, 2).toString());	
				taulukko[3] = Integer.parseInt(model.getValueAt(i, 3).toString());	
				taulukko[4] = Integer.parseInt(model.getValueAt(i, 4).toString());									
				kausi.aseta(taulukko);
				i++;
			}
		}			
	}
	
	/**
	 * Tallennetaan muutokset tietokantaan
	 * @param tiedosto1 joukkueet tietokanta
	 * @param tiedosto2 kaudet tietokanta
	 * @throws IOException
	 */
	public void talleta(String tiedosto1, String tiedosto2) throws IOException{	
		joukkueet.talleta(tiedosto1);
		kaudet.talleta(tiedosto2);	
	}
	
}
