## Grundlegende Git-Befehle (z. B. git init, git add, git commit, git push)

Diese grundlegenden Git-Befehle decken die wichtigsten Aspekte von Git ab, 
einschließlich Repository-Initialisierung, Branching, Committen, Remote-Verwalten 
und das Überprüfen des Status. 

### Initiales Git Repository lokal erstellen

`$ git init`
Dieser Befehl initialisiert ein neues Git-Repository in einem lokalen Verzeichnis

### Remote Repository klonen 
`$ git clone <URL>` z.B. ` $ git clone https://github.com/felicitasyeboah/mse-gruppe13`
Erstellt eine Kopie eines bestehenden Git-Repositorys

### Änderungen hinzufügen
`git add <Dateiname>` Fügt Änderungen an einem oder mehreren Dateien zum Staging-Bereich hinzu

`git add .` Fügt alle geänderten Dateien zum Staging-Bereich hinzu

### Commit erstellen
`git commit -m "<Nachricht>"` Erstellt einen neuen Commit mit den übergebenen Änderungen und einer Nachricht

### Remote-Repository verwalten

`git remote add <Name> <URL>` Fügt ein neues Remote-Repository zu Ihrer Liste hinzu

`git push <remote-name> <branch-name>` Sendet Ihre Commits an das Remote-Repository

`git pull <remote-name> <branch-name>` Holt die neuesten Änderungen vom Remote-Repository und integriert sie in Ihren lokalen Branch

### Repository aktualisieren
`git fetch <remote-name>` Lädt die neuesten Änderungen vom Remote-Repository herunter, ohne sie zu integrieren

### Branches verwalten
`git branch
` Erstellt einen neuen Branch oder zeigt vorhandene Branches an

`git checkout <branch-name>` Wechselt zu einem anderen Branch

### Branches löschen
`git branch -d <branch-name>` Löscht einen lokalen Branch

### Commit anzeigen
`git log` Zeigt den Commit-Verlauf an

`git log --graph` Zeigt den Commit-Verlauf als Graph darstellt

### Status überprüfen
`git status` Gibt den aktuellen Status des Arbeitsverzeichnisses und der Staging-Umgebung zurück 1.

Quellen:  
https://www.atlassian.com/de/git/glossary#terminology  
https://docs.aws.amazon.com/de_de/codecommit/latest/userguide/how-to-basic-git.html  
https://vfhvws.eduloop.de/loop/Moderne_Softwareentwicklung