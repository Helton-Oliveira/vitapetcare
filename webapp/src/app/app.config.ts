import {ApplicationConfig, provideBrowserGlobalErrorListeners} from '@angular/core';
import {provideRouter} from '@angular/router';

import {APP_ROUTES} from './app.routes';
import {provideClientHydration, withEventReplay} from '@angular/platform-browser';
import {HttpClient, provideHttpClient, withInterceptors} from '@angular/common/http';
import {provideTranslateService, TranslateLoader} from '@ngx-translate/core';
import {createTranslateLoader} from './shared/loaders/translate.loader';
import {tokenInterceptor} from './interceptor/token-interceptor';
import {provideToastr} from 'ngx-toastr';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(APP_ROUTES),
    provideClientHydration(withEventReplay()),
    provideHttpClient(withInterceptors([tokenInterceptor])),
    provideToastr(),
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
}
