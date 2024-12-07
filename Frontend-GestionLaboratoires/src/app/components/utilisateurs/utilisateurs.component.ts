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
import { NzIconDirective, NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzTableModule } from 'ng-zorro-antd/table';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {NzModalComponent, NzModalContentDirective, NzModalFooterDirective, NzModalService} from 'ng-zorro-antd/modal';
import {NzFormControlComponent, NzFormDirective, NzFormLabelComponent} from 'ng-zorro-antd/form';
import {NzMessageService} from 'ng-zorro-antd/message';

interface ItemData {
  id: string;
  name: string;
  age: string;
  address: string;
}

@Component({
  selector: 'app-utilisateurs',
  standalone: true,
  imports: [
    FormsModule,
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
    NzModalFooterDirective
  ],
  providers: [NzModalService],
  templateUrl: './utilisateurs.component.html',
  styleUrls: ['./utilisateurs.component.css']
})
export class UtilisateursComponent implements OnInit {
  i = 0;
  editId: string | null = null;
  listOfData: ItemData[] = [];
  originalData: ItemData | null = null;
  searchName: string = '';  // Property for search text
  searchAddresse: string = '';  // Property for search text

  isVisible = false;
  isOkLoading = false;

  // Filtered list that will be shown in the table
  filteredData: ItemData[] = [];

  // Form for adding new data
  validateForm: FormGroup;

  constructor(private modalService: NzModalService, private fb: NonNullableFormBuilder, private message: NzMessageService) {
    this.validateForm = this.fb.group({
      name: this.fb.control('', [Validators.required]),
      address: this.fb.control('', [Validators.required]),
      age: this.fb.control(0)
    });
  }



  ngOnInit(): void {
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();
    this.addRow();

    this.filteredData = [...this.listOfData]; // Initialize filtered data
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
    this.message.success('Utilisateur modifie avec suuces')
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
      name: `Fareh Abdallah ${this.i}`,
      age: '23',
      address: `Maroc, Khouribga no. ${this.i}`
    };
    this.listOfData = [...this.listOfData, newRow];
    this.i++;
    this.filterData();  // Filter the data after adding a row
  }

  deleteRow(id: string): void {
    this.listOfData = this.listOfData.filter(d => d.id !== id);
    this.filterData();  // Filter the data after deleting a row
    this.message.success('Utilisateur Supprimer avec succes');
  }

  filterData(): void{
    this.filterDataByName();
    this.filterDataByAddresse();
  }

  // Filter data based on search by Name
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
  filterDataByAddresse(): void {
    if (this.searchAddresse) {
      this.filteredData = this.listOfData.filter(item =>
        item.address.toLowerCase().includes(this.searchAddresse.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  // Update search text and trigger filtering
  onSearchAddresseChange(): void {
    this.filterDataByAddresse();
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







  submitForm(): void {
    if (this.validateForm.valid) {
      this.isOkLoading = true
      console.log('submit', this.validateForm.value);
      const newRow = {
        id: `${this.i}`,
        ...this.validateForm.value
      };
      this.listOfData = [...this.listOfData, newRow];
      setTimeout(() => {
        this.isOkLoading = false
        this.isVisible = false
        this.filterData();
        this.message.success('Utilisateur Ajoute avec succes')

      }, 2000);
      this.i++;


    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }


  handleOk() {
    this.submitForm()


  }
}
