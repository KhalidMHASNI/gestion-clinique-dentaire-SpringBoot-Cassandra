import { APP_INITIALIZER } from '@angular/core';
import { LoaderService } from './loader.service';

export function appInitializer(loaderService: LoaderService) {
  return () => {
    return new Promise<void>((resolve) => {
      setTimeout(() => {
        loaderService.hide();
        resolve();
      }, 2000);
    });
  };
}

export const appInitializerProvider = {
  provide: APP_INITIALIZER,
  useFactory: appInitializer,
  multi: true,
  deps: [LoaderService]
};
