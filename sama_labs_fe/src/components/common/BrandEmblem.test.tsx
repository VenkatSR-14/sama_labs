import { render, screen } from '@testing-library/react';
import { describe, it, expect } from 'vitest';
import BrandEmblem from './BrandEmblem';

describe('BrandEmblem Component', () => {
  it('renders the brand name Sāma and core values', () => {
    render(<BrandEmblem />);

    expect(screen.getByText(/Sāma/i)).toBeInTheDocument();
    expect(screen.getByText(/Equanimity • Clarity • Harmony/i)).toBeInTheDocument();
  });
});
