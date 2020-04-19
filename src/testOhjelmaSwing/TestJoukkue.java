package testOhjelmaSwing;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ohjelmaSwing.Joukkue;

public class TestJoukkue{

	/**
	 * Testataan getId metodin toimivuus
	 */
	@Test
    public void setId() {
    	Joukkue joukkue = new Joukkue();
    	assertEquals(0, joukkue.getId());
    	joukkue.setId(10);
    	assertEquals(10, joukkue.getId());
    }
	
	/**
	 * Testataan getId metodin toimivuus
	 */
	@Test
    public void getId() {
    	Joukkue joukkue = new Joukkue();
    	joukkue.setId(10);
    	assertEquals(10, joukkue.getId());
    }
	
	/**
	 * Testataan getNimi metodin toimivuus
	 */
	@Test
    public void getNimi() {
    	Joukkue joukkue = new Joukkue();  	
    	assertEquals("Uusi joukkue", joukkue.getNimi());
    }
	
	/**
	 * Testataan getManageri metodin toimivuus
	 */
	@Test
    public void getManageri() {
    	Joukkue joukkue = new Joukkue();  	
    	assertEquals("Uusi manageri", joukkue.getManageri());
    }
	
	/**
	 *  Testaa aseta ja palauta metodit, sekä String etää Taulukko muodossa
	 */
	@Test
    public void testAseta() {
	    Joukkue joukkue = new Joukkue();
	    
	    // Tekstirivinä
	    joukkue.aseta("1|nimi|manageri|puhelin|liittynyt|slogan|");
		
	    assertEquals("1", joukkue.palautaTaulukko()[0]);
		assertEquals("manageri", joukkue.palautaTaulukko()[2]);
		assertEquals("slogan", joukkue.palautaTaulukko()[5]);
		assertEquals("1|nimi|manageri|puhelin|liittynyt|slogan|", joukkue.palautaString());
		
		// Taulukkona
		joukkue.aseta(new String[] {"nolla0", "nolla1", "nolla2"});
		assertEquals("nolla0", joukkue.palautaTaulukko()[0]);
		assertEquals("nolla1", joukkue.palautaTaulukko()[1]);
		assertEquals("nolla2", joukkue.palautaTaulukko()[2]);
		assertEquals("slogan", joukkue.palautaTaulukko()[5]);
		assertEquals("nolla0|nolla1|nolla2|puhelin|liittynyt|slogan|", joukkue.palautaString());
	
	}
	
	/**
	 * Testaan Erota metodin toimivuus
	 */
	@Test
    public void testErota() {
    	StringBuilder jono = new StringBuilder("testi|testi2|");
    	assertEquals("testi", Joukkue.erota(jono, '|', false));
    	assertEquals("testi2", Joukkue.erota(jono, '|', false));
    	assertEquals("", Joukkue.erota(jono, '|', false));
    }
	
	/**
	 * 
	 */
	@Test
	public void testPalautaTaulukko() {
	    Joukkue joukkue = new Joukkue();
		assertEquals("0", joukkue.palautaTaulukko()[0]);
		assertEquals("-", joukkue.palautaTaulukko()[3]);
		assertEquals("-", joukkue.palautaTaulukko()[4]);
				
	}
	
	
	
}
