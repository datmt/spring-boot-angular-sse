import {Injectable} from '@angular/core';
import {Observable, shareReplay, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {SimpleResponse} from "./simple-response";

@Injectable({
  providedIn: 'root'
})
export class FortuneTellerService {

  private eventSource: EventSource | undefined;
  private sseDataSubject: Subject<string> = new Subject<string>();

  constructor(private httpClient: HttpClient) {
  }

  subscribeToFortuneTeller(): Observable<string> {
    if (!this.eventSource) {
      this.eventSource = new EventSource('http://localhost:8080/teller/notify');
      console.log('creating event source');
      this.eventSource.onmessage = event => {
        console.log('received event', event)
        this.sseDataSubject.next(event.data);
      };

      this.eventSource.onerror = error => {
        this.sseDataSubject.error(error);
        this.eventSource!.close();
      };

    }
    return this.sseDataSubject.asObservable();
  }

  requestFortuneTeller(name: string): Observable<SimpleResponse> {
    return this.httpClient.get<SimpleResponse>(`http://localhost:8080/teller/future/${name}`).pipe(shareReplay());
  }

}
