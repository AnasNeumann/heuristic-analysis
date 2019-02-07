import {Headers, RequestOptions, Http, URLSearchParams} from '@angular/http';
import {LocalService} from './LocalService';
import 'rxjs/add/operator/toPromise';
import { Injectable, Inject } from '@angular/core';

/**
 * Classe d'objet renvoyée par avs-ws
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 16/08/2017
 * @version 1.0
 */
export class MQTResponse{
    buildDate : Date;
    code : number;
    errors : any = {};
    id : number ;
    message : string;
    one : any;
    many : any[];
}

/*
 * Web Service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/08/2017
 * @version 1.0
 */
@Injectable()
export class WebService {
    private MQT_WS_API : string = "";

    /**
     * Constructor
     * @param http
     */
    constructor(private http: Http, private localService : LocalService, @Inject('conf') private conf : string) {
        this.MQT_WS_API = conf;
    }

     /**
      * Construire un objet de requête
      * @param data
      */
     public buildOauthRequest(data){
         var base = {
               user :  this.localService.getInt("MQTuser"),
               access  : this.localService.get("MQTaccess")
         };
         for(var param in data){
             base[param] = data[param];
         }
         return  base;
     }

     /**
      * get option for sending json data
      */
     private option(data :any, isMultipart : boolean) : RequestOptions{
         var content = isMultipart? "multipart/form-data;charset=UTF-8": "application/json;charset=UTF-8";
         let options = new RequestOptions();
         options.params = this.build(data);
         options.headers = new Headers({
                 "Content-Type": content,
                 "Accept": "application/json;charset=utf-8",
                 "Access-Control-Allow-Origin" : this.MQT_WS_API
         });
         options.withCredentials = true;
         return options;
     }

     /**
      * build data for body
      * @param data
      */
     private build(data){
         let params = new URLSearchParams();
         for(var param in data){
             params.set(param, data[param]);
         }
         return params;
     }

     /**
      * get data of path
      * @param path
      * @param data
      * @param isMultipart
      */
     public get(path : string, data : any): Promise<MQTResponse> {
         return this.http.get(this.MQT_WS_API+path, this.option(data, false)).toPromise().then(response => response.json() as MQTResponse);
     }

    /**
     * get data of path
     * @param path
     * @param data
     */
     public getOrRetry(path : string, data : any): Promise<MQTResponse> {
         var args = this.buildOauthRequest(data);
         return this.http.get(this.MQT_WS_API+path, this.option(args, false))
             .toPromise()
             .then(response => response.json() as MQTResponse)
             .catch(error => this.updateOauth2Access(this.retryToGet, path, data, this, error));
     }
     
     /**
      * retry after get
      * @param path
      * @param data
      * @param that
      */
     public retryToGet(path : string, data : any, that : any) : Promise<MQTResponse> {
         var args = that.buildOauthRequest(data);
         return that.http.get(that.MQT_WS_API+path, that.option(args, false)).toPromise().then(response => response.json() as MQTResponse);
     }
     

     /**
      * post data to path
      * @param path
      * @param data
      * @param isMultipart
      */
     public post(path : string, data : any): Promise<MQTResponse> {
         return this.http.post(this.MQT_WS_API+path, null, this.option(data, false)).toPromise().then(response => response.json() as MQTResponse);
     }

     /**
      * post data to path
      * @param path
      * @param data
      * @param auth
      */
      public postOrRetry(path : string, data : any): Promise<MQTResponse> {
          var args = this.buildOauthRequest(data);
          return this.http.post(this.MQT_WS_API+path, null, this.option(args, false))
              .toPromise()
              .then(response => response.json() as MQTResponse)
              .catch(error => this.updateOauth2Access(this.retryToPost, path, data, this, error));
      }
      
      /**
       * retry to post data to path
       * @param path
       * @param data
       * @param auth
       */
       public retryToPost(path : string, data : any, that : any ): Promise<MQTResponse> {
           var args = that.buildOauthRequest(data);
           return that.http.post(that.MQT_WS_API+path, null, that.option(args, false)).toPromise().then(response => response.json() as MQTResponse);
       }

     /**
      * upload data to path
      * @param path
      * @param data
      * @param isMultipart
      */
     public upload(path : string, data : any): Promise<MQTResponse> {
         let formData:FormData = new FormData();
         var args = this.buildOauthRequest(data);
         for(var param in args){
             formData.append(param, args[param]);
         }
         var completePath : string = this.MQT_WS_API+path;
         var that = this;
         return new Promise(function (resolve, reject) {
             var request = new XMLHttpRequest();
             request.open('POST', completePath);
             request.onload = function () {
                 if (request.status >= 200 && request.status < 300) {
                   resolve(JSON.parse(request.response));
                 } else {
                   that.updateOauth2Access(that.retryToUpload, path, data, that, null);
                   reject({
                     status: request.status,
                     statusText: request.statusText
                   });
                 }
               };
               request.onerror = function () {
                 that.updateOauth2Access(that.retryToUpload, path, data, that, null);
                 reject({
                   status: request.status,
                   statusText: request.statusText
                 });
             };
             request.send(formData);
         });
     }
     
     /**
      * retry to upload data to path
      * @param path
      * @param data
      * @param isMultipart
      */
     public retryToUpload(path : string, data : any, that : any): Promise<MQTResponse> {
         let formData:FormData = new FormData();
         var args = that.buildOauthRequest(data);
         for(var param in args){
             formData.append(param, args[param]);
         }
         var completePath : string = that.MQT_WS_API+path;
         return new Promise(function (resolve, reject) {
             var request = new XMLHttpRequest();
             request.open('POST', completePath);
             request.onload = function () {
                 resolve(JSON.parse(request.response));
               };
               request.onerror = function () {
                 reject({
                   status: request.status,
                   statusText: request.statusText
                 });
             };
             request.send(formData);
         });
     }

     /**
      * put data to path
      * @param path
      * @param data
      * @param isMultipart
      */
    public put(path : string, data : any): Promise<MQTResponse> {
        return this.http.put(this.MQT_WS_API+path, null, this.option(data, false)).toPromise().then(response => response.json() as MQTResponse);
    }

    /**
     * put data to path
     * @param path
     * @param data
     */
     public putOrRetry(path : string, data : any): Promise<MQTResponse> {
         var args = this.buildOauthRequest(data);
         return this.http.put(this.MQT_WS_API+path, null, this.option(args, false))
             .toPromise()
             .then(response => response.json() as MQTResponse)
             .catch(error => this.updateOauth2Access(this.retryToPut, path, data, this, error));
     }
     
     /**
      * retry to put data to path
      * @param path
      * @param data
      */
      public retryToPut(path : string, data : any, that : any): Promise<MQTResponse> {
          var args = that.buildOauthRequest(data);
          return that.http.put(that.MQT_WS_API+path, null, that.option(args, false)).toPromise().then(response => response.json() as MQTResponse);
      }

    /**
     * delete data to path
     * @param path
     * @param data
     * @param isMultipart
     */
    public delete(path : string, data : any): Promise<any> {
        return this.http.delete(this.MQT_WS_API+path, this.option(data, false)).toPromise().then(response => response.json());
    }

    /**
     * delete data to path
     * @param path
     * @param data
     */
     public deleteOrRetry(path : string, data : any): Promise<MQTResponse> {
         var args = this.buildOauthRequest(data);
         return this.http.delete(this.MQT_WS_API+path, this.option(args, false))
             .toPromise()
             .then(response => response.json() as MQTResponse)
             .catch(error => this.updateOauth2Access(this.retryToDelete, path, data, this, error));
     }
     
     /**
      * delete data to path
      * @param path
      * @param data
      */
      public retryToDelete(path : string, data : any, that : any): Promise<MQTResponse> {
          var args = that.buildOauthRequest(data);
          return that.http.delete(that.MQT_WS_API+path, that.option(args, false)).toPromise().then(response => response.json() as MQTResponse);
      }

     /**
      * To call before recalling a service
      * @param callback
      * @param path
      * @param params
      * @param newData
      */
     beforeCallback(callback, path : string, params : any, newData, that : any): Promise<any>{
         for(var key in newData){
             this.localService.set(key, newData[key]);
         }
         return callback(path, params, that);
     }

    /**
     * System d'authentification Auth2.0 : get access token
     * @param callback
     * @param path
     * @param params
     */
    private updateOauth2Access(callback, path : string, params : any, that : any, e : any) : Promise<any>{
        var data = {
               user :  this.localService.getInt("MQTuser"),
               refresh  : this.localService.get("MQTrefresh")
        };
        return this.post("/access", data)
            .then(response =>  this.beforeCallback(callback, path, params, {"MQTaccess" : response.message}, that))
            .catch(error => this.updateOauth2Refresh(callback, path, params, that, error));
    }

    /**
     * System d'authentification Auth2.0 : get refresh token
     * @param callback
     * @param path
     * @param params
     */
    private updateOauth2Refresh(callback, path : string, params : any, that : any, e : any) : Promise<any>{
        var data = {
             mail :  this.localService.get("MQTmail"),
             password  : this.localService.get("MQTpassword")
        };
        return this.post("/refresh", data)
            .then(response => this.beforeCallback(callback, path, params, {"MQTaccess" : response.one.access, "MQTrefresh" : response.one.refresh}, that))
            .catch(error => Promise.reject(error.message || error));
    }

    /**
     * Create a new session
     * @param mail
     * @param password
     * @param acess
     * @param refresh
     * @param user
     * @param profile
     */
    public createSession(mail: string, password : string, access : string, refresh : string, user : number, profile : any, isAdmin : boolean){
        this.localService.set("MQTconnected", true);
        this.localService.set("MQTisAdmin", isAdmin);
        this.localService.set("MQTmail", mail);
        this.localService.set("MQTpassword", password);
        this.localService.set("MQTaccess", access);
        this.localService.set("MQTrefresh", refresh);
        this.localService.set("MQTuser", user);
        this.localService.setObject("MQTprofile", profile);
    }

    /**
     * Destroy a complete user sessions
     */
    public destorySession(){
        this.localService.set("MQTconnected", false);
        this.localService.set("MQTisAdmin", false);
        this.localService.destroy("MQTmail");
        this.localService.destroy("MQTpassword");
        this.localService.destroy("MQTaccess");
        this.localService.destroy("MQTrefresh");
        this.localService.destroy("MQTuser");
        this.localService.destroy("MQTprofile");
    }
}
