import {Component, inject, OnInit, signal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {PageService} from './shared/services/page/page-service';
import {SidebarComponent} from './component/sidebar/sidebar.component';
import {User} from './shared/models/user/user.model';
import AuthService from './shared/services/auth/auth.service';
import {TranslateDirective} from '@ngx-translate/core';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-setup-page',
  imports: [
    CommonModule,
    RouterOutlet,
    SidebarComponent,
    NgOptimizedImage,
    FormsModule,
    TranslateDirective
  ],
  template: `
    <div class="flex w-full h-screen bg-gray-100">
      <app-sidebar [currentAccount]="user()"/>

      <section class="flex-1 h-full flex flex-col overflow-hidden p-6">

        <div class="w-full flex items-center justify-end h-20 shrink-0 gap-4 mb-2">

          <div class="flex items-center gap-4 md:gap-6">

            <div class="relative flex items-center gap-3 cursor-pointer group" (click)="toggleMenu()">
              <div class="h-10 w-10 md:h-11 md:w-11 shrink-0">
                <img
                  [ngSrc]="user().files?.[0]?.path || '../assets/logo.png'"
                  alt="User Avatar"
                  class="rounded-full object-cover shadow-sm"
                  width="44"
                  height="44"
                />
              </div>

              <div class="hidden sm:flex flex-col leading-tight">
            <span class="text-sm md:text-base font-bold text-gray-800">
              {{ user().name }}
            </span>
                <span class="text-[10px] md:text-xs text-gray-400 font-medium">
              {{ user().email }}
            </span>
              </div>
              <i class="fa-solid fa-chevron-down text-[10px] text-gray-400 transition-transform"
                 [class.rotate-180]="isOpen"></i>

              @if (isOpen) {
                <div
                  class="absolute right-0 top-full mt-2 w-full bg-white rounded-xl shadow-xl border border-gray-100 overflow-hidden z-50 animate-fadeIn">
                  <button
                    class="w-full flex items-center gap-3 px-4 py-3 text-sm text-red-600 hover:bg-red-50 transition"
                    (click)="logout()">
                    <i class="fa-solid fa-arrow-right-from-bracket"></i>
                    <span translate="global.field.logout"></span>
                  </button>
                </div>
              }
            </div>
          </div>
        </div>

        <div class="w-full flex-1 flex flex-col min-h-0">
          <nav class="py-4 flex items-center justify-between shrink-0" gap-4>
            <div class="flex flex-col">
              <h1 class="text-xl md:text-2xl lg:text-3xl font-bold text-gray-900 tracking-tight">
                {{ page()?.title }}
              </h1>
            </div>

            @if (page()?.actions?.length) {
              <div class="flex items-center gap-2 md:gap-3">
                @for (btn of page()?.actions; track btn) {
                  <button
                    class="btn-primary"
                    [style.background]="btn?.background"
                    (click)="btn?.action?.()"
                    [disabled]="!btn.isActive"
                  >
                    @if (btn?.icon) {
                      <i class="{{ btn.icon }}" [class.mr-1]="btn.title" [style.color]="btn.fontColor"></i>
                    }
                    @if (btn?.title) {
                      <span [style.color]="btn.fontColor">{{ btn.title }}</span>
                    }
                  </button>
                }
              </div>
            }
          </nav>

          <main class="w-full flex-1 overflow-y-auto min-h-0 bg-white rounded-3xl shadow-sm border border-gray-100">
            <router-outlet></router-outlet>
          </main>
        </div>
      </section>
    </div>
  `
})
export class SetupPageComponent implements OnInit {
  private pageService = inject(PageService)
  private authService = inject(AuthService)
  page = this.pageService.page;
  isOpen = false;
  user = signal<User>(new User());

  ngOnInit(): void {
    this.authService.getCurrentAccount()
      .then(res => this.user.set(res));
  }

  logout(): void {
    this.authService.logout();
  }

  toggleMenu(): void {
    this.isOpen = !this.isOpen;
  }
}
