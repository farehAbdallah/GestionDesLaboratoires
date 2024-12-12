import { Routes } from '@angular/router';
import { AccueilComponent } from './components/accueil/accueil.component';
import { UtilisateursComponent } from './components/utilisateurs/utilisateurs.component';
import { LoginComponent } from './components/login/login.component';
import {DashboardLayoutComponent} from './layout/dashboard-layout/dashboard-layout.component';
import { LaboratoiresComponent } from './components/laboratoires/laboratoires.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, data: { title: 'Log In' } },
  {
    path: '',
    component: DashboardLayoutComponent,
    children: [
      { path: 'accueil', component: AccueilComponent, data: { title: 'Accueil' } },
      { path: 'utilisateurs', component: UtilisateursComponent, data: { title: 'Gestion des Utilisateurs' } },
      { path: 'laboratoires', component: LaboratoiresComponent, data: { title: 'Gestion des Laboratoires' } }
    ],
  },
];
