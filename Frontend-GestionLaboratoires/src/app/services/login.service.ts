import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LaboratoireService} from './laboratoires.service';

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

  constructor(private http: HttpClient, private laboService: LaboratoireService) {}



  isTechnicien() {
    return this.hasRole('technicien');
  }

  isAdmin() {
    return this.hasRole('administrateur');
  }

  isEmploye() {
    return this.hasRole('employee');
  }

  hasRole(role: string): boolean {

    this.getLoged().subscribe(user => {
      return user &&  user.role === role;
    })
    return false;

  }

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
    this.laboService.setSelectedLabo('');
    this.loggedUser = null;

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

  // hasRole(role: string): boolean {
  //   this.loggedUser = this.getLoged().subscribe(user => { return user })
  //   return this.loggedUser && this.loggedUser.role === role;
  // }





}
