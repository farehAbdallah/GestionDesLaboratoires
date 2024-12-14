import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

interface ItemData {
  id: string;
  email: string;
  password: string;
  name: string;
  role: string;
}
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  isAuthenticated:boolean=false;
  loggedUser: any;

  private baseUrl = 'http://localhost:3000/logedUser'; // URL to json-server

  constructor(private http: HttpClient) {}

  // Fetch all users
  getLoged(): Observable<any> {
    return this.http.get<any>(this.baseUrl);
  }


  // Update an existing user
  updateLoggedUser(updatedUser: ItemData): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}`, updatedUser);
  }

  logOut(): void {
    this.isAuthenticated = false;

    this.updateLoggedUser({
      id: "",
      name: "",
      email: "",
      password: "",
      role: ""
    }).subscribe({
      next: () => {
        console.log('User successfully logged out and cleared in db.json');
      },
      error: (err) => {
        console.error('Error logging out:', err);
      },
    });
  }

  hasRole(role: string): boolean {
    this.loggedUser = this.getLoged().subscribe(user => { return user })
    return this.loggedUser && this.loggedUser.role === role;
  }



}
