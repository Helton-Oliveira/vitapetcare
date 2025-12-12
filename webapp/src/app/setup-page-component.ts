import {Component, inject} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {CommonModule} from '@angular/common';
import {PageService} from './shared/services/page/page-service';
import {SidebarComponent} from './component/sidebar/sidebar.component';

@Component({
  selector: 'app-setup-page',
  imports: [
    CommonModule,
    RouterOutlet,
    SidebarComponent
  ],
  template: `
    <header>HEADER AQUI</header>
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
                    class="btn-primary"
                    [class.flex]="true"
                    [class.items-center]="true"
                    [class.justify-center]="!btn.title"
                    [class.justify-start]="btn.title"
                    [style.background]="btn?.background"
                    (click)="btn?.action?.()"
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
export class SetupPageComponent {
  private pageService = inject(PageService)
  page = this.pageService.page;
}
