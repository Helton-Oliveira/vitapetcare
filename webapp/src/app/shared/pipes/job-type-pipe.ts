import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'jobTypePipe',
  standalone: true
})
export class JobTypePipe implements PipeTransform {

  transform(jobType: string | null | undefined): string {
    switch (jobType) {
      case 'CONSULTATION':
        return 'inline-flex items-center rounded-md bg-indigo-400/10 px-2 py-1 text-xs font-medium text-indigo-400 inset-ring inset-ring-indigo-400/20';
      case 'EXAMINATION':
        return 'inline-flex items-center rounded-md bg-cyan-400/10 px-2 py-1 text-xs font-medium text-cyan-400 inset-ring inset-ring-cyan-400/20';
      case 'SURGERY':
        return 'inline-flex items-center rounded-md bg-orange-400/10 px-2 py-1 text-xs font-medium text-orange-400 inset-ring inset-ring-orange-400/20';
      case 'HOSPITALIZATION':
        return 'inline-flex items-center rounded-md bg-sky-400/10 px-2 py-1 text-xs font-medium text-sky-400 inset-ring inset-ring-sky-400/20';
      case 'EMERGENCY':
        return 'inline-flex items-center rounded-md bg-rose-400/10 px-2 py-1 text-xs font-medium text-rose-400 inset-ring inset-ring-rose-400/20';
      case 'BATH':
        return 'inline-flex items-center rounded-md bg-teal-400/10 px-2 py-1 text-xs font-medium text-teal-400 inset-ring inset-ring-teal-400/20';
      case 'GROOMING':
        return 'inline-flex items-center rounded-md bg-lime-400/10 px-2 py-1 text-xs font-medium text-lime-400 inset-ring inset-ring-lime-400/20';
      case 'BATH_AND_GROOMING':
        return 'inline-flex items-center rounded-md bg-emerald-400/10 px-2 py-1 text-xs font-medium text-emerald-400 inset-ring inset-ring-emerald-400/20';
      case 'SANITARY_GROOMING':
        return 'inline-flex items-center rounded-md bg-fuchsia-400/10 px-2 py-1 text-xs font-medium text-fuchsia-400 inset-ring inset-ring-fuchsia-400/20';
      case 'STAY':
        return 'inline-flex items-center rounded-md bg-violet-400/10 px-2 py-1 text-xs font-medium text-violet-400 inset-ring inset-ring-violet-400/20';
      case 'NOT_INFORMED':
        return 'inline-flex items-center rounded-md bg-slate-400/10 px-2 py-1 text-xs font-medium text-slate-400 inset-ring inset-ring-slate-400/20';
      default:
        return 'inline-flex items-center rounded-md bg-gray-400/10 px-2 py-1 text-xs font-medium text-gray-400 inset-ring inset-ring-gray-400/20';
    }
  }
}
