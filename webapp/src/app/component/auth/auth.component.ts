import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  templateUrl: './auth.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule
  ]
})
export class AuthComponent implements OnInit {

  private fb = inject(FormBuilder);

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });


  ngOnInit(): void {
    console.log(this.form)
  }

  login(): void {
    console.log(this.form.value)
  }

  canSubmit(): boolean {
    return this.form.valid && this.form.dirty;
  }

}
