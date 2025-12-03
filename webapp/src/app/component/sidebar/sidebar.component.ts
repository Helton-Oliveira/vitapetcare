import {Component, inject} from '@angular/core';
import {TranslateModule} from '@ngx-translate/core';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  imports: [
    TranslateModule,
    RouterLink,
  ]
})
export class SidebarComponent {
  private router = inject(Router);


}
