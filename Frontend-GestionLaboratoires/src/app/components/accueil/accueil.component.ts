import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzFormControlComponent, NzFormDirective, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzInputDirective} from "ng-zorro-antd/input";
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzGridModule } from 'ng-zorro-antd/grid';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NzAvatarComponent} from 'ng-zorro-antd/avatar';
import {ChartModule, UIChart} from 'primeng/chart';
import {AreaChartComponent} from '../charts/area-chart/area-chart.component';
import {BarChartComponent} from '../charts/bar-chart/bar-chart.component';
import {NgIf} from '@angular/common';
import {NzSpinComponent} from 'ng-zorro-antd/spin';
import {NzEmptyComponent} from 'ng-zorro-antd/empty';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzDropDownDirective, NzDropdownMenuComponent} from 'ng-zorro-antd/dropdown';
import {NzMenuDirective, NzMenuItemComponent} from 'ng-zorro-antd/menu';
import {NzCarouselComponent, NzCarouselContentDirective} from 'ng-zorro-antd/carousel';
import {NzTableComponent} from 'ng-zorro-antd/table';
import {NzProgressComponent} from 'ng-zorro-antd/progress';
import {NzDescriptionsComponent, NzDescriptionsItemComponent} from 'ng-zorro-antd/descriptions';
import {UserService} from '../../services/user.service';



interface ItemData {
  id: string;
  email: string;
  password: string;
  name: string;
  role: string;
}

@Component({
  selector: 'app-accueil',
  standalone: true,
  imports: [
    FormsModule,
    NzColDirective,
    NzFormControlComponent,
    NzFormDirective,
    NzFormLabelComponent,
    NzInputDirective,
    NzRowDirective,
    ReactiveFormsModule,
    NzDividerModule,
    NzGridModule,
    NzIconDirective,
    NzAvatarComponent,
    UIChart,
    AreaChartComponent,
    BarChartComponent,
    NgIf,
    NzSpinComponent,
    NzEmptyComponent,
    NzSelectComponent,
    NzOptionComponent,
    NzButtonComponent,
    NzDropDownDirective,
    NzDropdownMenuComponent,
    NzMenuDirective,
    NzMenuItemComponent,
    NzCarouselComponent,
    NzCarouselContentDirective,
    NzTableComponent,
    NzProgressComponent,
    NzDescriptionsComponent,
    NzDescriptionsItemComponent
  ],
  templateUrl: './accueil.component.html',
  styleUrl: './accueil.component.css'
})
export class AccueilComponent {
  isDataLoaded = false;
  array = [1, 2, 3, 4];
  listOfData = [
    {
      patientNom: 'FAREH Abdallah',
      dossierNum: 32,
      analyseNom: 'amalyse de sang'
    }
  ];
  lisOfUsers: ItemData[] = [];
  lisOfPatients: ItemData[] = [];


  constructor(private cdr: ChangeDetectorRef, private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getUsers().subscribe(users => {
      this.lisOfUsers = users;
      this.lisOfPatients = this.lisOfUsers.filter(item => item.role === 'patient');
    })
    // Simulate data loading with a delay
    setTimeout(() => {
      this.isDataLoaded = true;
    }, 500); // Adjust delay as necessary
  }

  ngAfterViewInit(): void {
    // Trigger change detection to apply styles
    this.cdr.detectChanges();
  }


}
