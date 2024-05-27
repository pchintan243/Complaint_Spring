import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private authService: AuthService, private toastr: ToastrService, private router: Router) { }
  onLogin(loginForm: NgForm) {
    this.authService.authUser(loginForm.value).subscribe(
      (res: any) => {
        localStorage.setItem('token', JSON.stringify(res.token));
        localStorage.setItem('UserName', res.username);
        this.toastr.success("User logged in successfully");
        this.router.navigate(['/login']);
      }
    );
  }
}
