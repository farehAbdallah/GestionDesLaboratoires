import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnalyseService {
  private baseUrl = 'http://localhost:3000'; // URL pour accéder aux analyses dans json-server

  constructor(private http: HttpClient) {}

  // Récupérer toutes les analyses
  getAnalyses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/analyses`);
  }

  // Ajouter une nouvelle analyse
  addAnalyse(analyse: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/analyses`, analyse);
  }

  // Mettre à jour une analyse existante
  updateAnalyse(id: string, analyse: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/analyses/${id}`, analyse);
  }

  // Supprimer une analyse
  deleteAnalyse(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/analyses/${id}`);
  }

  // Récupérer toutes les épreuves (epreuves)
  getEpreuves(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/epreuves`);
  }

  // Ajouter une nouvelle épreuve
  addEpreuve(epreuve: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/epreuves`, epreuve);
  }

  // Mettre à jour une épreuve existante
  updateEpreuve(id: string, epreuve: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/epreuves/${id}`, epreuve);
  }

  // Supprimer une épreuve
  deleteEpreuve(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/epreuves/${id}`);
  }

  // Récupérer tous les tests
  getTests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/tests`);
  }

  // Ajouter un nouveau test
  addTest(test: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/tests`, test);
  }

  // Mettre à jour un test existant
  updateTest(id: string, test: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/tests/${id}`, test);
  }

  // Supprimer un test
  deleteTest(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/tests/${id}`);
  }

  getLaboratoires(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/laboratoires`);
  }
}
