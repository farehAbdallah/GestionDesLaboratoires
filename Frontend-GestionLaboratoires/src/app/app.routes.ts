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
import {LaboprofileComponent} from './components/laboprofile/laboprofile.component';




export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, data: { title: 'Log In' } },

  {
    path: 'labo',
    component: DashboardLayoutComponent,
    canActivate: [loginGuard],  // Ensure user is logged in
    children: [

      // { path: '', redirectTo: 'accueil', pathMatch: 'full' },
      { path: '', redirectTo: 'laboratoires', pathMatch: 'full' },
      // { path: ':id', redirectTo: ':id/profilelabo', pathMatch: 'full' },
      {
        path: ':id/profilelabo',
        component: LaboprofileComponent,
        data: { title: 'Profile Laboratoire', requiredRole: ['technicien'] },  // Only accessible to 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: 'accueil',
        component: AccueilComponent,
        data: { title: 'Accueil', requiredRole: ['technicien'] },  // Only accessible to 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: ':id/utilisateurs',
        component: UtilisateursComponent,
        data: { title: 'Gestion des Utilisateurs', requiredRole: ['administrateur'] },  // Accessible to 'employee' and 'administrateur'
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
        path: ':id/analyses',
        component: AnalysesComponent,
        data: { title: 'Gestion des Analyses', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: ':id/epreuves',
        component: EpreuvesComponent,
        data: { title: 'Gestion des Epreuves', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: ':id/tests',
        component: TestsComponent,
        data: { title: 'Gestion des Tests', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: ':id/patients',
        component: PatientsComponent,
        data: { title: 'Gestion des Patients', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: ':id/dossiers',
        component: DossiersComponent,
        data: { title: 'Gestion des Dossiers', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },
      {
        path: ':id/examens',
        component: ExaminsComponent,
        data: { title: 'Gestion des Examens', requiredRole: ['employee', 'administrateur'] },  // Accessible to 'employee' and 'administrateur'
        // canActivate: [roleGuard],
      },

    ],
  },
];
