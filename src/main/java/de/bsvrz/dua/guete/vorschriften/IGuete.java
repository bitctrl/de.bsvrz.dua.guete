/*
 * Segment Datenübernahme und Aufbereitung (DUA), Bibliothek Güteberechnung
 * Copyright (C) 2007 BitCtrl Systems GmbH 
 * Copyright 2016 by Kappich Systemberatung Aachen
 * 
 * This file is part of de.bsvrz.dua.guete.
 * 
 * de.bsvrz.dua.guete is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * de.bsvrz.dua.guete is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with de.bsvrz.dua.guete.  If not, see <http://www.gnu.org/licenses/>.

 * Contact Information:
 * Kappich Systemberatung
 * Martin-Luther-Straße 14
 * 52062 Aachen, Germany
 * phone: +49 241 4090 436 
 * mail: <info@kappich.de>
 */

package de.bsvrz.dua.guete.vorschriften;

/**
 * <code>interface</code>, das von allen Klassen implementiert werden sollte,
 * die eine Berechnungsvorschrift zur Güteberechnung zur Verfügung stellen.
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 */
public interface IGuete {

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese Güte-Indizes gehören mit dem Operator "<code>*</code>"
	 * verknüpft worden sind.
	 * 
	 * @param quellGueten
	 *            die Güte-Indizes aus denen die Gesamtgüte berechnet werden
	 *            soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 *         übergeben wurde
	 */
	double p(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese Güte-Indizes gehören mit dem Operator "<code>/</code>"
	 * verknüpft worden sind.
	 * 
	 * @param quellGueten
	 *            die Güte-Indizes aus denen die Gesamtgüte berechnet werden
	 *            soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 *         übergeben wurde
	 */
	double q(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese Güte-Indizes gehören mit dem Operator "<code>+</code>"
	 * verknüpft worden sind.
	 * 
	 * @param quellGueten
	 *            die Güte-Indizes aus denen die Gesamt-Güte berechnet werden
	 *            soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 *         übergeben wurde
	 */
	double s(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese Güte-Indizes gehören mit dem Operator "<code>+</code>"
	 * verknüpft worden sind.
	 * 
	 * @param quellGuetenMitGewichtung
	 *            die Güte-Indizes aus denen die Gesamt-Güte berechnet werden
	 *            soll mit einem jeweiligen Gewichtungsfaktor<br>
	 *            (Alle Werte sind Tupel der Form
	 *            <code>[Wert, Gewichtung]</code>)
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 *         übergeben wurde
	 */
	double sw(final double[]... quellGuetenMitGewichtung);

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese Güte-Indizes gehören mit dem Operator "<code>-</code>"
	 * verknüpft worden sind.
	 * 
	 * @param quellGueten
	 *            die Güte-Indizes aus denen die Gesamt-Güte berechnet werden
	 *            soll
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 *         übergeben wurde
	 */
	double d(final double... quellGueten);

	/**
	 * Diese Methode berechnet aus allen Güte-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-Güte unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese Güte-Indizes gehören mit dem Operator "<code>-</code>"
	 * verknüpft worden sind.
	 * 
	 * @param quellGuetenMitGewichtung
	 *            die Güte-Indizes aus denen die Gesamt-Güte berechnet werden
	 *            soll mit einem jeweiligen Gewichtungsfaktor<br>
	 *            (Alle Werte sind Tupel der Form
	 *            <code>[Wert, Gewichtung]</code>)
	 * @return die Gesamt-Güte oder 1.0, für den Fall, dass eine leere Menge
	 *         übergeben wurde
	 */
	double dw(final double[]... quellGuetenMitGewichtung);

	/**
	 * Diese Methode berechnet aus dem Güte-Index <b>quellGuete</b> und dem
	 * Exponenten <b>exponent</b> eine Gesamt-Güte unter der Vorraussetzung,
	 * dass der Wert, zu dem dieser Güte-Index gehört mit dem Exponenten
	 * <b>exponent</b> potenziert worden ist.
	 * 
	 * @param quellGuete
	 *            der erste Güte-Index
	 * @param exponent
	 *            der Exponent
	 * @return die Gesamt-Güte
	 */
	double e(final double quellGuete, final double exponent);

}
