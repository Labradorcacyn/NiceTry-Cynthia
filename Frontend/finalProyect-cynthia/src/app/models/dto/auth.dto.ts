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
  avatar: string;
  static nick: any;
  static email: any;
  static lastName: any;
  static city: any;
  static rol: any;
  static password: any;
  static password2: any;
  constructor() {
      this.nick = '';
      this.email = '';
      this.city = '';
      this.password = '';
      this.password2 = '';
      this.rol = true;
      this.avatar = '';
      this.name = '';
      this.lastName = '';
  }
}
