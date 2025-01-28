import { Component } from '@angular/core';

@Component({
  selector: 'app-impressum', // <- Der Selector
  standalone: true, // <- Wichtig: Dies macht die Komponente standalone
  templateUrl: './impressum.component.html',
  styleUrls: ['./impressum.component.css']
})
export class ImpressumComponent {}