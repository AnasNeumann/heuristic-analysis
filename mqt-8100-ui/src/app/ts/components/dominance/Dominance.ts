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
  selector: 'mqt-dominance',
  templateUrl: 'dominance.html',
  styleUrls: ['dominance.css', '../home/home.css']
})
export class Dominance extends GenericComponent {
    loaded : boolean = false;
    loaded2 : boolean = false;
    data : any[] = [];
    dominance : any[] = [];
    xAxisLabel = "Instances";
    yAxisLabel = "Valeurs obtenues";
    xAxisLabel2 = "Déviation par rapport à l'optimum (%)";
    yAxisLabel2 = "Pourcentage d'instances (%)";

    /**
     * Constructor
     * @param localService
     * @param i18NService
     */
     constructor(private modalService: BsModalService, private service : WebService, private messageService : MessageService, private renderer: Renderer, private localService : LocalService) {       
        super(messageService, renderer, localService);
        this.service.get("/heuristic", {}).then(response =>{
            for(var h in response.many){
                var newData = {
                    name : response.many[h].name,
                    series : []
                };
                for(var v in response.many[h].values){
                    var value = response.many[h].values[v];
                    newData.series.push({name : value.instance.id, value : value.value});
                }
                this.data.push(newData);
            }
            this.loaded = true;
        });
        this.service.get("/analyzer/dominance", {}).then(response =>{
              for(var h in response.many){
                  var newData = {
                      name : response.many[h].h.name,
                      series : [] 
                  };
                  for(var v in response.many[h].values){
                      var value = response.many[h].values[v];
                      newData.series.push({name : value.deviation, value : value.instances});
                  }
                  this.dominance.push(newData);
              }
              this.loaded2 = true;
        });
     }

}