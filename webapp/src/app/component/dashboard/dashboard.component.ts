import {Component, inject, OnInit} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {PageService} from '../../shared/services/page/page-service';
import {ButtonBuilder} from '../../shared/builders/button-builder';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
  ]
})
export class DashboardComponent implements OnInit {

  dashboard: string = ''
  private page = inject(PageService);

  ngOnInit(): void {
    this.setup()
  }

  setup() {
    this.page.setPage({
      title: 'Overview',
      subtitle: 'Dashboard',
      showSearch: true,
      actions: [
        ButtonBuilder.sort({}),
        ButtonBuilder.filter({}),
        ButtonBuilder.calendar({}),
      ],
    })
  }


}
