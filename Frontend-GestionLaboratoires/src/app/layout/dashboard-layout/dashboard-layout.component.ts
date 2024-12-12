import { Component } from '@angular/core';

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

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [NzBreadCrumbModule, NzIconModule, NzMenuModule, NzLayoutModule, RouterOutlet, RouterLink, NzRowDirective, NzAvatarComponent, NzDropDownDirective, NzDropdownMenuComponent, NzButtonComponent],
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.css'],

})
export class DashboardLayoutComponent {
  isCollapsed = false;

  username: any;

  pageTitle: string = 'Gestion Des Laboratoires';

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private loginService: LoginService) {

    this.loginService.getLoged().subscribe(user => { this.username = user.name; console.log("testing loged user", user)})
  }

  ngOnInit(): void {
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

  isAdmin() {
    return this.loginService.hasRole('administrateur');
  }

  isPatient() {
    return this.loginService.hasRole('patient');
  }

  isEmploye() {
    return this.loginService.hasRole('employee');
  }

}
