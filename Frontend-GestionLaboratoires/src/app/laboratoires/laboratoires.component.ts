import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormsModule,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { CommonModule } from '@angular/common';

import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconDirective } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzRowDirective, NzColDirective } from 'ng-zorro-antd/grid';
import { NzModalComponent, NzModalContentDirective, NzModalFooterDirective, NzModalService } from 'ng-zorro-antd/modal';
import { NzFormControlComponent, NzFormDirective, NzFormLabelComponent } from 'ng-zorro-antd/form';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzUploadChangeParam, NzUploadModule } from 'ng-zorro-antd/upload';
import { NzUploadFile } from 'ng-zorro-antd/upload';
import { NzTagModule } from 'ng-zorro-antd/tag';


interface LaboratoryData {
  id: string;
  name: string;
  nrc: string;
  logo: string;
  dateActivation: Date | null;
  isActive: boolean;
}

@Component({
  selector: 'app-laboratoires',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NzButtonModule,
    NzInputModule,
    NzPopconfirmModule,
    NzTableModule,
    NzDatePickerModule,
    NzRowDirective,
    NzColDirective,
    NzIconDirective,
    NzModalComponent,
    NzModalContentDirective,
    NzFormControlComponent,
    NzFormLabelComponent,
    ReactiveFormsModule,
    NzFormDirective,
    NzModalFooterDirective,
    NzRadioModule,
    NzUploadModule,
    NzTagModule,
    
  ],
  providers: [NzModalService],
  templateUrl: './laboratoires.component.html',
  styleUrls: ['./laboratoires.component.css']
})
export class LaboratoiresComponent implements OnInit {
  i = 0;
  editId: string | null = null;
  listOfData: LaboratoryData[] = [];
  originalData: LaboratoryData | null = null;
  searchName: string = '';
  searchNrc: string = '';

  isVisible = false;
  isOkLoading = false;

  filteredData: LaboratoryData[] = [];

  fileList: NzUploadFile[] = [];  // Liste des fichiers téléchargés
  logoFileName: string = ''; // Nom du fichier sélectionné

  validateForm: FormGroup;

  constructor(private modalService: NzModalService, private fb: NonNullableFormBuilder, private message: NzMessageService) {
    this.validateForm = this.fb.group({
      name: this.fb.control('', [Validators.required]),
      nrc: this.fb.control('', [Validators.required]),
      logo: this.fb.control('', [Validators.required]),
      dateActivation: this.fb.control(null, [Validators.required]),
      isActive: this.fb.control(true)
    });
  }

  ngOnInit(): void {
    this.addRow(); // Add some initial rows for demonstration
    this.filteredData = [...this.listOfData];
  }

  startEdit(id: string): void {
    this.editId = id;
    const currentData = this.listOfData.find(item => item.id === id);
    if (currentData) {
      this.originalData = { ...currentData };
    }
  }

  saveEdit(): void {
    this.editId = null;
    this.originalData = null;
    this.message.success('Laboratoire modifié avec succès');
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
    this.filterData();
  }

  addRow(): void {
    const newRow = {
      id: `${this.i}`,
      name: `Laboratoire ${this.i}`,
      nrc: `NRC-${this.i}`,
      logo: `Logo-${this.i}.png`,
      dateActivation: new Date(),
      isActive: true
    };
    this.listOfData = [...this.listOfData, newRow];
    this.i++;
    this.filterData();
  }

  deleteRow(id: string): void {
    this.listOfData = this.listOfData.filter(d => d.id !== id);
    this.filterData();
    this.message.success('Laboratoire supprimé avec succès');
  }

  filterData(): void {
    this.filterDataByName();
    this.filterDataByNrc();
  }

  filterDataByName(): void {
    if (this.searchName) {
      this.filteredData = this.listOfData.filter(item =>
        item.name.toLowerCase().includes(this.searchName.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData];
    }
  }

  filterDataByNrc(): void {
    if (this.searchNrc) {
      this.filteredData = this.listOfData.filter(item =>
        item.nrc.toLowerCase().includes(this.searchNrc.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData];
    }
  }

  onSearchNrcChange(): void {
    this.filterDataByNrc();
  }

  onSearchNameChange(): void {
    this.filterDataByName();
  }

  showModal(): void {
    this.validateForm.reset();
    this.isVisible = true;
  }

  handleCancel(): void {
    this.isVisible = false;
    this.validateForm.reset();
  }

  handleOk(): void {
    this.submitForm();
  }
  
  submitForm(): void {
    if (this.validateForm.valid) {
      this.isOkLoading = true;
  
      const newRow: LaboratoryData = {
        id: `${this.i}`, // ID unique pour chaque laboratoire
        name: this.validateForm.value.name,
        nrc: this.validateForm.value.nrc,
        logo: this.validateForm.value.logo,
        dateActivation: this.validateForm.value.dateActivation,
        isActive: this.validateForm.value.isActive
      };
  
      // Ajoutez le nouvel élément à `listOfData`
      this.listOfData = [...this.listOfData, newRow];
  
      // Mettez à jour `filteredData` pour refléter les nouvelles données
      this.filterData();
  
      setTimeout(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.message.success('Laboratoire ajouté avec succès');
      }, 2000);
  
      this.i++; // Incrémentez l'identifiant pour les futurs laboratoires
      this.validateForm.reset(); // Réinitialisez le formulaire après l'ajout
      this.logoFileName = ''; // Réinitialisez le nom du fichier
    } else {
      // Marquez les champs invalides comme sales pour afficher les erreurs
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }
  
  
  handleFileInput(event: any): void {
    const file = event.file.originFileObj;
    if (file) {
      this.logoFileName = file.name; // Mettre à jour le nom du fichier
      const reader = new FileReader();
      reader.onload = () => {
        this.validateForm.patchValue({ logo: reader.result as string });
      };
      reader.readAsDataURL(file);
    }
  }

  updateLogo(data: any, event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const reader = new FileReader();
      reader.onload = () => {
        data.logo = reader.result as string; // Update logo URL
      };
      reader.readAsDataURL(input.files[0]);
    }
  }

  handleUploadChange(event: NzUploadChangeParam): void {
    if (event.file && event.file.originFileObj) {
      const input = { target: { files: [event.file.originFileObj] } } as unknown as Event;
      this.updateLogo(this.editId ? this.listOfData.find(item => item.id === this.editId) : null, input);
    }
  }
  
  
  
  
}
