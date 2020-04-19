package ohjelmaSwing;


/**
 * Luokka yhden joukkueen muodostamiseen ja hallitsemiseen
 * @author Ville
 * @version 24.2.2020
 */
public class Joukkue {
		
	private String[] taulukko; 	
		
	/**
	 * Joukkue-luokan muodostaja
	 */
	public Joukkue() {
		taulukko = new String []{"0","Uusi joukkue","Uusi manageri", "-", "-", "-"};		
	}
	
	/**
	 * Palauttaa joukkueen id:n int muodossa
	 * @return joukkueen id
	 * @test testattu
	 */
	public int getId() {
		return Integer.parseInt(this.taulukko[0]);
	}
	
	/**
	 * Asettaa joukkueelle id:n
	 * @param id anna id
	 * @test testattu
	 */
	public void setId(int id) {
		this.taulukko[0] = Integer.toString(id);
	}
	
	/**
	 * Metodi joka palauttaa joukkueen nimen
	 * @return joukkueen nimi
	 * @test testattu
	 */
	public String getNimi() {
		return this.taulukko[1];
	}
	
	/**
	 * Metodi joka palauttaa Managerin nimen
	 * @return joukkueen nimi
	 * @test testattu
	 */
	public String getManageri() {
		return this.taulukko[2];
	}
	
	/**
	 * Asettaa tekstirivin joukkueen atribuutteihin. Formaatti id|joukkue|manageri|puhelin|liittynyt|slogan|
	 * @param tekstijono String
	 * @test testattu
	 */
	public void aseta(String rivi) {
		StringBuilder jono = new StringBuilder(rivi);	
		for (int i=0; i<this.taulukko.length; i++) {			
			if (jono.length()==0) {
				break;
			} else {
				this.taulukko[i] = erota(jono, '|', false);
			}
		}
	}
		
	/**
	 * Asettaa String taulukon atribuutteihin, taulukon koolla ei väliä
	 * @param String taulukko
	 * @test testattu
	 */
	public void aseta(String[] taulukko) {
		//Tehdään looppi lyhyemmän taulukon mukaan. Eli taulukoiden ei ole pakko olla samankokoiset
		int taulukonKoko = this.taulukko.length;
		if (taulukko.length<this.taulukko.length) {
			taulukonKoko = taulukko.length;
		}
		
		for (int i=0; i<taulukonKoko; i++) {			
			this.taulukko[i] = taulukko[i];
		}
	}
	
    /**
     * Erottaa jonosta ensimmäisen sanan
     * @param jono eli rivi tekstiä
     * @param merkki millä erotetaan sanat
     * @param etsitakaperin, aina false eli käydään läpi alusta loppuun päin
     * @return erotettu jonon osa
     * @test testattu
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
     * Palauttaa String taulukon, jossa joukkueen kaikki tiedot
     * @return joukkueen tiedot String taulukossa
     * @test testattu
     */
    public String[] palautaTaulukko() {
    	return this.taulukko;
    }
    
    /**
     * Palauttaa tekstirivin valmiissa tallennusmuodossa, jossa joukkueen kaikki tiedot
     * @return joukkueen tiedot Stringinä
     * Ei käytetä
     */
    public String palautaString() {
    	String rivi = "";
    	for (int i=0; i<this.taulukko.length; i++) {			
    		rivi += this.taulukko[i] + "|"; 
		}
    	return rivi;
    	
    }
    
}
