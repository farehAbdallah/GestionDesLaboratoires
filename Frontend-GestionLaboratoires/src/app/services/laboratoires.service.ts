// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import {ActivatedRoute} from '@angular/router';
//
// @Injectable({
//   providedIn: 'root'
// })
// export class LaboratoireService {
//   selectedLabo: string | null= null;
//
//   private baseUrl = 'http://localhost:3000'; // URL for the json-server
//
//   constructor(private http: HttpClient, private route: ActivatedRoute) {}
//
//   getLaboratoireId(): string | null {
//
//     // Extract the 'id' from the route parameters
//     const laboId = this.route.snapshot.paramMap.get('id');
//
//     // Optionally, save the id in the laboratoireService
//     if (laboId) {
//       this.setSelectedLabo(laboId);
//       alert("labo selected")
//     }
//     return laboId;
//   }
//
//   setSelectedLabo(laboId: string) {
//     this.selectedLabo = laboId;
//   }
//   getSelectedLabo() {
//     return this.selectedLabo;
//   }
//
//   // *** Laboratoires ***
//
//   // Get all laboratoires
//   getLaboratoires(): Observable<any[]> {
//     return this.http.get<any[]>(`${this.baseUrl}/laboratoires`);
//   }
//
//   // Get laboratoire by id
//   getLaboratoireById(id: string): Observable<any> {
//     return this.http.get<any>(`${this.baseUrl}/laboratoires/${id}`);
//   }
//
//
//   // Add a new laboratoire
//   addLaboratoire(laboratoire: any): Observable<any> {
//     return this.http.post<any>(`${this.baseUrl}/laboratoires`, laboratoire);
//   }
//
//   // Update an existing laboratoire
//   updateLaboratoire(id: string | null, laboratoire: any): Observable<any> {
//     return this.http.put<any>(`${this.baseUrl}/laboratoires/${id}`, laboratoire);
//   }
//
//   // Delete a laboratoire
//   deleteLaboratoire(id: string): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/laboratoires/${id}`);
//   }
//
//   // *** Adresses ***
//
//   // Get all adresses
//   getAdresses(): Observable<any[]> {
//     return this.http.get<any[]>(`${this.baseUrl}/adresses`);
//   }
//
//   // Add a new adresse
//   addAdresse(adresse: any): Observable<any> {
//     return this.http.post<any>(`${this.baseUrl}/adresses`, adresse);
//   }
//
//   // Update an existing adresse
//   updateAdresse(id: string, adresse: any): Observable<any> {
//     return this.http.put<any>(`${this.baseUrl}/adresses/${id}`, adresse);
//   }
//
//   // Delete an adresse
//   deleteAdresse(id: string): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/adresses/${id}`);
//   }
//
//   // *** Contacts Laboratoires ***
//
//   // Get all contacts laboratoires
//   getContactsLaboratoires(): Observable<any[]> {
//     return this.http.get<any[]>(`${this.baseUrl}/contactsLaboratoires`);
//   }
//
//   // Add a new contact laboratoire
//   addContactLaboratoire(contactLaboratoire: any): Observable<any> {
//     return this.http.post<any>(`${this.baseUrl}/contactsLaboratoires`, contactLaboratoire);
//   }
//
//   // Update an existing contact laboratoire
//   updateContactLaboratoire(id: string, contactLaboratoire: any): Observable<any> {
//     return this.http.put<any>(`${this.baseUrl}/contactsLaboratoires/${id}`, contactLaboratoire);
//   }
//
//   // Delete a contact laboratoire
//   deleteContactLaboratoire(id: string): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/contactsLaboratoires/${id}`);
//   }
// }
//
//
//
// ---------------------------------------------------------------------------------------------------------



import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LaboratoireService {
  selectedLabo: string | null = null;

  private springBootUrl = 'http://localhost:8085/api'; // URL for the Spring Boot API
  private springBootUrlNoApi = 'http://localhost:8085'; // URL for the Spring Boot API

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  getLaboratoireId(): string | null {
    // Extract the 'id' from the route parameters
    const laboId = this.route.snapshot.paramMap.get('id');

    // Optionally, save the id in the laboratoireService
    if (laboId) {
      this.setSelectedLabo(laboId);
      alert("labo selected");
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

  // Get all laboratoires from Spring API and adapt to JSON server format
  getLaboratoires(): Observable<any[]> {
    return this.http.get<any[]>(`${this.springBootUrl}/laboratoires`).pipe(
      map(laboratoires =>
        laboratoires.map(labo => ({
          id: labo.id.toString(),
          name: labo.nom,  // Rename `nom` to `name`
          logo: labo.logo,
          nrc: labo.nrc,
          dateActivation: labo.dateActivation,
          isActive: labo.active, // Rename `active` to `isActive`
          contacts: [] // Since contacts are not available in Spring API, leave it empty
        }))
      )
    );
  }

  // Get laboratoire by id from Spring API and adapt to JSON server format
  getLaboratoireById(id: string): Observable<any> {

    const labId = parseInt(id, 10);
    return this.http.get<any>(`${this.springBootUrl}/laboratoires/${labId}`).pipe(
      map(labo => ({
        id: labo.id.toString(),
        name: labo.nom,  // Rename `nom` to `name`
        logo: labo.logo,
        nrc: labo.nrc,
        dateActivation: labo.dateActivation,
        isActive: labo.active, // Rename `active` to `isActive`
        contacts: [] // No contacts available in Spring API, so set as empty
      }))
    );
  }

  // Add a new laboratoire to the Spring API
  addLaboratoire(laboratoire: any): Observable<any> {
    const payload = {
      // id: parseInt(laboratoire.id, 10),
      nom: laboratoire.name,  // Rename `nom` to `name`
      logo: laboratoire.logo,
      nrc: laboratoire.nrc,
      dateActivation: laboratoire.dateActivation,
      active: laboratoire.isActive, // Rename `active` to `isActive`
      contacts: laboratoire.contacts // No contacts available in Spring API, so set as empty
    };
    return this.http.post<any>(`${this.springBootUrl}/laboratoires`, payload);
  }

  // Update an existing laboratoire in the Spring API
  updateLaboratoire(id: string | null, laboratoire: any): Observable<any> {
    const payload = {
      id: parseInt(laboratoire.id, 10),
      nom: laboratoire.name,  // Rename `nom` to `name`
      logo: laboratoire.logo,
      nrc: laboratoire.nrc,
      dateActivation: laboratoire.dateActivation,
      active: laboratoire.isActive, // Rename `active` to `isActive`
      contacts: laboratoire.contacts // No contacts available in Spring API, so set as empty
    };
    return this.http.put<any>(`${this.springBootUrl}/laboratoires/${id}`, payload);
  }

  // Delete a laboratoire from the Spring API
  deleteLaboratoire(id: string): Observable<any> {
    return this.http.delete<any>(`${this.springBootUrl}/laboratoires/${id}`);
  }

  // *** Adresses ***

  // Get all adresses from Spring API and adapt to JSON server format
  getAdresses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.springBootUrlNoApi}/adresses`).pipe(
      map(adresses =>
        adresses.map(adresse => ({
          id: adresse.id.toString(), // Convert the ID to string
          numVoie: adresse.numVoie,
          nomVoie: adresse.nomVoie,
          codePostal: adresse.codePostal,
          ville: adresse.ville,
          commune: adresse.commune
        }))
      )
    );
  }

  // Add a new adresse to the Spring API
  addAdresse(adresse: any): Observable<any> {
    const payload = {
      // id: parseInt(adresse.id, 10), // Convert the ID to number
      numVoie: adresse.numVoie,
      nomVoie: adresse.nomVoie,
      codePostal: adresse.codePostal,
      ville: adresse.ville,
      commune: adresse.commune
    };
    return this.http.post<any>(`${this.springBootUrlNoApi}/adresses`, payload);
  }

  // Update an existing adresse in the Spring API
  updateAdresse(id: string, adresse: any): Observable<any> {
    const payload = {
      id: parseInt(adresse.id, 10), // Convert the ID to number
      numVoie: adresse.numVoie,
      nomVoie: adresse.nomVoie,
      codePostal: adresse.codePostal,
      ville: adresse.ville,
      commune: adresse.commune
    };
    return this.http.put<any>(`${this.springBootUrlNoApi}/adresses/${id}`, payload);
  }

  // Delete an adresse from the Spring API
  deleteAdresse(id: string): Observable<any> {
    return this.http.delete<any>(`${this.springBootUrlNoApi}/adresses/${id}`);
  }

  // *** Contacts Laboratoires ***

  // Get all contacts laboratoires (this will fetch from Spring API and adapt to JSON server format)
  getContactsLaboratoires(): Observable<any[]> {
    return this.http.get<any[]>(`${this.springBootUrlNoApi}/contacts`).pipe(
      map(contacts =>
        contacts.map(contact => ({
          id: contact.id.toString(),
          numTel: contact.numTel,
          fax: contact.fax,
          email: contact.email,
          laboratoireId: contact.laboratoireId.toString(), // Convert to string
          adresseId: contact.adresseId.toString() // Convert to string
        }))
      )
    );
  }

  // Add a new contact laboratoire to the Spring API
  addContactLaboratoire(contactLaboratoire: any): Observable<any> {
    const payload = {
      // id: parseInt(contactLaboratoire.id, 10),
      numTel: contactLaboratoire.numTel,
      fax: contactLaboratoire.fax,
      email: contactLaboratoire.email,
      laboratoireId: parseInt(contactLaboratoire.laboratoireId, 10),
      adresseId: parseInt(contactLaboratoire.adresseId, 10)
    };
    return this.http.post<any>(`${this.springBootUrlNoApi}/contacts`, payload);
  }

  // Update an existing contact laboratoire in the Spring API
  updateContactLaboratoire(id: string, contactLaboratoire: any): Observable<any> {
    // const payload = {
    //   id: parseInt(contactLaboratoire.id, 10),
    //   numTel: contactLaboratoire.numTel,
    //   fax: contactLaboratoire.fax,
    //   email: contactLaboratoire.email,
    //   laboratoireId: parseInt(contactLaboratoire.laboratoireId, 10),
    //   adresseId: parseInt(contactLaboratoire.adresseId, 10)
    // };
    return this.http.put<any>(`${this.springBootUrlNoApi}/contacts/${id}`, contactLaboratoire);
  }

  // Delete a contact laboratoire from the Spring API
  deleteContactLaboratoire(id: string): Observable<any> {
    return this.http.delete<any>(`${this.springBootUrlNoApi}/contacts/${id}`);
  }
}
