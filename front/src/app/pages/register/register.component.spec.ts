import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterComponent ],
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        HttpClientTestingModule
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form invalid when empty', () => {
    expect(component.registerForm.valid).toBeFalsy();
  });

  it('username field validity', () => {
    let username = component.registerForm.controls['username'];
    expect(username.valid).toBeFalsy();

    let errors = username.errors || {};
    expect(errors['required']).toBeTruthy();
  });

  it('email field validity', () => {
    let email = component.registerForm.controls['email'];
    expect(email.valid).toBeFalsy();

    let errors = email.errors || {};
    expect(errors['required']).toBeTruthy();

    email.setValue("test");
    errors = email.errors || {};
    expect(errors['email']).toBeTruthy();
  });

  it('password field validity', () => {
    let password = component.registerForm.controls['password'];
    expect(password.valid).toBeFalsy();

    let errors = password.errors || {};
    expect(errors['required']).toBeTruthy();

    password.setValue("12345");
    errors = password.errors || {};
    expect(errors['minlength']).toBeTruthy();
  });

});
