::TODO - ocultar ventana de ejecucion
set twain4jJarPath=%cd%
cd C:\Program Files (x86)\Java\jdk1.8.0_144/bin/
java -d32 -jar %twain4jJarPath%/src/TwainBDirect.jar nrreal.projects.twainbdirect.app.AppScannerInterface
exit