import {Injectable, signal} from '@angular/core';
import {PageDefinition} from '../../buiderls/page-definition';

@Injectable({providedIn: 'root'})
export class PageService {

  private readonly _page = signal<PageDefinition | null>(null);

  readonly page = this._page.asReadonly();

  setPage(page: PageDefinition) {
    this._page.set(page);
  }

  clearPage() {
    this._page.set(null);
  }
}
