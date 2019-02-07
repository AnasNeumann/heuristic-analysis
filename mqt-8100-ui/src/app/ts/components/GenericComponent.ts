import {LocalService} from '../services/LocalService';
import { Renderer } from '@angular/core';
import { MessageService } from '../services/MessageService';
import { Subscription } from 'rxjs/Subscription';

/**
 * Component générique
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 26/08/2017
 * @version 1.0
 */
export class GenericComponent {
    private r : Renderer;
    public subscription: Subscription;
    public shareURL = "https://www.uprodit.com/mqt-8100";
    public isAdmin  : boolean = false;  
    public isConnected : boolean = false;

    /**
     * Get the file of an object
     * @param elt
     * @param type
     * @param service
     * @param attr
     */
    public getFile(elt : any, type : string, service : any, attr){
        if(elt[attr] == null){
            setTimeout(function () {
                service.get("/file/"+type+"/"+ elt.id, {}).then(response =>{
                    elt[attr] = response.one.content;
                });
            }, this.waintingGenerator());  
        }
    }

    /**
     * Generate random time for sending request
     */
    public waintingGenerator() : number{
       return  Math.floor(Math.random() * 300) + 100;
    }

    /* Get the file of an object
    * @param elt
    * @param type
    * @param service
    * @param attr
    */
   public getFileWithId(elt : any, type : string, service : any, attr, id : string){
       if(elt[attr] == null){
           service.get("/file/"+type+"/"+ elt[id], {}).then(response =>{
               elt[attr] = response.one.content;
           });
       }
   }
    
    /**
     * Apply a filter on a array
     * @param array
     * @param attr
     * @param value
     */
    public applyFilter(array, attr, value, all){
        if(all == value || null == all){
            return array;
        }
        return array.filter(x => x[attr] <= value);
    }
    
    /**
     * Change size with animation
     * @param el
     * @param height
     */
    public setHeight(el, height) {
        this.r.setElementStyle(el, 'height', height + 'px');
    }

    /**
     * Convert date format
     * @param str
     */
    public convert(str : Date){
        var date : any = str.toString();
        var mnths = {
            Jan:"01", Feb:"02", Mar:"03", Apr:"04", May:"05", Jun:"06",
            Jul:"07", Aug:"08", Sep:"09", Oct:"10", Nov:"11", Dec:"12"
        };
        date = date.split(" ");
        return [ date[3], mnths[date[1]], date[2] ].join("-");
    }

    /**
     * get file from input
     * @param event
     */
    public getInputFile(event){
        var target = event.target || event.srcElement;
        return target;
    }

   /**
    * Constructor
    * @param localService
    * @param i18NService
    */
    constructor(messageService : MessageService, renderer: Renderer, localService : LocalService) {
       this.r = renderer;
       document.body.scrollTop = document.documentElement.scrollTop = 0;
       this.isAdmin = localService.isAdmin();
       this.isConnected = localService.isConnected();
       this.subscription = messageService.getMessage().subscribe(message => { 
           if("connect" == message.code){
               this.isAdmin = localService.isAdmin();
               this.isConnected = localService.isConnected();
           }
       });
    }

    /**
     * Quand le component se détruit
     */
    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
