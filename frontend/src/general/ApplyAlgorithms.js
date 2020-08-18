import React from 'react';
// import logo from './girl.jpg';
import './ApplyAlgorithms.css';
import UploadImage from './UploadImage';
import 'bootstrap/dist/css/bootstrap.min.css';  
import Tile from './Tile.js';

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

      {id: 8,
      address: "http://localhost:8080/edge-detection/laplaced",
      name: "Laplacian including Diagonals",
      image: this.state.laplaceDiag},
      ];
 
     return (
      <div className="App">
        {data.map(d => 
            <div>
              <input
                  type="checkbox"
                  onClick={this.onCheckBoxClicked(d)}
              />
              <label>
                {d.name}
              </label>
            </div>  
          )}

        {!this.state.imgUrl   
          ? <UploadImage onFileChange={this.onFileChange}/>
          : <div className="tiles">
              {!!this.state.imgUrl && 
                this.state.selectedData.map((d, index) => {
                  return (
                    <Tile key={index}
                          title={d.name}
                          img={this.state.img}
                          reqUrl={d.address}/>
                  );
                })
              }
            </div>
        }
      </div>
    );
  }
}

export default Algorithms;
