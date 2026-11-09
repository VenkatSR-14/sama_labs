import { useState } from 'react';
import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import theme from './theme/theme';
import { AuthProvider, useAuth } from './context/AuthContext';
import Login from './pages/Login/Login';
import Signup from './pages/Signup/Signup';
import Dashboard from './pages/Home/Dashboard';

function MainApp() {
  const { isAuthenticated, isLoading } = useAuth();
  const [authView, setAuthView] = useState<'login' | 'signup'>('login');

  if (isLoading) {
    return (
      <Box
        sx={{
          minHeight: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          bgcolor: 'background.default',
        }}
      >
        <CircularProgress color="primary" size={48} />
      </Box>
    );
  }

  if (isAuthenticated) {
    return <Dashboard />;
  }

  return authView === 'login' ? (
    <Login onNavigateToSignup={() => setAuthView('signup')} />
  ) : (
    <Signup onNavigateToLogin={() => setAuthView('login')} />
  );
}

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <Box
          sx={{
            minHeight: '100vh',
            width: '100%',
            bgcolor: 'background.default',
            backgroundImage: `
              radial-gradient(circle at 10% 20%, rgba(245, 158, 11, 0.05) 0%, transparent 40%),
              radial-gradient(circle at 90% 80%, rgba(200, 75, 49, 0.05) 0%, transparent 40%)
            `,
          }}
        >
          <MainApp />
        </Box>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;
