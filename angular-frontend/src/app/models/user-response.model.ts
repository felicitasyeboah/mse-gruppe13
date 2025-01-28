import { User } from '../services/api.service';

export interface UserResponse {
  message: string;
  data: User;
}
