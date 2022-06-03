import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthSignUpDto } from 'src/app/models/dto/auth.dto';
import { AuthService } from 'src/app/services/auth.service';
import { RestService } from 'src/app/services/rest-service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  imagenPrevia: any;
  files: any = [];
  avatar: File | undefined;
  url: any;
  loading: boolean | undefined;
  signUpDto = new AuthSignUpDto();

  password2 = '';
  constructor(private authService: AuthService,  private sanitizer: DomSanitizer, private rest: RestService) { }

  ngOnInit(): void {
  }

  doSignUp(){
    this.authService.register(this.signUpDto, this.avatar).subscribe(resp => {
      console.log(resp);
    }, err => {
      console.log(err);
    });
  }

  readUrl(event:any) {
    if (event.target.files && event.target.files[0]) {

      this.avatar = event.target.files[0];
    }
  }
}
