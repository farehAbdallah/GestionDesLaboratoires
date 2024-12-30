import { Component, OnInit } from '@angular/core';
import {FormGroup, FormsModule, NonNullableFormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import {NzModalComponent, NzModalContentDirective, NzModalFooterDirective, NzModalService} from 'ng-zorro-antd/modal';
import {CommonModule} from '@angular/common';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzPopconfirmModule} from 'ng-zorro-antd/popconfirm';
import {NzTableModule} from 'ng-zorro-antd/table';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NzFormControlComponent, NzFormDirective, NzFormLabelComponent} from 'ng-zorro-antd/form';
import {NzTagComponent} from 'ng-zorro-antd/tag';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';
import {UserService} from '../../services/user.service';
import {AnalyseService} from '../../services/analyses.service';
import {LaboratoireService} from '../../services/laboratoires.service';
import {ActivatedRoute} from '@angular/router';
import {LoginService} from '../../services/login.service';

interface ItemData {
  id: string;
  email: string;
  password: string;
  name: string;
  role: string;
  fkIdLaboratoire: string;
  profession: string;
  signature: string;
}

@Component({
  selector: 'app-utilisateurs',
  standalone: true,
  imports: [
    CommonModule,
    CommonModule,
    NzButtonModule,
    NzInputModule,
    NzPopconfirmModule,
    NzTableModule,
    NzRowDirective,
    NzIconDirective,
    NzModalComponent,
    NzModalContentDirective,
    NzFormControlComponent,
    NzFormLabelComponent,
    ReactiveFormsModule,
    NzColDirective,
    NzFormDirective,
    NzModalFooterDirective,
    NzTagComponent,
    FormsModule,
    NzSelectComponent,
    NzOptionComponent
  ],
  providers: [NzModalService],
  templateUrl: './utilisateurs.component.html',
  styleUrls: ['./utilisateurs.component.css']
})
export class UtilisateursComponent implements OnInit {
  i = 0;
  editId: string | null = null;
  listOfData: ItemData[] = [];
  listOfEmployees: ItemData[] = [];
  originalData: ItemData | null = null;
  isVisible = false;
  isOkLoading = false;
  filteredData: ItemData[] = [];
  validateForm: FormGroup;
  searchName: string = '';  // Property for search text
  searchEmail: string = '';  // Property for search text
  searchRole: string = ''; // Selected role for filtering
  listOfLaboratoires: any[] = [];
  laboratoireId: string | null = null;



  constructor(private fb: NonNullableFormBuilder,
              private message: NzMessageService,
              private userService: UserService,
              private authService: LoginService,
              private analyseService: AnalyseService,
              private laboratoireService: LaboratoireService,
              private route: ActivatedRoute
  ) {
    this.validateForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      role: ['', [Validators.required]],
      // fkIdLaboratoire: ['', [Validators.required]],
      profession: ['', [Validators.required]],
      signature: ['', [Validators.required]]
    });

  }

  ngOnInit(): void {
    this.laboratoireId = this.route.snapshot.paramMap.get('id');

    // Optionally, save the id in the laboratoireService
    if (this.laboratoireId) {
      this.laboratoireService.setSelectedLabo(this.laboratoireId);
    }
    // this.laboratoireId = this.laboratoireService.getSelectedLabo();
    this.loadUsers();
    this.loadLaboratoires();


  }

  loadLaboratoires(): void {
    this.analyseService.getLaboratoires().subscribe((laboratoires: any) => {
      this.listOfLaboratoires = laboratoires; // Charger la liste des laboratoires
      // console.log("list labo",this.listOfLaboratoires)
    });
  }

  getNomLaboratoire(id: string): string | undefined {
    const laboratoire = this.listOfLaboratoires.find(labo => labo.id === id);
    return laboratoire ? laboratoire.name : undefined;
  }

  loadUsers(): void {
    this.userService.getUsers().subscribe(users => {
      this.listOfData = users;
      this.filteredData = [...this.listOfData]; // Initialize filtered data
      this.filterDataByLaboratoire();

    });
    this.authService.getLoged().subscribe(user => {
      const loguedUser = user;
      if (loguedUser.role === 'administrateur'){
        this.loadEmploye();
      }
    })
  }
  loadEmploye(): void {
    this.filteredData = this.filteredData.filter(employe=> employe.role === 'employee' )
  }

  startEdit(id: string): void {
    this.editId = id;
    const currentData = this.listOfData.find(item => item.id === id);
    if (currentData) {
      // Store original data to allow canceling edits
      this.originalData = { ...currentData };
    }
  }

  saveEdit(): void {
    if (this.editId && this.originalData) {
      const updatedUser = this.listOfData.find(item => item.id === this.editId);
      // Update the user on the server
      this.userService.updateUser(this.editId, updatedUser).subscribe(() => {
        this.editId = null;
        this.originalData = null;
        this.loadUsers(); // Refresh the list after update
        this.message.success('Utilisateur modifié avec succès');
      });
    }
  }

  cancelEdit(): void {
    // Revert the changes to the original data
    if (this.editId && this.originalData) {
      const index = this.listOfData.findIndex(item => item.id === this.editId);
      if (index > -1) {
        this.listOfData[index] = { ...this.originalData };
      }
    }
    this.editId = null;
    this.originalData = null;
    this.loadUsers(); // Refresh the list after update
  }


  deleteRow(id: string): void {
    this.userService.deleteUser(id).subscribe(() => {
      this.loadUsers(); // Reload the users after deletion
      this.message.success('Utilisateur supprimé avec succès');
    });
  }

  showModal(): void {
    this.validateForm.reset();
    this.isVisible = true;
  }

  handleCancel(): void {
    this.isVisible = false;
    this.validateForm.reset();
  }

  submitForm(): void {
    if (this.validateForm.valid) {
      const newRow: ItemData = {
        id: `${Date.now()}`,
        ...this.validateForm.value,
        fkIdLaboratoire : this.laboratoireId
      };
      this.userService.addUser(newRow).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadUsers(); // Reload users after adding
        this.message.success('Utilisateur ajouté avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
  }



  getRoleColor(role: string): string {
    switch (role) {
      case 'administrateur':
        return 'green'; // Color for "admin"
      case 'employee':
        return 'cyan'; // Color for "employee"
      case 'technicien':
        return 'red'; // Color for "patient"
      default:
        return 'gray'; // Default color for undefined roles
    }
  }

  filterDataByLaboratoire(): void {
    if (this.laboratoireId) {
      this.filteredData = this.listOfData.filter(item =>
        item.fkIdLaboratoire === this.laboratoireId
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }


  filterDataByName(): void {
    if (this.searchName) {
      this.filteredData = this.listOfData.filter(item =>
        item.name.toLowerCase().includes(this.searchName.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  // Filter data based on search by Addresse
  filterDataByEmail(): void {
    if (this.searchEmail) {
      this.filteredData = this.listOfData.filter(item =>
        item.email.toLowerCase().includes(this.searchEmail.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  filterDataByRole(): void {
    if (this.searchRole) {
      this.filteredData = this.listOfData.filter(item =>
        item.role.toLowerCase().includes(this.searchRole.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  // Update search text and trigger filtering
  onSearchEmailChange(): void {
    this.filterDataByEmail();
  }
  onSearchNameChange(): void {
    this.filterDataByName();
  }
  onSearchRoleChange(): void {
    this.filterDataByRole();
  }

}
