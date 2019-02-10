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
  selector: 'mqt-wilcoxon',
  templateUrl: 'wilcoxon.html',
  styleUrls: ['wilcoxon.css', '../home/home.css', '../averagetest/averageTest.css']
})
export class Wilcoxon extends GenericComponent {
    loaded : boolean = false;
    data : any[] = [];
    order : any[] = [];
    heuristics : any[] = [];
    xAxisLabel = "Heursitiques";
    yAxisLabel = "Total rejets de Ho";

    /**
     * Constructor
     * @param localService
     * @param i18NService
     */
     constructor(private modalService: BsModalService, private service : WebService, private messageService : MessageService, private renderer: Renderer, private localService : LocalService) {       
        super(messageService, renderer, localService);
        this.service.get("/analyzer/wilcoxon", {}).then(response =>{
            for(var h in response.many){
                var newValue = {
                      name : response.many[h].h,
                      value : 0
                };
                for(var t in response.many[h].tests){
                    var result = response.many[h].tests[t];
                    if(result.value > result.w95 || result.value > result.w99){
                        newValue.value ++;
                        this.order.push({
                           inf : response.many[h].h,
                           sup : result.name
                        });
                    }
                }
                this.data.push(newValue);
            }
            this.heuristics = response.many;
            this.loaded = true;
        });
     }

}