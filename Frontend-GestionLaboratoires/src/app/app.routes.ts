import { provideRouter, Routes } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { AccueilComponent } from './components/accueil/accueil.component';
import { UtilisateursComponent } from './components/utilisateurs/utilisateurs.component';
import { LoginComponent } from './components/login/login.component';

import { DashboardLayoutComponent } from './layout/dashboard-layout/dashboard-layout.component';
import { loginGuard } from './guards/login.guard';
import {roleGuard} from './guards/role.guard';
import {AnalysesComponent} from './components/analyses/analyses.component';
import {EpreuvesComponent} from './components/epreuves/epreuves.component';
import {TestsComponent} from './components/tests/tests.component';
import {PatientsComponent} from './components/patients/patients.component';
import {DossiersComponent} from './components/dossiers/dossiers.component';
import {ExaminsComponent} from './components/examins/examins.component';
import {LaboratoiresComponent} from './components/laboratoires/laboratoires.component';
import {AdressesComponent} from './components/adresses/adresses.component';
import {ContactsLaboratoiresComponent} from './components/contacts-laboratoires/contacts-laboratoires.component';




export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, data: { title: 'Log In' } },
  {
    path: 'labo',
    component: DashboardLayoutComponent,
    canActivate: [loginGuard],  // Ensure user is logged in
    children: [

      { path: '', redirectTo: 'accueil', pathMatch: 'full' },
      {
        path: 'accueil',
        component: AccueilComponent,
        data: { title: 'Accueil', requiredRole: ['administrateur'] },  // Only accessible to 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'utilisateurs',
        component: UtilisateursComponent,
        data: { title: 'Gestion des Utilisateurs', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'laboratoires',
        component: LaboratoiresComponent,
        data: { title: 'Gestion des Laboratoires', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'contacts-laboratoires',
        component: ContactsLaboratoiresComponent,
        data: { title: 'Gestion des Contacts laboratoires', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'adresses',
        component: AdressesComponent,
        data: { title: 'Gestion des Adresses', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'analyses',
        component: AnalysesComponent,
        data: { title: 'Gestion des Analyses', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'epreuves',
        component: EpreuvesComponent,
        data: { title: 'Gestion des Epreuves', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'tests',
        component: TestsComponent,
        data: { title: 'Gestion des Tests', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'patients',
        component: PatientsComponent,
        data: { title: 'Gestion des Patients', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'dossiers',
        component: DossiersComponent,
        data: { title: 'Gestion des Dossiers', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'examens',
        component: ExaminsComponent,
        data: { title: 'Gestion des Examens', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },

    ],
  },
];
