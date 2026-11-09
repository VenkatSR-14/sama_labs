import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  palette: {
    primary: {
      main: '#C84B31', // Sacred saffron / vermilion
      light: '#E6694E',
      dark: '#932812',
      contrastText: '#FFFFFF',
    },
    secondary: {
      main: '#D4AF37', // Radiant Vedic gold
      light: '#E7C868',
      dark: '#A68417',
      contrastText: '#2B1E12',
    },
    background: {
      default: '#FDF9F3', // Temple sandalwood / sacred parchment cream
      paper: '#FFFFFF',
    },
    text: {
      primary: '#2B1E16', // Deep earthen charcoal
      secondary: '#6E5C50', // Warm sandalwood gray
    },
    divider: 'rgba(212, 175, 55, 0.25)', // Subtle golden thread divider
  },
  typography: {
    fontFamily: [
      'system-ui',
      '-apple-system',
      'BlinkMacSystemFont',
      '"Segoe UI"',
      'Roboto',
      'sans-serif',
    ].join(','),
    h4: {
      fontWeight: 700,
      letterSpacing: '0.04em',
      color: '#2B1E16',
    },
    h5: {
      fontWeight: 600,
      letterSpacing: '0.03em',
      color: '#2B1E16',
    },
    subtitle1: {
      letterSpacing: '0.02em',
      color: '#6E5C50',
    },
  },
  shape: {
    borderRadius: 12,
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 10,
          textTransform: 'none',
          fontWeight: 600,
          fontSize: '0.98rem',
          boxShadow: 'none',
          transition: 'all 0.25s ease-in-out',
        },
        containedPrimary: {
          background: 'linear-gradient(135deg, #D65A31 0%, #B83917 100%)',
          boxShadow: '0 4px 14px rgba(184, 57, 23, 0.28)',
          '&:hover': {
            background: 'linear-gradient(135deg, #E2683F 0%, #C44320 100%)',
            boxShadow: '0 6px 18px rgba(184, 57, 23, 0.38)',
          },
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          '& .MuiOutlinedInput-root': {
            borderRadius: 10,
            backgroundColor: '#FFFEFC',
            transition: 'all 0.2s ease-in-out',
            '&:hover .MuiOutlinedInput-notchedOutline': {
              borderColor: '#D4AF37',
            },
            '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
              borderColor: '#C84B31',
              borderWidth: '2px',
            },
          },
        },
      },
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          border: '1px solid rgba(212, 175, 55, 0.22)',
          boxShadow: '0 12px 40px rgba(110, 70, 30, 0.08), 0 2px 10px rgba(110, 70, 30, 0.04)',
        },
      },
    },
  },
});

export default theme;

