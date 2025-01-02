import { Component, OnInit } from '@angular/core';
import { FormGroup, FormsModule, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalComponent, NzModalContentDirective, NzModalFooterDirective, NzModalService } from 'ng-zorro-antd/modal';
import { CommonModule } from '@angular/common';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzColDirective, NzRowDirective } from 'ng-zorro-antd/grid';
import { NzIconDirective } from 'ng-zorro-antd/icon';
import { NzFormControlComponent, NzFormDirective, NzFormLabelComponent } from 'ng-zorro-antd/form';
import { NzTagComponent } from 'ng-zorro-antd/tag';
import { NzOptionComponent, NzSelectComponent } from 'ng-zorro-antd/select';
import { LaboratoireService } from '../../services/laboratoires.service';

interface ContactLaboratoireData {
  id: string;
  numTel: string;
  fax: string;
  email: string;
  laboratoireId: string;
  adresseId: string;
}

@Component({
  selector: 'app-contactsLaboratoires',
  standalone: true,
  imports: [
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
  templateUrl: './contacts-laboratoires.component.html',
  styleUrls: ['./contacts-laboratoires.component.css']
})
export class ContactsLaboratoiresComponent implements OnInit {
  i = 0;
  editId: string | null = null;
  listOfData: ContactLaboratoireData[] = [];
  originalData: ContactLaboratoireData | null = null;
  isVisible = false;
  isOkLoading = false;
  filteredData: ContactLaboratoireData[] = [];
  validateForm: FormGroup;
  searchLaboratoireId: string = '';
  searchAdresseId: string = '';
  listOfLaboratoires: any[] = [];
  listOfAdresses: any[] = [];

  constructor(private fb: NonNullableFormBuilder, private message: NzMessageService, private contactLaboratoireService: LaboratoireService) {
    this.validateForm = this.fb.group({
      numTel: ['', [Validators.required]],
      fax: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      laboratoireId: ['', [Validators.required]],
      adresseId: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadLaboratoires();
    this.loadAdresses();
    this.loadContactsLaboratoires();
  }

  loadContactsLaboratoires(): void {
    this.contactLaboratoireService.getContactsLaboratoires().subscribe((contactsLaboratoires: any) => {
      this.listOfData = contactsLaboratoires;
      this.filteredData = [...this.listOfData];
    });
  }

  loadLaboratoires(): void {
    this.contactLaboratoireService.getLaboratoires().subscribe(laboratoires => {
      this.listOfLaboratoires = laboratoires;
      this.filteredData = [...this.listOfData];
    });
  }

  loadAdresses(): void {
    this.contactLaboratoireService.getAdresses().subscribe(adresses => {
      this.listOfAdresses = adresses;
      this.filteredData = [...this.listOfData];
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
      const updatedContactLaboratoire = this.listOfData.find(item => item.id === this.editId);
    // console.log("original data testiiiiiing : ",updatedContactLaboratoire)
      // Update the epreuve on the server
      this.contactLaboratoireService.updateContactLaboratoire(this.editId, updatedContactLaboratoire).subscribe(() => {
        this.editId = null;
        this.originalData = null;
        this.loadContactsLaboratoires(); // Refresh the list after update
        this.message.success('Contact laboratoire modifié avec succès');
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
    this.loadContactsLaboratoires(); // Refresh the list after update
  }

  deleteRow(id: string): void {
    this.contactLaboratoireService.deleteContactLaboratoire(id).subscribe(() => {
      this.loadContactsLaboratoires(); // Reload the epreuves after deletion
      this.message.success('Contact Laboratoire supprimé avec succès');
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
      const newContactLaboratoire: ContactLaboratoireData = {
        id: `${Date.now()}`,
        ...this.validateForm.value
      };
      this.contactLaboratoireService.addContactLaboratoire(newContactLaboratoire).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadContactsLaboratoires();
        this.message.success('Contact laboratoire ajouté avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
  }

  filterDataByLaboratoireId(): void {
    if (this.searchLaboratoireId) {
      this.filteredData = this.listOfData.filter(item => {
        const laboratoire = this.listOfLaboratoires.find(lab => lab.id === item.laboratoireId);
        return laboratoire && laboratoire.name.toLowerCase().includes(this.searchLaboratoireId.toLowerCase());
      });
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  filterDataByAdresseId(): void {
    if (this.searchAdresseId) {
      this.filteredData = this.listOfData.filter(item => {
        const adresse = this.listOfAdresses.find(addr => addr.id === item.adresseId);
        return adresse && adresse.ville.toLowerCase().includes(this.searchAdresseId.toLowerCase());
      });
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  onSearchLaboratoireIdChange(): void {
    this.filterDataByLaboratoireId();
  }

  onSearchAdresseIdChange(): void {
    this.filterDataByAdresseId();
  }

  getNomLaboratoire(id: string): string | undefined {
    const laboratoire = this.listOfLaboratoires.find(laboratoire => laboratoire.id === id);
    return laboratoire ? laboratoire.name : undefined;


  }

  getVilleAdresse(id: string): string | undefined {
    const adresse = this.listOfAdresses.find(adresse => adresse.id === id);
    return adresse ? adresse.ville : undefined;
  }
}
