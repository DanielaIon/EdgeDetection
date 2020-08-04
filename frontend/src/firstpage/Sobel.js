import React from 'react';
// import logo from './girl.jpg';
import './Sobel.css';
import ImageUploader from 'react-images-upload';
import 'bootstrap/dist/css/bootstrap.min.css';  
import Tile from './Tile.js';

class Sobel extends React.Component {
 
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
        test: null
      };
  }

  onFileChange = (e) => {
    console.log(e[0]);
    let file = e[0]
    let url = URL.createObjectURL(file);
    this.setState({img: file, imgUrl: url});
  }

  render() {
    const data = [
      {id: 0,
        address: "http://localhost:8080/test",
        name: "TEST",
        image: this.state.test},

      {id: 1,
      address: "http://localhost:8080/sobel",
      name: "Sobel",
      image: this.state.sobel},

      {id: 2,
      address: "http://localhost:8080/prewitt",
      name: "Prewitt",
      image: this.state.prewit},

      {id: 3,
      address: "http://localhost:8080/roberts",
      name: "Roberts",
      image: this.state.roberts},

      {id: 4,
      address: "http://localhost:8080/scharr",
      name: "Scharr",
      image: this.state.scharr},

      {id: 5,
      address: "http://localhost:8080/canny",
      name: "Canny",
      image: this.state.canny},

      {id: 6,
      address: "http://localhost:8080/sobelFeldman",
      name: "Sobel Feldman",
      image: this.state.sobelFeldman},

      {id: 7,
      address: "http://localhost:8080/laplace",
      name: "Laplacian",
      image: this.state.laplace},

      {id: 8,
      address: "http://localhost:8080/laplaced",
      name: "Laplacian including Diagonals",
      image: this.state.laplaceDiag},
      ];

    return (
      <div className="App">
        
        {!this.state.imgUrl   
          ? <header className="App-header">
              <center>
                <table border="0" className="App-table">
                  <tbody>
                    <tr>
                      <td className="App-menu">
                      <ImageUploader
                          className="Upload"
                          style={{'width':'100px'}}
                          withIcon={false}
                          withLabel={false}
                          buttonText='Choose image'
                          singleImage={true}
                          onChange={this.onFileChange}
                          imgExtension={['.jpg', '.gif', '.png', '.gif']}
                          maxFileSize={5242880}
                      />
                      </td>
                    </tr>
                  </tbody>
                </table>
              </center>
            </header>
          : <div className="tiles">
              {!!this.state.imgUrl && 
                data.map((d, index) => {
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

export default Sobel;
