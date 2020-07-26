import { Component, OnInit } from '@angular/core';
import {Usuario} from './usuario';
import swal from 'sweetalert2';
import {AuthService} from './auth.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',

})
export class LoginComponent implements OnInit {

  titulo: string = 'Por favor Sign In!';
  usuario: Usuario;

  constructor(private authService: AuthService, private router: Router) {
    this.usuario = new Usuario(); //inicializacion del Usuario da lo mismo hacerlo fuera o dentro del constructor
  }

  ngOnInit() {
  }

  login(): void {
    console.log(this.usuario);

    if (this.usuario.username == null || this.usuario.password == null) {
      //alert('Error Login, username o password vacías!')
      swal('Error Login', 'Username o password vacías!');
      return;
    }
    this.authService.login(this.usuario).subscribe(response => {
      console.log(response);//JSON


      this.authService.guardarUsuario(response.access_token);
      this.authService.guardarToken(response.access_token);
      let usuario = this.authService.usuario;
      this.router.navigate(['/clientes']);
      swal('Login',`Hola ${usuario.username}, has iniciado sesión con éxito` )
      //alert(`Hola ${usuario.username}`);

    });
  }


}
