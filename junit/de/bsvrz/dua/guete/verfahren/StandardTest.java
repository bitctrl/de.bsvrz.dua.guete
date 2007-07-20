/**
 * Segment 4 Datenübernahme und Aufbereitung (DUA), SWE 4.11 Güteberechnung
 * Copyright (C) 2007 BitCtrl Systems GmbH 
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * Contact Information:<br>
 * BitCtrl Systems GmbH<br>
 * Weißenfelser Straße 67<br>
 * 04229 Leipzig<br>
 * Phone: +49 341-490670<br>
 * mailto: info@bitctrl.de
 */

package de.bsvrz.dua.guete.verfahren;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.bsvrz.dua.guete.vorschriften.IGuete;
import de.bsvrz.dua.guete.vorschriften.Standard;



/**
 * Test der Standard-Gueteberechnung
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 */
public class StandardTest {
	
	/**
	 * eine statische Instanz der Standard-Güteberechnung
	 */
	private IGuete g = null;
	
	
	/**
	 * Lädt eine statische Instanz der Standard-Güteberechnung
	 */
	@Before
	public void setUp(){
		this.g = new Standard();
	}
	
	
	/**
	 * Testet die Methoden <code>q(..)</code> und <code>p(..)</code>, die in
	 * der Standard-Güteberechnung identisch implementiert sind
	 */
	@Test
	public void testPundQ(){
		
		Assert.assertEquals(1.0, g.p(null));
		Assert.assertEquals(1.0, g.p());
		Assert.assertEquals(1.0, g.p(1.0, 1.0));
		
		Assert.assertEquals(1.0, g.q(null));
		Assert.assertEquals(1.0, g.q());
		Assert.assertEquals(1.0, g.q(1.0, 1.0));

		
		Assert.assertEquals(0.5, g.p(1.0, 0.5));
		Assert.assertEquals(0.5, g.p(0.5, 1.0));
		Assert.assertEquals(0.125, g.p(0.5, 0.25));
		Assert.assertEquals(0.0, g.p(0.0));
		Assert.assertEquals(0.0, g.p(0.0, 1.0));
		Assert.assertEquals(0.0, g.p(0.0, 1.0, 0.1));
		Assert.assertEquals(1.0, g.p(1.0, 1.0, 1.0));
		
		Assert.assertEquals(0.5, g.q(1.0, 0.5));
		Assert.assertEquals(0.5, g.q(0.5, 1.0));
		Assert.assertEquals(0.125, g.q(0.5, 0.25));
		Assert.assertEquals(0.0, g.q(0.0));
		Assert.assertEquals(0.0, g.q(0.0, 1.0));
		Assert.assertEquals(0.0, g.q(0.0, 1.0, 0.1));
		Assert.assertEquals(1.0, g.q(1.0, 1.0, 1.0));
		
	}

	
	/**
 	 * Testet die Methoden <code>s(..)</code> und <code>d(..)</code>, die in
	 * der Standard-Güteberechnung identisch implementiert sind
	 */
	@Test
	public void testSundD(){

		Assert.assertEquals(1.0, g.s(null));
		Assert.assertEquals(1.0, g.s());
		Assert.assertEquals(1.0, g.s(1.0, 1.0));
		
		Assert.assertEquals(1.0, g.d(null));
		Assert.assertEquals(1.0, g.d());
		Assert.assertEquals(1.0, g.d(1.0, 1.0));
		
		Assert.assertEquals(0.75, g.s(1.0, 0.5));
		Assert.assertEquals(0.625, g.s(1.0, 0.5, 0.5, 0.5));
		Assert.assertEquals(0.5, g.s(1.0, 0.5, 0.5, 0.5, 0.3, 0.2));
		
		Assert.assertEquals(0.75, g.d(1.0, 0.5));
		Assert.assertEquals(0.625, g.d(1.0, 0.5, 0.5, 0.5));
		Assert.assertEquals(0.5, g.d(1.0, 0.5, 0.5, 0.5, 0.3, 0.2));
		
	}

	
	/**
	 * Testet die Methoden <code>e(..)</code>  der Standard-Güteberechnung
	 */
	@Test
	public void testE(){
		Assert.assertTrue( istPraktischGleich( 0.01, g.e(0.1, 2) ) );
		Assert.assertTrue( istPraktischGleich( 0.001, g.e(0.1, 3)) );
		Assert.assertTrue( istPraktischGleich( 1.0, g.e(2, 0)) );		
		Assert.assertTrue( istPraktischGleich( 2.0, g.e(2, 1)) );
		Assert.assertTrue( istPraktischGleich( 4.0, g.e(2, 2)) );
		Assert.assertTrue( istPraktischGleich( Math.sqrt(2.0), g.e(2, 0.5)) );
	}

	
	/**
	 * Fasst verschiedene Tests zusammen
	 */
	@Test
	public void testAlles() {
		
		double a = 0.3;
		double b = 0.4;
		double c = 0.5;
		double d = 0.6;
		
		Assert.assertEquals((a*b), g.p(a, b));
		Assert.assertEquals((a*b), g.q(a, b));
		
		Assert.assertEquals(((a+b) / 2), g.s(a, b));
		Assert.assertEquals(((a+b) / 2), g.d(a, b));
		
		Assert.assertEquals((a*b+c*d)/2 * ( (a+c)/2 ), g.q( g.s( g.p(a, b), g.p(c, d)), g.d(a, c)));
		
	}
	
	
	/**
	 * Vergleicht 2 Gleitkommazahlen  
	 * 
	 * @param a Zahl Nr.1
	 * @param b Zahl Nr.1
	 * @return ob die beiden Zahlen bis auf eine Toleranz von 0.000001 gleich sind
	 */
	private final boolean istPraktischGleich(final double a, final double b){
		return Math.abs(a - b) < 0.000001;
	}
	
}
