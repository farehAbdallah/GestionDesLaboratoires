import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LaboprofileComponent } from './laboprofile.component';

describe('LaboprofileComponent', () => {
  let component: LaboprofileComponent;
  let fixture: ComponentFixture<LaboprofileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LaboprofileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LaboprofileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
