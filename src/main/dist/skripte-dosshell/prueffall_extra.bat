@echo off

call ..\..\..\skripte-dosshell\einstellungen.bat

set cp=..\..\de.bsvrz.sys.funclib.bitctrl\de.bsvrz.sys.funclib.bitctrl-runtime.jar
set cp=%cp%;..\de.bsvrz.dua.guete-runtime.jar
set cp=%cp%;..\de.bsvrz.dua.guete-test.jar
set cp=%cp%;..\..\junit-4.1.jar

title Pruefungen SE4 - DUA, SWE 4.11

echo ========================================================
echo #  Pruefungen SE4 - DUA, SWE 4.11
echo #
echo #  Zusaetzliche Test der Standard-Gueteberechnung
echo ========================================================
echo.

%java% -cp %cp% org.junit.runner.JUnitCore de.bsvrz.dua.guete.verfahren.StandardTest
pause
