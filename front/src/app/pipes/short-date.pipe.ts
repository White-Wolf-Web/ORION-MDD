import { Pipe, PipeTransform } from '@angular/core';


// Déclaration d'un pipe Angular avec le nom 'shortDate'
@Pipe({
  name: 'shortDate',
  standalone: true,
  
})


export class ShortDatePipe implements PipeTransform {
  transform(value: string): string {
    const date = new Date(value);
    return date.toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  }
}
