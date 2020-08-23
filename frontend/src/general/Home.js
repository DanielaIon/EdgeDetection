import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';  
import './Home.css';
import BeforeAfterSlider from './before-after-slider/index.js';
import Before from './before.jpeg'; 
import After from './after.png'; 

class Home extends Component {
  render () {
    return (
      <div className="MyPicture">
        {/* <BeforeAfterSlider 
          // className="MySlider"
          after={Before}
          before={After}
          width={800}
          height={600}
        /> */}
        <BeforeAfterSlider
          beforeSrc={Before}
          afterSrc={After}
          styles={{width: 800, height: 600}} />
      </div>
    )
  }
}

export default Home;
