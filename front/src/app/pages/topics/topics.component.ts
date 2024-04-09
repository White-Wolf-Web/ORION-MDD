import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Topic } from 'src/app/models/topic.model';
import { TopicsService } from 'src/app/services/topic/topic.service'; 
import { Observable } from 'rxjs';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit {
  topics: Topic[] = [];

  constructor(
    private cdr: ChangeDetectorRef,
    private topicsService: TopicsService
  ) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.topicsService.getTopics().subscribe({
      next: (topics) => {
        this.topics = topics;
        this.cdr.detectChanges();  // Assurez-vous que les changements sont détectés
      },
      error: (error) => console.error('Error loading topics:', error),
    });
  }

  handleAction(topic: Topic, isSubscribing: boolean): void {
    const action$ = isSubscribing ? this.topicsService.subscribeToTopic(topic.id) : this.topicsService.unsubscribeFromTopic(topic.id);

    action$.subscribe({
      next: () => {
        topic.subscribed = isSubscribing;
        console.log(`${isSubscribing ? 'Subscribed' : 'Unsubscribed'} to topic with ID: ${topic.id}`);
        this.cdr.detectChanges();
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





