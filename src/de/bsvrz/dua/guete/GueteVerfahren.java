/**
 * Segment 4 Daten�bernahme und Aufbereitung (DUA), SWE 4.11 G�teberechnung
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
 * Wei�enfelser Stra�e 67<br>
 * 04229 Leipzig<br>
 * Phone: +49 341-490670<br>
 * mailto: info@bitctrl.de
 */

package de.bsvrz.dua.guete;

import java.util.HashMap;
import java.util.Map;

import de.bsvrz.dua.guete.vorschriften.IGuete;
import de.bsvrz.dua.guete.vorschriften.Standard;
import de.bsvrz.sys.funclib.bitctrl.daf.AbstractDavZustand;

/**
 * Repr�sentiert den DAV-Enumerationstypen
 * <code>att.g�teVerfahren</code> 
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 */
public class GueteVerfahren 
extends AbstractDavZustand{
	
	/**
	 * Standardwert f�r die Guete
	 */
	private static final double STANDARD_GUETE = 1.0;
	
	/**
	 * Der Wertebereich dieses DAV-Enumerationstypen
	 */
	private static Map<Integer, GueteVerfahren> WERTE_BEREICH = 
						new HashMap<Integer, GueteVerfahren>();
	
	/**
	 * Standardverfahren gem�� Anwenderforderungen
	 */
	public static final GueteVerfahren STANDARD = new GueteVerfahren("Standard", 0, new Standard()); //$NON-NLS-1$
	
	/**
	 * die angewendete Berechnungsvorschrift
	 */
	private IGuete berechnungsVorschrift = null;
	
	
	/**
	 * {@inheritDoc}
	 */
	private GueteVerfahren(final String name,
						   final int code,
						   final IGuete berechnungsVorschrift){
		super(code, name);
		this.berechnungsVorschrift = berechnungsVorschrift;
		WERTE_BEREICH.put(code, this);
	}

		
	/**
	 * Erfragt das Verfahren zur Gueteberechnung
	 * 
	 * @return das Verfahren zur Gueteberechnung
	 */
	public final IGuete getBerechnungsVorschrift(){
		return this.berechnungsVorschrift;
	}

	
	/**
	 * Erfragt den Wert dieses DAV-Enumerationstypen 
	 * mit dem �bergebenen Code
	 *
	 * @param der Code des Enumerations-Wertes
	 */
	public static final GueteVerfahren getZustand(int code){
		return WERTE_BEREICH.get(code);
	}

	
	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * G�te-Indizes geh�ren mit dem Operator "<code>*</code>" verkn�pft worden sind.
	 * 
	 * @param quellGueten die G�te-Datens�tze aus denen die Gesamtg�te berechnet werden
	 * soll
	 * @return die Gesamt-G�te oder 1.0, f�r den Fall, dass eine leere Menge
	 * �bergeben wurde
	 * @throws GueteException wenn die Verfahren zur Berechnung der G�te innerhalb der
	 * �bergebenen Datens�tze nicht identisch ist
	 */
	public static GWert produkt(final GWert... quellGueten)
	throws GueteException{
		GWert ergebnis = new GWert(STANDARD_GUETE, STANDARD);
		
		if(quellGueten != null && quellGueten.length > 0){
			WerteMenge werteMenge = new WerteMenge(quellGueten);
			ergebnis = new GWert(werteMenge.getVerfahren().getBerechnungsVorschrift().p(werteMenge.getIndezes()),
							     werteMenge.getVerfahren());
		}
		
		return ergebnis;
	}
	

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * G�te-Indizes geh�ren mit dem Operator "<code>/</code>" verkn�pft worden sind.
	 * 
	 * @param quellGueten die G�te-Datens�tze aus denen die Gesamtg�te berechnet werden
	 * soll
	 * @return die Gesamt-G�te oder 1.0, f�r den Fall, dass eine leere Menge
	 * �bergeben wurde  
	 * @throws GueteException wenn die Verfahren zur Berechnung der G�te innerhalb der
	 * �bergebenen Datens�tze nicht identisch ist
	 */
	public static GWert quotient(final GWert... quellGueten)
	throws GueteException{
		GWert ergebnis = new GWert(STANDARD_GUETE, STANDARD);
		
		if(quellGueten != null && quellGueten.length > 0){
			WerteMenge werteMenge = new WerteMenge(quellGueten);
			ergebnis = new GWert(werteMenge.getVerfahren().getBerechnungsVorschrift().q(werteMenge.getIndezes()),
							     werteMenge.getVerfahren());
		}
		
		return ergebnis;
	}
	

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * G�te-Indizes geh�ren mit dem Operator "<code>+</code>" verkn�pft worden sind.
	 * 
	 * @param quellGueten die G�te-Datens�tze aus denen die Gesamtg�te berechnet werden
	 * soll
	 * @return die Gesamt-G�te oder 1.0, f�r den Fall, dass eine leere Menge
	 * �bergeben wurde
 	 * @throws GueteException wenn die Verfahren zur Berechnung der G�te innerhalb der
	 * �bergebenen Datens�tze nicht identisch ist
	 */
	public static GWert summe(final GWert... quellGueten)
	throws GueteException{
		GWert ergebnis = new GWert(STANDARD_GUETE, STANDARD);
		
		if(quellGueten != null && quellGueten.length > 0){
			WerteMenge werteMenge = new WerteMenge(quellGueten);
			ergebnis = new GWert(werteMenge.getVerfahren().getBerechnungsVorschrift().s(werteMenge.getIndezes()),
							     werteMenge.getVerfahren());
		}
		
		return ergebnis;
	}
	

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * G�te-Indizes geh�ren mit dem Operator "<code>-</code>" verkn�pft worden sind.
	 * 
	 * @param quellGueten die G�te-Datens�tze aus denen die Gesamtg�te berechnet werden
	 * soll
	 * @return die Gesamt-G�te oder 1.0, f�r den Fall, dass eine leere Menge
	 * �bergeben wurde
 	 * @throws GueteException wenn die Verfahren zur Berechnung der G�te innerhalb der
	 * �bergebenen Datens�tze nicht identisch ist
	 */
	public static GWert differenz(final GWert... quellGueten)
	throws GueteException{
		GWert ergebnis = new GWert(STANDARD_GUETE, STANDARD);
		
		if(quellGueten != null && quellGueten.length > 0){
			WerteMenge werteMenge = new WerteMenge(quellGueten);
			ergebnis = new GWert(werteMenge.getVerfahren().getBerechnungsVorschrift().d(werteMenge.getIndezes()),
							     werteMenge.getVerfahren());
		}
		
		return ergebnis;
	}
	
	
	/**
	 * Diese Methode berechnet aus dem G�te-Index <b>quellGuete</b> und dem Exponenten
	 * <b>exponent</b> eine Gesamt-G�te unter der Vorraussetzung, dass der Wert, zu dem
	 * dieser G�te-Index geh�rt mit dem Exponenten <b>exponent</b> potenziert worden ist.

	 * @param quellGueten der G�te-Datensatz
	 * @param exponent der Exponent
	 * @return die Gesamt-G�te
	 * @throws GueteException falls ungueltige Werte uebergeben worden sind
	 */
	public static GWert exp(final GWert quellGuete, final double exponent)
	throws GueteException{
		return new GWert(quellGuete.getVerfahren().getBerechnungsVorschrift().e(quellGuete.getIndex(), exponent), 
						 quellGuete.getVerfahren());
	}

}
