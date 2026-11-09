import React, { useState } from 'react';
import {
  Container,
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  FormControlLabel,
  Checkbox,
  Link,
  IconButton,
  InputAdornment,
  CircularProgress,
  Alert,
  Divider,
} from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import BrandEmblem from '../../components/common/BrandEmblem';
import { useAuth } from '../../context/AuthContext';

interface SignupProps {
  onNavigateToLogin?: () => void;
}

export const Signup: React.FC<SignupProps> = ({ onNavigateToLogin }) => {
  const { signup } = useAuth();

  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [agreeTerms, setAgreeTerms] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const [errors, setErrors] = useState<{
    fullName?: string;
    email?: string;
    password?: string;
    confirmPassword?: string;
    agreeTerms?: string;
  }>({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const validate = (): boolean => {
    const newErrors: typeof errors = {};

    if (!fullName.trim()) {
      newErrors.fullName = 'Full name is required';
    } else if (fullName.trim().length < 2) {
      newErrors.fullName = 'Please enter your complete name';
    }

    if (!email.trim()) {
      newErrors.email = 'Email address is required';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      newErrors.email = 'Please enter a valid email address';
    }

    if (!password) {
      newErrors.password = 'Password is required';
    } else if (password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters';
    }

    if (!confirmPassword) {
      newErrors.confirmPassword = 'Confirm your password';
    } else if (password !== confirmPassword) {
      newErrors.confirmPassword = 'Passwords do not match';
    }

    if (!agreeTerms) {
      newErrors.agreeTerms = 'Please acknowledge the terms to proceed';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (event: React.SubmitEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMessage(null);

    if (!validate()) return;

    setIsSubmitting(true);

    try {
      await signup(fullName, email, password);
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Unable to complete registration. Please try again.';
      setErrorMessage(message);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          minHeight: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          py: 4,
        }}
      >
        <Paper
          elevation={0}
          sx={{
            p: { xs: 3.5, sm: 4.5 },
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            borderRadius: 3,
            width: '100%',
            position: 'relative',
            overflow: 'hidden',
            background: 'linear-gradient(180deg, #FFFFFF 0%, #FDFBF7 100%)',
          }}
        >
          {/* Subtle Golden Aura Accent at Top */}
          <Box
            sx={{
              position: 'absolute',
              top: 0,
              left: 0,
              right: 0,
              height: '4px',
              background: 'linear-gradient(90deg, #D4AF37 0%, #C84B31 50%, #D4AF37 100%)',
            }}
          />

          {/* Sacred Brand Emblem */}
          <BrandEmblem size={68} />

          <Typography
            component="h2"
            variant="h5"
            sx={{
              fontWeight: 700,
              color: 'text.primary',
              textAlign: 'center',
              letterSpacing: '0.02em',
            }}
          >
            Create Your Account
          </Typography>

          <Typography
            variant="body2"
            sx={{
              color: 'text.secondary',
              mt: 0.5,
              mb: 3,
              textAlign: 'center',
              fontSize: '0.9rem',
            }}
          >
            Embark on a centered, balanced digital experience
          </Typography>

          {errorMessage && (
            <Alert
              severity="error"
              sx={{
                width: '100%',
                mb: 2.5,
                borderRadius: 2,
                border: '1px solid #FFCDD2',
              }}
            >
              {errorMessage}
            </Alert>
          )}

          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ width: '100%' }}
          >
            <TextField
              margin="dense"
              required
              fullWidth
              id="fullName"
              label="Full Name"
              name="fullName"
              autoComplete="name"
              autoFocus
              value={fullName}
              onChange={(e) => setFullName(e.target.value)}
              error={Boolean(errors.fullName)}
              helperText={errors.fullName}
              disabled={isSubmitting}
            />

            <TextField
              margin="dense"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              error={Boolean(errors.email)}
              helperText={errors.email}
              disabled={isSubmitting}
            />

            <TextField
              margin="dense"
              required
              fullWidth
              name="password"
              label="Password"
              id="password"
              autoComplete="new-password"
              type={showPassword ? 'text' : 'password'}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              error={Boolean(errors.password)}
              helperText={errors.password}
              disabled={isSubmitting}
              slotProps={{
                input: {
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={() => setShowPassword((prev) => !prev)}
                        edge="end"
                        size="small"
                        sx={{ color: 'text.secondary' }}
                      >
                        {showPassword ? <VisibilityOff fontSize="small" /> : <Visibility fontSize="small" />}
                      </IconButton>
                    </InputAdornment>
                  ),
                },
              }}
            />

            <TextField
              margin="dense"
              required
              fullWidth
              name="confirmPassword"
              label="Confirm Password"
              id="confirmPassword"
              autoComplete="new-password"
              type={showConfirmPassword ? 'text' : 'password'}
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              error={Boolean(errors.confirmPassword)}
              helperText={errors.confirmPassword}
              disabled={isSubmitting}
              slotProps={{
                input: {
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle confirm password visibility"
                        onClick={() => setShowConfirmPassword((prev) => !prev)}
                        edge="end"
                        size="small"
                        sx={{ color: 'text.secondary' }}
                      >
                        {showConfirmPassword ? <VisibilityOff fontSize="small" /> : <Visibility fontSize="small" />}
                      </IconButton>
                    </InputAdornment>
                  ),
                },
              }}
            />

            <Box sx={{ mt: 1.5, mb: 0.5 }}>
              <FormControlLabel
                control={
                  <Checkbox
                    checked={agreeTerms}
                    onChange={(e) => setAgreeTerms(e.target.checked)}
                    size="small"
                    sx={{
                      color: errors.agreeTerms ? 'error.main' : 'primary.light',
                      '&.Mui-checked': {
                        color: 'primary.main',
                      },
                    }}
                  />
                }
                label={
                  <Typography variant="body2" sx={{ color: 'text.secondary', fontSize: '0.82rem' }}>
                    I agree to the{' '}
                    <Link href="#" onClick={(e) => e.preventDefault()} underline="hover" sx={{ color: 'primary.main', fontWeight: 600 }}>
                      Terms of Service
                    </Link>{' '}
                    and{' '}
                    <Link href="#" onClick={(e) => e.preventDefault()} underline="hover" sx={{ color: 'primary.main', fontWeight: 600 }}>
                      Privacy Policy
                    </Link>
                  </Typography>
                }
              />
              {errors.agreeTerms && (
                <Typography variant="caption" color="error" sx={{ display: 'block', pl: 3.8, mt: -0.5 }}>
                  {errors.agreeTerms}
                </Typography>
              )}
            </Box>

            <Button
              type="submit"
              fullWidth
              variant="contained"
              size="large"
              disabled={isSubmitting}
              sx={{
                mt: 2.5,
                mb: 2.5,
                py: 1.3,
                fontSize: '1rem',
              }}
            >
              {isSubmitting ? (
                <CircularProgress size={24} sx={{ color: '#FFFFFF' }} />
              ) : (
                'Create Account'
              )}
            </Button>

            <Divider sx={{ my: 2.5, borderColor: 'rgba(212, 175, 55, 0.25)' }}>
              <Typography variant="caption" sx={{ color: 'text.secondary', px: 1 }}>
                or
              </Typography>
            </Divider>

            <Box sx={{ textAlign: 'center' }}>
              <Typography variant="body2" sx={{ color: 'text.secondary', fontSize: '0.9rem' }}>
                Already have an account?{' '}
                <Link
                  component="button"
                  type="button"
                  onClick={onNavigateToLogin}
                  variant="body2"
                  underline="hover"
                  sx={{
                    color: 'primary.main',
                    fontWeight: 700,
                    cursor: 'pointer',
                    fontSize: '0.9rem',
                  }}
                >
                  Sign In
                </Link>
              </Typography>
            </Box>
          </Box>
        </Paper>
      </Box>
    </Container>
  );
};

export default Signup;
