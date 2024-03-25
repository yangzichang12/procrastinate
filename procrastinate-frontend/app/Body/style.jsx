import styled from 'styled-components';

export const Button = styled.button`
  background-color: ${props => props.theme.colors.primary};
  color: ${props => props.theme.colors.secondary};
  border: none;
  padding: 0.5rem 1rem;
`;

export const StyledBodyContent = styled.div`
  max-width: ${props => props.theme.bodyContent.maxWidth};
  margin: ${props => props.theme.bodyContent.margin};
  padding: ${props => props.theme.bodyContent.padding};
  height: calc(100vh - 10vh);
`;

export const StyledBodyContainer = styled.div`
  max-width: ${props => props.theme.bodyContent.maxWidth};
  margin: ${props => props.theme.bodyContent.margin};
  padding: ${props => props.theme.bodyContent.padding};
  height: calc(100vh - 10vh);
`;
