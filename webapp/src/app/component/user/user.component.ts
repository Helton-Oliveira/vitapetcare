import {Component, inject, OnInit} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {PageService} from '../../shared/services/page/page-service';
import {ButtonBuilder} from '../../shared/buiderls/button-builder';
import {User} from '../../shared/models/user/user.model';
import {Router} from '@angular/router';
import {UserService} from '../../shared/services/user/user-service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './user.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
  ]
})
export class UserComponent implements OnInit {
  private page = inject(PageService);
  private router = inject(Router)
  private userService = inject(UserService)

  public users: User[] = [];

  ngOnInit(): void {
    this.setup()
    this.userService.getAll()
      .then(res => this.users = res.content)
  }

  setup() {
    this.page.setPage({
      title: 'Users',
      subtitle: 'usuarios',
      showSearch: true,
      actions: [
        ButtonBuilder.sort({}),
        ButtonBuilder.filter({}),
        ButtonBuilder.add({
          title: "Novo",
          action: () => {
            this.router.navigate(['app', 'user-update'])
          }
        }),
      ],
    })
  }


}
