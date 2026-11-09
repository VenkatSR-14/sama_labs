import React from 'react';
import {
  Container,
  Box,
  Paper,
  Typography,
  Button,
  Avatar,
  Chip,
  Grid,
} from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import BrandEmblem from '../../components/common/BrandEmblem';
import { useAuth } from '../../context/AuthContext';

export const Dashboard: React.FC = () => {
  const { user, logout } = useAuth();

  return (
    <Container maxWidth="md" sx={{ py: 6 }}>
      {/* Top Header Card */}
      <Paper
        elevation={0}
        sx={{
          p: { xs: 3, sm: 5 },
          borderRadius: 3,
          position: 'relative',
          overflow: 'hidden',
          background: 'linear-gradient(180deg, #FFFFFF 0%, #FDFBF7 100%)',
          mb: 4,
        }}
      >
        {/* Top Gold Accent */}
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

        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', flexWrap: 'wrap', gap: 2 }}>
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2.5 }}>
            <Avatar
              sx={{
                width: 64,
                height: 64,
                bgcolor: 'primary.main',
                fontSize: '1.5rem',
                fontWeight: 700,
                boxShadow: '0 4px 14px rgba(200, 75, 49, 0.3)',
              }}
            >
              {user?.fullName?.charAt(0).toUpperCase() || 'U'}
            </Avatar>
            <Box>
              <Typography variant="h5" sx={{ fontWeight: 700, color: 'text.primary' }}>
                Welcome, {user?.fullName || 'User'}
              </Typography>
              <Typography variant="body2" sx={{ color: 'text.secondary', mt: 0.2 }}>
                {user?.email}
              </Typography>
            </Box>
          </Box>

          <Button
            variant="outlined"
            color="primary"
            startIcon={<LogoutIcon />}
            onClick={logout}
            sx={{
              borderColor: 'rgba(200, 75, 49, 0.4)',
              color: 'primary.main',
              '&:hover': {
                borderColor: 'primary.main',
                bgcolor: 'rgba(200, 75, 49, 0.05)',
              },
            }}
          >
            Sign Out
          </Button>
        </Box>
      </Paper>

      {/* System Status / Welcome Overview */}
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <Paper
            elevation={0}
            sx={{
              p: 3.5,
              borderRadius: 3,
              background: '#FFFFFF',
              height: '100%',
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'space-between',
            }}
          >
            <Box>
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 1.5 }}>
                <CheckCircleIcon sx={{ color: '#2E7D32' }} />
                <Typography variant="h6" sx={{ fontWeight: 600 }}>
                  Authentication Active
                </Typography>
              </Box>
              <Typography variant="body2" sx={{ color: 'text.secondary', lineHeight: 1.6 }}>
                Your session is secured via stateless JSON Web Tokens (JWT) verified by the Java Spring Boot service and stored in Supabase PostgreSQL.
              </Typography>
            </Box>
            <Box sx={{ mt: 2.5 }}>
              <Chip
                label="Role: Verified Member"
                size="small"
                sx={{
                  bgcolor: '#FFF8E1',
                  color: '#795548',
                  fontWeight: 600,
                  border: '1px solid #FFE082',
                }}
              />
            </Box>
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper
            elevation={0}
            sx={{
              p: 3.5,
              borderRadius: 3,
              background: '#FFFFFF',
              height: '100%',
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              textAlign: 'center',
              justifyContent: 'center',
            }}
          >
            <BrandEmblem size={52} />
            <Typography variant="body2" sx={{ color: 'text.secondary', mt: 1, fontStyle: 'italic' }}>
              "In equanimity and focus, clarity unfolds."
            </Typography>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Dashboard;

