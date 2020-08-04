import React from 'react';
// import logo from './girl.jpg';
import ImageUploader from 'react-images-upload';
import 'bootstrap/dist/css/bootstrap.min.css';  
import Tile from './Tile.js';

class Laplace extends React.Component {
 
  constructor(props) {
      super(props);
      this.state = {
        img: null,
        imgUrl: null,
        laplace: null
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
      {
      address: "http://localhost:8080/laplace",
      name: "Laplacian",
      image: this.state.laplace},
      ];

    return (
      <div className="Laplace">
        
        {!this.state.imgUrl   
          ? <header className="Laplace-header">
              <center>
                <table border="0" className="Laplace-table">
                  <tbody>
                    <tr>
                      <td className="Laplace-menu">
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

export default Laplace;
