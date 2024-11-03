export interface SubscriptionDto {
    id: number;
    name: string;
    description: string;
    title?: string;
    content?: string;
    userIds: number[];
  }
  