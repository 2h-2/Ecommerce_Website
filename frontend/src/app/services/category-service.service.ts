import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export class Category{
  id: number = 0;
  categoryName: string = '';
  description: string = '';
}
@Injectable({
  providedIn: 'root'
})
export class CategoryServiceService {

  private apiUrl = 'http://localhost:8080/api/category';

  constructor(private http: HttpClient) { }

  addCategory(formData: FormData){
     return this.http.post<any>(`${this.apiUrl}/add`, formData);
  }

   getAllCategories(): Observable<any[]> {
      return this.http.get<any[]>(`http://localhost:8080/api/category`);
    }

}
