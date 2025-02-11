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
import { AnalyseService } from '../../services/analyses.service';
import {LaboratoireService} from '../../services/laboratoires.service';
import {ActivatedRoute} from '@angular/router';
import {LoginService} from '../../services/login.service';

interface EpreuveData {
  id: string;
  nom: string;
  analyseId: string;
}

@Component({
  selector: 'app-epreuves',
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
  templateUrl: './epreuves.component.html',
  styleUrls: ['./epreuves.component.css']
})
export class EpreuvesComponent implements OnInit {
  i = 0;
  editId: string | null = null;
  listOfData: EpreuveData[] = [];
  originalData: EpreuveData | null = null;
  isVisible = false;
  isOkLoading = false;
  filteredData: EpreuveData[] = [];
  validateForm: FormGroup;
  searchNom: string = '';  // Property for search text
  searchAnalyseId: string = ''; // Updated search property for analyseId
  listOfAnalyses: any[] = []; // List of available analyses
  laboratoireId: string | null = null;

  actionPermission: boolean = false;
  allowedRoles: string[] = ['administrateur', 'employee']


  constructor(private fb: NonNullableFormBuilder,
              private message: NzMessageService,
              private analyseService: AnalyseService,
              private laboratoireService: LaboratoireService,
              private loginService: LoginService,
              private route: ActivatedRoute
  ) {
    this.validateForm = this.fb.group({
      nom: ['', [Validators.required]],
      analyseId: ['', [Validators.required]] // Use analyseId instead of laboratoireId
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

    // Optionally, save the id in the laboratoireService
    if (this.laboratoireId) {
      this.laboratoireService.setSelectedLabo(this.laboratoireId);
    }
    this.loadAnalyses(); // Load available analyses
    this.loadEpreuves(); // Load epreuves after analyses

  }

  loadAnalyses(): void {
    this.analyseService.getAnalyses().subscribe((analyses: any) => {
      this.listOfAnalyses = analyses; // Load the list of analyses
      this.filterAnalyseByLaboratoire(); // Filter analyses by laboratoireId
      // console.log("list analyses", this.listOfAnalyses);
    });
  }

  loadEpreuves(): void {
    this.analyseService.getEpreuves().subscribe(epreuves => {
      // Filter epreuves based on the filtered analyses
      if (this.laboratoireId) {
        this.listOfData = epreuves.filter(epreuve =>
          this.listOfAnalyses.some(analyse => analyse.id === epreuve.analyseId && analyse.laboratoireId === this.laboratoireId)
        );
      } else {
        this.listOfData = epreuves; // No filtering if no laboratoireId
      }
      this.filteredData = [...this.listOfData]; // Initialize filtered data
    });
  }

  filterAnalyseByLaboratoire(): void {
    if (this.laboratoireId) {
      this.listOfAnalyses = this.listOfAnalyses.filter(item =>
        item.laboratoireId === this.laboratoireId
      );
    }
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
      const updatedEpreuve = this.listOfData.find(item => item.id === this.editId);
      // Update the epreuve on the server
      this.analyseService.updateEpreuve(this.editId, updatedEpreuve).subscribe(() => {
        this.editId = null;
        this.originalData = null;
        this.loadEpreuves(); // Refresh the list after update
        this.message.success('Épreuve modifiée avec succès');
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
    this.loadEpreuves(); // Refresh the list after update
  }

  deleteRow(id: string): void {
    this.analyseService.deleteEpreuve(id).subscribe(() => {
      this.loadEpreuves(); // Reload the epreuves after deletion
      this.message.success('Épreuve supprimée avec succès');
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
      const newEpreuve: EpreuveData = {
        id: `${Date.now()}`,
        ...this.validateForm.value
      };
      this.analyseService.addEpreuve(newEpreuve).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadEpreuves(); // Reload epreuves after adding
        this.message.success('Épreuve ajoutée avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
  }

  filterDataByNom(): void {
    if (this.searchNom) {
      this.filteredData = this.listOfData.filter(item =>
        item.nom.toLowerCase().includes(this.searchNom.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  filterDataByAnalyseId(): void {
    if (this.searchAnalyseId) {
      this.filteredData = this.listOfData.filter(item =>
        item.analyseId.toLowerCase().includes(this.searchAnalyseId.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  onSearchNomChange(): void {
    this.filterDataByNom();
  }

  onSearchAnalyseIdChange(): void {
    this.filterDataByAnalyseId();
  }

  getNomAnalyse(id: string): string | undefined {
    const analyse = this.listOfAnalyses.find(analyse => analyse.id === id);
    return analyse ? analyse.name : undefined;
  }
}
