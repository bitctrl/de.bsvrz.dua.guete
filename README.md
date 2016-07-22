[![Build Status](https://travis-ci.org/bitctrl/de.bsvrz.dua.guete.svg?branch=master)](https://travis-ci.org/bitctrl/de.bsvrz.dua.guete)
[![Build Status](https://api.bintray.com/packages/bitctrl/maven/de.bsvrz.dua.guete/images/download.svg)](https://bintray.com/bitctrl/maven/de.bsvrz.dua.guete)


# Segment 4 Datenübernahme und Aufbereitung (DUA), SWE 4.11 Güteberechnung

Version: ${version}

## Übersicht

Diese SW-Einheit stellt über die Schnittstelle zu einer Funktionsbibliothek 
eine Menge von Funktionen zur Berechnung eines Güte-Index aus mehreren Güte-Indizes
zur Verfügung. Die Bibliothek wird in Form einer JAR-Datei (de.bsvrz.dua.guete.jar)
bereitgestellt und kann über die CLASSPATH-Variable der Java-Maschine in eine Applikation
eingebunden werden. 


## Versionsgeschichte

### 2.0.0 

Releasedatum 31.5.2016

#### Neue Abhängigkeiten

Die SWE benötigt nun das Distributionspaket de.bsvrz.sys.funclib.bitctrl.dua in
Mindestversion 1.5.0 und de.bsvrz.sys.funclib.bitctrl in Mindestversion 1.4.0.

#### Fehlerkorrekturen

Folgende Fehler gegenüber vorhergehenden Versionen wurden korrigiert:
- Mögliche NullPointerException beim Kopieren von Gütewerten korrigiert.

1.4.0
- Umstellung auf Java 8 und UTF-8

1.3.0
- Umstellung auf Funclib-Bitctrl-Dua

1.2.0
- Umstellung auf Maven-Build

1.1.7
  - Problem mit NullPointerException bei Zwischenergebnissen 
    der Gueteberechnung behoben
   
1.1.5
  - Neuer Konstruktor fuer Guetewerte hinzugefuegt	

1.1.0
  - Umpacketierung

1.0.0
  - Erste Auslieferung

## Disclaimer

Segment 4 Datenübernahme und Aufbereitung (DUA), SWE 4.11 Güteberechnung
Copyright (C) 2007 BitCtrl Systems GmbH 

This program is free software; you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation; either version 2 of the License, or (at your option) any later
version.

This program is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
details.

You should have received a copy of the GNU General Public License along with
this program; if not, write to the Free Software Foundation, Inc., 51
Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.


# Kontakt

BitCtrl Systems GmbH
Weißenfelser Straße 67
04229 Leipzig
Phone: +49 341-490670
mailto: info@bitctrl.de

