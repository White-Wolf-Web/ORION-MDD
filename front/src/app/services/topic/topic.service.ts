import { Injectable } from '@angular/core';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, tap, catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { Topic } from 'src/app/models/topic.model'; 
import { AuthService } from '../auth/auth.service'; 

@Injectable({
  providedIn: 'root',
})
export class TopicsService {
  private apiUrl = 'http://localhost:8080/api/topics';

  constructor(private httpClient: HttpClient) {}

  getTopics(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.apiUrl}`);
  }

  subscribeToTopic(topicId: number): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/subscribe/${topicId}`, {});
  }

  unsubscribeFromTopic(topicId: number): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/unsubscribe/${topicId}`, {});
  }
}