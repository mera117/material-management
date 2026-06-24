import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MaterialService {

  private http = inject(HttpClient);

  private api = 'http://localhost:8081/api/materiales';

  getAll() {
    return this.http.get<any>(this.api);
  }

  getByTipo(tipo: string) {
    return this.http.get<any>(
      `${this.api}/tipo/${tipo}`
    );
  }

  getByCiudad(ciudadId: number) {
    return this.http.get<any>(
      `${this.api}/ciudad/${ciudadId}`
    );
  }

  getByFecha(fecha: string) {
    return this.http.get<any>(
      `${this.api}/fecha-compra?fecha=${fecha}`
    );
  }

  create(material: any) {
    return this.http.post<any>(
      this.api,
      material
    );
  }

  update(id: number, material: any) {
    return this.http.put<any>(
      `${this.api}/${id}`,
      material
    );
  }

  getTipos() {
    return this.http.get<any>(
      `${this.api}/tipos`
    );
  }

  getEstados() {
    return this.http.get<any>(
      `${this.api}/estados`
    );
  }
}