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
  selector: 'mqt-bound',
  templateUrl: 'bound.html',
  styleUrls: ['bound.css', '../home/home.css']
})
export class Bound extends GenericComponent {
    loaded : boolean = false;
    b : any = {};
    i : any = {};
    k : any = {};
    wins  : any[] = [];

    /**
     * Constructor
     * @param localService
     * @param i18NService
     */
     constructor(private modalService: BsModalService, private service : WebService, private messageService : MessageService, private renderer: Renderer, private localService : LocalService) {       
        super(messageService, renderer, localService);
        var win : number = 0, fail : number = 2;
        this.service.get("/engine/estimate", {}).then(response =>{
            this.b = response.one.bound;
            this.i = response.one.independence;
            this.k = response.one.kolmogorov;
            if(this.i.success){
                win++;
                fail--;
            }
            if(this.k.success){
                win++;
                fail--;
            }
            this.wins.push({"name" : "Tests réussis", "value" : win});
            this.wins.push({"name" : "Tests échoués", "value" : fail});
            this.loaded = true;
        });
     }

}