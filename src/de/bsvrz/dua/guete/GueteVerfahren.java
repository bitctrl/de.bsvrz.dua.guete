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

package de.bsvrz.dua.guete;

import java.util.HashMap;
import java.util.Map;

import stauma.dav.clientside.Data;
import de.bsvrz.dua.guete.vorschriften.Standard;
import de.bsvrz.sys.funclib.bitctrl.daf.AbstractDavZustand;

/**
 * Repräsentiert den DAV-Enumerationstypen
 * <code>att.güteVerfahren</code> 
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 */
public class GueteVerfahren 
extends AbstractDavZustand{
	
	/**
	 * Der Wertebereich dieses DAV-Enumerationstypen
	 */
	private static Map<Integer, GueteVerfahren> WERTE_BEREICH = 
						new HashMap<Integer, GueteVerfahren>();
	
	/**
	 * Standardverfahren gemäß Anwenderforderungen
	 */
	public static final GueteVerfahren STANDARD = new GueteVerfahren("Standard", 0, new Standard()); //$NON-NLS-1$
	
	/**
	 * Die zur Anwendung kommende Berechnungsvorschrift
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
	 * mit dem übergebenen Code
	 *
	 * @param der Code des Enumerations-Wertes
	 */
	public static final GueteVerfahren getZustand(int code){
		return WERTE_BEREICH.get(code);
	}


	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * Güte-Indizes gehören mit dem Operator "<code>*</code>" verknüpft worden sind.
	 * 
	 * @param quellGueten die Güte-Datensätze aus denen die Gesamtgüte berechnet werden
	 * soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 * übergeben wurde
	 * @throws GueteException wenn die Verfahren zur Berechnung der Güte innerhalb der
	 * übergebenen Datensätze nicht identisch ist
	 */
	public static double getPVonData(final Data... quellGueten)
	throws GueteException{
		double ergebnis = 1.0;
		
		if(quellGueten != null && quellGueten.length > 0){
			WerteMenge werteMenge = new WerteMenge(quellGueten);
			ergebnis = werteMenge.getVerfahren().getBerechnungsVorschrift().p(werteMenge.getIndezes());
		}
		
		return ergebnis;
	}
	

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * Güte-Indizes gehören mit dem Operator "<code>/</code>" verknüpft worden sind.
	 * 
	 * @param quellGueten die Güte-Datensätze aus denen die Gesamtgüte berechnet werden
	 * soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 * übergeben wurde  
	 * @throws GueteException wenn die Verfahren zur Berechnung der Güte innerhalb der
	 * übergebenen Datensätze nicht identisch ist
	 */
	public static double getQVonData(final Data... quellGueten)
	throws GueteException{
		double ergebnis = 1.0;
		
		if(quellGueten != null && quellGueten.length > 0){
			WerteMenge werteMenge = new WerteMenge(quellGueten);
			ergebnis = werteMenge.getVerfahren().getBerechnungsVorschrift().q(werteMenge.getIndezes());
		}
		
		return ergebnis;		
	}
	

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * Güte-Indizes gehören mit dem Operator "<code>+</code>" verknüpft worden sind.
	 * 
	 * @param quellGueten die Güte-Datensätze aus denen die Gesamtgüte berechnet werden
	 * soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 * übergeben wurde
 	 * @throws GueteException wenn die Verfahren zur Berechnung der Güte innerhalb der
	 * übergebenen Datensätze nicht identisch ist
	 */
	public double getSVonData(final Data... quellGueten)
	throws GueteException{
		double ergebnis = 1.0;
		
		if(quellGueten != null && quellGueten.length > 0){
			WerteMenge werteMenge = new WerteMenge(quellGueten);
			ergebnis = werteMenge.getVerfahren().getBerechnungsVorschrift().s(werteMenge.getIndezes());
		}
		
		return ergebnis;
	}
	

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * Güte-Indizes gehören mit dem Operator "<code>-</code>" verknüpft worden sind.
	 * 
	 * @param quellGueten die Güte-Datensätze aus denen die Gesamtgüte berechnet werden
	 * soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 * übergeben wurde
 	 * @throws GueteException wenn die Verfahren zur Berechnung der Güte innerhalb der
	 * übergebenen Datensätze nicht identisch ist
	 */
	public double getDVonData(final Data... quellGueten)
	throws GueteException{
		double ergebnis = 1.0;
		
		if(quellGueten != null && quellGueten.length > 0){
			WerteMenge werteMenge = new WerteMenge(quellGueten);
			ergebnis = werteMenge.getVerfahren().getBerechnungsVorschrift().d(werteMenge.getIndezes());
		}
		
		return ergebnis;
	}
	
	
	/**
	 * Diese Methode berechnet aus dem Güte-Index <b>quellGuete</b> und dem Exponenten
	 * <b>exponent</b> eine Gesamt-Güte unter der Vorraussetzung, dass der Wert, zu dem
	 * dieser Güte-Index gehört mit dem Exponenten <b>exponent</b> potenziert worden ist.

	 * @param quellGueten der Güte-Datensatz
	 * @param exponent der Exponent
	 * @return die Gesamt-Güte
	 */
	public double getEVonData(final Data quellGuete, final double exponent){
		double index = quellGuete.getScaledValue("Index").doubleValue(); //$NON-NLS-1$
		GueteVerfahren verfahren = GueteVerfahren.getZustand(
				quellGuete.getUnscaledValue("Verfahren").intValue()); //$NON-NLS-1$
		
		return verfahren.getBerechnungsVorschrift().e(index, exponent);
	}
	
}
