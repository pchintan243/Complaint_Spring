import { ToastrService } from 'ngx-toastr';
import { ComplaintService } from './../services/complaint.service';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Complaint } from '../model/complaint';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort } from '@angular/material/sort';


@Component({
  selector: 'app-complaint',
  templateUrl: './complaint.component.html',
  styleUrls: ['./complaint.component.css']
})
export class ComplaintComponent implements OnInit, AfterViewInit {

  @ViewChild('paginator') paginator!: MatPaginator;


  @ViewChild(MatSort) sort!: MatSort;

  dataSource!: MatTableDataSource<Complaint>;

  // complaintData!: Complaint[];

  displayedColumns: string[] = ['name', 'email', 'department', 'query', 'otherQuery', 'computerIp', 'phone', 'note', 'date', 'status', 'flag'];

  constructor(private complaintService: ComplaintService, private toastr: ToastrService, private router: Router) { }

  ngOnInit(): void {
    // this.getAllComplaint();
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
}

