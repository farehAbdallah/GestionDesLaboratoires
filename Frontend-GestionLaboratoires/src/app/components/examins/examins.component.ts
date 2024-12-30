import { Component, OnInit } from '@angular/core';
import { FormGroup, FormsModule, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import {NzModalComponent, NzModalContentDirective, NzModalFooterDirective, NzModalService} from 'ng-zorro-antd/modal';
import { PatientService } from '../../services/patients.service';
import { CommonModule } from '@angular/common';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzTableModule } from 'ng-zorro-antd/table';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NzFormControlComponent, NzFormDirective, NzFormLabelComponent} from 'ng-zorro-antd/form';
import {NzTagComponent} from 'ng-zorro-antd/tag';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';
import {AnalyseService} from '../../services/analyses.service';
import {LoginService} from '../../services/login.service';
import {ActivatedRoute} from '@angular/router';
import {LaboratoireService} from '../../services/laboratoires.service';

interface ExaminData {
  id: string;
  fkNumDossier: string;
  fkIdEpreuve: string;
  fkIdTestAnalyse: string;
  resultat: string;
}

@Component({
  selector: 'app-examins',
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
  templateUrl: './examins.component.html',
  styleUrls: ['./examins.component.css']
})
export class ExaminsComponent implements OnInit {
  listOfData: ExaminData[] = [];
  originalData: ExaminData | null = null;
  isVisible = false;
  isOkLoading = false;
  validateForm: FormGroup;
  editId: string | null = null;
  filteredData: ExaminData[] = [];
  listOfEpreuves: any[] = [];
  listOfTests: any[] = [];
  listofDossiers: any[] = [];
  actionPermission: boolean = false;
  allowedRoles: string[] = ['administrateur', 'employee']
  laboratoireId: string | null = null;



  // Filtering properties
  searchNumDossier: string = '';
  searchEpreuve: string = '';
  searchTestAnalyse: string = '';

  constructor(private fb: NonNullableFormBuilder,
              private message: NzMessageService,
              private examinsService: PatientService,
              private analyseService: AnalyseService,
              private loginService: LoginService,
              private route: ActivatedRoute,
              private laboratoireService: LaboratoireService,


  ) {
    this.validateForm = this.fb.group({
      fkNumDossier: ['', [Validators.required]],
      fkIdEpreuve: ['', [Validators.required]],
      fkIdTestAnalyse: ['', [Validators.required]],
      resultat: ['', [Validators.required]],
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
    this.loadExamins();
    this.loadEpreuves();
    this.loadTests();
    this.loadDossiers();

  }

  loadExamins(): void {
    this.examinsService.getExamins().subscribe(examins => {
      this.listOfData = examins;
      this.filteredData = [...this.listOfData];
    });
  }

  loadEpreuves(): void {
    this.analyseService.getEpreuves().subscribe(epreuves => {
      this.listOfEpreuves = epreuves;
    });
  }

  loadTests(): void {
    this.analyseService.getTests().subscribe(tests => {
      this.listOfTests = tests;
    });
  }

  loadDossiers(): void {
    this.examinsService.getDossiers().subscribe(dossiers => {
      this.listofDossiers = dossiers;
    });
  }

  getNomTest(id: string): string | undefined {
    const tests = this.listOfTests.find(test => test.id === id);
    return tests ? tests.nomTest : undefined;
  }

  getNomEpreuve(id: string): string | undefined {
    const epreuves = this.listOfEpreuves.find(epreuve => epreuve.id === id);
    return epreuves ? epreuves.nom : undefined;
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
      const updatedExamin = this.listOfData.find(item => item.id === this.editId);
      if (updatedExamin) {
        this.examinsService.updateExamin(this.editId, updatedExamin).subscribe(() => {
          this.editId = null;
          this.originalData = null;
          this.loadExamins();
          this.message.success('Examin modifié avec succès');
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
    this.loadExamins();
  }

  deleteRow(id: string): void {
    this.examinsService.deleteExamin(id).subscribe(() => {
      this.loadExamins();
      this.message.success('Examin supprimé avec succès');
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
      const newExamin: ExaminData = {
        id: `${Date.now()}`,
        ...this.validateForm.value
      };
      this.examinsService.addExamin(newExamin).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadExamins();
        this.message.success('Examin ajouté avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
  }

  filterDataByNumDossier(): void {
    if (this.searchNumDossier) {
      this.filteredData = this.listOfData.filter(item =>
        item.fkNumDossier.toLowerCase().includes(this.searchNumDossier.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData];
    }
  }

  filterDataByEpreuve(): void {
    if (this.searchEpreuve) {
      this.filteredData = this.listOfData.filter(item =>
        item.fkIdEpreuve.toLowerCase().includes(this.searchEpreuve.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData];
    }
  }

  filterDataByTestAnalyse(): void {
    if (this.searchTestAnalyse) {
      this.filteredData = this.listOfData.filter(item =>
        item.fkIdTestAnalyse.toLowerCase().includes(this.searchTestAnalyse.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData];
    }
  }

  onSearchNumDossierChange(): void {
    this.filterDataByNumDossier();
  }

  onSearchEpreuveChange(): void {
    this.filterDataByEpreuve();
  }

  onSearchTestAnalyseChange(): void {
    this.filterDataByTestAnalyse();
  }


}
