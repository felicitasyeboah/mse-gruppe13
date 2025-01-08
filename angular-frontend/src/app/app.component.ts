import { Component } from '@angular/core';
import { ApiService } from './services/api.service'; // ApiService importieren
import { FeedbackExampleComponent } from './feedback-example/feedback-example/feedback-example.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { FeedbackListComponent } from './feedback-list/feedback-list.component';
import { FeedbackFormComponent } from './feedback-form/feedback-form.component';
import { ImpressumComponent } from './impressum/impressum.component';
import { RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true, // Stellt sicher, dass es sich um eine Standalone-Komponente handelt
  imports: [ FeedbackListComponent, ImpressumComponent, NavbarComponent, FeedbackFormComponent, RouterModule,RouterOutlet], // Importiere die notwendigen Module und Komponenten
})
export class AppComponent {
  title = 'angular-frontend'; // Titel der Anwendung

  constructor(private apiService: ApiService) {
    // Testen, ob ApiService korrekt injiziert wird
    console.log('ApiService:', this.apiService);
  }

  ngOnInit() {
    // Optional: Du kannst hier API-Aufrufe oder andere Logik hinzufügen
    this.apiService.fetchUserFeedbacks(1).subscribe(
      (data: any) => {  // 'data' ist der Rückgabewert des API-Aufrufs
        console.log('Feedbacks:', data); // Wenn erfolgreich
      },
      (error: any) => {  // Fehlerbehandlung
        console.error('Error:', error);
      }
    );
  
  }
}
