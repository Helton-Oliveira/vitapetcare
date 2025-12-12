import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'rolePipe',
  standalone: true
})
export class RolePipe implements PipeTransform {

  transform(roleKey: string | null | undefined): string {
    switch (roleKey) {
      case 'ADMIN':
        return 'inline-flex items-center rounded-md bg-yellow-400/20 px-2 py-1 text-xs font-medium text-yellow-500 inset-ring inset-ring-yellow-400/30';
      case 'CLIENT':
        return 'inline-flex items-center rounded-md bg-red-400/10 px-2 py-1 text-xs font-medium text-red-400 inset-ring inset-ring-red-400/20';
      case 'MANAGER':
        return 'inline-flex items-center rounded-md bg-green-400/10 px-2 py-1 text-xs font-medium text-green-400 inset-ring inset-ring-green-500/20';
      case 'GROOMER':
        return 'inline-flex items-center rounded-md bg-blue-400/10 px-2 py-1 text-xs font-medium text-blue-400 inset-ring inset-ring-blue-400/30';
      case 'RECEPTIONIST':
        return 'inline-flex items-center rounded-md bg-pink-400/10 px-2 py-1 text-xs font-medium text-pink-400 inset-ring inset-ring-pink-400/20';
      case 'VETERINARIAN':
        return 'inline-flex items-center rounded-md bg-purple-400/10 px-2 py-1 text-xs font-medium text-purple-400 inset-ring inset-ring-purple-400/30';
      default:
        return 'inline-flex items-center rounded-md bg-gray-400/10 px-2 py-1 text-xs font-medium text-gray-400 inset-ring inset-ring-gray-400/20';
    }
  }
}
