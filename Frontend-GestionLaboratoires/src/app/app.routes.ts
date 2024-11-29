import { Routes } from '@angular/router';
import {AccueilComponent} from './components/accueil/accueil.component';
import {UtilisateursComponent} from './components/utilisateurs/utilisateurs.component';

export const routes: Routes = [
  {path: '', redirectTo: 'accueil', pathMatch: 'full' },
  {path: 'accueil', component: AccueilComponent},
  {path: 'utilisateurs', component: UtilisateursComponent}
];
