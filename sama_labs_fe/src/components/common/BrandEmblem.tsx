import React from 'react';
import { Box, Typography } from '@mui/material';

interface BrandEmblemProps {
  size?: number;
}

export const BrandEmblem: React.FC<BrandEmblemProps> = ({ size = 56 }) => {
  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        mb: 2.5,
      }}
    >
      {/* Radiant Lotus / Deepam Motif SVG */}
      <Box
        sx={{
          width: size,
          height: size,
          borderRadius: '50%',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          background: 'radial-gradient(circle, rgba(255, 238, 217, 0.9) 0%, rgba(254, 246, 237, 0.4) 70%, transparent 100%)',
          p: 0.5,
          position: 'relative',
        }}
      >
        <svg
          width={size * 0.85}
          height={size * 0.85}
          viewBox="0 0 100 100"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <defs>
            <linearGradient id="goldSaffron" x1="0%" y1="0%" x2="100%" y2="100%">
              <stop offset="0%" stopColor="#F59E0B" />
              <stop offset="50%" stopColor="#E65100" />
              <stop offset="100%" stopColor="#B71C1C" />
            </linearGradient>
            <linearGradient id="pureGold" x1="0%" y1="0%" x2="100%" y2="100%">
              <stop offset="0%" stopColor="#FDE68A" />
              <stop offset="60%" stopColor="#D4AF37" />
              <stop offset="100%" stopColor="#92620A" />
            </linearGradient>
            <filter id="gentleGlow" x="-20%" y="-20%" width="140%" height="140%">
              <feGaussianBlur stdDeviation="3" result="blur" />
              <feComposite in="SourceGraphic" in2="blur" operator="over" />
            </filter>
          </defs>

          {/* Halo ring */}
          <circle
            cx="50"
            cy="50"
            r="44"
            stroke="url(#pureGold)"
            strokeWidth="1.5"
            strokeDasharray="2 4"
            opacity="0.8"
          />

          {/* Central Flame / Lotus Petals */}
          {/* Left Petal */}
          <path
            d="M50 72 C32 66 26 44 42 28 C45 38 46 54 50 72 Z"
            fill="url(#goldSaffron)"
            opacity="0.9"
          />
          {/* Right Petal */}
          <path
            d="M50 72 C68 66 74 44 58 28 C55 38 54 54 50 72 Z"
            fill="url(#goldSaffron)"
            opacity="0.9"
          />
          {/* Center Royal Petal / Flame */}
          <path
            d="M50 76 C41 55 39 34 50 14 C61 34 59 55 50 76 Z"
            fill="url(#pureGold)"
            filter="url(#gentleGlow)"
          />

          {/* Base Lotus Pod / Chalice */}
          <path
            d="M32 72 C42 84 58 84 68 72 C60 76 40 76 32 72 Z"
            fill="url(#goldSaffron)"
          />

          {/* Auspicious Center Bindu (Golden Seed Point) */}
          <circle cx="50" cy="54" r="3.5" fill="#FFF8E7" />
        </svg>
      </Box>

      {/* Brand Title */}
      <Typography
        variant="h4"
        component="h1"
        sx={{
          fontWeight: 800,
          letterSpacing: '0.14em',
          mt: 0.5,
          background: 'linear-gradient(135deg, #B83917 0%, #D4AF37 80%)',
          WebkitBackgroundClip: 'text',
          WebkitTextFillColor: 'transparent',
          fontFamily: 'serif',
          textTransform: 'uppercase',
        }}
      >
        Sāma
      </Typography>

      <Typography
        variant="caption"
        sx={{
          color: 'text.secondary',
          letterSpacing: '0.22em',
          textTransform: 'uppercase',
          fontSize: '0.68rem',
          fontWeight: 500,
          mt: 0.3,
        }}
      >
        Equanimity • Clarity • Harmony
      </Typography>
    </Box>
  );
};

export default BrandEmblem;

