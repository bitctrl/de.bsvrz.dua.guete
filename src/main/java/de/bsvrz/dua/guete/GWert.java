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

import java.util.Objects;

import de.bsvrz.dav.daf.main.Data;
import de.bsvrz.sys.funclib.bitctrl.dua.DUAKonstanten;
import de.bsvrz.sys.funclib.bitctrl.dua.GanzZahl;
import de.bsvrz.sys.funclib.bitctrl.dua.MesswertZustand;

/**
 * Repräsentiert einen Guetewert inklusive Index und Verfahren.
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 */
public class GWert {

	/**
	 * aktuelle obere Intervallgrenze vom DAV-Attribut Guete.
	 */
	private static final long GUETE_MAX = 10000;

	/**
	 * aktuelle untere Intervallgrenze vom DAV-Attribut Guete.
	 */
	private static final long GUETE_MIN = 0;

	/**
	 * Das Berechnungsverfahren zur Behandlung dieser Guete.
	 */
	private final GueteVerfahren verfahren;

	/**
	 * Der Guete-Index.
	 */
	private final double index;

	/**
	 * Die Gewichtung des Guetewertes.
	 */
	private double gewichtung = 1.0;

	/**
	 * Der Guetewert, wie er über einen Standardkonstruktor <b>von aussen</b>
	 * zur Verfuegung gestellt wurde.
	 */
	private GanzZahl gueteAusDavWert = null;

	/**
	 * Standardkonstruktor<br>.
	 * <b>Achtung:</b> Dieser Konstruktor funktioniert nur fuer Attribute,
	 * deren Werte in Ganzzahlen mit dem Zustand
	 * <code>nicht ermittelbar == -1</code> abbildbar sind
	 * 
	 * @param davDatum
	 *            ein DAV-Datum (<code>!= null</code>)
	 * @param attributName
	 *            der Name des Attributs, unterhalb dem ein Item
	 *            <code>Güte</code> im übergebenen DAV-Datum steht. Also z.B.
	 *            <code>qKfz</code> für ein DAV-Kurzzeitdatum
	 */
	public GWert(final Data davDatum, final String attributName) {
		this(
				davDatum,
				attributName,
				davDatum.getItem(attributName)
						.getUnscaledValue("Wert").longValue() == DUAKonstanten.NICHT_ERMITTELBAR); //$NON-NLS-1$
	}

	/**
	 * Standardkonstruktor<br>.
	 * 
	 * @param davDatum
	 *            ein DAV-Datum (<code>!= null</code>)
	 * @param attributName
	 *            der Name des Attributs, unterhalb dem ein Item
	 *            <code>Güte</code> im übergebenen DAV-Datum steht. Also z.B.
	 *            <code>qKfz</code> für ein DAV-Kurzzeitdatum
	 * @param wertIsNichtErmittelbar
	 *            gibt an, ob der Wert, mit dem diese Guete assoziiert ist im
	 *            Zustand <code>nicht ermittelbar</code> steht
	 */
	public GWert(final Data davDatum, final String attributName,
			final boolean wertIsNichtErmittelbar) {
		if (davDatum == null) {
			throw new NullPointerException("Uebergebenes Datum ist <<null>>"); //$NON-NLS-1$
		}

		Data davGueteDatum = davDatum.getItem(attributName).getItem("Güte"); //$NON-NLS-1$

		this.gueteAusDavWert = GanzZahl.getGueteIndex();
		this.gueteAusDavWert.setWert(davGueteDatum
				.getUnscaledValue("Index").longValue()); //$NON-NLS-1$
		if (this.isVerrechenbar()) {
			this.index = this.gueteAusDavWert.getSkaliertenWert();
		} else {
			this.index = Double.NaN;
		}
		this.verfahren = GueteVerfahren.getZustand(davGueteDatum
				.getUnscaledValue("Verfahren").intValue()); //$NON-NLS-1$
	}

	/**
	 * Konstruktor für die Eingabe von Ganzzahlen.
	 * 
	 * @param gueteWert
	 *            ein skalierter Guetewert
	 * @param verfahren
	 *            das Berechnungsverfahren zur Behandlung dieser Guete
	 * @param wertIsNichtErmittelbar
	 *            gibt an, ob der Wert, mit dem diese Guete assoziiert ist im
	 *            Zustand <code>nicht ermittelbar</code> steht
	 */
	public GWert(final GanzZahl gueteWert, final GueteVerfahren verfahren,
			final boolean wertIsNichtErmittelbar) {
		this.gueteAusDavWert = gueteWert;
		this.verfahren = verfahren;
		if (this.isVerrechenbar()) {
			if (wertIsNichtErmittelbar) {
				this.index = 0.0;
			} else {
				if(this.gueteAusDavWert == null) {
					this.index = GanzZahl.getGueteIndex().getSkaliertenWert();
				} else {
					this.index = this.gueteAusDavWert.getSkaliertenWert();	
				}
			}
		} else {
			index = Double.NaN;
		}
	}

	/**
	 * Kopierkonstruktor.
	 * 
	 * @param vorlage
	 *            ein zu kopierendes <code>GWert</code>-Objekt
	 */
	public GWert(final GWert vorlage) {
		this.verfahren = vorlage.verfahren;
		this.index = vorlage.index;
		this.gewichtung = vorlage.gewichtung;
		this.gueteAusDavWert = vorlage.gueteAusDavWert == null ? null : new GanzZahl(vorlage.gueteAusDavWert);
	}

	/**
	 * Interner Konstruktor (nur für Zwischenergebnisse).
	 * 
	 * @param index
	 *            der Guete-Index
	 * @param verfahren
	 *            das Berechnungsverfahren zur Behandlung dieser Guete
	 * @throws GueteException
	 *             wenn kein Berechnungsverfahren angegeben wurde
	 */
	protected GWert(final double index, final GueteVerfahren verfahren)
			throws GueteException {
		if (verfahren == null) {
			throw new GueteException(
					"Es wurde kein Verfahren zur Berechnung der Guete angegeben"); //$NON-NLS-1$
		}
		this.index = index;
		this.verfahren = verfahren;
	}

	/**
	 * Erfragt eine Instanz eines Guetewertes mit der Kennzeichnung
	 * <code>nicht ermittelbar/fehlerhaft</code>.
	 * 
	 * @param verfahren
	 *            das Guete-Verfahren
	 * @return eine Instanz eines Guetewertes mit der Kennzeichnung
	 *         <code>nicht ermittelbar/fehlerhaft</code>
	 */
	public static final GWert getNichtErmittelbareGuete(
			final GueteVerfahren verfahren) {
		GanzZahl guete = GanzZahl.getGueteIndex();
		guete.setZustand(MesswertZustand.FEHLERHAFT_BZW_NICHT_ERMITTELBAR);
		return new GWert(GanzZahl.getGueteIndex(), verfahren, false);
	}

	/**
	 * Erfragt eine Instanz eines Guetewertes mit der Guete <code>1.0</code>.
	 * 
	 * @param verfahren
	 *            das Guete-Verfahren
	 * @return eine Instanz eines Guetewertes mit der Guete <code>1.0</code>
	 */
	public static final GWert getMaxGueteWert(final GueteVerfahren verfahren) {
		return new GWert(GanzZahl.getGueteIndex(), verfahren, false);
	}

	/**
	 * Erfragt eine Instanz eines Guetewertes mit der Guete <code>0.0</code>.
	 * 
	 * @param verfahren
	 *            das Guete-Verfahren
	 * @return eine Instanz eines Guetewertes mit der Guete <code>0.0</code>
	 */
	public static final GWert getMinGueteWert(final GueteVerfahren verfahren) {
		GWert minWert = null;
		try {
			minWert = new GWert(0.0, verfahren);
		} catch (GueteException e) {
			// kann nicht passieren
		}
		return minWert;
	}

	/**
	 * Setzt die Gewichtung dieses Wertes.
	 * 
	 * @param gewichtung
	 *            die Gewichtung dieses Wertes
	 */
	protected final void setGewichtung(final double gewichtung) {
		this.gewichtung = gewichtung;
	}

	/**
	 * Erfragt die Gewichtung dieses Wertes.
	 * 
	 * @return die Gewichtung dieses Wertes
	 */
	protected final double getGewichtung() {
		return this.gewichtung;
	}

	/**
	 * Erfragt den Guete-Index.
	 * 
	 * @return index der Guete-Index
	 */
	public final double getIndex() {
		return index;
	}

	/**
	 * Erfragt den Gueteindex als unskalierten Wert.
	 * 
	 * @return der Gueteindex als unskalierter Wert
	 */
	public final long getIndexUnskaliert() {
		long indexUnskaliert = DUAKonstanten.NICHT_ERMITTELBAR_BZW_FEHLERHAFT;

		if (!Double.isNaN(this.index)) { // d.h. der Index wurde bereits
											// initialisiert
			if (this.index >= GUETE_MIN && this.index <= GUETE_MAX) {
				GanzZahl dummy = GanzZahl.getGueteIndex();
				dummy.setSkaliertenWert(this.index);
				indexUnskaliert = dummy.getWert();
			}
		} else {
			if(this.gueteAusDavWert != null) {
				indexUnskaliert = this.gueteAusDavWert.getWert();
			}
		}

		return indexUnskaliert;
	}

	/**
	 * Erfragt den Gueteindex als unskalierten und ggf. gewichteten Wert
	 * 
	 * @return der Gueteindex als unskalierter und ggf. gewichteten Wert, wie er
	 *         nach der aktuellen Gewichtung aussehen wuerde
	 */
	public final long getIndexUnskaliertGewichtet() {
		long indexUnskaliertUndGewichtet = DUAKonstanten.NICHT_ERMITTELBAR_BZW_FEHLERHAFT;

		if (!Double.isNaN(this.index)) { // d.h. der Index wurde bereits
											// initialisiert
			double gewichteterWert = this.index * this.gewichtung;
			if (gewichteterWert >= GUETE_MIN && gewichteterWert <= GUETE_MAX) {
				GanzZahl dummy = GanzZahl.getGueteIndex();
				dummy.setSkaliertenWert(gewichteterWert);
				indexUnskaliertUndGewichtet = dummy.getWert();
			}
		} else {
			if(this.gueteAusDavWert != null) {
				indexUnskaliertUndGewichtet = this.gueteAusDavWert.getWert();
			}
		}

		return indexUnskaliertUndGewichtet;
	}

	/**
	 * Erfragt das Berechnungsverfahren zur Behandlung dieser Guete.
	 * 
	 * @return verfahren das Berechnungsverfahren zur Behandlung dieser Guete
	 */
	public final GueteVerfahren getVerfahren() {
		return verfahren;
	}

	/**
	 * Erportiert den Inhalt dieses Objekts in ein DAV-Datum.
	 * 
	 * @param zielDatum
	 *            ein DAV-Datum, dass den Guete-Index und das Guete-Verfahren in
	 *            der Form <code>index = </code>DAV-Datum<code>.Index</code>
	 *            bzw. <code>verfahren =
	 * </code>DAV-Datum<code>.Verfahren</code>
	 *            enthält (dabei muss <code>zielDatum != null</code> sein)
	 */
	public final void exportiere(Data zielDatum) {
		zielDatum.getUnscaledValue("Index").set(this.getIndexUnskaliert()); //$NON-NLS-1$
		zielDatum.getUnscaledValue("Verfahren").set(this.verfahren.getCode()); //$NON-NLS-1$
	}

	/**
	 * Erportiert den Inhalt dieses Objekts in ein DAV-Datum.
	 * 
	 * @param zielDatum
	 *            ein DAV-Datum (<code>!= null</code>)
	 * @param attributName
	 *            der Name des Attributs, unterhalb dem ein Item
	 *            <code>Güte</code> im übergebenen DAV-Datum steht. Also z.B.
	 *            <code>qKfz</code> für ein DAV-Kurzzeitdatum
	 */
	public final void exportiere(Data zielDatum, String attributName) {
		this.exportiere(zielDatum.getItem(attributName).getItem("Güte")); //$NON-NLS-1$
	}

	/**
	 * Erfragt, ob dieser Guetewert verrechenbar ist<br>
	 * Ein Guete-Wert gilt hier als verrechenbar, wenn er entweder ein
	 * Zwischenergebnis ist (also nicht mit den Standardkonstruktoren
	 * instanziiert wurde) oder wenn er nicht auf einem Zustand (Wert
	 * <code>&lt; 0</code>) steht.
	 * 
	 * @return ob dieser Guetewert verrechenbar ist
	 */
	protected final boolean isVerrechenbar() {
		return this.gueteAusDavWert == null
				|| !this.gueteAusDavWert.isZustand();
	}

	@Override
	public boolean equals(Object obj) {
		boolean ergebnis = false;

		if (obj != null && obj instanceof GWert) {
			GWert that = (GWert) obj;
			ergebnis = this.getIndex() == that.getIndex()
					&& this.getVerfahren().equals(that.getVerfahren());
		}

		return ergebnis;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(index, verfahren);
	}

	@Override
	public String toString() {
		return "Index: " + this.index + "\nVerfahren: " + this.verfahren; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
