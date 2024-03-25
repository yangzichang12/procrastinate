import styled from 'styled-components';

export const StyledNavbar = styled.nav`
  background-color: ${props => props.theme.navbar.backgroundColor};
  color: ${props => props.theme.navbar.color};
  padding: ${props => props.theme.navbar.padding};
  display: flex;
  justify-content: space-between; /* Add space between logo and menu items */
  align-items: center; /* Center items vertically */
`;

export const StyledMenu = styled.ul`
  list-style: none;
  padding: 0;
  display: flex;
`;

export const StyledMenuItem = styled.li`
  margin-right: 1rem;
`;

export const StyledLink = styled.a`
  text-decoration: none;
  color: inherit;
`;