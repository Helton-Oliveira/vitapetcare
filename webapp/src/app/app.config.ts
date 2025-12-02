import {ApplicationConfig, provideBrowserGlobalErrorListeners} from '@angular/core';
import {provideRouter} from '@angular/router';

import {APP_ROUTES} from './app.routes';
import {provideClientHydration, withEventReplay} from '@angular/platform-browser';
import {HttpClient, provideHttpClient} from '@angular/common/http';
import {provideTranslateService, TranslateLoader} from '@ngx-translate/core';
import {createTranslateLoader} from './shared/loaders/translate.loader';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(APP_ROUTES),
    provideClientHydration(withEventReplay()),
    provideHttpClient(),
    provideTranslateService({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      },
      lang: 'pt_br',
      fallbackLang: 'pt_br'
    }),
  ]
};
