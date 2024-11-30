import { Routes } from '@angular/router';
import {AccueilComponent} from './components/accueil/accueil.component';
import {UtilisateursComponent} from './components/utilisateurs/utilisateurs.component';
import {LoginComponent} from './components/login/login.component';

export const routes: Routes = [
  {path: '', redirectTo: 'accueil', pathMatch: 'full' },
  {path: 'accueil', component: AccueilComponent, data: { title: 'Accueil' },},
  {path: 'utilisateurs', component: UtilisateursComponent, data: { title: 'Gestion des Utilisateurs' },},
  {path: 'login', component: LoginComponent, data: { title: 'Log In' },}
];
