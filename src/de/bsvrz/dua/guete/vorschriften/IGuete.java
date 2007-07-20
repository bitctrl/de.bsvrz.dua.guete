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

package de.bsvrz.dua.guete.vorschriften;


/**
 * <code>interface</code>, das von allen Klassen implementiert werden sollte, die eine
 * Berechnungsvorschrift zur G�teberechnung zur Verf�gung stellen. 
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 */
public interface IGuete {
	
	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * G�te-Indizes geh�ren mit dem Operator "<code>*</code>" verkn�pft worden sind.
	 * 
	 * @param quellGueten die G�te-Indezes aus denen die Gesamtg�te berechnet werden
	 * soll
	 * @return die Gesamt-G�te oder 1.0, f�r den Fall, dass eine leere Menge
	 * �bergeben wurde 
	 */
	public double p(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * G�te-Indizes geh�ren mit dem Operator "<code>/</code>" verkn�pft worden sind.
	 * 
	 * @param quellGueten die G�te-Indezes aus denen die Gesamtg�te berechnet werden
	 * soll
	 * @return die Gesamt-G�te oder 1.0, f�r den Fall, dass eine leere Menge
	 * �bergeben wurde  
	 */
	public double q(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * G�te-Indizes geh�ren mit dem Operator "<code>+</code>" verkn�pft worden sind.
	 * 
	 * @param quellGueten die G�te-Indezes aus denen die Gesamt-G�te berechnet werden
	 * soll
	 * @return die Gesamt-G�te oder 1.0, f�r den Fall, dass eine leere Menge
	 * �bergeben wurde
	 */
	public double s(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * G�te-Indizes geh�ren mit dem Operator "<code>-</code>" verkn�pft worden sind.
	 * 
	 * @param quellGueten die G�te-Indezes aus denen die Gesamt-G�te berechnet werden
	 * soll
	 * @return die Gesamt-G�te oder 1.0, f�r den Fall, dass eine leere Menge
	 * �bergeben wurde
	 */
	public double d(final double... quellGueten);
	
	/**
	 * Diese Methode berechnet aus dem G�te-Index <b>quellGuete</b> und dem Exponenten
	 * <b>exponent</b> eine Gesamt-G�te unter der Vorraussetzung, dass der Wert, zu dem
	 * dieser G�te-Index geh�rt mit dem Exponenten <b>exponent</b> potenziert worden ist.
	 * 
	 * @param quellGuete der erste G�te-Index
	 * @param exponent der Exponent
	 * @return die Gesamt-G�te
	 */
	public double e(final double quellGuete, final double exponent);

}
