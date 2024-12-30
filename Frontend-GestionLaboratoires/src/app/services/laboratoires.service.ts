import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ActivatedRoute} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LaboratoireService {
  selectedLabo: string | null= null;

  private baseUrl = 'http://localhost:3000'; // URL for the json-server

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  getLaboratoireId(): string | null {

    // Extract the 'id' from the route parameters
    const laboId = this.route.snapshot.paramMap.get('id');

    // Optionally, save the id in the laboratoireService
    if (laboId) {
      this.setSelectedLabo(laboId);
      alert("labo selected")
    }
    return laboId;
  }

  setSelectedLabo(laboId: string) {
    this.selectedLabo = laboId;
  }
  getSelectedLabo() {
    return this.selectedLabo;
  }

  // *** Laboratoires ***

  // Get all laboratoires
  getLaboratoires(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/laboratoires`);
  }

  // Get laboratoire by id
  getLaboratoireById(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/laboratoires/${id}`);
  }


  // Add a new laboratoire
  addLaboratoire(laboratoire: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/laboratoires`, laboratoire);
  }

  // Update an existing laboratoire
  updateLaboratoire(id: string | null, laboratoire: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/laboratoires/${id}`, laboratoire);
  }

  // Delete a laboratoire
  deleteLaboratoire(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/laboratoires/${id}`);
  }

  // *** Adresses ***

  // Get all adresses
  getAdresses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/adresses`);
  }

  // Add a new adresse
  addAdresse(adresse: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/adresses`, adresse);
  }

  // Update an existing adresse
  updateAdresse(id: string, adresse: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/adresses/${id}`, adresse);
  }

  // Delete an adresse
  deleteAdresse(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/adresses/${id}`);
  }

  // *** Contacts Laboratoires ***

  // Get all contacts laboratoires
  getContactsLaboratoires(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/contactsLaboratoires`);
  }

  // Add a new contact laboratoire
  addContactLaboratoire(contactLaboratoire: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/contactsLaboratoires`, contactLaboratoire);
  }

  // Update an existing contact laboratoire
  updateContactLaboratoire(id: string, contactLaboratoire: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/contactsLaboratoires/${id}`, contactLaboratoire);
  }

  // Delete a contact laboratoire
  deleteContactLaboratoire(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/contactsLaboratoires/${id}`);
  }
}
