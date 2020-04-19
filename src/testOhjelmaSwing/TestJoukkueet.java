package testOhjelmaSwing;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import ohjelmaSwing.Joukkue;
import ohjelmaSwing.Joukkueet;

public class TestJoukkueet {
	
	
	@Test
	public void testJoukkueet() {
		Joukkueet joukkueet = new Joukkueet();
		try {
			joukkueet.alusta("testi.DAT");	
		} catch (IOException e) {
			System.out.println("ongelmia");
		}
		
		assertEquals(joukkueet.maaraJoukkueet(), 3);
		assertEquals(joukkueet.seuraavaId(), 6);
		assertEquals(joukkueet.taulukkoKoko(), 50);
				
		try {
			joukkueet.uusiJoukkue();
		} catch (IOException e) {
			System.out.println("ongelmia");
		}
		assertEquals(joukkueet.maaraJoukkueet(), 4);
		assertEquals(joukkueet.seuraavaId(), 7);
		
		//Kun poistetaan niin joukkueiden lukumäärä ei vähene, tämä tarkoituksella
		joukkueet.poistaJoukkue(6);
		assertEquals(joukkueet.maaraJoukkueet(), 4);
		
		//Testataan tarkista id toimivuus
		assertEquals(joukkueet.tarkistaId(5), true);
		assertEquals(joukkueet.tarkistaId(6), false);
		
		//Testataan palauta nimi toimivuus
		assertEquals(joukkueet.getNimi(5), "Mikko");
		assertEquals(joukkueet.getNimi(6), null);
		
		
		//Ainoastaan tallennuksessa ja alustuksessa tyhjät joukkueet katoavat
		try {
			joukkueet.talleta("testi.DAT");
		} catch (IOException e) {
			System.out.println("ongelmia");
		}
		
		Joukkueet joukkueet2 = new Joukkueet();
		try {
			joukkueet2.alusta("testi.DAT");	
		} catch (IOException e) {
			System.out.println("ongelmia");
		}		
		assertEquals(joukkueet2.maaraJoukkueet(), 3);
		
		//Palauta joukkueen taulukko
		assertEquals(joukkueet.palautaTaulukko()[0].getId(), 0);
		
		//Testataan miten päivitä joukkueen tiedot toimii
		Joukkue joukkue = new Joukkue();
		joukkue.setId(5);		
		try {
			joukkueet2.paivitaJoukkue(joukkue.palautaTaulukko());
		} catch (IOException e) {
			System.out.println(e);
		}
		assertEquals(joukkueet2.getNimi(5), "Uusi joukkue");
		
	}
}
