import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Usuario} from './usuario';
import {HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

//CLASE SERVICIO PARA ENVIAR DATOS DE USUARIO AL BACKEND
export class AuthService {

  private _usuario: Usuario;
  private _token: string;


  constructor(private http:HttpClient) { }

  public get usuario():Usuario{
    if(this._usuario != null){
      return this._usuario;
    }else if(this._usuario == null && sessionStorage.getItem('usuario')!=null){ //si ha iniciado sesion antes
     this._usuario = JSON.parse(sessionStorage.getItem('usuario'))  as Usuario //pasa de string a JSON y luego a Usuario
    return  this._usuario
    }
    return new Usuario(); //este es el caso en que el usuario es nuevo
  }

  public get token():string{
    if(this._token != null){
      return this._token;
    }else if(this._token == null && sessionStorage.getItem('token')!=null){ //si ha iniciado sesion antes
      this._token = sessionStorage.getItem('usuario');
      return  this._token
    }
    return null;
  }


  login(usuario:Usuario):Observable<any>{
    const urlEndpoint = 'http://localhost:8080/oauth/token';
    const credenciales = btoa('angularapp' + ':' + '12345'); //encriptar base 64
    const httpHeaders = new HttpHeaders({'Content-Type':'application/x-www-form-urlencoded',
    'Authorization':'Basic ' + credenciales});

    let params = new URLSearchParams();
    params.set('grant_type','password');
    params.set('username',usuario.username);
    params.set('password', usuario.password);

    console.log( params.toString());

    return this.http.post<any>(urlEndpoint, params.toString(), {headers: httpHeaders})

  }

  guardarUsuario(accessToken:string):void{
    let payload = this.obtenerDatosToken(accessToken);
    this._usuario = new Usuario();
    this._usuario.nombre = payload.nombre;
    this._usuario.apellido = payload.apellido;
    this._usuario.email = payload.email;
    this._usuario.username= payload.username;
    this._usuario.roles= payload.roles;
    sessionStorage.setItem('usuario', JSON.stringify(this._usuario) );   //convierte un  json a string

  }

  guardarToken(accessToken:string):void{
      this._token = accessToken;
      sessionStorage.setItem('usuario', accessToken); //el token ya es un string

  }

  obtenerDatosToken(accessToken:string):any{
        if(accessToken !=null){
          return JSON.parse(atob(accessToken.split(".")[1]));//lo pasa de string a JSON
        }

        return null;

  }

}
