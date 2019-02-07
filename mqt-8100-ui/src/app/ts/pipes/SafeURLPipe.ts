import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer} from '@angular/platform-browser';

/**
 * Pipe pour gérer les url sécurisées
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/09/2017
 * @version 1.0
 */
@Pipe({ name: 'safeURL' })
export class SafeURLPipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) {}
  transform(url) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}