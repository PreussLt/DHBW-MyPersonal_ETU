import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-deletebuffer',
  templateUrl: './deletebuffer.component.html',
  styleUrls: ['./deletebuffer.component.css']
})
export class DeletebufferComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.router.navigate(["/arbeitstage"])
  }


}
