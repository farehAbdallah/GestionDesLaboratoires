// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
//
// @Injectable({
//   providedIn: 'root'
// })
// export class AnalyseService {
//   private baseUrl = 'http://localhost:3000'; // URL pour accéder aux analyses dans json-server
//
//   constructor(private http: HttpClient) {}
//
//   // Récupérer toutes les analyses
//   getAnalyses(): Observable<any[]> {
//     return this.http.get<any[]>(`${this.baseUrl}/analyses`);
//   }
//
//   // Ajouter une nouvelle analyse
//   addAnalyse(analyse: any): Observable<any> {
//     return this.http.post<any>(`${this.baseUrl}/analyses`, analyse);
//   }
//
//   // Mettre à jour une analyse existante
//   updateAnalyse(id: string, analyse: any): Observable<any> {
//     return this.http.put<any>(`${this.baseUrl}/analyses/${id}`, analyse);
//   }
//
//   // Supprimer une analyse
//   deleteAnalyse(id: string): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/analyses/${id}`);
//   }
//
//   // Récupérer toutes les épreuves (epreuves)
//   getEpreuves(): Observable<any[]> {
//     return this.http.get<any[]>(`${this.baseUrl}/epreuves`);
//   }
//
//   // Ajouter une nouvelle épreuve
//   addEpreuve(epreuve: any): Observable<any> {
//     return this.http.post<any>(`${this.baseUrl}/epreuves`, epreuve);
//   }
//
//   // Mettre à jour une épreuve existante
//   updateEpreuve(id: string, epreuve: any): Observable<any> {
//     return this.http.put<any>(`${this.baseUrl}/epreuves/${id}`, epreuve);
//   }
//
//   // Supprimer une épreuve
//   deleteEpreuve(id: string): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/epreuves/${id}`);
//   }
//
//   // Récupérer tous les tests
//   getTests(): Observable<any[]> {
//     return this.http.get<any[]>(`${this.baseUrl}/tests`);
//   }
//
//   // Ajouter un nouveau test
//   addTest(test: any): Observable<any> {
//     return this.http.post<any>(`${this.baseUrl}/tests`, test);
//   }
//
//   // Mettre à jour un test existant
//   updateTest(id: string, test: any): Observable<any> {
//     return this.http.put<any>(`${this.baseUrl}/tests/${id}`, test);
//   }
//
//   // Supprimer un test
//   deleteTest(id: string): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/tests/${id}`);
//   }
//
//
//
//   getLaboratoires(): Observable<any[]> {
//     return this.http.get<any[]>(`${this.baseUrl}/laboratoires`);
//   }
// }

//-------------------------------------------------------------------------------------------------------------------------------
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AnalyseService {
  private baseUrl = 'http://localhost:8083/api'; // URL de l'API Spring Boot
  private springBootUrl = 'http://localhost:8085/api'; // URL for the Spring Boot API


  constructor(private http: HttpClient) {}

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

  // Récupérer toutes les analyses
  getAnalyses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/analyses`).pipe(
      map((analyses) =>
        analyses.map((analyse) => ({
          id: analyse.id.toString(), // Convertir ID en chaîne
          name: analyse.nom, // Renommer 'nom' en 'name'
          description: analyse.description,
          laboratoireId: analyse.fkIdLaboratoire.toString() // Convertir fkIdLaboratoire en chaîne
        }))
      )
    );
  }

  // Ajouter une nouvelle analyse
  addAnalyse(analyse: any): Observable<any> {
    const payload = {
      // id: parseInt(analyse.id, 10), // Convertir ID en nombre
      nom: analyse.name, // Renommer 'name' en 'nom'
      description: analyse.description,
      fkIdLaboratoire: parseInt(analyse.laboratoireId, 10) // Convertir laboratoireId en nombre
    };
    return this.http.post<any>(`${this.baseUrl}/analyses`, payload);
  }

  // Mettre à jour une analyse existante
  updateAnalyse(id: string, analyse: any): Observable<any> {
    const payload = {
      id: parseInt(analyse.id, 10), // Convertir ID en nombre
      nom: analyse.name, // Renommer 'name' en 'nom'
      description: analyse.description,
      epreuves: [],
      fkIdLaboratoire: parseInt(analyse.laboratoireId, 10) // Convertir laboratoireId en nombre
    };
    console.log("data li ghatsift l spring : ", payload)
    return this.http.put<any>(`${this.baseUrl}/analyses/${id}`, payload);
  }

  // Supprimer une analyse
  deleteAnalyse(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/analyses/${id}`);
  }

  // Récupérer toutes les épreuves
  getEpreuves(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/epreuves`).pipe(
      map((epreuves) =>
        epreuves.map((epreuve) => ({
          id: epreuve.id.toString(), // Convertir ID en chaîne
          nom: epreuve.nom, // Renommer 'nom' en 'name'
          analyseId: epreuve.fkIdAnalyse ? epreuve.fkIdAnalyse.toString() : null // Convertir fkIdAnalyse en chaîne
        }))
      )
    );
  }

  // Ajouter une nouvelle épreuve
  addEpreuve(epreuve: any): Observable<any> {
    const payload = {
      // id: parseInt(epreuve.id, 10), // Convertir ID en nombre
      nom: epreuve.nom, // Renommer 'name' en 'nom'
      fkIdAnalyse: epreuve.analyseId ? parseInt(epreuve.analyseId, 10) : null // Convertir analyseId en nombre
    };
    return this.http.post<any>(`${this.baseUrl}/epreuves`, payload);
  }

  // Mettre à jour une épreuve existante
  updateEpreuve(id: string, epreuve: any): Observable<any> {
    const payload = {
      id: parseInt(epreuve.id, 10), // Convertir ID en nombre
      nom: epreuve.nom, // Renommer 'name' en 'nom'
      fkIdAnalyse: epreuve.analyseId ? parseInt(epreuve.analyseId, 10) : null // Convertir analyseId en nombre
    };
    return this.http.put<any>(`${this.baseUrl}/epreuves/${id}`, payload);
  }

  // Supprimer une épreuve
  deleteEpreuve(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/epreuves/${id}`);
  }

  // Récupérer tous les tests
  getTests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/test-analyses`)
      .pipe(
      map((tests) =>
        tests.map((test) => ({
          id: test.id.toString(), // Convertir ID en chaîne
          nomTest: test.nomTest, // Renommer 'nom' en 'name'
          analyseId: test.fkIdAnalyse.toString(),
          sousEpreuve: test.sousEpreuve,
          intervalMinDeReference: test.intervalMinDeReference,
          intervalMaxDeReference: test.intervalMaxDeReference,
          uniteDeReference: test.uniteDeReference,
          details: test.details
        }))
      )
    );
  }

  // Ajouter un nouveau test
  addTest(test: any): Observable<any> {
    const payload = {

      id: parseInt(test.id, 10), // Convertir ID en nombre
      nomTest: test.nomTest, // Renommer 'name' en 'nom'
      sousEpreuve: test.sousEpreuve,
      intervalMinDeReference: test.intervalMinDeReference,
      intervalMaxDeReference: test.intervalMaxDeReference,
      uniteDeReference: test.uniteDeReference,
      details: test.details,
      fkIdAnalyse: parseInt(test.analyseId, 10)
    };
    return this.http.post<any>(`${this.baseUrl}/test-analyses`, payload);
  }

  // Mettre à jour un test existant


  updateTest(id: string, test: any): Observable<any> {
    const payload = {
      id: parseInt(test.id, 10), // Convertir ID en nombre
      nomTest: test.nomTest, // Renommer 'name' en 'nom'
      sousEpreuve: test.sousEpreuve,
      intervalMinDeReference: test.intervalMinDeReference,
      intervalMaxDeReference: test.intervalMaxDeReference,
      uniteDeReference: test.uniteDeReference,
      details: test.details,
      fkIdAnalyse: parseInt(test.analyseId, 10)
    };
    return this.http.put<any>(`${this.baseUrl}/test-analyses/${id}`, payload);
  }

  // Supprimer un test
  deleteTest(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/test-analyses/${id}`);
  }

  // // Récupérer tous les laboratoires
  // getLaboratoires(): Observable<any[]> {
  //   return this.http.get<any[]>(`${this.baseUrl}/laboratoires`).pipe(
  //     map((laboratoires) =>
  //       laboratoires.map((laboratoire) => ({
  //         id: laboratoire.id.toString(), // Convertir ID en chaîne
  //         name: laboratoire.nom // Renommer 'nom' en 'name'
  //       }))
  //     )
  //   );
  // }
}


