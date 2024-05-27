import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  constructor(private authService: AuthService, private toastr: ToastrService, private router: Router) { }
  onSubmit(registerForm: NgForm) {
    this.authService.registerUser(registerForm.value).subscribe(
      (res: any) => {
        this.toastr.success("User Registered successfully");
        this.router.navigate(['/login']);
      }
    )
  }

}
