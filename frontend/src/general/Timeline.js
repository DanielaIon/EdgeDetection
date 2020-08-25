import React from 'react';
import './ApplyAlgorithms.css';
import './Timeline.css';
import 'bootstrap/dist/css/bootstrap.min.css';  
import TimelineTile from './TimelineTile.js';
import BackupOutlinedIcon from '@material-ui/icons/BackupOutlined'; 
import ImageUploader from 'react-images-upload'; 
import { VerticalTimeline, VerticalTimelineElement }  from 'react-vertical-timeline-component';
import 'react-vertical-timeline-component/style.min.css';
import ArrowLeft from '@material-ui/icons/ArrowLeft';
import ArrowRight from '@material-ui/icons/ArrowRight';
class Algorithms extends React.Component {
 
  constructor(props) {
      super(props);
      this.state = {
        img: null,
        imgUrl: null,
        gaussianFilter: null,
        gradient: null,
        nms: null,
        laplacian: null,
        edgeFollowing: null,
        hystersisThresholding: null,
        finalResult: null,
        open: false,
      };

      this.onFileChange = this.onFileChange.bind(this);
  }

  onFileChange = (e) => {
    let file = e[0]
    let url = URL.createObjectURL(file);
    this.setState({
      img: file, 
      imgUrl: url,
      gaussianFilter: url,
      gradient: url,
      nms: url,
      laplacian: url,
      edgeFollowing: url,
      hystersisThresholding: url,
      finalResult: url,
    });
  }

  onImageClick = (e) => {
    e.preventDefault();
    this.setState({open: !this.state.open});
  }

  render() {
    const data = [
      {id: 2,
        address: "http://localhost:8080/new-edge-detection-algorithm/gradient",
        name: "Gradient",
        image: this.state.gradient},

      {id: 3,
        address: "http://localhost:8080/new-edge-detection-algorithm/nms",
        name: "Non Maximum Suppression",
        image: this.state.nms},

      {id: 4,
        address: "http://localhost:8080/new-edge-detection-algorithm/laplacian",
        name: "Laplacian term",
        image: this.state.laplacian},

      {id: 5,
        address: "http://localhost:8080/new-edge-detection-algorithm/edge-following",
        name: "Edge Following",
        image: this.state.gaussianFilter},

      ];
 
     return (
      <div className="App">
        {!this.state.imgUrl
        ?
        <div height="0px">
          <ImageUploader
                  className="AnotherAproach"
                  withIcon={false}
                  withLabel={false}
                  buttonText={<div> <BackupOutlinedIcon/>  {" Upload image "}</div>}
                  singleImage={true}
                  onChange={this.onFileChange}
                  imgExtension={['.jpg', '.png', '.jpeg']}
                  maxFileSize={5242880}
          />
        </div>

        :<div className="DivContainer">
            <VerticalTimeline>

            <VerticalTimelineElement
                      className="vertical-timeline-element--work"
                      contentStyle={{ background:  '#BD9EDC', color: '#000000' }}
                      contentArrowStyle={{ borderRight: '7px solid  #BD9EDC' }}
                      date="Original image"
                      iconStyle={{ background:  '#BD9EDC', color: '#000000' }}
                    >
                    <div className={this.state.open ? 'tile-container' : 'none'}>
                        <img    src={this.state.imgUrl}
                                onClick={this.onImageClick}
                                className={this.state.open ? 'magnified' : 'TimelineImage'}/>
                    </div>
              </VerticalTimelineElement>

              {data.map((d) => {
                return (
                  <VerticalTimelineElement
                      className="vertical-timeline-element--work"
                      contentStyle={{ background:  '#BD9EDC', color: '#000000' }}
                      contentArrowStyle={{ borderRight: '7px solid  #BD9EDC' }}
                      date={d.name}
                      iconStyle={{ background:  '#BD9EDC', color: '#000000' }}
                    >
                    <TimelineTile key={d.id}
                      title={d.name}
                      img={this.state.img}
                      reqUrl={d.address}/>
                    </VerticalTimelineElement>
                );})
              }  
            </VerticalTimeline>
            </div>
          }      
        </div>
    );
  }
}

export default Algorithms;
