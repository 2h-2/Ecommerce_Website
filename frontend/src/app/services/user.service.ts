import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  address: String;
  role: String;
  phone: String;
}

export interface UserDto {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient) { }

  getUserDetails(id: number): Observable<User> {
    const token = localStorage.getItem('access_token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    const options = { headers };
    const url = `http://localhost:8080/user/getUser/${id}`;

    return this.http.get<User>(url, options).pipe(
      catchError((error) => {
        console.error('Error fetching user details:', error);
        return throwError(() => error);
      })
    );
  }

  updateUser(userId: String, userDto: UserDto): Observable<User> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });
    const options = { headers };
    const url = `${this.baseUrl}/update/${userId}`;
    return this.http.put<User>(url, userDto, options);
  }
}
