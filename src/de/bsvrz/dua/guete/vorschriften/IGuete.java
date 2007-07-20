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

package de.bsvrz.dua.guete.vorschriften;


/**
 * <code>interface</code>, das von allen Klassen implementiert werden sollte, die eine
 * Berechnungsvorschrift zur Güteberechnung zur Verfügung stellen. 
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 */
public interface IGuete {
	
	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * Güte-Indizes gehören mit dem Operator "<code>*</code>" verknüpft worden sind.
	 * 
	 * @param quellGueten die Güte-Indezes aus denen die Gesamtgüte berechnet werden
	 * soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 * übergeben wurde 
	 */
	public double p(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * Güte-Indizes gehören mit dem Operator "<code>/</code>" verknüpft worden sind.
	 * 
	 * @param quellGueten die Güte-Indezes aus denen die Gesamtgüte berechnet werden
	 * soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 * übergeben wurde  
	 */
	public double q(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * Güte-Indizes gehören mit dem Operator "<code>+</code>" verknüpft worden sind.
	 * 
	 * @param quellGueten die Güte-Indezes aus denen die Gesamt-Güte berechnet werden
	 * soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 * übergeben wurde
	 */
	public double s(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen diese
	 * Güte-Indizes gehören mit dem Operator "<code>-</code>" verknüpft worden sind.
	 * 
	 * @param quellGueten die Güte-Indezes aus denen die Gesamt-Güte berechnet werden
	 * soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 * übergeben wurde
	 */
	public double d(final double... quellGueten);
	
	/**
	 * Diese Methode berechnet aus dem Güte-Index <b>quellGuete</b> und dem Exponenten
	 * <b>exponent</b> eine Gesamt-Güte unter der Vorraussetzung, dass der Wert, zu dem
	 * dieser Güte-Index gehört mit dem Exponenten <b>exponent</b> potenziert worden ist.
	 * 
	 * @param quellGuete der erste Güte-Index
	 * @param exponent der Exponent
	 * @return die Gesamt-Güte
	 */
	public double e(final double quellGuete, final double exponent);

}
