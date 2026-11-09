import { describe, it, expect, beforeEach, vi } from 'vitest';
import { authService } from './authService';

describe('authService', () => {
  beforeEach(() => {
    localStorage.clear();
    vi.restoreAllMocks();
  });

  it('manages JWT tokens in localStorage properly', () => {
    expect(authService.getToken()).toBeNull();

    authService.setToken('test.jwt.token');
    expect(authService.getToken()).toBe('test.jwt.token');

    authService.removeToken();
    expect(authService.getToken()).toBeNull();
  });

  it('login sends credentials and returns auth response', async () => {
    const mockResponse = {
      token: 'jwt.token.123',
      tokenType: 'Bearer',
      user: {
        id: '123-uuid',
        email: 'user@sama.com',
        fullName: 'Sama User',
        role: 'ROLE_USER',
      },
    };

    global.fetch = vi.fn().mockResolvedValue({
      ok: true,
      json: async () => mockResponse,
    } as unknown as Response);

    const result = await authService.login('user@sama.com', 'password123');

    expect(result.token).toBe('jwt.token.123');
    expect(result.user.email).toBe('user@sama.com');
    expect(authService.getToken()).toBe('jwt.token.123');
  });

  it('login throws error when API returns non-200', async () => {
    global.fetch = vi.fn().mockResolvedValue({
      ok: false,
      json: async () => ({ message: 'Invalid email or password' }),
    } as unknown as Response);

    await expect(authService.login('user@sama.com', 'wrongPassword')).rejects.toThrow(
      'Invalid email or password'
    );
  });
});

