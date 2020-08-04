import React from 'react';
import './EdgeDetection.css';
import girl from './girl.jpg';

import 'bootstrap/dist/css/bootstrap.min.css';   
import Carousel from 'react-bootstrap/Carousel' 

function EdgeDetection() {
  return (
    <div className='container-fluid body' >
      <center>
        <Carousel className="picture">
          <Carousel.Item >
            <img  className="d-block picture" src={girl} alt="..." />
            
            <Carousel.Caption>
              <h3>First Demo </h3>  
            </Carousel.Caption>
            
          </Carousel.Item>

          <Carousel.Item >
            <img className="d-block picture" src={girl} alt="..." />  
            <Carousel.Caption>  
              <h3>Second Demo</h3>  
            </Carousel.Caption>  
          </Carousel.Item>  

          <Carousel.Item >  
            <img className="d-block picture" src={girl} alt="..." /> 
            <Carousel.Caption> 
              <h3>Third Demo</h3> 
            </Carousel.Caption>
          </Carousel.Item>
        </Carousel> 
      </center>
    
    </div>
  );
}

export default EdgeDetection;
