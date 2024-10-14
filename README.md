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
 
`$git checkout -b (branchname)` Branch erstellen und in den Branch wechseln 

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

`git add` gelöschte Datei hinzufügen

`git rm` Datei löschen 


### Quellen
https://docs.github.com/de/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-branches  
https://docs.github.com/de/pull-requests/collaborating-with-pull-requests/addressing-merge-conflicts/resolving-a-merge-conflict-using-the-command-line  
https://www.atlassian.com/de/git/tutorials/using-branches/merge-conflicts  
https://vfhvws.eduloop.de/loop/Moderne_Softwareentwicklung  


