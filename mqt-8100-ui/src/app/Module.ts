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
import { Criteria } from './ts/components/criteria/Criteria';
import { Dominance } from './ts/components/dominance/Dominance';
import { Menu } from './ts/components/menu/Menu';
import { SignTest } from './ts/components/signtest/SignTest';
import { Wilcoxon } from './ts/components/wilcoxon/Wilcoxon';
import { AverageTest } from './ts/components/averagetest/AverageTest';
import { Estimate } from './ts/components/estimate/Estimate';
import { Bound } from './ts/components/bound/Bound';

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
    Criteria,
    Dominance,
    Menu,
    SignTest,
    Wilcoxon,
    AverageTest,
    Estimate,
    Bound
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
