package ohjelmaSwing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Luokka jossa pidetään yllä eri kausia
 * @author Ville
 * @version 24.2.2020 
 */
public class Kaudet{
	private ArrayList<Kausi> kaudet;

	/**
	 * Kaudet luokan muodostaja
	 */
	public Kaudet() {
		kaudet = new ArrayList<Kausi>();	
	}
	
	/**
	 * Alustaa kaudet-luokan tiedostosta
	 * @param tiedoston nimi String
	 * @throws IOException jos ongelmia
	 */
	public void alusta(String tiedosto) throws IOException{
		
		BufferedReader f = null;
		try {
			//String file = this.getClass().getResource(tiedosto).getFile();
			//f = new BufferedReader(new FileReader(file));
			InputStream in = getClass().getResourceAsStream(tiedosto); 
			f = new BufferedReader(new InputStreamReader(in));
	    } catch (NullPointerException ex) {
	    	throw new IOException(tiedosto + "-tiedoston avaaminen ei onnistu");
	    }	    
	    try {
	      String rivi;  
	      while ( ( rivi = f.readLine() ) != null ) {
	    	  Kausi kausi = new Kausi();
	    	  kausi.aseta(rivi);
	    	  kaudet.add(kausi);	    	  
	      }
	    } catch (IOException ex) {
	    	throw new IOException("Vikaa tiedostoa lukiessa");
	    
	    } finally {
	      f.close();
	    }			
	}
	
	/**
	 * Tallentaa kaudet-luokan tiedostoon
	 * @param tiedoston nimi String
	 * @throws IOException jos ongelmia
	 */
	public void talleta(String tiedosto) throws IOException{
		
		BufferedWriter f = null;
	   
		try {
			String file = this.getClass().getResource(tiedosto).getFile();
			f = new BufferedWriter(new FileWriter(file, false));
	    } catch (NullPointerException ex) {
	    	throw new IOException("Tallennustiedoston avaaminen ei onnistu");
	    }	    
	    try {
	      String rivi;  
	      for (Kausi kausi:kaudet) {
	    	  if (kausi != null) {
	    		  rivi = kausi.palautaString();		    	 
	    		  f.write(rivi);
		    	  f.newLine();
	    	  }	  
	      }
	    } catch (IOException ex) {
	    	throw new IOException("Vikaa tallennettaessa tietoja ");
	    
	    } finally {
	      f.close();
	  
	    }			
	}
	
	/**
	 * Lisää uuden tyhjän kauden halutulla id:llä
	 */
	public void uusiKausi(int id){
		Kausi kausi = new Kausi();
		kausi.setId(id);
		kaudet.add(kausi);				
	}
	
	/**
	 * Poistaa joukkueen kaikki kaudet
	 * @param valittuId joukkueen id
	 */
	public void poistaKaudet(int valittuId) {
		while(joukkueenKaudet(valittuId) > 0) {
			for(Kausi kausi: kaudet) {			
				if(kausi.getId() == valittuId) {
					kaudet.remove(kausi);
					break;
				}	
			}
		}
	}
	
	/**
	 * Poistaa kauden annetun id:n ja vuoden perusteella
	 * @param is
	 * @param vuosi
	 */	
	public void poistaKausi(int id, int vuosi) {
		for(Kausi kausi: kaudet) {
			if (kausi.getId() == id && kausi.getVuosi() == vuosi ) {
				kausi = null;
			}
		}
	}
	/**
	 * Kertoo joukkueen kausien lukumäärän	
	 * @param id
	 * @return
	 */
	public int joukkueenKaudet(int id) {
		int k = 0;
		for(Kausi kausi: kaudet) {
			if (kausi.getId() == id) {
				k++;
			}					
		}
		return k;
	}
	
	/**
	 * Tarkistaa löytyykö listasta valittuna olevaa ID:tä ja kautta
	 * @param id
	 * @param vuosi
	 * @return true tai false
	 */
	public boolean tarkistaId(int id, int vuosi) {
		for(Kausi kausi: kaudet) {
			if (kausi.getId() == id && kausi.getVuosi() == vuosi ) {
				return true;
			}					
		}
		return false;
	}
		
	/**
	 * Palauttaa Kaudet olion viitteen
	 * @return viite kausiin
	 */	
	public ArrayList<Kausi> palautaTaulukko() {
		return this.kaudet;
	}

	/**
	 * Palauttaa viitteen listaan jossa vain mestaruuskauden mukana
	 * @return ArrayList<Kausi> mestaruudet
	 */
	public ArrayList<Kausi> mestaruudet() {
		ArrayList<Kausi> ykkossijat = new ArrayList<>();
		for(Kausi kausi: kaudet) {
			if (kausi.getSijoitus() == 1) {
				ykkossijat.add(kausi);
			}
		}
		return ykkossijat;
	}
}
