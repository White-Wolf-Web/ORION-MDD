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
    return topicsJson ? JSON.parse(topicsJson) : [];
  }
  
  updateTopicSubscription(topicId: number, subscribed: boolean): void {
    let topics = this.loadTopicsState();
    const topicIndex = topics.findIndex(t => t.id === topicId);
    if (topicIndex !== -1) {
      topics[topicIndex].subscribed = subscribed;
      this.saveTopicsState(topics);
    }
  }
}
