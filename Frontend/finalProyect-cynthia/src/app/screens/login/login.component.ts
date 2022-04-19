import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthLoginDto } from 'src/app/models/dto/auth.dto';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginDto = new AuthLoginDto();
  hide = true;
  constructor(private authService: AuthService, private router: Router, private toastSvc: ToastrService) { }

  ngOnInit(): void {
  }

  doLogin(){
    this.authService.login(this.loginDto).subscribe(res=>{
      localStorage.setItem('token', res.token);
      localStorage.setItem('nombreUser', res.nick);
      localStorage.setItem('avatar', res.avatar);

      if(AuthenticatorAssertionResponse){
        if(res.role == "ADMIN"){
          this.toastSvc.success('Bienvenido ' + res.nick, 'Login');
          this.router.navigate(["/home"]);
        }else
          this.toastSvc.error('No tiene permisos para acceder a esta sección', 'Login');
      }else{
        this.toastSvc.error('No tiene permisos para acceder a esta sección', 'Login');
      }
    }, err=>{
      this.toastSvc.error('Usuario o contraseña incorrectos', 'Login');
    });
  }
}
