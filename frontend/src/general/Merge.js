import React from 'react';
// import logo from './girl.jpg';
import './Merge.css';
import 'bootstrap/dist/css/bootstrap.min.css';  
import BackupOutlinedIcon from '@material-ui/icons/BackupOutlined';
import ImageUploader from 'react-images-upload'; 

const axios = require('axios');

class Algorithms extends React.Component {
 
  constructor(props) {
      super(props);
      this.state = {
        img: null,
        imgUrl: null,
        result: null,
        open: false,
        loading: false,
        options: [],
        selectedOptions: [],
      };
  }

  componentDidMount() {
    axios.get('http://localhost:8080/merge')
      .then((res) => {
        this.setState({
          options: res.data,
          selectedOptions: res.data,
        });
      }).catch((error) => {
        console.log(error);
      });
  }

  onCheckBoxClicked = (value) => {
    return (e) => {
      if (e.target.checked) {
        this.setState({
          selectedOptions: [...this.state.selectedOptions, value]
        });
      } else {
        this.setState({
          selectedOptions: this.state.selectedOptions.filter(o => o !== value)
        });
      }
    }
  }

  onFileChange = (e) => {
    let file = e[0]
    let url = URL.createObjectURL(file);
    this.setState({
      img: file,
      imgUrl: url,
      loading: true
    });

    const formData = new FormData();
    formData.append( 
      "img",
      file 
    );
    formData.append( 
      "options",
      JSON.stringify(this.state.selectedOptions)
    );

    axios.post('http://localhost:8080/merge', formData, {headers: {"Content-type": "multipart/form-data"}})
      .then((res) => {
        let result = "data:image/png;base64," + res.data;
        this.setState({result: result, loading: false});
      }).catch((error) => {
        console.log(error);
      });
  }

  render() {
    
    return (
      <div className="App">
        <div className="DivContainer">
          <div className="Options">
            {this.state.options.map(op => 
              <div>
                <label className="container">
                  {op}
                  <input
                    type="checkbox"
                    checked={this.state.selectedOptions.filter(sop => op === sop).length > 0}
                    onClick={this.onCheckBoxClicked(op)}
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
                      imgExtension={['.jpg', '.png', '.jpeg']}
                      maxFileSize={5242880}
              />
              <div className="OptionsPictureContainer">
                {this.state.imgUrl && <img src={this.state.imgUrl} className="OptionsPicture"/>}
              </div>
            </div>
            <br/>
          </div>
          <div className="MergedPictures">
            {this.state.imgUrl &&
                <img
                  className="MergedPicture"
                  src={this.state.loading ? "https://i.pinimg.com/originals/58/4b/60/584b607f5c2ff075429dc0e7b8d142ef.gif" : this.state.result}
                />
            }
          </div>
        </div>
      </div>
    );
  }
}

export default Algorithms;
