import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSortModule, Sort } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { UserService } from '../../../core/services/user.service';
import { MatDialog } from '@angular/material/dialog';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    MatIconModule,
    RouterLink,
    MatInputModule,
    MatFormFieldModule,
  ],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  displayedColumns: string[] = ['cep', 'logradouro', 'bairro', 'cidade', 'estado', 'actions'];
  dataSource: any[] = [];
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;
  sortBy = 'logradouro';

  constructor(private userService: UserService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadAddresses();
  }

  loadAddresses(): void {
    this.userService.loadAllAddresses(this.pageIndex, this.pageSize, this.sortBy)
      .subscribe({
        next: (response) => {
          this.dataSource = response.content;
          this.totalElements = response.totalElements;
        },
        error: (err) => {
          console.error('Error loading addresses', err);
        }
      });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadAddresses();
  }

  deleteAddress(): void {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'Cancel',
    }).then((result: any) => {
      if (result.isConfirmed) {
        this.executeDelete();
      }
    });
  }

  executeDelete = () => {
    this.userService.deleteAddress().subscribe({
      next: () => {
        Swal.fire(
          'Deleted!',
          'The address has been deleted.',
          'success'
        );
        this.loadAddresses();
      },
      error: (err) => {
        Swal.fire(
          'Error!',
          'Failed to delete the address.',
          'error'
        );
        console.error('Delete error:', err);
      }
    });
  }

  addNewAddress = () => {
    Swal.fire({
      title: 'Add New Address',
      html:
        `<input id="cep" class="swal2-input" placeholder="CEP" type="text">
        <input id="logradouro" class="swal2-input" placeholder="Street">
        <input id="numero" class="swal2-input" placeholder="Number" type="text">
        <input id="complemento" class="swal2-input" placeholder="Complement">
        <input id="bairro" class="swal2-input" placeholder="Neighborhood">
        <input id="localidade" class="swal2-input" placeholder="City">
        <input id="uf" class="swal2-input" placeholder="State" maxlength="2">`,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Save',
      cancelButtonText: 'Cancel',
      preConfirm: () => {
        return {
          cep: (document.getElementById('cep') as HTMLInputElement).value,
          logradouro: (document.getElementById('logradouro') as HTMLInputElement).value,
          numero: (document.getElementById('numero') as HTMLInputElement).value,
          complemento: (document.getElementById('complemento') as HTMLInputElement).value,
          bairro: (document.getElementById('bairro') as HTMLInputElement).value,
          localidade: (document.getElementById('localidade') as HTMLInputElement).value,
          uf: (document.getElementById('uf') as HTMLInputElement).value.toUpperCase()
        }
      }
    }).then((result) => {
      if (result.isConfirmed) {
        const addressData = result.value;
        const userId = Number(localStorage.getItem('user_id'));
        const fullAddressData = { ...addressData, userId };

        this.userService.createAddress(fullAddressData).subscribe({
          next: () => {
            Swal.fire('Success!', 'Address added successfully', 'success');
            this.loadAddresses();
          },
          error: (err) => {
            Swal.fire('Error!', 'Failed to add address', 'error');
            console.error('Error adding address:', err);
          }
        });
      }
    });
  }

}




  

