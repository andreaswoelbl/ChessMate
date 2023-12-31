# ChessMate

Bei ChessMate handelt es sich um eine App, mit welcher zu jeder Zeit gegen einen Freund oder eine Freundin Schach gespielt werden kann. Es ist möglich Lobbys zu erstellen, in welchen jeweils zwei Personen gegeneinander eine Runde Schach spielen können. 

# Wie kann gespielt werden? 

Die App muss auf zwei Geräten installiert sein und gestartet werden. Im ersten Schritt wird eine Lobby von einer Person erstellt. Diese Person erhält dann einen Lobby-Code. Die zweite Person kann diesen Lobby-Code verwenden, um derselben Lobby zu joinen. Sobald sich beide Spieler in der Lobby befinden, kann ein Spiel gestartet werden. Nach einer Runde Schach kann über dieselbe Lobby von einem Spieler eine neue Runde gestartet werden, wo wieder die andere Person beitreten kann. 

# Das Spiel

Im Spiel gelten die klassischen Regeln von Schach, es gibt aber auch noch eine spezielle Funktion. Mithilfe eines speziellen Buttons ist es möglich zu Schummeln. Wenn ein Spieler vor seinem Zug diesen Button klickt, kann jede eigene Figur ausgewählt werden und sich auf irgendein freies Feld bewegen. Auf der Seite des Gegenübers wird nicht extra angezeigt, dass dieser Zug nicht den normalen Regeln von Schach entsprechen. 
Um fair zu bleiben, kann der Gegenspieler dieses Schummeln aber auch stoppen. Mithilfe des Zudeckens des eigenen Lichtsensors am Gerät wird signalisiert, dass man vermutet, dass der Gegner gerade geschummelt hat. Hat der Gegner geschummelt, so wird sein illegaler Zug zurückgesetzt. Hat der Gegner nicht geschummelt und der Lichtsensor wurde abgedeckt, so wurde fälschlicherweise ein illegaler Zug erkannt und man selbst erhält einen Nachteil. 

# Technische Details

  ## 1. Installation und Start der App
  
  Dieses Repository enthält zwei Teile. Einerseits ein Java-Projekt, welches einen Server startet, der für das Lobbysystem zuständig ist. Andererseits enthält es die App, welche auf Geräten installiert und gestartet werden kann. Es ist unbedingt notwendig, den Server über die Main.java Datei z.B. mithilfe von IntelliJ zu starten, bevor die Apps gestartet werden, sonst kann keine verbindung zum Server erstellt werden. Diese Verbindung ist notwendig für die Funktion des Lobbysystems.
  
  ## 2. Hinweis - Schummeln und der Lichtsensor
  
  Um die Funktion des Aufdeckens eines Schummelversuches zu ermöglichen, sollte der Lichtsensor bei Beginn des Spiels nicht abgedeckt sein, sonst kann dies zu Problemen im Spielfluss führen, wenn nicht korrekt eine Verdunkelung des Lichtsensors erkannt werden kann. Wenn Emulatoren verwendet werden, kann der simulierte Lichtsensor anfangs so eingestellt werden, dass viel Licht empfangen wird. Wenn dann das Aufdecken eines Schummelversuches simuliert werden soll, kann der Lichtwert, den der Lichtsensor erhält, minimiert und anschließend erneut erhöht werden, um eine kurzzeitige Abdeckung des Lichtsensors zu simulieren.
