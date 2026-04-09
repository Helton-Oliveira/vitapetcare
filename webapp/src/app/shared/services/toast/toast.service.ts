import { inject, Injectable } from "@angular/core";
import { ToastrService, ActiveToast } from 'ngx-toastr';

@Injectable({ providedIn: 'root' })
export class ToastService {

    private toastrService = inject(ToastrService);

    private configs: Partial<any> = {
        timeOut: 3000,
        closeButton: true,
        progressBar: true,
        progressAnimation: 'decreasing' as const,
        positionClass: 'toast-top-center',
    };

    onSuccess(message: string, title: string): ActiveToast<any> {
        return this.toastrService.success(message, title, { ...this.configs })
    }

    onError(message: string, title: string): ActiveToast<any> {
        return this.toastrService.error(message, title, { ...this.configs });
    }
}