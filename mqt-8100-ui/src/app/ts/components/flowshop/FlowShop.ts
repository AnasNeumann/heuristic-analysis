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
 * @since 04/03/2019
 * @version 1.0
 */
@Component({
  selector: 'mqt-flowshop',
  templateUrl: 'flowshop.html',
  styleUrls: ['flowshop.css', '../home/home.css']
})
export class FlowShop extends GenericComponent {
    loaded : boolean = false;
    form : any = {
            file : ""
    }

    /**
     * Constructor
     * @param localService
     * @param i18NService
     */
     constructor(private modalService: BsModalService, private service : WebService, private messageService : MessageService, private renderer: Renderer, private localService : LocalService) {       
        super(messageService, renderer, localService);
     }

    /**
     * Résoudre les instances à partir d'un fichier text
     * @param event
     */
    public solve(event){
        var file = this.getInputFile(event).files[0],
        reader = new FileReader();
        var that = this;
        reader.onload = function(e : any){
            that.form.file = e.target.result;
            that.service.upload("/solve/flowshop", that.form).then(response =>{
                console.log(response);
            });
        }
        reader.readAsText(file);
    }
}