// IMPORT ANGULAR PACKAGES
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { MatToolbarModule, MatIconModule, MatNativeDateModule, MatMenuModule, MatButtonModule, MatSlideToggleModule, MatDatepickerModule, MatSelectModule, MatTabsModule, MatProgressSpinnerModule, MatSidenavModule, MatSnackBarModule, MatSnackBar, MatInputModule } from '@angular/material';

//IMPORT LIBS AND FRAMEWORKS
import { CarouselModule, BsDropdownModule, ModalModule, TabsModule, ButtonsModule, TooltipModule, CollapseModule } from 'ngx-bootstrap';
import { InfiniteScrollModule } from 'angular2-infinite-scroll';
import { QuillModule } from 'ngx-quill';
import { NgxChartsModule } from '@swimlane/ngx-charts';

//IMPORT PIPES
import { EscapeHTMLPipe } from '../pipes/EscapeHTMLPipe';
import { SafeURLPipe } from '../pipes/SafeURLPipe';

//IMPORT COMPONENTS
import { Facebook } from '../components/facebook/Facebook';

@NgModule({
    declarations: [
       EscapeHTMLPipe,
       SafeURLPipe,
       Facebook
    ],
    imports: [
      FormsModule,
      MatToolbarModule,
      MatButtonModule,
      MatTabsModule,
      MatProgressSpinnerModule,
      MatSidenavModule,
      MatSlideToggleModule,
      MatIconModule, 
      MatMenuModule,
      MatSelectModule,
      MatSnackBarModule,
      MatDatepickerModule,
      MatNativeDateModule,
      MatInputModule,
      InfiniteScrollModule,
      CarouselModule.forRoot(),
      BsDropdownModule.forRoot(),
      ModalModule.forRoot(),
      CollapseModule.forRoot(),
      TabsModule.forRoot(),
      TooltipModule.forRoot(),
      ButtonsModule.forRoot(),
      QuillModule,
      NgxChartsModule
    ],
    exports: [
        FormsModule,
        MatToolbarModule,
        MatButtonModule,
        MatTabsModule,
        MatProgressSpinnerModule,
        MatSidenavModule,
        MatSlideToggleModule,
        MatIconModule, 
        MatMenuModule,
        MatSelectModule,
        MatSnackBarModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatInputModule,
        InfiniteScrollModule,
        CarouselModule,
        BsDropdownModule,
        ModalModule,
        CollapseModule,
        TabsModule,
        TooltipModule,
        ButtonsModule,
        EscapeHTMLPipe,
        SafeURLPipe,
        Facebook,
        QuillModule,
        NgxChartsModule
    ]
  })
  export class CommonModule {}