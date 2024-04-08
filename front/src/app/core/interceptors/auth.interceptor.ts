import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Vérifier si la requête ne concerne pas l'inscription ou la connexion
    if (!req.url.endsWith('/register') && !req.url.endsWith('/login')) {
      const authToken = this.authService.getToken();
      if (authToken) {
        const authReq = req.clone({
          headers: req.headers.set('Authorization', `Bearer ${authToken}`)
        });
        return next.handle(authReq);
      }
    }
    return next.handle(req);
  }
}