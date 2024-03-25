import React from 'react';
import styled, { useTheme } from 'styled-components';

const LogoContainer = styled.div`
  position: ${props => props.theme.logoContainer.position};
  top: ${props => props.theme.logoContainer.top};
  left: ${props => props.theme.logoContainer.left};
  padding: ${props => props.theme.logoContainer.padding};
`;

const LogoImage = styled.div`
  width: ${props => props.theme.logo.width};
  height: ${props => props.theme.logo.height};
  fill: ${props => props.theme.colors.logoColor};
`;

const Logo = ({ logoComponent: LogoComponent }) => {
  const theme = useTheme();

  return (
    <LogoContainer>
      <LogoImage as={LogoComponent} />
    </LogoContainer>
  );
};

export default Logo;
