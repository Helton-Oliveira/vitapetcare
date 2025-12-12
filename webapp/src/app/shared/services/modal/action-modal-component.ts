import {Component} from '@angular/core';
import {ModalRef} from './ModalRef';

@Component({
  selector: 'app-confirm-modal',
  template: `
    <!-- WRAPPER FIXO QUE CENTRALIZA E BLOQUEIA TUDO -->
    <div class="fixed inset-0 z-50 flex items-start justify-center pt-12">

      <!-- BACKDROP -->
      <div class="absolute inset-0 bg-black/45 backdrop-blur-sm animate-fadeIn"></div>

      <!-- MODAL -->
      <div
        id="actionModal"
        class="relative bg-white shadow-xl rounded-md w-[500px] max-w-[95%]
           border border-gray-200 animate-dropIn"
      >

        <!-- Cabeçalho -->
        <div class="px-6 py-4 border-b border-gray-200">
          <h2 class="text-xl font-semibold text-gray-800">Confirmação</h2>
        </div>

        <!-- Corpo -->
        <div class="px-6 py-4">
          <p class="text-gray-700 text-base leading-relaxed">
            Tem certeza que deseja prosseguir?
          </p>
        </div>

        <!-- Rodapé -->
        <div class="px-6 py-4 border-t border-gray-200 flex justify-end gap-3">
          <button
            class="px-4 py-2 font-bold text-lg rounded bg-gray-200 text-gray-800 hover:bg-gray-300"
            (click)="cancel()"
          >
            Cancel
          </button>

          <button
            class="px-4 py-2 font-bold text-lg rounded bg-red-600 text-white hover:bg-red-700"
            (click)="confirm()"
          >
            Confirm
          </button>
        </div>

      </div>
    </div>

  `,
})
export class ActionModalComponent {
  modalRef!: ModalRef;

  confirm() {
    this.modalRef.confirm();
  }

  cancel() {
    this.modalRef.dismiss();
  }
}
