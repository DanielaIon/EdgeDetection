import React from 'react';
import './ApplyAlgorithms.css';
import './AnotherAproach.css';
import 'bootstrap/dist/css/bootstrap.min.css';  
import Tile from './Tile.js';
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
        selectedData:[],
      };

      this.onFileChange = this.onFileChange.bind(this);
  }

  onFileChange = (e) => {
    let file = e[0]
    let url = URL.createObjectURL(file);
    this.setState({img: file, imgUrl: url});
  }

  onCheckBoxClicked = (value) => {
    return (e) => {
      if (e.target.checked) {

        this.setState({
          selectedData: [...this.state.selectedData, value]
        });
      } else {
        this.setState({
          selectedData: this.state.selectedData.filter(o => o.name !== value.name)
        });
      }
    }
  }

  onSelectAll = (data) => {
    return (e) => {
      if (e.target.checked) {
        this.setState({
          selectedData: data
        });

      } else {
        this.setState({
          selectedData: []
        });
      }
    }
  }
  
  render() {
    console.log(this.state.selectedData.map(d => d.name));

    const data = [
      {id: 0,
        address: "",
        name: "Original Image",
        image: this.state.img},
      {id: 1,
        address: "http://localhost:8080/new-edge-detection-algorithm/gaussianFilter",
        name: "Gaussian Filter",
        image: this.state.gaussianFilter},

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
        address: "http://localhost:8080/new-edge-detection-algorithm/edgeFollowing",
        name: "Edge Following",
        image: this.state.gaussianFilter},

      {id: 6,
        address: "http://localhost:8080/new-edge-detection-algorithm/hystersisThresholding",
        name: "Hystersis Thresholding",
        image: this.state.gaussianFilter},

      {id: 7,
        address: "http://localhost:8080/new-edge-detection-algorithm/finalResult",
        name: "Final Result",
        image: this.state.finalResult},
      ];
 
     return (
      <div className="App">
        <div className="DivContainer">
            <VerticalTimeline>
              
            <VerticalTimelineElement
                className="vertical-timeline-element--work"
                contentStyle={{ background:  '#BD9EDC', color: '#000000' }}
                contentArrowStyle={{ borderRight: '7px solid  #BD9EDC' }}
                date="Original Image"
                iconStyle={{ background:  '#BD9EDC', color: '#000000' }}
                icon={<ArrowLeft />}
              >
              <p>Original Image should be here</p>
              </VerticalTimelineElement>
              
              <VerticalTimelineElement
                className="vertical-timeline-element--work"
                contentStyle={{ background: '#7a3db8', color: '#000000' }}
                contentArrowStyle={{ borderRight: '7px solid  #7a3db8' }}
                date="Gaussian Filter"
                iconStyle={{ background: '#7a3db8', color: '#000000' }}
                icon={<ArrowRight />}
              >
              <p>Gaussian Filter Image should be here</p>
              </VerticalTimelineElement>

              <VerticalTimelineElement
                className="vertical-timeline-element--work"
                contentStyle={{ background:  '#BD9EDC', color: '#000000' }}
                contentArrowStyle={{ borderRight: '7px solid  #BD9EDC' }}
                date="Gradient"
                iconStyle={{ background:  '#BD9EDC', color: '#000000' }}
                icon={<ArrowLeft />}
              >
              <p>Gradient Image should be here</p>
              </VerticalTimelineElement>
              
              <VerticalTimelineElement
                className="vertical-timeline-element--work"
                contentStyle={{background: '#7a3db8'}}
                contentArrowStyle={{ borderRight: '7px solid  #7a3db8' }}
                date="Non Maximum Suppresion"
                iconStyle={{background: '#7a3db8', color: '#000000' }}
                icon={<ArrowRight />}
              >
              <p>Non Maximum Suppression Image should be here</p>
              </VerticalTimelineElement>

              <VerticalTimelineElement
                className="vertical-timeline-element--work"
                contentStyle={{ background:  '#BD9EDC', color: '#000000' }}
                contentArrowStyle={{ borderRight: '7px solid  #BD9EDC' }}
                date="Laplacian term"
                iconStyle={{ background:  '#BD9EDC', color: '#000000' }}
                icon={<ArrowLeft />}
              >
              <p>Laplacian Image should be here</p>
              </VerticalTimelineElement>
              
              <VerticalTimelineElement
                className="vertical-timeline-element--work"
                contentStyle={{ background: '#7a3db8', color: '#000000' }}
                contentArrowStyle={{ borderRight: '7px solid  #7a3db8' }}
                date="Edge following description"
                iconStyle={{ background: '#7a3db8', color: '#000000' }}
                icon={<ArrowRight />}
              >
              <p>Edge following Image should be here</p>
              </VerticalTimelineElement>

              <VerticalTimelineElement
                className="vertical-timeline-element--work"
                contentStyle={{ background:  '#BD9EDC', color: '#000000' }}
                contentArrowStyle={{ borderRight: '7px solid  #BD9EDC' }}
                date="Hystersis Thresholding description"
                iconStyle={{ background:  '#BD9EDC', color: '#000000' }}
                icon={<ArrowLeft />}
              >
              <p>Hystersis Thresholding Image should be here</p>
              </VerticalTimelineElement>
              
              <VerticalTimelineElement
                className="vertical-timeline-element--work"
                contentStyle={{ background: '#7a3db8', color: '#000000' }}
                contentArrowStyle={{ borderRight: '7px solid  #7a3db8' }}
                date="Final Result"
                iconStyle={{ background: '#7a3db8', color: '#000000' }}
                icon={<ArrowRight />}
              >
              <p>Final Image should be here</p>
              </VerticalTimelineElement>


            </VerticalTimeline>
            </div>
        </div>
    );
  }
}

export default Algorithms;
