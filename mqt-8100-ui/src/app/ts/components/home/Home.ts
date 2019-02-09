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
    formInstance = {
      optimal : 0    
    };
    formHeuristic = {
      name : ""
    };
    formValue = {
       h : 0,
       i : 0,
       value : 0
    };
    instances : any[] = [];
    heuristics : any[] = [];

    /**
     * Constructor
     * @param localService
     * @param i18NService
     */
     constructor(private modalService: BsModalService, private service : WebService, private messageService : MessageService, private renderer: Renderer, private localService : LocalService) {       
        super(messageService, renderer, localService);
        this.service.get("/instance", {}).then(response =>{
            this.instances = response.many;
            if(response.many.length > 0){
                this.formValue.i = response.many[0].id;
            }
        });
        this.service.get("/heuristic", {}).then(response =>{
            this.heuristics = response.many;
            if(response.many.length > 0){
                this.formValue.h = response.many[0].id;
            }
        });
     }

     /**
      * Créer une nouvelle instance
      */
     public createInstance(){
         this.service.post("/instance", this.formInstance).then(response =>{
             this.instances.push(response.one);
             for(var h in this.heuristics){
                 this.heuristics[h].values.push({value:0,instance:response.one,heuristicId:this.heuristics[h].id});
             }
             this.formInstance = {
                     optimal : 0    
             };
         });
     }

     /**
      * Créer une nouvelle heuristic
      */
     public createHeuristic(){
         this.service.post("/heuristic", this.formHeuristic).then(response =>{
             this.heuristics.push(response.one);
             this.formHeuristic = {
                     name : ""
             };
         });
     }

     /**
      * Supprimer une insance
      */
     public deleteInstance(elt : any){
         this.service.delete("/instance/"+elt.id, {}).then(response => {
             this.instances.splice(this.instances.indexOf(elt), 1);
             for(var h in this.heuristics){
                 for(var v in this.heuristics[h].values){
                     if(this.heuristics[h].values[v].instance.id == elt.id){
                         this.heuristics[h].values.splice(this.heuristics[h].values.indexOf(this.heuristics[h].values[v]), 1);
                     }
                 }
             }
         });
     }

     /**
      * Supprimer une heuristic
      */
     public deleteHeuristic(elt : any){
         this.service.delete("/heuristic/"+elt.id, {}).then(response => {
             this.heuristics.splice(this.heuristics.indexOf(elt), 1);
         });
     }

     /**
      * Affecter une valeur à une heuristic
      */
     public affect(){
         this.service.post("/heuristic/"+this.formValue.h+"/"+this.formValue.i, this.formValue).then(response => {
             for(var h in this.heuristics){
                 if(this.heuristics[h].id == this.formValue.h){
                     for(var v in this.heuristics[h].values){
                         if(this.heuristics[h].values[v].instance.id == this.formValue.i){
                             this.heuristics[h].values[v].value = this.formValue.value;
                         }
                     }
                 }
             }
             this.formValue.value = 0;
         });
     }
}