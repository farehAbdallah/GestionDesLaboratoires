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

    ],
  },
];
