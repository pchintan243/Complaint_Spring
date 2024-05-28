import { ToastrService } from 'ngx-toastr';
import { ComplaintService } from './../services/complaint.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-complaint-register',
  templateUrl: './complaint-register.component.html',
  styleUrls: ['./complaint-register.component.css']
})
export class ComplaintRegisterComponent {
  constructor(private complaintService: ComplaintService, private toastr: ToastrService, private router: Router) { }
  complaintRegister(complaintForm: NgForm) {
    this.complaintService.registerComplaint(complaintForm.value).subscribe(
      (res: any) => {
        this.toastr.success("User logged in successfully");
        this.router.navigate(['/']);
      }
    );
  }
}
