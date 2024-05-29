import { ToastrService } from 'ngx-toastr';
import { ComplaintService } from './../services/complaint.service';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Complaint } from '../model/complaint';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort } from '@angular/material/sort';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-complaint',
  templateUrl: './complaint.component.html',
  styleUrls: ['./complaint.component.css']
})
export class ComplaintComponent implements OnInit, AfterViewInit {

  @ViewChild('paginator') paginator!: MatPaginator;

  @ViewChild(MatSort) sort!: MatSort;

  dataSource!: MatTableDataSource<Complaint>;

  role!: string;

  // complaintData!: Complaint[];

  displayedColumns: string[] = ['name', 'email', 'department', 'query', 'otherQuery', 'computerIp', 'phone', 'note', 'date', 'status', 'flag'];

  constructor(private complaintService: ComplaintService, private toastr: ToastrService, private router: Router) { }
  ngOnInit(): void {
    this.getAllComplaints();
    this.getRole();
    if (this.role == 'FacultyHead' || this.role == 'AssistantManager') {
      this.displayedColumns.push('state');
    }
  }

  getAllComplaints() {
    this.complaintService.getAllComplaints()?.subscribe(complaints => {
      // this.complaintData = complaints;
      this.dataSource = new MatTableDataSource(complaints);
      this.dataSource.paginator = this.paginator;
      console.log(this.dataSource.data);
      this.dataSource.sort = this.sort;
    });
  }

  ngAfterViewInit(): void {
  }


  proceed(id: number) {
    this.complaintService.proceed(id).subscribe(() => {
      this.toastr.success('Complaint Processed');
      this.getAllComplaints();
    });
  }

  solved(id: number) {
    this.complaintService.solved(id).subscribe(() => {
      this.toastr.success('Complaint Solved');
      this.getAllComplaints();
    });
  }

  getRole() {

    let token = localStorage.getItem('token') as string;
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(token);

    this.role = decodedToken.role;

    return this.role;

  }

  getStatus(status: string) {
    let styles = {};
    if (status === 'Pending') {
      styles = {
        'color': 'red',
        'font-weight': '400'
      };
    } else if (status === 'Solved') {
      styles = {
        'color': '#008b00'
      };
    } else if (status === 'Processing') {
      styles = {
        'color': '#ff8900'
      };
    }
    return styles;
  }

}

