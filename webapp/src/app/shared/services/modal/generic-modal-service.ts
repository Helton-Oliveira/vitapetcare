import {EnvironmentInjector, Injectable,} from '@angular/core';
import {Overlay} from '@angular/cdk/overlay';
import {ComponentPortal} from '@angular/cdk/portal';
import {ModalRef} from './ModalRef';

export interface ModalOptions {
  size?: 'sm' | 'md' | 'lg' | 'xl';
  inputs?: Record<string, any>;
}

@Injectable({providedIn: 'root'})
export class GenericModalService {
  constructor(
    private overlay: Overlay,
    private injector: EnvironmentInjector
  ) {
  }

  show<T>(component: any, options: ModalOptions = {}): Promise<any> {
    const overlayRef = this.overlay.create({
      hasBackdrop: true,
      backdropClass: 'modal-backdrop',
      panelClass: ['modal-panel', options.size || 'md'],
      scrollStrategy: this.overlay.scrollStrategies.block(),
      positionStrategy: this.overlay
        .position()
        .global()
        .centerVertically()
        .centerHorizontally()
    });

    const portal = new ComponentPortal(component, null, this.injector);
    const componentRef = overlayRef.attach(portal);

    const modalRef = new ModalRef();

    return new Promise((resolve, reject) => {

      modalRef.confirm = (result?: any) => {
        overlayRef.dispose();
        resolve(result);
      };

      modalRef.dismiss = () => {
        overlayRef.dispose();
        reject();
      };

      (componentRef.instance as any).modalRef = modalRef;

      if (options.inputs) {
        Object.entries(options.inputs).forEach(([key, value]) => {
          (componentRef.instance as any)[key] = value;
        });
      }

      overlayRef.backdropClick().subscribe(() => modalRef.dismiss());
    });
  }

}
