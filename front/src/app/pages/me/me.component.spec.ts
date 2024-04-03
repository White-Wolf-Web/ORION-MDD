import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MeComponent } from './me.component';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let authServiceMock: any;
  let userServiceMock: any;
  let subscriptionServiceMock: any;
  let routerMock: any;

  beforeEach(async () => {
    authServiceMock = jasmine.createSpyObj('AuthService', ['logout']);
    userServiceMock = jasmine.createSpyObj('UserService', ['getCurrentUser', 'updateUser']);
    subscriptionServiceMock = jasmine.createSpyObj('SubscriptionService', ['getSubscriptionsForUser']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: UserService, useValue: userServiceMock },
        { provide: SubscriptionService, useValue: subscriptionServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update user data on save', () => {
    const updateMockResponse = { message: 'User updated successfully' };
    userServiceMock.updateUser.and.returnValue(of(updateMockResponse));

    component.userForm.patchValue({ username: 'updatedUser', email: 'updated@example.com' });
    component.save();

    expect(userServiceMock.updateUser).toHaveBeenCalledWith({ username: 'updatedUser', email: 'updated@example.com' });
    expect(component.userForm.value).toEqual({ username: 'updatedUser', email: 'updated@example.com' });
  });

  it('should navigate to login page on logout', () => {
    component.logout();
    expect(authServiceMock.logout).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
  });

});
