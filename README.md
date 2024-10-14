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

## Branches und ihre Nutzung, Umgang mit Merge Befehlen

Branches werden genutzt, um unabhängig voneinander entwickeln zu können, 
sodass keine Auswirkungen auf andere Branches im Repository entstehen. 
Das Repository verfügt über einen Standardbranch, weitere Branches können erstellt werden. 
Über einen Pull Request können verschiedene Branches zusammengeführt werden.

### Branch erstellen

In einem Branch kann unabhängig von den Änderungen der anderen Entwickler:innen des Projekts gearbeitet werden.
Der erste Branch im Repository ist der Standardbranch und trägt die Bezeichnung `main`.

Ein Branch kann mit verschiedenen Befehlen angelegt werden:

`$ git branch (branchname)` Branch erstellen
 
`$ git checkout -b (branchname)` Branch erstellen und in den Branch wechseln 

### Branches anzeigen und Branch löschen
`$ git branch` Anzeigen der verfügbaren Branches 

`$ git branch -d (branch name)` Branch löschen 

### Zusammenführen durch Pull Requests

Um Änderungen von einem Branch in einen anderen zusammenzuführen nutzt man Pull Requests. 
In Pull Requests sind die Unterschiede zwischen dem Inhalt in der Quellbranch und dem Inhalt in der Zielbranch aufgelistet.
Die vorgeschlagenen Änderungen werden durch die anderen Teammitglieder überprüft, Review-Kommentare können hinzugefügt werden.
Um die Änderungen zusammenzuführen, werden sie in den Basis-Branch integriert.
 
### Mergekonflikte

Branches können oft problemlos zusammengeführt werden, wenn Änderungen sich in unterschiedlichen Zeilen oder Dateien befinden. 

Wenn Änderungen von verschiedenen Personen in der gleichen Zeile der selben Datei stammen, muss entschieden werden, 
welche der Änderungen in einem neuen Commit übernommen werden sollen.
`$ git status` zeigt an, wo ein Konflikt auftritt. Der Konflikt muss direkt in der Datei gelöst werden. 
Datei wird geöffnet, zwischen der Zeile `<<<<<<< HEAD` und `=======` stehen die eigenen Änderungen, die 
durch `=======` von den anderen Änderungen getrennt sind. Nachdem entschieden wurde, welche Änderungen übernommen werden, 
können die Konfliktmarker gelöscht und die Datei hinzugefügt werden.

Wenn eine Person eine Datei ändert, die von einer anderen Person gelöscht wurde.

`$ git status` zeigt Konflikt an. Es muss entschieden werden, ob die Datei erhalten werden soll. 

`$ git add` gelöschte Datei hinzufügen

`$ git rm` Datei löschen 


### Quellen
https://docs.github.com/de/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-branches  
https://docs.github.com/de/pull-requests/collaborating-with-pull-requests/addressing-merge-conflicts/resolving-a-merge-conflict-using-the-command-line  
https://www.atlassian.com/de/git/tutorials/using-branches/merge-conflicts  
https://vfhvws.eduloop.de/loop/Moderne_Softwareentwicklung  


## Git mit IntelliJ/PyCharm benutzen: Local und Remote Repository


Git ermöglicht es, Änderungen an Dateien zu speichern, zurückzuverfolgen und bei Bedarf wiederherzustellen. Es gibt zwei Haupttypen von Repositories, die in diesem Kontext wichtig sind: lokale und remote Repositories. IntelliJ und PyCharm sind integrierte Entwicklungsumgebungen (IDE), welche die Softwareentwicklung erleichtern.

### Repository

Ein Repository ist das zentrale Element von Git. Es dient als Speicherort für deinen Code und deine Dateien, einschließlich des gesamten Versionsverlaufs. In einem remote Repository können auch mehrere Projektmitarbeiter zusammenarbeiten, und es kann entweder öffentlich zugänglich oder privat sein.

#### Lokal

Ein lokales Repository ist die Version des Projekts, die auf deinem eigenen Computer gespeichert ist. Hier arbeitet man aktiv an dem eigenem Code. Man kann Änderungen an Dateien vornehmen, diese hinzufügen und lokal speichern, ohne dass andere Entwickler diese Änderungen sofort sehen. Jede Änderung, die vorgenommen werden, kann mit einem __Commit__ festgehalten werden. Ein Commit enthält eine Snapshot des aktuellen Projektzustands und eine Nachricht, die die Änderungen beschreibt. In einem Repository können verschiedene __Entwicklungszweige (Branches)__ erstellt werden, um an Features oder Bugfixes zu arbeiten ohne des Hauptcode zu beeinflussen.

#### Remote
Ein remote Repository ist eine zentrale Version eines Projekts, die auf einem Server oder in der Cloud gespeichert ist, typischerweise bei Anbietern wie GitHub, GitLab oder Bitbucket. Mehrere Entwickler können auf dasselbe Repository zugreifen, ihre Änderungen synchronisieren und gemeinsam an einem Projekt arbeiten. Änderungen, die an einem lokalen Repository vorgenommen werden, können in das remote Repository hochgeladen __(Push)__ werden. Umgekehrt ist es möglich, Änderungen anderer Entwickler abzurufen __(Pull)__. Remote Repositories dienen als __Sicherheitskopie__ des Codes. Sollte ein lokales Repository verloren gehen oder beschädigt werden, bleiben die Daten im remote Repository erhalten. Zudem ermöglichen remote Repositories die zentrale Speicherung des __gesamten Versionsverlaufs__ eines Projekts, sodass jeder Entwickler den Projektverlauf nachvollziehen kann.

#### Repository-Begriffe

`Verzweigung` 

Eine Parallelversion Ihres Codes, die im Repository enthalten ist, aber keine Auswirkungen auf den primären oder Main Branch hat.

`Klon`

So kann man eine Kopie der Daten, alle Versionen, Dateien und Ordner, eines Repositories aus GitHub herunterladen

`Fork`

Es wird ein neues Repository erstellt, welches den Code und Sicherheitseinstellungen verwendet werden, wie das Repository, auf dem es basiert.

`Merge`

Änderungen werden von einer Verzweigung auf eine Andere übertragen.

`Upstream und Downstream`

Die Verzweigung eines ursprüglichen Repositorys, welches geforkt oder geklont wurde, wird Upstream genannt. Die Verzweigung, auf dem geklonten oder geforktem Zweig wird als Downstream bezeichnet.

### IntelliJ / PyCharm

IntelliJ IDEA und PyCharm sind integrierte Entwicklungsumgebungen (IDEs), die von JetBrains entwickelt wurden. Beide Tools bieten Entwicklern eine Vielzahl von Funktionen, um die Softwareentwicklung zu erleichtern, jedoch mit unterschiedlichen Schwerpunkten. Die IDEs können mit direkt mit Git arbeiten, wenn beides auf einem Gerät installiert wurde. Die Verbindung zwischen IDEs und lokalen sowie remote Repositories schafft eine effiziente und benutzerfreundliche Umgebung für die Softwareentwicklung. Entwickler können ihre Projekte einfach verwalten, Änderungen nachverfolgen und nahtlos mit anderen zusammenarbeiten, was die Qualität und Geschwindigkeit des Entwicklungsprozesses verbessert.

#### IntelliJ IDEA

IntelliJ IDEA ist eine IDE, die hauptsächlich für die Java-Entwicklung konzipiert ist, aber auch Unterstützung für viele andere Programmiersprachen bietet, wie beispielsweise Kotlin. IntelliJ hilft beim Programmieren durch folgende Funktionen:
* Intelligente Vorschläge zur Code-Vervollständigung
* Hilfreiche Funktionen zur Verbesserung und Umstrukturierung des Codes ohne seine Funktionalität zu verändern
* Einfache Integration mit Versionskontrollsystemen wie Git
* Debugging-Tools zur Fehlersuche im Code
* Umfangreiche Unterstützung für Frameworks wie Spring oder Hibernate


#### PyCharm

PyCharm ist hingegen speziell auf die Entwicklung mit Python ausgerichtet. Es bietet eine Umgebung für Python-Entwickler und unterstützt auch Webtechnologien. Folgende Funktionen helfen beim Programmieren:
* Umfassende Funktionen für die Python-Programmierung, einschließlich Code-Vervollständigung, Syntax-Hervorhebung und PEP 8-Überprüfung
* Spezielle Tools und Funktionen zur Unterstützung der Entwicklung mit dem Django-Webframework
* Integration von Funktionen für Datenanalyse, wie Jupyter Notebook und Unterstützung für Datenvisualisierungsbibliotheken
* Unterstützung in der Webentwicklung für HTML, CSS, JavaScript und Frameworks wie Flask


### Quellen

https://docs.github.com/de/repositories/creating-and-managing-repositories/about-repositories

https://www.jetbrains.com/help/idea/using-git-integration.html

https://www.jetbrains.com/help/pycharm/using-git-integration.html


## Beiträge der Teammitglieder zum Handout:
Felicitas Yeboah: Abschnitt - Grundlegende Git-Befehle
