import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Complaint, complaintData } from '../model/complaint';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {

  private baseUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  getAllComplaints(): Observable<Complaint[]> {

    let token = localStorage.getItem('token');
    let httpOptions;
    if (token) {
      token = token.replace(/^"(.*)"$/, '$1');
      httpOptions = {
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + token
        })
      };
    }
    return this.http.get<Complaint[]>(this.baseUrl + 'complaint/getAllComplaints', httpOptions);
  }

  registerComplaint(data: complaintData) {
    let token = localStorage.getItem('token');
    let httpOptions;
    if (token) {
      token = token.replace(/^"(.*)"$/, '$1');
      httpOptions = {
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + token
        })
      };
    }

    return this.http.post(this.baseUrl + 'complaint/registerComplaint', data, httpOptions);
  }
}
