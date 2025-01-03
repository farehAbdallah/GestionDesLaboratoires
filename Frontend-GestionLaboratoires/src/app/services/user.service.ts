import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8088/api/utilisateurs'; // URL to json-server

  constructor(private http: HttpClient) {}

  // Fetch all users
  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`).pipe(
      map((utilisateurs) =>
        utilisateurs.map((utilisateur) => ({
          id: utilisateur.idUtilisateur.toString(),
          email: utilisateur.email,
          password: utilisateur.password,
          name: utilisateur.nomComplet,
          role: utilisateur.role,
          fkIdLaboratoire: utilisateur.fkIdLaboratoire.toString(),
          profession: utilisateur.profession,
          signature: utilisateur.signature,
          numTel: utilisateur.numTel,
        }))
      )
    );
  }

  // Add a new user
  addUser(utilisateur: any): Observable<any> {
    const payload = {
      // id: utilisateur.idUtilisateur.toString(),
      email: utilisateur.email,
      password: utilisateur.password,
      nomComplet: utilisateur.name,
      role: utilisateur.role,
      fkIdLaboratoire: parseInt(utilisateur.fkIdLaboratoire, 10),
      profession: utilisateur.profession,
      signature: utilisateur.signature,
      numTel: utilisateur.numTel
    };
    return this.http.post<any>(this.baseUrl, payload);
  }

  // Update an existing user
  updateUser(id: string, utilisateur: any): Observable<any> {
    const payload = {
      id: parseInt(utilisateur.id, 10),
      email: utilisateur.email,
      password: utilisateur.password,
      nomComplet: utilisateur.name,
      role: utilisateur.role,
      fkIdLaboratoire: parseInt(utilisateur.fkIdLaboratoire, 10),
      profession: utilisateur.profession,
      signature: utilisateur.signature,
      numTel: utilisateur.numTel
    };
    return this.http.put<any>(`${this.baseUrl}/${id}`, payload);
  }

  // Delete a user
  deleteUser(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}`);
  }
}
