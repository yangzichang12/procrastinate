import React from 'react';
import {StyledFooter} from './style';
import {COPYRIGHTS} from './strings';

const Footer = () => {
  return (
    <StyledFooter>
      <p>&copy; {COPYRIGHTS}</p>
    </StyledFooter>
  );
};

export default Footer;
