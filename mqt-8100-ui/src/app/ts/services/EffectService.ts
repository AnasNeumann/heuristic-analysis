import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Rx';

/**
 * classe contenant tous les effets d'affichage
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/09/2017
 * @version 1.0
 */
@Injectable()
export class EffectService {

    /**
     * verify if elt exsit in array
     * @param array
     * @param attr
     * @param elt
     */
    public existInArray(array : any[], attr : string, elt : any){
        for(var a in array){           
            if(array[a][attr] == elt[attr]){
                return true;
            }
      }
        return false;
    }

    /**
     * Uint8 to String
     * @param u8a
     */
    public Uint8ToString(u8a) : string{
         var CHUNK_SZ = 0x8000;
         var c = [];
         for (var i=0; i < u8a.length; i+=CHUNK_SZ) {
           c.push(String.fromCharCode.apply(null, u8a.subarray(i, i+CHUNK_SZ)));
         }
         return btoa(c.join(""));
     }

    /**
     * String to Uint8
     * @param base64
     */
    public StringToUint8(base64) : Uint8Array {
        var binary_string =  window.atob(base64);
        var len = binary_string.length;
        var bytes = new Uint8Array( len );
        for (var i = 0; i < len; i++)        {
            bytes[i] = binary_string.charCodeAt(i);
        }
        return new Uint8Array(bytes.buffer);
    }
} 