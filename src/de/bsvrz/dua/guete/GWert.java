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

/**
 * Repräsentiert einen Guetewert inklusive Index und Verfahren 
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 */
public class GWert {
	
	/**
	 * Das Berechnungsverfahren zur Behandlung dieser Guete
	 */
	private GueteVerfahren verfahren = null;
	
	/**
	 * Der Guete-Index
	 */
	private double index = -1;
	
	
	/**
	 * Standardkonstruktor
	 * 
	 * @param davGueteDatum ein DAV-Datum, dass den Guete-Index und das Guete-Verfahren
	 * in der Form <code>index = Güte.Index</code> und <code>verfahren = Güte.Verfahren</code>
	 * enthält 
	 * @throws GueteException wenn das übergebene Datum nicht vollstädnig ausgelesen werden
	 * konnte
	 */
	public GWert(final Data davGueteDatum)
	throws GueteException{
		if(davGueteDatum == null){
			throw new NullPointerException("Uebergebene Guete ist <<null>>"); //$NON-NLS-1$
		}
		
		try{
			this.index = davGueteDatum.getScaledValue("Index").doubleValue(); //$NON-NLS-1$
			this.verfahren = GueteVerfahren.getZustand(davGueteDatum.
									getUnscaledValue("Verfahren").intValue()); //$NON-NLS-1$
		}catch(Exception ex){
			ex.printStackTrace();
			throw new GueteException("Guete konnte nicht ausgelesen werden", ex); //$NON-NLS-1$
		}
	}
	
	
	/**
	 * Interner Konstruktor
	 * 
	 * @param index der Guete-Index
	 * @param verfahren das Berechnungsverfahren zur Behandlung dieser Guete
	 * @throws GueteException wenn der Guete-Index kleiner 0 ist oder kein 
	 * Berechnungsverfahren angegeben wurde
	 */
	protected GWert(final double index,
					final GueteVerfahren verfahren)
	throws GueteException{
		if(index < 0.0){
			throw new GueteException("Ungueltiger Gueteindex: " + index); //$NON-NLS-1$
		}
		if(verfahren == null){
			throw new GueteException("Es wurde kein Verfahren zur Berechnung der Guete angegeben"); //$NON-NLS-1$
		}
		this.index = index;
		this.verfahren = verfahren;
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
	 * Erfragt das Berechnungsverfahren zur Behandlung dieser Guete
	 * 
	 * @return verfahren das Berechnungsverfahren zur Behandlung dieser Guete
	 */
	public final GueteVerfahren getVerfahren() {
		return verfahren;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Index: " + index + "\nVerfahren: " + this.verfahren; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
