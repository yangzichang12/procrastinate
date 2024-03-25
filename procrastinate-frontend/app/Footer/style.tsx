import styled from 'styled-components';

export const StyledFooter = styled.footer`
  background-color: ${props => props.theme.footer.backgroundColor};
  color: ${props => props.theme.footer.color};
  padding: ${props => props.theme.footer.padding};
  text-align: ${props => props.theme.footer.textAlign};
  position: fixed;
  bottom: 0;
  width: 100%;
`;