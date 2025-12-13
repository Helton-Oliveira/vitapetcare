import {Component, inject, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {PageService} from './shared/services/page/page-service';
import {SidebarComponent} from './component/sidebar/sidebar.component';
import {User} from './shared/models/user/user.model';
import AuthService from './shared/services/auth/auth.service';
import {TranslatePipe} from '@ngx-translate/core';
import {RolePipe} from './shared/pipes/role-pipe';

@Component({
  selector: 'app-setup-page',
  imports: [
    CommonModule,
    RouterOutlet,
    SidebarComponent,
    NgOptimizedImage,
    TranslatePipe,
    RolePipe
  ],
  template: `
    <header class="bg-pet-primary w-full h-20 flex items-center px-6 shadow-lg">

      <div class="flex items-center w-full">
        <img ngSrc="../assets/logo.png" class="w-[10%]" alt="Logo vita pet care" height="1024" width="1024"/>
      </div>

      <div class="flex items-center">
        <div class="items-center justify-center">
            <span class="text-sm text-white hidden md:inline">
                {{ 'global.field.welcome' | translate: {name: user.name} }}
            </span>
          <span [ngClass]="user.role | rolePipe">{{ "global.role." + user.role | translate }}</span>
        </div>

        <div class="h-13 w-16 bg-white/30 rounded-full border-2 border-white"></div>
      </div>
    </header>

    <div class="flex h-screen w-full bg-pet-background from-cyan-50 to-cyan-200">
      <!-- SIDEBAR -->
      <app-sidebar/>

      <!-- ÁREA PRINCIPAL -->
      <div class="flex-1 flex flex-col ">

        <!-- HEADER DINÂMICO -->
        <header class="flex justify-between items-start px-6 py-4">

          <!-- TÍTULOS -->
          <div>
            <h1 class="text-pet-primary font-semibold">
              {{ page()?.title }}
            </h1>

            @if (page()?.subtitle) {
              <p class="text-lg text-gray-500">
                {{ page()?.subtitle }}
              </p>
            }
          </div>

          <!-- SEARCH + ACTIONS -->
          <div class="flex items-center gap-4">

            <!-- ACTION BUTTONS -->
            @if (page()?.actions?.length) {
              <div class="flex gap-2">
                @for (btn of page()?.actions; track btn) {
                  <button
                    class="btn-primary "
                    [class.flex]="true"
                    [class.items-center]="true"
                    [class.justify-center]="!btn.title"
                    [class.justify-start]="btn.title"
                    [style.background]="btn?.background"
                    (click)="btn?.action?.()"
                    [disabled]="!btn.isActive"
                    [ngClass]="!btn.isActive ? 'opacity-50 hover:cursor-auto' : '' "
                  >
                    @if (btn.icon) {
                      <i
                        class="{{ btn.icon }} fa-2xl"
                        [class.mr-2]="btn.title"
                        [style.color]="btn.fontColor"
                      ></i>
                    }
                    @if (btn.title) {
                      <span class="text-2xl font-medium" [style.color]="btn.fontColor">{{ btn.title }}</span>
                    }

                  </button>
                }
              </div>
            }
          </div>

        </header>

        <!-- CONTEÚDO DAS ROTAS -->
        <main class="px-6 pb-6 w-full h-full">
          <router-outlet></router-outlet>
        </main>

      </div>
    </div>

  `
})
export class SetupPageComponent implements OnInit {
  private pageService = inject(PageService)
  private authService = inject(AuthService)
  page = this.pageService.page;
  user = new User();

  ngOnInit(): void {
    this.authService.getCurrentAccount()
      .then(res => this.user = res);
  }
}
