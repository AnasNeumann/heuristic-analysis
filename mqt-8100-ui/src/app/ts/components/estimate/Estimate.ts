import { Component, OnInit, NgModule, TemplateRef, Renderer } from '@angular/core';
import { GenericComponent } from '../GenericComponent';
import { LocalService } from '../../services/LocalService';
import { MessageService } from '../../services/MessageService';
import { WebService } from '../../services/WebService';
import { ActivatedRoute, Router} from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

/*
 * Component principal de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
@Component({
  selector: 'mqt-estimate',
  templateUrl: 'estimate.html',
  styleUrls: ['estimate.css', '../home/home.css']
})
export class Estimate extends GenericComponent {
    loaded : boolean = false;
    form = {
        value : 0    
    };
    estimates : any[] = [];

    /**
     * Constructor
     * @param localService
     * @param i18NService
     */
     constructor(private modalService: BsModalService, private service : WebService, private messageService : MessageService, private renderer: Renderer, private localService : LocalService) {       
        super(messageService, renderer, localService);
        this.service.get("/estimate", {}).then(response =>{
            this.estimates = response.many;
        });
     }

     /**
      * Créer une nouvelle estimation
      */
     public create(){
         this.service.post("/estimate", this.form).then(response =>{
             this.estimates.push(response.one);
             this.form = {
                 value : 0
             };
         });
     }

     /**
      * Supprimer une estimation
      */
     public delete(elt : any){
         this.service.delete("/estimate/"+elt.id, {}).then(response => {
             this.estimates.splice(this.estimates.indexOf(elt), 1);
         });
     }
}