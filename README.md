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

