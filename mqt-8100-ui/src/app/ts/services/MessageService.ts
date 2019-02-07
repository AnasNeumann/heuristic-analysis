import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs';

/**
 * Service d'envoi de message entre les components
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 20/08/2017
 * @version 1.0
 */
@Injectable()
export class MessageService {
    private subject = new Subject<any>();
    
   /**
    * Send a new message
    * @param message
    * @param code
    */
    public sendMessage(code : string, message: string) {
        this.subject.next({ code : code, text: message });
    }
    
    /**
     * Send only a code message
     * @param code
     */
    public sendCode(code : string){
        this.subject.next({code : code});
    }
 
    /**
     * Clear a message value
     */
    public clearMessage() {
        this.subject.next();
    }
 
    /**
     * Recieve messages
     */
    public getMessage(): Observable<any> {
        return this.subject.asObservable();
    }
}