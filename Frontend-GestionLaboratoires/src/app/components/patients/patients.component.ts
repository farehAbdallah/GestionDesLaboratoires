import { Component, OnInit } from '@angular/core';
import { FormGroup, FormsModule, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import {NzModalComponent, NzModalContentDirective, NzModalFooterDirective, NzModalService} from 'ng-zorro-antd/modal';
import { CommonModule } from '@angular/common';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzTableModule } from 'ng-zorro-antd/table';
import {PatientService} from '../../services/patients.service';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NzFormControlComponent, NzFormDirective, NzFormLabelComponent} from 'ng-zorro-antd/form';
import {NzTagComponent} from 'ng-zorro-antd/tag';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';
import {NzDividerComponent} from 'ng-zorro-antd/divider';
import {NzDescriptionsComponent, NzDescriptionsItemComponent} from 'ng-zorro-antd/descriptions';
import {NzDrawerComponent, NzDrawerContentDirective} from 'ng-zorro-antd/drawer';
import {NzListComponent, NzListItemComponent, NzListItemMetaComponent} from 'ng-zorro-antd/list';


interface PatientData {
  id: string;
  nomComplet: string;
  dateNaissance: string;
  lieuNaissance: string;
  sexe: string;
  typePieceIdentite: string;
  numPieceIdentite: string;
  adresse: string;
  numTel: string;
  email: string;
  visible_pour: string;
}

@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [
    CommonModule,
    NzButtonModule,
    NzInputModule,
    NzPopconfirmModule,
    NzTableModule,
    ReactiveFormsModule,
    FormsModule,
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
    NzDividerComponent,
    NzDescriptionsComponent,
    NzDrawerComponent,
    NzDescriptionsItemComponent,
    NzListComponent,
    NzListItemComponent,
    NzListItemMetaComponent,
    NzDrawerContentDirective
  ],
  providers: [NzModalService],
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {
  editId: string | null = null;
  listOfData: PatientData[] = [];
  originalData: PatientData | null = null;
  isVisible = false;
  isOkLoading = false;
  filteredData: PatientData[] = [];
  validateForm: FormGroup;
  searchName: string = '';  // Property for search text
  visible = false;
  selectedPatient: any = null;

  isModalVisible = false; //for edit



  constructor(
    private fb: NonNullableFormBuilder,
    private message: NzMessageService,
    private patientService: PatientService
  ) {
    this.validateForm = this.fb.group({
      nomComplet: ['', [Validators.required]],
      dateNaissance: ['', [Validators.required]],
      lieuNaissance: ['', [Validators.required]],
      sexe: ['', [Validators.required]],
      typePieceIdentite: ['', [Validators.required]],
      numPieceIdentite: ['', [Validators.required]],
      adresse: ['', [Validators.required]],
      numTel: ['', [Validators.required]],
      email: ['', [Validators.email]],
      visible_pour: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients(): void {
    this.patientService.getPatients().subscribe(patients => {
      this.listOfData = patients;
      this.filteredData = [...this.listOfData];
    });
  }


  showEditModal(patient: PatientData): void {
    // Populate the form with patient data
    this.editId = patient.id;
    this.validateForm.patchValue({
      nomComplet: patient.nomComplet,
      dateNaissance: patient.dateNaissance,
      lieuNaissance: patient.lieuNaissance,
      sexe: patient.sexe,
      typePieceIdentite: patient.typePieceIdentite,
      numPieceIdentite: patient.numPieceIdentite,
      adresse: patient.adresse,
      numTel: patient.numTel,
      email: patient.email,
      visible_pour: patient.visible_pour
    });

    // Show the modal
    this.isModalVisible = true;
  }

  handleModalOk(): void {
    if (this.validateForm.valid) {
      const updatedPatient: PatientData = {
        id: this.editId!,
        ...this.validateForm.value
      };
      this.patientService.updatePatient(updatedPatient.id, updatedPatient).subscribe(() => {
        this.isOkLoading = false;
        this.isModalVisible = false;
        this.loadPatients();
        this.message.success('Patient mis à jour avec succès');
      });
    }
  }

  handleModalCancel(): void {
    this.isModalVisible = false;
  }

  startEdit(id: string): void {
    this.editId = id;
    const currentData = this.listOfData.find(item => item.id === id);
    if (currentData) {
      this.originalData = { ...currentData };
    }
  }

  saveEdit(): void {
    if (this.editId && this.originalData) {
      const updatedPatient = this.listOfData.find(item => item.id === this.editId);
      this.patientService.updatePatient(this.editId, updatedPatient).subscribe(() => {
        this.editId = null;
        this.originalData = null;
        this.loadPatients();
        this.message.success('Patient modifié avec succès');
      });
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
    this.loadPatients();
  }

  deleteRow(id: string): void {
    this.patientService.deletePatient(id).subscribe(() => {
      this.loadPatients();
      this.message.success('Patient supprimé avec succès');
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
      const newPatient: PatientData = {
        id: `${Date.now()}`,
        ...this.validateForm.value
      };
      console.log(this.validateForm)
      this.patientService.addPatient(newPatient).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadPatients();
        this.message.success('Patient ajouté avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
  }

  filterDataByName(): void {
    if (this.searchName) {
      this.filteredData = this.listOfData.filter(item =>
        item.nomComplet.toLowerCase().includes(this.searchName.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  onSearchNameChange(): void {
    this.filterDataByName();
  }


  openDrawer(patient: any): void {
    this.selectedPatient = patient;
    this.visible = true;
  }

  closeDrawer(): void {
    this.visible = false;
    this.selectedPatient = null;
  }
}
