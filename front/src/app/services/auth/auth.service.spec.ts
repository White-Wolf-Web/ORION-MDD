import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';

describe('AuthService', () => {
  let service: AuthService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });

    service = TestBed.inject(AuthService);
    httpController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpController.verify();
  });

  it('should register a new user', () => {
    const mockUser = {
      username: 'testuser',
      email: 'test@example.com',
      password: '123456',
    };

    service.register(mockUser).subscribe((response) => {
      expect(response).toEqual({ success: true });
    });

    const req = httpController.expectOne(`${service['apiUrl']}/register`);
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(mockUser);
    req.flush({ success: true });
  });

  it('should login a user', () => {
    const mockCredentials = {
      email: 'test@example.com',
      password: '123456',
    };

    service.login(mockCredentials).subscribe((response) => {
      expect(response).toEqual({ token: 'fake-jwt-token' });
    });

    const req = httpController.expectOne(`${service['apiUrl']}/login`);
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(mockCredentials);
    req.flush({ token: 'fake-jwt-token' });
  });

  it('should update user information', () => {
    const mockUpdateData = {
      username: 'updatedUser',
      email: 'updated@example.com',
    };

    service.updateUser(mockUpdateData).subscribe((response) => {
      expect(response).toEqual({ success: true });
    });

    const req = httpController.expectOne(`${service['apiUrl']}/updateUser`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toEqual(mockUpdateData);
    req.flush({ success: true });
  });

  it('should remove token on logout', () => {
    spyOn(localStorage, 'removeItem');
    service.logout();
    expect(localStorage.removeItem).toHaveBeenCalledWith('token');
  });
});
