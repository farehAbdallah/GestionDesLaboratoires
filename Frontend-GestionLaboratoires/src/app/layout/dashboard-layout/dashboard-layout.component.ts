import {Component, OnInit} from '@angular/core';

import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import {ActivatedRoute, NavigationEnd, Router, RouterLink, RouterOutlet} from '@angular/router';
import {NzRowDirective} from 'ng-zorro-antd/grid';
import {filter, map} from 'rxjs';
import {NzAvatarComponent} from 'ng-zorro-antd/avatar';
import {NzDropDownDirective, NzDropdownMenuComponent} from 'ng-zorro-antd/dropdown';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {LoginService} from '../../services/login.service';
import {NgIf} from '@angular/common';
import {LaboratoireService} from '../../services/laboratoires.service';

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [NzBreadCrumbModule, NzIconModule, NzMenuModule, NzLayoutModule, RouterOutlet, RouterLink, NzRowDirective, NzDropDownDirective, NzDropdownMenuComponent, NzButtonComponent, NgIf],
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.css'],

})
export class DashboardLayoutComponent implements OnInit{
  isCollapsed = false;

  logguedUser: any;

  selectedLabo: any;

  pageTitle: string = 'Gestion Des Laboratoires';

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private loginService: LoginService, private laboService: LaboratoireService) {

    this.loginService.getLoged().subscribe(user => { this.logguedUser = user; console.log("testing loged user", user)})
  }

  ngOnInit(): void {
    // this.laboService.setSelectedLabo('');
    if(this.isRouteActive('labo/laboratoires')) {
      this.laboService.setSelectedLabo('');
    }

    this.loginService.getLoged().subscribe(user => { this.logguedUser = user; console.log("testing loged user wa cherif", user)});
    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationEnd),
        map(() => {
          let route = this.activatedRoute;
          while (route.firstChild) {
            route = route.firstChild;
          }
          return route.snapshot.data['title'] || '';
        })
      )
      .subscribe((title: string) => {
        this.pageTitle = title;
      });

  }

  isRouteActive(route: string): boolean {
    return this.router.url === route;
  }

  logOut() {
    this.loginService.logOut();
    this.router.navigate(['login']);
  }

  isTechnicien() {
    return this.hasRole('technicien');
  }

  isAdmin() {
    return this.hasRole('administrateur');
  }

  isEmploye() {
    return this.hasRole('employee');
  }

  hasRole(role: string): boolean {
    return this.logguedUser && this.logguedUser.role === role;
  }

  getSelectedLaboNavigation(){
    return this.laboService.getSelectedLabo();
  }
  releaseLabo(){
    this.laboService.setSelectedLabo('');
  }


}
