import { Component, OnInit, NgModule, TemplateRef, Renderer, Input } from '@angular/core';
import { GenericComponent } from '../GenericComponent';
import { LocalService } from '../../services/LocalService';
import { MessageService } from '../../services/MessageService';
import { WebService } from '../../services/WebService';
import { ActivatedRoute, Router} from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

/**
 * Component d'un boutton pour partager sur facebook
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/11/2017
 * @version 1.0
 */
@Component({
  selector: 'mqt-facebook',
  templateUrl: 'facebook.html'
})
export class Facebook extends GenericComponent {
    @Input() path: string;
    @Input() id : number;

   /**
    * Constructor
    * @param localService
    * @param i18NService
    */
    constructor(private messageService : MessageService, private renderer: Renderer, private localService : LocalService) {       
        super(messageService, renderer, localService);       
    }
   
}