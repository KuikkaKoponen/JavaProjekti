package ohjelmaSwing;


/**
 * Luokka yhden kauden muodostamiseen ja hallitsemiseen
 * @author Ville
 * @version 0.1
 */
public class Kausi {
		
	private Integer[] taulukko;		
	
	/**
	 * Joukkue-luokan muodostaja
	 */
	public Kausi() {
		taulukko = new Integer []{-1,0,0,0,0};		
	}
	
	/**
	 * Asettaa kaudelle oikean id:n
	 */
	public void setId(int id) {
		this.taulukko[0] = id;
	}
	
	/**
	 * Palauttaa kauden id:n
	 * @return kauden id
	 */
	public int getId() {
		return this.taulukko[0];
	}
	
	/**
	 * Palauttaa kauden vuoden
	 * @return kauden vuosi
	 */
	public Integer getVuosi() {
		return this.taulukko[1];
	}
	
	/**
	 * Palauttaa kauden sijoituksen
	 * @return kauden sijoitus
	 */
	public Integer getSijoitus() {
		return this.taulukko[2];
	}
	
	/**
	 * Asettaa tekstirivin kauden atribuutteihin. Formaatti: ID|Kausi|Sijoitus|Pisteet|Varausnumero|
	 * @param tekstijono String
	 */
	public void aseta(String rivi)   {
		
		StringBuilder jono = new StringBuilder(rivi);		
		for (int i=0; i<this.taulukko.length; i++) {			
			if (jono.length()==0) {
				break;
			} else {				
				try {
					this.taulukko[i] = Integer.parseInt(erota(jono, '|', false).trim());
				} catch (NumberFormatException e) {
					System.out.println("Syötetty joku muu kuin numero "); // Ei mahdollista tulla
				}					
			}
		}
	}
		
	/**
	 * Asettaa Integer taulukon atribuutteihin
	 * @param taulukko
	 */
	public void aseta(Integer[] taulukko) {
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
     * Palauttaa String taulukon viitteen
     * @return kauden tiedot taulukossa
     */
    public Integer[] palautaTaulukko() {
    	return this.taulukko;
    }
    
    /**
     * Palauttaa tekstirivin valmiissa tallennusmuodossa, jossa kauden kaikki tiedot
     * @return kauden tiedot Stringinä
     */
    public String palautaString() {
    	String rivi = "";
    	for (int i=0; i<this.taulukko.length; i++) {			
    		rivi += this.taulukko[i] + "|"; 
		}
    	return rivi;
    	
    }
    
}
