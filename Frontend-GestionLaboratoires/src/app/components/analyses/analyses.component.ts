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
import {AnalyseService} from '../../services/analyses.service';

interface AnalyseData {
  id: string;
  name: string;
  description: string;
  laboratoireId: string;
}

@Component({
  selector: 'app-analyses',
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
  templateUrl: './analyses.component.html',
  styleUrls: ['./analyses.component.css']
})
export class AnalysesComponent implements OnInit {
  i = 0;
  editId: string | null = null;
  listOfData: AnalyseData[] = [];
  originalData: AnalyseData | null = null;
  isVisible = false;
  isOkLoading = false;
  filteredData: AnalyseData[] = [];
  validateForm: FormGroup;
  searchName: string = '';  // Property for search text
  searchDescription: string = '';  // Property for search text
  searchLaboratoire: string = ''; // Selected type for filtering
  listOfLaboratoires: any[] = [];

  constructor(private fb: NonNullableFormBuilder, private message: NzMessageService, private analyseService: AnalyseService) {
    this.validateForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      laboratoireId: ['', [Validators.required]] // Default value set to "biologique"
    });
  }

  ngOnInit(): void {
    this.loadAnalyses();
    this.loadLaboratoires();
  }

  loadLaboratoires(): void {
    this.analyseService.getLaboratoires().subscribe((laboratoires: any) => {
      this.listOfLaboratoires = laboratoires; // Charger la liste des laboratoires
      console.log("list labo",this.listOfLaboratoires)
    });
  }

  loadAnalyses(): void {
    this.analyseService.getAnalyses().subscribe(analyses => {
      this.listOfData = analyses;
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
      const updatedAnalyse = this.listOfData.find(item => item.id === this.editId);
      // Update the analyse on the server
      this.analyseService.updateAnalyse(this.editId, updatedAnalyse).subscribe(() => {
        this.editId = null;
        this.originalData = null;
        this.loadAnalyses(); // Refresh the list after update
        this.message.success('Analyse modifiée avec succès');
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
    this.loadAnalyses(); // Refresh the list after update
  }

  deleteRow(id: string): void {
    this.analyseService.deleteAnalyse(id).subscribe(() => {
      this.loadAnalyses(); // Reload the analyses after deletion
      this.message.success('Analyse supprimée avec succès');
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
      const newAnalyse: AnalyseData = {
        id: `${Date.now()}`,
        ...this.validateForm.value
      };
      this.analyseService.addAnalyse(newAnalyse).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadAnalyses(); // Reload analyses after adding
        this.message.success('Analyse ajoutée avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
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

  filterDataByDescription(): void {
    if (this.searchDescription) {
      this.filteredData = this.listOfData.filter(item =>
        item.description.toLowerCase().includes(this.searchDescription.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  // filterDataByLaboratoire(): void {
  //   if (this.searchLaboratoire) {
  //     this.filteredData = this.listOfData.filter(item =>
  //       item.laboratoireId.toLowerCase().includes(this.searchLaboratoire.toLowerCase())
  //     );
  //   } else {
  //     this.filteredData = [...this.listOfData]; // Show all data if no search text
  //   }
  // }

  onSearchDescriptionChange(): void {
    this.filterDataByDescription();
  }
  onSearchNameChange(): void {
    this.filterDataByName();
  }
  // onSearchLaboratoireChange(): void {
  //   this.filterDataByLaboratoire();
  // }

  getNomLaboratoire(id: string): string | undefined {
    const laboratoire = this.listOfLaboratoires.find(labo => labo.id === id);
    return laboratoire ? laboratoire.name : undefined;
  }


}
