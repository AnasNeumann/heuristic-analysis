import { Injectable } from '@angular/core';

/**
 * Local data Service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/08/2017
 * @version 1.0
 */
@Injectable()
export class LocalService {

    /**
     * Constructor
     */
    constructor(){
        
    }

    /**
     * Verifier si le user est connecté
     */
    public isConnected() : boolean{
        return this.getBoolean("MQTconnected");
    }

    /**
     * Verifier si le user est connecté et administrateur
     */
    public isAdmin() : boolean{
        return this.getBoolean("MQTisAdmin");
    }

    /**
     * get a string
     * @param key
     */
    public get(key : string) : string{
       return localStorage.getItem(key);
    }
    
    /**
     * get a number
     * @param key
     */
    public getInt(key : string) : number{
        return parseInt(this.get(key));
    }
    
    /**
     * get a boolean
     * @param key
     */
    public getBoolean(key : string)  :boolean{
        return this.get(key) == 'true';
    }
    
    /**
     * get a complex object
     * @param key
     */
    public getObject(key: string) : any{
        return JSON.parse(this.get(key));
    }
    
    /**
     * save a simple object
     * @param key
     * @param value
     */
    public set(key : string, value : any){
        localStorage.setItem(key, value);
    }
    
    /**
     * save a complex object
     * @param key
     * @param value
     */
    public setObject(key : string, value : any){
        this.set(key, JSON.stringify(value));
    }
    
    /**
     * destroy a cookie
     * @param key
     */
    public destroy(key :string){
        localStorage.removeItem(key);
    }
    
}