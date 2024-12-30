import { Component, OnInit } from '@angular/core';
import { FormGroup, FormsModule, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import {NzModalComponent, NzModalContentDirective, NzModalFooterDirective, NzModalService} from 'ng-zorro-antd/modal';
import {PatientService} from '../../services/patients.service';
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
import {ActivatedRoute} from '@angular/router';
import {LaboratoireService} from '../../services/laboratoires.service';
import {LoginService} from '../../services/login.service'; // Assuming you have a service for dossiers

interface DossierData {
  id: string;
  numDossier: string;
  fkEmailUtilisateur: string;
  fkIdPatient: string;
  date: string;
}

@Component({
  selector: 'app-dossiers',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
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
    NzOptionComponent,
  ],
  providers: [NzModalService],
  templateUrl: './dossiers.component.html',
  styleUrls: ['./dossiers.component.css']
})
export class DossiersComponent implements OnInit {
  listOfData: DossierData[] = [];
  originalData: DossierData | null = null;
  isVisible = false;
  isOkLoading = false;
  validateForm: FormGroup;
  editId: string | null = null;
  filteredData: DossierData[] = [];
  listOfUtilisateurs: any[] = [];
  listOfPatients: any[] = [];
  laboratoireId: string | null = null;
  loguedUser: any;
  actionPermission: boolean = false;
  allowedRoles: string[] = ['administrateur', 'employee']



  // Filtering properties
  searchNumDossier: string = '';
  searchPatientId: string = '';
  searchDate: string = '';


  constructor(private fb: NonNullableFormBuilder,
              private message: NzMessageService,
              private dossierService: PatientService,
              private route: ActivatedRoute,
              private laboratoireService: LaboratoireService,
              private loginService: LoginService
              ) {
    this.validateForm = this.fb.group({
      numDossier: ['', [Validators.required]],
      fkEmailUtilisateur: ['', [Validators.required]],
      fkIdPatient: ['', [Validators.required]],
      date: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {


    this.loginService.getLoged().subscribe(user => {
      if (user && ( this.allowedRoles.includes(user.role) ) ) {
        this.actionPermission = true;
      }
      else {
        this.actionPermission = false;
      }
    })
    this.laboratoireId = this.route.snapshot.paramMap.get('id');

    console.log("actionPermission : ", this.loginService.isAdmin())
    if (this.laboratoireId) {
      this.laboratoireService.setSelectedLabo(this.laboratoireId);
    }
    this.loadUtilisateurs();
    this.loadDossiers();
    this.loadPatients();

  }



  loadDossiers(): void {

    this.loginService.getLoged().subscribe(user => {
      if (user.role === 'employee'){
        this.dossierService.getDossiers().subscribe(dossiers => {
          if (this.laboratoireId) {
            this.listOfData = dossiers.filter(dossier =>
              this.listOfUtilisateurs.some(utilisateur => utilisateur.id === user.id && utilisateur.email === dossier.fkEmailUtilisateur && utilisateur.fkIdLaboratoire === this.laboratoireId)
            );
          } else {
            this.listOfData = dossiers; // No filtering if no laboratoireId
          }
          this.filteredData = [...this.listOfData]; // Initialize filtered data
        });
      }
      else {
        this.dossierService.getDossiers().subscribe(dossiers => {
          if (this.laboratoireId) {
            this.listOfData = dossiers.filter(dossier =>
              this.listOfUtilisateurs.some(utilisateur => utilisateur.email === dossier.fkEmailUtilisateur && utilisateur.fkIdLaboratoire === this.laboratoireId)
            );
          } else {
            this.listOfData = dossiers; // No filtering if no laboratoireId
          }
          this.filteredData = [...this.listOfData]; // Initialize filtered data
        });
      }


    });




  }

  loadUtilisateurs(): void {
    this.dossierService.getUtilisateurs().subscribe((utilisateurs: any) => {
      this.listOfUtilisateurs = utilisateurs;
      this.filterUtilisateurByLaboratoire();
      // console.log("list utilisateurs",this.listOfUtilisateurs)
    });
  }

  filterUtilisateurByLaboratoire(): void {
    if (this.laboratoireId) {
      this.listOfUtilisateurs = this.listOfUtilisateurs.filter(item =>
        item.fkIdLaboratoire === this.laboratoireId
      );
    }
  }

  loadPatients(): void {
    this.dossierService.getPatients().subscribe((patients: any) => {
      this.listOfPatients = patients; // Charger la liste des laboratoires
    });
  }

  getEmailUtilisateurs(id: string): string | undefined {
    const utilisateur = this.listOfUtilisateurs.find(user => user.id === id);
    return utilisateur ? utilisateur.email : undefined;
  }
  getNomPatient(id: string): string | undefined {
    const patients = this.listOfPatients.find(patient => patient.id === id);
    return patients ? patients.nomComplet : undefined;
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
      const updatedDossier = this.listOfData.find(item => item.id === this.editId);
      if (updatedDossier) {
        this.dossierService.updateDossier(this.editId, updatedDossier).subscribe(() => {
          this.editId = null;
          this.originalData = null;
          this.loadDossiers();
          this.message.success('Dossier modifié avec succès');
        });
      }
    }
  }

  cancelEdit(): void {
    if (this.editId && this.originalData) {
      const index = this.listOfData.findIndex(item => item.id === this.editId);
      if (index > -1) {
        this.listOfData[index] = { ...this.originalData };
      }
    }
    this.editId = null;
    this.originalData = null;
    this.loadDossiers();
  }

  deleteRow(id: string): void {
    this.dossierService.deleteDossier(id).subscribe(() => {
      this.loadDossiers();
      this.message.success('Dossier supprimé avec succès');
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
      const newDossier: DossierData = {
        id: `${Date.now()}`,
        ...this.validateForm.value
      };
      this.dossierService.addDossier(newDossier).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadDossiers();
        this.message.success('Dossier ajouté avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
  }

  filterDataByNumDossier(): void {
    if (this.searchNumDossier) {
      this.filteredData = this.listOfData.filter(item =>
        item.numDossier.toLowerCase().includes(this.searchNumDossier.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  filterDataByPatientId(): void {
    if (this.searchPatientId) {
      this.filteredData = this.listOfData.filter(item =>
        this.getNomPatient(item.fkIdPatient)?.toLowerCase()?.includes(this.searchPatientId)
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  filterDataByDate(): void {
    if (this.searchDate) {
      this.filteredData = this.listOfData.filter(item =>
        item.date.toLowerCase().includes(this.searchDate.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  onSearchNumDossierChange(): void {
    this.filterDataByNumDossier();
  }

  onSearchPatientIdChange(): void {
    this.filterDataByPatientId();
  }

  onSearchDateChange(): void {
    this.filterDataByDate();
  }
}
