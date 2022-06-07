import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './modules/app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialImportsModule } from './modules/material-imports.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './screens/login/login.component';
import { HomeComponent } from './screens/home/home.component';
import { RouterModule } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import {MatFormFieldModule} from '@angular/material/form-field';
import { RegisterComponent } from './screens/register/register.component';
import { NavbarComponent } from './screens/navbar/navbar.component';
import { CompositionComponent } from './screens/composition/composition.component';
import { ChampionComponent } from './screens/champion/champion.component';
import { UsersListComponent } from './screens/users-list/users-list.component';
import { UsersItemComponent } from './screens/users-item/users-item.component';
import { ChampionListComponent } from './screens/champion-list/champion-list.component';
import { ChampionItemComponent } from './screens/champion-item/champion-item.component';
import { TraitItemComponent } from './screens/trait-item/trait-item.component';
import { TraitListComponent } from './screens/trait-list/trait-list.component';
import { CreateChampionComponent } from './screens/create-champion/create-champion.component';
import { CreateTraitComponent } from './screens/create-trait/create-trait.component';
import { NgxPaginationModule } from 'ngx-pagination';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    NavbarComponent,
    CompositionComponent,
    ChampionComponent,
    UsersListComponent,
    UsersItemComponent,
    ChampionListComponent,
    ChampionItemComponent,
    TraitItemComponent,
    TraitListComponent,
    CreateChampionComponent,
    CreateTraitComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MaterialImportsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatFormFieldModule,
    RouterModule,
    NgxPaginationModule,
    ToastrModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
