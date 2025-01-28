import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenFeedbackComponent } from './open-feedback.component';

describe('OpenFeedbackComponent', () => {
  let component: OpenFeedbackComponent;
  let fixture: ComponentFixture<OpenFeedbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OpenFeedbackComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OpenFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
