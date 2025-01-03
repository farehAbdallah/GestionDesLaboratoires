import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {map, Observable, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private baseUrl = 'http://localhost:8086/api'; // URL pour accéder aux patients dans json-server
  private springUrl = 'http://localhost:8088/api'; // URL pour accéder aux patients dans json-server

  constructor(private http: HttpClient) {}

  // Récupérer tous les patients
  getPatients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/patients`).pipe(
      map(patients =>
        patients.map(patient => ({
          id: patient.id.toString(),
          nomComplet: patient.nomComplet,
          dateNaissance: patient.dateNaissance,
          lieuNaissance: patient.lieuDeNaissance,
          sexe: patient.sexe,
          typePieceIdentite: patient.typePieceIdentite,
          numPieceIdentite: patient.numPieceIdentite,
          adresse: patient.adresse,
          numTel: patient.numTel,
          email: patient.email,
          visible_pour: patient.visiblePour,
        }))
      )
    );
  }

  // Ajouter un nouveau patient
  addPatient(patient: any): Observable<any> {
    const payload = {
      // id: patient.id.toString(),
      nomComplet: patient.nomComplet,
      dateNaissance: patient.dateNaissance,
      lieuDeNaissance: patient.lieuNaissance,
      sexe: patient.sexe,
      typePieceIdentite: patient.typePieceIdentite,
      numPieceIdentite: patient.numPieceIdentite,
      adresse: patient.adresse,
      numTel: patient.numTel,
      email: patient.email,
      visiblePour: patient.visible_pour,
    };
    return this.http.post<any>(`${this.baseUrl}/patients`, payload);
  }

  // Mettre à jour un patient existant
  updatePatient(id: string, patient: any): Observable<any> {
    const payload = {
      id: parseInt(patient.id, 10),
      nomComplet: patient.nomComplet,
      dateNaissance: patient.dateNaissance,
      lieuDeNaissance: patient.lieuNaissance,
      sexe: patient.sexe,
      typePieceIdentite: patient.typePieceIdentite,
      numPieceIdentite: patient.numPieceIdentite,
      adresse: patient.adresse,
      numTel: patient.numTel,
      email: patient.email,
      visiblePour: patient.visible_pour,
    };
    return this.http.put<any>(`${this.baseUrl}/patients/${id}`, payload);
  }

  // Supprimer un patient
  deletePatient(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/patients/${id}`);
  }

  // Get all dossiers
  getDossiers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/dossiers`).pipe(
      map(dossiers =>
        dossiers.map(dossier => ({
          id: dossier.numDossier.toString(),
          numDossier: dossier.numDossier.toString(),
          fkEmailUtilisateur: dossier.fkEmailUtilisateur,
          fkIdPatient: dossier.patientId.toString(),
          date: dossier.date,
        }))
       )
    ); // Assuming the API is structured to support dossiers
  }

  // Add a new dossier
  addDossier(dossier: any): Observable<any> {
    const payload = {
      // numDossier: parseInt(dossier.numDossier, 10),
      fkEmailUtilisateur: dossier.fkEmailUtilisateur,
      patientId: parseInt(dossier.fkIdPatient, 10),
      date: dossier.date,
    };
    return this.http.post<any>(`${this.baseUrl}/dossiers`, payload); // Assuming POST method is used for adding a dossier
  }

  // Update an existing dossier
  updateDossier(id: string, dossier: any): Observable<any> {
    const payload = {
      numDossier: parseInt(id, 10),
      fkEmailUtilisateur: dossier.fkEmailUtilisateur,
      patientId: parseInt(dossier.fkIdPatient, 10),
      date: dossier.date,
    };
    return this.http.put<any>(`${this.baseUrl}/dossiers/${id}`, payload); // Assuming PUT method for updating a dossier
  }

  // Delete a dossier
  deleteDossier(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/dossiers/${id}`); // Assuming DELETE method for removing a dossier
  }

  // Récupérer tous les examens
  getExamins(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/examins`).pipe(
      tap(examins => console.log('Données reçues:', examins)),
      map(examins =>
        examins.map(examin => ({
          id: examin.id ? examin.id.toString() : null,
          fkNumDossier: examin.fkNumDossier ? examin.fkNumDossier.toString() : null,
          fkIdEpreuve: examin.fkIdEpreuve ? examin.fkIdEpreuve.toString() : null,
          fkIdTestAnalyse: examin.fkIdTestAnalyse ? examin.fkIdTestAnalyse.toString() : null,
          resultat: examin.resultat || null
        }))
      )
    );
  }



  // Ajouter un nouvel examen
  addExamin(examen: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/examins`, examen);
  }

  // Mettre à jour un examen existant
  updateExamin(id: string, examen: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/examins/${id}`, examen);
  }

  // Supprimer un examen
  deleteExamin(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/examins/${id}`);
  }

  getUtilisateurs(): Observable<any[]> {
    return this.http.get<any[]>(`${this.springUrl}/utilisateurs`);
  }

}
