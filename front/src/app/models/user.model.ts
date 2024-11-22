export interface UserDto {
    id: number;
    username: string;
    email: string;
    createdAt: string;
    password?:string;
    updatedAt?: string;
    subscriptions?: {
      id: number; 
      name: string; 
      description?: string; 
    }[];
  }
  