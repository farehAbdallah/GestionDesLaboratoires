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
import {LaboratoireService} from '../../services/laboratoires.service';

interface AdresseData {
  id: string;
  numVoie: string;
  nomVoie: string;
  codePostal: string;
  ville: string;
  commune: string;
}

@Component({
  selector: 'app-adresses',
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
  templateUrl: './adresses.component.html',
  styleUrls: ['./adresses.component.css']
})
export class AdressesComponent implements OnInit {
  i = 0;
  editId: string | null = null;
  listOfData: AdresseData[] = [];
  originalData: AdresseData | null = null;
  isVisible = false;
  isOkLoading = false;
  filteredData: AdresseData[] = [];
  validateForm: FormGroup;
  searchVille: string = '';  // Property for search text
  searchCommune: string = '';  // Property for search text



  constructor(private fb: NonNullableFormBuilder, private message: NzMessageService, private adresseService: LaboratoireService) {
    this.validateForm = this.fb.group({
      numVoie: ['', [Validators.required]],
      nomVoie: ['', [Validators.required]],
      codePostal: ['', [Validators.required]],
      ville: ['', [Validators.required]],
      commune: ['', [Validators.required]]
    });

  }

  ngOnInit(): void {
    this.loadAdresses();
  }

  loadAdresses(): void {
    this.adresseService.getAdresses().subscribe(adresses => {
      this.listOfData = adresses;
      this.filteredData = [...this.listOfData]; // Initialize filtered data
    });
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
      const updatedAdresse = this.listOfData.find(item => item.id === this.editId);
      // Update the user on the server
      this.adresseService.updateAdresse(this.editId, updatedAdresse).subscribe(() => {
        this.editId = null;
        this.originalData = null;
        this.loadAdresses(); // Refresh the list after update
        this.message.success('Adresse modifié avec succès');
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
    this.loadAdresses(); // Refresh the list after update
  }


  deleteRow(id: string): void {
    this.adresseService.deleteAdresse(id).subscribe(() => {
      this.loadAdresses(); // Reload the users after deletion
      this.message.success('Adresse supprimée avec succès');
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
      const newRow: AdresseData = {
        id: `${Date.now()}`,
        ...this.validateForm.value
      };
      this.adresseService.addAdresse(newRow).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadAdresses(); // Reload users after adding
        this.message.success('Adresse ajoutée avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
  }


  filterDataByVille(): void {
    if (this.searchVille) {
      this.filteredData = this.listOfData.filter(item =>
        item.ville.toLowerCase().includes(this.searchVille.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  filterDataByCommune(): void {
    if (this.searchCommune) {
      this.filteredData = this.listOfData.filter(item =>
        item.commune.toLowerCase().includes(this.searchCommune.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  onSearchVilleChange(): void {
    this.filterDataByVille();
  }
  onSearchCommuneChange(): void {
    this.filterDataByCommune();
  }
}
