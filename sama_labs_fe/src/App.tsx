import { useState } from 'react';
import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import theme from './theme/theme';
import Login from './pages/Login/Login';
import Signup from './pages/Signup/Signup';

function App() {
  const [currentPage, setCurrentPage] = useState<'login' | 'signup'>('login');

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
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
        {currentPage === 'login' ? (
          <Login onNavigateToSignup={() => setCurrentPage('signup')} />
        ) : (
          <Signup onNavigateToLogin={() => setCurrentPage('login')} />
        )}
      </Box>
    </ThemeProvider>
  );
}

export default App;
