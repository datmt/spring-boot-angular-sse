import {Injectable} from '@angular/core';
import {Observable, shareReplay, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {SimpleResponse} from "./simple-response";
//@ts-ignore
import {v4 as uuid} from 'uuid';
@Injectable({
  providedIn: 'root'
})
export class FortuneTellerService {

  private eventSource: EventSource | undefined;
  private sseDataSubject: Subject<string> = new Subject<string>();
  private static retryCount = 0;
  private static readonly MAX_RETRIES = 5;
  //generate unique id for each subscriber
  private static subscriberId = uuid();
  constructor(private httpClient: HttpClient) {
  }

  private connectToSSE() {
    this.eventSource = new EventSource(`http://localhost:8080/teller/subscribe/${FortuneTellerService.subscriberId}`);
    console.log('creating event source');
    this.eventSource.onmessage = event => {
      console.log('received event', event)
      this.sseDataSubject.next(event.data);
    };

    this.eventSource.onerror = error => {
      console.log('error', error);
      if (FortuneTellerService.retryCount > FortuneTellerService.MAX_RETRIES) {
        console.log('too many retries');
        this.sseDataSubject.error(error);
        this.eventSource!.close();
        return;
      }
      FortuneTellerService.retryCount++;
      this.sseDataSubject.error(error);
      this.eventSource!.close();
      this.connectToSSE();
    };

  }
  subscribeToFortuneTeller(): Observable<string> {
    if (!this.eventSource) {
      this.connectToSSE();
    }
    return this.sseDataSubject.asObservable();
  }

  requestFortuneTeller(name: string): Observable<SimpleResponse> {
    return this.httpClient.get<SimpleResponse>(`http://localhost:8080/teller/future/${name}/${FortuneTellerService.subscriberId}`).pipe(shareReplay());
  }

}
