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
  files: any = []
  loading: boolean | undefined;
  signUpDto = new AuthSignUpDto();

  password2 = '';
  constructor(private authService: AuthService,  private sanitizer: DomSanitizer, private rest: RestService) { }

  ngOnInit(): void {
  }

  doSignUp(){
    this.authService.register(this.signUpDto).subscribe(data =>{
        data.userRoles= 'ADMIN';
    })
  }

  public onFileSelected(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();

      reader.onload = (event: ProgressEvent) => {
       (<FileReader>event.target).result;
      }

      reader.readAsDataURL(event.target.files[0]);
    }
  }
}
