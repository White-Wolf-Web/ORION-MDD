import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/models/topic.model';
import { TopicsService } from 'src/app/services/topic/topic.service'; 
import { TopicsStateService } from 'src/app/services/topic/topics-state.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit {
  topics: Topic[] = [];

  constructor(
    private topicsService: TopicsService,
    private topicsStateService: TopicsStateService
  ) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    const storedTopics = this.topicsStateService.loadTopicsState();
    if (storedTopics.length > 0) {
      this.topics = storedTopics;
    } else {
      this.topicsService.getTopics().subscribe({
        next: (topics) => {
          this.topics = topics;
          this.topicsStateService.saveTopicsState(this.topics);
        },
        error: (error) => console.error('Error loading topics:', error),
      });
    }
  }

  handleAction(topic: Topic, isSubscribing: boolean): void {
    const action$ = isSubscribing ? this.topicsService.subscribeToTopic(topic.id) : this.topicsService.unsubscribeFromTopic(topic.id);

    action$.subscribe({
      next: () => {
        this.topicsStateService.updateTopicSubscription(topic.id, isSubscribing);
        this.loadTopics(); 
      },
      error: (error) => console.error(`Error handling action for topic ID: ${topic.id}:`, error),
    });
  }

  public onSubscribe(topicId: number): void {
    this.topicsService.subscribeToTopic(topicId).subscribe({
      next: () => console.log(`Subscribed to topic with ID: ${topicId}`),
      error: (error) => console.error(`Error subscribing to topic:`, error)
    });
  }
  
  public onUnsubscribe(topicId: number): void {
    this.topicsService.unsubscribeFromTopic(topicId).subscribe({
      next: () => console.log(`Unsubscribed from topic with ID: ${topicId}`),
      error: (error) => console.error(`Error unsubscribing from topic:`, error)
    });
  }
}