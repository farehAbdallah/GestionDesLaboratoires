import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactsLaboratoiresComponent } from './contacts-laboratoires.component';

describe('ContactsLaboratoiresComponent', () => {
  let component: ContactsLaboratoiresComponent;
  let fixture: ComponentFixture<ContactsLaboratoiresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContactsLaboratoiresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContactsLaboratoiresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
