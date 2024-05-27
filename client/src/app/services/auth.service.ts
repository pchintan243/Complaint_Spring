import { UserForLogin, UserForRegister } from './../model/user';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  authUser(user: UserForLogin) {
    return this.http.post(this.baseUrl + 'auth/userLogin', user);
  }

  registerUser(user: UserForRegister) {
    return this.http.post(this.baseUrl + 'auth/userRegister', user);
  }
}
