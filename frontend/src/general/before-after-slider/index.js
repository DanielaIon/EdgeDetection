/*
 * Before-After Slider
 * Display before and after images in one space of one image.
 * When the user hovers over the image, the image is divided at that point
 * Move the cursor to the right to see the after image
 * Move the cursor to the left to see the before image
 * The parent element should define some width for this component, flexbox works well
 */

import React from 'react';
import './index.css';



import PropTypes from 'prop-types';

export default class BeforeAfterSlider extends React.Component {

  static propTypes = {
    beforeSrc: PropTypes.string.isRequired,
    afterSrc: PropTypes.string.isRequired,
    styles: PropTypes.object,  // any additional styles to be passed into the container
  };

  constructor(props) {
    super(props);
    this.state = this.initialState();
  }
  initialState = () => {
    return ({
      afterWidth: 50,
      followMouse: false
    });
  };

  componentDidMount = () => {
    this.container.addEventListener('mousedown', (e) => {
      this.setState({followMouse: true})
    });
    this.container.addEventListener('mouseup', (e) => {
      this.setState({followMouse: false})
    });
    this.container.addEventListener('mousemove', (e) => {
      if (this.state.followMouse) {
        this.updateWidth(this.calcWidth(e));
      }
    });
  };

  updateWidth = (width) => {
    this.setState({
      afterWidth: width,
    })
  };

  calcWidth = (e) => {
    if (e.button !== 0) {
      console.log(e)
    }
    return (
      (e.clientX -                        // cursor x axis
      this.container.offsetLeft) /        // how far left on the page is this element
      this.container.clientWidth) * 100;  // total width of the element
  };

  styles = () => {
    return {
      position: 'relative',
      maxWidth: '100%',
      maxHeight: '100%',
      ...this.props.styles,
    }
  };

  render = () => {
    return (
      <div
        style={this.styles()}
        ref={(c) => this.container = c}
      >
        <BeforeImg
          src={this.props.beforeSrc}
          width={`${this.props.styles.width}px`}
          height={`${this.props.styles.height}px`}
          />
        <AfterImg 
          src={this.props.afterSrc}
          width={`${this.state.afterWidth}%`}
          height={`${this.props.styles.height}px`} 
          />
      </div>
    );
  };
}


const BeforeImg = ({
  src,
  width,
  height
}) => {

  const styles = {
    backgroundImage: `url('${src}')`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: 'cover',
    backgroundPosition: 'top left',
    height: height,
    width: width,
    position: 'absolute',
    top: 0,
    left: 0,
    'border-radius': '50px 15px 50px 15px',

  }

  return (
    <div
    style={styles} />
  );
}

const AfterImg = ({
  src,
  width,
  height
}) => {

  const styles = {
    backgroundImage: `url('${src}')`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: 'cover',
    backgroundPosition: 'top left',
    height: height,
    width: width,
    position: 'absolute',
    top: 0,
    left: 0,
    'border-radius': '50px 0px 0px 15px',
    'background-clip': 'padding-box'
  }

  return (
    <div
    style={styles} />
  );
}