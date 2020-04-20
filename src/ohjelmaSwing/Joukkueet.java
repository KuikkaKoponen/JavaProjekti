package ohjelmaSwing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Luokka jossa pidetään yllä eri joukkueita
 * @author Ville
 * version 24.2.2020 
 */
public class Joukkueet{
		
	private Joukkue[] taulukko;
	private int maksJoukkueet = 50;
	private int maaraJoukkueet = 0;
	
	/**
	 * Joukkueet luokan muodostaja
	 */
	public Joukkueet() {
		this.taulukko = new Joukkue[this.maksJoukkueet];
	
	}
	
	/**
	 * Palauttaa joukkueiden määrän
	 * @return joukkueiden määrä
	 * @test testattu
	 */
	public int maaraJoukkueet() {
		return this.maaraJoukkueet;
	}
	
	/**
	 * Palauttaa taulukon koon
	 * @return taulukon koko
	 * @test testattu
	 */
	public int taulukkoKoko() {
		return taulukko.length;
	}
	
	/**
	 * Alustaa joukkueet-luokan tiedostosta
	 * @param tiedoston nimi string muodossa
	 * @throws IOException
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
	      int i = 0;
	      while ( ( rivi = f.readLine() ) != null ) {
	    	  this.taulukko[i] = new Joukkue();
	    	  this.taulukko[i].aseta(rivi);
	    	  this.maaraJoukkueet++;
	    	  i++;    	  	        
	      }
	    } catch (IOException ex) {
	    	throw new IOException("Vikaa tiedostoa lukiessa");
	    
	    } finally {
	      f.close();
	    }			
	}
	
	/**
	 * Tallentaa joukkueet-luokan tiedostoon
	 * @param tiedoston nimi string muodossa
	 * @throws IOException jos ongelmia
	 */
	public void talleta(String tiedosto) throws IOException{
		
		BufferedWriter f = null;

		try {
			String file = this.getClass().getResource(tiedosto).getFile();
			f = new BufferedWriter(new FileWriter(file, false));	
			//InputStream in = getClass().getResourceAsStream(tiedosto); 
			//f = new BufferedReader(new InputStreamReader(in));

	    } catch (NullPointerException ex) {
	    	throw new IOException("Tallennustiedoston avaaminen ei onnistu");
	    }	    
	    try {
	      String rivi;  
	      for (int i = 0; i<this.maaraJoukkueet; i++) {
	    	  if (this.taulukko[i] != null) {
	    		  rivi = taulukko[i].palautaString();
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
	 * Lisää uuden tyhjän joukkueen taulukon loppuun
	 * @throws IOException jos ongelmia
	 * @test testattu
	 */
	public void uusiJoukkue() throws IOException{
		if (this.maaraJoukkueet < this.maksJoukkueet) {
			int id = seuraavaId();
			this.taulukko[this.maaraJoukkueet] = new Joukkue();
			this.taulukko[this.maaraJoukkueet].setId(id);
			this.maaraJoukkueet++;
		} else {
			throw new IOException("Ohjelmassa ei ole tilaa uusille joukkueille.\n"
					+ "Kokeile: 1) Käynnistä uudelleen (-> poistetut joukkueen katoavat). Jos ei toimi niin \n"
					+ "2) Pyydä Java-koodaaja Ville paikalla (-> vaihda Joukkueet-luokan maksJoukkueet-atribuutin arvoa)");
		}
	}
	
	/**
	 * Poistaa joukkueen annetun id:n perusteella
	 * @param id poistettavan joukkueen id
	 * @test testattu
	 */
	
	public void poistaJoukkue(int id) {
		for (int i=0; i<this.maaraJoukkueet;i++) {
			if (this.taulukko[i] != null) {
				if (this.taulukko[i].getId() == id) {
					this.taulukko[i] = null;
				}
			}		
		}
	}
	
	/**
	 * Tarkistaa löytyykö listasta valittuna olevaa id:tä
	 * @param id numero
	 * @return true tai false
	 * @test testattu
	 */
	public boolean tarkistaId(int valittuId) {
		for (int i=0; i<this.maaraJoukkueet;i++) {
			if (this.taulukko[i] != null) {
				if (this.taulukko[i].getId() == valittuId) {
					return true;
				}
			}		
		}
		return false;
	}
	
	/**
	 * Palauttaa seuraavan id numeron
	 * @return id numero
	 * @test testattu
	 */
	public int seuraavaId() {
		//Haetaan eka vapaa id, jos taulukossa on tyhjiä arvoja niin skipataan se
		int id = 1;
		for (int i=0; i<this.maaraJoukkueet;i++) {
			if (this.taulukko[i] != null) {
				if (this.taulukko[i].getId() >= id) {
					id = this.taulukko[i].getId() + 1;
				}
			}			
		}
		return id;
	}
	
	/**
	 * Palauttaa nimen id:n perusteella	
	 * @param id 
	 * @return nimi
	 * @test testattu
	 */
	public String getNimi(int id) {
		for (int i=0; i<this.maaraJoukkueet;i++) {
			if (this.taulukko[i] != null) {
				if (this.taulukko[i].getId() >= id) {
					return this.taulukko[i].getNimi();
				}
			}			
		}
		return null;
	}
	
	/**
	 * Palauttaa Joukkueet olion viitteen
	 * @return joukkueet taulukossa
	 * @test testattu
	 */
	public Joukkue[] palautaTaulukko() {
		return this.taulukko;
	}
	
    /**
     * Päivittää tiedot oikealle joukkueelle
     * @param taulukko
     * @throws IOException 
     * @test testattu
     */
	public void paivitaJoukkue(String[] taulukko) throws IOException {	
		int id = Integer.parseInt(taulukko[0]);
		for (int i=0; i<this.maaraJoukkueet;i++) {
			if (this.taulukko[i] != null) {
				if (this.taulukko[i].getId() == id) {
					this.taulukko[i].aseta(taulukko);
					break;
				}
			}		
		}
	}
}
