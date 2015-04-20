/*
 * Segment 4 Datenübernahme und Aufbereitung (DUA), SWE 4.11 Güteberechnung
 * Copyright (C) 2007-2015 BitCtrl Systems GmbH
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

import java.util.TreeSet;

/**
 * Stellt eine Menge von Guete-Indizes zusammen mit ihrem Berechnungs-Verfahren
 * (konsistent) zur Verfügung.
 *
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 * @version $Id$
 */
public class WerteMenge {

	/**
	 * Das Berechnungs-Verfahren.
	 */
	private GueteVerfahren verfahren;

	/**
	 * die Guete-Indizes.
	 */
	private double[] indizes;

	/**
	 * die Guete-Indizes mit Gewichtung.
	 */
	private double[][] indizesMitGewichtung;

	/**
	 * zeigt an, ob einer der Werte gewichtet ist.
	 */
	private boolean gewichtet;

	/**
	 * Zeigt an, ob einer der Indizes, innerhalb dieser Menge auf einem der
	 * Zustände <code>fehlerhaft</code>, <code>nicht ermittelbar</code> oder
	 * <code>nicht ermittelbar/fehlerhaft</code> steht.
	 */
	private boolean verrechenbar = true;

	/**
	 * Standardkonstruktor.
	 *
	 * @param datenSaetze
	 *            die Guete-Datensaetze (darf nicht <code>null</code> oder leer
	 *            sein)
	 * @throws GueteException
	 *             wenn die uebergebenen Datensaetze nicht alle dasselbe
	 *             Berechnungs-Verfahren implementieren
	 */
	protected WerteMenge(final GWert... datenSaetze) throws GueteException {
		final TreeSet<GueteVerfahren> alleVerfahren = new TreeSet<GueteVerfahren>();

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
						"Die uebergebenen Datensaetze verlangen"
								+ " unterschiedliche Guete-Berechnungsverfahren");
			}
		}

		this.verfahren = alleVerfahren.isEmpty() ? GueteVerfahren.STANDARD
				: alleVerfahren.first();
	}

	/**
	 * Erfragt, ob einer der Indizes, innerhalb dieser Menge auf einem der
	 * Zustände <code>fehlerhaft</code>, <code>nicht ermittelbar</code> oder
	 * <code>nicht ermittelbar/fehlerhaft</code> steht.
	 *
	 * @return ob einer der Indizes, innerhalb dieser Menge auf einem der
	 *         Zustände <code>fehlerhaft</code>, <code>nicht ermittelbar</code>
	 *         oder <code>nicht ermittelbar/fehlerhaft</code> steht
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
