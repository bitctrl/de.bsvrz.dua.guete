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

package de.bsvrz.dua.guete;

import java.util.TreeSet;

/**
 * Stellt eine Menge von Guete-Indizes zusammen mit ihrem Berechnungs-Verfahren
 * (konsistent) zur Verfügung.
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 */
public class WerteMenge {

	/**
	 * Das Berechnungs-Verfahren.
	 */
	private GueteVerfahren verfahren = null;

	/**
	 * die Guete-Indizes.
	 */
	private double[] indizes = null;

	/**
	 * die Guete-Indizes mit Gewichtung.
	 */
	private double[][] indizesMitGewichtung = null;

	/**
	 * zeigt an, ob einer der Werte gewichtet ist.
	 */
	private boolean gewichtet = false;

	/**
	 * Zeigt an, ob einer der Indizes, innerhalb dieser Menge auf einem der
	 * Zustände <code>fehlerhaft</code>, <code>nicht ermittelbar</code>
	 * oder <code>nicht ermittelbar/fehlerhaft</code> steht.
	 */
	private boolean verrechenbar = true;

	/**
	 * Standardkonstruktor.
	 * 
	 * @param datenSaetze
	 *            die Guete-Datensaetze (darf nicht <code>null</code> oder
	 *            leer sein)
	 * @throws GueteException
	 *             wenn die uebergebenen Datensaetze nicht alle dasselbe
	 *             Berechnungs-Verfahren implementieren
	 */
	protected WerteMenge(GWert... datenSaetze) throws GueteException {
		TreeSet<GueteVerfahren> alleVerfahren = new TreeSet<GueteVerfahren>();

		this.indizes = new double[datenSaetze.length];
		this.indizesMitGewichtung = new double[datenSaetze.length][2];
		for (int i = 0; i < indizes.length; i++) {
			if (!datenSaetze[i].isVerrechenbar()) {
				verrechenbar = false;
				break;
			}
			alleVerfahren.add(datenSaetze[i].getVerfahren());
			this.indizes[i] = datenSaetze[i].getIndex();

			this.indizesMitGewichtung[i][0] = datenSaetze[i].getIndex();
			this.indizesMitGewichtung[i][1] = datenSaetze[i].getGewichtung();
			if (datenSaetze[i].getGewichtung() != 1.0) {
				this.gewichtet = true;
			}

			if (alleVerfahren.size() > 1) {
				throw new GueteException(
						"Die uebergebenen Datensaetze verlangen" + //$NON-NLS-1$
								" unterschiedliche Guete-Berechnungsverfahren"); //$NON-NLS-1$
			}
		}

		this.verfahren = alleVerfahren.isEmpty() ? GueteVerfahren.STANDARD
				: alleVerfahren.first();
	}

	/**
	 * Erfragt, ob einer der Indizes, innerhalb dieser Menge auf einem der
	 * Zustände <code>fehlerhaft</code>, <code>nicht ermittelbar</code>
	 * oder <code>nicht ermittelbar/fehlerhaft</code> steht.
	 * 
	 * @return ob einer der Indizes, innerhalb dieser Menge auf einem der
	 *         Zustände <code>fehlerhaft</code>,
	 *         <code>nicht ermittelbar</code> oder
	 *         <code>nicht ermittelbar/fehlerhaft</code> steht
	 */
	protected final boolean isVerrechenbar() {
		return this.verrechenbar;
	}

	/**
	 * Erfragt die Guete-Indizes.
	 * 
	 * @return die Guete-Indizes
	 */
	protected final double[] getIndizes() {
		return indizes;
	}

	/**
	 * Erfragt die Guete-Indizes mit Gewichtung.
	 * 
	 * @return die Guete-Indizes mit Gewichtung
	 */
	protected final double[][] getIndizesMitGewichtung() {
		return this.indizesMitGewichtung;
	}

	/**
	 * Erfragt, ob einer der Werte innerhalb in dieser Wertemenge gewichtet ist.
	 * 
	 * @return ob einer der Werte innerhalb in dieser Wertemenge gewichtet ist
	 */
	protected final boolean isGewichtet() {
		return this.gewichtet;
	}

	/**
	 * Erfragt das Berechnungs-Verfahren.
	 * 
	 * @return das Berechnungs-Verfahren
	 */
	protected final GueteVerfahren getVerfahren() {
		return verfahren;
	}

}
