
import { Injectable } from '@angular/core';
import { Topic } from '../models/topic.model';


@Injectable({
    providedIn: 'root',
  })
  export class StorageService {

saveTopicsState(topics: Topic[]): void {
    localStorage.setItem('topicsState', JSON.stringify(topics));
  }
  
  loadTopicsState(): Topic[] | null {
    const topicsJson = localStorage.getItem('topicsState');
    return topicsJson ? JSON.parse(topicsJson) : null;
  }
}