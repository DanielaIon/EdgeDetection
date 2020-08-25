import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';  
import './Home.css';
import BeforeAfterSlider from './before-after-slider/index.js';

import Original from '../resources/home-pic/Original.jpeg'; 
import Canny from '../resources/home-pic/Canny.png'; 
import Kirsch from '../resources/home-pic/Kirsch.png'; 
import Scharr from '../resources/home-pic/Scharr.png'; 
import Sobel from '../resources/home-pic/Sobel.png'; 
import SobelFeldman from '../resources/home-pic/SobelFeldman.png'; 
import Robinson from '../resources/home-pic/Robinson.png'; 
import Roberts from '../resources/home-pic/Roberts.png'; 
import Prewitt from '../resources/home-pic/Prewitt.png'; 
import Laplacian from '../resources/home-pic/Laplacian.png'; 

import ArrowLeftIcon from '@material-ui/icons/ArrowLeft';
import ArrowRightIcon from '@material-ui/icons/ArrowRight';
import Button from 'react-bootstrap/Button'

class Home extends Component {

  constructor(props) {
    super(props);
    this.state = {
      before: {src: Original, title:"Original"},
      after: {src: Canny, title:"Canny"},
      before_next_ID:1,
      before_prev_ID:9,
      after_next_ID:2,
      after_prev_ID:0,
      list:[
          {src: Original, title:"Original"},
          {src: Canny, title:"Canny"}, 
          {src: Kirsch, title:"Kirsch"},
          {src:  Scharr, title:"Scharr"},
          {src: Sobel, title:"Sobel"}, 
          {src: SobelFeldman, title:"Sobel Feldman"},
          {src: Roberts, title:"Roberts"},
          {src: Robinson, title:"Robinson"}, 
          {src: Prewitt, title:"Prewitt"},
          {src: Laplacian, title:"Laplacian"} ],
    };

    this.handleAfterPrevClick = this.handleAfterPrevClick.bind(this);
    this.handleAfterNextClick = this.handleAfterNextClick.bind(this);
    this.handleBeforePrevClick = this.handleBeforePrevClick.bind(this);
    this.handleBeforeNextClick = this.handleBeforeNextClick.bind(this);
}

  handleAfterPrevClick(event) {
    event.preventDefault()

    this.setState({
      after: this.state.list[this.state.after_prev_ID],
      after_next_ID: (this.state.after_next_ID > 0) ? (this.state.after_next_ID - 1 ) : 9,
      after_prev_ID: (this.state.after_prev_ID > 0) ? (this.state.after_prev_ID - 1 ) : 9
    });
  }

  handleAfterNextClick(event) {
    event.preventDefault()

    this.setState({
      after: this.state.list[this.state.after_next_ID],
      after_next_ID: ((this.state.after_next_ID + 1 ) % 10),
      after_prev_ID: ((this.state.after_prev_ID + 1 ) % 10)
    });
  }

  handleBeforePrevClick(event) {
    event.preventDefault()

    this.setState({
      before: this.state.list[this.state.before_prev_ID],
      before_next_ID: (this.state.before_next_ID > 0) ? (this.state.before_next_ID - 1 ) : 9,
      before_prev_ID: (this.state.before_prev_ID > 0) ? (this.state.before_prev_ID - 1 ) : 9
    });
  }

  handleBeforeNextClick(event) {
    event.preventDefault()

    this.setState({
      before: this.state.list[this.state.before_next_ID],
      before_next_ID: ((this.state.before_next_ID + 1 ) % 10),
      before_prev_ID: ((this.state.before_prev_ID + 1 ) % 10)
    });
  }

  render () {
    return (
      <div>
          <div className="MyPicture">
            <BeforeAfterSlider
              beforeSrc={this.state.before.src}
              afterSrc={this.state.after.src}
              styles={{width: 800, height: 600}} />
          </div>

          <div className="ButtonContainer">
            <div className="LeftButtons">
              <center>
                <Button 
                  variant="secondary" 
                  active 
                  onClick={this.handleAfterPrevClick}>
                  {<ArrowLeftIcon className="BlackIcon LargeIcon"/>}
                </Button >
                {this.state.after.title}
                <Button 
                  variant="secondary" 
                  active 
                  onClick={this.handleAfterNextClick}>
                  {<ArrowRightIcon className="BlackIcon LargeIcon"/>}
                  </Button >
                </center>
            </div>

            <div className="RightButtons">
            <center>
              <Button 
                  variant="secondary" 
                  active 
                  onClick={this.handleBeforePrevClick}>
                  {<ArrowLeftIcon className="BlackIcon LargeIcon"/>}
                </Button >
                {this.state.before.title}
                <Button 
                  variant="secondary" 
                  active 
                  onClick={this.handleBeforeNextClick}>
                  {<ArrowRightIcon className="BlackIcon LargeIcon"/>}
                </Button >
              </center>
            </div>
          </div>
        <br/>
      </div>
    )
  }
}

export default Home;
