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
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent
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
    ToastrModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
