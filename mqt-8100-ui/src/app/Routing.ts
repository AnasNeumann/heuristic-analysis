import { NgModule } from '@angular/core';
import { RouterModule, Routes  }   from '@angular/router';

//IMPORT COMPONENTS
import { Home } from './ts/components/home/Home';

/**
 * Module de routing
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/02/2019
 * @version 1.0
 */
const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: Home },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes,  { useHash: true })],
  exports: [ RouterModule ]
})
export class Routing {}