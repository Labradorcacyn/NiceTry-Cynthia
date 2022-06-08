import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChampionListComponent } from '../screens/champion-list/champion-list.component';
import { CreateChampionComponent } from '../screens/create-champion/create-champion.component';
import { CreateTraitComponent } from '../screens/create-trait/create-trait.component';
import { HomeComponent } from '../screens/home/home.component';
import { LoginComponent } from '../screens/login/login.component';
import { RegisterComponent } from '../screens/register/register.component';
import { TraitListComponent } from '../screens/trait-list/trait-list.component';
import { UsersListComponent } from '../screens/users-list/users-list.component';


const routes: Routes = [
  { path: 'home', component:HomeComponent},
  { path: 'login', component:LoginComponent},
  { path: 'register', component:RegisterComponent},
  { path: 'users', component:UsersListComponent},
  { path: 'champions', component:ChampionListComponent},
  { path: 'traits', component:TraitListComponent},
  { path: 'create-champion', component:CreateChampionComponent},
  { path: 'champions/edit-champion/:id', component:CreateChampionComponent},
  { path: 'create-trait', component:CreateTraitComponent},
  { path: 'traits/edit-trait/:id', component:CreateTraitComponent},


  { path: '', pathMatch: 'full', redirectTo: '/login'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
