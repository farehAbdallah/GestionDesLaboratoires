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
import { LaboratoireService } from '../../services/laboratoires.service';
import {Router, RouterLink} from '@angular/router'; // Import the service


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
    RouterLink

  ],
  providers: [NzModalService],
  templateUrl: './laboratoires.component.html',
  styleUrls: ['./laboratoires.component.css']
})
export class LaboratoiresComponent implements OnInit {
  editId: string | null = null;
  listOfData: LaboratoryData[] = [];
  originalData: LaboratoryData | null = null;
  filteredData: LaboratoryData[] = [];
  validateForm: FormGroup;
  isVisible = false;
  isOkLoading = false;
  searchName = '';
  searchNrc = '';
  logoFileName = '';

  constructor(
    private fb: NonNullableFormBuilder,
    private laboratoireService: LaboratoireService, // Inject service
    private message: NzMessageService,
    private router: Router
  ) {
    this.validateForm = this.fb.group({
      name: ['', [Validators.required]],
      nrc: ['', [Validators.required]],
      logo: ['', [Validators.required]],
      dateActivation: [null, [Validators.required]],
      isActive: [true]
    });
  }

  ngOnInit(): void {
    this.loadLaboratoires();
  }

  // Load laboratoires from backend (db.json)
  loadLaboratoires(): void {
    this.laboratoireService.getLaboratoires().subscribe(
      (data) => {
        this.listOfData = data;
        this.filteredData = [...this.listOfData];
      });
  }

  filterData(): void {
    this.filteredData = this.listOfData.filter(
      (item) =>
        item.name.toLowerCase().includes(this.searchName.toLowerCase()) &&
        item.nrc.toLowerCase().includes(this.searchNrc.toLowerCase())
    );
  }

  onSearchNameChange(): void {
    this.filterData();
  }

  onSearchNrcChange(): void {
    this.filterData();
  }

  showModal(): void {
    this.validateForm.reset();
    this.logoFileName = '';
    this.isVisible = true;
  }

  // Start editing an existing laboratory
  startEdit(id: string): void {
    this.editId = id;
    const currentData = this.listOfData.find((item) => item.id === id);

    if (currentData) {
      this.originalData = { ...currentData };
    }
  }

  // Save edited laboratory
  saveEdit(): void {
    if (this.editId && this.originalData) {
      const updatedLaboratoire = this.listOfData.find(item => item.id === this.editId);
      // Update the user on the server
      this.laboratoireService.updateLaboratoire(this.editId, updatedLaboratoire).subscribe(() => {
        this.editId = null;
        this.originalData = null;
        this.loadLaboratoires(); // Refresh the list after update
        this.message.success('Laboratoire modifié avec succès');
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
    this.loadLaboratoires(); // Refresh the list after update
  }

  // Delete laboratory
  deleteRow(id: string): void {
    this.laboratoireService.deleteLaboratoire(id).subscribe(() => {
        this.loadLaboratoires();
        this.message.success('Laboratoire supprimé avec succès');
      });

  }

  handleCancel(): void {
    this.isVisible = false;
    this.validateForm.reset();
  }

  submitForm(): void {
    if (this.validateForm.valid) {
      const newRow: LaboratoryData = {
        id: `${Date.now()}`,
        ...this.validateForm.value
      };
      this.laboratoireService.addLaboratoire(newRow).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadLaboratoires(); // Reload users after adding
        this.message.success('Laboratoire ajouté avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
  }

  // Handle logo file upload
  handleFileInput(event: NzUploadChangeParam): void {
    const file = event.file.originFileObj;
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.logoFileName = file.name;

        this.validateForm.patchValue({ logo: file.name });
        // this.validateForm.patchValue({ logo: reader.result as string });
      };
      reader.readAsDataURL(file);
    }
  }

  // Handle upload change
  handleUploadChange(event: NzUploadChangeParam): void {
    if (event.file && event.file.originFileObj) {
      const input = { target: { files: [event.file.originFileObj] } } as unknown as Event;
      this.updateLogo(this.editId ? this.listOfData.find(item => item.id === this.editId) : null, input);
    }
  }

  // Update logo URL in the form when file is uploaded

  updateLogo(data: any, event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      const file = input.files[0]; // Safely access the first file
      const reader = new FileReader();

      reader.onload = () => {
        if (file) {
          data.logo = file.name; // Update logo URL with file name
          // Optionally use Base64 content:
          // data.logo = reader.result as string;
        }
      };

      reader.readAsDataURL(file); // Read the file as Base64
    } else {
      console.error("No file selected or input.files is null.");
    }
  }


  navigateToLabo(id: string) {
    this.laboratoireService.setSelectedLabo(id);
    this.router.navigate(['labo', id, 'profilelabo']);
  }
  getSelectedLabo() {
    return this.laboratoireService.getSelectedLabo();
  }


}
