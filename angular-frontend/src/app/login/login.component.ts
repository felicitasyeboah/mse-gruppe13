import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  standalone: true,
  template: `
    <h1>Login</h1>
    <form>
      <label for="username">Benutzername:</label>
      <input id="username" type="text" />
      <br />
      <label for="password">Passwort:</label>
      <input id="password" type="password" />
      <br />
      <button type="submit">Anmelden</button>
    </form>
  `,
})
export class LoginComponent {}