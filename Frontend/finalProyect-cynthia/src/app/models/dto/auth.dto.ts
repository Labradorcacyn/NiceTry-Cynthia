export class AuthLoginDto{
  email:string;
  password: string;

  constructor(){
      this.email ='';
      this.password ='';
  }
}

export class AuthSignUpDto {
  name: string;
  lastName: string;
  rol: boolean;
  nick: string;
  email: string;
  city: string;
  password: string;
  password2: string;
  file: string;
  constructor() {
      this.nick = '';
      this.email = '';
      this.city = '';
      this.password = '';
      this.password2 = '';
      this.rol = true;
      this.file = '';
      this.name = '';
      this.lastName = '';
  }
}
