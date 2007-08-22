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

import stauma.dav.clientside.Data;
import de.bsvrz.sys.funclib.bitctrl.dua.DUAKonstanten;
import de.bsvrz.sys.funclib.bitctrl.dua.GanzZahl;
import de.bsvrz.sys.funclib.bitctrl.dua.MesswertZustand;

/**
 * Repräsentiert einen Guetewert inklusive Index und Verfahren 
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 */
public class GWert {
	
	/**
	 * aktuelle obere Intervallgrenze vom DAV-Attribut Guete
	 */
	private static final long GUETE_MAX = 10000;
	
	/**
	 * aktuelle untere Intervallgrenze vom DAV-Attribut Guete
	 */
	private static final long GUETE_MIN = 0;
		
	/**
	 * Das Berechnungsverfahren zur Behandlung dieser Guete
	 */
	private GueteVerfahren verfahren = null;
	
	/**
	 * Der Guete-Index
	 */
	private double index = -1;
	
	/**
	 * Die Gewichtung des Guetewertes
	 */
	private double gewichtung = 1.0;  

	/**
	 * Der Guetewert, wie er über einen Standardkonstruktor <b>von aussen</b>
	 * zur Verfuegung gestellt wurde
	 */
	private GanzZahl gueteAusDavWert = null;
	
	
	/**
	 * Standardkonstruktor
	 * 
	 * @param davGueteDatum ein DAV-Datum, dass den Guete-Index und das Guete-Verfahren
	 * in der Form <code>index = </code>DAV-Datum<code>.Index</code> bzw. <code>verfahren =
	 * </code>DAV-Datum<code>.Verfahren</code>
	 * enthält (dabei muss <code>davGueteDatum != null</code> sein)
	 */
	public GWert(final Data davGueteDatum){
		if(davGueteDatum == null){
			throw new NullPointerException("Uebergebene Guete ist <<null>>"); //$NON-NLS-1$
		}
		
		this.gueteAusDavWert = GanzZahl.getGueteIndex();
		this.gueteAusDavWert.setWert(davGueteDatum.getUnscaledValue("Index").longValue()); //$NON-NLS-1$
		if( !this.gueteAusDavWert.isZustand() ){
			this.index = this.gueteAusDavWert.getSkaliertenWert();
		}
		this.verfahren = GueteVerfahren.getZustand(davGueteDatum.
									getUnscaledValue("Verfahren").intValue()); //$NON-NLS-1$
	}
	
	
	/**
	 * Konstruktor
	 * 
	 * @param davDatum ein DAV-Datum (<code>!= null</code>)
	 * @param attributName der Name des Attributs, unterhalb dem ein Item
	 * <code>Güte</code> im übergebenen DAV-Datum steht. Also z.B. <code>qKfz</code> 
	 * für ein DAV-Kurzzeitdatum
	 */
	public GWert(final Data davDatum, final String attributName){
		this(davDatum.getItem(attributName).getItem("Güte")); //$NON-NLS-1$
	}
	
	
	/**
	 * Konstruktor für die Eingabe von Ganzzahlen
	 * 
	 * @param gueteWert ein skalierter Guetewert
	 * @param verfahren das Berechnungsverfahren zur Behandlung dieser Guete
	 */
	public GWert(final GanzZahl gueteWert, final GueteVerfahren verfahren){
		this.gueteAusDavWert = gueteWert;
		this.verfahren = verfahren;
		if( !this.gueteAusDavWert.isZustand() ){
			this.index = this.gueteAusDavWert.getSkaliertenWert();
		}
	}
	
	
	/**
	 * Interner Konstruktor (nur für Zwischenergebnisse)
	 * 
	 * @param index der Guete-Index
	 * @param verfahren das Berechnungsverfahren zur Behandlung dieser Guete
	 * @throws GueteException wenn kein Berechnungsverfahren angegeben wurde
	 */
	protected GWert(final double index,
					final GueteVerfahren verfahren)
	throws GueteException{
		if(verfahren == null){
			throw new GueteException("Es wurde kein Verfahren zur Berechnung der Guete angegeben"); //$NON-NLS-1$
		}
		this.index = index;
		this.verfahren = verfahren;
	}
	

	/**
	 * Erfragt eine Instanz eines Guetewertes mit der Kennzeichnung 
	 * <code>nicht ermittelbar/fehlerhaft</code>
	 * 
	 * @param verfahren das Guete-Verfahren
	 * @return eine Instanz eines Guetewertes mit der Kennzeichnung 
	 * <code>nicht ermittelbar/fehlerhaft</code>
	 */
	public static final GWert getNichtErmittelbareGuete(final GueteVerfahren verfahren){
		GanzZahl guete = GanzZahl.getGueteIndex();
		guete.setZustand(MesswertZustand.FEHLERHAFT_BZW_NICHT_ERMITTELBAR);
		return new GWert(GanzZahl.getGueteIndex(), verfahren);
	}


	/**
	 * Erfragt eine Instanz eines Guetewertes mit der Guete <code>1.0</code>
	 * 
	 * @param verfahren das Guete-Verfahren
	 * @return eine Instanz eines Guetewertes mit der Guete <code>1.0</code>
	 */
	public static final GWert getMaxGueteWert(final GueteVerfahren verfahren){
		return new GWert(GanzZahl.getGueteIndex(), verfahren);
	}

	
	/**
	 * Setzt die Gewichtung dieses Wertes
	 * 
	 * @param gewichtung die Gewichtung dieses Wertes
	 */
	protected final void setGewichtung(final double gewichtung){
		this.gewichtung = gewichtung;
	}
	
	
	/**
	 * Erfragt die Gewichtung dieses Wertes
	 * 
	 * @return die Gewichtung dieses Wertes
	 */
	protected final double getGewichtung(){
		return this.gewichtung;
	}
	
	
	/**
	 * Erfragt den Guete-Index
	 * 
	 * @return index der Guete-Index
	 */
	public final double getIndex() {
		return index;
	}
	
	
	/**
	 * Erfragt den Gueteindex als unskalierten Wert
	 * 
	 * @return der Gueteindex als unskalierter Wert
	 */
	public final long getIndexUnskaliert(){
		long indexUnskaliert = DUAKonstanten.NICHT_ERMITTELBAR_BZW_FEHLERHAFT;
		
		GanzZahl dummy = GanzZahl.getGueteIndex();
		dummy.setSkaliertenWert(this.index);
		
		if(dummy.isZustand()){
			indexUnskaliert = dummy.getZustand().getCode();
		}else{
			if(dummy.getWert() >= GUETE_MIN && dummy.getWert() <= GUETE_MAX){
				indexUnskaliert = dummy.getWert();
			}
		}
		
		return indexUnskaliert;	
	}
	
	
	/**
	 * Erfragt das Berechnungsverfahren zur Behandlung dieser Guete
	 * 
	 * @return verfahren das Berechnungsverfahren zur Behandlung dieser Guete
	 */
	public final GueteVerfahren getVerfahren() {
		return verfahren;
	}

	
	/**
	 * Erportiert den Inhalt dieses Objekts in ein DAV-Datum
	 * 
	 * @param zielDatum ein DAV-Datum, dass den Guete-Index und das Guete-Verfahren
	 * in der Form <code>index = </code>DAV-Datum<code>.Index</code> bzw. <code>verfahren =
	 * </code>DAV-Datum<code>.Verfahren</code>
	 * enthält (dabei muss <code>zielDatum != null</code> sein)
	 */
	public final void exportiere(Data zielDatum){
		zielDatum.getUnscaledValue("Index").set(this.getIndexUnskaliert()); //$NON-NLS-1$
		zielDatum.getUnscaledValue("Verfahren").set(this.verfahren.getCode()); //$NON-NLS-1$
	}
	
	
	/**
	 * Erportiert den Inhalt dieses Objekts in ein DAV-Datum
	 * 
	 * @param zielDatum ein DAV-Datum (<code>!= null</code>)
	 * @param attributName der Name des Attributs, unterhalb dem ein Item
	 * <code>Güte</code> im übergebenen DAV-Datum steht. Also z.B. <code>qKfz</code> 
	 * für ein DAV-Kurzzeitdatum
	 */
	public final void exportiere(Data zielDatum, String attributName){
		this.exportiere(zielDatum.getItem(attributName).getItem("Güte")); //$NON-NLS-1$
	}
	
	
	/**
	 * Erfragt, ob dieser Guetewert verrechenbar ist<br>
	 * Ein Guete-Wert gilt hier als verrechenbar, wenn er entweder ein Zwischenergebnis
	 * ist (also nicht mit den Standardkonstruktoren instanziiert wurde) oder wenn er kein
	 * Zwischenergebnis ist und nicht auf einem der drei Zustände
	 * <code>fehlerhaft</code>, <code>nicht ermittelbar</code> oder
	 * <code>nicht ermittelbar/fehlerhaft</code> steht
	 *  
	 * @return ob dieser Guetewert verrechenbar ist
	 */
	protected final boolean isVerrechenbar(){
		return this.gueteAusDavWert == null || !this.gueteAusDavWert.isZustand();
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		boolean ergebnis = false;
		
		if(obj != null && obj instanceof GWert){
			GWert that = (GWert)obj;
			ergebnis = this.getIndex() == that.getIndex() &&
					   this.getVerfahren().equals(that.getVerfahren());
		}
		
		return ergebnis;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Index: " + this.index + "\nVerfahren: " + this.verfahren; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
