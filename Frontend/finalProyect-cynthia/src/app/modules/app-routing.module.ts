import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../screens/home/home.component';
import { LoginComponent } from '../screens/login/login.component';
import { RegisterComponent } from '../screens/register/register.component';
import { UsersListComponent } from '../screens/users-list/users-list.component';


const routes: Routes = [
  { path: 'home', component:HomeComponent},
  { path: 'login', component:LoginComponent},
  { path: 'register', component:RegisterComponent},
  { path: 'users', component:UsersListComponent},

  { path: '', pathMatch: 'full', redirectTo: '/login'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
