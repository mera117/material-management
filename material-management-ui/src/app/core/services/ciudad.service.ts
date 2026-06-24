import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CiudadService {

  private http = inject(HttpClient);

  private api = 'http://localhost:8081/api/ciudades';

  getAll() {
    return this.http.get<any>(this.api);
  }

  getByDepartamento(id: number) {
    return this.http.get<any>(
      `${this.api}/departamento/${id}`
    );
  }
}