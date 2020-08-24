import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';  
import './Home.css';
import BeforeAfterSlider from './before-after-slider/index.js';
import Before from './before_pic.jpeg'; 
import After from './after_pic.png'; 

class Home extends Component {
  render () {
    return (
      <div className="MyPicture">
        <BeforeAfterSlider
          beforeSrc={Before}
          afterSrc={After}
          styles={{width: 800, height: 600}} />
      </div>
    )
  }
}

export default Home;
