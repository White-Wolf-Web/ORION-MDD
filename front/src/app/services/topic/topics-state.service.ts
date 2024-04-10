import { Injectable } from '@angular/core';
import { Topic } from 'src/app/models/topic.model';


@Injectable({
  providedIn: 'root',
})
export class TopicsStateService {
  private topicsKey = 'topicsState';

  saveTopicsState(topics: Topic[]): void {
    localStorage.setItem(this.topicsKey, JSON.stringify(topics));
  }
  
  loadTopicsState(): Topic[] {
    const topicsJson = localStorage.getItem(this.topicsKey);
    if (topicsJson) {
      return JSON.parse(topicsJson);
    }
    return [];
  }

  updateTopicSubscription(topicId: number, subscribed: boolean): void {
    const topics = this.loadTopicsState();
    const index = topics.findIndex((t) => t.id === topicId);
    if (index !== -1) {
      topics[index].subscribed = subscribed;
      this.saveTopicsState(topics);
    }
  }
}