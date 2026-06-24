import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DepartamentoService {

  private http = inject(HttpClient);

  private api = 'http://localhost:8081/api/departamentos';

  getAll() {
    return this.http.get<any>(this.api);
  }
}