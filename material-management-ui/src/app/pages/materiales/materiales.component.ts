import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
  FormsModule,
} from '@angular/forms';

import { MaterialService } from '../../core/services/material.service';
import { CiudadService } from '../../core/services/ciudad.service';
import { DepartamentoService } from '../../core/services/departamento.service';

@Component({
  selector: 'app-materiales',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './materiales.component.html',
  styleUrls: ['./materiales.component.scss']
})
export class MaterialesComponent implements OnInit {
  private fb = inject(FormBuilder);

  private materialService = inject(MaterialService);
  private ciudadService = inject(CiudadService);
  private departamentoService = inject(DepartamentoService);

  form!: FormGroup;

  materiales: any[] = [];

  departamentos: any[] = [];
  ciudades: any[] = [];

  tipos: string[] = [];
  estados: string[] = [];

  editing = false;
  materialId?: number;

  selectedTipo = '';
  selectedCiudad = '';

  ngOnInit(): void {
    this.buildForm();

    this.loadMateriales();
    this.loadDepartamentos();
    this.loadTipos();
    this.loadEstados();
  }

  buildForm(): void {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: [''],
      tipo: ['', Validators.required],
      precio: [null, Validators.required],
      fechaCompra: ['', Validators.required],
      fechaVenta: [''],
      estado: ['', Validators.required],
      departamentoId: ['', Validators.required],
      ciudadId: ['', Validators.required],
    });
  }

  loadMateriales(): void {
    this.materialService.getAll().subscribe((response) => {
      this.materiales = response.data;
    });
  }

  loadDepartamentos(): void {
    this.departamentoService.getAll().subscribe((response) => {
      this.departamentos = response.data;
    });
  }

  loadTipos(): void {
    this.materialService.getTipos().subscribe((response) => {
      this.tipos = response.data;
    });
  }

  loadEstados(): void {
    this.materialService.getEstados().subscribe((response) => {
      this.estados = response.data;
    });
  }

  onDepartamentoChange(): void {
    const departamentoId = this.form.get('departamentoId')?.value;

    if (!departamentoId) {
      return;
    }

    this.ciudadService
      .getByDepartamento(departamentoId)
      .subscribe((response) => {
        this.ciudades = response.data;
      });
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();

      return;
    }

    const request = {
      nombre: this.form.value.nombre,
      descripcion: this.form.value.descripcion,
      tipo: this.form.value.tipo,
      precio: this.form.value.precio,
      fechaCompra: this.form.value.fechaCompra,
      fechaVenta: this.form.value.fechaVenta,
      estado: this.form.value.estado,
      ciudadId: this.form.value.ciudadId,
    };

    if (this.editing) {
      this.materialService.update(this.materialId!, request).subscribe(() => {
        this.resetForm();

        this.loadMateriales();
      });
    } else {
      this.materialService.create(request).subscribe(() => {
        this.resetForm();

        this.loadMateriales();
      });
    }
  }

  edit(material: any): void {
    this.editing = true;

    this.materialId = material.id;

    this.form.patchValue({
      nombre: material.nombre,
      descripcion: material.descripcion,
      tipo: material.tipo,
      precio: material.precio,
      fechaCompra: material.fechaCompra,
      fechaVenta: material.fechaVenta,
      estado: material.estado,
      ciudadId: material.ciudadId,
    });
  }

  resetForm(): void {
    this.editing = false;

    this.materialId = undefined;

    this.form.reset();

    this.selectedTipo = '';
    this.selectedCiudad = '';
  }

  filterByTipo(): void {
    if (!this.selectedTipo) {
      this.loadMateriales();

      return;
    }

    this.materialService.getByTipo(this.selectedTipo).subscribe((response) => {
      this.materiales = response.data;
    });
  }

  filterByCiudad(): void {
    if (!this.selectedCiudad) {
      this.loadMateriales();

      return;
    }

    this.materialService
      .getByCiudad(Number(this.selectedCiudad))
      .subscribe((response) => {
        this.materiales = response.data;
      });
  }
}
