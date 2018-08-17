import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class PeopleService {
  public API = '//localhost:8080/api';
  public STUBS_API = this.API + "/stubs";
  public PERSON_API = this.STUBS_API + "/person";

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.PERSON_API);
  }

  get(id: string) {
    return this.http.get(this.PERSON_API + '/' + id);
  }

}
