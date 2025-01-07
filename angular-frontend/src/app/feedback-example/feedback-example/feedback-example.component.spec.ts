import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackExampleComponent } from './feedback-example.component';

describe('FeedbackExampleComponent', () => {
  let component: FeedbackExampleComponent;
  let fixture: ComponentFixture<FeedbackExampleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FeedbackExampleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FeedbackExampleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
