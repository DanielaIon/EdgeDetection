import React from 'react';
// import logo from './girl.jpg';
import './ApplyAlgorithms.css';
import UploadImage from './UploadImage';
import 'bootstrap/dist/css/bootstrap.min.css';  
import Tile from './Tile.js';
import RefreshIcon from '@material-ui/icons/Refresh';
import BackupOutlinedIcon from '@material-ui/icons/BackupOutlined'; 
import ImageUploader from 'react-images-upload'; 

class Algorithms extends React.Component {
 
  constructor(props) {
      super(props);
      this.state = {
        img: null,
        imgUrl: null,
        sobel: null,
        prewit: null,
        laplace: null,
        laplaceDiag: null,
        sobelFeldman: null,
        canny: null,
        scharr: null,
        roberts: null,
        test: null,
        selectedData:[],
      };

      this.onFileChange = this.onFileChange.bind(this);
  }

  onFileChange = (e) => {
    console.log(e[0]);
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

  
  render() {
    this.state.selectedData.forEach(d => console.log(d))

    const data = [
      {id: 0,
        address: "http://localhost:8080/edge-detection/test",
        name: "TEST",
        image: this.state.test},

      {id: 1,
      address: "http://localhost:8080/edge-detection/sobel",
      name: "Sobel",
      image: this.state.sobel},

      {id: 2,
      address: "http://localhost:8080/edge-detection/prewitt",
      name: "Prewitt",
      image: this.state.prewit},

      {id: 3,
      address: "http://localhost:8080/edge-detection/roberts",
      name: "Roberts",
      image: this.state.roberts},

      {id: 4,
      address: "http://localhost:8080/edge-detection/scharr",
      name: "Scharr",
      image: this.state.scharr},

      {id: 5,
      address: "http://localhost:8080/edge-detection/canny",
      name: "Canny",
      image: this.state.canny},

      {id: 6,
      address: "http://localhost:8080/edge-detection/sobelFeldman",
      name: "Sobel Feldman",
      image: this.state.sobelFeldman},

      {id: 7,
      address: "http://localhost:8080/edge-detection/laplace",
      name: "Laplacian",
      image: this.state.laplace},
      ];
 
     return (
      <div className="App">
        <div className="DivContainer">
            <div className="Options">
              {data.map(d => 
                  <div>
                    <label className="container">
                      {d.name}
                      <input
                        type="checkbox"
                        onClick={this.onCheckBoxClicked(d)}
                    />
                    <span class="checkmark"></span>
                    </label>
                  </div>  
                )}
                <div height="0px">
                  <ImageUploader
                          className="Upload"
                          withIcon={false}
                          withLabel={false}
                          buttonText={<div> <BackupOutlinedIcon/>  {" Upload image "}</div>}
                          singleImage={true}
                          onChange={this.onFileChange}
                          imgExtension={['.jpg', '.png']}
                          maxFileSize={5242880}
                  />
                  <div className="OptionsPictureContainer">
                      {this.state.imgUrl && <img src={this.state.imgUrl} className="OptionsPicture"/>}
                  </div>
                </div>
            </div>

            <div className="Pictures">
              {this.state.imgUrl   
                &&
                 <div className="tiles">
                    {!!this.state.imgUrl && 
                      this.state.selectedData.map((d) => {
                        return (
                          <Tile key={d.id}
                                title={d.name}
                                img={this.state.img}
                                reqUrl={d.address}/>
                        );
                      })
                    }
                  </div>
              }
            </div>
        </div>
      </div>
    );
  }
}

export default Algorithms;
