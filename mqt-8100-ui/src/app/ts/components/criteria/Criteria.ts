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
 * @since 07/02/2019
 * @version 1.0
 */
@Component({
  selector: 'mqt-criteria',
  templateUrl: 'criteria.html',
  styleUrls: ['criteria.css', '../home/home.css']
})
export class Criteria extends GenericComponent {
    heuristics : any[] = [];
    maxDeviations : any[] = [];
    optimal : any[] = [];
    worst  : any[] = [];
    xAxisLabel = "Heursitiques";
    yAxisLabel = "Deviation maximale";


    /**
     * Constructor
     * @param localService
     * @param i18NService
     */
     constructor(private modalService: BsModalService, private service : WebService, private messageService : MessageService, private renderer: Renderer, private localService : LocalService) {       
        super(messageService, renderer, localService);
        this.service.get("/analyzer/criteria", {}).then(response =>{
            this.heuristics = response.many;
            for(var h in response.many){
                this.maxDeviations.push({"name" : response.many[h].h.name, "value" : response.many[h].maxDeviation});
                this.optimal.push({"name" : response.many[h].h.name, "value" : response.many[h].nbrOptimal});
                this.worst.push({"name" : response.many[h].h.name, "value" : response.many[h].nbrWorstValue});
            }
            this.loaded = true;
        });
     }

}