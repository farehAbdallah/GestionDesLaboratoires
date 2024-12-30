import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {NzFormControlComponent, NzFormDirective, NzFormItemComponent} from 'ng-zorro-antd/form';
import {NzInputDirective, NzInputGroupComponent} from 'ng-zorro-antd/input';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzCheckboxComponent} from 'ng-zorro-antd/checkbox';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd/message';
import {LoginService} from '../../services/login.service';
import {LaboratoireService} from '../../services/laboratoires.service';


interface ItemData {
  id: string;
  email: string;
  password: string;
  name: string;
  role: string;
  fkIdLaboratoire: string;
  profession: string;
  signature: string;
}
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NzFormItemComponent,
    NzFormControlComponent,
    NzInputGroupComponent,
    NzColDirective,
    NzButtonComponent,
    NzRowDirective,
    NzCheckboxComponent,
    NzInputDirective,
    NzFormDirective,
    NzIconDirective
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  loginForm!: FormGroup;
  listOfUsers: ItemData[] = [];
  logguedUser: any;


  constructor(private fb: FormBuilder, private userService: UserService, private router:Router, private message: NzMessageService, private loginService: LoginService, private laboService: LaboratoireService) {

  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: this.fb.control('', [Validators.email, Validators.required]),
      password: this.fb.control('', [Validators.required]),
    });

    this.userService.getUsers().subscribe(users => {
      this.listOfUsers = users;
    })
    this.loginService.getLoged().subscribe(item => {
      this.loginService.loggedUser = item; // Adjust this assignment based on the structure
      this.logguedUser = item;
      console.log('Loged User response:', this.logguedUser);
    });
  }


  logIn(): void {
    const user = this.listOfUsers.find((findUser:any) =>{
      return findUser.email === this.loginForm.value.email && findUser.password === this.loginForm.value.password;
    })

    if (user) {
      console.log(user);
      this.message.success('Connecter avec succes');
      this.loginService.loggedUser = user;
      this.loginService.updateLoggedUser(user).subscribe({
        next: () => {
          if(user.role === 'technicien'){
            this.router.navigate(['labo']); // Navigate to a new page if needed
          } else {
            this.laboService.setSelectedLabo(user.fkIdLaboratoire);
            this.router.navigate(['labo', user.fkIdLaboratoire, 'profilelabo']);
          }
        },
        error: (err) => {
          this.message.error("Erreur lors de l'enregistrement de l'utilisateur connecté.");
        },
      });
    } else {
      this.message.error('Connexion échouée');
    }
  }



}
