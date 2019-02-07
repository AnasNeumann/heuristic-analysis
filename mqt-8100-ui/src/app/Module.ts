import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpModule } from '@angular/http';

//IMPORT MODULES
import { CommonModule as Common } from './ts/modules/CommonModule';

// IMPORT DE LA CONFIGURATION
import { config } from '../config';

//IMPORT COMPONENTS
import { Main } from './ts/components/main/Main';
import { Home } from './ts/components/home/Home';

// OTHER IMPORTS
import { Routing } from './Routing';

// IMPORT SERVICES
import { LocalService } from './ts/services/LocalService';
import { WebService } from './ts/services/WebService';
import { MessageService } from './ts/services/MessageService';
import { EffectService } from './ts/services/EffectService';

@NgModule({
  declarations: [
    Main,
    Home,
  ],
  imports: [
    CommonModule,
    Common,
    HttpModule,
    BrowserModule,
    BrowserAnimationsModule,
    Routing
  ],
  providers: [
      Main,      
      { provide: "conf", useValue: config.ws_url },
      LocalService,
      WebService,
      MessageService,
      EffectService
  ],
  bootstrap: [Main]
})
export class Module {}
