import { render, screen } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { ThemeProvider } from '@mui/material/styles';
import theme from '../../theme/theme';
import Login from './Login';
import { AuthProvider } from '../../context/AuthContext';

describe('Login Component', () => {
  it('renders login form elements properly', () => {
    render(
      <ThemeProvider theme={theme}>
        <AuthProvider>
          <Login onNavigateToSignup={vi.fn()} />
        </AuthProvider>
      </ThemeProvider>
    );

    expect(screen.getByRole('heading', { name: /Welcome Back/i })).toBeInTheDocument();
    expect(screen.getByLabelText(/Email Address/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/^Password/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Sign In/i })).toBeInTheDocument();
    expect(screen.getByText(/Remember me/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Create an account/i })).toBeInTheDocument();
  });
});

