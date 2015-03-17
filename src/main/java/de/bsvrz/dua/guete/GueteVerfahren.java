/**
 * Segment 4 Daten�bernahme und Aufbereitung (DUA), SWE 4.11 G�teberechnung
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
import de.bsvrz.sys.funclib.bitctrl.dua.GanzZahl;
import de.bsvrz.sys.funclib.bitctrl.dua.MesswertZustand;

/**
 * Repr�sentiert den DAV-Enumerationstypen <code>att.g�teVerfahren</code>.
 *
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 * @version $Id$
 */
public final class GueteVerfahren extends AbstractDavZustand {

	/**
	 * Der Wertebereich dieses DAV-Enumerationstypen.
	 */
	private static Map<Integer, GueteVerfahren> werteBereich = new HashMap<Integer, GueteVerfahren>();

	/**
	 * Standard-Guete mit Status <code>nicht ermittelbar/fehlerhaft</code>.
	 */
	private static final GanzZahl FEHLERHAFT_BZW_NICHT_ERMITTELBAR = GanzZahl
			.getGueteIndex();
	static {
		GueteVerfahren.FEHLERHAFT_BZW_NICHT_ERMITTELBAR
		.setZustand(MesswertZustand.FEHLERHAFT_BZW_NICHT_ERMITTELBAR);
	}

	/**
	 * Standardverfahren gem�� Anwenderforderungen.
	 */
	public static final GueteVerfahren STANDARD = new GueteVerfahren(
			"Standard", 0, new Standard()); //$NON-NLS-1$

	/**
	 * GWert mit Status <code>nicht ermittelbar/fehlerhaft</code> und
	 * Standardverfahren.
	 */
	public static final GWert STD_FEHLERHAFT_BZW_NICHT_ERMITTELBAR = new GWert(
			GueteVerfahren.FEHLERHAFT_BZW_NICHT_ERMITTELBAR,
			GueteVerfahren.STANDARD, false);

	/**
	 * Standardwert f�r die Guete.
	 */
	private static final double STANDARD_GUETE = 1.0;

	/**
	 * die angewendete Berechnungsvorschrift.
	 */
	private IGuete berechnungsVorschrift;

	/**
	 * Standardkonstruktor.
	 *
	 * @param name
	 *            der Name des Gueteverfahrens
	 * @param code
	 *            der Code des Gueteverfahrens
	 * @param berechnungsVorschrift
	 *            die Berechnungsvorschrift des Gueteverfahrens
	 */
	private GueteVerfahren(final String name, final int code,
			final IGuete berechnungsVorschrift) {
		super(code, name);
		this.berechnungsVorschrift = berechnungsVorschrift;
		GueteVerfahren.werteBereich.put(code, this);
	}

	/**
	 * Erfragt das Verfahren zur Gueteberechnung.
	 *
	 * @return das Verfahren zur Gueteberechnung
	 */
	public IGuete getBerechnungsVorschrift() {
		return this.berechnungsVorschrift;
	}

	/**
	 * Erfragt den Wert dieses DAV-Enumerationstypen mit dem �bergebenen Code.
	 *
	 * @param code
	 *            der Code des Enumerations-Wertes
	 * @return den Wert dieses DAV-Enumerationstypen mit dem �bergebenen Code.
	 */
	public static GueteVerfahren getZustand(final int code) {
		return GueteVerfahren.werteBereich.get(code);
	}

	/**
	 * Erfragt eine gewichtete Version des uebergebenen Guetewerts.
	 *
	 * @param quellGuete
	 *            ein Guetewert
	 * @param gewichtung
	 *            die Gewichtung fuer den uebergebenen Guetewert
	 * @return der <b>gewichtete</b> uebergebene Guetewert
	 * @throws GueteException
	 *             wenn die Verfahren zur Berechnung der Guete innerhalb der
	 *             uebergebenen Datensaetze nicht identisch sind
	 */
	public static GWert gewichte(final GWert quellGuete, final double gewichtung)
			throws GueteException {
		final GWert ergebnis = new GWert(quellGuete.getIndex(),
				quellGuete.getVerfahren());
		ergebnis.setGewichtung(gewichtung);

		return ergebnis;
	}

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese G�te-Indizes geh�ren mit dem Operator "<code>*</code>" verkn�pft
	 * worden sind.
	 *
	 * @param quellGueten
	 *            die G�te-Datens�tze aus denen die Gesamtg�te berechnet werden
	 *            soll
	 * @return die Gesamt-G�te, 1.0 f�r den Fall, dass eine leere Menge
	 *         �bergeben wurde, oder <code>nicht ermittelbar/fehlerhaft</code>,
	 *         wenn der Wert sonst nicht errechnet werden konnte
	 * @throws GueteException
	 *             wenn die Verfahren zur Berechnung der Guete innerhalb der
	 *             uebergebenen Datensaetze nicht identisch sind
	 */
	public static GWert produkt(final GWert... quellGueten)
			throws GueteException {
		GWert ergebnis = new GWert(GueteVerfahren.STANDARD_GUETE,
				GueteVerfahren.STANDARD);

		if ((quellGueten != null) && (quellGueten.length > 0)) {
			final WerteMenge werteMenge = new WerteMenge(quellGueten);
			if (werteMenge.isVerrechenbar()) {
				ergebnis = new GWert(werteMenge.getVerfahren()
						.getBerechnungsVorschrift().p(werteMenge.getIndizes()),
						werteMenge.getVerfahren());
			} else {
				ergebnis = new GWert(
						GueteVerfahren.FEHLERHAFT_BZW_NICHT_ERMITTELBAR,
						werteMenge.getVerfahren(), false);
			}
		}

		return ergebnis;
	}

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese G�te-Indizes geh�ren mit dem Operator "<code>/</code>" verkn�pft
	 * worden sind.
	 *
	 * @param quellGueten
	 *            die G�te-Datens�tze aus denen die Gesamtg�te berechnet werden
	 *            soll
	 * @return die Gesamt-G�te, 1.0 f�r den Fall, dass eine leere Menge
	 *         �bergeben wurde, oder <code>nicht ermittelbar/fehlerhaft</code>,
	 *         wenn der Wert sonst nicht errechnet werden konnte
	 * @throws GueteException
	 *             wenn die Verfahren zur Berechnung der Guete innerhalb der
	 *             uebergebenen Datensaetze nicht identisch sind
	 */
	public static GWert quotient(final GWert... quellGueten)
			throws GueteException {
		GWert ergebnis = new GWert(GueteVerfahren.STANDARD_GUETE,
				GueteVerfahren.STANDARD);

		if ((quellGueten != null) && (quellGueten.length > 0)) {
			final WerteMenge werteMenge = new WerteMenge(quellGueten);
			if (werteMenge.isVerrechenbar()) {
				ergebnis = new GWert(werteMenge.getVerfahren()
						.getBerechnungsVorschrift().q(werteMenge.getIndizes()),
						werteMenge.getVerfahren());
			} else {
				ergebnis = new GWert(
						GueteVerfahren.FEHLERHAFT_BZW_NICHT_ERMITTELBAR,
						werteMenge.getVerfahren(), false);
			}
		}

		return ergebnis;
	}

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese G�te-Indizes geh�ren mit dem Operator "<code>+</code>" verkn�pft
	 * worden sind.
	 *
	 * @param quellGueten
	 *            die G�te-Datens�tze aus denen die Gesamtg�te berechnet werden
	 *            soll
	 * @return die Gesamt-G�te, 1.0 f�r den Fall, dass eine leere Menge
	 *         �bergeben wurde, oder <code>nicht ermittelbar/fehlerhaft</code>,
	 *         wenn der Wert sonst nicht errechnet werden konnte
	 * @throws GueteException
	 *             wenn die Verfahren zur Berechnung der Guete innerhalb der
	 *             uebergebenen Datensaetze nicht identisch sind
	 */
	public static GWert summe(final GWert... quellGueten) throws GueteException {
		GWert ergebnis = new GWert(GueteVerfahren.STANDARD_GUETE,
				GueteVerfahren.STANDARD);

		if ((quellGueten != null) && (quellGueten.length > 0)) {
			final WerteMenge werteMenge = new WerteMenge(quellGueten);
			if (werteMenge.isVerrechenbar()) {
				if (werteMenge.isGewichtet()) {
					ergebnis = new GWert(werteMenge.getVerfahren()
							.getBerechnungsVorschrift()
							.sw(werteMenge.getIndizesMitGewichtung()),
									werteMenge.getVerfahren());
				} else {
					ergebnis = new GWert(werteMenge.getVerfahren()
							.getBerechnungsVorschrift()
							.s(werteMenge.getIndizes()),
							werteMenge.getVerfahren());
				}
			} else {
				ergebnis = new GWert(
						GueteVerfahren.FEHLERHAFT_BZW_NICHT_ERMITTELBAR,
						werteMenge.getVerfahren(), false);
			}
		}

		return ergebnis;
	}

	/**
	 * Diese Methode berechnet aus allen G�te-Indizes aus <b>quellGueten</b>
	 * eine Gesamt-G�te unter der Vorraussetzung, dass alle Werte, zu denen
	 * diese G�te-Indizes geh�ren mit dem Operator "<code>-</code>" verkn�pft
	 * worden sind.
	 *
	 * @param quellGueten
	 *            die G�te-Datens�tze aus denen die Gesamtg�te berechnet werden
	 *            soll
	 * @return die Gesamt-G�te, 1.0 f�r den Fall, dass eine leere Menge
	 *         �bergeben wurde, oder <code>nicht ermittelbar/fehlerhaft</code>,
	 *         wenn der Wert sonst nicht errechnet werden konnte
	 * @throws GueteException
	 *             wenn die Verfahren zur Berechnung der Guete innerhalb der
	 *             uebergebenen Datensaetze nicht identisch sind
	 */
	public static GWert differenz(final GWert... quellGueten)
			throws GueteException {
		GWert ergebnis = new GWert(GueteVerfahren.STANDARD_GUETE,
				GueteVerfahren.STANDARD);

		if ((quellGueten != null) && (quellGueten.length > 0)) {
			final WerteMenge werteMenge = new WerteMenge(quellGueten);
			if (werteMenge.isVerrechenbar()) {
				if (werteMenge.isGewichtet()) {
					ergebnis = new GWert(werteMenge.getVerfahren()
							.getBerechnungsVorschrift()
							.dw(werteMenge.getIndizesMitGewichtung()),
									werteMenge.getVerfahren());
				} else {
					ergebnis = new GWert(werteMenge.getVerfahren()
							.getBerechnungsVorschrift()
							.d(werteMenge.getIndizes()),
							werteMenge.getVerfahren());
				}
			} else {
				ergebnis = new GWert(
						GueteVerfahren.FEHLERHAFT_BZW_NICHT_ERMITTELBAR,
						werteMenge.getVerfahren(), false);
			}
		}

		return ergebnis;
	}

	/**
	 * Diese Methode berechnet aus dem G�te-Index <b>quellGuete</b> und dem
	 * Exponenten <b>exponent</b> eine Gesamt-G�te unter der Vorraussetzung,
	 * dass der Wert, zu dem dieser G�te-Index geh�rt mit dem Exponenten
	 * <b>exponent</b> potenziert worden ist.
	 *
	 * @param quellGuete
	 *            der G�te-Datensatz
	 * @param exponent
	 *            der Exponent
	 * @return die Gesamt-G�te oder <code>nicht ermittelbar/fehlerhaft</code>,
	 *         wenn der Wert sonst nicht errechnet werden konnte
	 * @throws GueteException
	 *             falls ungueltige Werte uebergeben worden sind
	 */
	public static GWert exp(final GWert quellGuete, final double exponent)
			throws GueteException {
		GWert ergebnis = new GWert(
				GueteVerfahren.FEHLERHAFT_BZW_NICHT_ERMITTELBAR,
				quellGuete.getVerfahren(), false);

		if (quellGuete.isVerrechenbar()) {
			ergebnis = new GWert(quellGuete.getVerfahren()
					.getBerechnungsVorschrift()
					.e(quellGuete.getIndex(), exponent),
					quellGuete.getVerfahren());
		}

		return ergebnis;
	}

}
