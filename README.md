## Was ist Git und warum sollte es verwendet werden?


Git ist ein kostenfreies Versionskontrollsystem, welches der Verwaltung von Projekten in der Softwareentwicklung dient. Es ermöglicht effektives Speichern, Ändern und Protokollieren von Quellcode. Bei der Versionsverwaltung unterscheidet man zwischen drei Arten von Versionskontrollsystemen:

`Lokal`
<br> Daten werden lokal gespeichert.

`Zentral`
<br> Daten werden auf einem zentralen Server gespeichert und müssen von dort auf den PC übermittelt werden.

`Verteilt`<br>
Daten werden zwar auch zentral in einem Repository (Aufbewahrungsort) abgelegt, allerdings verfügt jeder Mitarbeitende über eine Kopie.

### Sicherheit

Git zählt zu den verteilten Versionskontrollsystemen und gewährleistet als solches besondere Sicherheit. 
Dadurch, dass einzelne Mitarbeiter:innen Kopien der Projektdaten erhalten, können diese durch einen Serverausfall nicht so leicht verloren gehen wie in der zentralen Versionskontrolle.

### Flexibilität

Darüber hinaus zeichnet sich Git durch seine inhärente Flexibilität aus, da verschiedene Mitarbeiter:innen simultan an demselben Projekt arbeiten können, ohne Versionen zu überschreiben und auf diesem Wege möglicherweise zu verlieren. 
Durch die Protokollierung der Versionen kann auch auf frühere Projektstände zurückgegriffen werden ohne neuere zu gefährden.  

### Einsteigerfreundlichkeit

Ein weiterer Vorteil von Git ist der Kostenpunkt; Denn bei dem Open-Source-Tool fallen keine Gebühren an. 
Unter anderem dadurch wird Git von der breiten Masse und auch vielen Anfänger:innen genutzt, wodurch es nicht an Tutorials, ob in Schrift- oder Video-Form oder als Podcast, mangelt.

## Nützliche Git-Tools und Plattformen


### Plattformen

Hosting-Plattformen vereinfachen die Zusammenarbeit der Entwickler:innen bei Software-Projekten. Mithilfe eines cloudbasierten Speichers können verschiedene Mitarbeitende effizient an einem Projekt arbeiten.

#### Github
Github zählt zu den beliebtesten Hosting-Plattformen von Git und besticht durch eine gute Übersichtlichkeit und ein simples, transparentes Design.<br>
https://github.com/

#### Gitlab
Auch mithilfe von Gitlab können Repositories verwaltet werden. Gitlab kann im Unterschied zu Github jedoch eigenständig gehostet werden und ist durch die Vielzahl an Funktionen weniger einsteigerfreundlich.<br> 
https://about.gitlab.com/


### Git-Tools

Um die Nutzung von Git zu erleichtern und effizienter zu gestalten existieren diverse Git-Gui-Clients.

#### Github desktop
Github desktop wird oft als Erstes genannt, wenn nach einer Desktop-Anwendung für Git gesucht wird. Es ist open-source und kostenfrei und hilft besonders Anfänger:innen bei dem Umgang mit Git.<br>
https://github.com/apps/desktop

#### Gitkraken
Gitkraken ist ein ansprechend gestalteter Git-Gui-Client, der das Verwalten der Repositories durch ein eingängiges User Interface vereinfacht. Es zielt darauf ab Arbeitsabläufe zu simplifizieren und bietet diverse Tools, um Prozesse zu beschleunigen. Dieses Tool ist im Unterschied zu Github desktop allerdings nicht kostenfrei.<br>
https://www.gitkraken.com/

#### Sourcetree
Auch Sourcetree schafft durch das angebotene User Interface eine bessere Übersicht über die Repositories. Diese Desktop-Anwendung ist für Windows und macOS verfügbar.<br>
https://www.sourcetreeapp.com/

### Quellen
https://www.kreativmedia.ch/de/git-dokumentation#:~:text=Git%20ist%20ein%20frei%20verf%C3%BCgbares,Protokollierung%20von%20Anpassungen)%20von%20Dateien.<br>
https://www.atlassian.com/de/git/tutorials/what-is-git#:~:text=Mit%20Git%20wird%20die%20Arbeit,der%20komplette%20Projektverlauf%20aufgerufen%20werden.<br>
https://www.arocom.de/fachbegriffe/webentwicklung/git<br>
https://pages.cms.hu-berlin.de/cms-webtech/gitlab-documentation/docs/home/#:~:text=GitLab%20ist%20eine%20auf%20git,Personen%20an%20einem%20Projekt%20vereinfacht.<br>
https://www.upguard.com/blog/bitbucket-vs-github#:~:text=Contents&text=If%20you%20boil%20it%20down,mostly%20enterprise%20and%20business%20users.<br>
https://blog.devart.com/best-git-gui-clients-for-windows.html<br>
https://elementor.com/blog/de/github-vs-gitlab-welches-ist-das-beste-fuer-sie-in-year/?utm_source=google&utm_medium=cpc&utm_campaign=21737176931&utm_term=&lang=de&gad_source=1&gclid=Cj0KCQjwgrO4BhC2ARIsAKQ7zUmnxlRRJQW9M6MWvmqId3OwQMSdU_KDBpMxPmqXN1nlL1y8M8tL4DEaAqGkEALw_wcB<br>
https://github.com/apps/desktop<br>
https://about.gitlab.com/<br>
https://github.com/<br>
https://www.gitkraken.com/<br>
https://www.sourcetreeapp.com/<br>

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
`$ git add <Dateiname>` Fügt Änderungen an einem oder mehreren Dateien zum Staging-Bereich hinzu

`$ git add .` Fügt alle geänderten Dateien zum Staging-Bereich hinzu

### Commit erstellen
`$ git commit -m "<Nachricht>"` Erstellt einen neuen Commit mit den übergebenen Änderungen und einer Nachricht

### Remote-Repository verwalten

`$ git remote add <Name> <URL>` Fügt ein neues Remote-Repository zu Ihrer Liste hinzu

`$ git push <remote-name> <branch-name>` Sendet Ihre Commits an das Remote-Repository

`$ git pull <remote-name> <branch-name>` Holt die neuesten Änderungen vom Remote-Repository und integriert sie in Ihren lokalen Branch

### Repository aktualisieren
`$ git fetch <remote-name>` Lädt die neuesten Änderungen vom Remote-Repository herunter, ohne sie zu integrieren

### Branches verwalten
`$ git branch
` Erstellt einen neuen Branch oder zeigt vorhandene Branches an

`$ git checkout <branch-name>` Wechselt zu einem anderen Branch

### Branches löschen
`$ git branch -d <branch-name>` Löscht einen lokalen Branch

### Commit anzeigen
`$ git log` Zeigt den Commit-Verlauf an

`$ git log --graph` Zeigt den Commit-Verlauf als Graph darstellt

### Status überprüfen
`$ git status` Gibt den aktuellen Status des Arbeitsverzeichnisses und der Staging-Umgebung zurück 1.

Quellen:  
https://www.atlassian.com/de/git/glossary#terminology  
https://docs.aws.amazon.com/de_de/codecommit/latest/userguide/how-to-basic-git.html  
https://vfhvws.eduloop.de/loop/Moderne_Softwareentwicklung