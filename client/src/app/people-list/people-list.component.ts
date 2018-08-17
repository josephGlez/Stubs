import { Component, OnInit } from '@angular/core';
import { PeopleService } from '../shared/people/people.service';

@Component({
  selector: 'app-people-list',
  templateUrl: './people-list.component.html',
  styleUrls: ['./people-list.component.css']
})
export class PeopleListComponent implements OnInit {
  peoples: Array<any>;

  constructor(private peopleService: PeopleService) { }

  ngOnInit() {
    this.peopleService.getAll().subscribe(data => {
      this.peoples = data;
    });
  }
}
