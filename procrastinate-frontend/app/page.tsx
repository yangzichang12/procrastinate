"use client";
import theme from './Theme/theme'; 
import { ThemeProvider } from 'styled-components';
import Navbar from "./Navbar/navBarContainer";
import Footer from "./Footer/Footer"
import BodyContainer from "./Body/bodyContainer";

export default async function Home() {
  return (
    <ThemeProvider theme={theme}>
       <Navbar />
      <BodyContainer/>
      <Footer/> 
    </ThemeProvider>
  );
}
