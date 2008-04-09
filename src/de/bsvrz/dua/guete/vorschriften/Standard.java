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
 * Implementierung des Verfahrens der Standard-Güteberechnung.
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 * 
 * @version $Id$
 */
public class Standard implements IGuete {

	/**
	 * {@inheritDoc}.
	 * 
	 * Ueber allen Werten wird hier das arithmetische Mittel berechnet
	 */
	public final double s(final double... quellGueten) {
		double summe = 0.0;
		double mittelwert = 1.0;

		if (quellGueten != null && quellGueten.length != 0) {
			for (double guete : quellGueten) {
				summe += guete;
			}
			mittelwert = summe / quellGueten.length;
		}

		return mittelwert;
	}

	/**
	 * {@inheritDoc}.
	 * 
	 * Ueber allen Werten wird hier das arithmetische Mittel unter vorheriger
	 * Gewichtung der Einzelnwerte berechnet
	 */
	public double sw(double[]... quellGuetenMitGewichtung) {
		double summe = 0.0;
		double summeGewichte = 0.0;
		double mittelwert = 1.0;

		if (quellGuetenMitGewichtung != null
				&& quellGuetenMitGewichtung.length != 0) {
			for (double[] gueteMitGewichtung : quellGuetenMitGewichtung) {
				double guete = gueteMitGewichtung[0];
				double gewichtung = Math.abs(gueteMitGewichtung[1]);
				summe += gewichtung * guete;
				summeGewichte += gewichtung;
			}
			if (summeGewichte != 0) {
				mittelwert = summe / summeGewichte;
			}
		}

		return mittelwert;
	}

	/**
	 * {@inheritDoc}.
	 * 
	 * Ueber allen Werten wird hier das arithmetische Mittel berechnet
	 */
	public final double d(final double... quellGueten) {
		return s(quellGueten);
	}

	/**
	 * {@inheritDoc}.
	 * 
	 * Ueber allen Werten wird hier das arithmetische Mittel unter vorheriger
	 * Gewichtung der Einzelnwerte berechnet
	 */
	public double dw(double[]... quellGuetenMitGewichtung) {
		return sw(quellGuetenMitGewichtung);
	}

	/**
	 * {@inheritDoc}
	 */
	public final double e(final double quellGuete, final double exponent) {
		return Math.pow(quellGuete, exponent);
	}

	/**
	 * {@inheritDoc}.
	 * 
	 * Die einzelnen Werte werden hier multiplikativ miteinander verknüpft
	 */
	public final double p(final double... quellGueten) {
		double produkt = 1.0;

		if (quellGueten != null && quellGueten.length != 0) {
			for (double guete : quellGueten) {
				produkt *= guete;
			}
		}

		return produkt;
	}

	/**
	 * {@inheritDoc}.
	 * 
	 * Die einzelnen Werte werden hier multiplikativ miteinander verknüpft
	 */
	public final double q(final double... quellGueten) {
		return p(quellGueten);
	}

}
