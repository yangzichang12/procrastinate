import React from 'react';
import {NavItemList} from './strings';
import HomeLogoSvg from './homeLogoSvg';
import {StyledNavbar, StyledMenu, StyledMenuItem, StyledLink} from './style';

const Navbar = () => {
  return (
    <StyledNavbar>
      <div>
        <HomeLogoSvg/>
      </div>
      <StyledMenu>
      {NavItemList.map((item, index) => (
        <StyledMenuItem key={index}><StyledLink href="#">{item}</StyledLink></StyledMenuItem>
        ))}
      </StyledMenu>
    </StyledNavbar>
  );
};

export default Navbar;
