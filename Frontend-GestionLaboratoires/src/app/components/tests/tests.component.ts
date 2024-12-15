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

interface TestData {
  id: string;
  nomTest: string;
  analyseId: string;
  sousEpreuve: string;
  intervalMinDeReference: number;
  intervalMaxDeReference: number;
  uniteDeReference: string;
  details: string;
}

@Component({
  selector: 'app-tests',
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
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.css']
})
export class TestsComponent implements OnInit {
  // i = 0;
  editId: string | null = null;
  listOfData: TestData[] = [];
  originalData: TestData | null = null;
  isVisible = false;
  isOkLoading = false;
  filteredData: TestData[] = [];
  validateForm: FormGroup;
  searchName: string = '';  // Property for search text
  searchAnalyse: string = '';  // Property for search text
  listOfAnalyses: any[] = []; // List of analyses for filtering

  constructor(private fb: NonNullableFormBuilder, private message: NzMessageService, private analyseService: AnalyseService) {
    this.validateForm = this.fb.group({
      nomTest: ['', [Validators.required]],
      analyseId: ['', [Validators.required]],  // ID of the associated analysis
      sousEpreuve: ['', [Validators.required]],
      intervalMinDeReference: ['', [Validators.required]],
      intervalMaxDeReference: ['', [Validators.required]],
      uniteDeReference: ['', [Validators.required]],
      details: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadTests();
    this.loadAnalyses();  // Load the available analyses
  }

  loadAnalyses(): void {
    this.analyseService.getAnalyses().subscribe((analyses: any) => {
      this.listOfAnalyses = analyses; // Load the list of analyses for filtering
    });
  }

  loadTests(): void {
    this.analyseService.getTests().subscribe(tests => {
      this.listOfData = tests;
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
      const updatedTest = this.listOfData.find(item => item.id === this.editId);
      // Update the test on the server
      this.analyseService.updateTest(this.editId, updatedTest).subscribe(() => {
        this.editId = null;
        this.originalData = null;
        this.loadTests(); // Refresh the list after update
        this.message.success('Test modifié avec succès');
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
    this.loadTests(); // Refresh the list after canceling edit
  }

  deleteRow(id: string): void {
    this.analyseService.deleteTest(id).subscribe(() => {
      this.loadTests(); // Reload the tests after deletion
      this.message.success('Test supprimé avec succès');
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
      const newTest: TestData = {
        id: `${Date.now()}`,
        ...this.validateForm.value
      };
      this.analyseService.addTest(newTest).subscribe(() => {
        this.isOkLoading = false;
        this.isVisible = false;
        this.loadTests(); // Reload tests after adding
        this.message.success('Test ajouté avec succès');
      });
    }
  }

  handleOk(): void {
    this.submitForm();
  }

  filterDataByName(): void {
    if (this.searchName) {
      this.filteredData = this.listOfData.filter(item =>
        item.nomTest.toLowerCase().includes(this.searchName.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  filterDataByAnalyse(): void {
    if (this.searchAnalyse) {
      this.filteredData = this.listOfData.filter(item =>
        this.getNomAnalyse(item.analyseId)?.toLowerCase().includes(this.searchAnalyse.toLowerCase())
      );
    } else {
      this.filteredData = [...this.listOfData]; // Show all data if no search text
    }
  }

  onSearchAnalyseChange(): void {
    this.filterDataByAnalyse();
  }

  onSearchNameChange(): void {
    this.filterDataByName();
  }

  getNomAnalyse(id: string): string | undefined {
    const analyse = this.listOfAnalyses.find(analyse => analyse.id === id);
    return analyse ? analyse.name : undefined;
  }
}
