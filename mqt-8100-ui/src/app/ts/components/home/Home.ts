import { Component, OnInit, NgModule, TemplateRef, Renderer } from '@angular/core';
import { LocalService } from '../../services/LocalService';
import { MessageService } from '../../services/MessageService';
import { WebService } from '../../services/WebService';
import { ActivatedRoute, Router} from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { GenericComponent } from '../GenericComponent';

/*
 * Component de l'acceuil
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/07/2018
 * @version 1.0
 */
@Component({
  selector: 'mqt-home',
  templateUrl: 'home.html',
  styleUrls: ['home.css']
})
export class Home extends GenericComponent {

    /**
     * Constructor
     * @param localService
     * @param i18NService
     */
     constructor(private modalService: BsModalService, private service : WebService, private messageService : MessageService, private renderer: Renderer, private localService : LocalService) {       
        super(messageService, renderer, localService);
     }
}