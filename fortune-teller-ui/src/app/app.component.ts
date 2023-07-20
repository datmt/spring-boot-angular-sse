import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FortuneTellerService} from "./fortune-teller.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'fortune-teller-ui';
  name = '';

  constructor(private fortuneTellerService: FortuneTellerService,
              private cd : ChangeDetectorRef

              ) {
  }

  fortuneResult: string = 'Your future is unknown!'

  waitingForResult: boolean = false;
  waitText = '';

  ngOnInit(): void {
    this.fortuneTellerService.subscribeToFortuneTeller().subscribe({
      next: (fortune) => {
        this.waitingForResult = false;
        console.log('received fortune', fortune)
        this.fortuneResult = fortune.content;
        this.cd.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  tellingFuture() {
    this.waitingForResult = true;
    this.fortuneTellerService.requestFortuneTeller(this.name).subscribe({
      next: waitText => {
        this.waitText = waitText.content;
        this.cd.detectChanges();
      }
    })
  }
}
