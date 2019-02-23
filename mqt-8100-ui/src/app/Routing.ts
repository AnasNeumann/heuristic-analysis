import { NgModule } from '@angular/core';
import { RouterModule, Routes  }   from '@angular/router';

//IMPORT COMPONENTS
import { Home } from './ts/components/home/Home';
import { Criteria } from './ts/components/criteria/Criteria';
import { Dominance } from './ts/components/dominance/Dominance';
import { SignTest } from './ts/components/signtest/SignTest';
import { Wilcoxon } from './ts/components/wilcoxon/Wilcoxon';
import { AverageTest } from './ts/components/averagetest/AverageTest';
import { Estimate } from './ts/components/estimate/Estimate';
import { Bound } from './ts/components/bound/Bound';

/**
 * Module de routing
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/02/2019
 * @version 1.0
 */
const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: Home },
  { path: 'criteria', component: Criteria },
  { path: 'dominance', component: Dominance },
  { path: 'signTest', component: SignTest },
  { path: 'wilcoxon', component: Wilcoxon },
  { path: 'averageTest', component: AverageTest },
  { path: 'estimate', component: Estimate },
  { path: 'boundGenerator', component: Bound },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes,  { useHash: true })],
  exports: [ RouterModule ]
})
export class Routing {}