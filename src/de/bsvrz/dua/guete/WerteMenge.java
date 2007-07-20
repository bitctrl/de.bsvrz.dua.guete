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

import java.util.TreeSet;

/**
 * Stellt eine Menge von Guete-Indezes zusammen mit ihrem
 * Berechnungs-Verfahren (konsistent) zur Verfügung 
 * 
 * @author BitCtrl Systems GmbH, Thierfelder
 *
 */
public class WerteMenge {

	/**
	 * Das Berechnungs-Verfahren 
	 */
	private GueteVerfahren verfahren = null;
	
	/**
	 * die Guete-Indezes
	 */
	private double[] indezes = null;
	
	
	/**
	 * Standardkonstruktor
	 *
	 * @param datenSaetze die Guete-Datensaetze (darf nicht <code>null</code>
	 * oder leer sein)
	 * @throws GueteException wenn die uebergebenen Datensaetze nicht alle dasselbe
	 * Berechnungs-Verfahren implementieren
	 */
	public WerteMenge(GWert... datenSaetze)
	throws GueteException{
		TreeSet<GueteVerfahren> alleVerfahren = new TreeSet<GueteVerfahren>();
		
		this.indezes = new double[datenSaetze.length];
		for(int i = 0; i<indezes.length; i++){
			alleVerfahren.add(datenSaetze[i].getVerfahren());
			this.indezes[i] = datenSaetze[i].getIndex();
			if(alleVerfahren.size() > 1){
				throw new GueteException("Die uebergebenen Datensaetze verlangen" + //$NON-NLS-1$
						" unterschiedliche Guete-Berechnungsverfahren"); //$NON-NLS-1$
			}
		}
		
		this.verfahren = alleVerfahren.first();
	}


	/**
	 * Erfragt die Guete-Indezes
	 * 
	 * @return indezes die Guete-Indezes
	 */
	public final double[] getIndezes() {
		return indezes;
	}


	/**
	 * Erfragt das Berechnungs-Verfahren
	 * 
	 * @return verfahren das Berechnungs-Verfahren
	 */
	public final GueteVerfahren getVerfahren() {
		return verfahren;
	}
	
}
