## Git mit IntelliJ/PyCharm benutzen: Local und Remote Repository


Git ermöglicht es, Änderungen an Dateien zu speichern, zurückzuverfolgen und bei Bedarf wiederherzustellen. Es gibt zwei Haupttypen von Repositories, die in diesem Kontext wichtig sind: lokale und remote Repositories. IntelliJ und PyCharm sind integrierte Entwicklungsumgebungen (IDE), welche die Softwareentwicklung erleichtern.

### Repository

Ein Repository ist das zentrale Element von Git. Es dient als Speicherort für deinen Code und deine Dateien, einschließlich des gesamten Versionsverlaufs. In einem remote Repository können auch mehrere Projektmitarbeiter zusammenarbeiten, und es kann entweder öffentlich zugänglich oder privat sein.

#### Lokal

Ein lokales Repository ist die Version des Projekts, die auf deinem eigenen Computer gespeichert ist. Hier arbeitet man aktiv an dem eigenem Code. Man kann Änderungen an Dateien vornehmen, diese hinzufügen und lokal speichern, ohne dass andere Entwickler diese Änderungen sofort sehen. Jede Änderung, die vorgenommen werden, kann mit einem __Commit__ festgehalten werden. Ein Commit enthält eine Snapshot des aktuellen Projektzustands und eine Nachricht, die die Änderungen beschreibt. In einem Repository können verschiedene __Entwicklungszweige (Branches)__ erstellt werden, um an Features oder Bugfixes zu arbeiten ohne des Hauptcode zu beeinflussen.

#### Remote
Ein remote Repository ist eine zentrale Version eines Projekts, die auf einem Server oder in der Cloud gespeichert ist, typischerweise bei Anbietern wie GitHub, GitLab oder Bitbucket. Mehrere Entwickler können auf dasselbe Repository zugreifen, ihre Änderungen synchronisieren und gemeinsam an einem Projekt arbeiten. Änderungen, die an einem lokalen Repository vorgenommen werden, können in das remote Repository hochgeladen __(Push)__ werden. Umgekehrt ist es möglich, Änderungen anderer Entwickler abzurufen __(Pull)__. Remote Repositories dienen als __Sicherheitskopie__ des Codes. Sollte ein lokales Repository verloren gehen oder beschädigt werden, bleiben die Daten im remote Repository erhalten. Zudem ermöglichen remote Repositories die zentrale Speicherung des __gesamten Versionsverlaufs__ eines Projekts, sodass jeder Entwickler den Projektverlauf nachvollziehen kann.


### IntelliJ / PyCharm

IntelliJ IDEA und PyCharm sind integrierte Entwicklungsumgebungen (IDEs), die von JetBrains entwickelt wurden. Beide Tools bieten Entwicklern eine Vielzahl von Funktionen, um die Softwareentwicklung zu erleichtern, jedoch mit unterschiedlichen Schwerpunkten.

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
