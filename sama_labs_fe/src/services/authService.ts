import { ENV } from '../config/env';
import type { UserProfile } from '../types/auth';

export interface AuthResponse {
  token: string;
  tokenType: string;
  user: UserProfile;
}

const TOKEN_KEY = 'sama_auth_token';

export const authService = {
  /**
   * Returns saved JWT token from localStorage
   */
  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  },

  /**
   * Saves JWT token to localStorage
   */
  setToken(token: string): void {
    localStorage.setItem(TOKEN_KEY, token);
  },

  /**
   * Clears saved token from localStorage
   */
  removeToken(): void {
    localStorage.removeItem(TOKEN_KEY);
  },

  /**
   * Authenticates user via Java Spring Boot backend
   */
  async login(email: string, password: string): Promise<AuthResponse> {
    const response = await fetch(`${ENV.API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password }),
    });

    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message || 'Authentication failed');
    }

    this.setToken(data.token);
    return data as AuthResponse;
  },

  /**
   * Registers user via Java Spring Boot backend
   */
  async signup(fullName: string, email: string, password: string): Promise<AuthResponse> {
    const response = await fetch(`${ENV.API_BASE_URL}/auth/signup`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ fullName, email, password }),
    });

    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message || 'Registration failed');
    }

    this.setToken(data.token);
    return data as AuthResponse;
  },

  /**
   * Fetches current authenticated user profile using JWT token
   */
  async getCurrentUser(): Promise<UserProfile | null> {
    const token = this.getToken();
    if (!token) return null;

    try {
      const response = await fetch(`${ENV.API_BASE_URL}/auth/me`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        this.removeToken();
        return null;
      }

      const user = await response.json();
      return user as UserProfile;
    } catch {
      return null;
    }
  },

  /**
   * Logs out user
   */
  logout(): void {
    this.removeToken();
  },
};
