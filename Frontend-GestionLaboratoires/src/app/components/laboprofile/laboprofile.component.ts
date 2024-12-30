import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {NzGridModule} from 'ng-zorro-antd/grid';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzDatePickerModule} from 'ng-zorro-antd/date-picker';
import {NzRadioModule} from 'ng-zorro-antd/radio';
import {NzIconModule} from 'ng-zorro-antd/icon';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzTagModule} from 'ng-zorro-antd/tag';
import {NzDescriptionsModule} from 'ng-zorro-antd/descriptions';
import {NzDividerModule} from 'ng-zorro-antd/divider';
import {LaboratoireService} from '../../services/laboratoires.service';
import {ActivatedRoute} from '@angular/router';
import {NzModalComponent, NzModalModule, NzModalService} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzUploadComponent} from 'ng-zorro-antd/upload';


interface LaboratoryData {
  id: string;
  name: string;
  nrc: string;
  logo: string;
  dateActivation: Date | null;
  isActive: boolean;
}


@Component({
  selector: 'app-laboprofile',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    NzGridModule,
    NzFormModule,
    NzInputModule,
    NzSelectModule,
    NzDatePickerModule,
    NzRadioModule,  // Make sure this is here
    NzIconModule,
    NzButtonModule,
    NzTagModule,
    NzDescriptionsModule,
    NzDividerModule,
    NzModalModule,
    NzUploadComponent,
  ],
  providers: [NzModalService],
  templateUrl: './laboprofile.component.html',
  styleUrls: ['./laboprofile.component.css'],

})
export class LaboprofileComponent implements OnInit {
  laboratoire: any = {};  // To store the laboratoire data
  adresseLaboratoire: any = {};  // To store the laboratoire adresse data
  contactLaboratoire: any = {};  // To store the laboratoire contact data
  laboId: string | null = '';


  isGeneralModalVisible = false;
  isContactModalVisible = false;
  isAddressModalVisible = false;

  generalForm: FormGroup;
  contactForm: FormGroup;
  addressForm: FormGroup;

  logoFileName: string | null = null;
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private laboratoireService: LaboratoireService,
    private message: NzMessageService,
    private route: ActivatedRoute
  ) {
    this.generalForm = this.fb.group({
      name: ['', Validators.required],
      nrc: ['', Validators.required],
      isActive: [true],
      logo: ['']
    });

    this.contactForm = this.fb.group({
      numTel: ['', Validators.required],
      fax: [''],
      email: ['', [Validators.required, Validators.email]]
    });

    this.addressForm = this.fb.group({
      numVoie: ['', Validators.required],
      nomVoie: ['', Validators.required],
      codePostal: ['', Validators.required],
      ville: ['', Validators.required],
      commune: ['', Validators.required]
    });
  }

  ngOnInit(): void {


    // Extract the 'id' from the route parameters
    this.laboId = this.route.snapshot.paramMap.get('id');

    // Optionally, save the id in the laboratoireService
    if (this.laboId) {
      this.laboratoireService.setSelectedLabo(this.laboId);
    }


    if (this.laboId) {
      // Fetch the laboratoire data by ID
      this.laboratoireService.getLaboratoireById(this.laboId).subscribe(
        (data) => {
          this.laboratoire = data;  // Assign the fetched data to the laboratoire property
        },
        (error) => {
          console.error('Error fetching laboratoire data:', error);
        }
      );
      this.laboratoireService.getContactsLaboratoires().subscribe(
        (data) => {
          this.contactLaboratoire = data.find(value => value.laboratoireId === this.laboId);  // Assign the fetched data to the laboratoire property
        },
        (error) => {
          console.error('Error fetching adresse data:', error);
        }
      );
      this.laboratoireService.getAdresses().subscribe(
        (data) => {
          // Correction de la logique de recherche
          this.adresseLaboratoire = data.find(value =>
            this.contactLaboratoire.laboratoireId === this.laboId &&
            this.contactLaboratoire.adresseId === value.id
          );
        },
        (error) => {
          console.error('Error fetching adresse data:', error);
        }
      );

    } else {
      console.error('No laboratoire ID selected.');
    }
  }



  showGeneralModal() {
    this.generalForm.patchValue(this.laboratoire);
    this.isGeneralModalVisible = true;
  }

  showContactModal() {
    this.contactForm.patchValue(this.contactLaboratoire);
    this.isContactModalVisible = true;
  }

  showAddressModal() {
    this.addressForm.patchValue(this.adresseLaboratoire);
    this.isAddressModalVisible = true;
  }

  // Add file handler method
  handleFileInput(event: any): void {
    if (event.type === 'success') {
      const file = event.file.originFileObj;
      this.selectedFile = file;
      this.logoFileName = file.name;
      this.generalForm.patchValue({logo: file});
    }
  }

  // Update submit method
  handleGeneralSubmit() {
    if (this.generalForm.valid) {
      const formData = new FormData();

      formData.append('logo', this.laboratoire.logo);
      formData.append('name', this.generalForm.get('name')?.value);
      formData.append('nrc', this.generalForm.get('nrc')?.value);
      formData.append('isActive', this.generalForm.get('isActive')?.value);
      if(this.generalForm.get('isActive')?.value && this.laboratoire.isActive === false){
        formData.append('dateActivation', Date.now().toString())
      }
      if (this.selectedFile) {
        formData.append('logo', this.selectedFile);
      }


      this.laboratoireService.updateLaboratoire(this.laboId, formData)
        .subscribe(
          () => {
            this.message.success('Informations générales mises à jour');
            this.isGeneralModalVisible = false;
            this.refreshData();
          },
          error => this.message.error('Erreur lors de la mise à jour')
        );
    }
  }

  handleContactSubmit() {
    if (this.contactForm.valid) {
      this.laboratoireService.updateContactLaboratoire(this.contactLaboratoire.id, this.contactForm.value)
        .subscribe(
          () => {
            this.message.success('Contact mis à jour');
            this.isContactModalVisible = false;
            this.refreshData();
          },
          error => this.message.error('Erreur lors de la mise à jour')
        );
    }
  }

  handleAddressSubmit() {
    if (this.addressForm.valid) {
      this.laboratoireService.updateAdresse(this.adresseLaboratoire.id, this.addressForm.value)
        .subscribe(
          () => {
            this.message.success('Adresse mise à jour');
            this.isAddressModalVisible = false;
            this.refreshData();
          },
          error => this.message.error('Erreur lors de la mise à jour')
        );
    }
  }

  private refreshData() {
    // Refresh all data after updates
    this.ngOnInit();
  }

  // Cancel handlers
  handleGeneralCancel() { this.isGeneralModalVisible = false; }
  handleContactCancel() { this.isContactModalVisible = false; }
  handleAddressCancel() { this.isAddressModalVisible = false; }

}
